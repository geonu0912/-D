package com.sp.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.common.dao.CommonDAO;

@Service("service.commandService")
public class CommandServiceImpl implements CommandService {
	@Autowired
	private CommonDAO dao;
	
	@Override
	public void insertCommand(String id) throws Exception {
		try {
			dao.insertData(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void insertCommand(String id, Map<String, Object> map) throws Exception {
		try {
			dao.insertData(id, map);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void insertCommand(String id, Object value) throws Exception {
		try {
			dao.insertData(id, value);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int intValue(String id) {
		int result=0;
		try {
			result=dao.selectOne(id);
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public int intValue(String id, Map<String, Object> map) {
		int result=0;
		try {
			result=dao.selectOne(id, map);
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public int intValue(String id, Object value) {
		int result=0;
		try {
			result=dao.selectOne(id, value);
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public <T> List<T> listCommand(String id) {
		List<T> list=null;
		try {
			list=dao.selectList(id);
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public <T> List<T> listCommand(String id, Map<String, Object> map) {
		List<T> list=null;
		try {
			list=dao.selectList(id, map);
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public <T> List<T> listCommand(String id, Object value) {
		List<T> list=null;
		try {
			list=dao.selectList(id, value);
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public <T> T readCommand(String id) {
		T dto=null;
		try {
			dto = dao.selectOne(id);
		} catch (Exception e) {
		}
		return dto;
	}

	@Override
	public <T> T readCommand(String id, Map<String, Object> map) {
		T dto=null;
		try {
			dto = dao.selectOne(id, map);
		} catch (Exception e) {
		}
		return dto;
	}

	@Override
	public <T> T readCommand(String id, Object value) {
		T dto=null;
		try {
			dto = dao.selectOne(id, value);
		} catch (Exception e) {
		}
		return dto;
	}

	@Override
	public void updateCommand(String id) throws Exception {
		try {
			dao.updateData(id);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateCommand(String id, Map<String, Object> map) throws Exception {
		try {
			dao.updateData(id, map);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateCommand(String id, Object value) throws Exception {
		try {
			dao.updateData(id, value);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteCommand(String id) throws Exception {
		try {
			dao.deleteData(id);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void deleteCommand(String id, Map<String, Object> map) throws Exception {
		try {
			dao.deleteData(id, map);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteCommand(String id, Object value) throws Exception {
		try {
			dao.deleteData(id, value);
		} catch (Exception e) {
			throw e;
		}
	}

}
