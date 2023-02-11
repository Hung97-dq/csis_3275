package com.example.crs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crs.model.Course;
import com.example.crs.model.CourseRepositoty;

@CrossOrigin(origins ="http://localhost:8081")
@RestController    //Return will be convert to JSON
@RequestMapping("/api") //provide prefix
public class CourseController {

	@Autowired //This annotation allow object to be there when you start the server, dont need to new this object
	CourseRepositoty courseRepo;
	
	@PostMapping("/courses")
	public ResponseEntity<Course> createCourse(@RequestBody Course course){
		
		// information will be saved in RequestBody, a JSON document will be send to server 
		//and then be converted to java object by spring boot
		try {
		Course newCourse = new Course(course.getCode(), course.getTitle());
		courseRepo.save(newCourse);
		} catch(Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
		
	}
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
	
	@GetMapping("/courses/{id}") // use {} for path variable 1
	public ResponseEntity<Course> getCourseById(@PathVariable("id") long id) { //2
		// path variable, id in 1 and 2 need to have the same name
		// When get request occur with the id, the get mapping invoke the method
		try {
		Optional<Course> courseData = courseRepo.findById(id);
		if (courseData.isPresent()) {
			return new ResponseEntity<>(courseData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/courses/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course course) {
		Optional<Course> courseData = courseRepo.findById(id);

		if (courseData.isPresent()) {
			Course _course = courseData.get();
			_course.setCode(course.getCode());
			_course.setTitle(course.getTitle());
			return new ResponseEntity<>(courseRepo.save(_course), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/courses")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			courseRepo.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/courses/{id}")
	public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") long id) {
		try {
			courseRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
