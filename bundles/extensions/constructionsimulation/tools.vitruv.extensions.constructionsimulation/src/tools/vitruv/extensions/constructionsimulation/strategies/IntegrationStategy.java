package tools.vitruv.extensions.constructionsimulation.strategies;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.vsum.VirtualModel;
import tools.vitruv.framework.change.description.VitruviusChange;

/**
 * Abstract base class for all integration strategies. does not use GoF strategy pattern.
 *
 * @author Johannes Hoor
 *
 */
public abstract class IntegrationStategy {
    protected Logger logger;
    private Resource model = null;

    public Resource getModel() {
        return model;
    }

    /**
     * Instantiates a new integration stategy.
     */
    public IntegrationStategy() {
        this.logger = LogManager.getRootLogger();
    }

    /**
     * Three step process: 1) load 2)check and enforce invariants 3)create changes that Vitruvius
     * can use.
     *
     * @param resource
     *            the resource
     * @param sync
     *            the sync
     * @return List of changes
     */
    public List<VitruviusChange> integrateModel(final IResource resource, final VirtualModel vmodel) {
        this.logger.info("Start loading Model " + resource.getName());
        model = this.loadModel(resource.getLocation().toString());
        this.logger.info("Invariant Check/Enforce for Model " + resource.getName());
        model = this.checkAndEnforceInvariants(model);
        this.logger.info("Traverse Model and create Change-Models for Vitruvius Integration/Synchronization "
                + resource.getName());
        final List<VitruviusChange> changes = this.createChangeModels(resource, model);
        this.propagateChanges(changes, vmodel);
        this.logger.info("Finish integration for model: " + model.toString() + "@" + model.getTimeStamp());
        return changes;
    }

    /**
     * Propagate changes.
     *
     * @param changes
     *            the changes
     * @param sync
     *            the sync
     */
    private void propagateChanges(final List<VitruviusChange> changes, final VirtualModel vmodel) {

        try {
        	VitruviusChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
            vmodel.propagateChange(compositeChange);
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Load model.
     *
     * @param path
     *            the path
     * @return the resource
     */
    protected abstract Resource loadModel(String path);

    /**
     * Check and enforce invariants.
     *
     * @param model
     *            the model
     * @return the resource
     */
    protected abstract Resource checkAndEnforceInvariants(Resource model);

    /**
     * Creates the change models.
     *
     * @param resource
     *            the resource
     * @param validModel
     *            the valid model
     * @return the e list
     */
    protected abstract List<VitruviusChange> createChangeModels(IResource resource, Resource validModel);

}
