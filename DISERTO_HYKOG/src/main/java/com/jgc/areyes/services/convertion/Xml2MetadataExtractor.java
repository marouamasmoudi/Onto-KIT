package com.jgc.areyes.services.convertion;

import com.jgc.areyes.services.Converter;
import com.jgc.areyes.services.ConvertionContext;
import com.jgc.areyes.services.ConvertionResult;
/**
 * 
 * @author masmoudi
 *
 */
public class Xml2MetadataExtractor implements Converter {

	private static Xml2MetadataExtractor INSTANCE;
	
	public static Xml2MetadataExtractor getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new Xml2MetadataExtractor();
		}
		return INSTANCE;
	}
	
	private Xml2MetadataExtractor(){}
	
	@Override
	public ConvertionResult convert(ConvertionContext convertionContext) {

		ConvertionResult result = new ConvertionResult();
		
		System.out.println("extraction xml file");

		return result;
	}



}
