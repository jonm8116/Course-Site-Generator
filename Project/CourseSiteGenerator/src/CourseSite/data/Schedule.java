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
public class Schedule {
    private final StringProperty type;
    private final StringProperty date;
    private final StringProperty title;
    private final StringProperty topic;
    //NON-REQUIRED DATA FIELDS
    private String time;
    private String link;
    private String criteria;
    
    public Schedule(String initType, String initDate, String initTitle, String initTopic){
        type = new SimpleStringProperty(initType);
        date = new SimpleStringProperty(initDate);
        title = new SimpleStringProperty(initTitle);
        topic = new SimpleStringProperty(initTopic);
    }
    //NAVBAR TITLE
    public StringProperty typeProperty() {
        return type;
    }
    public String getType(){
        return type.get();
    }
    public void setType(String newType){
        type.set(newType);
    }
    //file name
    public StringProperty dateProperty() {
        return date;
    }
    public String getDate(){
        return date.get();
    }
    public void setDate(String newDate){
        date.set(newDate);
    }
    //script
    public StringProperty titleProperty() {
        return title;
    }
    public String getTitle(){
        return title.get();
    }
    public void setTitle(String newTitle){
        title.set(newTitle);
    }
    //use
    public StringProperty topicProperty(){
        return topic;
    }
    public String getTopic(){
        return topic.get();
    }
    public void setTopic(String newTopic){
        topic.set(newTopic);
    }
    
        public void setTime(String time) {
        this.time = time;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getTime() {
        return time;
    }

    public String getLink() {
        return link;
    }

    public String getCriteria() {
        return criteria;
    }
}
