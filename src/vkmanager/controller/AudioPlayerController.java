/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vkmanager.controller;

import java.awt.event.MouseEvent;
import java.net.URL;
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

    @FXML
    private void button1Pressing(){
        System.out.println(musicList.getHeight());
        VKTrack track = new VKTrack("Thousand Foot Crutch - Move", 180);
        ImageView playIm = new ImageView(play);
        playIm.setViewport(new Rectangle2D(0, 0, 16, 16));
        ImageView pauseIm = new ImageView(pause);
        pauseIm.setViewport(new Rectangle2D(0, 0, 16, 16));
        track.setPause(pauseIm);
        track.setPlay(playIm);
        Button toAdd = new Button("Thousand Foot Crutch - Move");
        toAdd.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                track.invertStatus();
                reloadTrackButton(track, toAdd);
            }
            
        });
        toAdd.setStyle("-fx-background-color: White");
        musicList.addRow(rowsNumber++, toAdd);
        reloadTrackButton(track, toAdd);
    }
    
    public void reloadTrackButton(VKTrack track, Button trackBut){    
        System.out.println(trackBut.getWidth() + " " + trackBut.getHeight());        
        trackBut.setGraphic(track.isPlaying() ? track.getPause() : track.getPlay());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        musicList.prefHeight(scroll.getPrefHeight());
        musicList.maxHeight(scroll.getMaxHeight());
        Image playIm = new Image(getClass().getResourceAsStream("/res/img/play.gif"));
        Image pauseIm = new Image(getClass().getResourceAsStream("/res/img/pause.gif"));
        
    }

}
