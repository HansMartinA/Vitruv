package tools.vitruv.framework.versioning.emfstore.impl

import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.branch.impl.RemoteBranchImpl
import tools.vitruv.framework.versioning.emfstore.LocalRepositoryJava
import tools.vitruv.framework.versioning.emfstore.PushState
import tools.vitruv.framework.versioning.emfstore.RemoteRepository
import tools.vitruv.framework.versioning.exceptions.LocalBranchNotFoundException
import tools.vitruv.framework.versioning.exceptions.RemoteBranchNotFoundException
import tools.vitruv.framework.versioning.exceptions.RepositoryNotFoundException
import java.util.List

class LocalRepositoryImpl extends AbstractLocalRepository implements LocalRepositoryJava {
	static extension Logger = Logger::getLogger(LocalRepositoryImpl)

	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	RemoteRepository remoteProject
	@Accessors(PUBLIC_GETTER)
	val List<RemoteBranch> remoteBranches

	val Set<RemoteRepository> remoteRepositories

	new() {
		super()
		remoteRepositories = newHashSet
		remoteBranches = newArrayList
	}

	override addRemoteRepository(RemoteRepository remoteRepository) {
		if (null !== remoteRepository) {
			remoteRepositories += remoteRepository
		}
	}

	override push() {
		push(currentBranch)
	}

	override addOrigin(LocalBranch branch, RemoteRepository remoteRepository) {
		if (!localBranches.exists[it === branch])
			throw new LocalBranchNotFoundException
		if (!remoteRepositories.exists[it === remoteRepository])
			throw new RepositoryNotFoundException
		val branchName = branch.name
		val remoteBranch = remoteRepository.branches.findFirst[name == branchName]
		if (null === remoteBranch)
			throw new RemoteBranchNotFoundException
		val newBranch = createRemoteBranch(remoteBranch.name, remoteRepository)
		branch.remoteBranch = newBranch
	}

	override createRemoteBranch(String name, RemoteRepository remoteRepository) {
		val remoteName = remoteRepository.name
		val newBranchName = '''«remoteName»/«name»'''
		val newBranch = new RemoteBranchImpl(newBranchName)
		addCommit(initialCommit, newBranch)
		newBranch.remoteRepository = remoteRepository
		remoteBranches += newBranch
		return newBranch
	}

	override push(LocalBranch localBranch) {
		val remoteBranch = localBranch.remoteBranch
		if (null === remoteBranch)
			throw new RemoteBranchNotFoundException
		val remoteRepo = remoteBranch.remoteRepository
		if (null === remoteRepo)
			throw new RepositoryNotFoundException
		val ids = remoteRepo.getIdentifiers(localBranch.name)
		val currentCommits = localBranch.commits
		if (ids.length > currentCommits.length)
			return PushState::COMMIT_NOT_ACCEPTED
		val x = 0 ..< ids.length
		val serverHasNewerCommits = x.map[currentCommits.get(it).identifier -> ids.get(it)].exists[key != value]
		if (serverHasNewerCommits)
			return PushState::COMMIT_NOT_ACCEPTED

		val commitsToPush = currentCommits.drop(ids.length)
		commitsToPush.forEach [ commit |
			remoteRepo.push(commit, localBranch.name)
			addCommit(commit, remoteBranch)
		]
		val localCommits = localBranch.commits.toList
		val remoteCommits = remoteBranch.commits.toList
		val localIds = localCommits.map[identifier]
		val remoteIds = remoteCommits.map[identifier]
		localIds.forEach [ localId, i |
			val remoteId = remoteIds.get(i)
			if (remoteId != localId)
				throw new IllegalStateException('''Id at «i» should be «localId» but was «remoteId»''')
		]
		return PushState::SUCCESS
	}

	override pull(LocalBranch branch) {
		val remoteBranch = branch.remoteBranch
		if (null === remoteBranch)
			throw new RemoteBranchNotFoundException
		val remoteRepo = remoteBranch.remoteRepository
		if (null === remoteRepo)
			throw new RepositoryNotFoundException
		val toLocal = branch.commits.length === remoteBranch.commits.length
		val ids = remoteRepo.getIdentifiers(branch.name)
		val localIds = remoteBranch.commits.map[identifier]
		if (localIds.length > ids.length)
			throw new IllegalStateException
		localIds.forEach [ localId, i |
			val remoteId = ids.get(i)
			if (remoteId != localId)
				throw new IllegalStateException
		]
		ids.drop(localIds.length).forEach [ id |
			debug('''Pulling commit «id»''')
			val commit = remoteRepo.pullCommit(id, branch.name)
			addCommit(commit, remoteBranch)
			if (toLocal)
				addCommit(commit, branch)
		]
	}

}
