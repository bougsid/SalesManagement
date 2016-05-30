package view.screensframework;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Angie
 */
public class ScreensController {
    //Holds the screens to be displayed

    private static final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private HashMap<String, Parent> screens = new HashMap<>();
    private HashMap<String, Stage> stages = new HashMap<>();

    //Add the screen to the collection
    public void addScreen(String name, Parent screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Parent getScreen(String name) {
        return screens.get(name);
    }

    public String getName(String resource) {
        return resource.substring(resource.lastIndexOf("/") + 1, resource.lastIndexOf(".fxml"));
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public Stage loadScreen(String resource, boolean init) {
        //String name = getName(resource);
        Stage stage = stages.get(resource);
        if(stage == null){
            stage = new Stage();
            stages.put(resource,stage);
        }
        Parent parent = getScreen(resource);
        if (parent == null) {
            parent = loadHelper(null, resource);
            screens.put(resource, parent);
        } else {
            if (init) {
                parent = loadHelper(parent.getScene(), resource);
            }
        }
        stage.setScene(parent.getScene());
        stage.show();
        return stage;
    }

    //This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
//    public Stage setScreen(final String resource) {
//        if (screens.get(resource) != null) {
//            Stage stage = stages.get(resource);
//            if (stage != null) {
//                stage.show();
//                return stage;
//            }
//
//            stage = new Stage();
//            Parent parent = getScreen(resource);
//            Scene scene = parent.getScene();
//            if (scene == null) {
//                scene = new Scene(getScreen(resource));
//                scene.getStylesheets().add(getClass().getResource("/resources/css/tableView.css").toExternalForm());
//            } else {
//                try {
//                    scene.setRoot(FXMLLoader.load(getClass().getResource(resource)));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            stage.setScene(scene);
//            stage.show();
//            stages.put(resource, stage);
//            return stage;
//        } else {
//            System.out.println("screen hasn't been loaded!!! \n");
//            return null;
//        }
//    }

    public Parent loadHelper(Scene scene, String resource) {
        Parent parent = null;
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            myLoader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> param) {
                    return context.getBean(param);
                }
            });
            myLoader.setResources(ResourceBundle.getBundle("bundles.sm", Locale.getDefault()));
            parent = myLoader.load();
            ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
            myScreenController.setScreenParent(this);
            if (scene == null) {
                scene = new Scene(parent);
            } else {
                scene.setRoot(parent);
            }
            scene.getStylesheets().add(getClass().getResource("/resources/css/tableView.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }

    public void closeStage(String resource) {
        Stage stage = stages.get(resource);
        if (stage != null) {
            stages.remove(resource);
            stage.close();
        }
    }

    //This method will remove the screen with the given name from the collection of screens
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }
}
