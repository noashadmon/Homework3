package core.test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class AdminTest {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testMakeClass() { //create class successful
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void wrongClassName() { //class must have correct name
    		this.admin.createClass("test", 2017, "teach", 2);
    		assertFalse(this.admin.classExists("TEST", 2017));
    	
    }

    @Test
    public void testMakeClass2() { //cant create a class in the past
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test 
    //instructor for 2 courses in one year
    public void instructorCourseLimitCornerClass() { //instructor can teach at most 2 courses in a year
    		this.admin.createClass("Test", 2017, "Instructor", 15);
    		this.admin.createClass("Test2", 2017, "Instructor", 15);
    		assertTrue(this.admin.classExists("Test2", 2017));
    }
    
    @Test 
    //instructor teaches 3 or more courses in a year
    public void instructorCourseLimitViolation() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test2", 2017, "Instructor", 15);
		this.admin.createClass("Test3", 2017, "Instructor", 15);
		assertFalse(this.admin.classExists("Test3", 2017));
    		
    }
    
    //tests capacity is greater than 0 or valid
    @Test
    public void testCapacity() {
    		this.admin.createClass("Test", 2017, "Instr", 0);
    		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void nonNegCap() {
    		this.admin.createClass("Test", 2017, "instr", -1);
    		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    public void testCapacity2() {
    		this.admin.createClass("Test", 2017, "Instr", 1);
    		assertTrue(this.admin.classExists("Test", 2017));
    }
    
    //tests change capacity
    	//ask about this
    //capacity of 2, and then 2 people enroll
    //have to use student class to register students
    //lower the capacity to 1 --> shouldnt be able to lower the capacity
    //so all you need to do is confirm that nothing has changed
    @Test 
    public void changeCapMore() {
    		IStudent student = new Student();
    		this.admin.createClass("test", 2017, "instr", 2);
    		student.registerForClass("student1", "test", 2017);
    		IStudent student2 = new Student();
    		student2.registerForClass("student2", "test", 2017);
    		IStudent student3 = new Student();
    		student3.registerForClass("student3", "test", 2017);
    		this.admin.changeCapacity("test", 2017, 3);
    		assertEquals(3, this.admin.getClassCapacity("test", 2017));
    }
    
    @Test
    public void changeCapLess() {
    		IStudent student = new Student();
    		this.admin.createClass("test", 2017, "instr", 2);
    		student.registerForClass("student1", "test", 2017);
    		IStudent student2 = new Student();
    		student2.registerForClass("student2", "test", 2017);
    		this.admin.changeCapacity("test", 2017, 1);
    		assertEquals(2, this.admin.getClassCapacity("test", 2017));	
    }
    
   @Test
   public void changeCapWithoutClass() { //shouldnt be able to change capacity if class doesnt exist
	   this.admin.changeCapacity("test", 2017, 3);
	   assertNotEquals(3, this.admin.getClassCapacity("test", 2017));
   }
    
    @Test
    public void doubleClass() { //cant have two classes in a year with the same name tought by 2 different instructors
    		this.admin.createClass("test", 2017, "instr", 3);
    		this.admin.createClass("test", 2017, "instr2", 3);
    		assertNotEquals("instr2", this.admin.getClassInstructor("test", 2017));
    }
    
    @Test
    public void noClassName() { //classes must have a name - cant be null
    		this.admin.createClass("", 2017, "instr", 3);
    		assertFalse(this.admin.classExists("", 2017));
    }
    
    @Test
    public void noINstrName() { //instructor must have a name
    		this.admin.createClass("test", 2017, "", 3);
    		assertFalse(this.admin.classExists("test", 2017));   	
    }
    
    
    
    
}
