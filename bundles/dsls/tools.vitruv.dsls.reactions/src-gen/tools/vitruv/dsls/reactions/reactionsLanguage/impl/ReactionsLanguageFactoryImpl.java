/**
 * generated by Xtext 2.10.0
 */
package tools.vitruv.dsls.reactions.reactionsLanguage.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import tools.vitruv.dsls.reactions.reactionsLanguage.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReactionsLanguageFactoryImpl extends EFactoryImpl implements ReactionsLanguageFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ReactionsLanguageFactory init()
  {
    try
    {
      ReactionsLanguageFactory theReactionsLanguageFactory = (ReactionsLanguageFactory)EPackage.Registry.INSTANCE.getEFactory(ReactionsLanguagePackage.eNS_URI);
      if (theReactionsLanguageFactory != null)
      {
        return theReactionsLanguageFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ReactionsLanguageFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReactionsLanguageFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case ReactionsLanguagePackage.REACTIONS_FILE: return createReactionsFile();
      case ReactionsLanguagePackage.REACTIONS_SEGMENT: return createReactionsSegment();
      case ReactionsLanguagePackage.REACTION: return createReaction();
      case ReactionsLanguagePackage.REACTION_ROUTINE_CALL: return createReactionRoutineCall();
      case ReactionsLanguagePackage.INVARIANT_VIOLATION_EVENT: return createInvariantViolationEvent();
      case ReactionsLanguagePackage.TRIGGER: return createTrigger();
      case ReactionsLanguagePackage.MODEL_CHANGE: return createModelChange();
      case ReactionsLanguagePackage.CONCRETE_MODEL_CHANGE: return createConcreteModelChange();
      case ReactionsLanguagePackage.MODEL_ELEMENT_CHANGE: return createModelElementChange();
      case ReactionsLanguagePackage.MODEL_ATTRIBUTE_CHANGE: return createModelAttributeChange();
      case ReactionsLanguagePackage.ARBITRARY_MODEL_CHANGE: return createArbitraryModelChange();
      case ReactionsLanguagePackage.ELEMENT_EXISTENCE_CHANGE_TYPE: return createElementExistenceChangeType();
      case ReactionsLanguagePackage.MODEL_ELEMENT_USAGE_CHANGE_TYPE: return createModelElementUsageChangeType();
      case ReactionsLanguagePackage.ELEMENT_CREATION_CHANGE_TYPE: return createElementCreationChangeType();
      case ReactionsLanguagePackage.ELEMENT_DELETION_CHANGE_TYPE: return createElementDeletionChangeType();
      case ReactionsLanguagePackage.ELEMENT_REFERENCE_CHANGE_TYPE: return createElementReferenceChangeType();
      case ReactionsLanguagePackage.ELEMENT_ROOT_CHANGE_TYPE: return createElementRootChangeType();
      case ReactionsLanguagePackage.ELEMENT_INSERTION_CHANGE_TYPE: return createElementInsertionChangeType();
      case ReactionsLanguagePackage.ELEMENT_INSERTION_IN_LIST_CHANGE_TYPE: return createElementInsertionInListChangeType();
      case ReactionsLanguagePackage.ELEMENT_INSERTION_AS_ROOT_CHANGE_TYPE: return createElementInsertionAsRootChangeType();
      case ReactionsLanguagePackage.ELEMENT_REMOVAL_CHANGE_TYPE: return createElementRemovalChangeType();
      case ReactionsLanguagePackage.ELEMENT_REMOVAL_AS_ROOT_CHANGE_TYPE: return createElementRemovalAsRootChangeType();
      case ReactionsLanguagePackage.ELEMENT_REMOVAL_FROM_LIST_CHANGE_TYPE: return createElementRemovalFromListChangeType();
      case ReactionsLanguagePackage.ELEMENT_REPLACEMENT_CHANGE_TYPE: return createElementReplacementChangeType();
      case ReactionsLanguagePackage.ELEMENT_CREATION_AND_INSERTION_CHANGE_TYPE: return createElementCreationAndInsertionChangeType();
      case ReactionsLanguagePackage.ELEMENT_DELETION_AND_REMOVAL_CHANGE_TYPE: return createElementDeletionAndRemovalChangeType();
      case ReactionsLanguagePackage.ELEMENT_DELETION_AND_CREATION_AND_REPLACEMENT_CHANGE_TYPE: return createElementDeletionAndCreationAndReplacementChangeType();
      case ReactionsLanguagePackage.ELEMENT_CHANGE_TYPE: return createElementChangeType();
      case ReactionsLanguagePackage.ELEMENT_COMPOUND_CHANGE_TYPE: return createElementCompoundChangeType();
      case ReactionsLanguagePackage.ROUTINE: return createRoutine();
      case ReactionsLanguagePackage.ROUTINE_INPUT: return createRoutineInput();
      case ReactionsLanguagePackage.MATCHER: return createMatcher();
      case ReactionsLanguagePackage.MATCHER_STATEMENT: return createMatcherStatement();
      case ReactionsLanguagePackage.RETRIEVE_MODEL_ELEMENT: return createRetrieveModelElement();
      case ReactionsLanguagePackage.MATCHER_CHECK_STATEMENT: return createMatcherCheckStatement();
      case ReactionsLanguagePackage.ACTION: return createAction();
      case ReactionsLanguagePackage.ROUTINE_CALL_STATEMENT: return createRoutineCallStatement();
      case ReactionsLanguagePackage.ACTION_STATEMENT: return createActionStatement();
      case ReactionsLanguagePackage.CREATE_MODEL_ELEMENT: return createCreateModelElement();
      case ReactionsLanguagePackage.DELETE_MODEL_ELEMENT: return createDeleteModelElement();
      case ReactionsLanguagePackage.UPDATE_MODEL_ELEMENT: return createUpdateModelElement();
      case ReactionsLanguagePackage.CREATE_CORRESPONDENCE: return createCreateCorrespondence();
      case ReactionsLanguagePackage.REMOVE_CORRESPONDENCE: return createRemoveCorrespondence();
      case ReactionsLanguagePackage.CODE_BLOCK: return createCodeBlock();
      case ReactionsLanguagePackage.EXECUTE_ACTION_BLOCK: return createExecuteActionBlock();
      case ReactionsLanguagePackage.ROUTINE_CALL_BLOCK: return createRoutineCallBlock();
      case ReactionsLanguagePackage.TAGGABLE: return createTaggable();
      case ReactionsLanguagePackage.EXISTING_ELEMENT_REFERENCE: return createExistingElementReference();
      case ReactionsLanguagePackage.TAG_CODE_BLOCK: return createTagCodeBlock();
      case ReactionsLanguagePackage.PRECONDITION_CODE_BLOCK: return createPreconditionCodeBlock();
      case ReactionsLanguagePackage.CORRESPONDING_OBJECT_CODE_BLOCK: return createCorrespondingObjectCodeBlock();
      case ReactionsLanguagePackage.EXECUTION_CODE_BLOCK: return createExecutionCodeBlock();
      case ReactionsLanguagePackage.RETURN_STATEMENT: return createReturnStatement();
      case ReactionsLanguagePackage.MODEL_ATTRIBUTE_INSERTED_CHANGE: return createModelAttributeInsertedChange();
      case ReactionsLanguagePackage.MODEL_ATTRIBUTE_REMOVED_CHANGE: return createModelAttributeRemovedChange();
      case ReactionsLanguagePackage.MODEL_ATTRIBUTE_REPLACED_CHANGE: return createModelAttributeReplacedChange();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReactionsFile createReactionsFile()
  {
    ReactionsFileImpl reactionsFile = new ReactionsFileImpl();
    return reactionsFile;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReactionsSegment createReactionsSegment()
  {
    ReactionsSegmentImpl reactionsSegment = new ReactionsSegmentImpl();
    return reactionsSegment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Reaction createReaction()
  {
    ReactionImpl reaction = new ReactionImpl();
    return reaction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReactionRoutineCall createReactionRoutineCall()
  {
    ReactionRoutineCallImpl reactionRoutineCall = new ReactionRoutineCallImpl();
    return reactionRoutineCall;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InvariantViolationEvent createInvariantViolationEvent()
  {
    InvariantViolationEventImpl invariantViolationEvent = new InvariantViolationEventImpl();
    return invariantViolationEvent;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Trigger createTrigger()
  {
    TriggerImpl trigger = new TriggerImpl();
    return trigger;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelChange createModelChange()
  {
    ModelChangeImpl modelChange = new ModelChangeImpl();
    return modelChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConcreteModelChange createConcreteModelChange()
  {
    ConcreteModelChangeImpl concreteModelChange = new ConcreteModelChangeImpl();
    return concreteModelChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelElementChange createModelElementChange()
  {
    ModelElementChangeImpl modelElementChange = new ModelElementChangeImpl();
    return modelElementChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelAttributeChange createModelAttributeChange()
  {
    ModelAttributeChangeImpl modelAttributeChange = new ModelAttributeChangeImpl();
    return modelAttributeChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ArbitraryModelChange createArbitraryModelChange()
  {
    ArbitraryModelChangeImpl arbitraryModelChange = new ArbitraryModelChangeImpl();
    return arbitraryModelChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementExistenceChangeType createElementExistenceChangeType()
  {
    ElementExistenceChangeTypeImpl elementExistenceChangeType = new ElementExistenceChangeTypeImpl();
    return elementExistenceChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelElementUsageChangeType createModelElementUsageChangeType()
  {
    ModelElementUsageChangeTypeImpl modelElementUsageChangeType = new ModelElementUsageChangeTypeImpl();
    return modelElementUsageChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementCreationChangeType createElementCreationChangeType()
  {
    ElementCreationChangeTypeImpl elementCreationChangeType = new ElementCreationChangeTypeImpl();
    return elementCreationChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementDeletionChangeType createElementDeletionChangeType()
  {
    ElementDeletionChangeTypeImpl elementDeletionChangeType = new ElementDeletionChangeTypeImpl();
    return elementDeletionChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementReferenceChangeType createElementReferenceChangeType()
  {
    ElementReferenceChangeTypeImpl elementReferenceChangeType = new ElementReferenceChangeTypeImpl();
    return elementReferenceChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementRootChangeType createElementRootChangeType()
  {
    ElementRootChangeTypeImpl elementRootChangeType = new ElementRootChangeTypeImpl();
    return elementRootChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementInsertionChangeType createElementInsertionChangeType()
  {
    ElementInsertionChangeTypeImpl elementInsertionChangeType = new ElementInsertionChangeTypeImpl();
    return elementInsertionChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementInsertionInListChangeType createElementInsertionInListChangeType()
  {
    ElementInsertionInListChangeTypeImpl elementInsertionInListChangeType = new ElementInsertionInListChangeTypeImpl();
    return elementInsertionInListChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementInsertionAsRootChangeType createElementInsertionAsRootChangeType()
  {
    ElementInsertionAsRootChangeTypeImpl elementInsertionAsRootChangeType = new ElementInsertionAsRootChangeTypeImpl();
    return elementInsertionAsRootChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementRemovalChangeType createElementRemovalChangeType()
  {
    ElementRemovalChangeTypeImpl elementRemovalChangeType = new ElementRemovalChangeTypeImpl();
    return elementRemovalChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementRemovalAsRootChangeType createElementRemovalAsRootChangeType()
  {
    ElementRemovalAsRootChangeTypeImpl elementRemovalAsRootChangeType = new ElementRemovalAsRootChangeTypeImpl();
    return elementRemovalAsRootChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementRemovalFromListChangeType createElementRemovalFromListChangeType()
  {
    ElementRemovalFromListChangeTypeImpl elementRemovalFromListChangeType = new ElementRemovalFromListChangeTypeImpl();
    return elementRemovalFromListChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementReplacementChangeType createElementReplacementChangeType()
  {
    ElementReplacementChangeTypeImpl elementReplacementChangeType = new ElementReplacementChangeTypeImpl();
    return elementReplacementChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementCreationAndInsertionChangeType createElementCreationAndInsertionChangeType()
  {
    ElementCreationAndInsertionChangeTypeImpl elementCreationAndInsertionChangeType = new ElementCreationAndInsertionChangeTypeImpl();
    return elementCreationAndInsertionChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementDeletionAndRemovalChangeType createElementDeletionAndRemovalChangeType()
  {
    ElementDeletionAndRemovalChangeTypeImpl elementDeletionAndRemovalChangeType = new ElementDeletionAndRemovalChangeTypeImpl();
    return elementDeletionAndRemovalChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementDeletionAndCreationAndReplacementChangeType createElementDeletionAndCreationAndReplacementChangeType()
  {
    ElementDeletionAndCreationAndReplacementChangeTypeImpl elementDeletionAndCreationAndReplacementChangeType = new ElementDeletionAndCreationAndReplacementChangeTypeImpl();
    return elementDeletionAndCreationAndReplacementChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementChangeType createElementChangeType()
  {
    ElementChangeTypeImpl elementChangeType = new ElementChangeTypeImpl();
    return elementChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementCompoundChangeType createElementCompoundChangeType()
  {
    ElementCompoundChangeTypeImpl elementCompoundChangeType = new ElementCompoundChangeTypeImpl();
    return elementCompoundChangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Routine createRoutine()
  {
    RoutineImpl routine = new RoutineImpl();
    return routine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RoutineInput createRoutineInput()
  {
    RoutineInputImpl routineInput = new RoutineInputImpl();
    return routineInput;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Matcher createMatcher()
  {
    MatcherImpl matcher = new MatcherImpl();
    return matcher;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MatcherStatement createMatcherStatement()
  {
    MatcherStatementImpl matcherStatement = new MatcherStatementImpl();
    return matcherStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RetrieveModelElement createRetrieveModelElement()
  {
    RetrieveModelElementImpl retrieveModelElement = new RetrieveModelElementImpl();
    return retrieveModelElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MatcherCheckStatement createMatcherCheckStatement()
  {
    MatcherCheckStatementImpl matcherCheckStatement = new MatcherCheckStatementImpl();
    return matcherCheckStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Action createAction()
  {
    ActionImpl action = new ActionImpl();
    return action;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RoutineCallStatement createRoutineCallStatement()
  {
    RoutineCallStatementImpl routineCallStatement = new RoutineCallStatementImpl();
    return routineCallStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ActionStatement createActionStatement()
  {
    ActionStatementImpl actionStatement = new ActionStatementImpl();
    return actionStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CreateModelElement createCreateModelElement()
  {
    CreateModelElementImpl createModelElement = new CreateModelElementImpl();
    return createModelElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DeleteModelElement createDeleteModelElement()
  {
    DeleteModelElementImpl deleteModelElement = new DeleteModelElementImpl();
    return deleteModelElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UpdateModelElement createUpdateModelElement()
  {
    UpdateModelElementImpl updateModelElement = new UpdateModelElementImpl();
    return updateModelElement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CreateCorrespondence createCreateCorrespondence()
  {
    CreateCorrespondenceImpl createCorrespondence = new CreateCorrespondenceImpl();
    return createCorrespondence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RemoveCorrespondence createRemoveCorrespondence()
  {
    RemoveCorrespondenceImpl removeCorrespondence = new RemoveCorrespondenceImpl();
    return removeCorrespondence;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CodeBlock createCodeBlock()
  {
    CodeBlockImpl codeBlock = new CodeBlockImpl();
    return codeBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExecuteActionBlock createExecuteActionBlock()
  {
    ExecuteActionBlockImpl executeActionBlock = new ExecuteActionBlockImpl();
    return executeActionBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RoutineCallBlock createRoutineCallBlock()
  {
    RoutineCallBlockImpl routineCallBlock = new RoutineCallBlockImpl();
    return routineCallBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Taggable createTaggable()
  {
    TaggableImpl taggable = new TaggableImpl();
    return taggable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExistingElementReference createExistingElementReference()
  {
    ExistingElementReferenceImpl existingElementReference = new ExistingElementReferenceImpl();
    return existingElementReference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TagCodeBlock createTagCodeBlock()
  {
    TagCodeBlockImpl tagCodeBlock = new TagCodeBlockImpl();
    return tagCodeBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PreconditionCodeBlock createPreconditionCodeBlock()
  {
    PreconditionCodeBlockImpl preconditionCodeBlock = new PreconditionCodeBlockImpl();
    return preconditionCodeBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CorrespondingObjectCodeBlock createCorrespondingObjectCodeBlock()
  {
    CorrespondingObjectCodeBlockImpl correspondingObjectCodeBlock = new CorrespondingObjectCodeBlockImpl();
    return correspondingObjectCodeBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExecutionCodeBlock createExecutionCodeBlock()
  {
    ExecutionCodeBlockImpl executionCodeBlock = new ExecutionCodeBlockImpl();
    return executionCodeBlock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReturnStatement createReturnStatement()
  {
    ReturnStatementImpl returnStatement = new ReturnStatementImpl();
    return returnStatement;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelAttributeInsertedChange createModelAttributeInsertedChange()
  {
    ModelAttributeInsertedChangeImpl modelAttributeInsertedChange = new ModelAttributeInsertedChangeImpl();
    return modelAttributeInsertedChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelAttributeRemovedChange createModelAttributeRemovedChange()
  {
    ModelAttributeRemovedChangeImpl modelAttributeRemovedChange = new ModelAttributeRemovedChangeImpl();
    return modelAttributeRemovedChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelAttributeReplacedChange createModelAttributeReplacedChange()
  {
    ModelAttributeReplacedChangeImpl modelAttributeReplacedChange = new ModelAttributeReplacedChangeImpl();
    return modelAttributeReplacedChange;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReactionsLanguagePackage getReactionsLanguagePackage()
  {
    return (ReactionsLanguagePackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ReactionsLanguagePackage getPackage()
  {
    return ReactionsLanguagePackage.eINSTANCE;
  }

} //ReactionsLanguageFactoryImpl
