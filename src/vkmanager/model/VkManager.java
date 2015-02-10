package vkmanager.model;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VkManager extends Application{
    private AnchorPane musicPane;
    private AnchorPane mainPane;

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(VkManager.class.getResource("/vkmanager/view/Authorize.fxml"));
        mainPane = loader.load();
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();
        
        
        
        /*initRootPane();
        showPlayer();
        Scene scene = new Scene(mainPane);

        stage.setScene(scene);
        stage.show();*/
    }
    
    public static void main(String[] args) throws IOException{
        //User user = User.createUser(87582268, "Max", "Vorontsov", "1", "9a60686c01d8a6761246267ed57ae0cec15374b4f4983f62dccbb54d221c6786c6b06b5c4d748535ab4e7");
        launch(args);
    }

}
