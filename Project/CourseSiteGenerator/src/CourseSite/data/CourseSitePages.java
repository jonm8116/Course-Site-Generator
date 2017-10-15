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
public class CourseSitePages {
    
    private final StringProperty navbarTitle;
    private final StringProperty fileName;
    private final StringProperty script;
    //private final CheckBox use;
    private final Boolean useValue;
    
    public CourseSitePages(String navbar, String file, String scriptname, boolean useValue){
        navbarTitle = new SimpleStringProperty(navbar);
        fileName = new SimpleStringProperty(file);
        script = new SimpleStringProperty(scriptname);
        //use = new CheckBox();
        //use.setSelected(useValue);
        this.useValue = useValue;
    }

    public boolean isUseValue() {
        return useValue.booleanValue();
    }
    //NAVBAR TITLE
    public StringProperty navbarProperty() {
        return navbarTitle;
    }
    public String getNavbarTitle(){
        return navbarTitle.get();
    }
    public void setNavbarTitle(String newNav){
        navbarTitle.set(newNav);
    }
    //file name
    public StringProperty fileNameProperty() {
        return fileName;
    }
    public String getFileName(){
        return fileName.get();
    }
    public void setFileName(String newFile){
        fileName.set(newFile);
    }
    //script
    public StringProperty scriptProperty() {
        return script;
    }
    public String getScript(){
        return script.get();
    }
    public void setScript(String newScript){
        script.set(newScript);
    }
    //use
//    public CheckBox useProperty() {
//        return use;
//    }
//    public boolean getUse(){
//        return ;
//    }

}
