package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.java.pcm2java.repository

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.run.transformationexecuter.EmptyEObjectMappingTransformation
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.emftext.language.java.parameters.OrdinaryParameter
import org.emftext.language.java.parameters.ParametersFactory
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.DataType
import org.palladiosimulator.pcm.repository.Parameter
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.framework.run.util.TransformationUtils
import edu.kit.ipd.sdq.vitruvius.casestudies.pcm.util.PCMNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.java.util.JaMoPPNamespace
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.DataTypeCorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.pcm2java.PCM2JaMoPPUtils

class ParameterMappingTransformation extends EmptyEObjectMappingTransformation {

	val private static Logger logger = Logger.getLogger(ParameterMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return Parameter
	}

	/**
	 * called when Parameter is created:
	 * Create similar JaMoPP parameter
	 */
	override createEObject(EObject eObject) {
		val Parameter parameter = eObject as Parameter
		var OrdinaryParameter jaMoPPParam = ParametersFactory.eINSTANCE.createOrdinaryParameter
		jaMoPPParam.setName(parameter.parameterName)
		val TypeReference typeReference = DataTypeCorrespondenceHelper.
			claimUniqueCorrespondingJaMoPPDataTypeReference(parameter.dataType__Parameter, blackboard.correspondenceModel)
		if (null == typeReference) {
			logger.warn(
				"No corresponding for data type " + parameter.dataType__Parameter +
					" found. Can not create correspondence for the parameter " + parameter)
			return #[]
		}
		jaMoPPParam.setTypeReference(typeReference)
		return jaMoPPParam.toList;
	}

	/**
	 * called when Parameter is removed:
	 */
	override removeEObject(EObject eObject) {
		null
	}

	/**
	 * Reacts to either a name change
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val transformationResult = new TransformationResult
		val Set<EObject> correspondingObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedAttribute, featureCorrespondenceMap,
				blackboard.correspondenceModel)
		if (correspondingObjects.nullOrEmpty || correspondingObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).nullOrEmpty) {
			return transformationResult
		}
		if (affectedAttribute.name.equals(PCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME)) {
			val boolean saveFilesOfChangedEObjects = true
			PCM2JaMoPPUtils.updateNameAttribute(correspondingObjects, newValue, affectedAttribute,
				featureCorrespondenceMap, blackboard.correspondenceModel, saveFilesOfChangedEObjects)
		}
		return transformationResult
	}

	/**
	 * Reacts to a data type change
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val transformationResult = new TransformationResult
		val Set<EObject> correspondingEObjects = PCM2JaMoPPUtils.
			checkKeyAndCorrespondingObjects(affectedEObject, affectedReference, featureCorrespondenceMap,
				blackboard.correspondenceModel)
		if (correspondingEObjects.nullOrEmpty ||
			correspondingEObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).nullOrEmpty) {
			return transformationResult
		}
		val correspondingParameter = correspondingEObjects.filter(typeof(org.emftext.language.java.parameters.Parameter)).
			get(0)
		if (affectedReference.name.equals(PCMNamespace.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE) &&
			newValue instanceof DataType) {
			try {
				val TypeReference typeReference = DataTypeCorrespondenceHelper.
					claimUniqueCorrespondingJaMoPPDataTypeReference(newValue as DataType, blackboard.correspondenceModel)
				correspondingParameter.setTypeReference(typeReference)
				val oldTUID = blackboard.correspondenceModel.calculateTUIDFromEObject(correspondingParameter)
				blackboard.correspondenceModel.updateTUID(oldTUID, correspondingParameter)
			} catch (RuntimeException e) {
				logger.warn("Could not find correspondence for PCM data type " + oldValue + " . " + e)
			}
		}
		transformationResult
	}

	/**
	 * add type Correspondence
	 */
	override setCorrespondenceForFeatures() {
		val pcmDummyParam = RepositoryFactory.eINSTANCE.createParameter
		val jaMoPPDummyParam = ParametersFactory.eINSTANCE.createOrdinaryParameter
		val EStructuralFeature pcmDataTypeAttribute = TransformationUtils.
			getReferenceByNameFromEObject(PCMNamespace.PCM_PARAMETER_ATTRIBUTE_DATA_TYPE, pcmDummyParam);
		val jaMoPPTypeReference = TransformationUtils.getReferenceByNameFromEObject(
			JaMoPPNamespace.JAMOPP_REFERENCE_TYPE_REFERENCE, jaMoPPDummyParam)
		val EStructuralFeature pcmParameterNameAttribute = TransformationUtils.getAttributeByNameFromEObject(PCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME, pcmDummyParam)
		val EStructuralFeature jaMoPPParammeterNameAttribute = TransformationUtils.getAttributeByNameFromEObject(JaMoPPNamespace.JAMOPP_ATTRIBUTE_NAME, jaMoPPDummyParam)
		featureCorrespondenceMap.put(pcmDataTypeAttribute, jaMoPPTypeReference)
		featureCorrespondenceMap.put(pcmParameterNameAttribute, jaMoPPParammeterNameAttribute)
	}

}