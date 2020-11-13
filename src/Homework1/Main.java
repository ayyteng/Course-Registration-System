package Homework1;

import java.io.*;
import java.util.*;


public class Main implements Serializable{
	
	
    private static final long serialVersionUID = 1L;
    private static ArrayList<Course> courseList = new ArrayList<>();
    private static ArrayList<Student> studentList = new ArrayList<>();

    public static void main(String[] args) throws IOException{
    	//only call loadCourseList() when running the code for the first time, in order to create the student list. 
    	loadCourseList();
    	deserializeNameArrayLists();
    	serializeNameArrayLists();
    	serializeCourseArrayLists();
    	deserializeCourseArrayLists();
        mainInterface();
    }
    //Adds CSV elements into an arraylist
    public static void loadCourseList(){
        try (BufferedReader file = new BufferedReader(new FileReader("MyUniversityCourses.csv"))){
            String line = file.readLine();
            while(line != null){
                String[] info = line.split(",");
                Course newCourse = new Course(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), new ArrayList<>(), info[5], Integer.parseInt(info[6]), info[7]);
                courseList.add(newCourse);
                line = file.readLine();
            }
            file.close();
        } catch (FileNotFoundException e){
        } catch (IOException e) {
        }
        System.out.println("Courses succesfully loaded");
    }

    //Main login interface
    public static void mainInterface() throws IOException {
    	Admin admin = new Admin("Admin", "Admin001");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Login (enter a number):");
        System.out.println("1) Student  2) Admin");
        String username, password;
        switch (Integer.parseInt(input.readLine())) {
        	case 1: //Login as a student
        		System.out.println("Username: ");
        		username = input.readLine();
        		System.out.println("Password: ");
        		password = input.readLine();
        		for(Student s : studentList){
        			if(username.equals(s.getUsername()) && password.equals(s.getPassword())) {
        				Student student = new Student(s.getUsername(), s.getPassword(), s.getFirstName(), s.getLastName());
        				student.studentInterface(s, courseList); //calls for interface method
        				break;
        			}
        		}
            break;
            case 2: //Login as an admin
                System.out.println("Username: ");
                username = input.readLine();
                System.out.println("Password: ");
                password = input.readLine();
                if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())){
                	admin.adminInterface(courseList, studentList); //calls for interface method
                }
                break;
            default:
                System.out.println("Error.");
                mainInterface();
        }
    }
    
    
    //Serialization
    public static void serializeNameArrayLists(){
        try {
            FileOutputStream studentFileOut = new FileOutputStream("allstudents");
            ObjectOutputStream studentObjectOut = new ObjectOutputStream(studentFileOut);
            studentObjectOut.writeObject(studentList);
            studentObjectOut.close();
            studentFileOut.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void serializeCourseArrayLists(){
        try{
            FileOutputStream courseFileOut= new FileOutputStream("allcourses");
            ObjectOutputStream courseObjectOut= new ObjectOutputStream(courseFileOut);
            courseObjectOut.writeObject(courseList);
            courseObjectOut.close();
            courseFileOut.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    public static void deserializeNameArrayLists(){
        ArrayList<Student> students = new ArrayList<>();
        try{
            FileInputStream studentFileInput = new FileInputStream("allstudents");
            ObjectInputStream studentObjectInput = new ObjectInputStream(studentFileInput);
            students = (ArrayList) studentObjectInput.readObject();
            studentObjectInput.close();
            studentFileInput.close();       
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(ClassNotFoundException c){
            c.printStackTrace();
        }

        studentList = students;
    }
    public static void deserializeCourseArrayLists(){
        ArrayList<Course> courses = new ArrayList<>();
        try{
            FileInputStream courseFileInput = new FileInputStream("allcourses");
            ObjectInputStream courseObjectInput = new ObjectInputStream(courseFileInput);
            courses = (ArrayList) courseObjectInput.readObject();
            courseObjectInput.close();
            courseFileInput.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(ClassNotFoundException c){
            c.printStackTrace();
        }

        courseList = courses;
    }

}
