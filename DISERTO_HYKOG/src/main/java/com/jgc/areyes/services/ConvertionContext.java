package com.jgc.areyes.services;

import java.io.File;
/**
 * The class that describe the input file (Name, extension,etc.)
 * 
 * @author masmoudi
 *
 */
public class ConvertionContext {

	
	private File file;
	
	private String baseFileName;
	
	private String extension;
	
	private String filePath;
	
	private char fileDelimiter;
	
	private String source;
	
	private String rdfQuadFilePath;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getBaseName() {
		return baseFileName;
	}

	public void setBaseName(String baseName) {
		this.baseFileName = baseName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public char getFileDelimiter() {
		return fileDelimiter;
	}

	public void setFileDelimiter(char fileDelimiter) {
		this.fileDelimiter = fileDelimiter;
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
