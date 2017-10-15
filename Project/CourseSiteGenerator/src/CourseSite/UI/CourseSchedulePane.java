/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseSite.UI;

import CourseSite.app.CourseApp;
import static CourseSite.app.TAProp.END_DATE_FRIDAY;
import static CourseSite.app.TAProp.MISSING_TA_NAME_MESSAGE;
import static CourseSite.app.TAProp.MISSING_TA_NAME_TITLE;
import static CourseSite.app.TAProp.SCHEDULE_CALENDAR_BOUNDARY_ERROR;
import static CourseSite.app.TAProp.START_DATE_MONDAY;
import CourseSite.data.CourseData;
import CourseSite.data.Schedule;
import CourseSite.workspace.CourseController;
import djf.settings.AppPropertyType;
import djf.ui.AppMessageDialogSingleton;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
import properties_manager.PropertiesManager;

/**
 *
 * @author John
 */
public class CourseSchedulePane {
    Pane parentPane;
    VBox parentPaneBox;
    
    ScrollPane parentScrollPane;
    
    BorderPane parentBorderPane;
    
    Pane calendarBoundariesPane;
    Pane scheduleItemsPane;
    PropertiesManager props;
    ObservableList<Label> labelList;
    //TableView stuff
    TableView<Schedule> itemsTable;
    TableColumn<Schedule, String> typeColumn;
    TableColumn<Schedule, String> dateColumn;
    TableColumn<Schedule, String> titleColumn;
    TableColumn<Schedule, String> topicColumn;
    Button minusBox;
    VBox addEditBox;
    ComboBox typeComboBox;
    DatePicker dateRow;
    TextField timeTextField;
    TextField titleTextField;
    TextField topicTextField;
    TextField linkTextField;
    TextField criteriaTextField;
    Button addEditButton;
    Button clearButton;
    
    Label scheduleLabel;
    CourseController controller;
    
    DatePicker startDatePicker;
    DatePicker endDatePicker;
    //BOUNDARY VALUES
    String calendarStart;
    String calendarEnd;
    
    public CourseSchedulePane(CourseApp app){
        controller = new CourseController(app);
        props = PropertiesManager.getPropertiesManager();
        parentPaneBox = new VBox();
        parentPane = new Pane();
        parentPane.getChildren().add(parentPaneBox);
        
        scheduleLabel = new Label(props.getProperty(AppPropertyType.SCHEDULE_LABEL.toString()));
        parentPaneBox.getChildren().add(scheduleLabel);
        
        calendarBoundariesPane = new Pane();
        scheduleItemsPane = new Pane();
        
        parentPaneBox.getChildren().add(calendarBoundariesPane);
        VBox calendarBoundaryVBox = new VBox();
        
        calendarBoundariesPane.getChildren().add(calendarBoundaryVBox);
        Label calendarBoundaryLabel = new Label(props.getProperty(AppPropertyType.CALENDAR_BOUNDARIES_LABEL.toString()));
        
        calendarBoundaryVBox.getChildren().add(calendarBoundaryLabel);
        
        HBox calendarTimesHBox = new HBox();
        calendarBoundaryVBox.getChildren().add(calendarTimesHBox);
        Label calendarStartTimeLabel = new Label(props.getProperty(AppPropertyType.CALENDAR_START_TIME_LABEL.toString()));
        calendarTimesHBox.getChildren().add(calendarStartTimeLabel);
        startDatePicker = new DatePicker();
        calendarTimesHBox.getChildren().add(startDatePicker);
        Label calendarEndTimeLabel = new Label(props.getProperty(AppPropertyType.CALENDAR_END_TIME_LABEL.toString()));
        calendarTimesHBox.getChildren().add(calendarEndTimeLabel);
        endDatePicker = new DatePicker();
        calendarTimesHBox.getChildren().add(endDatePicker);
        
        parentPaneBox.getChildren().add(scheduleItemsPane);
        VBox scheduleItemVBox = new VBox();
        scheduleItemsPane.getChildren().add(scheduleItemVBox);
        Label scheduleItemsLabel = new Label(props.getProperty(AppPropertyType.SCHEDULE_ITEMS_LABEL.toString()));
        HBox scheduleItemHBox = new HBox();
        scheduleItemVBox.getChildren().add(scheduleItemHBox);
        scheduleItemHBox.getChildren().add(scheduleItemsLabel);
        minusBox = new Button();
        Label minusLabel = new Label(props.getProperty(AppPropertyType.RECITATION_MINUS_LABEL.toString()));
        minusBox.setText("-");
        scheduleItemHBox.getChildren().add(minusBox);
        
        itemsTable = new TableView();
        typeColumn = new TableColumn(props.getProperty(AppPropertyType.CALENDAR_TABLE_TYPE.toString()));
        dateColumn = new TableColumn(props.getProperty(AppPropertyType.CALENDAR_TABLE_DATE.toString()));
        titleColumn = new TableColumn(props.getProperty(AppPropertyType.CALENDAR_TABLE_TITLE.toString()));
        topicColumn = new TableColumn(props.getProperty(AppPropertyType.CALENDAR_TABLE_TOPIC.toString()));
        
        //SET WIDTHS OF COLUMNS
        typeColumn.prefWidthProperty().bind(itemsTable.widthProperty().divide(4));
        dateColumn.prefWidthProperty().bind(itemsTable.widthProperty().divide(4));
        titleColumn.prefWidthProperty().bind(itemsTable.widthProperty().divide(4));
        topicColumn.prefWidthProperty().bind(itemsTable.widthProperty().divide(4));
        
        // BEFORE ADDING TO SCHEDULE ITEMS
        CourseData data = (CourseData)app.getDataComponent();
        //HARD CODED VALUES
//        Schedule itemOne = new Schedule("holiday", "mon", "supper", "food");
//        Schedule itemTwo = new Schedule("holiday", "mon", "supper", "food");
//        data.getSchedules().add(itemOne);
//        data.getSchedules().add(itemTwo);
        itemsTable.setItems(data.getSchedules());
        
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("type")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("date")
        );
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("title")
        );
        topicColumn.setCellValueFactory(
                new PropertyValueFactory<Schedule, String>("topic")
        );
        
        itemsTable.getColumns().add(typeColumn);
        itemsTable.getColumns().add(dateColumn);
        itemsTable.getColumns().add(titleColumn);
        itemsTable.getColumns().add(topicColumn);
        
        scheduleItemVBox.getChildren().add(itemsTable);
        addEditBox = new VBox();
        scheduleItemVBox.getChildren().add(addEditBox);
        Label addEditLabel = new Label(props.getProperty(AppPropertyType.ADD_EDIT_LABEL.toString()));
        addEditBox.getChildren().add(addEditLabel);
        HBox typeHBox = new HBox();
        addEditBox.getChildren().add(typeHBox);
        Label typeLabel = new Label(props.getProperty(AppPropertyType.CALENDAR_TABLE_TYPE.toString()));
        typeHBox.getChildren().add(typeLabel);
        ObservableList<String> typeOptions = FXCollections.observableArrayList(
                "Holiday",
                "Lecture",
                "HW",
                "References"
        );
        typeComboBox = new ComboBox(typeOptions);
        typeHBox.getChildren().add(typeComboBox);
        
        //DATE SECTION
        HBox dateHBox = new HBox();
        addEditBox.getChildren().add(dateHBox);
        Label dateLabel = new Label(props.getProperty(AppPropertyType.CALENDAR_TABLE_DATE.toString()));
        dateHBox.getChildren().add(dateLabel);
        dateRow = new DatePicker();
        dateHBox.getChildren().add(dateRow);
        //Time section
        HBox timeHBox = new HBox();
        addEditBox.getChildren().add(timeHBox);
        Label timeLabel = new Label(props.getProperty(AppPropertyType.TIME_LABEL.toString()));
        timeHBox.getChildren().add(timeLabel);
        timeTextField = new TextField();
        timeHBox.getChildren().add(timeTextField);
        //title section
        HBox titleHBox = new HBox();
        addEditBox.getChildren().add(titleHBox);
        Label titleLabel = new Label(props.getProperty(AppPropertyType.CALENDAR_TABLE_TITLE.toString()));
        titleHBox.getChildren().add(titleLabel);
        titleTextField = new TextField();
        titleHBox.getChildren().add(titleTextField);
        //topic section
        HBox topicHBox = new HBox();
        addEditBox.getChildren().add(topicHBox);
        Label topicLabel = new Label(props.getProperty(AppPropertyType.CALENDAR_TABLE_TOPIC.toString()));
        dateHBox.getChildren().add(topicLabel);
        topicTextField = new TextField();
        topicHBox.getChildren().add(topicLabel);
        topicHBox.getChildren().add(topicTextField);
        //link section
        HBox linkHBox = new HBox();
        addEditBox.getChildren().add(linkHBox);
        Label linkLabel = new Label(props.getProperty(AppPropertyType.LINK_LABEL.toString()));
        linkHBox.getChildren().add(linkLabel);
        linkTextField = new TextField();
        linkHBox.getChildren().add(linkTextField);
        //Criteria section
        HBox criteriaHBox = new HBox();
        addEditBox.getChildren().add(criteriaHBox);
        Label criteriaLabel = new Label(props.getProperty(AppPropertyType.CRITERIA_LABEL.toString()));
        criteriaHBox.getChildren().add(criteriaLabel);
        criteriaTextField = new TextField();
        criteriaHBox.getChildren().add(criteriaTextField);
        //update section
        HBox updateHBox = new HBox();
        addEditBox.getChildren().add(updateHBox);
        addEditButton = new Button(props.getProperty(AppPropertyType.ADD_EDIT_LABEL.toString()));
        updateHBox.getChildren().add(addEditButton);
        clearButton = new Button(props.getProperty(AppPropertyType.CLEAR_LABEL.toString()));
        updateHBox.getChildren().add(clearButton);
        
        parentScrollPane = new ScrollPane(parentPane);
        parentBorderPane = new BorderPane();
        
        parentBorderPane.setCenter(parentScrollPane);
        
        //START STYLING
        parentScrollPane.setMaxWidth(800);
        parentScrollPane.setPadding(new Insets(20, 20, 20, 20));
        parentPaneBox.setPadding(new Insets(0, 0, 0, 70));
        parentPaneBox.setSpacing(30);
        parentScrollPane.setStyle("-fx-background-color: blue");
        //CALENDAR BOUNDARIES PANE
        calendarBoundariesPane.setStyle("-fx-background-color: #74C2E1");
        scheduleItemsPane.setStyle("-fx-background-color: #74C2E1");
        itemsTable.prefWidthProperty().bind(scheduleItemsPane.widthProperty());
        //ADD EDIT PANE
        addEditLabel.setPadding(new Insets(0, 0, 20, 0));
        typeComboBox.setMaxWidth(100);
        typeLabel.setPadding(new Insets(0, 50, 20, 0));
        dateLabel.setPadding(new Insets(0, 50, 20, 0));
        timeLabel.setPadding(new Insets(0, 50, 20, 0));
        titleLabel.setPadding(new Insets(0, 43, 20, 0));
        topicLabel.setPadding(new Insets(0, 43, 20, 0));
        linkLabel.setPadding(new Insets(0, 50, 20, 0));
        criteriaLabel.setPadding(new Insets(0, 20, 20, 0));
        updateHBox.setSpacing(50);

        //DEFINE EVENT HANDLERS HERE
        addEditButton.setOnAction(e->{
            String time = timeTextField.getText();
            String title = titleTextField.getText();
            String topic = topicTextField.getText();
            String link = linkTextField.getText();
            String criteria = criteriaTextField.getText();
            //DO PROCESSING TO GET DATE
            String date="";
            try{
                int dayOfMonth = dateRow.getValue().getDayOfMonth();
                int month = dateRow.getValue().getMonthValue();
                int year = dateRow.getValue().getYear();

                String dayOfMonthString = String.valueOf(dayOfMonth);
                String monthString = String.valueOf(month);
                String yearString = String.valueOf(year);
                date = monthString + "/" + dayOfMonthString + "/" + yearString;
            } catch(NullPointerException ex){
                
            }
            //GET TYPE COMBOBOX
            try{
                String value = (String)typeComboBox.getSelectionModel().getSelectedItem();
                if(value.equals("Holiday")){
                    //Schedule(String initType, String initDate, String initTitle, String initTopic)
                    Schedule holiday = new Schedule("Holiday", date, title, topic);
                    holiday.setCriteria(criteria);
                    holiday.setLink(link);
                    holiday.setTime(time);
                    if(addEditButton.getText().equals("Update")){
                       Schedule oldItem = itemsTable.getSelectionModel().getSelectedItem();
                       controller.updateScheduleItem(oldItem, holiday);
                    } else{
                        controller.addScheduleItem(holiday);
                    }

                } else if(value.equals("Lecture")){
                    Schedule lecture = new Schedule("Lecture", date, title, topic);
                    lecture.setCriteria(criteria);
                    lecture.setLink(link);
                    lecture.setTime(time);
                    if(addEditButton.getText().equals("Update")){
                       Schedule oldItem = itemsTable.getSelectionModel().getSelectedItem();
                       controller.updateScheduleItem(oldItem, lecture);
                    } else{
                        controller.addScheduleItem(lecture); 
                    }

                } else if(value.equals("HW")){
                    Schedule hw = new Schedule("HW", date, title, topic);
                    hw.setCriteria(criteria);
                    hw.setLink(link);
                    hw.setTime(time);
                    if(addEditButton.getText().equals("Update")){
                       Schedule oldItem = itemsTable.getSelectionModel().getSelectedItem();
                       controller.updateScheduleItem(oldItem, hw);
                    } else{
                        controller.addScheduleItem(hw);
                    }
                } else if(value.equals("References")){
                    Schedule reference = new Schedule("References", date, title, topic);
                    reference.setCriteria(criteria);
                    reference.setLink(link);
                    reference.setTime(time);
                    if(addEditButton.getText().equals("Update")){
                       Schedule oldItem = itemsTable.getSelectionModel().getSelectedItem();
                       controller.updateScheduleItem(oldItem, reference);
                    } else{
                        controller.addScheduleItem(reference);
                    }
                }
            } catch(NullPointerException ex){
                    Schedule illa = new Schedule("", date, title, topic);
                    controller.addScheduleItem(illa);
            }
            timeTextField.clear();
            titleTextField.clear();
            topicTextField.clear();
            linkTextField.clear();
            criteriaTextField.clear();
            dateRow.setValue(null);
            typeComboBox.setValue(null);
        });
        itemsTable.setOnMouseClicked(e->{
            addEditButton.setText("Update");
            Schedule selectItem = itemsTable.getSelectionModel().getSelectedItem();
            timeTextField.setText(selectItem.getTime());
            titleTextField.setText(selectItem.getTitle());
            topicTextField.setText(selectItem.getTopic());
            linkTextField.setText(selectItem.getLink());
            criteriaTextField.setText(selectItem.getCriteria());
            String[] dateComps = selectItem.getDate().split("/");
            int month = Integer.parseInt(dateComps[0]);
            int day = Integer.parseInt(dateComps[1]);
            int year = Integer.parseInt(dateComps[2]);
            dateRow.setValue(LocalDate.of(year, month, day));
            typeComboBox.setValue(selectItem.getType());
        });
        itemsTable.setOnKeyPressed(e->{
            controller.deleteScheduleItem(e.getCode());
        });
        clearButton.setOnAction(e->{
            timeTextField.clear();
            titleTextField.clear();
            topicTextField.clear();
            linkTextField.clear();
            criteriaTextField.clear();
            dateRow.setValue(null);
            addEditButton.setText("Add/Edit");
            typeComboBox.setValue(null);
        });
        //HAVE TWO LISTENERS FOR CHECKING DATE PICKERS
        startDatePicker.setOnAction(e->{
            boolean value=true;
            if(startDatePicker.getValue() != null){
                if(endDatePicker.getValue() != null){ 
                    value = checkDateValue();
                }
            }
            if(!value){
                startDatePicker.setValue(null);
                endDatePicker.setValue(null);
            } else{
                try{
                String initCalendarStart = createDate(startDatePicker.getValue());
                String initCalendarEnd = createDate(endDatePicker.getValue());
                setCalendarStart(initCalendarStart);
                setCalendarEnd(initCalendarEnd);
                } catch(NullPointerException ex){
                    
                }
            }
        });
        endDatePicker.setOnAction(e->{
            boolean value = true;
            if(endDatePicker.getValue() != null){
                if(startDatePicker.getValue() != null){
                    value= checkDateValue();
                }
            }
            if(!value){
                startDatePicker.setValue(null);
                endDatePicker.setValue(null);
            } else{
                try{
                String initCalendarStart = createDate(startDatePicker.getValue());
                String initCalendarEnd = createDate(endDatePicker.getValue());
                setCalendarStart(initCalendarStart);
                setCalendarEnd(initCalendarEnd);
                } catch(NullPointerException ex){
                    
                }
            }
        });
    }
    
    public String createDate(LocalDate initDate){
        int dayOfMonth = initDate.getDayOfMonth();
        int month = initDate.getMonthValue();
        int year = initDate.getYear();

        String dayOfMonthString = String.valueOf(dayOfMonth);
        String monthString = String.valueOf(month);
        String yearString = String.valueOf(year);
        String date = monthString + "/" + dayOfMonthString + "/" + yearString;
        return date;
    }
    
    public boolean checkDateValue(){
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();
        boolean wasItTrue = true;
        if(start.getDayOfWeek().getValue() != 1){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(START_DATE_MONDAY), props.getProperty(START_DATE_MONDAY));
            wasItTrue = false;
        }
        
        if(end.getDayOfWeek().getValue() != 5){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(END_DATE_FRIDAY), props.getProperty(END_DATE_FRIDAY));
            wasItTrue = false;
        }
        //NOW CHECK IF ONE OF THE VALUES IS BEFORE THE OTHER
        if(start.compareTo(end)>0){
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SCHEDULE_CALENDAR_BOUNDARY_ERROR), props.getProperty(SCHEDULE_CALENDAR_BOUNDARY_ERROR));
            wasItTrue = false;
        }
        return wasItTrue;
    }
    
    public void setCalendarStart(String calendarStart) {
        this.calendarStart = calendarStart;
    }

    public void setCalendarEnd(String calendarEnd) {
        this.calendarEnd = calendarEnd;
    }

    public String getCalendarStart() {
        return calendarStart;
    }

    public String getCalendarEnd() {
        return calendarEnd;
    }

    public TableView<Schedule> getItemsTable() {
        return itemsTable;
    }

    public Label getScheduleLabel() {
        return scheduleLabel;
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
