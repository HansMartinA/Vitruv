package tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.userinteraction.UserInteractionType

class CorrespondenceFailCustomDialog extends AbstractCorrespondenceFailHandler {
	val boolean abortEffect
	val String message

	new(boolean abortEffect, String message) {
		this.abortEffect = abortEffect
		this.message = message
	}

	override handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType,
		UserInteracting userInteracting) {
		logFail(foundObjects, sourceElement, expectedType)
		logger.debug("Show user dialog with message: " + message)
		userInteracting.showMessage(UserInteractionType::MODAL, message)
		abortEffect
	}
}
