package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.Iterables;

import allElementTypes.Root;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.correspondence.Correspondence;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.propagation.ResourceAccess;
import tools.vitruv.framework.propagation.impl.AbstractChangePropagationSpecification;
import tools.vitruv.framework.vsum.internal.InternalVirtualModel;
import tools.vitruv.framework.vsum.internal.ModelInstance;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.AllElementTypesDomain;
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.testutils.matchers.ModelMatchers;
import tools.vitruv.testutils.metamodels.AllElementTypesCreators;
import tools.vitruv.variability.vave.Vave;
import tools.vitruv.variability.vave.VirtualModelProduct;
import tools.vitruv.variability.vave.impl.VaveImpl;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.VavemodelFactory;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveTest {

	public static class RedundancyChangePropagationSpecification extends AbstractChangePropagationSpecification {
		public static URI getTargetResourceUri(final URI sourceUri) {
			String _fileString = sourceUri.trimFileExtension().toFileString();
			String _plus = (_fileString + "Copy.");
			String _fileExtension = sourceUri.fileExtension();
			String _plus_1 = (_plus + _fileExtension);
			return URI.createFileURI(_plus_1);
		}

		public RedundancyChangePropagationSpecification(final VitruvDomain sourceDomain,
				final VitruvDomain targetDomain) {
			super(sourceDomain, targetDomain);
		}

		@Override
		public boolean doesHandleChange(final EChange change, final CorrespondenceModel correspondenceModel) {
			if ((change instanceof InsertRootEObject)) {
				EObject _newValue = ((InsertRootEObject) change).getNewValue();
				return (_newValue instanceof Root);
			}
			return false;
		}

		@Override
		public void propagateChange(final EChange change, final CorrespondenceModel correspondenceModel,
				@Extension final ResourceAccess resourceAccess) {
			boolean _doesHandleChange = this.doesHandleChange(change, correspondenceModel);
			boolean _not = (!_doesHandleChange);
			if (_not) {
				return;
			}
			final InsertRootEObject<Root> typedChange = ((InsertRootEObject<Root>) change);
			final Root insertedRoot = typedChange.getNewValue();
			final Iterable<Root> correspondingRoots = Iterables.<Root>filter(
					CorrespondenceModelUtil.<Correspondence>getCorrespondingEObjects(correspondenceModel, insertedRoot),
					Root.class);
			Root _xifexpression = null;
			int _size = IterableExtensions.size(correspondingRoots);
			boolean _equals = (_size == 1);
			if (_equals) {
				_xifexpression = ((Root[]) Conversions.unwrapArray(correspondingRoots, Root.class))[0];
			} else {
				Root _xblockexpression = null;
				{
					Root _Root = AllElementTypesCreators.aet.Root();
					final Procedure1<Root> _function = (Root it) -> {
						it.setId(insertedRoot.getId());
					};
					final Root newRoot = ObjectExtensions.<Root>operator_doubleArrow(_Root, _function);
					correspondenceModel.createAndAddCorrespondence(List.<EObject>of(insertedRoot),
							List.<EObject>of(newRoot));
					_xblockexpression = newRoot;
				}
				_xifexpression = _xblockexpression;
			}
			final Root correspondingRoot = _xifexpression;
			EObject _eContainer = insertedRoot.eContainer();
			boolean _tripleNotEquals = (_eContainer != null);
			if (_tripleNotEquals) {
				final Set<Root> correspondingObjects = CorrespondenceModelUtil
						.<Root, Correspondence>getCorrespondingEObjects(correspondenceModel, insertedRoot.eContainer(),
								Root.class);
				Assertions.assertEquals(1, correspondingObjects.size());
				Root _get = ((Root[]) Conversions.unwrapArray(correspondingObjects, Root.class))[0];
				_get.setRecursiveRoot(correspondingRoot);
			}
			final URI resourceURI = typedChange.getResource().getURI();
			resourceAccess.persistAsRoot(correspondingRoot,
					RedundancyChangePropagationSpecification.getTargetResourceUri(resourceURI));
		}
	}

	private static final String MODEL_PATH = "models";
	private static final String VAVE_MODEL = MODEL_PATH + "/" + "vaveTest.vavemodel";

	private URI createTestModelResourceUri(final String suffix, Path projectFolder) {
		return URI.createFileURI(projectFolder.resolve((("root" + suffix) + ".allElementTypes")).toString());
	}

	private Path projectFolder;

	@BeforeEach
	public void initializeProjectFolder(@TestProject final Path projectFolder) {
		this.projectFolder = projectFolder;
	}

	@Test
	public void testVSUMCreation() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		Vave vave = new VaveImpl(domains, new HashSet<>());
		VirtualModelProduct vsum = vave.externalizeProduct(this.projectFolder, "");
		assertNotNull(vsum);
	}

	@Test
	public void testVSUMPropagation() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new AllElementTypesDomainProvider().getDomain());
		Vave vave = new VaveImpl(domains, new HashSet<>());
		final VirtualModelProduct virtualModel = vave.externalizeProduct(this.projectFolder.resolve("vsum"), "");

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", this.projectFolder));
		monitoredResource.getContents().add(AllElementTypesCreators.aet.Root());
		AllElementTypesCreators.aet.Root().setId("root");
		
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		virtualModel.propagateChange(recordedChange);
		final ModelInstance vsumModel = virtualModel
				.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));

		vave.internalizeChanges(virtualModel);
	}

	@Test
	public void testVSUMPropagationAndConsistency() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(
				aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		Vave vave = new VaveImpl(domains, changePropagationSpecifications);

		final VirtualModelProduct virtualModel = vave.externalizeProduct(this.projectFolder.resolve("vsum"), "");

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		
		Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", this.projectFolder));
		monitoredResource.getContents().add(AllElementTypesCreators.aet.Root());
		AllElementTypesCreators.aet.Root().setId("root");
		
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		
		virtualModel.propagateChange(recordedChange);
		
		final ModelInstance sourceModel = virtualModel
				.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		final ModelInstance targetModel = virtualModel.getModelInstance(RedundancyChangePropagationSpecification
				.getTargetResourceUri(this.createTestModelResourceUri("", this.projectFolder)));
		
		MatcherAssert.<Resource>assertThat(targetModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));
		Assertions.assertEquals(1,
				CorrespondenceModelUtil.<Correspondence>getCorrespondingEObjects(virtualModel.getCorrespondenceModel(),
						sourceModel.getResource().getContents().get(0)).size());
	}

	@Test
	public void testDeltaApplication() throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		domains.add(new AllElementTypesDomainProvider().getDomain());
		Vave vave = new VaveImpl(domains, new HashSet<>());
		final VirtualModelProduct virtualModel = vave.externalizeProduct(this.projectFolder.resolve("vsum"), ""); // empty product
																													

		final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
		changeRecorder.addToRecording(resourceSet);
		changeRecorder.beginRecording();
		Resource _createResource = resourceSet.createResource(this.createTestModelResourceUri("", this.projectFolder));
		final Procedure1<Resource> _function = (Resource it) -> {
			EList<EObject> _contents = it.getContents();
			Root _Root = AllElementTypesCreators.aet.Root();
			final Procedure1<Root> _function_1 = (Root it_1) -> {
				it_1.setId("root");
			};
			Root _doubleArrow = ObjectExtensions.<Root>operator_doubleArrow(_Root, _function_1);
			_contents.add(_doubleArrow);
		};
		final Resource monitoredResource = ObjectExtensions.<Resource>operator_doubleArrow(_createResource, _function);
		final TransactionalChange recordedChange = changeRecorder.endRecording();
		virtualModel.propagateChange(recordedChange);
		final ModelInstance vsumModel = virtualModel
				.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel.getResource(), ModelMatchers.containsModelOf(monitoredResource));

		vave.internalizeChanges(virtualModel);
		final VirtualModelProduct virtualModel2 = vave.externalizeProduct(this.projectFolder.resolve("vsum2"), "");

		final ModelInstance vsumModel2 = virtualModel2
				.getModelInstance(this.createTestModelResourceUri("", this.projectFolder));
		MatcherAssert.<Resource>assertThat(vsumModel2.getResource(), ModelMatchers.containsModelOf(monitoredResource));
	}

	@Test // Test wrt. problem space
	public void testVaveModelCreation() {
		// create tree content of simple vave model instance
		vavemodel.System system = VavemodelFactory.eINSTANCE.createSystem();
		vavemodel.Feature car = VavemodelFactory.eINSTANCE.createFeature();
		vavemodel.Feature engineType = VavemodelFactory.eINSTANCE.createFeature();
		vavemodel.Feature gasoline = VavemodelFactory.eINSTANCE.createFeature();
		vavemodel.Feature electric = VavemodelFactory.eINSTANCE.createFeature();
		vavemodel.Feature smogControl = VavemodelFactory.eINSTANCE.createFeature();
		car.setName("car");
		engineType.setName("engineType");
		gasoline.setName("gasoline");
		electric.setName("electric");
		smogControl.setName("smogControl");
		system.getFeature().add(car);
		FeatureRevision car_1 = VavemodelFactory.eINSTANCE.createFeatureRevision();
		car_1.setRevisionID(1);
		car.getFeaturerevision().add(car_1);
		FeatureRevision gasoline_1 = VavemodelFactory.eINSTANCE.createFeatureRevision();
		gasoline_1.setRevisionID(1);
		gasoline.getFeaturerevision().add(gasoline_1);
		FeatureRevision electric_1 = VavemodelFactory.eINSTANCE.createFeatureRevision();
		electric_1.setRevisionID(1);
		electric.getFeaturerevision().add(electric_1);
		FeatureRevision smogControl_1 = VavemodelFactory.eINSTANCE.createFeatureRevision();
		smogControl_1.setRevisionID(1);
		smogControl.getFeaturerevision().add(smogControl_1);
		vavemodel.TreeConstraint treeconstr1 = VavemodelFactory.eINSTANCE.createTreeConstraint();
		treeconstr1.setType(vavemodel.GroupType.XOR);
		// Make Engine Type mandatory by adding a tree constraint of type XOR to its
		// parent feature Car
		addContainment(car, treeconstr1, "treeconstraint");
		addContainment(treeconstr1, engineType, "feature");
		vavemodel.TreeConstraint treeconstr2 = VavemodelFactory.eINSTANCE.createTreeConstraint();
		treeconstr2.setType(vavemodel.GroupType.ORNONE);
		addContainment(car, treeconstr2, "treeconstraint");
		addContainment(treeconstr2, smogControl, "feature");
		vavemodel.TreeConstraint treeconstr3 = VavemodelFactory.eINSTANCE.createTreeConstraint();
		treeconstr3.setType(vavemodel.GroupType.OR);
		addContainment(engineType, treeconstr3, "treeconstraint");
		addContainment(treeconstr3, gasoline, "feature");
		addContainment(treeconstr3, electric, "feature");
		// create cross-tree constraint implication: gasoline implies smog control
		vavemodel.CrossTreeConstraint crosstreeconstr1 = VavemodelFactory.eINSTANCE.createCrossTreeConstraint();
		vavemodel.Implication<FeatureOption> implication1 = VavemodelFactory.eINSTANCE.createImplication();
		vavemodel.Variable<FeatureOption> variable1 = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variable2 = VavemodelFactory.eINSTANCE.createVariable();
		crosstreeconstr1.setExpression(implication1);
		implication1.getTerm().add(variable1);
		implication1.getTerm().add(variable2);
		variable1.setOption(gasoline);
		variable2.setOption(smogControl);
		system.getConstraint().add(crosstreeconstr1);

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("vave", new XMIResourceFactoryImpl());
		ResourceSet resSet = new ResourceSetImpl();
		Resource resource = resSet
				.createResource(URI.createFileURI(this.projectFolder.resolve("models/car.vave").toString()));
		resource.getContents().add(system);
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addContainment(EObject container, EObject containment, String structFeature) {
		EStructuralFeature eStructFeature = container.eClass().getEStructuralFeature(structFeature);
		List<EObject> features = (List<EObject>) container.eGet(eStructFeature);
		features.add(containment);
	}

}