package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorAllElementTypesToAllElementTypes extends AbstractReactionsExecutor {
  public ExecutorAllElementTypesToAllElementTypes(final UserInteracting userInteracting) {
    super(userInteracting,
    	new AllElementTypesDomainProvider().getDomain(), 
    	new AllElementTypesDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.CreateRootTestReaction.getExpectedChangeType(), new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.CreateRootTestReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.DeleteRootTestReaction.getExpectedChangeType(), new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.DeleteRootTestReaction(userInteracting));
  }
}
