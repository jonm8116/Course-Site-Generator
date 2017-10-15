/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import CourseSite.UI.CourseTADataPane;
import CourseSite.app.CourseApp;
import CourseSite.data.CourseData;
import CourseSite.data.CourseSitePages;
import CourseSite.data.Recitation;
import CourseSite.data.Schedule;
import CourseSite.data.Student;
import CourseSite.data.TeachingAssistant;
import CourseSite.data.Team;
import CourseSite.files.TimeSlot;
import CourseSite.workspace.CourseWorkspace;
import djf.components.AppDataComponent;
import djf.ui.AppGUI;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 *
 * @author John
 */
public class TestSave {
    static CourseApp app;
    
    public TestSave(CourseApp initApp){
        app = initApp;
    }
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_EMAIL = "email";
    //ADDING EXTRA JSON FIELDS FOR OTHER TABS
    //COURSE DETAILS
    static final String JSON_USE = "use";
    static final String JSON_NAVBAR = "navbar";
    static final String JSON_FILENAME = "filename";
    static final String JSON_SCRIPT = "script";
    //RECITATION
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAY_TIME = "day_time";
    static final String JSON_LOCATION = "location";
    static final String JSON_FIRST_TA = "first_ta";
    static final String JSON_SECOND_TA = "second_ta";
    //SCHEDULE
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";
    //TEAM 
    static final String JSON_COLOR = "color";
    static final String JSON_TEXT_COLOR = "text_color";
    static final String JSON_LINK = "link";
    //STUDENTS
    static final String JSON_FIRST_NAME = "first_name";
    static final String JSON_LAST_NAME  = "last_name";
    static final String JSON_TEAM       = "team";
    static final String JSON_ROLE       = "role";
    //ARRAY NAMES
    static final String JSON_SITE_PAGES = "site_pages";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_SCHEDULES = "schedules";
    static final String JSON_TEAMS     = "teams";
    static final String JSON_STUDENTS = "students";
    
    //MAKE DATA FIELD FOR OFFICE HOURS TIME SLOT
    ObservableList<TimeSlot> timeSlots = FXCollections.observableArrayList();

    public ObservableList<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
    
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	CourseData dataManager = (CourseData)data;

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        //COMMENT THIS FOR NOW
//        dataManager.initHours(startHour, endHour);
//
//        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
//        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name, email);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
//            dataManager.addOfficeHoursReservation(day, time, name);
            TimeSlot timeslot = new TimeSlot(day, time, name);
            timeSlots.add(timeslot);
        }
        //START LOADING FOR OTHER TABLE VIEWS
        JsonArray jsonSitePageArray = json.getJsonArray(JSON_SITE_PAGES);
        for(int i=0; i< jsonSitePageArray.size(); i++){
            JsonObject jsonSitePage = jsonSitePageArray.getJsonObject(i);
            String navbar = jsonSitePage.getString(JSON_NAVBAR);
            String filename = jsonSitePage.getString(JSON_FILENAME);
            String script = jsonSitePage.getString(JSON_SCRIPT);
            CourseSitePages sitePage = new CourseSitePages(navbar, filename, script, true);
            dataManager.getSitePages().add(sitePage);
        }
        //RECITATIONS
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATIONS);
        for(int i=0; i<jsonRecitationArray.size(); i++){
            JsonObject jsonRec = jsonRecitationArray.getJsonObject(i);
            String section = jsonRec.getString(JSON_SECTION);
            String instructor = jsonRec.getString(JSON_INSTRUCTOR);
            String location = jsonRec.getString(JSON_LOCATION);
            String dayTime = jsonRec.getString(JSON_DAY_TIME);
            String firstTA = jsonRec.getString(JSON_FIRST_TA);
            String secondTA = jsonRec.getString(JSON_SECOND_TA);
            Recitation rec = new Recitation(section, instructor, dayTime, location, firstTA, secondTA);
            dataManager.getRecitations().add(rec);
        }
        //SCHEDULE DATA
        JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULES);
        for(int i=0; i<jsonScheduleArray.size(); i++){
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);
            String type = jsonSchedule.getString(JSON_TYPE);
            String date = jsonSchedule.getString(JSON_DATE);
            String title = jsonSchedule.getString(JSON_TITLE);
            String topic = jsonSchedule.getString(JSON_TOPIC);
            Schedule schedule = new Schedule(type, date, title, topic);
            //dataManager.getSchedules().add(schedule);
        }
        //TEAMS
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_TEAMS);
        for(int i=0; i< jsonTeamsArray.size(); i++){
            JsonObject jsonTeam = jsonTeamsArray.getJsonObject(i);
            String name = jsonTeam.getString(JSON_NAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXT_COLOR);
            String link = jsonTeam.getString(JSON_LINK);
            Team team = new Team(name, color, textColor, link);
            dataManager.getTeams().add(team);
        }
        //STUDENTS
        JsonArray jsonStudentsArray = json.getJsonArray(JSON_STUDENTS);
        for(int i=0; i<jsonStudentsArray.size(); i++){
            JsonObject jsonStudent = jsonStudentsArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_FIRST_NAME);
            String lastName = jsonStudent.getString(JSON_LAST_NAME);
            String team = jsonStudent.getString(JSON_TEAM);
            String role =  jsonStudent.getString(JSON_ROLE);
            Student student = new Student(firstName, lastName, team, role);
            dataManager.getStudents().add(student);
        }
    }
       
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }


    public static void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	CourseData dataManager = (CourseData)data;  //alternate: CourseData dataManager = new CourseData(app); 

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        //HARD CODE TA DATA
        TeachingAssistant taOne = new TeachingAssistant("Jack Fishman", "jack@fishman.edu");
        TeachingAssistant taTwo = new TeachingAssistant("Hugh Jackman", "hugh.jack@aus.edu");
        TeachingAssistant taThree = new TeachingAssistant("Brad pitt", "bradd.pitt@aus.edu");
        dataManager.getTeachingAssistants().add(taOne);
        dataManager.getTeachingAssistants().add(taTwo);
        dataManager.getTeachingAssistants().add(taThree);
        //SAMPLE EXTRA DATA
//        ObservableList<TeachingAssistant> tas = FXCollections.observableArrayList();
//        tas.add(taOne);
//        tas.add(taTwo);
//        tas.add(taThree);
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail()).build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE - MAYBE LATER
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        TimeSlot time1 = new TimeSlot("MONDAY", "10_00am", "Jack");
        TimeSlot time2 = new TimeSlot("TUESDAY", "10_30am", "Jack");
        TimeSlot time3 = new TimeSlot("THURSDAY", "2_00am", "Jack");
        TimeSlot time4 = new TimeSlot("MONDAY", "8_00am", "Jack");
        
        ObservableList<TimeSlot> officeHours = FXCollections.observableArrayList();
        officeHours.add(time4);
        officeHours.add(time3);
        officeHours.add(time2);
        officeHours.add(time1);
        
	//ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        //START WRITING STUFF FOR COURSE DETAILS
        JsonArrayBuilder sitePageArrayBuilder = Json.createArrayBuilder();
        //HARD CODE FOR SITE PAGES
        CourseSitePages pageOne = new CourseSitePages("Home", "index.html", "homeBuilder.js", true);
        CourseSitePages pageTwo = new CourseSitePages("Syllabus", "syllabus.html", "syllabusBuilder.js", true);
        dataManager.getSitePages().add(pageOne);
        dataManager.getSitePages().add(pageTwo);
        ObservableList<CourseSitePages> sitePages = dataManager.getSitePages();
        for(CourseSitePages page: sitePages){
           JsonObject pageJson = Json.createObjectBuilder()
                    .add(JSON_NAVBAR, page.getNavbarTitle())
                    .add(JSON_FILENAME, page.getFileName())
                    .add(JSON_SCRIPT, page.getScript()).build();
           sitePageArrayBuilder.add(pageJson);
        }
        JsonArray sitePageArray = sitePageArrayBuilder.build();
        //START WRITING STUFF FOR RECITATION
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        //HARD CODE RECITATIONS
        Recitation sectionOne = new Recitation("L8", "Mckenna", "Wed", "New Cs", "Banerjee", "other banerjee");
        Recitation sectionTwo = new Recitation("R02", "O'Neil", "tues", "BLC", "kathrynn", "kelly");
        dataManager.getRecitations().add(sectionTwo);
        dataManager.getRecitations().add(sectionOne);
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        for(Recitation rec: recitations){
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, rec.getSection())
                    .add(JSON_INSTRUCTOR, rec.getInstructor())
                    .add(JSON_DAY_TIME, rec.getDayTime())
                    .add(JSON_LOCATION, rec.getLocation())
                    .add(JSON_FIRST_TA, rec.getFirstTA())
                    .add(JSON_SECOND_TA, rec.getSecontTA()).build();
            recitationArrayBuilder.add(recJson);
        }
        JsonArray recArray = recitationArrayBuilder.build();
        //SCHEDULE DATA STUFF
        JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        //HARD CODE SCHEDULE
        Schedule itemOne = new Schedule("holiday", "mon", "supper", "food");
        Schedule itemTwo = new Schedule("holiday", "mon", "supper", "food");
        dataManager.getSchedules().add(itemOne);
        dataManager.getSchedules().add(itemTwo);
        ObservableList<Schedule> schedules = dataManager.getSchedules();
        for(Schedule schedule: schedules){
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, schedule.getType())
                    .add(JSON_DATE, schedule.getDate())
                    .add(JSON_TITLE, schedule.getTitle())
                    .add(JSON_TOPIC, schedule.getTopic()).build();
            scheduleArrayBuilder.add(scheduleJson);
        }
        JsonArray scheduleArray = scheduleArrayBuilder.build();
        //PROJECTS - TEAMS
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        //HARD CODE VALUES FOR TEAMS
        Team teamOne = new Team("Jack", "66682", "ff838ff", "https://beyondtherainbow/skittle.com");
        Team teamTwo = new Team("Fishman", "66682", "ff838ff", "https://beyondtherainbow/skittle.com");
        dataManager.getTeams().add(teamOne);
        dataManager.getTeams().add(teamTwo);
        ObservableList<Team> teams = dataManager.getTeams();
        for(Team team: teams){
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_NAME, team.getName())
                    .add(JSON_COLOR, team.getColor())
                    .add(JSON_TEXT_COLOR, team.getTextColor())
                    .add(JSON_LINK, team.getLink()).build();
            teamsArrayBuilder.add(teamJson);
        }
        JsonArray teamArray = teamsArrayBuilder.build();
        //PROJECTS - STUDENTS
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        //HARD CODE FOR STUDENTS
        Student studentOne = new Student("Shah", "Murthy", "Jack", "Principle Investigator");
        Student studentTwo = new Student("Shah", "Murthy", "Jack", "Principle Investigator");
        dataManager.getStudents().add(studentTwo);
        dataManager.getStudents().add(studentOne);
        ObservableList<Student> students = dataManager.getStudents();
        for(Student student: students){
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_FIRST_NAME, student.getFirstName())
                    .add(JSON_LAST_NAME, student.getLastName())
                    .add(JSON_TEAM, student.getTeam())
                    .add(JSON_ROLE, student.getRole()).build();
            studentsArrayBuilder.add(studentJson);
        }
        JsonArray studentArray = studentsArrayBuilder.build();
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                //NOW TO ADD THE REST OF THE JSON ARRAYS
                .add(JSON_SITE_PAGES, sitePageArray)
                .add(JSON_RECITATIONS, recArray)
                .add(JSON_SCHEDULES, scheduleArray)
                .add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION
//    public void importData(AppDataComponent data, String filePath) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public void exportData(AppDataComponent data, String filePath) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    //CREATE MAIN METHOD TO GENERATE TEST JSON FILE
    public static void main(String[] args){
        //Stage primaryStage = new Stage();
        CourseApp app = new CourseApp();
//        String[] arr = new String[10];
//        app.main(arr);
//        app.buildDataFilesComponent();
//        CourseWorkspace wrk = (CourseWorkspace)app.getWorkspaceComponent();
        //CourseData dt = (CourseData)app.getDataComponent();
//        wrk.getTADataTabPane().reloadOfficeHoursGrid(dt);
        app.loadProperties("app_properties.xml");
        //AppGUI gui = new AppGUI(primaryStage, "TAManager", app);
        //app.buildDataFilesComponent();
        //CourseTADataPane taData = new CourseTADataPane(app);
        TestSave test = new TestSave(app);
        //app.loadProperties("app_properties.xml");
        AppDataComponent data = new CourseData(app);
        //taData.reloadOfficeHoursGrid((CourseData)data);
        //wrk.getTADataTabPane().reloadOfficeHoursGrid((CourseData)data);
        //taData.reloadOfficeHoursGrid((CourseData)data);
        //app.setDataComponent(data);
        try{
           test.saveData(data, "./work/SiteSaveTest.json");
           //test.loadData(data, "./work/SiteSaveTest.json");
           //saveData("./work/SiteSaveTest.json");
           System.out.print("here");
        } catch(IOException ex){
            System.out.print("An IO Exception occurred");
        }
    }
}
