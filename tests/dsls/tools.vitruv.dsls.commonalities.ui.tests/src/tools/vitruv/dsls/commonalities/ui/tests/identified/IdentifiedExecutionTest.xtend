package tools.vitruv.dsls.commonalities.ui.tests.identified

import org.eclipse.emf.ecore.util.EcoreUtil
import pcm_mockup.Repository
import tools.vitruv.dsls.commonalities.testutils.CommonalitiesExecutionTest
import uml_mockup.UPackage

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler
import org.junit.jupiter.api.Test
import allElementTypes.Root
import allElementTypes2.Root2
import static tools.vitruv.testutils.domains.AllElementTypesCreators.newRoot
import static tools.vitruv.testutils.domains.AllElementTypes2Creators.newRoot2
import static tools.vitruv.testutils.domains.PcmMockupCreators.newRepository
import static tools.vitruv.testutils.domains.UmlMockupCreators.newUMLPackage
import static tools.vitruv.testutils.domains.AllElementTypes2Creators.newNonRoot2
import static tools.vitruv.testutils.domains.AllElementTypesCreators.newNonRoot
import static tools.vitruv.testutils.domains.PcmMockupCreators.newComponent
import static tools.vitruv.testutils.domains.UmlMockupCreators.newUMLClass
import static extension tools.vitruv.testutils.domains.AllElementTypes2Creators.allElementTypes2
import static extension tools.vitruv.testutils.domains.AllElementTypesCreators.allElementTypes
import static extension tools.vitruv.testutils.domains.PcmMockupCreators.pcm_mockup
import static extension tools.vitruv.testutils.domains.UmlMockupCreators.uml_mockup

class IdentifiedExecutionTest extends CommonalitiesExecutionTest {
	override createCompiler(ExecutionTestCompiler.Factory factory) {
		factory.createCompiler [
			projectName = 'commonalities-test-identified'
			commonalities = #['Identified.commonality', 'Sub.commonality']
			domainDependencies = #[
				'tools.vitruv.testutils.domains',
				'tools.vitruv.testutils.metamodels'
			]
		]
	}

	@Test
	def void rootInsert() {
		createAndSynchronizeModel('testid'.allElementTypes2, newRoot2 => [id2 = 'testid'])

		assertThat(resourceAt('testid'.allElementTypes2), contains(newRoot2 => [id2 = 'testid']))
		assertThat(resourceAt('testid'.allElementTypes), contains(newRoot => [id = 'testid']))
		assertThat(resourceAt('testid'.pcm_mockup), contains(newRepository => [id = 'testid']))
		assertThat(resourceAt('testid'.uml_mockup), contains(newUMLPackage => [id = 'testid']))
	}

	@Test
	def void multiRootInsert() {
		createAndSynchronizeModel('first'.allElementTypes2, newRoot2 => [id2 = 'first'])
		createAndSynchronizeModel('second'.allElementTypes2, newRoot2 => [id2 = 'second'])
		createAndSynchronizeModel('third'.allElementTypes2, newRoot2 => [id2 = 'third'])

		assertThat(resourceAt('first'.allElementTypes2), contains(newRoot2 => [id2 = 'first']))
		assertThat(resourceAt('first'.allElementTypes), contains(newRoot => [id = 'first']))
		assertThat(resourceAt('first'.pcm_mockup), contains(newRepository => [id = 'first']))
		assertThat(resourceAt('first'.uml_mockup), contains(newUMLPackage => [id = 'first']))

		assertThat(resourceAt('second'.allElementTypes2), contains(newRoot2 => [id2 = 'second']))
		assertThat(resourceAt('second'.allElementTypes), contains(newRoot => [id = 'second']))
		assertThat(resourceAt('second'.pcm_mockup), contains(newRepository => [id = 'second']))
		assertThat(resourceAt('second'.uml_mockup), contains(newUMLPackage => [id = 'second']))

		assertThat(resourceAt('third'.allElementTypes2), contains(newRoot2 => [id2 = 'third']))
		assertThat(resourceAt('third'.allElementTypes), contains(newRoot => [id = 'third']))
		assertThat(resourceAt('third'.pcm_mockup), contains(newRepository => [id = 'third']))
		assertThat(resourceAt('third'.uml_mockup), contains(newUMLPackage => [id = 'third']))
	}

	@Test
	def void rootDelete() {
		createAndSynchronizeModel('first'.allElementTypes2, newRoot2 => [id2 = 'first'])
		createAndSynchronizeModel('second'.allElementTypes2, newRoot2 => [id2 = 'second'])
		createAndSynchronizeModel('third'.allElementTypes2, newRoot2 => [id2 = 'third'])

		EcoreUtil.delete(Root.from('second'.allElementTypes))
		resourceAt('second'.allElementTypes).delete(null);
		assertThat(resourceAt('second'.allElementTypes), doesNotExist)
	// TODO Extend assertions
	}

	@Test
	def void setIdAttribute() {
		createAndSynchronizeModel('startid'.allElementTypes2, newRoot2 => [id2 = 'startid'])

		saveAndSynchronizeChanges(Root2.from('startid'.allElementTypes2).record[id2 = '1st id'])
		assertThat(resourceAt('startid'.allElementTypes2), contains(newRoot2 => [id2 = '1st id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(newRoot => [id = '1st id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(newRepository => [id = '1st id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(newUMLPackage => [id = '1st id']))

		saveAndSynchronizeChanges(Root.from('startid'.allElementTypes).record[id = '2nd id'])
		assertThat(resourceAt('startid'.allElementTypes2), contains(newRoot2 => [id2 = '2nd id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(newRoot => [id = '2nd id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(newRepository => [id = '2nd id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(newUMLPackage => [id = '2nd id']))

		saveAndSynchronizeChanges(Repository.from('startid'.pcm_mockup).record[id = '3th id'])
		assertThat(resourceAt('startid'.allElementTypes2), contains(newRoot2 => [id2 = '3th id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(newRoot => [id = '3th id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(newRepository => [id = '3th id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(newUMLPackage => [id = '3th id']))

		saveAndSynchronizeChanges(UPackage.from('startid'.uml_mockup).record[id = '4th id'])
		assertThat(resourceAt('startid'.allElementTypes2), contains(newRoot2 => [id2 = '4th id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(newRoot => [id = '4th id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(newRepository => [id = '4th id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(newUMLPackage => [id = '4th id']))
	}

	@Test
	def void setSimpleAttribute() {
		createAndSynchronizeModel('test'.allElementTypes2, newRoot2 => [
			singleValuedEAttribute2 = 0
			id2 = 'test'
		])

		saveAndSynchronizeChanges(Root2.from('test'.allElementTypes2).record[singleValuedEAttribute2 = 1])
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			singleValuedEAttribute2 = 1
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			singleValuedEAttribute = 1
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root.from('test'.allElementTypes).record[singleValuedEAttribute = 2])
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			singleValuedEAttribute2 = 2
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			singleValuedEAttribute = 2
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root2.from('test'.allElementTypes2).record[singleValuedEAttribute2 = 3])
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			singleValuedEAttribute2 = 3
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			singleValuedEAttribute = 3
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root.from('test'.allElementTypes).record[singleValuedEAttribute = 4])
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			singleValuedEAttribute2 = 4
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			singleValuedEAttribute = 4
			id = 'test'
		]))
	}

	@Test
	def void setMultiValue() {
		createAndSynchronizeModel('test'.allElementTypes2, newRoot2 => [
			multiValuedEAttribute2 += #[1, 2, 3]
			id2 = 'test'
		])
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			multiValuedEAttribute2 += #[1, 2, 3]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			multiValuedEAttribute += #[1, 2, 3]
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root2.from('test'.allElementTypes2).record[multiValuedEAttribute2 += 4])
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			multiValuedEAttribute2 += #[1, 2, 3, 4]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			multiValuedEAttribute += #[1, 2, 3, 4]
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root.from('test'.allElementTypes).record[multiValuedEAttribute += 5])
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			multiValuedEAttribute2 += #[1, 2, 3, 4, 5]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			multiValuedEAttribute += #[1, 2, 3, 4, 5]
			id = 'test'
		]))

	/*		Vitruvius doesn’t correctly translate the changes?
	 * 		saveAndSynchronizeChanges(newRoot2.from('test'.allElementTypes2) => [multiValuedEAttribute2 -= #[1, 3, 5]])
	 * 		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
	 * 			multiValuedEAttribute2 += #[2, 4]
	 * 			id2 = 'test'
	 * 		]))
	 * 		assertThat(resourceAt('test'.allElementTypes), contains(root => [
	 * 			multiValuedEAttribute += #[2, 4]
	 * 			id = 'test'
	 * 		]))

	 * 		saveAndSynchronizeChanges(Root.from('test'.allElementTypes) => [multiValuedEAttribute -= #[2]])
	 * 		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
	 * 			multiValuedEAttribute2 += 4
	 * 			id2 = 'test'
	 * 		]))
	 * 		assertThat(resourceAt('test'.allElementTypes), contains(root => [
	 * 			multiValuedEAttribute += 4
	 * 			id = 'test'
	 ]))*/
	}

	@Test
	def void rootWithReferenceInsert() {
		createAndSynchronizeModel('testid'.allElementTypes2, newRoot2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += newNonRoot2 => [id2 = 'testname']
		])

		assertThat(resourceAt('testid'.allElementTypes2), contains(newRoot2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += newNonRoot2 => [id2 = 'testname']
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(newRoot => [
			id = 'testid'
			multiValuedContainmentEReference += newNonRoot => [id = 'testname']
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(newRepository => [
			id = 'testid'
			components += newComponent => [name = 'testname']
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(newUMLPackage => [
			id = 'testid'
			classes += newUMLClass => [name = 'testname']
		], ignoringFeatures('id')))
	}

	@Test
	def void multiReferenceInsert() {
		createAndSynchronizeModel('testid'.allElementTypes2, newRoot2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				newNonRoot2 => [id2 = 'first'],
				newNonRoot2 => [id2 = 'second']
			]
		])
		assertThat(resourceAt('testid'.allElementTypes2), contains(newRoot2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				newNonRoot2 => [id2 = 'first'],
				newNonRoot2 => [id2 = 'second']
			]
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(newRoot => [
			id = 'testid'
			multiValuedContainmentEReference += #[
				newNonRoot => [id = 'first'],
				newNonRoot => [id = 'second']
			]
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(newRepository => [
			id = 'testid'
			components += #[
				newComponent => [name = 'first'],
				newComponent => [name = 'second']
			]
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(newUMLPackage => [
			id = 'testid'
			classes += #[
				newUMLClass => [name = 'first'],
				newUMLClass => [name = 'second']
			]
		], ignoringFeatures('id')))

		saveAndSynchronizeChanges(Repository.from('testid'.pcm_mockup).record [
			components += newComponent => [name = 'third']
		])
		assertThat(resourceAt('testid'.allElementTypes2), contains(newRoot2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				newNonRoot2 => [id2 = 'first'],
				newNonRoot2 => [id2 = 'second'],
				newNonRoot2 => [id2 = 'third']
			]
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(newRoot => [
			id = 'testid'
			multiValuedContainmentEReference += #[
				newNonRoot => [id = 'first'],
				newNonRoot => [id = 'second'],
				newNonRoot => [id = 'third']
			]
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(newRepository => [
			id = 'testid'
			components += #[
				newComponent => [name = 'first'],
				newComponent => [name = 'second'],
				newComponent => [name = 'third']
			]
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(newUMLPackage => [
			id = 'testid'
			classes += #[
				newUMLClass => [name = 'first'],
				newUMLClass => [name = 'second'],
				newUMLClass => [name = 'third']
			]
		], ignoringFeatures('id')))
	}
}
