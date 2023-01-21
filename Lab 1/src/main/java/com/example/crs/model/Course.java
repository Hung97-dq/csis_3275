package com.example.crs.model;

import jakarta.persistence.*;

@Entity
@Table(name= "courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name= "code")
	private String code;
	
	@Column(name="title")
	private String title;
	
}
