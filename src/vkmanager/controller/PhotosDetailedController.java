/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vkmanager.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import vkmanager.model.User;
import vkmanager.model.VKApi;
import vkmanager.model.photos.VKPhoto;
import vkmanager.model.photos.VKPhotoAlbum;

//http://vk.com/id49364104

public class PhotosDetailedController implements Initializable{
    @FXML
    private GridPane gridPaneDetailed;
    @FXML
    private Label albumDescription;
    
    private int idForDownload;
    private User user;
    private VKApi vkapi;
    private ArrayList<VKPhoto> photos;
    private VKPhotoAlbum album;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        vkapi = new VKApi(User.getCurrentUser());
    }
    
    public void showPhotosFromAlbum(VKPhotoAlbum album, int userId){
        this.album = album;
        idForDownload = album.getId();
        albumDescription.setText(album.getDescription());
        photos = vkapi.getPhotosFromAlbum(idForDownload, userId);
        
        int i=0; int j = 0;
        gridPaneDetailed.addRow(0);
        for(VKPhoto photo : photos){
            Image photoThumbLink = new Image(photo.getLink_s());
            ImageView photoThumb = new ImageView(photoThumbLink);
            
            photoThumb.setFitHeight(160);
            photoThumb.setFitWidth(240);
            
            photoThumb.setPreserveRatio(true);
            photoThumb.setSmooth(true);
            photoThumb.setCache(true);
            
            Button photoButt = new Button();
            photoButt.setGraphic(photoThumb);
            photoButt.setStyle("-fx-background-color: white;");
            photoButt.setMinSize(240, 160);
            photoButt.setAlignment(Pos.CENTER);
            
            
            BorderPane bp = new BorderPane(null, photoButt, null, null, null);
            gridPaneDetailed.add(bp, j, i);
            
            j++;
            if(j==3){
                i++;
                j = 0;
                gridPaneDetailed.addRow(i);
            }
        }
    }
    
    public void saveAlbumOnHard() throws MalformedURLException, IOException{
        int i = 0;
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Выберите папку для сохранения альбома");
        String userDir = System.getProperty("user.home");
        File defaultDirectory = new File(userDir + "/Desktop");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(null);
        
        System.out.println(selectedDirectory);
        
        if(selectedDirectory != null){
            for(VKPhoto photo : photos){
                i++;
                URL photoUrl = new URL(photo.getLink_l());
                File file = new File(selectedDirectory.toString() + "\\" + photo.getId() + ".jpg");
                FileUtils.copyURLToFile(photoUrl, file);
            }
        }
    }
}
