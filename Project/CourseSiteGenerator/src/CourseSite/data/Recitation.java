/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 *
 * @author John
 */
public class Recitation {
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayTime;
    private final StringProperty location;
    private final StringProperty firstTA;
    private final StringProperty secondTA;
    
    public Recitation(String initSection, String initInstructor, String initDayTime, String initLocation, String initFirstTA, String initSecondTA){
        section = new SimpleStringProperty(initSection);
        instructor = new SimpleStringProperty(initInstructor);
        dayTime = new SimpleStringProperty(initDayTime);
        location = new SimpleStringProperty(initLocation);
        firstTA = new SimpleStringProperty(initFirstTA);
        secondTA = new SimpleStringProperty(initSecondTA);
    }
    //NAVBAR TITLE
    public StringProperty sectionProperty() {
        return section;
    }
    public String getSection(){
        return section.get();
    }
    public void setSection(String newSection){
        section.set(newSection);
    }
    //file name
    public StringProperty instructorProperty() {
        return instructor;
    }
    public String getInstructor(){
        return instructor.get();
    }
    public void setInstructor(String newInstructor){
        instructor.set(newInstructor);
    }
    //script
    public StringProperty dayTimeProperty() {
        return dayTime;
    }
    public String getDayTime(){
        return dayTime.get();
    }
    public void setDayTime(String newDayTime){
        dayTime.set(newDayTime);
    }
    //use
    public StringProperty locationProperty(){
        return location;
    }
    public String getLocation(){
        return location.get();
    }
    public void setLocation(String newLocation){
        location.set(newLocation);
    }
    
    public StringProperty firstTAProperty(){
        return firstTA;
    }
    public String getFirstTA(){
        return firstTA.get();
    }
    public void setFirstTA(String newFirstTA){
        firstTA.set(newFirstTA);
    }
    public StringProperty secondTAProperty(){
        return secondTA;
    }
    public String getSecontTA(){
        return secondTA.get();
    }
    public void setSecondTA(String newSecondTA){
        secondTA.set(newSecondTA);
    }
}
