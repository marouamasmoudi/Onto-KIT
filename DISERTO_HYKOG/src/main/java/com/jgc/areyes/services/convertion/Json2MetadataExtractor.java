package com.jgc.areyes.services.convertion;

import com.jgc.areyes.services.ConvertionContext;
import com.jgc.areyes.services.ConvertionResult;
import com.jgc.areyes.services.Converter;
/**
 * 
 * @author masmoudi
 *
 */
public class Json2MetadataExtractor implements Converter {

	private static Json2MetadataExtractor INSTANCE;
	
	public static Json2MetadataExtractor getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new Json2MetadataExtractor();
		}
		return INSTANCE;
	}
	
	private Json2MetadataExtractor(){}
	
	@Override
	public ConvertionResult convert(ConvertionContext convertionContext) {

		ConvertionResult result = new ConvertionResult();
		
		System.out.println("extraction json file");

		return result;
	}





}
