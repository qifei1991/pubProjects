package com.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/indexControl")
@Controller
public class indexControl {

	@RequestMapping("/indexJump.do")
	public String indexControl(HttpServletRequest request,
			HttpServletResponse response, Model model){
		String url = request.getParameter("url");
		if(null == url){
			model.addAttribute("test", "freemaker测试!");
			return "view/index";
		}
		model.addAttribute("test", "freemaker测试!");
		return url;
	}
}
