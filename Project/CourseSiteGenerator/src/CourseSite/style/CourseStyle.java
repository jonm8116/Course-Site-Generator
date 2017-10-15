/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.style;

import CourseSite.UI.CourseDetailsPane;
import CourseSite.UI.CourseProjectsPane;
import CourseSite.UI.CourseRecitationPane;
import CourseSite.UI.CourseSchedulePane;
import CourseSite.UI.CourseTADataPane;
import CourseSite.app.CourseApp;
import CourseSite.data.TeachingAssistant;
import CourseSite.workspace.CourseWorkspace;
import djf.AppTemplate;
import djf.components.AppStyleComponent;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 *
 * @author John
 */
public class CourseStyle extends AppStyleComponent{
    
    // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE
    
    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";
    
    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    // FOR HIGHLIGHTING CELLS, COLUMNS, AND ROWS
    public static String CLASS_HIGHLIGHTED_GRID_CELL = "highlighted_grid_cell";
    public static String CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN = "highlighted_grid_row_or_column";
    
    
    //ADD STYLE FOR COURSE DETAILS TAB
    public static String COURSE_DETAILS_LABELS = "course_info_labels";
    public static String COURSE_DETAILS_COURSE_INFO_PANE = "course_info_pane";
    
    public static String COURSE_DETAILS_SCROLLPANE = "course_details_scrollpane";
    
    public static String COURSE_HEADER_LABELS = "course_header_labels";
    
    public static String COURSE_LABEL_TEXTS = "course_label_texts";
    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;
    
    /**
     * This constructor initializes all style for the application.
     * 
     * @param initApp The application to be stylized.
     */
    public CourseStyle(CourseApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        try{
            initTAWorkspaceStyle();
        } catch(ClassCastException ex){
            //int i = 0;
        }
        initCourseDetailsStyle();
        initCourseHeaderLabels();
    }
    
    private void initCourseHeaderLabels(){
        CourseWorkspace wrkspace = (CourseWorkspace)app.getWorkspaceComponent();
        CourseDetailsPane courseDetails = (CourseDetailsPane) wrkspace.getCourseDetailsTab();
        //CourseTADataPane taData = wrkspace.getTADataTabPane();
        CourseRecitationPane recPane = wrkspace.getCourseRecitation();
        CourseSchedulePane schedulePane = wrkspace.getCourseSchedule();
        CourseProjectsPane projectPane = wrkspace.getCourseProjects();
        
        courseDetails.getCourseInfoLabel().getStyleClass().add( COURSE_HEADER_LABELS);
        recPane.getRecitationLabel().getStyleClass().add( COURSE_HEADER_LABELS);
        schedulePane.getScheduleLabel().getStyleClass().add( COURSE_HEADER_LABELS);
        projectPane.getProjectsLabel().getStyleClass().add( COURSE_HEADER_LABELS);
        
        //try to do other labels
        //wrkspace.getDetailsTab().getStyleClass().add(COURSE_LABEL_TEXTS);
        
        
    }
    
    private void initCourseDetailsStyle() {
        CourseWorkspace wrkspace = (CourseWorkspace)app.getWorkspaceComponent();
        CourseDetailsPane courseDetails = (CourseDetailsPane) wrkspace.getCourseDetailsTab();
        setStyleClassCourseDetails(courseDetails.getLabelList(), COURSE_DETAILS_LABELS);
        
        courseDetails.getCourseInfoPane().getStyleClass().add(COURSE_DETAILS_COURSE_INFO_PANE);
        courseDetails.getParentScrollPane().getStyleClass().add(COURSE_DETAILS_SCROLLPANE);
        
        
        
    }
    
    /**
     * This function specifies all the style classes for
     * all user interface controls in the workspace.
     */
    private void initTAWorkspaceStyle() {
        // LEFT SIDE - THE HEADER
        //CourseTADataPane workspaceComponent = (CourseTADataPane)app.getWorkspaceComponent();
        CourseWorkspace wrkspace = (CourseWorkspace)app.getWorkspaceComponent();
        //CourseTADataPane workspaceComponent = (CourseTADataPane) wrkspace.getTADataTab();
        //CourseTADataPane workspaceComponent = (CourseTADataPane)app.getTADataComponent();
        CourseTADataPane workspaceComponent = wrkspace.getTADataTabPane();
        
        workspaceComponent.getTAsHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getTAsHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);

        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = workspaceComponent.getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        workspaceComponent.getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        workspaceComponent.getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getEmailTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getAddButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
        workspaceComponent.getClearButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);

        // RIGHT SIDE - THE HEADER
        workspaceComponent.getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        
        
        //NEW ATTEMPT AT WORKING WITH IT
        
        
    }
    
    /**
     * This method initializes the style for all UI components in
     * the office hours grid. Note that this should be called every
     * time a new TA Office Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        //CourseWorkspace wrkspace = (CourseWorkspace)app.getWorkspaceComponent();
        //CourseTADataPane workspaceComponent = (CourseTADataPane) wrkspace.getTADataTab();
        CourseWorkspace wrkspace = (CourseWorkspace)app.getWorkspaceComponent();
        CourseTADataPane workspaceComponent = wrkspace.getTADataTabPane();
        
        workspaceComponent.getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }
    
    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node)nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }
    
    private void setStyleClassCourseDetails(ObservableList<Label>list, String styleClass){
        for(Object components: list){
            Label label = (Label)components;
            label.getStyleClass().add(styleClass);
        }
    }
}
