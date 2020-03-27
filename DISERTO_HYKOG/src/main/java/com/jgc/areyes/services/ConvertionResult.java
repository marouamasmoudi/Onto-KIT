package com.jgc.areyes.services;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * The class that contains the result of the extractor service (metadata)
 * 
 * @author masmoudi
 *
 */
public class ConvertionResult {

	
	private Map<String, Object> entities = new HashMap<>();
	
	private String mds; //subject of the RML mapping

	private File originFile; 
	
	private String source; //source of the data input (oss , noaa)
	
	private String rdfQuadFilePath;
	/**
	 * @return
	 */
	public Map<String, Object> getEntities() {
		return entities;
	}

	public void setEntities(Map<String, Object> entities) {
		this.entities = entities;
	}

	public void putEntity(String key, Object value){
		entities.put(key, value);
	}
	
	public String getMds() {
		return mds;
	}

	public void setMds(String mds) {
		this.mds = mds;
	}

	public File getOriginFile() { 
		return originFile;
	}

	public void setOriginFile(File originFile) {
		this.originFile = originFile;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRdfQuadFilePath() {
		return rdfQuadFilePath;
	}

	public void setRdfQuadFilePath(String rdfQuadFilePath) {
		this.rdfQuadFilePath = rdfQuadFilePath;
	}
	
	
	

	
	
}
