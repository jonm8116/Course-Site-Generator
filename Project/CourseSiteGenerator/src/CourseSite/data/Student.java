/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author John
 */
public class Student {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty team;
    private final StringProperty role;

    
    public Student(String initFirstName, String initLastName, String initTeam, String initRole){
        firstName = new SimpleStringProperty(initFirstName);
        lastName = new SimpleStringProperty(initLastName);
        team = new SimpleStringProperty(initTeam);
        role = new SimpleStringProperty(initRole);
    }
    //NAVBAR TITLE
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public String getFirstName(){
        return firstName.get();
    }
    public void setFirstName(String initFirst){
        firstName.set(initFirst);
    }
    //file name
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public String getLastName(){
        return lastName.get();
    }
    public void setLastName(String initLast){
        lastName.set(initLast);
    }
    //script
    public StringProperty teamProperty() {
        return team;
    }
    public String getTeam(){
        return team.get();
    }
    public void setTeam(String initTeam){
        team.set(initTeam);
    }
    //use
    public StringProperty roleProperty(){
        return role;
    }
    public String getRole(){
        return role.get();
    }
    public void setRole(String initRole){
        role.set(initRole);
    }
}
