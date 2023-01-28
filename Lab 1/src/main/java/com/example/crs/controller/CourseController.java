package com.example.crs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crs.model.Course;
import com.example.crs.model.CourseRepositoty;

@CrossOrigin(origins ="http://localhoast:8081")
@RestController    //Return will be convert to JSON
@RequestMapping("/api") //provide prefix
public class CourseController {

	@Autowired //This annotation allow object to be there when you start the server, dont need to new this object
	CourseRepositoty courseRepo;
	
	@GetMapping("/courses")
	public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required=false) String title) {
		
		try {
			List<Course> courses = new ArrayList<>();
			if(title == null) {
				
				
				courseRepo.findAll().forEach(courses::add);
			} else {
				courseRepo.findByTitle(title).forEach(courses::add);
			}
			if(courses.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity(courses, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
