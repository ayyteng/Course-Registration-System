package Homework1;

import java.io.IOException;
import java.util.ArrayList;

public interface StudentUI {
	//One giant interface
	public void studentInterface(Student student, ArrayList<Course>arrL) throws IOException;
	ArrayList<Course> getCourseList();
}

