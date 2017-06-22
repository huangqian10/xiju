/**
 * 
 */
package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import com.xiyoukeji.xiju.service.TestService;

/**
 * @author hq
 *
 */
@RestController
public class TestController {

	@Autowired
	private TestService testService;
	
	@GetMapping("test.do")
	public void test(HttpServletRequest request,
			HttpServletResponse response){
		testService.test();
		
	}
}
