/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.UI;

import CourseSite.app.CourseApp;
import CourseSite.app.TAProp;
import CourseSite.data.CourseData;
import CourseSite.workspace.CourseController;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jtps.jTPS;
import properties_manager.PropertiesManager;
import CourseSite.data.TeachingAssistant;
import CourseSite.style.CourseStyle;
import CourseSite.workspace.CourseController;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 *
 * @author John
 */
public class CourseTADataPane {
    
    protected Pane workspace;
    
    // THIS DENOTES THAT THE USER HAS BEGUN WORKING AND
    // SO THE WORKSPACE IS VISIBLE AND USABLE
    protected boolean workspaceActivated;
    
    
    CourseApp app;
    CourseController controller;
     // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    //ADD NEW CHECKBOX COLUMN
    TableColumn undergradCheckBox;

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    //ADD CLEAR BUTTON TO CLEAR CURRENT TA
    //HBox clearBox;
    Button clearButton;
    
    //MAKE JTPS OBJECT DECLARATION
    jTPS transaction;
    
    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;
    //DEFINE COMBOBOX
    ComboBox taStartTimes;
    ComboBox taEndTimes;
        //INSTANTIATE SUBMIT BUTTON FOR COMBOBOX
    Button submitButton;
    
    
    /**
     * The contstructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public CourseTADataPane(CourseApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //INITIALIZE JTPS OBJECT
        transaction = new jTPS();
        
        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(TAProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CourseData data = (CourseData) app.getDataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(TAProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(TAProp.EMAIL_COLUMN_TEXT.toString());
        String undergradColumnText = props.getProperty(TAProp.UG_CHECK_BOX.toString());
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        undergradCheckBox = new TableColumn(undergradColumnText);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        undergradCheckBox.setCellFactory( CheckBoxTableCell.forTableColumn(undergradCheckBox) );
        taTable.setEditable(true);
        
        taTable.getColumns().add(undergradCheckBox);
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        
        

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(TAProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(TAProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(TAProp.ADD_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        addBox = new HBox();
        //ADD BOX FOR CLEAR BUTTON
        //clearBox = new HBox();
        clearButton = new Button("Clear");
        
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.275));  //origin: .275
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        //ADD CLEAR BUTTON
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.getChildren().add(clearButton);
        
        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(TAProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();
        
        //INSTANTIATE COMBOBOX OPTIONS
        //
        ObservableList<String> options = 
                FXCollections.observableArrayList(
                    "12:00am",
                    "1:00am",
                    "2:00am",
                    "3:00am",
                    "4:00am",
                    "5:00am",
                    "6:00am",
                    "7:00am",
                    "8:00am",
                    "9:00am",
                    "10:00am",
                    "11:00am",
                    "12:00pm",
                    "1:00pm",
                    "2:00pm",
                    "3:00pm",
                    "4:00pm",
                    "5:00pm",
                    "6:00pm",
                    "7:00pm",
                    "8:00pm",
                    "9:00pm",
                    "10:00pm",
                    "11:00pm"
                );
        //INSTANTIATE COMBOBOX
        taStartTimes = new ComboBox(options);
        taEndTimes = new ComboBox(options);
        //INSTANTIATE SUBMIT BUTTON FOR COMBOBOX
        submitButton = new Button("SUBMIT");
        
        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        VBox rightPane = new VBox();
        
        //ADD HBOX TO ADD COMBO BOXES
        HBox midRightPane = new HBox();
        HBox midTopRightPane = new HBox();
        HBox midBottomRightPane = new HBox();
        VBox taTimes = new VBox();
        //rightPane.getChildren().add(midRightPane);
        Label startTime = new Label("START TIME");
        Label endTime = new Label("END TIME");
        //topRightPane.getChildren().add(officeHoursHeaderBox);

        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(midRightPane);
        midRightPane.getChildren().add(officeHoursGridPane);
        midRightPane.getChildren().add(taTimes);
        
        taTimes.getChildren().add(midTopRightPane);
        midTopRightPane.getChildren().add(startTime);
        midTopRightPane.getChildren().add(endTime);
        
        taTimes.getChildren().add(midBottomRightPane);
        midBottomRightPane.getChildren().add(taStartTimes);
        midBottomRightPane.getChildren().add(taEndTimes);
        taTimes.getChildren().add(submitButton);
        //rightPane.getChildren().add(officeHoursGridPane);
        //ADD COMBOBOXES TO RIGHT PANE
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(rightPane));   //origin: new ScrollPane(rightPane)
        workspace = new BorderPane();
        
        // AND PUT EVERYTHING IN THE WORKSPACE
        ((BorderPane) workspace).setCenter(sPane);

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));

        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new CourseController(app);
        // CONTROLS FOR ADDING TAs
        
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            //FIRST CHECK IF BUTTON IS IN UPDATE STATE
            if(addButton.getText().equals("Update TA")){
                TeachingAssistant itemPick = taTable.getSelectionModel().getSelectedItem();
                //Comment out statement for now to test if only need with transaction
                controller.handleEditTA(itemPick);
                //ADD ON TRANSACTION FOR CONTROL Z
//                Update_Transaction updateTransaction = new Update_Transaction(itemPick, controller, data);
//                transaction.addTransaction(updateTransaction);
            } else{
            controller.handleAddTA();
            }
        });
        
        taTable.setOnMouseClicked(e-> {
            TeachingAssistant itemPick = taTable.getSelectionModel().getSelectedItem();
            controller.handleTableRowSelect(addButton, itemPick, emailTextField, nameTextField);
            
        });
        if(taTable.getSelectionModel().isEmpty()){
            addButton.setText("Add TA");
        }
        //EVENT HANDLER FOR SUBMIT BUTTON WITH COMBO BOX
        submitButton.setOnAction(e-> {
            controller.submitComboBox(taStartTimes, taEndTimes, submitButton, options, data, app);
            
        });
        
        clearButton.setOnAction(e-> {
            //CLEAR BOTH TEXT FIELDS
            nameTextField.clear();
            emailTextField.clear();
            addButton.setText("Add TA");
            nameTextField.requestFocus();
        });
        
        taTable.setFocusTraversable(true);
        //DELETE KEY PRESS EVENT HANDLER
        taTable.setOnKeyPressed(e -> {
            controller.handleKeyPress(e.getCode());
            
        });
        
        //KeyCodeCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        
        app.getGUI().getPrimaryScene().setOnKeyReleased(e->{
            if(e.isControlDown()){
                if(e.getCode() == KeyCode.Z){
                    transaction.undoTransaction();
                }
                if(e.getCode() == KeyCode.Y){
                    transaction.doTransaction();
                }
            }
        });
        
        //CALL THIS AS CHEAP WAY TO GET GRID TO SHOW UP
//        taStartTimes.setValue("8:00am");
//        taEndTimes.setValue("8:00pm");
//        controller.submitComboBox(taStartTimes, taEndTimes, submitButton, options, data, app);
//        taStartTimes.setValue(null);
//        taEndTimes.setValue(null);
        
    }
    
    //GETTER FOR CONTROLLER
    public CourseController getController(){
        return controller;
    }
    
    //GETTER FOR WORKSPACE
    public Pane getWorkspace(){
        return workspace;
    }
    
    public jTPS getTransaction(){
        return transaction;
    }
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }
    //GETTER FOR CLEAR 
    public Button getClearButton(){
        return clearButton;
    }
    
    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    //@Override
    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
    }
    
    // @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        CourseData taData = (CourseData)dataComponent;
        reloadOfficeHoursGrid(taData);
    }
    
    //METHOD FROM DATA CLASS
//    public StringProperty getCellTextProperty(int col, int row) {
//        String cellKey = getCellKey(col, row);
//        return officeHours.get(cellKey);
//    }
//    public String getCellKey(int col, int row) {
//        return col + "_" + row;
//    }
    
    public void reloadOfficeHoursGrid(CourseData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e.getCode());
                //CHECKING ONE AREA IF CONTROL Z HAPPENED
                //controller.transactionKeyPress(e.getCode(), transaction, e.isControlDown());
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        CourseStyle taStyle = (CourseStyle)app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(CourseData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());   
    }

}
