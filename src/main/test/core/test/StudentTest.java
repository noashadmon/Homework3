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

public class StudentTest {
	
	private IAdmin admin;
	private IStudent student;
	private IInstructor instr;
	
	@Before
	public void setup() {
		 this.student = new Student();
		 this.admin = new Admin();
		 this.instr = new Instructor();
		 
	     this.admin.createClass("test", 2017, "instr", 2);
	 }
	
	@Test
	public void checkRegister() { //check that student can register
		this.student.registerForClass("student", "test", 2017);
		assertTrue(this.student.isRegisteredFor("student", "test", 2017));
	}
	@Test 
	public void classFull() { //shouldnt be able to register if class is ful
		this.student.registerForClass("student", "test", 2017);
		IStudent student2 = new Student();
		student2.registerForClass("student2", "test", 2017);
		IStudent student3 = new Student();
		student3.registerForClass("student3", "test", 2017);
		assertFalse(student3.isRegisteredFor("student3", "test", 2017));
		
	}
	
	@Test
	public void reachedCap() { //student cant register if meached enrollment cap
		this.student.registerForClass("student", "test", 2017);
		IStudent student2 = new Student();
		student2.registerForClass("student2", "test", 2017);
		IStudent student3 = new Student();
		student3.registerForClass("student2", "test", 2017);
		assertFalse(student3.isRegisteredFor("student3", "test", 2017));	
	}
	
	@Test
	public void unknownClass() { //shouldnt be able to register for a class that doesn't exist
		this.student.registerForClass("student", "test2", 2017);
		assertFalse(this.student.isRegisteredFor("student", "test2", 2017));
	}
	
	@Test
	public void testDrop() { //test that a student can drop a class
		this.student.registerForClass("student", "test", 2017);
		assertTrue(this.student.isRegisteredFor("student", "test", 2017));
		
		this.student.dropClass("student", "test", 2017);
		assertFalse(this.student.isRegisteredFor("student", "test", 2017));
		
	}
	
	@Test
	public void checkSubmit() { //check that a student can submit homework
		this.student.registerForClass("student", "test", 2017);
		this.student.submitHomework("student", "hw", "answer", "test", 2017);
		assertTrue(this.student.hasSubmitted("student", "hw", "test", 2017));	
	}
	
	@Test
	public void submitWrongHW() { //student didn't submit homework if submitted wrong assignment
		this.student.registerForClass("student", "test", 2017);
		instr.addHomework("instr", "test", 2017, "hw1");
		this.student.submitHomework("student", "hw2", "answer", "test", 2017);
		assertFalse(this.student.hasSubmitted("student", "hw1", "test", 2017));
	
	}
	
	@Test
	public void notRegistered() { //cant submit hw if not registered
		instr.addHomework("instr", "test", 2017, "hw1");
		this.student.submitHomework("student", "hw", "answer", "test", 2017);
		assertFalse(this.student.hasSubmitted("student", "hw1", "test", 2017));
	}

	@Test
	public void noName() { //student must have a name when registering
		this.student.registerForClass("", "test", 2017);
		assertFalse(this.student.isRegisteredFor("", "test", 2017));
		
	}
	
	@Test
	public void noClassName() { //class must have a name when registering
		this.student.registerForClass("student", "", 2017);
		assertFalse(this.student.isRegisteredFor("student", "", 2017));
	}
	
	@Test 
	public void classEnded() { //cant register if class is over
		this.student.registerForClass("student", "test", 2017);
		assertTrue(admin.classExists("test", 2017));
		IStudent student2 = new Student();
		assertFalse(admin.classExists("test", 2017));
		student2.registerForClass("student2", "test", 2017);
		assertFalse(student2.isRegisteredFor("student2", "test", 2017));	
	}
	

}
