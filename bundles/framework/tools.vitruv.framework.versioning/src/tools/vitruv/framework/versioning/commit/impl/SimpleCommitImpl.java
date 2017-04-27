/**
 */
package tools.vitruv.framework.versioning.commit.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.versioning.Author;
import tools.vitruv.framework.versioning.commit.Commit;
import tools.vitruv.framework.versioning.commit.CommitPackage;
import tools.vitruv.framework.versioning.commit.SimpleCommit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Commit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link tools.vitruv.framework.versioning.commit.impl.SimpleCommitImpl#getParent <em>Parent</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleCommitImpl extends CommitImpl implements SimpleCommit {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected Commit parent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleCommitImpl() {
		super();
	}

	/**
	 * @param changes
	 * @param commitmessage
	 */
	public SimpleCommitImpl(final EList<EChange> changes, final String message, final Author author, final Commit parent) {
		super(changes, new CommitMessageImpl(message, author));
		// assert changes.size() > 0;
		assert parent != null;
		this.parent = parent;
		final int oldSize = parent.getCommitsBranchedFromThis().size(); 
		this.parent.getCommitsBranchedFromThis().add(this);
		assert oldSize + 1 == parent.getCommitsBranchedFromThis().size();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CommitPackage.Literals.SIMPLE_COMMIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (Commit)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CommitPackage.SIMPLE_COMMIT__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Commit basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Commit newParent, NotificationChain msgs) {
		Commit oldParent = parent;
		parent = newParent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CommitPackage.SIMPLE_COMMIT__PARENT, oldParent, newParent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Commit newParent) {
		if (newParent != parent) {
			NotificationChain msgs = null;
			if (parent != null)
				msgs = ((InternalEObject)parent).eInverseRemove(this, CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS, Commit.class, msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS, Commit.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CommitPackage.SIMPLE_COMMIT__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT:
				if (parent != null)
					msgs = ((InternalEObject)parent).eInverseRemove(this, CommitPackage.COMMIT__COMMITS_BRANCHED_FROM_THIS, Commit.class, msgs);
				return basicSetParent((Commit)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT:
				return basicSetParent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT:
				setParent((Commit)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT:
				setParent((Commit)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CommitPackage.SIMPLE_COMMIT__PARENT:
				return parent != null;
		}
		return super.eIsSet(featureID);
	}

} //SimpleCommitImpl
