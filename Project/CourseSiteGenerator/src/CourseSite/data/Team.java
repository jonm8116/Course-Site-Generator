/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author John
 */
public class Team {
    
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    private final StringProperty link;
    private Color actualColor;
    private Color actualTextColor;

    
    public Team(String initName, String initColor, String initTextColor, String initLink){
        name = new SimpleStringProperty(initName);
        color = new SimpleStringProperty(initColor);
        textColor = new SimpleStringProperty(initTextColor);
        link = new SimpleStringProperty(initLink);
    }
    //NAVBAR TITLE
    public StringProperty nameProperty() {
        return name;
    }
    public String getName(){
        return name.get();
    }
    public void setType(String nameType){
        name.set(nameType);
    }
    //file name
    public StringProperty colorProperty() {
        return color;
    }
    public String getColor(){
        return color.get();
    }
    public void setColor(String newColor){
        color.set(newColor);
    }
    //script
    public StringProperty textColorProperty() {
        return textColor;
    }
    public String getTextColor(){
        return textColor.get();
    }
    public void setTextColor(String newTextColor){
        textColor.set(newTextColor);
    }
    //use
    public StringProperty linkProperty(){
        return link;
    }
    public String getLink(){
        return link.get();
    }
    public void setLink(String newLink){
        link.set(newLink);
    }
    
    public Color getActualColor(){
        return actualColor;
    }
    public Color getActualTextColor(){
        return actualTextColor;
    }
    //SETTERS
    public void setActualColor(Color initColor){
        actualColor = initColor;
    }
    public void setActualTextColor(Color initColor){
        actualTextColor = initColor;
    }
    @Override
    public String toString(){
        return name.get();
    }
}
