package com.jgc.areyes.services;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import static com.jgc.areyes.services.Constants.CSV_SOURCE;

/**
 * 
 * @author masmoudi
 *
 */
public class ExtractorServiceImpl implements ExtractorService {

	// @Override
	public ConvertionResult extractData(File file) {

		ConvertionResult result = new ConvertionResult();

		// 1. prepare input context
		ConvertionContext context = prepareContext(file);

		// 2. resolve input
		Converter converter = InputResolver.getInstance().resolve(context); // find
																			// which
																			// converter
																			// to
																			// use

		// 3. Conversion

		result = converter.convert(context);

	
		return result;
	}

	private ConvertionContext prepareContext(File file) {
		ConvertionContext context = new ConvertionContext();
		String fileName = file.getName();

		context.setBaseName(FilenameUtils.getBaseName(fileName));
		String extension = FilenameUtils.getExtension(fileName);
		context.setExtension(extension);
		if (extension.equalsIgnoreCase("csv")) {
			context.setFileDelimiter(',');
			context.setSource(CSV_SOURCE);
		} else {
			context.setSource("OSS");

		}
		context.setFile(file);
		context.setFilePath(file.getAbsolutePath());
		context.setRdfQuadFilePath(file.getParent() + "\\RDFquad.ttl");

		return context;
	}

}
