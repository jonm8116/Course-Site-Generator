/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.UI;

import CourseSite.app.CourseApp;
import CourseSite.data.CourseData;
import CourseSite.data.CourseSitePages;
import CourseSite.data.SiteInfo;
import CourseSite.data.TeachingAssistant;
import djf.settings.AppPropertyType;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;

/**
 *
 * @author John
 */
public class CourseDetailsPane{
    
    ScrollPane parentScrollPane;
    Pane parentPane;
    VBox parentVBox;
    BorderPane parentBorderPane;
    Pane courseInfoPane;
    Pane siteTemplatePane;
    Pane pageStylePane;
    PropertiesManager props;
    Label exportDir;
    Button exportDirButton;
    ObservableList<Label> labelList;
    //TableView stuff
    TableView<CourseSitePages> siteTemplateTableView;
    TableColumn<CourseSitePages, Boolean> useColumn;
    TableColumn<CourseSitePages, String> navBarColumn;
    TableColumn<CourseSitePages, String> fileNameColumn;
    TableColumn<CourseSitePages, String> scriptColumn;
    ComboBox stylesheetComboBox;
    
    Label courseInfoLabel;
    
    //MAKE DATA FIELDS TO GRAB FOR LATER
    String titleValue;
    String instrNameValue;
    String instrHomeValue;
    //UI FIELDS TO EXPORT
    ComboBox subjectBox;
    ComboBox semesterChoices;
    ComboBox subjectNumber;
    ComboBox yearChoices;
    TextField courseTitleField;
    TextField courseInstrName;
    TextField courseInstrHome;
    
    //MAKE IMAGEVIEWS SO THAT THE USER CAN VIEW THE IMAGES
    ImageView bannerImgView;
    ImageView leftFootImgView;
    ImageView rightFootImgView;
    Image bannerImg;
    Image rightFootImg;
    Image leftFootImg;
    
    //buttons
    Button leftFootImgButton;
    Button bannerImgButton;
    Button rightFootImgButton;
    
    public CourseDetailsPane(CourseApp app){
        props = PropertiesManager.getPropertiesManager();
        labelList = FXCollections.observableArrayList();
        parentPane = new Pane();
        parentVBox = new VBox();
        //SET BORDERPANES
        
        //this.getChildren().add(parentPane);
        VBox parentPaneBox = new VBox();
        courseInfoPane = new Pane();
        siteTemplatePane = new Pane();
        pageStylePane = new Pane();
        parentPane.getChildren().add(parentPaneBox);
        
        
        parentPaneBox.getChildren().add(courseInfoPane);
        parentPaneBox.getChildren().add(siteTemplatePane);
        parentPaneBox.getChildren().add(pageStylePane);
        
        //CREATE COMPONENTS FOR COURSEINFOPANE
        VBox courseInfoBox = new VBox();
        courseInfoLabel = new Label(props.getProperty(AppPropertyType.COURSE_INFO_LABEL.toString()));
        labelList.add(courseInfoLabel);
        courseInfoBox.getChildren().add(courseInfoLabel);
        courseInfoPane.getChildren().add(courseInfoBox);
        HBox subjectHBox = new HBox();
        Label subjectLabel = new Label(props.getProperty(AppPropertyType.COURSE_SUBJECT_LABEL.toString()));
        labelList.add(subjectLabel);
        ObservableList<String> subjectOptions = FXCollections.observableArrayList(
                "CSE",
                "ISE"
        );
        subjectBox = new ComboBox(subjectOptions);
        Label number = new Label(props.getProperty(AppPropertyType.COURSE_SUBJECT_NUMBER.toString()));
        labelList.add(number);
        ObservableList<String> numberOptions = FXCollections.observableArrayList(
                "114",
                "214",
                "219",
                "380",
                "381",
                "308"
        );
        subjectNumber = new ComboBox(numberOptions);
        subjectHBox.getChildren().add(subjectLabel);
        subjectHBox.getChildren().add(subjectBox);
        subjectHBox.getChildren().add(number);
        subjectHBox.getChildren().add(subjectNumber);
        
        courseInfoBox.getChildren().add(subjectHBox);
        //SEMESTER BOX
        HBox semesterBox = new HBox();
        Label semesterLabel = new Label(props.getProperty(AppPropertyType.COURSE_SEMESTER_LABEL.toString()));
        labelList.add(semesterLabel);
        ObservableList<String> semesterOptions = FXCollections.observableArrayList(
                "Fall",
                "Spring"
        );
        semesterChoices = new ComboBox(semesterOptions);
        Label yearLabel = new Label(props.getProperty(AppPropertyType.COURSE_YEAR_LABEL.toString()));
        labelList.add(yearLabel);
        ObservableList<String> yearOptions = FXCollections.observableArrayList(
                "2015",
                "2016",
                "2017",
                "2018",
                "2019"
        );
        yearChoices = new ComboBox(yearOptions);
        semesterBox.getChildren().add(semesterLabel);
        semesterBox.getChildren().add(semesterChoices);
        semesterBox.getChildren().add(yearLabel);
        semesterBox.getChildren().add(yearChoices);
        //ADD TO COURSE INFO
        courseInfoBox.getChildren().add(semesterBox);
        HBox courseInfoTitle = new HBox();
        Label courseTitle = new Label(props.getProperty(AppPropertyType.COURSE_INFO_TITLE.toString()));
        labelList.add(courseTitle);
        courseTitleField = new TextField();
        courseInfoBox.getChildren().add(courseInfoTitle);
        courseInfoTitle.getChildren().add(courseTitle);
        courseInfoTitle.getChildren().add(courseTitleField);
        HBox courseInstrNameBox = new HBox();
        Label instrName = new Label(props.getProperty(AppPropertyType.COURSE_INSTR_NAME.toString()));
        labelList.add(instrName);
        courseInstrName = new TextField();
        courseInfoBox.getChildren().add(courseInstrNameBox);
        courseInstrNameBox.getChildren().add(instrName);
        courseInstrNameBox.getChildren().add(courseInstrName);
        //INSTRUCTOR HOME
        HBox courseInstrHomeBox = new HBox();
        Label instrHome = new Label(props.getProperty(AppPropertyType.COURSE_INSTR_HOME.toString()));
        labelList.add(instrHome);
        courseInstrHome = new TextField();
        courseInfoBox.getChildren().add(courseInstrHomeBox);
        courseInstrHomeBox.getChildren().add(instrHome);
        courseInstrHomeBox.getChildren().add(courseInstrHome);
        //Export dir
        HBox courseExportDirBox = new HBox();
        Label exportDirLabel = new Label(props.getProperty(AppPropertyType.COURSE_EXPORT_DIR_LABEL.toString()));
        labelList.add(exportDirLabel);
        exportDir = new Label("sample");
        exportDirButton = new Button(props.getProperty(AppPropertyType.COURSE_EXPORT_DIR_BUTTON.toString()));
        courseInfoBox.getChildren().add(courseExportDirBox);
        courseExportDirBox.getChildren().add(exportDirLabel);
        courseExportDirBox.getChildren().add(exportDir);
        courseExportDirBox.getChildren().add(exportDirButton);
        //NEXT MAJOR PANE
        //TIME TO START NEXT BOX
        VBox siteTemplateVBox = new VBox();
        siteTemplatePane.getChildren().add(siteTemplateVBox);
        Label siteTemplateLabel = new Label(props.getProperty(AppPropertyType.COURSE_SITE_TEMPLATE_TITLE.toString()));
        labelList.add(siteTemplateLabel);
        siteTemplateVBox.getChildren().add(siteTemplateLabel);
        Label siteTemplateDescription = new Label(props.getProperty(AppPropertyType.COURSE_SITE_TEMPLATE_TEXT.toString()));
        labelList.add(siteTemplateDescription);
        siteTemplateVBox.getChildren().add(siteTemplateDescription);
        Label siteTemplateDir = new Label("sample");
        labelList.add(siteTemplateDir);
        siteTemplateVBox.getChildren().add(siteTemplateDir);
        
        Button siteTemplateButton = new Button(props.getProperty(AppPropertyType.SITE_TEMPLATE_BUTTON_TEXT.toString()));
        siteTemplateVBox.getChildren().add(siteTemplateButton);
        Label sitePagesLabel = new Label(props.getProperty(AppPropertyType.SITE_PAGES_LABEL.toString()));
        labelList.add(sitePagesLabel);
        siteTemplateVBox.getChildren().add(sitePagesLabel);
        
        siteTemplateTableView = new TableView();
        useColumn = new TableColumn(props.getProperty(AppPropertyType.USE_COLUMN_TEXT.toString()));
        navBarColumn = new TableColumn(props.getProperty(AppPropertyType.NAVBAR_COLUMN_TEXT.toString()));
        fileNameColumn = new TableColumn(props.getProperty(AppPropertyType.FILE_NAME_COLUMN_TEXT.toString()));
        scriptColumn = new TableColumn(props.getProperty(AppPropertyType.SCRIPT_COLUMN_TEXT.toString()));
        //SET WIDTHS OF COLUMNS
        useColumn.prefWidthProperty().bind(siteTemplateTableView.widthProperty().divide(4));
        navBarColumn.prefWidthProperty().bind(siteTemplateTableView.widthProperty().divide(4));
        fileNameColumn.prefWidthProperty().bind(siteTemplateTableView.widthProperty().divide(4));
        scriptColumn.prefWidthProperty().bind(siteTemplateTableView.widthProperty().divide(4));
        //GET DATA COMPONENT TO ADD TO DATA
        CourseData data = (CourseData)app.getDataComponent();
        //HARD CODE SAMPLE OBJECTS
//        CourseSitePages pageOne = new CourseSitePages("Home", "index.html", "homeBuilder.js", true);
//        CourseSitePages pageTwo = new CourseSitePages("Syllabus", "syllabus.html", "syllabusBuilder.js", true);
//        data.getSitePages().add(pageOne);
//        data.getSitePages().add(pageTwo);
        //HARD CODING FOR SITE INFO
        SiteInfo thisSite = new SiteInfo(subjectBox, subjectNumber, semesterChoices, yearChoices, courseTitleField, courseInstrName, courseInstrHome);
        data.getSiteInfo().add(thisSite);
        
        siteTemplateTableView.setItems(data.getSitePages());
        
        //SET VALUES INSIDE TABLE
//        useColumn.setCellValueFactory(
//              new PropertyValueFactory<CourseSitePages, CheckBox>("use")  
//        );
        useColumn.setCellFactory( CheckBoxTableCell.forTableColumn(useColumn));
        navBarColumn.setCellValueFactory(
                new PropertyValueFactory<CourseSitePages, String>("navbarTitle")
        );
        fileNameColumn.setCellValueFactory(
                new PropertyValueFactory<CourseSitePages, String>("fileName")
        );
        scriptColumn.setCellValueFactory(
                new PropertyValueFactory<CourseSitePages,String>("script")
        );
        siteTemplateTableView.getColumns().add(useColumn);
        siteTemplateTableView.getColumns().add(navBarColumn);
        siteTemplateTableView.getColumns().add(fileNameColumn);
        siteTemplateTableView.getColumns().add(scriptColumn);
        
        //siteTemplateTableView.setMaxWidth(siteTemplatePane.getWidth());
        siteTemplateTableView.setEditable(true);
        siteTemplateVBox.getChildren().add(siteTemplateTableView);
        
        VBox pageStyleVBox = new VBox();
        pageStylePane.getChildren().add(pageStyleVBox);
        Label pageStyleLabel = new Label(props.getProperty(AppPropertyType.PAGE_STYLE_LABEL.toString()));
        labelList.add(pageStyleLabel);
        pageStyleVBox.getChildren().add(pageStyleLabel);
        
        HBox bannerImgBox = new HBox();
        pageStyleVBox.getChildren().add(bannerImgBox);
        Label bannerImgLabel = new Label(props.getProperty(AppPropertyType.BANNER_IMG_LABEL.toString()));
        labelList.add(bannerImgLabel);
        bannerImgBox.getChildren().add(bannerImgLabel);
        
        String bannerPath = System.getProperty("user.dir");
        bannerPath += "\\export_dir\\public_html\\images\\";
        bannerImg = new Image("http://www3.cs.stonybrook.edu/~cse308/Section01/images/SBUDarkRedShieldLogo.png");
        bannerImgView = new ImageView(bannerImg);
        bannerImgBox.getChildren().add(bannerImgView);
        bannerImgButton = new Button(props.getProperty(AppPropertyType.BANNER_IMG_BUTTON_TEXT.toString()));
        bannerImgBox.getChildren().add(bannerImgButton);
        
        HBox leftImgBox = new HBox();
        pageStyleVBox.getChildren().add(leftImgBox);
        Label leftFootImgLabel = new Label(props.getProperty(AppPropertyType.LEFT_FOOT_IMG_LABEL.toString()));
        labelList.add(leftFootImgLabel);
        leftImgBox.getChildren().add(leftFootImgLabel);
        leftFootImg = new Image("http://www3.cs.stonybrook.edu/~cse308/Section01/images/SBUWhiteShieldLogo.jpg");
        leftFootImgView = new ImageView(leftFootImg);
        leftFootImgView.maxWidth(300);
        leftImgBox.getChildren().add(leftFootImgView);
        leftFootImgButton = new Button(props.getProperty(AppPropertyType.LEFT_FOOT_IMG_BUTTON_TEXT));
        leftImgBox.getChildren().add(leftFootImgButton);
        
        HBox rightImgBox = new HBox();
        pageStyleVBox.getChildren().add(rightImgBox);
        Label rightFootImgLabel = new Label(props.getProperty(AppPropertyType.RIGHT_FOOT_IMG_LABEL.toString()));
        labelList.add(rightFootImgLabel);
        rightImgBox.getChildren().add(rightFootImgLabel);
        rightFootImg = new Image("http://www3.cs.stonybrook.edu/~cse308/Section01/images/CSLogo.png");
        rightFootImgView = new ImageView(rightFootImg);
        rightFootImgView.maxWidth(rightFootImg.widthProperty().multiply(0.7).doubleValue());
        rightImgBox.getChildren().add(rightFootImgView);
        rightFootImgButton = new Button(props.getProperty(AppPropertyType.RIGHT_FOOT_IMG_BUTTON_TEXT));
        rightImgBox.getChildren().add(rightFootImgButton);
        
        HBox stylesheetBox = new HBox();
        pageStyleVBox.getChildren().add(stylesheetBox);
        
        Label stylesheetLabel = new Label(props.getProperty(AppPropertyType.STYLESHEET_LABEL.toString()));
        labelList.add(stylesheetLabel);
        stylesheetBox.getChildren().add(stylesheetLabel);
        ObservableList<String> stylesheetOptions = FXCollections.observableArrayList(
                "course_homepage_layout.css",
                "sea_wolf.css"
        );
        stylesheetComboBox = new ComboBox(stylesheetOptions);
        stylesheetBox.getChildren().add(stylesheetComboBox);
        
        Label footNoteLabel = new Label(props.getProperty(AppPropertyType.FOOTNOTE_LABEL.toString()));
        labelList.add(footNoteLabel);
        pageStyleVBox.getChildren().add(footNoteLabel);
        
        //MAKE EVENT HANDLER FOR SAVING TEXT
        courseTitleField.setOnAction(e->{
           titleValue = courseTitleField.getText();
        });
        courseInstrName.setOnAction(e->{
            instrNameValue = courseInstrName.getText();
        });
        courseInstrHome.setOnAction(e->{
            instrHomeValue = courseInstrHome.getText();
        });
        
        //INITIALIZE AND ADD SCROLLPANE
        parentBorderPane = new BorderPane();
        
        parentScrollPane = new ScrollPane();
        //this.getChildren().add(parentScrollPane);
        parentScrollPane.setContent(parentPaneBox);
        
        parentScrollPane.prefHeight(500);
        parentScrollPane.prefWidth(500);
        
        parentBorderPane.setCenter(parentScrollPane);
                
        //DO ALL STYLING AND PADDING HERE
        //PAD LABELS FIRST
        subjectLabel.setPadding(new Insets(0, 40, 0, 0)); //margins around the whole grid
        number.setPadding(new Insets(0, 40, 0, 10));
        parentScrollPane.setMaxWidth(800);                       //(top/right/bottom/left)
        parentScrollPane.setPadding(new Insets(20, 20, 20, 20));
        courseInfoPane.setStyle("-fx-border-color: blue;");
        parentPaneBox.setPadding(new Insets(0, 0, 0, 70));
        parentPaneBox.setSpacing(60);
        
        //COURSE INFO PANE
        courseInfoLabel.setPadding(new Insets(0, 0, 20, 0));
        number.setPadding(new Insets(0, 20, 25, 20));
        semesterChoices.setStyle("-fx-pref-width: 68");
        semesterLabel.setPadding(new Insets(0, 34, 0, 0));
        yearLabel.setPadding(new Insets(0, 35, 25, 20));
        yearChoices.setStyle("-fx-pref-width: 68");
        courseTitle.setPadding(new Insets(0, 150, 25, 0));
        courseTitleField.setStyle("-fx-pref-width: 170");
        instrName.setPadding(new Insets(0, 78, 25, 0));
        courseInstrName.setStyle("-fx-pref-width: 170");
        instrHome.setPadding(new Insets(0, 78, 25, 0));
        courseInstrHome.setStyle("-fx-pref-width: 170");
        exportDirLabel.setPadding(new Insets(0, 50, 25, 0));
        exportDir.setPadding(new Insets(0, 70, 0, 0));
        courseInfoPane.setStyle("-fx-background-color: #74C2E1");
        
        //SITE TEMPLATE PANE
        siteTemplateLabel.setPadding(new Insets(0, 0, 20, 0));
        siteTemplateDescription.setPadding(new Insets(0, 0, 10, 0));
        siteTemplateDir.setPadding(new Insets(0, 0, 10, 0));
        sitePagesLabel.setPadding(new Insets(20, 0, 15, 0));
        siteTemplatePane.setStyle("-fx-background-color: #74C2E1");
        
        //PAGE STYLE PANE
        pageStyleLabel.setPadding(new Insets(0, 0, 25, 0));
        bannerImgLabel.setPadding(new Insets(0, 40, 25, 0));
        leftFootImgLabel.setPadding(new Insets(0, 55, 25, 0));
        rightFootImgLabel.setPadding(new Insets(0, 48, 25, 0));
        stylesheetLabel.setPadding(new Insets(0, 112, 20, 0));
        stylesheetComboBox.setStyle("-fx-pref-width: 60");
        
        pageStylePane.setStyle("-fx-background-color: #74C2E1");
        
        //DO EVENT HANDLING RIGHT HERE
        bannerImgButton.setOnAction(e->{
            DirectoryChooser exportDirectory = new DirectoryChooser();
            File exportedDirectory = exportDirectory.showDialog(app.getGUI().getWindow());
        });
        rightFootImgButton.setOnAction(e->{
            DirectoryChooser exportDirectory = new DirectoryChooser();
            File exportedDirectory = exportDirectory.showDialog(app.getGUI().getWindow());
        });
        leftFootImgButton.setOnAction(e->{
            DirectoryChooser exportDirectory = new DirectoryChooser();
            File exportedDirectory = exportDirectory.showDialog(app.getGUI().getWindow());
        });
    }
    
    public Label getCourseInfoLabel() {
        return courseInfoLabel;
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
    public Pane getCourseInfoPane(){
        return courseInfoPane;
    }
    public Pane getSiteTemplatePane(){
        return siteTemplatePane;
    }
    public Pane getPageStylePane(){
        return pageStylePane;
    }
    public ScrollPane getParentScrollPane(){
        return parentScrollPane;
    }
    public String getTitleValue() {
        return titleValue;
    }

    public String getInstrNameValue() {
        return instrNameValue;
    }

    public String getInstrHomeValue() {
        return instrHomeValue;
    }
    
    
    
    public ComboBox getSubjectBox() {
        return subjectBox;
    }

    public ComboBox getSemesterChoices() {
        return semesterChoices;
    }

    public ComboBox getSubjectNumber() {
        return subjectNumber;
    }

    public ComboBox getYearChoices() {
        return yearChoices;
    }

    public TextField getCourseTitleField() {
        return courseTitleField;
    }

    public TextField getCourseInstrName() {
        return courseInstrName;
    }

    public TextField getCourseInstrHome() {
        return courseInstrHome;
    }
}
