/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.UI;

import CourseSite.app.CourseApp;
import CourseSite.data.CourseData;
import CourseSite.data.Student;
import CourseSite.data.Team;
import CourseSite.workspace.CourseController;
import djf.settings.AppPropertyType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import properties_manager.PropertiesManager;

/**
 *
 * @author John
 */
public class CourseProjectsPane {
    Pane parentPane;
    VBox parentPaneBox;
    
    ScrollPane parentScrollPane;
    
    BorderPane parentBorderPane;
    
    Pane teamsPane;
    Pane studentsPane;
    PropertiesManager props;
    ObservableList<Label> labelList;
    //TableView stuff
    TableView<Team> teamsTable;
    TableColumn<Team, String> nameColumn;
    TableColumn<Team, String> colorColumn;
    TableColumn<Team, String> textColorColumn;
    TableColumn<Team, String> linkColumn;
    //STUDENT TABLE
    TableView<Student> studentsTable;
    TableColumn<Student, String> firstNameColumn;
    TableColumn<Student, String> lastNameColumn;
    TableColumn<Student, String> teamColumn;
    TableColumn<Student, String> roleColumn;
    
    Button minusBox;
    VBox addEditBox;
    ComboBox teamComboBox;
    DatePicker dateRow;
    TextField nameTextField;
    TextField linkTextField;
    TextField firstNameTextField;
    TextField lastNameTextField;
    TextField roleTextField;
    //USE COMBO BOX INSTEAD OF TEXT FIELD FOR STUDENT ROLE
    ComboBox roleComboBox;
    
    
    Button teamAddEditButton;
    Button teamClearButton;
    
    ColorPicker colorColorPicker;
    Circle colorCircle;
    ColorPicker textColorPicker;
    Circle textColorCircle;
    Button studentMinusBox;
    
    Button studentAddEditButton;
    Button studentClearButton;
    
    Label projectsLabel;
    
    CourseController controller;
    
    public CourseProjectsPane(CourseApp app){
        props = PropertiesManager.getPropertiesManager();
        controller = new CourseController(app);
        parentPaneBox = new VBox();
        parentPane = new Pane();
        parentPane.getChildren().add(parentPaneBox);
        
        projectsLabel = new Label(props.getProperty(AppPropertyType.PROJECT_LABEL.toString()));
        parentPaneBox.getChildren().add(projectsLabel);
        
        teamsPane = new Pane();
        studentsPane = new Pane();
        
        parentPaneBox.getChildren().add(teamsPane);
        VBox teamsVBox = new VBox();
        
        teamsPane.getChildren().add(teamsVBox);
        Label teamsLabel = new Label(props.getProperty(AppPropertyType.TEAMS_LABEL.toString()));
        
        HBox teamsTitleHBox = new HBox();
        teamsTitleHBox.getChildren().add(teamsLabel);
        minusBox = new Button();
        Label minusLabel = new Label(props.getProperty(AppPropertyType.RECITATION_MINUS_LABEL));
        minusBox.setText("-");
        teamsTitleHBox.getChildren().add(minusBox);
        teamsVBox.getChildren().add(teamsTitleHBox);
        
        
        //parentPaneBox.getChildren().add(teamsVBox);
        
        teamsTable = new TableView();
        nameColumn = new TableColumn(props.getProperty(AppPropertyType.NAME_COLUMN_TEXT.toString()));
        colorColumn = new TableColumn(props.getProperty(AppPropertyType.COLOR_COLUMN_TEXT.toString()));
        textColorColumn = new TableColumn(props.getProperty(AppPropertyType.TEXT_COLOR_COLUMN_TEXT.toString()));
        linkColumn = new TableColumn(props.getProperty(AppPropertyType.LINK_COLUMN_TEXT.toString()));

        //SET WIDTHS OF COLUMNS
        nameColumn.prefWidthProperty().bind(teamsTable.widthProperty().divide(4));
        colorColumn.prefWidthProperty().bind(teamsTable.widthProperty().divide(4));
        textColorColumn.prefWidthProperty().bind(teamsTable.widthProperty().divide(4));
        linkColumn.prefWidthProperty().bind(teamsTable.widthProperty().divide(4));
        
        //BEFORE ADDING SET THE COLUMN VALUES
        CourseData data = (CourseData)app.getDataComponent();
        //HARD CODED DATA
//        Team teamOne = new Team("Jack", "66682", "ff838ff", "https://beyondtherainbow/skittle.com");
//        Team teamTwo = new Team("Fishman", "66682", "ff838ff", "https://beyondtherainbow/skittle.com");
//        data.getTeams().add(teamOne);
//        data.getTeams().add(teamTwo);
        teamsTable.setItems(data.getTeams());
        //SET CELL VALUE FACTORIES
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("name")
        );
        colorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("color")
        );
        textColorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("textColor")
        );
        linkColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("link")
        );
        
        teamsTable.getColumns().add(nameColumn);
        teamsTable.getColumns().add(colorColumn);
        teamsTable.getColumns().add(textColorColumn);
        teamsTable.getColumns().add(linkColumn);
        
        //USED TO STOP TABLE FROM GROWING
        teamsTable.setMaxWidth(600);
        
        teamsVBox.getChildren().add(teamsTable);
        addEditBox = new VBox();
        teamsVBox.getChildren().add(addEditBox);
        Label addEditLabel = new Label(props.getProperty(AppPropertyType.ADD_EDIT_LABEL.toString()));
        addEditBox.getChildren().add(addEditLabel);
        HBox nameHBox = new HBox();
        addEditBox.getChildren().add(nameHBox);
        Label nameLabel = new Label(props.getProperty(AppPropertyType.NAME_COLUMN_TEXT.toString()));
        nameHBox.getChildren().add(nameLabel);
        nameTextField= new TextField();
        nameHBox.getChildren().add(nameTextField);
        HBox colorHBox = new HBox();
        teamsVBox.getChildren().add(colorHBox);
        Label colorLabel = new Label(props.getProperty(AppPropertyType.COLOR_LABEL.toString()));
        colorHBox.getChildren().add(colorLabel);
        
        VBox colorVBox = new VBox();
        colorCircle = new Circle (40);
        colorColorPicker = new ColorPicker();
        colorHBox.getChildren().add(colorVBox);
        colorVBox.getChildren().add(colorCircle);
        colorVBox.getChildren().add(colorColorPicker);
        
        //TEXT COLOR
        HBox textColorHBox = new HBox();
        colorHBox.getChildren().add(textColorHBox);
        Label textColorLabel = new Label(props.getProperty(AppPropertyType.TEXT_COLOR_LABEL.toString()));
        textColorHBox.getChildren().add(textColorLabel);
        
        VBox textColorVBox = new VBox();
        textColorCircle = new Circle (40);
        textColorPicker = new ColorPicker();
        textColorHBox.getChildren().add(textColorVBox);
        textColorVBox.getChildren().add(textColorCircle);
        textColorVBox.getChildren().add(textColorPicker);
        //EVENT HANDLER FOR CHANGING COLORS
        colorColorPicker.setOnAction(e->{
            colorCircle.setFill(colorColorPicker.getValue());
        });
        textColorPicker.setOnAction(e->{
            textColorCircle.setFill(textColorPicker.getValue());
        });
        //LINK LABEL
        HBox linkHBox = new HBox();
        teamsVBox.getChildren().add(linkHBox);
        Label linkLabel = new Label(props.getProperty(AppPropertyType.LINK_LABEL.toString()));
        linkHBox.getChildren().add(linkLabel);
        linkTextField = new TextField();
        linkHBox.getChildren().add(linkTextField);
        //ADDING BUTTONS
        HBox teamButtonHBox = new HBox();
        teamsVBox.getChildren().add(teamButtonHBox);
        teamAddEditButton = new Button(props.getProperty(AppPropertyType.ADD_EDIT_LABEL.toString()));
        teamClearButton = new Button(props.getProperty(AppPropertyType.CLEAR_LABEL.toString()));
        teamButtonHBox.getChildren().add(teamAddEditButton);
        teamButtonHBox.getChildren().add(teamClearButton);
        
        //PLACE IN LARGER PARENT PANE INTO PARENT VBOX
        parentPaneBox.getChildren().add(studentsPane);
        
        VBox studentsParentVBox = new VBox();
        Label studentLabel = new Label(props.getProperty(AppPropertyType.STUDENT_LABEL.toString()));
        HBox studentHBox = new HBox();
        studentsPane.getChildren().add(studentsParentVBox);
        studentsParentVBox.getChildren().add(studentHBox);
        studentHBox.getChildren().add(studentLabel);
        studentMinusBox = new Button();
        studentHBox.getChildren().add(studentMinusBox);
        Label studentMinusLabel = new Label(props.getProperty(AppPropertyType.RECITATION_MINUS_LABEL.toString()));
        studentMinusBox.setText("-");
        //STUDENTS TABLEVIEW
        studentsTable = new TableView();
        firstNameColumn = new TableColumn(props.getProperty(AppPropertyType.FIRST_NAME_LABEL.toString()));
        lastNameColumn = new TableColumn(props.getProperty(AppPropertyType.LAST_NAME_LABEL.toString()));
        teamColumn = new TableColumn(props.getProperty(AppPropertyType.TEAM_LABEL.toString()));
        roleColumn = new TableColumn(props.getProperty(AppPropertyType.ROLE_LABEL.toString()));

        //SET WIDTHS OF COLUMNS
        firstNameColumn.prefWidthProperty().bind(studentsTable.widthProperty().divide(4));
        lastNameColumn.prefWidthProperty().bind(studentsTable.widthProperty().divide(4));
        teamColumn.prefWidthProperty().bind(studentsTable.widthProperty().divide(4));
        roleColumn.prefWidthProperty().bind(studentsTable.widthProperty().divide(4));
        
        //BEFORE ADDING TO TABLE. ADD DATA
//        Student studentOne = new Student("Shah", "Murthy", "Jack", "Principle Investigator");
//        Student studentTwo = new Student("Shah", "Murthy", "Jack", "Principle Investigator");
//        data.getStudents().add(studentTwo);
//        data.getStudents().add(studentOne);
        studentsTable.setItems(data.getStudents());
        
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName")
        );
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
        teamColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("team")
        );
        roleColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("role")
        );
        
        studentsTable.getColumns().add(firstNameColumn);
        studentsTable.getColumns().add(lastNameColumn);
        studentsTable.getColumns().add(teamColumn);
        studentsTable.getColumns().add(roleColumn);
        
        //SETTING MAX WIDTH
        studentsTable.setMaxWidth(600);
        
        studentsParentVBox.getChildren().add(studentsTable);
        
        Label studentAddEditLabel = new Label(props.getProperty(AppPropertyType.ADD_EDIT_LABEL.toString()));
        studentsParentVBox.getChildren().add(studentAddEditLabel);
        //FIRST NAME COMPONENTS
        HBox firstNameHBox = new HBox();
        studentsParentVBox.getChildren().add(firstNameHBox);
        Label studentFirstNameLabel = new Label(props.getProperty(AppPropertyType.FIRST_NAME_LABEL.toString()));
        firstNameHBox.getChildren().add(studentFirstNameLabel);
        firstNameTextField = new TextField();
        firstNameHBox.getChildren().add(firstNameTextField);
        
        //LAST NAME COMPONENTS
        HBox lastNameHBox = new HBox();
        studentsParentVBox.getChildren().add(lastNameHBox);
        Label studentLastNameLabel = new Label(props.getProperty(AppPropertyType.LAST_NAME_LABEL.toString()));
        lastNameHBox.getChildren().add(studentLastNameLabel);
        lastNameTextField = new TextField();
        lastNameHBox.getChildren().add(lastNameTextField);
        
         //TEAM COMPONENTS
        HBox teamHBox = new HBox();
        studentsParentVBox.getChildren().add(teamHBox);
        Label studentTeamLabel = new Label(props.getProperty(AppPropertyType.TEAM_LABEL.toString()));
        teamHBox.getChildren().add(studentTeamLabel);
//        ObservableList<String> teamOptions = FXCollections.observableArrayList(
//                "C4 Comics",
//                "The hidden titantic",
//                "The comp sci premed"
//        );
        
        teamComboBox = new ComboBox(data.getTeams());
        teamHBox.getChildren().add(teamComboBox);
        
        //ROLE COMPONENTS
        HBox roleHBox = new HBox();
        studentsParentVBox.getChildren().add(roleHBox);
        Label studentRoleLabel = new Label(props.getProperty(AppPropertyType.ROLE_LABEL.toString()));
        roleHBox.getChildren().add(studentRoleLabel);
        roleTextField = new TextField();
        ObservableList<String> roleOptions = FXCollections.observableArrayList(
                "Lead Programmer",
                "Project Manager",
                "Data Designer",
                "Lead Designer"
        );
        
        roleComboBox = new ComboBox(roleOptions);
        roleHBox.getChildren().add(roleComboBox);
        
        HBox studentButtonHBox = new HBox();
        studentsParentVBox.getChildren().add(studentButtonHBox);
        studentAddEditButton = new Button(props.getProperty(AppPropertyType.ADD_EDIT_LABEL.toString()));
        studentClearButton = new Button(props.getProperty(AppPropertyType.CLEAR_LABEL.toString()));
        studentButtonHBox.getChildren().add(studentAddEditButton);
        studentButtonHBox.getChildren().add(studentClearButton);
       
        parentScrollPane = new ScrollPane(parentPane);
        parentBorderPane = new BorderPane();
        parentBorderPane.setCenter(parentScrollPane);
        
        //STYLING GOES HERE
        parentScrollPane.setMaxWidth(800);                       //(top/right/bottom/left)
        parentScrollPane.setPadding(new Insets(20, 20, 20, 20));
        parentPaneBox.setPadding(new Insets(0, 0, 0, 70));
        parentPaneBox.setSpacing(60);
        parentScrollPane.setStyle("-fx-background-color: blue");
        
        //teams pane
        teamsPane.setStyle("-fx-background-color: #74C2E1");
        teamsTable.prefWidthProperty().bind(teamsPane.widthProperty());
        teamsLabel.setPadding(new Insets(0,0, 20, 0));
        addEditLabel.setPadding(new Insets(20, 0, 20, 0));
        nameLabel.setPadding(new Insets(0, 50, 20, 0));
        colorLabel.setPadding(new Insets(0, 50, 20, 0));
        textColorLabel.setPadding(new Insets(0, 0, 20, 20));
        linkHBox.setPadding(new Insets(20, 0, 20, 0));
        linkLabel.setPadding(new Insets(0, 50, 0, 0));
        teamButtonHBox.setSpacing(50);
        
        //add edit pane
        studentsPane.setStyle("-fx-background-color: #74C2E1");
        studentsTable.prefWidthProperty().bind(studentsPane.widthProperty());
        studentsPane.prefWidthProperty().bind(parentPane.widthProperty());
        studentLabel.setPadding(new Insets(0, 0, 20, 0));
        studentAddEditLabel.setPadding(new Insets(20, 0, 20, 0));
        studentFirstNameLabel.setPadding(new Insets(0, 50, 20, 0));
        studentLastNameLabel.setPadding(new Insets(0, 55, 20, 0));
        studentTeamLabel.setPadding(new Insets(0, 90, 20, 0));
        teamComboBox.setMaxWidth(100);
        studentRoleLabel.setPadding(new Insets(0, 90, 20, 0));
        studentButtonHBox.setSpacing(95);
        
        //DO ALL EVENT HANDLING DOWN HERE
        teamAddEditButton.setOnAction(e->{
            String teamName = nameTextField.getText();
            String teamLink = linkTextField.getText();
            String color = colorColorPicker.getValue().toString();
            color = color.substring(2, 8);
            String textColor = textColorPicker.getValue().toString();
            textColor = textColor.substring(2, 8);
            Color actualColor = colorColorPicker.getValue();
            Color actualTextColor = textColorPicker.getValue();
            
            if(teamAddEditButton.getText().equals("Update")){
                Team oldTeam = teamsTable.getSelectionModel().getSelectedItem();
                Team newTeam = new Team(teamName, color, textColor, teamLink);
                controller.updateTeam(oldTeam, newTeam);
                nameTextField.clear();
                linkTextField.clear();
                colorColorPicker.setValue(Color.BLACK);
                textColorPicker.setValue(Color.BLACK);
                nameTextField.requestFocus();
            }
            else{
                Team team = new Team(teamName, color, textColor, teamLink);
                team.setActualColor(actualColor);
                team.setActualTextColor(actualTextColor);
                controller.addTeam(team);
                nameTextField.clear();
                linkTextField.clear();
                colorColorPicker.setValue(Color.BLACK);
                textColorPicker.setValue(Color.BLACK);
            }
        });
        teamsTable.setOnMouseClicked(e->{
            teamAddEditButton.setText("Update");
            Team selectTeam = teamsTable.getSelectionModel().getSelectedItem();
            nameTextField.setText(selectTeam.getName());
            linkTextField.setText(selectTeam.getLink());
            colorColorPicker.setValue(selectTeam.getActualColor());
            textColorPicker.setValue(selectTeam.getActualTextColor());
        });
        teamsTable.setOnKeyPressed(e->{
            controller.deleteTeam(e.getCode());
            nameTextField.clear();
            linkTextField.clear();
            colorColorPicker.setValue(Color.BLACK);
            textColorPicker.setValue(Color.BLACK);
        });
        //MAKE EVENT LISTENERS FOR STUDENTS TABLE
        studentAddEditButton.setOnAction(e->{
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            //String role = roleTextField.getText();
            String role = (String)roleComboBox.getValue();
            try{
                Team team = (Team)teamComboBox.getValue();
                String teamName = team.getName();

                Student student = new Student(firstName, lastName, teamName, role);
                if(studentAddEditButton.getText().equals("Update")){
                    Student oldStudent = studentsTable.getSelectionModel().getSelectedItem();
                    controller.updateStudent(oldStudent, student);
                } else{
                    controller.addStudent(student);
                }
                firstNameTextField.clear();
                lastNameTextField.clear();
                //roleTextField.clear();
                roleComboBox.setValue(null);
            } catch(NullPointerException ex){
                Student student = new Student(firstName, lastName, "", role);
                controller.addStudent(student);
                firstNameTextField.clear();
                lastNameTextField.clear();
                //roleTextField.clear();
                roleComboBox.setValue(null);
            }
        });
        //STUDENT TABLE
        studentsTable.setOnMouseClicked(e->{
            studentAddEditButton.setText("Update");
            Student selectStudent = studentsTable.getSelectionModel().getSelectedItem();
            try{
                firstNameTextField.setText(selectStudent.getFirstName());
                lastNameTextField.setText(selectStudent.getLastName());
                //roleTextField.setText(selectStudent.getRole());
                roleComboBox.setValue(selectStudent.getRole());
            } catch(NullPointerException ex){
                
            }
        });
        studentsTable.setOnKeyPressed(e->{
            controller.deleteStudent(e.getCode());
            firstNameTextField.clear();
            lastNameTextField.clear();
            //roleTextField.clear();
            roleComboBox.setValue(null);
        });
        teamClearButton.setOnAction(e->{
            nameTextField.clear();
            linkTextField.clear();
            colorColorPicker.setValue(Color.BLACK);
            textColorPicker.setValue(Color.BLACK);
            nameTextField.requestFocus();
            teamAddEditButton.setText("Add/Edit");
        });
        studentClearButton.setOnAction(e->{
            firstNameTextField.clear();
            lastNameTextField.clear();
            //roleTextField.clear();
            roleComboBox.setValue(null);
            firstNameTextField.requestFocus();
            studentAddEditButton.setText("Add/Edit");
        });
    }

    public TableView<Student> getStudentsTable() {
        return studentsTable;
    }

    public TableView<Team> getTeamsTable() {
        return teamsTable;
    }

    public Label getProjectsLabel() {
        return projectsLabel;
    }
    
    public Pane getParentPane(){
        return parentPane;
    }
    public ScrollPane getParentScrollPane(){
        return parentScrollPane;
    }
    public BorderPane getParentBorderPane(){
        return parentBorderPane;
    }
}
