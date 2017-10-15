/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import CourseSite.app.CourseApp;
import CourseSite.data.CourseData;
import CourseSite.data.CourseSitePages;
import CourseSite.data.Recitation;
import CourseSite.data.Schedule;
import CourseSite.data.Student;
import CourseSite.data.TeachingAssistant;
import CourseSite.data.Team;
import djf.components.AppDataComponent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John
 */
public class TestSaveTest {
    
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

    public TestSaveTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadData method, of class TestSave.
     */
    @Test
    public void testLoadData() throws Exception {
        System.out.println("loadData");
        String filePath = "./work/SiteSaveTest.json";
        CourseApp app = new CourseApp();
        app.loadProperties("app_properties.xml");
        //app.buildAppComponentsHook();
        CourseData data = new CourseData(app);
        //app.buildAppComponentsHook();
        TestSave instance = new TestSave(app);
        //AppDataComponent data = app.getDataComponent();
        instance.loadData(data, filePath);
        
        CourseData loadedData = (CourseData)data;
        //GET ALL DATA STRUCTURES
        ObservableList<TeachingAssistant> tas = loadedData.getTeachingAssistants();
        ObservableList<CourseSitePages> sitePages = loadedData.getSitePages();
        ObservableList<Recitation> recs = loadedData.getRecitations();
        ObservableList<Schedule> schedules = loadedData.getSchedules();
        ObservableList<Team> teams = loadedData.getTeams();
        ObservableList<Student> students = loadedData.getStudents();
        
        //LOADED FROM FILE
//        ObservableList<TeachingAssistant> expectTA = FXCollections.observableArrayList();
//        ObservableList<CourseSitePages> expectSite = FXCollections.observableArrayList();
//        ObservableList<Recitation> expectRec = FXCollections.observableArrayList();
//        ObservableList<Schedule> expectSchedule = FXCollections.observableArrayList();
//        ObservableList<Team> expectTeam = FXCollections.observableArrayList();
//        ObservableList<Student> expectStudent = FXCollections.observableArrayList();
        
        //GET ACTUAL VALUES FROM JSON FILE
        JsonObject json = loadJSONFile(filePath);
        JsonArray ugTAs = json.getJsonArray(JSON_UNDERGRAD_TAS);
        FXCollections.reverse(tas);
        for (int i = 0; i < ugTAs.size(); i++) {
            JsonObject jsonTA = ugTAs.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
//            TeachingAssistant ta = new TeachingAssistant(name, email);
//            expectTA.add(ta);
            assertEquals(tas.get(i).getName(), name);
            assertEquals(tas.get(i).getEmail(), email);
        }
        
        JsonArray jsonSitePageArray = json.getJsonArray(JSON_SITE_PAGES);
        for(int i=0; i< jsonSitePageArray.size(); i++){
            JsonObject jsonSitePage = jsonSitePageArray.getJsonObject(i);
            String navbar = jsonSitePage.getString(JSON_NAVBAR);
            String filename = jsonSitePage.getString(JSON_FILENAME);
            String script = jsonSitePage.getString(JSON_SCRIPT);
            //CourseSitePages sitePage = new CourseSitePages(navbar, filename, script, true);
            assertEquals(sitePages.get(i).getNavbarTitle(), navbar);
            assertEquals(sitePages.get(i).getFileName(), filename);
            assertEquals(sitePages.get(i).getScript(), script);
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
           // Recitation rec = new Recitation(section, instructor, dayTime, location, firstTA, secondTA);
            assertEquals(recs.get(i).getSection(), section);
            assertEquals(recs.get(i).getInstructor(), instructor);
            assertEquals(recs.get(i).getLocation(), location);
            assertEquals(recs.get(i).getDayTime(), dayTime);
            assertEquals(recs.get(i).getFirstTA(), firstTA);
            assertEquals(recs.get(i).getSecontTA(), secondTA);
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
            assertEquals(schedules.get(i).getType(), type);
            assertEquals(schedules.get(i).getDate(), date);
            assertEquals(schedules.get(i).getTitle(), title);
            assertEquals(schedules.get(i).getTopic(), topic);
        }
         //TEAMS
        JsonArray jsonTeamsArray = json.getJsonArray(JSON_TEAMS);
        for(int i=0; i< jsonTeamsArray.size(); i++){
            JsonObject jsonTeam = jsonTeamsArray.getJsonObject(i);
            String name = jsonTeam.getString(JSON_NAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXT_COLOR);
            String link = jsonTeam.getString(JSON_LINK);
//            Team team = new Team(name, color, textColor, link);
            assertEquals(teams.get(i).getName(), name);
            assertEquals(teams.get(i).getColor(), color);
            assertEquals(teams.get(i).getTextColor(), textColor);
            assertEquals(teams.get(i).getLink(), link);
        }
        //STUDENTS
        JsonArray jsonStudentsArray = json.getJsonArray(JSON_STUDENTS);
        for(int i=0; i<jsonStudentsArray.size(); i++){
            JsonObject jsonStudent = jsonStudentsArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_FIRST_NAME);
            String lastName = jsonStudent.getString(JSON_LAST_NAME);
            String team = jsonStudent.getString(JSON_TEAM);
            String role =  jsonStudent.getString(JSON_ROLE);
//            Student student = new Student(firstName, lastName, team, role);
            assertEquals(students.get(i).getFirstName(), firstName);
            assertEquals(students.get(i).getLastName(), lastName);
            assertEquals(students.get(i).getTeam(), team);
            assertEquals(students.get(i).getRole(), role);
        }
//        CourseData data = (CourseData)app.getDataComponent();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * Test of saveData method, of class TestSave.
     */
//    @Test
//    public void testSaveData() throws Exception {
//        System.out.println("saveData");
//        AppDataComponent data = null;
//        String filePath = "";
//        TestSave.saveData(data, filePath);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }

    /**
     * Test of main method, of class TestSave.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        TestSave.main(args);
//        try{
//            testLoadData();
//        } catch(Exception ex){
//            System.out.print("Exception thrown");
//        }
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
