/**
 */
package tools.vitruv.extensions.integration.correspondence.integration;

import tools.vitruv.dsls.reactions.meta.correspondence.reactions.ReactionsCorrespondence;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.extensions.integration.correspondence.integration.IntegrationCorrespondence#isCreatedByIntegration <em>Created By Integration</em>}</li>
 * </ul>
 *
 * @see tools.vitruv.extensions.integration.correspondence.integration.IntegrationPackage#getIntegrationCorrespondence()
 * @model
 * @generated
 */
public interface IntegrationCorrespondence extends ReactionsCorrespondence {
	/**
	 * Returns the value of the '<em><b>Created By Integration</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created By Integration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created By Integration</em>' attribute.
	 * @see #setCreatedByIntegration(boolean)
	 * @see tools.vitruv.extensions.integration.correspondence.integration.IntegrationPackage#getIntegrationCorrespondence_CreatedByIntegration()
	 * @model default="false"
	 * @generated
	 */
	boolean isCreatedByIntegration();

	/**
	 * Sets the value of the '{@link tools.vitruv.extensions.integration.correspondence.integration.IntegrationCorrespondence#isCreatedByIntegration <em>Created By Integration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created By Integration</em>' attribute.
	 * @see #isCreatedByIntegration()
	 * @generated
	 */
	void setCreatedByIntegration(boolean value);

} // IntegrationCorrespondence
