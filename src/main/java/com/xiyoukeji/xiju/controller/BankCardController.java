/**
 * 
 */
package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hq
 *
 */
@Controller
public class BankCardController extends BaseController {

	
	@RequestMapping("/bankCard/add.do")
	public void add(HttpServletRequest request,HttpServletResponse response){
		
	}
}
