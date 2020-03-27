package com.jgc.areyes.services.convertion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import com.csvreader.CsvReader;
import com.jgc.areyes.services.Converter;
import com.jgc.areyes.services.ConvertionContext;
import com.jgc.areyes.services.ConvertionResult;

/**
 * 
 * @author masmoudi
 *
 */
public class Csv2MetadataExtractor implements Converter {

	private static Csv2MetadataExtractor INSTANCE;

	public static Csv2MetadataExtractor getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Csv2MetadataExtractor();
		}
		return INSTANCE;
	}

	private Csv2MetadataExtractor() {
	}

	@Override
	public ConvertionResult convert(ConvertionContext convertionContext) {

		ConvertionResult result = new ConvertionResult();
		// add parser
		CsvReader reader;
		try {
			reader = new CsvReader(new FileInputStream(
					convertionContext.getFilePath()),
					convertionContext.getFileDelimiter(),
					Charset.defaultCharset());
			// reader.setDelimiter(convertionContext.getFileDelimiter());
			reader.setSafetySwitch(false);

			reader.readHeaders();
			int i=0;
			String[] headers = reader.getHeaders();
			for (String header : headers) {
				if (i==0) {
					result.setMds(header);
				}
				else{
					result.putEntity(header, null);
				}
				i++;
			}

			//result.setMds(convertionContext.getBaseName()); // set the file name into the
			// variable Mds
			result.setSource(convertionContext.getSource());
			result.setRdfQuadFilePath(convertionContext.getRdfQuadFilePath());
			System.out.println("metadata extracted from csv file");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
