/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.data;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author John
 */
public class SiteInfo {
    //COMBOBOXES FOR SITE INFO
    ComboBox subject;
    ComboBox number;
    ComboBox semester;
    ComboBox year;
    //TEXTFIELDS
    TextField title;
    TextField instrName;
    TextField instrHome;
    
    public SiteInfo(ComboBox initSubject, ComboBox initNum, ComboBox initSem, ComboBox initYr, TextField initTitle, TextField initName, TextField initHome){
        subject = initSubject;
        number = initNum;
        semester = initSem;
        year = initYr;
        title = initTitle;
        instrName = initName;
        instrHome = initHome;
    }
    //GETTERS FOR THE ACTUAL VALUES
    public Object getSubject(){
        return subject.getValue();
    }
    public Object getNumber(){
        return number.getValue();
    }
    public Object getSemester(){
        return semester.getValue();
    }
    public Object getYear(){
        return year.getValue();
    }
    public Object getTitle(){
        return title.getText();
    }
    public Object getInstrName(){
        return instrName.getText();
    }
    public Object getInstrHome(){
        return instrHome.getText();
    }
}
