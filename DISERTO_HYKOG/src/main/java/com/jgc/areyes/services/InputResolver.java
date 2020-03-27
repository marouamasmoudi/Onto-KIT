package com.jgc.areyes.services;

import com.jgc.areyes.services.convertion.Csv2MetadataExtractor;
import com.jgc.areyes.services.convertion.Json2MetadataExtractor;
import com.jgc.areyes.services.convertion.Raster2ObjectConverter;

import static com.jgc.areyes.services.Constants.*;
/**
 * 
 * @author masmoudi
 *
 */
public class InputResolver {

private static InputResolver INSTANCE;
	
	public static InputResolver getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new InputResolver();
		}
		return INSTANCE;
	}
	
	private InputResolver(){}
	
	public Converter resolve(ConvertionContext context){
		
	String extension = context.getExtension();
	
		if (JSON_EXTENSION.equals(extension)) {
			return Json2MetadataExtractor.getInstance();
			
		}else if (CSV_EXTENSION.equals(extension)) {
			return Csv2MetadataExtractor.getInstance();
			
		}else if (XML_EXTENSION.equals(extension)) {
			return Csv2MetadataExtractor.getInstance();
		}
		
		else if (IMG_EXTENSION.equals(extension)) {
			return Raster2ObjectConverter.getInstance();
		}
		
		return null;
	}
}
