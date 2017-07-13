package tools.vitruv.framework.tests

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.PropagatedChange

/**
 * Interface to register and notify {@link ChangeObserver}s.
 * 
 * @author Patrick Stoeckle
 * @version 0.1.0
 * @since 2017-05-30
 */
interface ChangeObservable {
	def void registerObserver(ChangeObserver observer)

	def void unRegisterObserver(ChangeObserver observer)

	def void notifyObservers(VURI vuri, PropagatedChange change)
}
