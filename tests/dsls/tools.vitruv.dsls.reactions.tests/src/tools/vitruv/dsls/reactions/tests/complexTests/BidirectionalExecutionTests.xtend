package tools.vitruv.dsls.reactions.tests.complexTests

import allElementTypes.Root
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

import static org.hamcrest.CoreMatchers.*
import static tools.vitruv.testutils.domains.AllElementTypesCreators.*
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.containsModelOf
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject

class BidirectionalExecutionTests extends AbstractAllElementTypesReactionsTests {
	static val SOURCE_MODEL = getProjectModelPath("EachTestModelSource");
	static val TARGET_MODEL = getProjectModelPath("EachTestModelTarget");

	String[] nonContainmentNonRootIds = #["NonRootHelper0", "NonRootHelper1", "NonRootHelper2"];

	@BeforeEach
	def setup() {
		createAndSynchronizeModel(SOURCE_MODEL, newRoot => [
			id = 'EachTestModelSource'
			nonRootObjectContainerHelper = newNonRootObjectContainerHelper => [
				id = 'NonRootObjectContainer'
				nonRootObjectsContainment += nonContainmentNonRootIds.map [ nonRootId |
					newNonRoot => [
						id = nonRootId
					]
				]
			]
		])

		assertThat(resourceAt(SOURCE_MODEL), containsModelOf(resourceAt(TARGET_MODEL)))
	}

	private def VitruviusChange getSourceModelChanges(PropagatedChange propagatedChange) {
		return (propagatedChange.consequentialChanges as CompositeContainerChange).changes.findFirst [
			URI.toString.endsWith(SOURCE_MODEL)
		]
	}

	@Test
	def void testBasicBidirectionalApplication() {
		val propagatedChanges = saveAndSynchronizeChanges(Root.from(TARGET_MODEL).record [
			singleValuedContainmentEReference = newNonRoot => [
				id = 'bidirectionalId'
			]
		])

		assertThat(propagatedChanges.size, is(1))
		val consequentialSourceModelChange = propagatedChanges.get(0).sourceModelChanges
		assertThat(consequentialSourceModelChange.EChanges.get(0), is(instanceOf(CreateEObject)))
		assertThat(consequentialSourceModelChange.EChanges.get(1), is(instanceOf(ReplaceSingleValuedEReference)))
		assertThat(resourceAt(SOURCE_MODEL), containsModelOf(resourceAt(TARGET_MODEL)))
		assertThat(Root.from(SOURCE_MODEL).singleValuedContainmentEReference.id, is('bidirectionalId'))
		assertThat(Root.from(TARGET_MODEL).singleValuedContainmentEReference.id, is('bidirectionalId'));
	}

	/** Regression test for #175:
	 *  Removing an object from its container for which the UUID is not cached in the local UUID resolver,
	 *  the UUID resolution failed: Local and global resolution both failed, because the object
	 *  has a changed URI (due to removal from container).
	 */
	@Test
	def void testApplyRemoveInOtherModel() {
		val propagatedChanges = saveAndSynchronizeChanges(Root.from(TARGET_MODEL).record [
			nonRootObjectContainerHelper.nonRootObjectsContainment.remove(0)
		])

		assertThat(propagatedChanges.size, is(1))
		val consequentialSourceModelChange = propagatedChanges.get(0).sourceModelChanges
		assertThat(consequentialSourceModelChange.EChanges.get(0), is(instanceOf(RemoveEReference)))
		assertThat(consequentialSourceModelChange.EChanges.get(1), is(instanceOf(DeleteEObject)))
		assertThat(resourceAt(SOURCE_MODEL), containsModelOf(resourceAt(TARGET_MODEL)))
		assertThat(Root.from(SOURCE_MODEL).nonRootObjectContainerHelper.nonRootObjectsContainment.size, is(2))
		assertThat(Root.from(TARGET_MODEL).nonRootObjectContainerHelper.nonRootObjectsContainment.size, is(2))
	}

	/** Regression test for #175:
	 *  Removing a root object from a resource for which the UUID is not cached in the local UUID resolver,
	 *  the UUID resolution failed: Local and global resolution both failed, because the object
	 *  has a changed URI (due to removal from container).
	 */
	@Test
	def void testApplyRemoveRootInOtherModel() {
		val propagatedChanges = deleteAndSynchronizeModel(TARGET_MODEL)

		assertThat(propagatedChanges.size, is(1))
		val consequentialSourceModelChange = propagatedChanges.get(0).sourceModelChanges
		assertThat(consequentialSourceModelChange.EChanges.get(0), is(instanceOf(RemoveRootEObject)))
		assertThat(consequentialSourceModelChange.EChanges.get(1), is(instanceOf(DeleteEObject)))
		assertThat(resourceAt(SOURCE_MODEL), doesNotExist())
		assertThat(resourceAt(TARGET_MODEL), doesNotExist())
	}
}
