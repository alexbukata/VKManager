package vkmanager.model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
        
        User user = User.createUser(138367346, "Max", "Vorontsov", "1", "c88df59f3ae5c427e5adab0430151a44b50bbb2b0002d0888def3bf986579c384991e21cb125136d69cd3");
        launch(args);
    }

    private void showPlayer(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(VkManager.class.getResource("/vkmanager/view/photos/Photos.fxml"));
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
