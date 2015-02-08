package vkmanager.model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class VkManager extends Application{

    private BorderPane rootPane;
    private AnchorPane musicPane;
    private AnchorPane mainPane;

    @Override
    public void start(Stage stage) throws Exception{

        initRootPane();
        showPlayer();
        Scene scene = new Scene(mainPane);

        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        launch(args);
    }

    private void showPlayer(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(VkManager.class.getResource("/vkmanager/view/music/AudioPlayer.fxml"));
            AnchorPane audioPane = loader.load();
            rootPane.setCenter(audioPane);
        } catch (IOException ex) {
            Logger.getLogger(VkManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void initRootPane(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(VkManager.class.getResource("/vkmanager/view/RootVK.fxml"));
            mainPane = loader.load();
            rootPane = (BorderPane) mainPane.getChildren().get(0);
            
        } catch (IOException ex) {
            Logger.getLogger(VkManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
