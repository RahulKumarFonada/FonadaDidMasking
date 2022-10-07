package com.fonada.masking.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fonada.masking.common.Constants;
import com.fonada.masking.entity.InComingDidEntity;
import com.fonada.masking.exceptions.BaseException;
import com.fonada.masking.exceptions.FileUploadException;

import au.com.bytecode.opencsv.CSVReader;

public class CSVHelper {
	private static final Logger log = LoggerFactory.getLogger(CSVHelper.class);
	public static String TYPE = "text/csv";
//	static String[] HEADERs = { "app", "Title", "Description", "Published" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static String TYPEEXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPEEXCEL.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static Iterable<CSVRecord> readToCSV(InputStream is) {
		Iterable<CSVRecord> csvRecords = null;
		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			CSVParser csvParser = new CSVParser(fileReader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			csvRecords = csvParser.getRecords();

		} catch (IOException e) {
			throw new RuntimeException("Fail to parse CSV file: " + e.getMessage());
		}
		return csvRecords;
	}

	@SuppressWarnings("unchecked")
	public static List<String[]> convertToCSVStringList(InputStream is) throws BaseException {

		try {
			CSVReader reader = new CSVReader(new InputStreamReader(is), CSVReader.DEFAULT_SEPARATOR);
			List<String[]> csvEntries = reader.readAll();
			/*
			 * BufferedReader fileReader = new BufferedReader(new InputStreamReader(is,
			 * "UTF-8")); CSVParser csvParser = new CSVParser(fileReader,
			 * CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
			 * ); csvRecords = csvParser.getRecords();
			 */
			//validateCsv(csvEntries, Constants.DID_MASKING_UPLOAD_SCHEMA_LENGTH);
			return csvEntries;
		} catch (IOException e) {
			throw new BaseException("Fail To Parse Given File." + e.getMessage());

		}
	}

	public static ByteArrayInputStream writeInCSV(List<InComingDidEntity> tutorials) {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (InComingDidEntity tutorial : tutorials) {
				List<String> data = Arrays.asList(
				/*
				 * String.valueOf(tutorial.getId()), tutorial.getTitle(),
				 * tutorial.getDescription(), String.valueOf(tutorial.isPublished())
				 */
				);

				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}

	private static void validateCsv(List<String[]> lines, Integer schemaNumberOfColumns) throws FileUploadException {
		if (lines.isEmpty()) {
			log.info("***** VALIDATE CSV *****File cannot be empty");
			throw new FileUploadException(HttpStatus.BAD_REQUEST, "File cannot be empty");
		}
		validateHeader(lines.get(0), schemaNumberOfColumns);
	}

	public static String validateHeader(String[] row, Integer schemaNumberOfColumns) throws FileUploadException {
		log.info("****** Validate CSV HEADER ******");
		String response = "";
		if (row.length < schemaNumberOfColumns) {
			response = "Please check the number of mandatory columns";
			log.info("***** VALIDATE EHEADER *****::" + response);

			// throw new FileUploadException(HttpStatus.BAD_REQUEST, "Please check the
			// number of mandatory columns");
		} else {
			response = "Header Success";
		}
		return response;
	}

}
