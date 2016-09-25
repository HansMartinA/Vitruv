package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReinitializeOperationRequiredRoleEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private ReinitializeOperationRequiredRoleEffect.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void callRoutine1(final OperationRequiredRole requiredRole, @Extension final RoutinesFacade _routinesFacade) {
      InterfaceRequiringEntity _requiringEntity_RequiredRole = requiredRole.getRequiringEntity_RequiredRole();
      _routinesFacade.removeRequiredRole(requiredRole, _requiringEntity_RequiredRole);
      _routinesFacade.addRequiredRole(requiredRole);
    }
  }
  
  public ReinitializeOperationRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole requiredRole) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.ReinitializeOperationRequiredRoleEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.requiredRole = requiredRole;
  }
  
  private OperationRequiredRole requiredRole;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReinitializeOperationRequiredRoleEffect with input:");
    getLogger().debug("   OperationRequiredRole: " + this.requiredRole);
    
    userExecution.callRoutine1(requiredRole, effectFacade);
    
    postprocessElementStates();
  }
}
