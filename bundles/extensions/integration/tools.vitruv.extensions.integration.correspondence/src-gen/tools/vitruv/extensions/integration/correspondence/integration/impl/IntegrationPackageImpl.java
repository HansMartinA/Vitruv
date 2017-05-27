/**
 */
package tools.vitruv.extensions.integration.correspondence.integration.impl;

import tools.vitruv.extensions.integration.correspondence.integration.IntegrationCorrespondence;
import tools.vitruv.extensions.integration.correspondence.integration.IntegrationFactory;
import tools.vitruv.extensions.integration.correspondence.integration.IntegrationPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IntegrationPackageImpl extends EPackageImpl implements IntegrationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integrationCorrespondenceEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see tools.vitruv.extensions.integration.correspondence.integration.IntegrationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IntegrationPackageImpl() {
		super(eNS_URI, IntegrationFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link IntegrationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IntegrationPackage init() {
		if (isInited) return (IntegrationPackage)EPackage.Registry.INSTANCE.getEPackage(IntegrationPackage.eNS_URI);

		// Obtain or create and register package
		IntegrationPackageImpl theIntegrationPackage = (IntegrationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof IntegrationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new IntegrationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ReactionsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIntegrationPackage.createPackageContents();

		// Initialize created meta-data
		theIntegrationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIntegrationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IntegrationPackage.eNS_URI, theIntegrationPackage);
		return theIntegrationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegrationCorrespondence() {
		return integrationCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegrationCorrespondence_CreatedByIntegration() {
		return (EAttribute)integrationCorrespondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegrationFactory getIntegrationFactory() {
		return (IntegrationFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		integrationCorrespondenceEClass = createEClass(INTEGRATION_CORRESPONDENCE);
		createEAttribute(integrationCorrespondenceEClass, INTEGRATION_CORRESPONDENCE__CREATED_BY_INTEGRATION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ReactionsPackage theReactionsPackage = (ReactionsPackage)EPackage.Registry.INSTANCE.getEPackage(ReactionsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		integrationCorrespondenceEClass.getESuperTypes().add(theReactionsPackage.getReactionsCorrespondence());

		// Initialize classes and features; add operations and parameters
		initEClass(integrationCorrespondenceEClass, IntegrationCorrespondence.class, "IntegrationCorrespondence", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegrationCorrespondence_CreatedByIntegration(), ecorePackage.getEBoolean(), "createdByIntegration", "false", 0, 1, IntegrationCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //IntegrationPackageImpl
