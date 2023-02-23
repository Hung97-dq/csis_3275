package com.example.crs.model;

import java.util.HashSet;
import java.util.Set;

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
	
	@OneToMany(mappedBy= "course", 
			cascade = CascadeType.ALL, // generate object in different situation
			fetch = FetchType.LAZY)  // LAZY: may not be able to get all detail of object  ><EAGER: inefficient
	private Set<Section> sections = new HashSet<>();
	
	
	// Owner class or owner entity
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name = "course_students", joinColumns= {
			@JoinColumn(name= "code", referencedColumnName = "id")
	},
	inverseJoinColumns = {
			@JoinColumn(name= "StudentID", referencedColumnName = "id")
	})
	private Set<Student> students = new HashSet<>();
	
	public Course() {
		
	}
	
	public Course(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Section> getSections() {
		return sections;
	}

	public void setSections(Set<Section> sections) {
		this.sections = sections;
	}
	
	public void addSection(Section section) {
		this.sections.add(section);
		section.setCourse(this);
	}
	
	public void addStudent(Student student) {
		this.getStudents().add(student);
		student.getCourses().add(this);
	}

	 public void removeStudent(Student student) {
		    Student studentss = this.students.stream().filter(t -> t.getId() == student.getId()).findFirst().orElse(null);
		    if (studentss != null) {
		      this.students.remove(studentss);
		      studentss.getCourses().remove(this);
		    }
		  }
	 
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	
	
}
