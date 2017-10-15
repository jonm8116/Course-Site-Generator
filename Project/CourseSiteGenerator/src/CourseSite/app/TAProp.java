/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.app;

/**
 *
 * @author John
 */
public enum TAProp {
    // FOR SIMPLE OK/CANCEL DIALOG BOXES
    OK_PROMPT,
    CANCEL_PROMPT,
    
    // THESE ARE FOR TEXT PARTICULAR TO THE APP'S WORKSPACE CONTROLS
    TAS_HEADER_TEXT,
    NAME_COLUMN_TEXT,
    EMAIL_COLUMN_TEXT,
    NAME_PROMPT_TEXT,
    EMAIL_PROMPT_TEXT,
    ADD_BUTTON_TEXT,
    OFFICE_HOURS_SUBHEADER,
    OFFICE_HOURS_TABLE_HEADERS,
    DAYS_OF_WEEK,
    
    // THESE ARE FOR ERROR MESSAGES PARTICULAR TO THE APP
    MISSING_TA_NAME_TITLE,
    MISSING_TA_NAME_MESSAGE,
    MISSING_TA_EMAIL_TITLE,
    MISSING_TA_EMAIL_MESSAGE,
    TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE,
    TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE,
    //NEWLY ADDED SINGLETON DIALOG
    NOT_SELECTED_COMBOBOX_TITLE,
    NOT_SELECTED_COMBOBOX_MESSAGE,
    COMBOBOX_TIME_ERROR_TITLE,
    COMBOBOX_TIME_ERROR_MESSAGE,
    UG_CHECK_BOX,
    //ADD IN ERROR MESSAGES FOR RECITATION
    REC_MISSING_SECTION,
    REC_MISSING_INSTR_NAME,
    REC_MISSING_DAY_TIME,
    REC_MISSING_LOCATION,
    REC_MISSING_TA,
    MISSING_DATE,
    MISSING_TITLE,
    MISSING_TYPE,
    TEAM_MISSING_NAME,
    TEAM_MISSING_COLOR,
    TEAM_MISSING_TEXT_COLOR,
    TEAM_MISSING_LINK,
    STUDENT_MISSING_FIRST_NAME,
    STUDENT_MISSING_LAST_NAME,
    STUDENT_MISSING_ROLE,
    STUDENT_MISSING_TEAM,
    START_DATE_MONDAY,
    END_DATE_FRIDAY,
    SCHEDULE_CALENDAR_BOUNDARY_ERROR
}
