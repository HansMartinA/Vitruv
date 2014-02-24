package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.CreateNonRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;

public class Add2ChangeHelper extends Notification2ChangeHelper {

    private static final Logger logger = Logger.getLogger(Add2ChangeHelper.class.getSimpleName());

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final CreateNonRootEObject<Object> createdObject = ChangeFactory.eINSTANCE.createCreateNonRootEObject();
        createdObject.setChangedEObject((EObject) notification.getNewValue());
        // TODO use old value of affectedEObject as affectedEObject
        final EObject affectedEObject = (EObject) notification.getNotifier();
        createdObject.setAffectedEObject(affectedEObject);
        final Object newFeatureValue = affectedEObject.eGet((EStructuralFeature) notification.getFeature());
        createdObject.setNewValue(newFeatureValue);
        createdObject.setAffectedFeature((EReference) notification.getFeature());
        this.addChangeToList(changeList, createdObject);
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        logger.warn("createChangeFromAttributeChangeNotification in Add2ChangeHelper called. "
                + "Check if the call is correct for notification " + notification);
        final UpdateEAttribute<Object> updateAttribute = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        updateAttribute.setAffectedEObject((EObject) notification.getNotifier());
        updateAttribute.setAffectedFeature((EAttribute) notification.getFeature());
        updateAttribute.setNewValue(notification.getNewValue());

        this.addChangeToList(changeList, updateAttribute);
    }

}
