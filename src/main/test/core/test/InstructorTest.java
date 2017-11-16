package core.test;


import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstructorTest {

	private IAdmin admin;
	private IInstructor instr;
	 
	@Before
	 public void setup() {
		 this.instr = new Instructor();
		 this.admin = new Admin();
	     this.admin.createClass("test", 2017, "instr", 15);
	 }
	 
	 @Test
	 public void correctHomeworkName() { //check that you can add homework
		 this.instr.addHomework("instr", "test", 2017, "hw1");
		 assertTrue(this.instr.homeworkExists("test", 2017, "hw1"));
	 }
	 @Test
	 public void wrongHwName() { //check that a homework that wasn't added doesnt exist
		 this.instr.addHomework("instr", "test", 2017, "hw1");
		 assertFalse(this.instr.homeworkExists("test", 2017, "hw2"));
	 }
	 
	 @Test 
	 public void badInstructor() { //check that the instructor and homework are linked
		 this.instr.addHomework("instrOther", "test", 2017, "hw1");
		 assertFalse(this.instr.homeworkExists("test", 2017, "hw1"));	 
	 }
	 
	 @Test
	 public void wrongYear() { //shouldnt be able to assign homework to past year
		 this.instr.addHomework("instr", "test", 2015, "hw");
		 assertFalse(this.instr.homeworkExists("test", 2015, "hw"));	 
	 }
	 
	 @Test
	 public void assignGradeStudent() { //check that assign grade works for student
		 IStudent student = new Student();
		 student.registerForClass("student", "test", 2017);
		 this.instr.addHomework("instr", "test", 2017, "hw");
		 student.submitHomework("student", "hw", "solution", "test", 2017);
		 this.instr.assignGrade("instr", "test", 2017, "hw", "student", 80);
		 assertEquals((Integer)80, this.instr.getGrade("test", 2017, "hw", "student"));	 
	 }
	 
	 @Test
	 public void noHomework() { //cant assign a grade to a student who didn't submit homework
		 IStudent student = new Student();
		 student.registerForClass("student", "test", 2017);
		 this.instr.addHomework("instr", "test", 2017, "hw");
		 this.instr.assignGrade("instr", "test", 2017, "hw", "student", 80);
		 assertNull(this.instr.getGrade("test", 2017, "hw", "student")); 
		 
	 }
	 
	 @Test
	 public void testGradeWithoutStudent() { //shouldnt be able to give a student a grade if they didnt register for that class
		 IStudent student = new Student();
		 student.registerForClass("student", "test2", 2017);
		 this.instr.addHomework("instr", "test", 2017, "hw");
		 this.instr.assignGrade("instr", "test", 2017, "hw", "student", 80);
		 assertNull(this.instr.getGrade("test", 2017, "hw", "student")); 		 
	 }
	 
	 @Test
	 public void wrongClass() { //cant assign a grade if not teaching that class
		 IStudent student = new Student();
		 student.registerForClass("student", "test", 2017);
		 this.instr.addHomework("instr", "test2", 2017, "hw");
		 student.submitHomework("student", "hw", "solution", "test", 2017);
		 this.instr.assignGrade("instr", "test", 2017, "hw", "student", 80);
		 assertNull(this.instr.getGrade("test", 2017, "hw", "student")); 
	 }
	 
	 @Test
	 public void wrongInstructor() { //cant assign a grade if wrong instructor
		 IStudent student = new Student();
		 student.registerForClass("student", "test", 2017);
		 this.instr.addHomework("instr2", "test2", 2017, "hw");
		 student.submitHomework("student", "hw", "solution", "test", 2017);
		 this.instr.assignGrade("instr2", "test", 2017, "hw", "student", 80);
		 assertNull(this.instr.getGrade("test", 2017, "hw", "student")); 
	 }
	 
	 @Test
	 public void nullHw() { //homework name cant be null
		 this.instr.addHomework("instr", "test", 2017, "");
		 assertFalse(this.instr.homeworkExists("test", 2017, ""));
		 
	 }
	 
	 @Test
	 public void nullClass() { //cant assign homework to a null class
		 this.instr.addHomework("instr", "", 2017, "hw");
		 assertFalse(this.instr.homeworkExists("", 2017, "instr")); 
	 }
	 @Test
	 public void wrongPercent() { //cant give a percentage less than 0
		 IStudent student = new Student();
		 student.registerForClass("student", "test", 2017);
		 this.instr.addHomework("instr", "test", 2017, "hw1");
		 student.submitHomework("student", "hw1", "answer", "test", 2017);
		 this.instr.assignGrade("instr", "test", 2017, "hw1", "student", -9);
		 assertFalse(this.instr.getGrade("test", 2017, "hw1", "student") < 0);
	 }
}
