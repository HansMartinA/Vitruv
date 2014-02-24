package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emf;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.ChangeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.UpdateEReference;

public class Set2ChangeHelper extends Notification2ChangeHelper {

    @Override
    void createChangeFromRefernceChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateEReference<Object> setEReference = ChangeFactory.eINSTANCE.createUpdateEReference();
        final EObject affectedEObject = (EObject) notification.getNotifier();
        setEReference.setAffectedEObject(affectedEObject);
        setEReference.setAffectedFeature((EReference) notification.getFeature());
        setEReference.setNewValue(affectedEObject.eGet((EStructuralFeature) notification.getFeature()));
        this.addChangeToList(changeList, setEReference);
    }

    @Override
    void createChangeFromAttributeChangeNotification(final Notification notification, final List<Change> changeList) {
        final UpdateEAttribute<Object> setAttribute = ChangeFactory.eINSTANCE.createUpdateEAttribute();
        setAttribute.setAffectedEObject((EObject) notification.getNotifier());
        setAttribute.setAffectedFeature((EAttribute) notification.getFeature());
        setAttribute.setNewValue(notification.getNewValue());
        this.addChangeToList(changeList, setAttribute);
    }

}
