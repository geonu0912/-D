package com.sp.service;

import java.util.List;
import java.util.Map;

public interface CommandService {
	
	public void insertCommand(String id, Map<String,Object> map) throws Exception; 
	public void insertCommand(String id, Object value) throws Exception;
	public void insertCommand(String id) throws Exception;
	
	public void updateCommand(String id, Map<String,Object> map) throws Exception; 
	public void updateCommand(String id, Object value) throws Exception; 
	public void updateCommand(String id) throws Exception; 
	
	public void deleteCommand(String id, Map<String,Object> map) throws Exception; 
	public void deleteCommand(String id, Object value) throws Exception; 
	public void deleteCommand(String id) throws Exception; 
	
	public int intValue(String id, Map<String,Object> map) throws Exception;
	public int intValue(String id, Object value) throws Exception;
	public int intValue(String id) throws Exception;
	
	public <T> List<T> listCommand(String id, Map<String, Object> map) throws Exception;
	public <T> List<T> listCommand(String id, Object value) throws Exception;
	public <T> List<T> listCommand(String id) throws Exception;

	public <T> T readCommand(String id, Map<String, Object> map) throws Exception;
	public <T> T readCommand(String id, Object value) throws Exception;
	public <T> T readCommand(String id) throws Exception;
}
