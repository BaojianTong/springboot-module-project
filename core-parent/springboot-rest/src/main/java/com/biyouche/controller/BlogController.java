package com.biyouche.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biyouche.domain.BlogType;
import com.biyouche.service.BlogService;

@RestController
public class BlogController {

	 @Autowired
	 private BlogService blogService;
	 
	 @RequestMapping(value="/blogTypes")
	 public List<BlogType> blogTypes(HttpServletRequest request, HttpServletResponse response){
		 return blogService.queryBlogType();
	 }
}
	