package com.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basic.QueryDataDao;

@Controller
@RequestMapping("/queryController")
public class QueryController 
{
	@Autowired
	QueryDataDao queryDataDao;
	
	
	@RequestMapping(value = "/findById.do")
	@ResponseBody
	public String findById(){
		
		String sql = "select * from fs_document where documentid = 2";
		List list = queryDataDao.queryDataBySql(sql);
		return list.toString();
	}
}