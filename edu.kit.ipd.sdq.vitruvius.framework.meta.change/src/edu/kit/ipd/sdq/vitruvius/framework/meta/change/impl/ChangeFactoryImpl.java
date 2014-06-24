/**
 */
package edu.kit.ipd.sdq.vitruvius.framework.meta.change.impl;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ChangeFactoryImpl extends EFactoryImpl implements ChangeFactory {
	/**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static ChangeFactory init() {
        try {
            ChangeFactory theChangeFactory = (ChangeFactory)EPackage.Registry.INSTANCE.getEFactory(ChangePackage.eNS_URI);
            if (theChangeFactory != null) {
                return theChangeFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ChangeFactoryImpl();
    }

	/**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ChangeFactoryImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case ChangePackage.CREATE_NON_ROOT_EOBJECT: return createCreateNonRootEObject();
            case ChangePackage.CREATE_ROOT_EOBJECT: return createCreateRootEObject();
            case ChangePackage.UNSET_EFEATURE: return createUnsetEFeature();
            case ChangePackage.UPDATE_EATTRIBUTE: return createUpdateEAttribute();
            case ChangePackage.UPDATE_EREFERENCE: return createUpdateEReference();
            case ChangePackage.UPDATE_ECONTAINMENT_REFERENCE: return createUpdateEContainmentReference();
            case ChangePackage.DELETE_NON_ROOT_EOBJECT: return createDeleteNonRootEObject();
            case ChangePackage.DELETE_ROOT_EOBJECT: return createDeleteRootEObject();
            case ChangePackage.INSERT_IN_ELIST: return createInsertInEList();
            case ChangePackage.REMOVE_FROM_ELIST: return createRemoveFromEList();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends Object> CreateNonRootEObject<T> createCreateNonRootEObject() {
        CreateNonRootEObjectImpl<T> createNonRootEObject = new CreateNonRootEObjectImpl<T>();
        return createNonRootEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public CreateRootEObject createCreateRootEObject() {
        CreateRootEObjectImpl createRootEObject = new CreateRootEObjectImpl();
        return createRootEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends EStructuralFeature> UnsetEFeature<T> createUnsetEFeature() {
        UnsetEFeatureImpl<T> unsetEFeature = new UnsetEFeatureImpl<T>();
        return unsetEFeature;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends Object> UpdateEAttribute<T> createUpdateEAttribute() {
        UpdateEAttributeImpl<T> updateEAttribute = new UpdateEAttributeImpl<T>();
        return updateEAttribute;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends Object> UpdateEReference<T> createUpdateEReference() {
        UpdateEReferenceImpl<T> updateEReference = new UpdateEReferenceImpl<T>();
        return updateEReference;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends Object> UpdateEContainmentReference<T> createUpdateEContainmentReference() {
        UpdateEContainmentReferenceImpl<T> updateEContainmentReference = new UpdateEContainmentReferenceImpl<T>();
        return updateEContainmentReference;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public <T extends Object> DeleteNonRootEObject<T> createDeleteNonRootEObject() {
        DeleteNonRootEObjectImpl<T> deleteNonRootEObject = new DeleteNonRootEObjectImpl<T>();
        return deleteNonRootEObject;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public DeleteRootEObject createDeleteRootEObject() {
        DeleteRootEObjectImpl deleteRootEObject = new DeleteRootEObjectImpl();
        return deleteRootEObject;
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EStructuralFeature> InsertInEList<T> createInsertInEList() {
        InsertInEListImpl<T> insertInEList = new InsertInEListImpl<T>();
        return insertInEList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public <T extends EStructuralFeature> RemoveFromEList<T> createRemoveFromEList() {
        RemoveFromEListImpl<T> removeFromEList = new RemoveFromEListImpl<T>();
        return removeFromEList;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ChangePackage getChangePackage() {
        return (ChangePackage)getEPackage();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
	@Deprecated
	public static ChangePackage getPackage() {
        return ChangePackage.eINSTANCE;
    }

} //ChangeFactoryImpl
