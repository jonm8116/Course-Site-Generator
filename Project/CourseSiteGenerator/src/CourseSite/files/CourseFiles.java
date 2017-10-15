/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.files;

import CourseSite.UI.CourseDetailsPane;
import CourseSite.UI.CourseSchedulePane;
import CourseSite.app.CourseApp;
import CourseSite.data.CourseData;
import CourseSite.data.CourseSitePages;
import CourseSite.data.Recitation;
import CourseSite.data.Schedule;
import CourseSite.data.Student;
import CourseSite.data.TeachingAssistant;
import CourseSite.data.Team;
import CourseSite.workspace.CourseWorkspace;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
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
public class CourseFiles implements AppFileComponent{
    
    CourseApp app;
    
    public CourseFiles(CourseApp initApp){
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
    
    //VAR NAMES FOR COURSE DETAILS JSON SAVING
    static final String JSON_DETAILS_SUBJECT = "subject";
    static final String JSON_DETAILS_SEMESTER = "semester";
    static final String JSON_DETAILS_NUMBER = "number";
    static final String JSON_DETAILS_YEAR   = "year";
    static final String JSON_DETAILS_TITLE  = "title";
    static final String JSON_DETAILS_INSTR_NAME = "instr_name";
    static final String JSON_DETAILS_INSTR_HOME = "instr_home";
    //VAR NAMES FOR RECITATION SAVING
    static final String JSON_REC_SECTION = "section";
    static final String JSON_REC_DAY_TIME = "day_time";
    static final String JSON_REC_LOCATION = "location";
    static final String JSON_REC_TA_ONE   = "ta_1";
    static final String JSON_REC_TA_TWO   = "ta_2";
    //VAR NAMES FOR SCHEDULE PANE
    static final String JSON_SCHEDULE_START_MONTH = "startingMondayMonth";
    static final String JSON_SCHEDULE_START_DAY = "startingMondayDay";
    static final String JSON_SCHEDULE_END_MONTH = "endingFridayMonth";
    static final String JSON_SCHEDULE_END_DAY = "endingFridayDay";
    static final String JSON_SCHEDULE_HOLIDAYS = "holidays";
    static final String JSON_SCHEDULE_LECTURES = "lectures";
    static final String JSON_SCHEDULE_REFERENCES = "references";
    static final String JSON_SCHEDULE_RECITATIONS = "recitations";
    static final String JSON_SCHEDULE_HWS       = "hws";
    //VAR NAMES FOR EACH SCHEDULE ITEM
    static final String JSON_ITEM_MONTH = "month";
    static final String JSON_ITEM_DAY   = "day";
    static final String JSON_ITEM_TITLE = "title";
    static final String JSON_ITEM_LINK  = "link";
    static final String JSON_ITEM_TOPIC = "topic";
    static final String JSON_ITEM_CRITERIA  = "criteria";
    static final String JSON_ITEM_TIME  = "time";
    //VAR NAMES FOR TEAM FIELDS
    static final String JSON_TEAM_NAME  ="name";
    static final String JSON_TEAM_RED   ="red";
    static final String JSON_TEAM_GREEN ="green";
    static final String JSON_TEAM_BLUE  ="blue";
    static final String JSON_TEAM_TEXT_COLOR = "text_color";
    //VAR NAMES FOR STUDENT FIELDS
    static final String JSON_STUDENT_LAST_NAME  = "lastName";
    static final String JSON_STUDENT_FIRST_NAME = "firstName";
    static final String JSON_STUDENT_TEAM_NAME  = "team";
    static final String JSON_STUDENT_ROLE       = "role";
    //VAR NAMES FOR TEAM AND STUDENT ARRS
    static final String JSON_TEAM_ARRAY = "teams";
    static final String JSON_STUDENT_ARRAY = "students";
    //VAR PROJECTS 
    static final String JSON_PROJECT_WORK = "work";
    static final String JSON_PROJECT_SEMESTER = "semester";
    static final String JSON_PROJECT_PROJECTS = "projects";
    static final String JSON_PROJECT_STUDENTS = "students";
    static final String JSON_PROJECT_LINK     = "link";
    
    
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	CourseData dataManager = (CourseData)data;

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

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
            dataManager.addOfficeHoursReservation(day, time, name);
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
            //THE STRINGS BELOW ARE NOT PART OF ORIGIN
            String time = jsonSchedule.getString(JSON_ITEM_TIME);
            String link = jsonSchedule.getString(JSON_ITEM_LINK);
            String criteria = jsonSchedule.getString(JSON_ITEM_CRITERIA);
            Schedule schedule = new Schedule(type, date, title, topic);
            schedule.setCriteria(criteria);
            schedule.setLink(link);
            schedule.setTime(time);
            dataManager.getSchedules().add(schedule);
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

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	CourseData dataManager = (CourseData)data;

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail()).build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
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
        ObservableList<Schedule> schedules = dataManager.getSchedules();
        for(Schedule schedule: schedules){
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_TYPE, schedule.getType())
                    .add(JSON_DATE, schedule.getDate())
                    .add(JSON_TITLE, schedule.getTitle())
                    .add(JSON_TOPIC, schedule.getTopic())
                    //ORIGIN DOES NOT INCLUDE THE ADDING BELOW
                    .add(JSON_ITEM_LINK, schedule.getLink())
                    .add(JSON_ITEM_TIME, schedule.getTime())
                    .add(JSON_ITEM_CRITERIA, schedule.getCriteria())
                    .build();
            scheduleArrayBuilder.add(scheduleJson);
        }
        JsonArray scheduleArray = scheduleArrayBuilder.build();
        //PROJECTS - TEAMS
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
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
        //PUT INFO FOR COURSE NAME
        
        
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
    
    public void saveCourseDetailsJson(CourseApp app, String filePath) throws FileNotFoundException{
        
        CourseWorkspace wrkspace = (CourseWorkspace)app.getWorkspaceComponent();
        CourseDetailsPane detailPane = wrkspace.getCourseDetailsTab();
        String subject = (String)detailPane.getSubjectBox().getValue();
        String semester = (String)detailPane.getSemesterChoices().getValue();
        String number = (String)detailPane.getSubjectNumber().getValue();
        String year = (String)detailPane.getYearChoices().getValue();
        String title = detailPane.getCourseTitleField().getText();
        String instrName = detailPane.getCourseInstrName().getText();
        String instrHome = detailPane.getCourseInstrHome().getText();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_DETAILS_SUBJECT, subject)
		.add(JSON_DETAILS_SEMESTER, semester)
                .add(JSON_DETAILS_NUMBER, number)
                .add(JSON_DETAILS_YEAR, year)
                .add(JSON_DETAILS_TITLE, title)
                .add(JSON_DETAILS_INSTR_NAME, instrName)
                .add(JSON_DETAILS_INSTR_HOME, instrHome)
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
    //SAVE METHOD FOR CREATING TA DATA JSON
    public void saveTAData(AppDataComponent data, String filePath) throws IOException{
        // GET THE DATA
	CourseData dataManager = (CourseData)data;

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
		    .add(JSON_EMAIL, ta.getEmail()).build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                //NOW TO ADD THE REST OF THE JSON ARRAYS
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
    
    public void saveRecitationData(AppDataComponent data, String filePath) throws IOException{
        
        CourseData dataManager = (CourseData)data;
        
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        for(Recitation rec: recitations){
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_REC_SECTION, rec.getSection())
                    .add(JSON_REC_DAY_TIME, rec.getDayTime())
                    .add(JSON_REC_LOCATION, rec.getLocation())
                    .add(JSON_REC_TA_ONE, rec.getFirstTA())
                    .add(JSON_REC_TA_TWO, rec.getSecontTA()).build();
            recitationArrayBuilder.add(recJson);
        }
        JsonArray recArray = recitationArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_RECITATIONS, recArray)
                //NOW TO ADD THE REST OF THE JSON ARRAYS
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
    
    public void saveScheduleData(AppDataComponent data, String filePath) throws IOException{
        CourseData dataManager = (CourseData)data;
        
        CourseWorkspace workspace = (CourseWorkspace)app.getWorkspaceComponent();
        CourseSchedulePane schedulePane = workspace.getCourseSchedule();
        String startDate = schedulePane.getCalendarStart();
        String[] startArr = startDate.split("/");
        String startingMondayMonth = startArr[0];
        String startingMondayDay = startArr[1];
        
        String endDate = schedulePane.getCalendarEnd();
        String[] endArr = endDate.split("/");
        String endingFridayMonth = endArr[0];
        String endingFridayDay  = endArr[1];
        
        //NOW START USING LOOPS TO EXTRACT PROPER DATA
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> holidays = dataManager.getSchedules();
        for(Schedule schedule: holidays){
            if(schedule.getType().equals("Holiday")){
                String date = schedule.getDate();
                String[] dateArr = date.split("/");
                String month = dateArr[0];
                String day = dateArr[1];
                JsonObject recJson = Json.createObjectBuilder()
                        .add(JSON_ITEM_MONTH, month)
                        .add(JSON_ITEM_DAY, day)
                        .add(JSON_ITEM_TITLE, schedule.getTitle())
                        .add(JSON_ITEM_LINK, schedule.getLink())
                        .build();

                holidayArrayBuilder.add(recJson);
            }
        }
        JsonArray holidayArray = holidayArrayBuilder.build();
        //NOW LOOP FOR LECTURES
        JsonArrayBuilder lecturesArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> lectures = dataManager.getSchedules();
        for(Schedule lecture: lectures){
            if(lecture.getType().equals("Lecture")){
                String date = lecture.getDate();
                String[] dateArr = date.split("/");
                String month = dateArr[0];
                String day = dateArr[1];
                JsonObject recJson = Json.createObjectBuilder()
                        .add(JSON_ITEM_MONTH, month)
                        .add(JSON_ITEM_DAY, day)
                        .add(JSON_ITEM_TITLE, lecture.getTitle())
                        .add(JSON_ITEM_TOPIC, lecture.getTopic())
                        .add(JSON_ITEM_LINK, lecture.getLink())
                        .build();

                lecturesArrayBuilder.add(recJson);
            }
        }
        JsonArray lecturesArray = lecturesArrayBuilder.build();
        //NOW FOR REFERENCES
        JsonArrayBuilder referencesArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> references = dataManager.getSchedules();
        for(Schedule reference: references){
            if(reference.getType().equals("References")){
                String date = reference.getDate();
                String[] dateArr = date.split("/");
                String month = dateArr[0];
                String day = dateArr[1];
                JsonObject recJson = Json.createObjectBuilder()
                        .add(JSON_ITEM_MONTH, month)
                        .add(JSON_ITEM_DAY, day)
                        .add(JSON_ITEM_TITLE, reference.getTitle())
                        .add(JSON_ITEM_TOPIC, reference.getTopic())
                        .add(JSON_ITEM_LINK, reference.getLink())
                        .build();

                referencesArrayBuilder.add(recJson);
            }
        }
        JsonArray referencesArray = referencesArrayBuilder.build();
        
        //NOW RECITATIONS
        JsonArrayBuilder recitationsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        for(Recitation rec: recitations){
            
                JsonObject recJson = Json.createObjectBuilder()
                        .add(JSON_ITEM_MONTH, rec.getDayTime())
                        .add(JSON_ITEM_DAY, rec.getDayTime())
                        .add(JSON_ITEM_TITLE, rec.getSection())
                        .add(JSON_ITEM_TOPIC, rec.getSection())
                        .build();
                recitationsArrayBuilder.add(recJson);
        }
        JsonArray recitationsArray = recitationsArrayBuilder.build();
        
        //NOW HWS
        JsonArrayBuilder hwsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> hws = dataManager.getSchedules();
        for(Schedule hw: hws){
            if(hw.getType().equals("HW")){
                String date = hw.getDate();
                String[] dateArr = date.split("/");
                String month = dateArr[0];
                String day = dateArr[1];
                JsonObject recJson = Json.createObjectBuilder()
                        .add(JSON_ITEM_MONTH, month)
                        .add(JSON_ITEM_DAY, day)
                        .add(JSON_ITEM_TITLE, hw.getTitle())
                        .add(JSON_ITEM_TOPIC, hw.getTopic())
                        .add(JSON_ITEM_LINK, hw.getLink())
                        .add(JSON_ITEM_TIME, hw.getTime())
                        .add(JSON_ITEM_CRITERIA, hw.getCriteria())
                        .build();

                hwsArrayBuilder.add(recJson);
            }
        }
        JsonArray hwsArray = hwsArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_SCHEDULE_START_MONTH, startingMondayMonth)
		.add(JSON_SCHEDULE_START_DAY, startingMondayDay)
                .add(JSON_SCHEDULE_END_MONTH, endingFridayMonth)
                .add(JSON_SCHEDULE_END_DAY, endingFridayDay)
                .add(JSON_SCHEDULE_HOLIDAYS, holidayArray)
                .add(JSON_SCHEDULE_LECTURES, lecturesArray)
                .add(JSON_SCHEDULE_REFERENCES, referencesArray)
                .add(JSON_SCHEDULE_RECITATIONS, recitationsArray)
                .add(JSON_SCHEDULE_HWS, hwsArray)
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
    
    public void saveTeamsAndStudents(AppDataComponent data, String filePath) throws IOException{
        CourseData dataManager = (CourseData)data;
        
        JsonArrayBuilder teamsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> teams = dataManager.getTeams();
        for(Team team: teams){
            String colorStr = team.getColor();
            String red = String.valueOf(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ));
            String green = String.valueOf(Integer.valueOf( colorStr.substring( 3, 5 ), 16 ));
            String blue = String.valueOf(Integer.valueOf( colorStr.substring( 5, 6 ), 16 ));
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_TEAM_NAME, team.getName())
                    .add(JSON_TEAM_RED, red)
                    .add(JSON_TEAM_GREEN, green)
                    .add(JSON_TEAM_BLUE, blue)
                    .add(JSON_TEAM_TEXT_COLOR, team.getTextColor())
                    .build();

            teamsArrayBuilder.add(recJson);
        }
        JsonArray teamsArray = teamsArrayBuilder.build();
        
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = dataManager.getStudents();
        for(Student student: students){
//            String colorStr = team.getColor();
//            String red = String.valueOf(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ));
//            String green = String.valueOf(Integer.valueOf( colorStr.substring( 3, 5 ), 16 ));
//            String blue = String.valueOf(Integer.valueOf( colorStr.substring( 5, 7 ), 16 ));
            JsonObject recJson = Json.createObjectBuilder()
                    .add(JSON_STUDENT_LAST_NAME, student.getLastName())
                    .add(JSON_STUDENT_FIRST_NAME, student.getFirstName())
                    .add(JSON_STUDENT_TEAM_NAME, student.getTeam())
                    .add(JSON_STUDENT_ROLE, student.getRole())
                    .build();
            studentsArrayBuilder.add(recJson);
        }
        JsonArray studentsArray= studentsArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_TEAM_ARRAY, teamsArray)
		.add(JSON_STUDENT_ARRAY, studentsArray)
                .build();
        
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
    public void saveProjects(AppDataComponent data, String filePath){
        
    }
    
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        try{
            String courseDetailsPath = filePath + "CourseDetails.json";
            saveCourseDetailsJson(app, courseDetailsPath);
        } catch(Exception ex){
            
        }try{
            String taDataPath = filePath + "TAData.json";
            saveTAData(data, taDataPath);
        } catch(Exception ex){
            
        }
        try{
            String recDataPath = filePath + "RecData.json";
            saveRecitationData(data, recDataPath);
        } catch(Exception ex){
            
        }
        try{
            String scheduleDataPath = filePath + "ScheduleData.json";
            saveScheduleData(data, scheduleDataPath);
        } catch(Exception ex){
            
        }
            String teamsAndStudentsPath = filePath + "TeamsAndStudents.json";
            saveTeamsAndStudents(data, teamsAndStudentsPath);
    }
}
