package tools.vitruv.framework.versioning.emfstore

import java.util.List
import java.util.Set

import org.eclipse.xtext.xbase.lib.Functions.Function1

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.versioning.author.Author
import tools.vitruv.framework.versioning.branch.Branch
import tools.vitruv.framework.versioning.branch.LocalBranch
import tools.vitruv.framework.versioning.branch.RemoteBranch
import tools.vitruv.framework.versioning.common.commit.MergeCommit
import tools.vitruv.framework.versioning.common.commit.SimpleCommit
import tools.vitruv.framework.vsum.VersioningVirtualModel

interface LocalRepository<T> extends AbstractRepository {
	def Author getAuthor()

	def List<RemoteBranch<T>> getRemoteBranches()

	def LocalBranch<T> createLocalBranch(String name)

	def LocalBranch<T> getCurrentBranch()

	def PushState push()

	def PushState push(LocalBranch<T> localBranch)

	def RemoteBranch<T> createRemoteBranch(String name, T remoteRepository)

	def Set<LocalBranch<T>> getLocalBranches()

	def SimpleCommit commit(String s)

	def SimpleCommit commit(String s, VURI vuri)

	def SimpleCommit commit(String s, VersioningVirtualModel virtualModel)

	def SimpleCommit commit(String s, VersioningVirtualModel virtualModel, VURI vuri)

	def T getRemoteProject()

	def VersioningVirtualModel getVirtualModel()

	def void addOrigin(LocalBranch<T> branch, T remoteRepository)

	def void addRemoteRepository(T remoteRepository)

	def void checkout()

	def void checkout(VURI vuri)

	def void checkout(VersioningVirtualModel virtualModel)

	def void checkout(VersioningVirtualModel virtualModel, VURI vuri)

	def void createBranchOnServer(String name, T remoteRemote)

	def void pull()

	def void pull(LocalBranch<T> branch)

	def void setAllFlag(boolean allFlag)

	def void setAuthor(Author author)

	def void setCurrentBranch(LocalBranch<T> branch)

	def void setRemoteProject(T remote)

	def void setVirtualModel(VersioningVirtualModel versioningVirtualModel)

	def void addIDPair(Pair<String, String> pair)

	def MergeCommit merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback,
		VersioningVirtualModel virtualModel
	)

	def MergeCommit merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback,
		VersioningVirtualModel sourceVirtualModel,
		VersioningVirtualModel targetVirtualModel
	)

	def MergeCommit merge(
		Branch source,
		Branch target,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> triggeredCallback
	)
}
