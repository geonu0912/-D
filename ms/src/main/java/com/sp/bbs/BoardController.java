package com.sp.bbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp.common.MyUtil;
import com.sp.common.service.CommandService;

@Controller("bbs.boardController")
public class BoardController {
	@Autowired
	private CommandService service;
	
	@Autowired
	private MyUtil util;
	
	@RequestMapping(value="/bbs/list")
	public String list(HttpServletRequest req,
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(value="searchKey", defaultValue="subject") String searchKey,
			@RequestParam(value="searchValue", defaultValue="") String searchValue,
			Model model
			) throws Exception {

   	    String cp = req.getContextPath();
   	    
		int rows = 10;  // 한 화면에 보여주는 게시물 수
		int total_page = 0;
		int dataCount = 0;
   	    
		if(req.getMethod().equalsIgnoreCase("GET")) { // GET 방식인 경우
			searchValue = URLDecoder.decode(searchValue, "utf-8");
		}

        // 전체 페이지 수
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchKey", searchKey);
        map.put("searchValue", searchValue);

        dataCount = service.intValue("bbs.dataCount", map);
        if(dataCount != 0)
            total_page = util.pageCount(rows,  dataCount) ;

        // 다른 사람이 자료를 삭제하여 전체 페이지수가 변화 된 경우
        if(total_page < current_page) 
            current_page = total_page;

        // 리스트에 출력할 데이터를 가져오기
        int start = (current_page - 1) * rows;
        if(start<0) start=0;
        map.put("rows", rows);
        map.put("start", start);

        // mysql은 오라클과 다르게 컬럼명이 대문자로 변환되지 않고 쿼리에 작성한 데로 map 키로 저장된다.
        List<Map<String, Object>> listMap = service.listCommand("bbs.listBoard",map);

        // 리스트의 번호
        int listNum, n = 0;
        for(Map<String, Object> m : listMap) {
            listNum = dataCount - (start + n);
            m.put("listNum", listNum);
            n++;
        }

        String query = "";
        String listUrl;
        String articleUrl;
        if(searchValue.length()!=0) {
        	query = "searchKey=" +searchKey + 
        	             "&searchValue=" + URLEncoder.encode(searchValue, "utf-8");	
        }
        
    	listUrl = cp+"/bbs/list";
        articleUrl = cp+"/bbs/article?page=" + current_page;
        if(query.length()!=0) {
        	listUrl = listUrl + "?" + query;
            articleUrl = articleUrl + "&"+ query;
        }
        
        String paging = util.paging(current_page, total_page, listUrl);

        model.addAttribute("listMap", listMap);
        model.addAttribute("articleUrl", articleUrl);
        model.addAttribute("page", current_page);
        model.addAttribute("total_page", total_page);
        model.addAttribute("dataCount", dataCount);
        model.addAttribute("paging", paging);
		
        return "bbs/list";
	}
	
	@RequestMapping(value="/bbs/created", method=RequestMethod.GET)
	public String createdForm(Model model) throws Exception {
		model.addAttribute("mode", "created");
		return "bbs/created";
	}
	
	@RequestMapping(value="/bbs/created", method=RequestMethod.POST)
	public String createdSubmit(
			         @RequestParam Map<String, Object> paramMap,
			         HttpServletRequest req) throws Exception {
		
		paramMap.put("ipAddr", req.getRemoteAddr());
		service.insertCommand("bbs.insertBoard", paramMap);
		
		return "redirect:/bbs/list";
	}

	@RequestMapping(value="/bbs/article")
	public String article(
			@RequestParam(value="num") int num,
			@RequestParam(value="page") String page,
			@RequestParam(value="searchKey", defaultValue="subject") String searchKey,
			@RequestParam(value="searchValue", defaultValue="") String searchValue,
			Model model) throws Exception {
		
		searchValue = URLDecoder.decode(searchValue, "utf-8");
		
		String query="page="+page;
		if(searchValue.length()!=0) {
			query+="&searchKey="+searchKey+"&searchValue="+URLEncoder.encode(searchValue, "UTF-8");
		}
		service.updateCommand("bbs.updateHitCount", num);

		// 해당 레코드 가져 오기
		Map<String, Object> resultMap = service.readCommand("bbs.readBoard", num);
		if(resultMap==null)
			return "redirect:/bbs/list?"+query;
		
		String str=((String)resultMap.get("content")).replaceAll("\n", "<br>");
		resultMap.put("content", str);
        
		// 이전 글, 다음 글
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		map.put("num", num);

		Map<String, Object> preReadMap = service.readCommand("bbs.preReadBoard", map);
		Map<String, Object> nextReadMap = service.readCommand("bbs.nextReadBoard", map);
        
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("preReadMap", preReadMap);
		model.addAttribute("nextReadMap", nextReadMap);

		model.addAttribute("page", page);
		model.addAttribute("query", query);

        return "bbs/article";
	}
	
	
	@RequestMapping(value="/bbs/delete")
	public String delete(
			@RequestParam(value="num") int num,
			@RequestParam(value="page") String page
			) throws Exception {
		
		// 자료 삭제
		service.deleteCommand("bbs.deleteBoard", num);

		return "redirect:/bbs/list?page="+page;
    }
	
	@RequestMapping(value="/bbs/update", method=RequestMethod.GET)
	public String updateForm(
			@RequestParam(value="num") int num,
			@RequestParam(value="page") String page,
			Model model
			) throws Exception {

		Map<String, Object> resultMap = service.readCommand("bbs.readBoard", num);
		if(resultMap==null)
			return "redirect:/bbs/list?page="+page;

		model.addAttribute("mode", "update");
		model.addAttribute("page", page);
		model.addAttribute("resultMap", resultMap);

		return "bbs/created";
	}
	
	@RequestMapping(value="/bbs/update", method=RequestMethod.POST)
	public String updateSubmit(
			@RequestParam Map<String, Object> paramMap,
			@RequestParam(value="page") String page		
			) throws Exception {
		// 수정 하기
		service.updateCommand("bbs.updateBoard", paramMap);
    	
		return "redirect:/bbs/list?page="+page;
    }
}

