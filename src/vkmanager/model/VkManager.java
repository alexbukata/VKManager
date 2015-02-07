package vkmanager.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
<<<<<<< HEAD
=======
import vkmanager.controller.PhotosController;
import vkmanager.model.photos.*;
>>>>>>> b825d0a4e3ec4d9fdd323a07dc1896996c0663e3

public class VkManager extends Application{

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/vkmanager/view/photos/Photos.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
<<<<<<< HEAD
    public static void main(String[] args){
        launch(args);
=======
    public static void main(String[] args) throws IOException{
        User user = User.createUser(87582268, "Max", "Vorontos", "photolink", "e76387afe7ffb846fe1ae7bf0b723d1e0fd4784ab20aee5037a77a251943a1f2f69e7ca5d40d40018f4fe");
        launch(args);
        
        //ArrayList<VKPhotoAlbum> albums = vkApi.getAllUserAlbums();
        
        
        /*for(VKPhotoAlbum album : albums){
            System.out.println("Альбом: " + album.getName() + "; Описание: " + album.getDescription() + "; Обложка: " + album.getThumb());
        }*/
        
        //ArrayList<VKPhoto> photos = vkApi.getPhotosFromAlbum(albums.get(1).getId());
        
        /*vkApi.savePhotos(photos);*/
        
        //vkApi.getAlbumThumb(Integer.parseInt(albums.get(1).getThumb()));
        //System.out.println(albums);
        
        //launch(args);
>>>>>>> b825d0a4e3ec4d9fdd323a07dc1896996c0663e3
    }

}
