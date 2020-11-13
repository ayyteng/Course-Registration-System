package Homework1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Admin extends User implements AdminUI{

	private static final long serialVersionUID = 1L;

	public Admin(String username, String password) {
        super(username, password);
    }

	//Serialization
    public static void serializeNameArrayLists(ArrayList<Student> arrL){
        try {
            FileOutputStream studentFileOut = new FileOutputStream("allstudents");
            ObjectOutputStream studentObjectOut = new ObjectOutputStream(studentFileOut);
            studentObjectOut.writeObject(arrL);
            studentObjectOut.close();
            studentFileOut.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
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
    public static void deserializeNameArrayLists(ArrayList<Student> arrL){
        ArrayList<Student> students = new ArrayList<>();
        try
        {
            FileInputStream studentFileInput = new FileInputStream("allstudents");
            ObjectInputStream studentObjectInput = new ObjectInputStream(studentFileInput);
            students = (ArrayList) studentObjectInput.readObject();
            studentObjectInput.close();
            studentFileInput.close();
            
        }catch(IOException ioe){
            System.out.print("IO exception on deserialization\n");
            ioe.printStackTrace();
        }catch(ClassNotFoundException c){
            System.out.print("Class not found\n");
            c.printStackTrace();
        }

        arrL = students;
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
            System.out.print("IO exception on deserialization\n");
            ioe.printStackTrace();
        }catch(ClassNotFoundException c){
            System.out.print("Class not found\n");
            c.printStackTrace();
        }

        arrL = courses;
    }
    
    //Create student info
    public static Student createStudent() {
    	Scanner create = new Scanner(System.in);
        System.out.println("Enter a first name: ");
        String first = create.nextLine();
        System.out.println("Enter a last name: ");
        String last = create.nextLine();
        System.out.println("Enter a username: ");
        String username = create.nextLine();
        System.out.println("Enter a password: ");
        String password = create.nextLine();
        Student student = new Student(username, password, first, last);
        return student;
    }

    //Edit course information
    public static void editCourse(Course course, ArrayList<Course> arrL) throws IOException{
        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        //Reading from course edit interface
        try (BufferedReader editCourse = new BufferedReader(new FileReader("EditCourse.txt"))) {
			line = editCourse.readLine();
			while(line != null){
			    System.out.print(line + "\n");
			    line  = editCourse.readLine();
			}
		}
        switch(Integer.parseInt(input.readLine())){
            case 1:
                System.out.print(String.format("Edit: " + course.getName()));
                course.setName(input.readLine());
                break;
            case 2:
                System.out.print(String.format("Edit: " + course.getID()));
                course.setID(input.readLine());
                break;
            case 3:
                System.out.print(String.format("Edit: " + course.getMaxStudents()));
                course.setMaxStudents(Integer.parseInt(input.readLine()));
                break;
            case 4:
                System.out.print(String.format("Edit: " + course.getCurrentStudents()));
                course.setCurrentStudents(Integer.parseInt(input.readLine()));
                break;
            case 5:
                System.out.print(String.format("Edit: " + course.getInstructor()));
                course.setInstructor(input.readLine());
                break;
            case 6:
                System.out.print(String.format("Edit: " + course.getSection()));
                course.setSection(Integer.parseInt(input.readLine()));
                break;
            case 7:
                System.out.print(String.format("Edit: " + course.getLocation()));
                course.setLocation(input.readLine());
                break;
            case 8:
                serializeCourseArrayLists(arrL);
                return;
        }
        editCourse(course, arrL);
    }
    //Course search
    public static Course courseSearch(String name, ArrayList<Course> arrL){
        for(Course course : arrL){
            if(name.equals(course.getName())){
                return course;
            }
        }
        return null;
    }
    
  
    public static ArrayList<Course> findCourseByID(String id, ArrayList<Course> arrL){
        ArrayList<Course> list = new ArrayList<>();
        for(Course course : arrL){
            if(id.equals(course.getID())){
                list.add(course);
            }
        }
        return list;
    }

    public static Student findStudent(String firstName, String lastName, ArrayList<Student> arrL){
        for(Student s : arrL){
            if(firstName.equals(s.getFirstName()) && lastName.equals(s.getLastName())){
                return s;
            }
        }
        return null;
    }
    
    public void adminInterface(ArrayList<Course> courseList, ArrayList<Student> studentList) throws IOException{
        boolean foundCourse;
        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Welcome Admin! (Enter a number)\n1.Course Management\n2.Reports\n3.Exit\n");
        //--------------------COURSE MANAGEMENT------------------------//
        switch(Integer.parseInt(input.readLine())){
            case 1: //Read from admin interface text file
                BufferedReader adminInterface = new BufferedReader(new FileReader("adminCourseManagement.txt"));
                line = adminInterface.readLine();
                while(line != null){
                    System.out.print(line + "\n");
                    line  = adminInterface.readLine();
                }
                switch (Integer.parseInt(input.readLine())){
                    case 1: //Add a new course
                        Course course = new Course();
                        editCourse(course, courseList);
                        courseList.add(course);
                        break;
                    case 2: //Delete a course
                        foundCourse = false;
                        while (!foundCourse) {
                            System.out.println("Enter a course name: ");
                            String courseName = input.readLine();
                            course = courseSearch(courseName, courseList);
                            if(course != null){
                                courseList.remove(course);
                                for(Student s : studentList){
                                    if(s.getCourseList().contains(course)){
                                        s.courses.remove(course);
                                    }
                                }
                                foundCourse = true;
                            } else {
                                System.out.print("Cannot find course!\n");
                            }
                        }
                        break;
                    case 3: //Edit a course
                        foundCourse = false;
                        while (!foundCourse) {
                            System.out.print("Enter a course name\n");
                            String courseName = input.readLine();
                            course = courseSearch(courseName, courseList);
                            if(course != null){
                                editCourse(course, courseList);
                                foundCourse = true;
                            } else {
                                System.out.print("Cannot find course!\n");
                            }
                        }
                        break;
                    case 4: //Display course info based on ID
                        foundCourse = false;
                        while (!foundCourse) {
                            System.out.println("Enter a course ID: ");
                            String courseID = input.readLine();
                            ArrayList<Course> listOfCourses = findCourseByID(courseID, courseList);
                            if(listOfCourses != null){
                                for(Course c : listOfCourses){
                                    System.out.print(c.toString() + "\n");
                                }
                                foundCourse = true;
                            } else {
                                System.out.print("Cannot find course!\n");
                            }
                        }
                        break;
                    case 5: //Register a new student
                        studentList.add(createStudent());
                        break;
                    case 6: //Exit
                        serializeCourseArrayLists(courseList);
                        break;
                }
                break;

             //---------------------REPORTS----------------------//
            case 2:
                BufferedReader adminRUI = new BufferedReader(new FileReader("AdminReports.txt"));
                line = adminRUI.readLine();
                while(line != null){
                    System.out.print(line + "\n");
                    line  = adminRUI.readLine();
                }
                switch (Integer.parseInt(input.readLine())){
                    case 1: //Displays courses
                        for(Course course : courseList){
                            System.out.print(String.format("%s, %s, Current: %d, Max: %d\n", course.getName(), course.getID(), course.getCurrentStudents(), course.getMaxStudents()));
                        }
                        break;
                    case 2: //Displays courses that are full 
                        for(Course course : courseList){
                            if(course.getCurrentStudents() >= course.getMaxStudents()){
                                System.out.print(course.toString() + "\n");
                            }
                        }
                        break;
                    case 3: //Create a file with full courses
                        PrintWriter writer = new PrintWriter("FullCourses.txt");
                        for(Course course : courseList){
                            if(course.getCurrentStudents() >= course.getMaxStudents()){
                                writer.println(course.toString());
                            }
                        }
                        break;
                    case 4: //Displays a course's registered students
                        foundCourse = false;
                        while (!foundCourse) {
                            System.out.println("Enter a course name: ");
                            String courseName = input.readLine();
                            Course course = courseSearch(courseName, courseList);
                            if(course != null){
                                for(Student s : course.getStudentList()){
                                    System.out.print(s.toString() + "\n");
                                }
                                foundCourse = true;
                            } else {
                                System.out.println("Error. Course not found");
                            }
                        }
                        break;
                    case 5: //Displays a student's registered courses
                        boolean foundStudent = false;
                        while (!foundStudent) {
                            System.out.println("Enter a first name: ");
                            String firstName = input.readLine();
                            System.out.println("Enter last name: ");
                            String lastName = input.readLine();
                            Student student = findStudent(firstName, lastName, studentList);
                            if(student != null){
                                for(Course c : student.getCourseList()){
                                    System.out.print(c.toString() + "\n");
                                }
                                foundStudent = true;
                            } else {
                                System.out.println("Error. Course not found.");
                            }
                        }
                        break;
                    case 6: //Sorts courses by registered students
                        Collections.sort(courseList);
                        for(Course c : courseList){
                            System.out.print(c.toString() + "\n");
                        }
                        break;
                    case 7: //Exit
                        serializeCourseArrayLists(courseList);
                        serializeNameArrayLists(studentList);
                        break;

                }
                break;
            case 3: //Exit from CRS
                serializeCourseArrayLists(courseList);
                serializeNameArrayLists(studentList);
                System.out.println("Logged out");
                System.exit(1);


        }
        serializeCourseArrayLists(courseList);
        serializeNameArrayLists(studentList);
        System.out.println("_________________");
        adminInterface(courseList, studentList);
    }

}
