package mir.effects.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.effects.simpleChangesTests.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class HelperResponseForNonRootObjectContainerContentsInitializationEffect extends AbstractEffectRealization {
  public HelperResponseForNonRootObjectContainerContentsInitializationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<NonRoot> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<NonRoot> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceNewNonRoot(final CreateNonRootEObjectInList<NonRoot> change) {
    NonRoot _newValue = change.getNewValue();
    return _newValue;
  }
  
  private EObject getCorrepondenceSourceNonRootContainer(final CreateNonRootEObjectInList<NonRoot> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect HelperResponseForNonRootObjectContainerContentsInitializationEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    NonRootObjectContainerHelper nonRootContainer = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceNonRootContainer(change), // correspondence source supplier
    	(NonRootObjectContainerHelper _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	NonRootObjectContainerHelper.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    NonRoot newNonRoot = initializeCreateElementState(
    	() -> getCorrepondenceSourceNewNonRoot(change), // correspondence source supplier
    	() -> AllElementTypesFactoryImpl.eINSTANCE.createNonRoot(), // element creation supplier
    	() -> null, // tag supplier
    	NonRoot.class);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.simpleChangesTests.HelperResponseForNonRootObjectContainerContentsInitializationEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, nonRootContainer, newNonRoot);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<NonRoot> change, final NonRootObjectContainerHelper nonRootContainer, final NonRoot newNonRoot) {
      NonRoot _newValue = change.getNewValue();
      String _id = _newValue.getId();
      newNonRoot.setId(_id);
      EList<NonRoot> _nonRootObjectsContainment = nonRootContainer.getNonRootObjectsContainment();
      _nonRootObjectsContainment.add(newNonRoot);
    }
  }
}
