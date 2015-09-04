package edu.kit.ipd.sdq.vitruvius.framework.vsum;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Invariants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ValidationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MappingManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MetamodelManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Validating;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ViewTypeManaging;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalContractsBuilder;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;
import edu.kit.ipd.sdq.vitruvius.framework.vsum.helper.FileSystemHelper;

public class VSUMImpl implements ModelProviding, CorrespondenceProviding, Validating {

    private static final Logger logger = Logger.getLogger(VSUMImpl.class.getSimpleName());

    private final MappingManaging mappingManaging;
    private final MetamodelManaging metamodelManaging;
    private final ViewTypeManaging viewTypeManaging;

    protected final Map<VURI, ModelInstance> modelInstances;
    private final ResourceSet resourceSet;
    private final Map<Mapping, InternalCorrespondenceInstance> mapping2CorrespondenceInstanceMap;

    private ClassLoader classLoader;

    public VSUMImpl(final MetamodelManaging metamodelManaging, final ViewTypeManaging viewTypeManaging,
            final MappingManaging mappingManaging) {
        this(metamodelManaging, viewTypeManaging, mappingManaging, null);
    }

    public VSUMImpl(final MetamodelManaging metamodelManaging, final ViewTypeManaging viewTypeManaging,
            final MappingManaging mappingManaging, final ClassLoader classLoader) {
        this.metamodelManaging = metamodelManaging;
        this.viewTypeManaging = viewTypeManaging;
        this.mappingManaging = mappingManaging;

        this.resourceSet = new ResourceSetImpl();

        this.modelInstances = new HashMap<VURI, ModelInstance>();
        this.mapping2CorrespondenceInstanceMap = new HashMap<Mapping, InternalCorrespondenceInstance>();

        this.classLoader = classLoader;

        loadVURIsOfVSMUModelInstances();
        loadAndMapCorrepondenceInstances();
    }

    @Override
    public ModelInstance getModelInstanceCopy(final VURI uri) {
        // TODO Auto-generated method stub

        return null;
    }

    /**
     * Supports three cases: 1) get registered 2) create non-existing 3) get unregistered but
     * existing that contains at most a root element without children. But throws an exception if an
     * instance that contains more than one element exists at the uri.
     *
     * DECISION Since we do not throw an exception (which can happen in 3) we always return a valid
     * model. Hence the caller do not have to check whether the retrieved model is null.
     */
    @Override
    public ModelInstance getAndLoadModelInstanceOriginal(final VURI modelURI) {
        final ModelInstance modelInstance = getModelInstanceOriginal(modelURI);
        try {
            final TransactionalEditingDomain transactionalEditingDomain = getTransactionalEditingDomain();
            RecordingCommand recordingCommand = new RecordingCommand(transactionalEditingDomain) {
                @Override
                protected void doExecute() {
                    modelInstance.load(getMetamodelByURI(modelURI).getDefaultLoadOptions());
                }
            };
            transactionalEditingDomain.getCommandStack().execute(recordingCommand);
        } catch (RuntimeException re) {
            // could not load model instance --> this should only be the case when the model is not
            // Existing yet
            logger.info("Exception during loading of model instance " + modelInstance + " occured: " + re, re);
        }
        return modelInstance;
    }

    public ModelInstance getModelInstanceOriginal(final VURI modelURI) {
        ModelInstance modelInstance = this.modelInstances.get(modelURI);
        if (modelInstance == null) {
            final TransactionalEditingDomain transactionalEditingDomain = getTransactionalEditingDomain();
            RecordingCommand recordingCommand = new RecordingCommand(transactionalEditingDomain) {
                @Override
                protected void doExecute() {
                    // case 2 or 3
                    ModelInstance internalModelInstance = getOrCreateUnregisteredModelInstance(modelURI);
                    VSUMImpl.this.modelInstances.put(modelURI, internalModelInstance);
                    saveVURIsOfVSUMModelInstances();
                }
            };
            transactionalEditingDomain.getCommandStack().execute(recordingCommand);
            modelInstance = this.modelInstances.get(modelURI);
        }
        return modelInstance;
    }

    public boolean existsModelInstance(final VURI modelURI) {
        return this.modelInstances.containsKey(modelURI);
    }

    /**
     * Saves the resource for the given vuri. If the VURI is not existing yet it will be created.
     *
     * @param vuri
     *            The VURI to save
     */
    @Override
    public void saveExistingModelInstanceOriginal(final VURI vuri) {
        saveExistingModelInstanceOriginal(vuri, null);
    }

    private void saveExistingModelInstanceOriginal(final VURI vuri,
            final Pair<EObject, TUID> tuidToUpdateWithRootEObjectPair) {
        final TransactionalEditingDomain transactionalEditingDomain = getTransactionalEditingDomain();
        transactionalEditingDomain.getCommandStack().execute(new RecordingCommand(transactionalEditingDomain) {
            @Override
            protected void doExecute() {
                ModelInstance modelInstanceToSave = getModelInstanceOriginal(vuri);
                Metamodel metamodel = getMetamodelByURI(vuri);
                Resource resourceToSave = modelInstanceToSave.getResource();
                try {
                    EcoreResourceBridge.saveResource(resourceToSave, metamodel.getDefaultSaveOptions());
                } catch (IOException e) {
                    throw new RuntimeException("Could not save VURI + " + vuri + ": " + e);
                }
                saveAllChangedCorrespondences(modelInstanceToSave, tuidToUpdateWithRootEObjectPair);
                for (EObject root : modelInstanceToSave.getRootElements()) {
                    metamodel.removeRootFromTUIDCache(root);
                }

            }
        });
    }

    @Override
    public void saveModelInstanceOriginalWithEObjectAsOnlyContent(final VURI vuri, final EObject rootEObject,
            final TUID oldTUID) {
        final ModelInstance modelInstance = getAndLoadModelInstanceOriginal(vuri);
        getTransactionalEditingDomain().getCommandStack()
                .execute(new RecordingCommand(getTransactionalEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        final Resource resource = modelInstance.getResource();
                        // clear the resource first
                        resource.getContents().clear();
                        resource.getContents().add(rootEObject);
                        VSUMImpl.this.saveExistingModelInstanceOriginal(vuri,
                                new Pair<EObject, TUID>(rootEObject, oldTUID));
                    }
                });
    }

    private void saveAllChangedCorrespondences(final ModelInstance modelInstanceToSave,
            final Pair<EObject, TUID> tuidToUpdateNewRootEObjectPair) {
        VURI metamodeURI = modelInstanceToSave.getMetamodeURI();
        Metamodel metamodel = this.metamodelManaging.getMetamodel(metamodeURI);
        Set<InternalCorrespondenceInstance> allCorrespondenceInstances = getOrCreateAllCorrespondenceInstancesForMM(
                metamodel);
        for (InternalCorrespondenceInstance correspondenceInstance : allCorrespondenceInstances) {
            if (null != tuidToUpdateNewRootEObjectPair && tuidToUpdateNewRootEObjectPair.getSecond() != null) {
                correspondenceInstance.update(tuidToUpdateNewRootEObjectPair.getSecond(),
                        tuidToUpdateNewRootEObjectPair.getFirst());
            }
            if (correspondenceInstance.changedAfterLastSave()) {
                if (correspondenceInstance instanceof CorrespondenceInstanceDecorator) {
                    saveCorrespondenceInstanceDecorators((CorrespondenceInstanceDecorator) correspondenceInstance);
                }
                correspondenceInstance.resetChangedAfterLastSave();
            }
        }
    }

    private void saveCorrespondenceInstanceDecorators(final CorrespondenceInstanceDecorator correspondenceInstance) {
        Map<String, Object> fileExtPrefix2ObjectMap = correspondenceInstance.getFileExtPrefix2ObjectMapForSave();
        for (Entry<String, Object> fileExtPrefixAndObject : fileExtPrefix2ObjectMap.entrySet()) {
            String fileExtPrefix = fileExtPrefixAndObject.getKey();
            String fileName = getFileNameForCorrespondenceInstanceDecorator(correspondenceInstance, fileExtPrefix);
            Object object = fileExtPrefixAndObject.getValue();
            FileSystemHelper.saveObjectToFile(object, fileName);
        }
    }

    private String getFileNameForCorrespondenceInstanceDecorator(
            final InternalCorrespondenceInstance correspondenceInstance, final String fileExtPrefix) {
        VURI vuri = correspondenceInstance.getURI();
        VURI newVURI = vuri.replaceFileExtension(fileExtPrefix + VitruviusConstants.getCorrespondencesFileExt());
        return newVURI.toResolvedAbsolutePath();
    }

    private ModelInstance getOrCreateUnregisteredModelInstance(final VURI modelURI) {
        String fileExtension = modelURI.getFileExtension();
        Metamodel metamodel = this.metamodelManaging.getMetamodel(fileExtension);
        if (metamodel == null) {
            throw new RuntimeException("Cannot create a new model instance at the uri '" + modelURI
                    + "' because no metamodel is registered for the file extension '" + fileExtension + "'!");
        }
        return getOrCreateUnregisteredModelInstance(modelURI, metamodel);
    }

    private ModelInstance getOrCreateUnregisteredModelInstance(final VURI modelURI, final Metamodel metamodel) {
        ModelInstance modelInstance = loadModelInstance(modelURI, metamodel);
        getOrCreateAllCorrespondenceInstancesForMM(metamodel);
        return modelInstance;
    }

    private ModelInstance loadModelInstance(final VURI modelURI, final Metamodel metamodel) {
        URI emfURI = modelURI.getEMFUri();
        Resource modelResource = EcoreResourceBridge.loadResourceAtURI(emfURI, this.resourceSet,
                metamodel.getDefaultLoadOptions());
        ModelInstance modelInstance = new ModelInstance(modelURI, modelResource);
        return modelInstance;
    }

    private Collection<Mapping> getAllMappings(final Metamodel metamodel) {
        return this.mappingManaging.getAllMappings(metamodel);
    }

    public Set<InternalCorrespondenceInstance> getOrCreateAllCorrespondenceInstancesForMM(final Metamodel metamodel) {
        Collection<Mapping> mappings = getAllMappings(metamodel);
        Set<InternalCorrespondenceInstance> correspondenceInstances = new HashSet<InternalCorrespondenceInstance>(
                null == mappings ? 0 : mappings.size());
        if (null == mappings) {
            logger.warn("mappings == null. No correspondence instace for MM: " + metamodel + " created."
                    + "Empty correspondence list will be returned");
            return correspondenceInstances;
        }
        for (Mapping mapping : mappings) {
            InternalCorrespondenceInstance correspondenceInstance = this.mapping2CorrespondenceInstanceMap.get(mapping);
            if (correspondenceInstance == null) {
                correspondenceInstance = createAndRegisterCorrespondenceInstance(mapping);
            }
            correspondenceInstances.add(correspondenceInstance);
        }
        return correspondenceInstances;
    }

    private InternalCorrespondenceInstance createAndRegisterCorrespondenceInstance(final Mapping mapping) {
        InternalCorrespondenceInstance correspondenceInstance;
        VURI[] mmURIs = mapping.getMetamodelURIs();
        VURI correspondencesVURI = FileSystemHelper.getCorrespondencesVURI(mmURIs);
        correspondenceInstance = createCorrespondenceInstance(mapping, correspondencesVURI);
        if (correspondenceInstance instanceof CorrespondenceInstanceDecorator) {
            loadAndInitializeCorrespondenceInstanceDecorators((CorrespondenceInstanceDecorator) correspondenceInstance);
        }
        this.mapping2CorrespondenceInstanceMap.put(mapping, correspondenceInstance);
        return correspondenceInstance;
    }

    private InternalCorrespondenceInstance createCorrespondenceInstance(final Mapping mapping,
            final VURI correspondencesVURI) {
        Resource correspondencesResource = this.resourceSet.createResource(correspondencesVURI.getEMFUri());
        return InternalContractsBuilder.createCorrespondenceInstance(mapping, this, correspondencesVURI,
                correspondencesResource);
    }

    private void loadAndInitializeCorrespondenceInstanceDecorators(
            final CorrespondenceInstanceDecorator correspondenceInstance) {
        Set<String> fileExtPrefixesForObjects = correspondenceInstance.getFileExtPrefixesForObjectsToLoad();
        Map<String, Object> fileExtPrefix2ObjectMap = new HashMap<String, Object>();
        for (String fileExtPrefix : fileExtPrefixesForObjects) {
            String fileName = getFileNameForCorrespondenceInstanceDecorator(correspondenceInstance, fileExtPrefix);
            Object loadedObject = FileSystemHelper.loadObjectFromFile(fileName, this.classLoader);
            fileExtPrefix2ObjectMap.put(fileExtPrefix, loadedObject);
        }
        correspondenceInstance.initialize(fileExtPrefix2ObjectMap);
    }

    // @Override
    public ModelInstance getModelInstanceOriginalForImport(final VURI uri) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns the correspondenceInstance for the mapping from the metamodel at the first VURI to
     * the metamodel at the second VURI or the other way round
     *
     * @return the found correspondenceInstance or null if there is none
     */
    @Override
    public InternalCorrespondenceInstance getCorrespondenceInstanceOriginal(final VURI mmAVURI, final VURI mmBVURI) {
        Mapping mapping = this.mappingManaging.getMapping(mmAVURI, mmBVURI);
        if (this.mapping2CorrespondenceInstanceMap.containsKey(mapping)) {
            return this.mapping2CorrespondenceInstanceMap.get(mapping);
        }
        logger.warn("no mapping found for the metamodel at: " + mmAVURI + " and the metamodel at: " + mmBVURI);
        return null;
    }

    /**
     * Returns all correspondences instances for a given VURI. null will be returned. We are not
     * creating new CorrespondenceInstance here, cause we can not guess the linked model. The method
     * {@link getCorrespondenceInstanceOriginal} must be called before to create the appropriate
     * correspondence instance
     *
     * @see edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.CorrespondenceInstance
     * @return set that contains all CorrespondenceInstances for the VURI or null if there is non
     */
    @Override
    public Set<InternalCorrespondenceInstance> getOrCreateAllCorrespondenceInstances(final VURI model1uri) {
        Metamodel metamodelForUri = this.metamodelManaging.getMetamodel(model1uri.getFileExtension());
        return getOrCreateAllCorrespondenceInstancesForMM(metamodelForUri);
    }

    @Override
    public CorrespondenceInstance getCorrespondenceInstanceCopy(final VURI model1uri, final VURI model2uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValidationResult validate(final Invariants invariants) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValidationResult validate(final ModelInstance modelInstance, final Invariants invariants) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValidationResult validate(final ModelInstance modelInstanceA, final ModelInstance modelInstanceB,
            final Invariants invariants) {
        // TODO Auto-generated method stub
        return null;
    }

    private void loadVURIsOfVSMUModelInstances() {
        Set<VURI> vuris = FileSystemHelper.loadVSUMvURIsFromFile();
        for (VURI vuri : vuris) {
            Metamodel metamodel = getMetamodelByURI(vuri);
            ModelInstance modelInstance = loadModelInstance(vuri, metamodel);
            this.modelInstances.put(vuri, modelInstance);
        }
    }

    private void saveVURIsOfVSUMModelInstances() {
        FileSystemHelper.saveVSUMvURIsToFile(this.modelInstances.keySet());
    }

    private Metamodel getMetamodelByURI(final VURI uri) {
        String fileExtension = uri.getFileExtension();
        return this.metamodelManaging.getMetamodel(fileExtension);
    }

    private void loadAndMapCorrepondenceInstances() {
        Metamodel[] metamodels = this.metamodelManaging.getAllMetamodels();
        for (Metamodel metamodel : metamodels) {
            getOrCreateAllCorrespondenceInstancesForMM(metamodel);
        }
    }

    @Override
    public synchronized TransactionalEditingDomain getTransactionalEditingDomain() {
        if (null == TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet)) {
            attachTransactionalEditingDomain();
        }
        return TransactionalEditingDomain.Factory.INSTANCE.getEditingDomain(this.resourceSet);
    }

    private void attachTransactionalEditingDomain() {
        TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(this.resourceSet);
    }

    @Override
    public void detachTransactionalEditingDomain() {
        TransactionalEditingDomain domain = TransactionalEditingDomain.Factory.INSTANCE
                .getEditingDomain(this.resourceSet);
        if (domain != null) {
            domain.dispose();
        }
    }

    @Override
    public void deleteModelInstanceOriginal(final VURI vuri) {
        final ModelInstance modelInstance = getModelInstanceOriginal(vuri);
        final Resource resource = modelInstance.getResource();
        getTransactionalEditingDomain().getCommandStack()
                .execute(new RecordingCommand(getTransactionalEditingDomain()) {
                    @Override
                    protected void doExecute() {
                        try {
                            resource.delete(null);
                            VSUMImpl.this.modelInstances.remove(modelInstance);
                        } catch (final IOException e) {
                            logger.info("Deletion of resource " + resource + " did not work. Reason: " + e);
                        }
                    }
                });
    }
}
