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
import CourseSite.UI.CourseTADataPane;
import java.util.Locale;
import CourseSite.files.CourseFiles;
import CourseSite.style.CourseStyle;
import CourseSite.data.CourseData;
import djf.AppTemplate;
import CourseSite.workspace.CourseWorkspace;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import static javafx.application.Application.launch;

/**
 * This class serves as the application class for our TA Manager App program. 
 * Note that much of its behavior is inherited from AppTemplate, as defined in
 * the Desktop Java Framework. This app starts by loading all the UI-specific
 * settings like icon files and tooltips and other things, then the full 
 * User Interface is loaded using those settings. Note that this is a 
 * JavaFX application.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class CourseApp extends AppTemplate {
    /**
     * This hook method must initialize all four components in the
     * proper order ensuring proper dependencies are respected, meaning
     * all proper objects are already constructed when they are needed
     * for use, since some may need others for initialization.
     */
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL FOUR COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, SO BE CAREFUL OF THE ORDER
        dataComponent = (AppDataComponent) new CourseData(this);
        workspaceComponent =  new CourseWorkspace(this);
        fileComponent = (AppFileComponent) new CourseFiles(this);
        //taDataComponent = new CourseTADataPane(this);
        styleComponent = new CourseStyle(this);
        //debug
        //int i=0;
    }
    //USED TO HELP WITH TEST SAVE CLASS
    public void buildDataFilesComponent(){
        dataComponent = (AppDataComponent)new CourseData(this);
        fileComponent = (AppFileComponent)new CourseFiles(this);
        workspaceComponent = (AppWorkspaceComponent)new CourseWorkspace(this, true);
    }
    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method inherited
     * from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }
}
