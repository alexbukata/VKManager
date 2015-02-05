package vkmanager.model;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vkmanager.model.photos.*;

public class VkManager extends Application{

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/vkmanager/view/AudioPlayer.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        
        User user = User.createUser(87582268, "Max", "Vorontos", "photolink", "134989dc2668bc61a978c61b33f99692b80ea99f26301fbbe7a1d12bec3a5f73d9668d1158b1f42ba37b9");
        VKApi vkApi = new VKApi(user);
        
        ArrayList<VKPhotoAlbum> albums = vkApi.getAllUserAlbums();
        /*for(VKPhotoAlbum album : albums){
            System.out.println("Альбом: " + album.getName() + "; Описание: " + album.getDescription() + "; Обложка: " + album.getThumb());
        }*/
        
        ArrayList<VKPhoto> photos = vkApi.getPhotosFromAlbum(albums.get(1).getId());
        
        vkApi.savePhotos(photos);
        
        //vkApi.getAlbumThumb(Integer.parseInt(albums.get(1).getThumb()));
        //System.out.println(albums);
        
        //launch(args);
    }

}
