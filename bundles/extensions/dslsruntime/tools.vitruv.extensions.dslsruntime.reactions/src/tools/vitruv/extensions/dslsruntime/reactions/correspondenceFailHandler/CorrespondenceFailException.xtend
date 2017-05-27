package tools.vitruv.extensions.dslsruntime.reactions.correspondenceFailHandler

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.userinteraction.UserInteracting

class CorrespondenceFailException extends AbstractCorrespondenceFailHandler {
	override handle(Iterable<? extends EObject> foundObjects, EObject sourceElement, Class<?> expectedType, UserInteracting userInteracting) {
		logFail(foundObjects, sourceElement, expectedType);
		logger.debug("Throw exception due to correspondence fail");
		throw new IllegalArgumentException(
			"There were (" + foundObjects.size + ") corresponding elements of type " +
				expectedType.getSimpleName() + " for: " + sourceElement + ", which are: " + foundObjects);
	}
}