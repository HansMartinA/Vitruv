package tools.vitruv.framework.metamodel;

import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import tools.vitruv.framework.util.command.VitruviusRecordingCommand;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;

public interface ModelRepository {
	void persistRootElement(VURI persistenceVuri, EObject rootElement);
	ModelInstance getModel(VURI modelVuri);
	void forceReloadModelIfExisting(VURI modelVuri);
    void saveAllModels();
    
    void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable);
    void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command);
    
    ResourceSet getResourceSet();
}
