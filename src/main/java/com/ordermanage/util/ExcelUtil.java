package com.ordermanage.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//Excel
public class ExcelUtil {
	private final static String excel2003=".xls";
	private final static String excel2007=".xlsx";
	
	/**
	 * 根据文件后缀，自适应文件版本
	 * @param in
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public Workbook getWorkBook(InputStream in,String fileName) throws Exception{
		Workbook wb=null;
		fileName=fileName.substring(fileName.lastIndexOf("."));
		if(excel2003.equals(fileName)){
			wb=new HSSFWorkbook(in);
		}else if(excel2007.equals(fileName)){
			wb=new XSSFWorkbook(in);
		}else{
			throw new Exception("解析格式有误");
		}
		return wb;
	}
	/**
	 * 导出Excel
	 * @param sheetName
	 * @param title
	 * @param values
	 * @return
	 */
	public HSSFWorkbook getExcel(String sheetName,String[] title,String[][] values){
		HSSFWorkbook wb=new HSSFWorkbook();
		
		//创建表
		HSSFSheet sheet=wb.createSheet(sheetName);
		
		//第一行
		HSSFRow row=sheet.createRow(0);
		
		//创建单元格，设置表头居中
		HSSFCellStyle style=wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		//列对象
		HSSFCell cell=null;
		
		//标题
		for(int i=0;i<title.length;i++){
			String t=title[i];
			cell=row.createCell(i);
			cell.setCellValue(t);
			cell.setCellStyle(style);
		}
		
		//值
		for(int i = 0;i<values.length;i++){
			row=sheet.createRow(i+1);
			for(int j=0;j<values[i].length;j++){
				cell=row.createCell(j);
				cell.setCellValue(values[i][j]);
			}
			
		}
		return wb;
	}
	/**
	 * 获取Excel表中数据
	 * @param in 输入流
	 * @param fileName 文本名
	 * @return
	 * @throws Exception
	 */
	public List<List<Object>> getListByExcel(InputStream in,String fileName) throws Exception{
		//创建工作簿
		Workbook wb=this.getWorkBook(in, fileName);
		if(wb==null){
			throw new Exception("创建工作簿为空");
		}
		Sheet sheet=null;
		Row row=null;
		Cell cell=null;
		
		List<List<Object>> list=new ArrayList();
		//遍历工作簿
		for(int i=0;i<wb.getNumberOfSheets();i++){
			sheet=wb.getSheetAt(i);
			if(sheet==null){
				continue;
			}
			//遍历所有行
			for(int j=sheet.getFirstRowNum();j<=sheet.getLastRowNum();j++){
				row=sheet.getRow(j);
				if(row==null){
					continue;
				}
				//遍历所有列
				List<Object> li=new ArrayList<>();
				for(int k=row.getFirstCellNum();k<row.getLastCellNum();k++){
					cell=row.getCell(k);
					li.add(this.getCellValue(cell));
				}
				list.add(li);
			}
		}
		return list;
	}
	
	 /** 
   * 描述：对表格中数值进行格式化 
   * @param cell 
   * @return 
   */  
  public  Object getCellValue(Cell cell){  
      Object value = null;  
      DecimalFormat df = new DecimalFormat("0");  //格式化number String字符  
      SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化  
      DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字  
        
      switch (cell.getCellType()) {  
	        case Cell.CELL_TYPE_STRING:  
	            value = cell.getRichStringCellValue().getString();  
	            break;  
	        case Cell.CELL_TYPE_NUMERIC:  
	            if("General".equals(cell.getCellStyle().getDataFormatString())){  
	                value = df.format(cell.getNumericCellValue());  
	            }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){  
	                value = sdf.format(cell.getDateCellValue());  
	            }else{  
	                value = df2.format(cell.getNumericCellValue());  
	            }  
	            break;  
	        case Cell.CELL_TYPE_BOOLEAN:  
	            value = cell.getBooleanCellValue();  
	            break;  
	        case Cell.CELL_TYPE_BLANK:  
	            value = "";  
	            break;  
	        default:  
	            break;  
      }  
      return value;  
  }  
}
