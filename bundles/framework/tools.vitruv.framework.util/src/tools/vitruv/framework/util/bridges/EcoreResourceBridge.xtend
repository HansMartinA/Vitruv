/** 
 * Copyright (c) 2012 University of Luxembourg and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Max E. Kramer - initial API and implementation
 */
package tools.vitruv.framework.util.bridges

import java.io.IOException
import java.util.Collections
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl

/** 
 * A utility class hiding details of the resources part of the Eclipse Modeling
 * Framework API related for recurring tasks that are not project-specific.<br/>
 * <br/>
 * (Note that it is disputable whether this class conforms to the bridge pattern
 * as we are currently only providing one implementation and the "abstractions"
 * can be regarded as low-level.)
 * @author Max E. Kramer
 */
final class EcoreResourceBridge {
	/** 
	 * Utility classes should not have a public or default constructor. 
	 */
	private new() {
	}

	/** 
	 * Returns the root element of the content of the given IResource if it is
	 * unique (exactly one root element) and {@code null} otherwise.
	 * @see getResourceContentRootIfUnique
	 * @param iResourcea resource
	 * @return the unique root element (if existing) otherwise {@code null}
	 */
	def static EObject getResourceContentRootFromVURIIfUnique(URI uri, ResourceSet resourceSet) {
		val Resource emfResource = loadResourceAtURI(uri, resourceSet)
		return getResourceContentRootIfUnique(emfResource)
	}

	/** 
	 * Returns the root element of the content of the given resource if it is
	 * unique (exactly one root element) and {@code null} otherwise.
	 * @param resourcea resource
	 * @return the unique root element (if existing) otherwise {@code null}
	 */
	def static EObject getResourceContentRootIfUnique(Resource resource) {
		val List<EObject> resourceContents = resource.getContents()
		if (resourceContents.size() === 1) {
			return resourceContents.get(0)
		} else {
			return null
		}
	}

	/** 
	 * Returns the root element of the content of the given resource if it is
	 * unique (exactly one root element) and throws a{@link java.lang.RuntimeException RuntimeException} otherwise.
	 * @param resourcea resource
	 * @param modelNamethe name of the model represented by this resource (for
	 * logging and error output)
	 * @return the root content element of the given resource
	 */
	def static EObject getUniqueContentRoot(Resource resource, String modelName) {
		val EObject uniqueResourceContentRootElement = getResourceContentRootIfUnique(resource)
		if (uniqueResourceContentRootElement !== null) {
			return uniqueResourceContentRootElement
		} else {
			throw new RuntimeException('''The «modelName» '«»«resource»' has to contain exactly one root element!''')
		}
	}

	/** 
	 * Returns the root element of the content of the model at the given URI if
	 * it is unique (exactly one root element) and has the type of the given
	 * class and throws a {@link java.lang.RuntimeException RuntimeException}otherwise
	 * @param resourcea resource
	 * @param modelNamethe name of the model represented by this resource (for
	 * logging and error output)
	 * @param rootElementClassthe class of which the root element has to be an instance of
	 * @param<T>
	 * the type of the root element
	 * @return the root element
	 */
	def static <T extends EObject> T getUniqueContentRootIfCorrectlyTyped(Resource resource, String modelName,
		Class<T> rootElementClass) {
		val EObject rootElement = getUniqueContentRoot(resource, modelName)
		return JavaBridge.dynamicCast(rootElement,
			rootElementClass, '''root element '«»«rootElement»' of the «modelName» '«»«resource»'«»''')
	}

	/** 
	 * Returns the root element of the model instance if it is the only one with
	 * a compatible type. It is NOT necessary to have exactly one root element
	 * as long as only one of these element matches the given type. If there is
	 * not exactly one such element a {@link java.lang.RuntimeExceptionRuntimeException} is thrown.
	 * @param resourcea resource
	 * @param modelNamethe name of the model represented by this resource (for
	 * logging and error output)
	 * @param rootElementClassthe class of which the root element has to be an instance of
	 * @param<T>
	 * the type of the root element
	 * @return the root element
	 */
	@SuppressWarnings("unchecked") def static <T extends EObject> T getUniqueTypedRootEObject(Resource resource,
		String modelName, Class<T> rootElementClass) {
		var T typedRootObject = null
		for (EObject rootObject : resource.getContents()) {
			if (rootElementClass.isInstance(rootObject)) {
				if (typedRootObject !== null) {
					throw new RuntimeException('''There are more than one root objects in «modelName», which match the given type.''')
				}
				typedRootObject = rootObject as T
			}
		}
		if (typedRootObject === null) {
			throw new RuntimeException('''The resource «modelName» does not contain a correctly typed root element.''')
		}
		return typedRootObject
	}

	/** 
	 * Returns the root element of the model instance, which is the first one.
	 * It is NOT necessary to have exactly one root element. If there is not at
	 * least one root element a {@link java.lang.RuntimeExceptionRuntimeException} is thrown.
	 * @param resourcea resource
	 * @param modelNamethe name of the model represented by this resource (for
	 * logging and error output)
	 * @return the root element
	 */
	def static EObject getFirstRootEObject(Resource resource, String modelName) {
		if (resource.getContents().size() < 1) {
			throw new RuntimeException('''The resource «modelName» does not contain a root element.''')
		}
		return resource.getContents().get(0)
	}

	/** 
	 * Returns a set containing all contents of the given resource.
	 * @param resourcea resource
	 * @return a set containing all resource contents
	 */
	def static Set<EObject> getAllContentsSet(Resource resource) {
		return EcoreBridge.getAllContentsSet(resource.getAllContents())
	}

	/** 
	 * Saves the given eObject as the only content of the model at the given
	 * URI.<br/>
	 * <br/>
	 * <b>Attention</b>: If a resource already exists at the given URI it will
	 * be overwritten!
	 * @param eObjectthe new root element
	 * @param resourceURIthe URI of the resource for which the content should be
	 * replaced or created
	 * @throws IOExceptionif an error occurred during saving
	 */
	def static void saveEObjectAsOnlyContent(EObject eObject, URI resourceURI,
		ResourceSet resourceSet) throws IOException {
		var Resource resource = loadResourceAtURI(resourceURI, resourceSet)
		saveEObjectAsOnlyContent(eObject, resource)
	}

	/** 
	 * Saves the given eObject as the only content of the given resource.
	 * @param eObjectthe new root element
	 * @param resourcethe resource for which the content should be replaced or
	 * created
	 * @throws IOExceptionif an error occurred during saving
	 */
	def static void saveEObjectAsOnlyContent(EObject eObject, Resource resource) throws IOException {
		resource.getContents().clear()
		resource.getContents().add(eObject)
		saveResource(resource)
	}

	/** 
	 * Saves the given resource.
	 * @param resourcethe resource to be saved
	 * @throws IOExceptionif an error occurred during saving
	 */
	def static void saveResource(Resource resource) throws IOException {
		saveResource(resource, Collections.emptyMap())
	}

	/** 
	 * Saves the given resource with the given options.
	 * @param resourcethe resource to be saved
	 * @param saveOptionsthe options for saving the resource
	 * @throws IOExceptionif an error occurred during saving
	 */
	def static void saveResource(Resource resource, Map<?, ?> saveOptions) throws IOException {
		resource.save(saveOptions)
	}

	def static Resource loadResourceAtURI(URI resourceURI, ResourceSet resourceSet) {
		return loadResourceAtURI(resourceURI, resourceSet, Collections.emptyMap())
	}

	def static Resource loadResourceAtURI(URI resourceURI, ResourceSet resourceSet, Map<Object, Object> loadOptions) {
		var Resource resource = null
		try {
			if (EMFBridge.existsResourceAtUri(resourceURI)) {
				resource = resourceSet.getResource(resourceURI, true)
			}
			if (resource === null) {
				var Resource oldResource = resourceSet.getResource(resourceURI, false)
				if (oldResource !== null) {
					oldResource.delete(null)
				}
				resource = resourceSet.createResource(resourceURI)
			} else {
				resource.load(loadOptions)
			}
			// Fixes issue caused by JaMoPP: If a model is transitively loaded
			// (e.g. because of an import) the URI starts with pathmap instead of
			// the usual URI. If you try to load this model again the URI remains wrong.
			resource.setURI(resourceURI)
		} catch (IOException e) {
			// soften
			throw new RuntimeException(e)
		}

		return resource
	}

	def static void registerMetamodelPackageOn(ResourceSet rs, Object pckg, String... nsURIs) {
		for (String nsURI : nsURIs) {
			rs.getPackageRegistry().put(nsURI, pckg)
		}
	}

	def static void registerGlobalMetamodelPackage(String nsURI, Object pckg) {
		EPackage.Registry.INSTANCE.put(nsURI, pckg)
	}

	def static void registerExtensionFactoryOn(ResourceSet rs, Object resourceFactory, String... fileExtensions) {
		for (String fileExtension : fileExtensions) {
			rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension, resourceFactory)
		}
	}

	def static void registerGlobalExtensionFactory(String fileExtension, Object resourceFactory) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(fileExtension, resourceFactory)
	}

	def static void registerDefaultXMIExtensionFactory(String fileExtension) {
		registerGlobalExtensionFactory(fileExtension, new XMIResourceFactoryImpl())
	}
}
