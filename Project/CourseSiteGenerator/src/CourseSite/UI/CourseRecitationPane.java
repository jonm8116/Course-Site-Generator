/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.UI;

import CourseSite.app.CourseApp;
import CourseSite.data.CourseData;
import CourseSite.data.Recitation;
import CourseSite.data.TeachingAssistant;
import CourseSite.workspace.CourseController;
import djf.settings.AppPropertyType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author John
 */
public class CourseRecitationPane {
    ScrollPane parentScrollPane;
    
    Pane parentPane;
    VBox parentPaneBox;
    
    BorderPane parentBorderPane;
    
    Pane recitationTableViewPane;
    Pane addEditPane;
    PropertiesManager props;
    ObservableList<Label> labelList;
    //TableView stuff
    TableView recitationTable;
    TableColumn<Recitation, String> sectionColumn;
    TableColumn<Recitation, String> instructorColumn;
    TableColumn<Recitation, String> dayTimeColumn;
    TableColumn<Recitation, String> locationColumn;
    TableColumn<Recitation, String> firstTAColumn;
    TableColumn<Recitation, String> secondTAColumn;
    
    Button minusBox;
    
    TextField sectionField;
    TextField instructorField;
    TextField dayTimeField;
    TextField locationField;
    TextField firstTAField;
    TextField secondTAField;
    //USE COMBOBOX INSTEAD OF TEXT FIELD FOR TAS
    ComboBox firstTABox;
    ComboBox secondTABox;   
    Button addEditButton;
    Button clearButton;
    
    Label recitationLabel;
    //CONTROLLER
    CourseController controller;
    
    public CourseRecitationPane(CourseApp app){
        //initialize props
        
        props = PropertiesManager.getPropertiesManager();
        labelList = FXCollections.observableArrayList();
        parentPane = new Pane();
        //SET BORDERPANES
        parentPaneBox = new VBox();
        
        recitationTableViewPane = new Pane();
        addEditPane = new Pane();
        parentPane.getChildren().add(parentPaneBox);
        
        recitationLabel = new Label(props.getProperty(AppPropertyType.RECITATION_LABEL.toString()));
        VBox recTableVBox = new VBox();
        recitationTableViewPane.getChildren().add(recTableVBox);
        HBox recitationTitleHBox = new HBox();
        recTableVBox.getChildren().add(recitationTitleHBox);
        recitationTitleHBox.getChildren().add(recitationLabel);
        
        minusBox = new Button();
        Label minusLabel = new Label(props.getProperty(AppPropertyType.RECITATION_MINUS_LABEL.toString()));
        minusBox.setText("-");
        recitationTitleHBox.getChildren().add(minusBox);
        
        parentPaneBox.getChildren().add(recitationTableViewPane);
        
        recitationTable = new TableView();
        sectionColumn = new TableColumn();
        instructorColumn = new TableColumn();
        dayTimeColumn = new TableColumn();
        locationColumn = new TableColumn();
        firstTAColumn = new TableColumn();
        secondTAColumn = new TableColumn();
        
        sectionColumn.setText(props.getProperty(AppPropertyType.REC_TABLE_SECTION_TEXT.toString()));
        instructorColumn.setText(props.getProperty(AppPropertyType.REC_TABLE_INSTRUCTOR_TEXT.toString()));
        dayTimeColumn.setText(props.getProperty(AppPropertyType.REC_TABLE_DAYTIME_TEXT.toString()));
        locationColumn.setText(props.getProperty(AppPropertyType.REC_TABLE_LOCATION_TEXT.toString()));
        firstTAColumn.setText(props.getProperty(AppPropertyType.REC_TABLE_TA_TEXT.toString()));
        secondTAColumn.setText(props.getProperty(AppPropertyType.REC_TABLE_TA_TEXT.toString()));

        //BEFORE ADDING COLUMNS TO CELL
        
        CourseData data = (CourseData)app.getDataComponent();
        //HARD CODED VALUES
//        Recitation sectionOne = new Recitation("L8", "Mckenna", "Wed", "New Cs", "Banerjee", "other banerjee");
//        Recitation sectionTwo = new Recitation("R02", "O'Neil", "tues", "BLC", "kathrynn", "kelly");
//        data.getRecitations().add(sectionTwo);
//        data.getRecitations().add(sectionOne);
        
        sectionColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("section")
        );
        instructorColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("instructor")
        );
        dayTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("dayTime")
        );
        locationColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("location")
        );
        firstTAColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("firstTA")
        );
        secondTAColumn.setCellValueFactory(
                new PropertyValueFactory<Recitation, String>("secondTA")
        );
        recitationTable.setItems(data.getRecitations());
        
        
        recitationTable.getColumns().add(sectionColumn);
        recitationTable.getColumns().add(instructorColumn);
        recitationTable.getColumns().add(dayTimeColumn);
        recitationTable.getColumns().add(locationColumn);
        recitationTable.getColumns().add(firstTAColumn);
        recitationTable.getColumns().add(secondTAColumn);
        
        parentPaneBox.getChildren().add(recitationTable);
        
        parentPaneBox.getChildren().add(addEditPane);
        
        //ADD EDIT PANE
        VBox addEditVBox = new VBox();
        addEditPane.getChildren().add(addEditVBox);
        Label addEditLabel = new Label(props.getProperty(AppPropertyType.ADD_EDIT_LABEL.toString()));
        addEditVBox.getChildren().add(addEditLabel);
        //SECTION HBOX
        HBox sectionHBox = new HBox();
        Label sectionLabel = new Label(props.getProperty(AppPropertyType.REC_TABLE_SECTION_TEXT.toString()));
        sectionHBox.getChildren().add(sectionLabel);
        sectionField = new TextField();
        sectionHBox.getChildren().add(sectionField);
        addEditVBox.getChildren().add(sectionHBox);
        //INSTRUCTOR HBOX
        HBox instructorHBox = new HBox();
        Label instructorLabel = new Label(props.getProperty(AppPropertyType.REC_TABLE_INSTRUCTOR_TEXT.toString()));
        addEditVBox.getChildren().add(instructorHBox);
        instructorHBox.getChildren().add(instructorLabel);
        instructorField = new TextField();
        instructorHBox.getChildren().add(instructorField);
        
        //DAYTIME HBOX
        HBox dayTimeHBox = new HBox();
        Label dayTimeLabel = new Label(props.getProperty(AppPropertyType.REC_TABLE_DAYTIME_TEXT.toString()));
        addEditVBox.getChildren().add(dayTimeHBox);
        dayTimeHBox.getChildren().add(dayTimeLabel);
        dayTimeField = new TextField();
        dayTimeHBox.getChildren().add(dayTimeField);
        
        //LOCATION HBOX
        HBox locationHBox = new HBox();
        Label locationLabel = new Label(props.getProperty(AppPropertyType.REC_TABLE_LOCATION_TEXT.toString()));
        addEditVBox.getChildren().add(locationHBox);
        locationHBox.getChildren().add(locationLabel);
        locationField = new TextField();
        locationHBox.getChildren().add(locationField);
        
        //TA HBOX #1
        HBox firstTAHBox = new HBox();
        Label firstTALabel = new Label(props.getProperty(AppPropertyType.REC_TABLE_TA_TEXT.toString()));
        addEditVBox.getChildren().add(firstTAHBox);
        firstTAHBox.getChildren().add(firstTALabel);
        firstTAField = new TextField();
        //INSTANTIATE NEW COMBO BOX AND PLACE INSTEAD OF TEXT FIELD
        firstTABox = new ComboBox(data.getTeachingAssistants());
        firstTAHBox.getChildren().add(firstTABox);    //origin: firstTAField
        
        //TA HBOX #2
        HBox secondTAHBox = new HBox();
        Label secondTALabel = new Label(props.getProperty(AppPropertyType.REC_TABLE_TA_TEXT.toString()));
        addEditVBox.getChildren().add(secondTAHBox);
        secondTAHBox.getChildren().add(secondTALabel);
        secondTAField = new TextField();
        //INSTANTIATE NEW COMBO BOX AND PLACE INSTEAD OF TEXT FIELD
        secondTABox =  new ComboBox(data.getTeachingAssistants());
        secondTAHBox.getChildren().add(secondTABox);
        
        HBox buttonHBox = new HBox();
        addEditVBox.getChildren().add(buttonHBox);
        addEditButton = new Button(props.getProperty(AppPropertyType.ADD_EDIT_LABEL.toString()));
        buttonHBox.getChildren().add(addEditButton);
        clearButton = new Button(props.getProperty(AppPropertyType.CLEAR_LABEL.toString()));
        buttonHBox.getChildren().add(clearButton);
        
        //INITIALIZE AND ADD SCROLLPANE
        parentBorderPane = new BorderPane();
        
        parentScrollPane = new ScrollPane();
        //this.getChildren().add(parentScrollPane);
        parentScrollPane.setContent(parentPaneBox);
        
        parentScrollPane.prefHeight(500);
        parentScrollPane.prefWidth(500);
        
        parentBorderPane.setCenter(parentScrollPane);
        
        //STYLING
        parentScrollPane.setMaxWidth(800);
        parentScrollPane.setPadding(new Insets(20, 20, 20, 20));
        parentPaneBox.setPadding(new Insets(0, 0, 0, 70));
        parentPaneBox.setSpacing(30);
        recitationLabel.setPadding(new Insets(0, 20, 25, 0));
        recitationTableViewPane.setStyle("-fx-background-color: #74C2E1");
        //parentPaneBox.setStyle("-fx-background-color: #8C8984");
        addEditPane.setStyle("-fx-background-color: #74C2E1");
        parentScrollPane.setStyle("-fx-background-color: blue");
        
        //SET PADDING FOR LABELS
        sectionLabel.setPadding(new Insets(0, 60, 20, 0));
        instructorLabel.setPadding(new Insets(0, 38, 20, 0));
        dayTimeLabel.setPadding(new Insets(0, 53, 20, 0));
        locationLabel.setPadding(new Insets(0, 52, 20, 0));
        firstTALabel.setPadding(new Insets(0, 95, 20, 0));
        secondTALabel.setPadding(new Insets(0, 95, 20, 0));
        
        addEditButton.setMaxWidth(100);
        buttonHBox.setSpacing(80);
        //INITIALIZE CONTROLLER
        controller = new CourseController(app);
        
        //DO EVENT HANDLING DOWN HERE
        addEditButton.setOnAction(e->{
            //DATA VALUES:
            //Section, instr, dayTime, location, firstTA, secondTA
            String section = sectionField.getText();
            String instr = instructorField.getText();
            String day = dayTimeField.getText();
            String location = locationField.getText();
            TeachingAssistant taOne = (TeachingAssistant)firstTABox.getValue();
            String firstTA = taOne.getName();
            TeachingAssistant taTwo = (TeachingAssistant)secondTABox.getValue();
            String secondTA = taTwo.getName();
            
            if(addEditButton.getText().equals("Update")){
                Recitation selectedRec = (Recitation)recitationTable.getSelectionModel().getSelectedItem();
                controller.updateRec(section, instr, day, location, firstTA, secondTA, selectedRec);
                
            } else{
                controller.addRec(section, instr, day, location, firstTA, secondTA); 
            }
            sectionField.clear();
            instructorField.clear();
            dayTimeField.clear();
            locationField.clear();
            firstTABox.setValue(null);
            secondTABox.setValue(null);
            sectionField.requestFocus();
        });
        clearButton.setOnAction(e->{
            sectionField.clear();
            instructorField.clear();
            dayTimeField.clear();
            locationField.clear();
            firstTABox.setValue(null);
            secondTABox.setValue(null);
            addEditButton.setText("Add/Edit");
            sectionField.requestFocus();
        });
        recitationTable.setOnMouseClicked(e->{
            Recitation selectedRec = (Recitation)recitationTable.getSelectionModel().getSelectedItem();
            addEditButton.setText("Update");
            sectionField.setText(selectedRec.getSection());
            instructorField.setText(selectedRec.getInstructor());
            dayTimeField.setText(selectedRec.getDayTime());
            locationField.setText(selectedRec.getLocation());
            firstTABox.setValue(data.getTA(selectedRec.getFirstTA()));
            secondTABox.setValue(data.getTA(selectedRec.getSecontTA()));
        });
        recitationTable.setOnKeyPressed(e->{
            controller.deleteRecitation(e.getCode());
            sectionField.clear();
            instructorField.clear();
            dayTimeField.clear();
            locationField.clear();
            firstTABox.setValue(null);
            secondTABox.setValue(null);
            addEditButton.setText("Add/Edit");
            sectionField.requestFocus();
        });
    }

    public TableView getRecitationTable() {
        return recitationTable;
    }

    public Label getRecitationLabel() {
        return recitationLabel;
    }
    
    public ObservableList<Label> getLabelList(){
        return labelList;
    }
    
    public BorderPane getParentBorderPane(){
        return parentBorderPane;
    }
    public Pane getParentPane(){
        return parentPane;
    }

    public ScrollPane getParentScrollPane(){
        return parentScrollPane;
    }
}
