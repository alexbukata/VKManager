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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import vkmanager.model.User;
import vkmanager.model.VKApi;
import vkmanager.model.photos.VKPhotoAlbum;

//http://vk.com/id49364104

public class PhotosController implements Initializable{
    @FXML
    private Button testButt;
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField userIdAlb;
    
    private int idForDownload;
    private User user;
    private VKApi vkapi;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try{
            downloadAlbums();
        }catch(IOException ex){
            Logger.getLogger(PhotosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getIdFromHref(String href){
        href = href.substring(href.lastIndexOf("vk.com") + 7);
        System.out.println(href);
        int id = 0;
        /*If id is integer*/
        if((href.substring(0, 2).equals("id"))){
            for(int i = 2; i < href.length(); i++){
                if(i == href.length()-1 || href.charAt(i) == '?' || href.charAt(i) == '&'){
                    if(i == href.length()-1){
                        id = Integer.parseInt(href.substring(2, i+1));
                    }else{
                        id = Integer.parseInt(href.substring(2, i));
                    }
                    System.out.println(id);
                    break;
                }
            }
        /*if id is string*/
        }else{
            for(int i = 0; i < href.length(); i++){
                if(i == href.length()-1 || href.charAt(i) == '?' || href.charAt(i) == '&'){
                    if(i == href.length()-1){
                        href = href.substring(0, i+1);
                    }else{
                        href = href.substring(0, i);
                    }
                    System.out.println(href);
                    break;
                }
            }
            id = vkapi.getIntUserId(href);
        }
        return id;
    }
    
    @FXML
    private void downloadAlbums() throws IOException{
         user = User.getCurrentUser();
         vkapi = new VKApi(user);
         idForDownload = user.getId();
         if(userIdAlb.getText() != null && !userIdAlb.getText().isEmpty()){
             idForDownload = getIdFromHref(userIdAlb.getText());
         }
         System.out.println(idForDownload);
         ArrayList<VKPhotoAlbum> albums = vkapi.getAllUserAlbums(idForDownload);
         showAlbums(albums);
    }
    
    private void showAlbums(ArrayList<VKPhotoAlbum> albums){
        gridPane.getChildren().clear();
        
        int i = 0; int j = 0;
        for(VKPhotoAlbum album : albums){
            Button alb_butt = new Button();
            String album_cover = (album.getThumb() != null) ? album.getThumb() : "http://vk.com/images/camera_big.png";
            Image alb_thumb_link = new Image(album_cover);
            ImageView alb_thumb = new ImageView(alb_thumb_link);
            alb_butt.setGraphic(alb_thumb);
            alb_butt.setStyle("-fx-background-color: white;");
            alb_butt.setAlignment(Pos.TOP_CENTER);
            Text albName = new Text(album.getName());
            albName.setWrappingWidth(240);
            albName.setFont(new Font(20));
            alb_butt.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    try{
                        vkapi.savePhotos(vkapi.getPhotosFromAlbum(album.getId(), idForDownload));
                    }catch(IOException ex){
                        Logger.getLogger(PhotosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            gridPane.add(alb_butt, j, i);
            gridPane.add(albName, j, i);
            albName.setTextOrigin(VPos.BOTTOM);
            albName.setTextAlignment(TextAlignment.CENTER);
            j++;
            if(j==3){
                i++;
                j = 0;
                gridPane.addRow(i);
            }
        }
    }
    
}
