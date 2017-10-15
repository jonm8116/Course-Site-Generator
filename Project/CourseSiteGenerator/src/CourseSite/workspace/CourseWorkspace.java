/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.workspace;

/**
 *
 * @author John
 */
import CourseSite.UI.CourseDetailsPane;
import CourseSite.UI.CourseProjectsPane;
import CourseSite.UI.CourseRecitationPane;
import CourseSite.UI.CourseSchedulePane;
import CourseSite.UI.CourseTADataPane;
import CourseSite.app.CourseApp;
import CourseSite.data.CourseData;
import CourseSite.files.CourseFiles;
import CourseSite.style.CourseStyle;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.settings.AppPropertyType;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;

public class CourseWorkspace extends AppWorkspaceComponent{
    //INSTANCE VARIABLES
    CourseApp app;
    TabPane tabs;
    Tab taDataTab;
    Tab recitationDataTab;
    Tab scheduleDataTab;
    Tab projectDataTab;
    Tab courseDetailsTab;
    PropertiesManager props;
    //TAB DATA
    CourseTADataPane taData;
    CourseDetailsPane courseDetails;
    CourseRecitationPane courseRecitation;
    CourseSchedulePane courseSchedule;
    CourseProjectsPane courseProjects;
    
    
    
    public CourseWorkspace(CourseApp initApp){
        app = initApp;
        tabs = app.getGUI().getTabPane();
        tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        //INITIALIZE TABPANE TABS
        taDataTab = new Tab();
        recitationDataTab = new Tab();
        scheduleDataTab = new Tab();
        projectDataTab = new Tab();
        courseDetailsTab = new Tab();
        //INITIALIZE PROPERTIES MANAGER
        props = PropertiesManager.getPropertiesManager();
        
        
        taData = new CourseTADataPane(app);
        taDataTab.setContent(taData.getWorkspace());
        taDataTab.setText(props.getProperty(AppPropertyType.COURSE_TA_DATA_TAB.toString()));
        //Set other tabs
        recitationDataTab.setText(props.getProperty(AppPropertyType.COURSE_RECITATION_TAB.toString()));
        scheduleDataTab.setText(props.getProperty(AppPropertyType.COURSE_SCHEDULE_TAB.toString()));
        projectDataTab.setText(props.getProperty(AppPropertyType.COURSE_PROJECT_TAB.toString()));
        
//        if(app.getFileName().equals("app_properties_german.xml")){
//            
//        } else{
            courseDetails = new CourseDetailsPane(app);
            courseDetailsTab.setContent(courseDetails.getParentBorderPane());
            courseDetailsTab.setText(props.getProperty(AppPropertyType.COURSE_DETAILS_TAB.toString()));
            courseRecitation = new CourseRecitationPane(app);
            recitationDataTab.setContent(courseRecitation.getParentBorderPane());
            courseSchedule = new CourseSchedulePane(app);
            scheduleDataTab.setContent(courseSchedule.getParentBorderPane());
            courseProjects = new CourseProjectsPane(app);
            projectDataTab.setContent(courseProjects.getParentBorderPane());
        //}
        
//        CourseController control = taData.getController();
//        control.startupTAGrid((CourseData)app.getDataComponent(), app, taData.getOfficeHoursGridTACellPanes(), taData);
        //ADD TABS
        tabs.getTabs().add(courseDetailsTab);
        tabs.getTabs().add(taDataTab);
        tabs.getTabs().add(recitationDataTab);
        tabs.getTabs().add(scheduleDataTab);
        tabs.getTabs().add(projectDataTab);
        
        
        workspace = app.getGUI().getWorkspacePane();
        //ADD ALL STUFF FOR WORKSPACE BELOW
        //workspace = new Pane();
        //workspace.getChildren().add(tabs);
        
    }
    //EXTRA CONSTRUCTOR TO HELP WITH TEST SAVE
    public CourseWorkspace(CourseApp initApp, boolean value){
        taData = new CourseTADataPane(initApp);
    }
    //TAB GETTERS
//    Tab taDataTab;
//    Tab recitationDataTab;
//    Tab scheduleDataTab;
//    Tab projectDataTab;
//    Tab courseDetailsTab;
    public Tab getDataTab(){
        return taDataTab;
    }
    public Tab getRecitationTab(){
        return recitationDataTab;
    }
    public Tab getScheduleTab(){
        return scheduleDataTab;
    }
    public Tab getProjectTab(){
        return projectDataTab;
    }
    public Tab getDetailsTab(){
        return courseDetailsTab;
    }
    
    
    public CourseRecitationPane getCourseRecitation() {
        return courseRecitation;
    }

    public CourseSchedulePane getCourseSchedule() {
        return courseSchedule;
    }

    public CourseProjectsPane getCourseProjects() {
        return courseProjects;
    }

    //GETTERS
    public CourseDetailsPane getCourseDetailsTab(){
        return courseDetails;
    }
    
    public CourseTADataPane getTADataTabPane(){
        return taData;
    }
    
    public Pane getTADataTab(){
        return (Pane) taDataTab.getContent();
    }
    
    
    
    @Override
    public void resetWorkspace() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        taData.getOfficeHoursGridPane().getChildren().clear();
        taData.getOfficeHoursGridTimeHeaderPanes().clear();
        taData.getOfficeHoursGridTimeHeaderLabels().clear();
        taData.getOfficeHoursGridDayHeaderPanes().clear();
        taData.getOfficeHoursGridDayHeaderLabels().clear();
        taData.getOfficeHoursGridTimeCellPanes().clear();
        taData.getOfficeHoursGridTimeCellLabels().clear();
        taData.getOfficeHoursGridTACellPanes().clear();
        taData.getOfficeHoursGridTACellLabels().clear();
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        CourseWorkspace wrkspace = new CourseWorkspace(app);
//        CourseData appData = new CourseData(app);
//        CourseFiles file = new CourseFiles(app);
//        CourseStyle style = new CourseStyle(app);
//        
//        app.setWorkspaceComponent(wrkspace);
//        app.setDataComponent(appData);
//        app.setFileComponent(file);
//        app.setStyleComponent(style);
//        CourseTADataPane taData = getTADataTabPane();
//        CourseData data = (CourseData)dataComponent;
//        taData.reloadOfficeHoursGrid(data);
          
        CourseData data = (CourseData)dataComponent;
        taData.reloadOfficeHoursGrid(data);
    }
    
}
