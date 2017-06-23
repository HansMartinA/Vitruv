/** 
 */
package tools.vitruv.framework.versioning.repository

import tools.vitruv.framework.versioning.Named
import tools.vitruv.framework.versioning.author.Signed
import tools.vitruv.framework.versioning.commit.Commit

/** 
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tag</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link tools.vitruv.framework.versioning.repository.Tag#getCommit <em>Commit</em>}</li>
 * </ul>
 * @see tools.vitruv.framework.versioning.repository.RepositoryPackage#getTag()
 * @model
 * @generated
 */
interface Tag extends Named, Signed {
	/** 
	 * Returns the value of the '<em><b>Commit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit</em>' reference.
	 * @see #setCommit(Commit)
	 * @see tools.vitruv.framework.versioning.repository.RepositoryPackage#getTag_Commit()
	 * @model required="true"
	 * @generated
	 */
	def Commit getCommit()

	/** 
	 * Sets the value of the '{@link tools.vitruv.framework.versioning.repository.Tag#getCommit <em>Commit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit</em>' reference.
	 * @see #getCommit()
	 * @generated
	 */
	def void setCommit(Commit value)
// Tag
}