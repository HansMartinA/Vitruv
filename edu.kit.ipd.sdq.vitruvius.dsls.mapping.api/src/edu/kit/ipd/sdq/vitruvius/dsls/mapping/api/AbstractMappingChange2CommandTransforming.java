package edu.kit.ipd.sdq.vitruvius.dsls.mapping.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.VitruviusTransformationRecordingCommand;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.datatypes.ChangeType;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MappingRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.interfaces.MIRUserInteracting;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.MappingExecutionState;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * The base for the {@link Change2CommandTransforming} generated for the mapping language.
 * @author Dominik Werle
 * @deprecated use Responses instead
 */
@Deprecated
public abstract class AbstractMappingChange2CommandTransforming implements Change2CommandTransforming {
	private final static Logger LOGGER = Logger.getLogger(AbstractMappingChange2CommandTransforming.class);

	private List<Pair<ChangeType, MappingRealization>> changeTypeAndMappings = new ArrayList<Pair<ChangeType, MappingRealization>>();

	public AbstractMappingChange2CommandTransforming() {
		this.changeTypeAndMappings = new ArrayList<>();
		this.setup();
	}

	protected void addMapping(final Pair<ChangeType, MappingRealization> mapping) {
		this.changeTypeAndMappings.add(mapping);
	}

	protected void addMapping(MappingRealization mapping) {
		this.changeTypeAndMappings.add(new Pair<ChangeType, MappingRealization>(ChangeType.ANY_CHANGE, mapping));
	}

	@Override
	public void transformChanges2Commands(final Blackboard blackboard) {
		final List<Change> changes = blackboard.getAndArchiveChangesForTransformation();
		final List<Command> commands = new ArrayList<Command>();

		for (final Change change : changes) {
			commands.addAll(this.handleChange(change, blackboard));
		}

		blackboard.pushCommands(commands);
	}

	private List<Command> handleChange(final Change change, final Blackboard blackboard) {
		final List<Command> result = new ArrayList<Command>();
		if (change instanceof CompositeChange) {
			for (final Change c : ((CompositeChange) change).getChanges()) {
				result.addAll(this.handleChange(c, blackboard));
			}
		} else if (change instanceof EMFModelChange) {
			result.addAll(this.handleEChange(((EMFModelChange) change).getEChange(), blackboard));
		} else {
			throw new IllegalArgumentException("Change subtype " + change.getClass().getName() + " not handled");
		}

		return result;
	}

	protected List<Command> callRelevantMappings(final EChange eChange, final Blackboard blackboard) {
		final List<Command> result = new ArrayList<Command>();
		final List<MappingRealization> relevantMappings = this.getCandidateMappings(eChange);
		final MappedCorrespondenceInstance mappedCorrespondenceInstance = JavaHelper
				.requireType(blackboard.getCorrespondenceInstance(), MappedCorrespondenceInstance.class);
		
		final Command command = EMFCommandBridge
				.createVitruviusTransformationRecordingCommand(() -> {
					final MappingExecutionState state = null; // new MappingExecutionState(mappedCorrespondenceInstance, blackboard);
					LOGGER.debug("call relevant mappings");
					for (final MappingRealization mapping : relevantMappings) {
						LOGGER.debug(mapping.getMappingID() + " (" + mapping.toString() + ")");
						mapping.applyEChange(eChange, blackboard, state);
					}
					return state;
				});
		
		result.add(command);
		return result;
	}

	protected List<Command> handleEChange(final EChange eChange, final Blackboard blackboard) {
		return this.callRelevantMappings(eChange, blackboard);
	}

	protected List<MappingRealization> getCandidateMappings(final EChange eChange) {
		return this.changeTypeAndMappings.stream().filter((it) -> it.getFirst().covers(eChange))
				.map((it) -> it.getSecond()).collect(Collectors.toList());
	}

	protected abstract void setup();

	public abstract void setUserInteracting(MIRUserInteracting userInteracting);
}
