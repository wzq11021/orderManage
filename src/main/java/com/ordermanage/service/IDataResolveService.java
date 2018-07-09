package com.ordermanage.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IDataResolveService {
	
	HSSFWorkbook dataResolve(String data)throws JsonParseException, JsonMappingException, IOException ;

}
