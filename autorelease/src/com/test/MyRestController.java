package com.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.basic.QueryDataDao;

@RestController
public class MyRestController {

	@Autowired
	private Test test;
	
	@Autowired
	QueryDataDao queryDataDao;
	
	@RequestMapping(value="/sum/{i}/{j}",method=RequestMethod.GET)
	@ResponseBody
    public String sum(@PathVariable int i,@PathVariable int j, HttpServletRequest request,HttpServletResponse response) throws Exception {
        return test.sum(i, j).toString();
    }
	
	@RequestMapping(value="/updateData",method=RequestMethod.GET)
	@ResponseBody
	public String updateData(){
		String sql1 = "update test1 set id=2";
		String sql2 = "update test2 set id2=3";
		queryDataDao.updateDataBySql(sql1);
		queryDataDao.updateDataBySql(sql2);
		return "success";
	}
}
