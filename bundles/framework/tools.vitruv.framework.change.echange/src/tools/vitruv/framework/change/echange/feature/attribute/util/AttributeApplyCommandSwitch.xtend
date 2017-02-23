package tools.vitruv.framework.change.echange.feature.attribute.util

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.SetCommand
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.util.command.RemoveAtCommand

public class AttributeApplyCommandSwitch extends AttributeSwitch<Command> {
	override public Command caseInsertEAttributeValue(InsertEAttributeValue object) {
		val editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), null);
		return AddCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue, object.index);
	}	
	override public Command caseRemoveEAttributeValue(RemoveEAttributeValue object) {
		val editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), null);
		return RemoveAtCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue, object.index);
	}
	override public Command caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute object) {
		val editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), null)
		return SetCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue)
	}	
}