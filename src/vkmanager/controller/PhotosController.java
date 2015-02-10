/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vkmanager.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import vkmanager.model.User;
import vkmanager.model.VKApi;
import vkmanager.model.photos.VKPhotoAlbum;

public class PhotosController implements Initializable{
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField userIdAlb;
    @FXML
    private SplitPane splitPane;
    
    private int idForDownload;
    private User user;
    private VKApi vkapi;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        user = User.getCurrentUser();
        vkapi = VKApi.getVKApi();
        try{
            loadAlbums();
        }catch(IOException ex){
            Logger.getLogger(PhotosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void loadAlbums() throws IOException{
         idForDownload = user.getId();
         if(userIdAlb.getText() != null && !userIdAlb.getText().isEmpty()){
             idForDownload = vkapi.getIdFromHref(userIdAlb.getText());
         }
         
         if(vkapi.getNumberOfAlbums(idForDownload) != 0){
            vkapi.getNumberOfAlbums(idForDownload);
            ArrayList<VKPhotoAlbum> albums = vkapi.getAllUserAlbums(idForDownload);
            showAlbums(albums);
         }else{
             gridPane.getChildren().clear();
             Text t = new Text("Доступных альбомов нет"); 
             t.setFont(new Font(20));
             gridPane.add(t, 0, 0);
         }
    }
    
    private void showAlbums(ArrayList<VKPhotoAlbum> albums){
        gridPane.getChildren().clear();
        
        int i = 0; int j = 0;
        gridPane.addRow(0);
        for(VKPhotoAlbum album : albums){
            String albumCover = (album.getThumb() != null) ? album.getThumb() : "/res/img/camera_big.png";
            Image albThumbLink = new Image(albumCover);
            ImageView albThumb = new ImageView(albThumbLink);
            
            albThumb.setFitHeight(160);
            albThumb.setFitWidth(240);
            
            albThumb.setPreserveRatio(true);
            albThumb.setSmooth(true);
            albThumb.setCache(true);
            
            Text albName = new Text(album.getName());
            albName.setWrappingWidth(240);
            if(albName.getText().length() > 20)
                albName.setFont(new Font(18));
            else albName.setFont(new Font(20));
            albName.setTextAlignment(TextAlignment.CENTER);
            
            Button albumButt = new Button();
            albumButt.setGraphic(albThumb);
            albumButt.setStyle("-fx-background-color: white;");
            albumButt.setMinSize(240, 160);
            albumButt.setAlignment(Pos.CENTER);
            
            albumButt.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent event){
                    Parent root;
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vkmanager/view/photos/Photos_detailed.fxml"));
                        root = loader.load();
                        PhotosDetailedController detController = loader.getController();
                        detController.showPhotosFromAlbum(album, idForDownload);
                        
                        Stage stage = new Stage();
                        stage.setTitle("Альбом: " + album.getName());
                        stage.setScene(new Scene(root, 800, 600));
                        stage.show();
                        //((Node)(event.getSource())).getScene().getWindow().hide();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
            BorderPane bp = new BorderPane(albName, albumButt, null, null, null);
            gridPane.add(bp, j, i);
            
            j++;
            if(j==3){
                i++;
                j = 0;
                gridPane.addRow(i);
            }
        }
    }
    
}
