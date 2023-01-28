package com.example.crs.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepositoty extends JpaRepository<Course, Long> {
	List<Course> findByTitle(String title);
	List<Course> findByCode (String code);
	List<Course> findByTitleContaining (String title);
}
