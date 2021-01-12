package tools.vitruv.dsls.commonalities.ui.quickfix

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tools.vitruv.dsls.common.ui.validation.ProjectValidation
import static tools.vitruv.dsls.commonalities.util.CommonalitiesLanguageConstants.RUNTIME_BUNDLE
import static extension tools.vitruv.dsls.commonalities.testutils.CommonalitiesProjectSetup.*
import org.junit.jupiter.api.^extension.ExtendWith
import static extension tools.vitruv.dsls.common.ui.ProjectAccess.*
import static org.hamcrest.CoreMatchers.is
import org.hamcrest.MatcherAssert
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider
import tools.vitruv.testutils.TestProjectManager
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Path
import tools.vitruv.testutils.TestProject
import static edu.kit.ipd.sdq.commons.util.org.eclipse.core.resources.IProjectUtil.*
import java.util.Set
import tools.vitruv.dsls.commonalities.testutils.BugFixedAbstractQuickfixTest

@DisplayName("quick fixes for missing bundles")
@ExtendWith(#[InjectionExtension, TestProjectManager])
@InjectWith(CommonalitiesLanguageUiInjectorProvider)
class MissingBundlesQuickfixTest extends BugFixedAbstractQuickfixTest {
	var Path projectLocation

	@BeforeEach
	def void captureProjectLocation(@TestProject Path projectLocation) {
		this.projectLocation = projectLocation
	}

	@Test
	@DisplayName("fixes a missing runtime bundle dependency")
	def void fixMissingRuntimeBundle() {
		val testProject = setupProject() => [
			pluginProject => [
				removeRequiredBundle(RUNTIME_BUNDLE)
				apply(new NullProgressMonitor)
			]
		]

		val testCommonality = '''
			concept test
			
			commonality Test {}
		'''

		testQuickfixesOn(
			testCommonality,
			ProjectValidation.ErrorCodes.BUNDLE_MISSING_ON_CLASSPATH,
			new Quickfix('''Add dependency on ‹«RUNTIME_BUNDLE»›''', null, testCommonality)
		)

		val requiredBundles = (testProject.pluginProject.requiredBundles.toList() ?: emptyList()).map[name].toSet()
		MatcherAssert.assertThat(requiredBundles, is(Set.of(RUNTIME_BUNDLE)))
	}

	@Test
	@DisplayName("fixes a missing domain bundle dependency")
	def void fixMissingDomainBundle() {
		val testProject = setupProject()
		val missingBundle = "tools.vitruv.testutils.domains"

		val testCommonality = '''
			concept test
			
			commonality Test {
				with AllElementTypes:Root
			}
		'''

		testQuickfixesOn(
			testCommonality,
			ProjectValidation.ErrorCodes.BUNDLE_MISSING_ON_CLASSPATH,
			new Quickfix( '''Add dependency on ‹«missingBundle»›''', null, testCommonality)
		)

		val requiredBundles = (testProject.pluginProject.requiredBundles.toList() ?: emptyList()).map[name].toSet()
		MatcherAssert.assertThat(requiredBundles, is(Set.of(RUNTIME_BUNDLE, missingBundle)))
	}

	def private setupProject() {
		createProjectAt(projectName, projectLocation).setupAsCommonalitiesProject()
	}

	override getProjectName() {
		projectLocation.fileName.toString
	}
}