/*
 * generated by Xtext 2.12.0
 */
package tools.vitruv.dsls.mirbase.ui.tests;

import com.google.inject.Injector;
import org.eclipse.xtext.testing.IInjectorProvider;
import tools.vitruv.dsls.mirbase.ui.internal.MirbaseActivator;

public class MirBaseUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return MirbaseActivator.getInstance().getInjector("tools.vitruv.dsls.mirbase.MirBase");
	}

}
