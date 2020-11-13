

//Organizes data taken from CSV file 

package Homework1;

import java.util.ArrayList;
import java.io.*;

public class Course implements Serializable, Comparable<Course>{

    private String name;
    private String id;
    private String instructor;
    private String location;
    private int maxStudents;
    private int currentStudents;
    private int section;
    private ArrayList<Student> students = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    public Course(String name, String id, int maxStudents, int currentStudents, ArrayList<Student> students, String instructor, int section, String location){
        this.name = name;
        this.id = id;
        this.maxStudents = maxStudents;
        this.currentStudents = currentStudents;
        this.students = students;
        this.instructor = instructor;
        this.section = section;
        this.location = location;
    }
    
    public Course() {
        this.name = "New Course";
    }
    //adds one to student count
    public void register(Student student){
        students.add(student);
        currentStudents++;
    }
    //subtracts one from student count
    public void withdraw(Student student) {
        students.remove(student);
        currentStudents--;
    }

    public ArrayList<Student> getStudentList(){
        return this.students;
    }
    
    //getters and setters
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getID(){
        return this.id;
    }

    public void setID(String id){
        this.id = id;
    }

    public int getMaxStudents(){
        return this.maxStudents;
    }

    public void setMaxStudents(int maxStudents){
        this.maxStudents = maxStudents;
    }

    public int getCurrentStudents(){
        return this.currentStudents;
    }

    public void setCurrentStudents(int currentStudents){
        this.currentStudents = currentStudents;
    }

    public String getInstructor(){
        return this.instructor;
    }

    public void setInstructor(String instructor){
        this.instructor = instructor;
    }

    public int getSection(){
        return this.section;
    }

    public void setSection(int section){
        this.section = section;
    }

    public String getLocation(){
        return this.location;
    }

    public void setLocation(String location){
        this.location = location;
    }
    
    public int compareTo(Course course){
        return this.currentStudents - course.getCurrentStudents();
    }
    //Formats course view
    public String toString(){
        return String.format("%s, %s, %d, %d, %s, %d, %s", name, id, maxStudents, currentStudents, instructor, section, location);
    }



}