package Homework1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Student extends User implements Serializable, StudentUI{


    private static final long serialVersionUID = 1L;
    public ArrayList<Course> courses = new ArrayList<>();


    public Student(String username, String password, String firstName, String lastName){
        super(username, password, firstName, lastName);
    }
    
    public String toString(){
        return String.format("%s %s", getFirstName(), getLastName());
    }
    
    public ArrayList<Course> getCourseList(){
        return this.courses;
    }
   
    //Serialization 
    public static void serializeCourseArrayLists(ArrayList<Course> arrL){
        try{
            FileOutputStream courseFileOut= new FileOutputStream("allcourses");
            ObjectOutputStream courseObjectOut= new ObjectOutputStream(courseFileOut);
            courseObjectOut.writeObject(arrL);
            courseObjectOut.close();
            courseFileOut.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    public static void deserializeCourseArrayLists(ArrayList<Course> arrL){
        ArrayList<Course> courses = new ArrayList<>();
        try
        {
            FileInputStream courseFileInput = new FileInputStream("allcourses");
            ObjectInputStream courseObjectInput = new ObjectInputStream(courseFileInput);
            courses = (ArrayList<Course>) courseObjectInput.readObject();
            courseObjectInput.close();
            courseFileInput.close();
        }catch(IOException ioe){
            System.out.println("IO exception on deserialization");
            ioe.printStackTrace();
        }catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
        }

        arrL = courses;
    }
    //Course Search
    public static Course courseSearch(String name, ArrayList<Course> arrL){
        for(Course course : arrL){
            if(name.equals(course.getName())){
                return course;
            }
        }
        return null;
    }

    public static Course courseSearch(String name, int section, ArrayList<Course>arrL){
        for(Course course : arrL){
            if(name.equals(course.getName()) && section == course.getSection()){
                return course;
            }
        }
        return null;
    }
    
    //Main Interface
    public void studentInterface(Student student, ArrayList<Course>arrL) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Course course;
        boolean foundCourse;
        String courseName;
        //Reading from interface text file
        try (BufferedReader studentUI = new BufferedReader(new FileReader("StudentCourseManagement.txt"))) {
			String line = studentUI.readLine();
			while(line != null){
			    System.out.print(line + "\n");
			    line  = studentUI.readLine();
			}
		}
        //Switch case for each menu option
        switch (Integer.parseInt(input.readLine())) {
            case 1: //Displays courses
                for (Course c : arrL) {
                    System.out.print(c.toString() + "\n");
                }
                break;
            case 2: //Displays open courses
                for (Course c : arrL) {
                    if (c.getCurrentStudents() < c.getMaxStudents()) {
                        System.out.print(c.toString() + "\n");
                    }
                }
                break;
            case 3: //Registers student to a course
                foundCourse = false; //initializing boolean as false
                while (!foundCourse) {
                    System.out.println("Enter a course name:");
                    courseName = input.readLine();
                    course = courseSearch(courseName, arrL);
                    if(course != null){
                        if(!student.courses.contains(course)){
                            student.courses.add(course);
                            course.register(student);
                            foundCourse = true;
                            System.out.print(String.format("You have registered for " + course.getName()) + "\n");
                        } else{
                            System.out.println("Error. Already registered for this course.");
                            foundCourse = true;
                        }
                    } else {
                        System.out.println("Course not found.");
                    }
                }
            break;
            case 4: //Withdraws student from course
                foundCourse = false;
                while(!foundCourse) {
                    System.out.println("Enter a course name: ");
                    courseName = input.readLine();
                    course = courseSearch(courseName, arrL);
                    if(course != null){
                        if(student.courses.contains(course)) {
                            student.courses.remove(course);
                            course.withdraw(student);
                            foundCourse = true;
                            System.out.print(String.format("You withdrew from " + course.getName()) + "\n");
                        } else {
                            System.out.println("Error. Not registered for this course");
                        }
                    } else {
                        System.out.println("Course not found.");
                    }
                }
            break;
            case 5: //displays student's registered courses
                for(Course c : student.getCourseList()){
                    System.out.print(String.format("%s, %s, %s, %d, %s", c.getName(), c.getID(), c.getInstructor(),  c.getSection(), c.getLocation()));
                }
            break;
            case 6: //Exit
                System.out.println("Logged out");
                serializeCourseArrayLists(arrL);
                System.exit(1);
        }
        serializeCourseArrayLists(arrL);
        System.out.println("__________________");
        studentInterface(student, arrL);
    }
    
    

}
