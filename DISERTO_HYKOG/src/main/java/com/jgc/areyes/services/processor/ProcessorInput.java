package com.jgc.areyes.services.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jgc.areyes.services.ConvertionResult;
import com.jgc.areyes.services.processor.unit.UnitCommandProcess;

/**
 * 
 * @author masmoudi
 *
 */
public class ProcessorInput {

	private final Map<String, Object> entities; // metadata

	private final String OntologyPath;
	private final String thesaurusPath;
	private final String mds;
	private final String source;
	private final String rdfQuadFilePath;
	private String rmlFilePath;
	private final String inputFilePath;
	private int hyperNodeCount;

	private List<UnitCommandProcess> listCommandProcess = new ArrayList<UnitCommandProcess>();

	/**
	 * @param result
	 * @param ontologyPath
	 * @param thesaurusPath
	 */
	public ProcessorInput(ConvertionResult result, String ontologyPath,
			String thesaurusPath, String inputFilePath) {
		super();
		this.entities = result.getEntities();
		this.mds = result.getMds();
		this.source = result.getSource();
		this.OntologyPath = ontologyPath;
		this.rdfQuadFilePath = result.getRdfQuadFilePath();
		this.thesaurusPath = thesaurusPath;
		this.inputFilePath = inputFilePath;
	}

	public List<UnitCommandProcess> getListCommandProcess() {
		return listCommandProcess;
	}

	public void setListCommandProcess(
			List<UnitCommandProcess> listCommandProcess) {
		this.listCommandProcess = listCommandProcess;
	}

	public void addUnitCommandProcess(UnitCommandProcess process) {
		listCommandProcess.add(process);
	}

	public Map<String, Object> getEntities() {
		return entities;
	}

	public String getMds() {
		return mds;
	}

	public String getOntologyPath() {
		return OntologyPath;
	}

	public String getSource() {
		return source;
	}

	public String getRdfQuadFilePath() {
		return rdfQuadFilePath;
	}

	public String getThesaurusPath() {
		return thesaurusPath;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public String getRmlFilePath() {
		return rmlFilePath;
	}

	public void setRMLFilePath(String rmlFilePath) {
		this.rmlFilePath = rmlFilePath;
	}

	public int getHyperNodeCount() {
		return hyperNodeCount;
	}

	public void setHyperNodeCount(int hyperNodeCount) {
		this.hyperNodeCount = hyperNodeCount;
	}
}
