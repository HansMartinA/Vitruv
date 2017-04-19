package tools.vitruv.framework.vsum

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

class VirtualModelConfigurationBuilder {
	private val VirtualModelConfiguration modelConfiguration;
	
	public new() {
		this.modelConfiguration = new VirtualModelConfiguration();	
	}
	
	public def VirtualModelConfigurationBuilder addMetamodel(VitruvDomain metamodel) {
		modelConfiguration.addMetamodel(metamodel);
		return this;
	}
	
	public def VirtualModelConfigurationBuilder addChangePropagationSpecification(ChangePropagationSpecification changePropagationSpecification) {
		modelConfiguration.addChangePropagationSpecification(changePropagationSpecification);
		return this;
	}

	// TODO HK Generate here to avoid that the configuration gets modified by further calls to the builder afterwards	
	public def VirtualModelConfiguration toConfiguration() {
		return modelConfiguration;
	}
}
