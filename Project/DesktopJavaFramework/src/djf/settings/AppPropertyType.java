package djf.settings;

/**
 * This enum provides properties that are to be loaded via
 * XML files to be used for setting up the application.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public enum AppPropertyType {
    // LOADED FROM app_properties.xml

    // INITIAL WINDOW TITLE AND LOGO
    APP_TITLE,
    APP_LOGO,
    
    // FOR FINDING THE CSS FILE
    APP_CSS,
    APP_PATH_CSS,

    // FILE TOOLBAR ICONS
    NEW_ICON,
    LOAD_ICON,
    SAVE_ICON,
    SAVE_AS_ICON,
    EXIT_ICON,
    //ADD EXPORT ICON
    EXPORT_ICON,
        
    // FILE TOOLBAR TOOLTIPS FOR BUTTONS
    NEW_TOOLTIP,
    LOAD_TOOLTIP,
    SAVE_TOOLTIP,
    SAVE_AS_TOOLTIP,
    EXPORT_TOOLTIP,
    EXIT_TOOLTIP,
    //ADDED TOOLTIPS
    UNDO_TOOLTIP,
    REDO_TOOLTIP,
    ABOUT_TOOLTIP,
	
    // ERROR MESSAGES AND TITLES FOR DIALOG BOXES
    NEW_ERROR_MESSAGE,
    NEW_ERROR_TITLE,
    LOAD_ERROR_MESSAGE,
    LOAD_ERROR_TITLE,
    SAVE_ERROR_MESSAGE,
    SAVE_ERROR_TITLE,
    PROPERTIES_LOAD_ERROR_MESSAGE,
    PROPERTIES_LOAD_ERROR_TITLE,
	
    // AND VERIFICATION MESSAGES AND TITLES
    NEW_COMPLETED_MESSAGE,
    NEW_COMPLETED_TITLE,
    LOAD_COMPLETED_MESSAGE,
    LOAD_COMPLETED_TITLE,
    SAVE_COMPLETED_MESSAGE,
    SAVE_COMPLETED_TITLE,	
    SAVE_UNSAVED_WORK_TITLE,
    SAVE_UNSAVED_WORK_MESSAGE,
    SAVE_WORK_TITLE,
    LOAD_WORK_TITLE,

    // DATA FILE EXTENSIONS AND DESSCRIPTIONS
    WORK_FILE_EXT,
    WORK_FILE_EXT_DESC,
    //ADD NEW OPTIONS FOR COMBOBOX PROCEED
    COMBOBOX_PROCEED_TITLE,
    COMBOBOX_PROCEED_MESSAGE,
    //TABS
    COURSE_DETAILS_TAB,
    COURSE_TA_DATA_TAB,
    COURSE_RECITATION_TAB,
    COURSE_SCHEDULE_TAB,
    COURSE_PROJECT_TAB,
    //COURSEDETAILSPANE
    COURSE_INFO_LABEL,
    COURSE_SUBJECT_LABEL,
    COURSE_SUBJECT_NUMBER,
    COURSE_SEMESTER_LABEL,
    COURSE_YEAR_LABEL,
    COURSE_INFO_TITLE,
    COURSE_INSTR_NAME,
    COURSE_INSTR_HOME,
    COURSE_EXPORT_DIR_LABEL,
    COURSE_EXPORT_DIR_BUTTON,
    COURSE_SITE_TEMPLATE_TITLE,
    COURSE_SITE_TEMPLATE_TEXT,
    SITE_TEMPLATE_BUTTON_TEXT,
    SITE_PAGES_LABEL,
    //COURSE DETAILS TABLE VIEW STUFF
    USE_COLUMN_TEXT,
    NAVBAR_COLUMN_TEXT,
    FILE_NAME_COLUMN_TEXT,
    SCRIPT_COLUMN_TEXT,
    //COURSE DETAILS PAGE STYLE PANE
    PAGE_STYLE_LABEL,
    BANNER_IMG_LABEL,
    BANNER_IMG_BUTTON_TEXT,
    RIGHT_FOOT_IMG_BUTTON_TEXT,
    LEFT_FOOT_IMG_BUTTON_TEXT,
    LEFT_FOOT_IMG_LABEL,
    RIGHT_FOOT_IMG_LABEL,
    STYLESHEET_LABEL,
    FOOTNOTE_LABEL,
    //RECITATION TAB DATA
    RECITATION_LABEL,
    RECITATION_MINUS_LABEL,
    REC_TABLE_SECTION_TEXT,
    REC_TABLE_INSTRUCTOR_TEXT,
    REC_TABLE_DAYTIME_TEXT,
    REC_TABLE_LOCATION_TEXT,
    REC_TABLE_TA_TEXT,
    ADD_EDIT_LABEL,
    CLEAR_LABEL,
    //SCHEDULE TAB DATA
    SCHEDULE_LABEL,
    CALENDAR_BOUNDARIES_LABEL,
    SCHEDULE_ITEMS_LABEL,
    CALENDAR_START_TIME_LABEL,
    CALENDAR_END_TIME_LABEL,
    CALENDAR_TABLE_TYPE,
    CALENDAR_TABLE_DATE,
    CALENDAR_TABLE_TITLE,
    CALENDAR_TABLE_TOPIC,
    TIME_LABEL,
    LINK_LABEL,
    CRITERIA_LABEL,
    //PROJECT TAB
    PROJECT_LABEL,
    TEAMS_LABEL,
    NAME_COLUMN_TEXT,
    COLOR_COLUMN_TEXT,
    TEXT_COLOR_COLUMN_TEXT,
    LINK_COLUMN_TEXT,
    COLOR_LABEL,
    TEXT_COLOR_LABEL,
    STUDENT_LABEL,
    FIRST_NAME_LABEL,
    LAST_NAME_LABEL,
    TEAM_LABEL,
    ROLE_LABEL,
    //ICONS
    UNDO_ICON,
    REDO_ICON,
    ABOUT_ICON,
    //ABOUT MESSAGE
    ABOUT_MESSAGE,
    ABOUT_TITLE
}