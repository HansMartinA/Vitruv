package tools.vitruv.dsls.mapping.generator

import tools.vitruv.dsls.mapping.mappingLanguage.ConstraintExpression
import tools.vitruv.dsls.mapping.mappingLanguage.DefaultContainExpression
import tools.vitruv.dsls.mapping.mappingLanguage.Mapping
import tools.vitruv.dsls.mapping.mappingLanguage.MappingFile
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors

import static extension tools.vitruv.framework.util.bridges.JavaHelper.*
import static extension tools.vitruv.dsls.mapping.helpers.MappingLanguageHelper.*
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport
import org.eclipse.emf.ecore.EPackage
import tools.vitruv.dsls.mirbase.mirBase.NamedMetaclassReference

@Accessors(PUBLIC_GETTER)
class MappingLanguageGeneratorState {
	private List<Mapping> mappings
	private List<MetamodelImport> imports
	private Map<Mapping, List<MetamodelImport>> mappingToImports
	private Map<Mapping, Map<MetamodelImport, List<NamedMetaclassReference>>> mappingToImportToModelElements
	private Map<Mapping, Map<MetamodelImport, List<ConstraintExpression>>> mappingToImportToConstraints

	public new(MappingFile file) {
		this.imports = file.imports
		this.mappings = file.eAllContents.filter(Mapping).toList.sortWith [ m1, m2 |
			if(m2.requires.exists[it.mapping.equals(m1)]) (-1) else (1)
		]
		
		mappingToImports = newHashMap
		mappingToImportToModelElements = newHashMap
		mappingToImportToConstraints = newHashMap
		for (mapping : mappings) {
			val importToModelElementes = newHashMap
			val importToConstraints = newHashMap
			for (imp : imports) {
				val modelElements = mapping.allSignatureElements.filterWithPackage(imp.package).toList
				importToModelElementes.put(imp, modelElements)
				val constraints = mapping.allConstraintExpressions.filterWithPackage(imp.package).toList
				importToConstraints.put(imp, constraints)
				
				if (!(modelElements.empty && constraints.empty)) {
					mappingToImports.getOrPut(mapping, [newArrayList]).add(imp)
				}
			}
			mappingToImportToModelElements.put(mapping, importToModelElementes)
			mappingToImportToConstraints.put(mapping, importToConstraints)
		}
	}
	
	public def getModelElements(Mapping mapping, MetamodelImport imp) {
		return mappingToImportToModelElements.get(mapping)?.get(imp) ?: #[]
	}
	
	public def getConstraints(Mapping mapping, MetamodelImport imp) {
		return mappingToImportToConstraints.get(mapping)?.get(imp) ?: #[]
	}
	
	public def getDefaultContainments(Mapping mapping, MetamodelImport imp) {
		return mappingToImportToConstraints.get(mapping)?.get(imp)?.filter(DefaultContainExpression) ?: #[]
	}
	
	public def getImports(Mapping mapping) {
		return mappingToImports.get(mapping) ?: #[]
	}
	
	public def getImportsForPackage(EPackage ePackage) {
		return imports.findFirst[it.package.equals(ePackage)]
	}
	
	public def getOtherImport(MetamodelImport imp) {
		val index = imports.indexOf(imp)
		if ((index == -1) || (imports.size != 2)) {
			return null
		} else {
			return imports.get(1 - index);
		}
	}
	
	public def claimOneImport(Mapping mapping) {
		return mappingToImports.get(mapping).claimExactlyOne
	}
}