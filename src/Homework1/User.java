

//Organizes user information 

package Homework1;

import java.io.Serializable;
import java.util.ArrayList;
abstract class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private String firstName;
    private String lastName;

    //method overloading
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    //method overloading
    public User(String username, String password, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public User(){
        this.username = "username";
        this.password = "password";
    }
    
    //getters and setters
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public static Course courseSearch(String name, ArrayList<Course> arrL) {
    	return null;
    }
}
