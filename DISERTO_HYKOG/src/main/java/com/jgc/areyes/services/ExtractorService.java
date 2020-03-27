package com.jgc.areyes.services;

import java.io.File;
/**
 * Service to extract metadata from the uploaded file 
 * 
 * @author masmoudi
 *
 */
public interface ExtractorService {

	
	public ConvertionResult extractData(File file);
	
}
