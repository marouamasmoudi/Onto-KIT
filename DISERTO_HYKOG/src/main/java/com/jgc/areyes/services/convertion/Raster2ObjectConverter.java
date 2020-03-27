package com.jgc.areyes.services.convertion;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;

import com.jgc.areyes.services.Converter;
import com.jgc.areyes.services.ConvertionContext;
import com.jgc.areyes.services.ConvertionResult;

/**
 * @author masmoudi
 *
 */
public class Raster2ObjectConverter implements Converter {

	private File fileHDR;
	static {

		System.out.println("GDAL init...");
		gdal.AllRegister();
		int count = gdal.GetDriverCount();
		System.out.println(count + " available Drivers");
		for (int i = 0; i < count; i++) {
			try {
				Driver driver = gdal.GetDriver(i);
				// System.out.println(" " + driver.getShortName() + " : "
				// + driver.getLongName());
			} catch (Exception e) {
				System.err.println("Error loading driver " + i);
			}
		}
	}

	private static Raster2ObjectConverter INSTANCE;

	public static Raster2ObjectConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Raster2ObjectConverter();
		}
		return INSTANCE;
	}

	private Raster2ObjectConverter() {
	}

	@Override
	public ConvertionResult convert(ConvertionContext convertionContext) {
		ConvertionResult result = new ConvertionResult();
		String filePath = convertionContext.getFilePath();
		result.setMds(openFile(new File(filePath)));
		result.setSource(convertionContext.getSource());
		result.setRdfQuadFilePath(convertionContext.getRdfQuadFilePath());
		return result;
	}

	private String openFile(File file) {
		String property = null;
		Dataset poDataset = null;

		try {
			poDataset = (Dataset) gdal.Open(file.getAbsolutePath(),
					gdalconst.GA_ReadOnly);
			if (poDataset == null) {
				System.out.println("The image could not be read.");
				printLastError();
				return null;
			}
		} catch (Exception e) {
			System.err.println("Exception caught.");
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		Vector<String> files = poDataset.GetFileList();
		for (String fileN : files) {
			if (fileN.endsWith("hdr")) {
				fileHDR = new File(fileN);
				System.out.println(fileHDR);
				try {
					List<String> lines = FileUtils.readLines(fileHDR);
					property = (String) lines.stream()
							.filter(x -> x.startsWith("values")) // Choisir que
							// les
							// lignes
							// commencant
							// par le
							// mot
							// values (x
							// est
							// la ligne)
							.map(line -> getConcept(line)) // Chercher le
							// concept
							.distinct() // supprimer les concepts dupliqu�s
							.findFirst().get();

					System.out.println(property);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return property;

	}

	private void printLastError() {
		System.out.println("Last error: " + gdal.GetLastErrorMsg());
		System.out.println("Last error no: " + gdal.GetLastErrorNo());
		System.out.println("Last error type: " + gdal.GetLastErrorType());
	}

	private String getConcept(String line) {
		return line.substring(line.indexOf("{") + 1, line.indexOf(","));
	}

}
