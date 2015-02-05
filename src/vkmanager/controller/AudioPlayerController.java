/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vkmanager.controller;

import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import vkmanager.model.VKTrack;
import vkmanager.model.VKTrackPlayer;

/**
 * FXML Controller class
 *
 * @author i-mad_000
 */
public class AudioPlayerController implements Initializable{

    @FXML
    private GridPane musicList;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button but1;

    private static int rowsNumber = 0;
    private Image play;
    private Image pause;
    private VKTrackPlayer player;
    private ArrayList<Button> buttons;
    
    @FXML
    private void button1Pressing() throws MalformedURLException{
        
        ImageView playIm = new ImageView(play);
        playIm.setViewport(new Rectangle2D(0, 0, 16, 16));
        ImageView pauseIm = new ImageView(pause);
        pauseIm.setViewport(new Rectangle2D(0, 0, 16, 16));
<<<<<<< HEAD
=======
        
        
        Button toAdd = new Button("Thousand Foot Crutch - Move");
        VKTrack track = new VKTrack("Thousand Foot Crutch - Move", new URL("http://cs1-35v4.vk-cdn.net/p19/733d7811539325.mp3"), toAdd);
>>>>>>> origin/Photos
        track.setPause(pauseIm);
        track.setPlay(playIm);
        player.addTrack(track);
        track.pause();
        toAdd.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                player.invertStatus(track.getTrackIndex());
            }
            
        });
        toAdd.setStyle("-fx-background-color: White");
        musicList.addRow(rowsNumber++, toAdd);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        musicList.prefHeight(scroll.getPrefHeight());
        musicList.maxHeight(scroll.getMaxHeight());
        play = new Image(getClass().getResourceAsStream("/res/img/play.gif"));
        pause = new Image(getClass().getResourceAsStream("/res/img/pause.gif"));
        player = VKTrackPlayer.getInstance();
    }

}
