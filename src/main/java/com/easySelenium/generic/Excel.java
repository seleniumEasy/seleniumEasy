package com.easySelenium.generic;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.easySelenium.common.CommonConstant;

public class Excel 
{
	static HSSFWorkbook workbook=null;
	static HSSFSheet firstSheet=null;
	
	static Excel xl=new Excel();
	
	public static int getRowCount(String path,String sheet)	{
		int r=0;
		try{
			FileInputStream file=new FileInputStream(path);
			r=WorkbookFactory.create(file).getSheet(sheet).getLastRowNum();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public static String getCellValue(String path,String sheet,int r,int c){
		String v="";
		try{
			v=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getRow(r).getCell(c).toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return v;
	}

	public void setReference(HSSFWorkbook workbook,HSSFSheet firstSheet){
		this.workbook=workbook;
		this.firstSheet=firstSheet;
	}
	
	public static void createExcelFile(){
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet firstSheet=workbook.createSheet("First Sheet");
		Row headerRow=firstSheet.createRow(0);
		headerRow.setHeightInPoints(40);
		xl.setReference(workbook,firstSheet);		
	}
	
	
	public static void setStatus(String name,String value) throws IOException{
		
		int row=firstSheet.getLastRowNum();
		Row newrow=firstSheet.createRow(row+1);
		
		newrow.createCell(0).setCellValue(name);
		newrow.createCell(1).setCellValue(value);
	
		
		FileOutputStream fos=new FileOutputStream(new File(CommonConstant.REPORT_PATH+CommonConstant.EXCEL_REPORT));
		HSSFCellStyle hsfstyle=workbook.createCellStyle();
//	    hsfstyle.setBorderBottom((short) 1);
//	    hsfstyle.setFillBackgroundColor((short)245);
	    workbook.write(fos);
	    workbook.close();
	    fos.flush();
	    fos.close();
		
	}
}
