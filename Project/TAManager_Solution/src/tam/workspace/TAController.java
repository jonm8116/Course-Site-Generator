package tam.workspace;

import djf.controller.AppFileController;
import djf.ui.AppGUI;
import static tam.TAManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.style.TAStyle;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static tam.style.TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;
import tam.transaction.Add_Transaction;
import tam.transaction.Delete_Transaction;
import tam.transaction.Toggle_Transaction;
import tam.transaction.Update_Transaction;
import tam.workspace.TAWorkspace;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class TAController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    TAManagerApp app;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }
    
    /**
     * This helper method should be called every time an edit happens.
     */    
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA
            if(!email.matches("^[A-Za-z0-9._\\%-]+@[A-Za-z0-9.-]+\\.[a-z]{3}$")){
                
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));   
            } 
            else{
                data.addTA(name, email);
                
                TeachingAssistant newTA = new TeachingAssistant(name, email);
                Add_Transaction addTransaction = new Add_Transaction(newTA, data);
                jTPS transaction = workspace.getTransaction();
                transaction.addTransaction(addTransaction);
                
                // CLEAR THE TEXT FIELDS
                nameTextField.setText("");
                emailTextField.setText("");

                // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
                nameTextField.requestFocus();

                // WE'VE CHANGED STUFF
                markWorkAsEdited();
            }
        }
    }

    /**
     * This function provides a response for when the user presses a
     * keyboard key. Note that we're only responding to Delete, to remove
     * a TA.
     * 
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE) {
            // GET THE TABLE
            TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
            TableView taTable = workspace.getTATable();
            
            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant)selectedItem;
                String taName = ta.getName();
                TAData data = (TAData)app.getDataComponent();
                data.removeTA(taName);
                
                //SAVE STRING PROP TO PUT BACK LATER
                Label cellLabel = null;
                ArrayList<String> cellKeys = new ArrayList<>();
                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
                HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
                for (Label label : labels.values()) {
                    if (label.getText().equals(taName)
                    || (label.getText().contains(taName + "\n"))
                    || (label.getText().contains("\n" + taName))) {
                        data.removeTAFromCell(label.textProperty(), taName);
                        //SAVE CELLPROP
                        cellLabel = label;
                        for(String key: labels.keySet()){
                            if(labels.get(key).equals(label)){
                                cellKeys.add(key);
                            }
                        }
                    }
                }
                // WE'VE CHANGED STUFF
                Delete_Transaction deleteTransaction = new Delete_Transaction(ta, data, cellLabel, cellKeys);
                jTPS transaction = workspace.getTransaction();
                transaction.addTransaction(deleteTransaction);
                
                markWorkAsEdited();
            }
        }
    }
    
    /**This function will be used to check if a user pressed 
     * control Z or control Y for undo or redo for a transaction
     * 
     * @param code
     * @param transaction 
     * @param e
     */
    public void transactionKeyPress(KeyCode code, jTPS transaction, boolean isControlDown){
        if(code == KeyCode.Z && isControlDown){
            transaction.undoTransaction();
        } 
    }
    
    public void handleTableRowSelect(javafx.scene.control.Button button, TeachingAssistant ta, TextField email, TextField name){
       try{
        email.setText(ta.getEmail());
        name.setText(ta.getName());
        button.setText("Update TA");
       } catch(NullPointerException ex){
           
       }
    }
    
    public void handleEditTA(TeachingAssistant oldTA){
        //FIRST PERFORM ALL FUNCTIONS FOR IN HANDLEADDTA
        
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TAData data = (TAData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));                        
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (!((!(name.equals(oldTA.getName())) && email.equals(oldTA.getEmail())) || (name.equals(oldTA.getName()) && !(email.equals(oldTA.getEmail()))))) {    //origin: data.containsTA(name, email)
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA
            if(!email.matches("^[A-Za-z0-9._\\%-]+@[A-Za-z0-9.-]+\\.[a-z]{3}$")){
                
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));   
            } 
            else{
                
                ObservableList<TeachingAssistant> list = data.getTeachingAssistants();
                list.remove(oldTA);
                data.addTA(name, email);
                
                //LOOP THRU OFFICE HOURS ON RIGHT SIDE TO CHANGE TA
                Iterator<Entry<String, StringProperty>> it = data.getOfficeHours().entrySet().iterator();
        
            //MAKE ARRAYLIST TO HOLD ALL KEYS TO TOGGLE OFFICE HOURS
            ArrayList<String> cellKeys = new ArrayList<>();
        while(it.hasNext()){
           
            String key = it.next().getKey();
            StringProperty value = data.getOfficeHours().get(key);
            if(value.getValue().contains(oldTA.getName())){
                data.removeTAFromCell(value, oldTA.getName());
                data.toggleTAOfficeHours(key, name);
                cellKeys.add(key);
            }

        }
            
            TeachingAssistant newTA = new TeachingAssistant(name, email);
            //MAKE TRANSACTION OBJECT FOR UNDOING
            jTPS transaction = workspace.getTransaction();
            Update_Transaction updateTransaction = new Update_Transaction(newTA, this, data, oldTA, cellKeys, workspace);
            transaction.addTransaction(updateTransaction);
        
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

            // WE'VE CHANGED STUFF
            markWorkAsEdited();
            //SORT TA ITEMS
            ObservableList<TeachingAssistant> taList = data.getTeachingAssistants();
            Collections.sort(taList);
            
            }
        }
    }
    
    public void submitComboBox(ComboBox taStartTimes, ComboBox taEndTimes, Button submitButton, ObservableList<String> options, TAData data, TAManagerApp app){
        //VAR NAMES:
//            ComboBox taStartTimes;
//            ComboBox taEndTimes;
//            Button submitButton;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if(taStartTimes.getSelectionModel().isEmpty() || taEndTimes.getSelectionModel().isEmpty()){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(NOT_SELECTED_COMBOBOX_TITLE), props.getProperty(NOT_SELECTED_COMBOBOX_MESSAGE));
            //System.out.println("in here");    //debug  
        } else{
            boolean isStartPM = false;
            boolean isEndPM = false;
            
            int taStartIndex = taStartTimes.getSelectionModel().getSelectedIndex();
            int taEndIndex = taEndTimes.getSelectionModel().getSelectedIndex();

            String startTime = options.get(taStartIndex);
            String endTime = options.get(taEndIndex);
            //startTime
            if(startTime.contains("pm")){
                isStartPM = true;
            }
            if(endTime.contains("pm")){
                isEndPM = true;
            }
            //DELETE NON NUMERIC CHARS            
            startTime = startTime.replaceAll("[^\\d]", "");
            endTime = endTime.replaceAll("[^\\d]", "");

            int taStartTime = Integer.parseInt(startTime);
            int taEndTime = Integer.parseInt(endTime);
            taStartTime = taStartTime / 100;
            taEndTime = taEndTime / 100;
            
            if(taStartTime == 12){
                taStartTime = taStartTime - 12;
            } 
            if(taEndTime == 12){
                taEndTime = taEndTime - 12;
            }
            if(isStartPM){
                taStartTime = taStartTime + 12;
            }
            if(isEndPM){
                taEndTime = taEndTime +12;
            }
            
            if(taStartTime >= taEndTime){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(COMBOBOX_TIME_ERROR_TITLE), props.getProperty(COMBOBOX_TIME_ERROR_MESSAGE));
            } else{
                boolean proceed=true;
                if(taStartTime > data.getStartHour() || taEndTime < data.getEndHour()){
                    proceed = app.getGUI().getFileController().promptProceedComboBox();
                }
                if(proceed){
                    HashMap<String, StringProperty> officeHours = data.getOfficeHours();
                    //MAKE HASHMAP DEEP COPY
                    HashMap<String, StringProperty> editedOfficeHours = new HashMap<String, StringProperty>(officeHours);

                    //THIS CONTAINS THE ORIGINAL START TIME
                    int originalStartTime = data.getStartHour();

                    startTime = String.valueOf(taStartTime);
                    endTime = String.valueOf(taEndTime);
                    //RESET WORKSPACE
                    app.getWorkspaceComponent().resetWorkspace();
                    data.initHours(startTime, endTime);
                    //data.setOfficeHours(editedOfficeHours);
                    app.getWorkspaceComponent().reloadWorkspace(data);
                    //Initial times: (don't need to reassign taStartTime * taEndTime
                    //Start: 9AM
                    //End: 8pm
                    int startTimeDifference = 9 - taStartTime;

                    TAWorkspace wrkspc = (TAWorkspace)app.getWorkspaceComponent();
                    HashMap<String, Pane> officeHoursGridTACellPanes = wrkspc.getOfficeHoursGridTACellPanes();



                    HashMap<String, StringProperty> newOfficeHours = new HashMap<>();
                    for(Entry<String, StringProperty> entry: officeHours.entrySet()){

                        StringProperty strProp = entry.getValue();
                        String value = strProp.getValue();
                        if(value.contains("MON") || value.contains("TUES") || value.contains("WED") || 
                                value.contains("THURS") || value.contains("FRI") || value.contains("am") || value.contains("pm") ||
                                value.contains("Start") || value.contains("End")){

                        } else{
                            strProp.setValue("");
                            entry.setValue(strProp);
                        }
                    }

                    //TAKE AWAY STATEMENTS FOR NOW AND USE OLD GRID
                    //officeHours.clear();
                    //officeHours.putAll(editedOfficeHours);


                    for(Entry<String, StringProperty> entry: editedOfficeHours.entrySet()){
                        String key = (String)entry.getKey();
                        String[] keyArr = key.split("_");
                        String keyCol = keyArr[0];
                        String keyRow = keyArr[1];

                        StringProperty value = (StringProperty)entry.getValue();
                        String cellName = value.getValue();

                        //String day;
                        if(!(cellName.contains("MON") || cellName.contains("TUES") || cellName.contains("WED") || 
                                cellName.contains("THURS") || cellName.contains("FRI") || cellName.contains("am") || cellName.contains("pm") ||
                                cellName.contains("Start") || cellName.contains("End") || keyCol.contains("0") || keyCol.contains("1") ||
                                keyRow.contains("0"))){

                            int keyRowVal = Integer.parseInt(keyRow);
                            //FORMULA: (initialTime - editedTime) * 2
                            //ADD OR SUB THIS VALUE FROM GIVEN KEY
                            int timeDifference = originalStartTime - taStartTime;
                            int changeRow = Math.abs(timeDifference * 2);

                            keyRowVal = keyRowVal + changeRow;
                            key = keyArr[0] + "_" + String.valueOf(keyRowVal);

                            try{
                                if(!cellName.equals("")){
                                   data.toggleTAOfficeHours(key, cellName);
                                }
                            } catch(NullPointerException ex){

                            }
                        }
                        //MARK FILE AS EDITED
                        app.getGUI().getFileController().markAsEdited(app.getGUI());
                    }
                    //data.addOfficeHoursReservation("MONDAY", "12_00am", "John Kim"); //debug
                    //data.addOfficeHoursReservation("TUESDAY", "11_00am", "Anika");   //debug
                }
            }
        }
        
        
    }
    
    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant)selectedItem;
            String taName = ta.getName();
            TAData data = (TAData)app.getDataComponent();
            String cellKey = pane.getId();
            
            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            data.toggleTAOfficeHours(cellKey, taName);
            
            Toggle_Transaction toggleTransaction = new Toggle_Transaction(cellKey, taName, data);
            jTPS transaction = workspace.getTransaction();
            transaction.addTransaction(toggleTransaction);
            
            // WE'VE CHANGED STUFF
            markWorkAsEdited();
        }
    }
    
    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        TAData data = (TAData)app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        TAData data = (TAData)app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        
        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);
        
        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        
        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }
}