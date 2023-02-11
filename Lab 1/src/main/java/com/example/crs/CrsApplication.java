package com.example.crs;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.crs.model.Course;
import com.example.crs.model.CourseRepositoty;
import com.example.crs.model.Section;

@SpringBootApplication
public class CrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsApplication.class, args);
	}
	
	//ApplicationRunner is a functional interface (i.e. there is only one method)
	// This method will be called after the application startup as @Bean, runs After main function
	@Bean
	ApplicationRunner init (CourseRepositoty courseRepo) {
		//lambda (Ad-host method, only use once and will not be use anymore)
		// (arg) is the parameter parse to the method
		return (arg) ->{
			Course csis3275 = new Course("CSIS3275","Software Engineering");
//			Section section1 = new Section(1);
//			csis3275.getSections().add(section1);
//			section1.setCourse(csis3275);
			
			csis3275.addSection(new Section(1));
			csis3275.addSection(new Section(2));
			csis3275.addSection(new Section(3));
			courseRepo.save(csis3275);
			
			
//			courseRepo.save(new Course("CSIS3275","Software Engineering"));
//			courseRepo.save(new Course("CSIS2175","Advanced Integrated Software Development"));
//			courseRepo.save(new Course("CSIS1190","Excel in Business"));
			
//			List<Course> courses = courseRepo.findAll();
//			
//			for(Course course:courses) {
//				System.out.print(course);
//			}
			
			// one line code, use :: to call the method of system.out class
			courseRepo.findAll().forEach(System.out::println);
			
			
			// if we don't use lambda, then we have write long code at the following:
			/**
			 * ApplicationRunner a = new ApplicationRunner(
			 * 	@Override
			 * public void run(ApplicationArguments args) throws Exception{
			 * courseRepo.save(new Course("CSIS3275","Software Engineering"));
			courseRepo.save(new Course("CSIS2175","Advanced Integrated Software Development"));
			courseRepo.save(new Course("CSIS1190","Excel in Business"));
			 * }
			 * )
			 */
		};
		
	}

}
