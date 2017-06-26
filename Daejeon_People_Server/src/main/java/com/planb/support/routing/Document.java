package com.planb.support.routing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.planb.support.utilities.Log;

public class Document {
	private XSSFWorkbook wb;

	public Document(List<RESTResource> resourceList) {
		String fileName = "API Document.xlsx";
		wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("API");
		
		XSSFRow rowHead = sheet.createRow(0);
		rowHead.createCell(0).setCellValue("Function Category");
		rowHead.createCell(1).setCellValue("Summary");
		rowHead.createCell(2).setCellValue("Method");
		rowHead.createCell(3).setCellValue("URI");
		rowHead.createCell(4).setCellValue("Request Header");
		rowHead.createCell(5).setCellValue("Params");
		rowHead.createCell(6).setCellValue("Request Body");
		rowHead.createCell(7).setCellValue("Success Code");
		rowHead.createCell(8).setCellValue("Response Header");
		rowHead.createCell(9).setCellValue("Response Body");
		rowHead.createCell(10).setCellValue("Failure Code");
		rowHead.createCell(11).setCellValue("ETC");
		
		int rowCount = 1;
		
		for(RESTResource resource : resourceList) {
			XSSFRow row = sheet.createRow(rowCount++);
			
			row.createCell(0).setCellValue(resource.getFunctionCategory());
			row.createCell(1).setCellValue(resource.getSummary());
			row.createCell(2).setCellValue(resource.getMethod());
			row.createCell(3).setCellValue(resource.getUri());
			row.createCell(4).setCellValue(getKeyValueStr(resource.getRequestHeaders()));
			row.createCell(5).setCellValue(getKeyValueStr(resource.getParams()));
			row.createCell(6).setCellValue(getKeyValueStr(resource.getRequestBody()));
			row.createCell(7).setCellValue(resource.getSuccessCode());
			row.createCell(8).setCellValue(getKeyValueStr(resource.getResponseHeaders()));
			row.createCell(9).setCellValue(getKeyValueStr(resource.getResponseBody()));
			row.createCell(10).setCellValue(resource.getFailureCode());
			row.createCell(11).setCellValue(resource.getEtc());
		}
		
		try {
			wb.write(new FileOutputStream(fileName));
			Log.info("REST Resource Documentation Complete");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getKeyValueStr(String s) {
		StringBuilder builder = new StringBuilder();
		String[] requestHeaders = s.split(",");
		for(String requestHeader : requestHeaders) {
			builder.append(requestHeader.trim()).append("\n");
		}
		
		return builder.toString().substring(0, builder.toString().length() - 1);
	}
}
