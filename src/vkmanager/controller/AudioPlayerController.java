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
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import vkmanager.model.User;
import vkmanager.model.VKApi;
import vkmanager.model.music.VKTrack;
import vkmanager.model.music.VKTrackPlacer;
import vkmanager.model.music.VKTrackPlacer1;
import vkmanager.model.music.VKTrackPlayer;

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
    @FXML
    private ProgressBar bar1;

    private static int rowsNumber = 0;
    private Image play;
    private Image pause;
    private VKTrackPlayer player;
    private VKTrackPlacer1 placer;
    private ArrayList<Button> buttons;
    private VKApi vkapi;

    @FXML
    private void button1Pressing() throws MalformedURLException{

        ArrayList<VKTrack> tracks = vkapi.getAllUserMusic();
        player.addAllMusic(tracks);
        System.out.println(tracks);
        for (VKTrack track : tracks) {
            //System.out.println(track.getTrackIndex());
            ImageView playIm = new ImageView(play);
            ImageView pauseIm = new ImageView(pause);
            track.setPause(pauseIm);
            track.setPlay(playIm);
            placer.placeAll();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        musicList.prefHeight(scroll.getPrefHeight());
        musicList.maxHeight(scroll.getMaxHeight());
        play = new Image(getClass().getResourceAsStream("/res/img/play.gif"));
        pause = new Image(getClass().getResourceAsStream("/res/img/pause.gif"));
        player = VKTrackPlayer.getInstance();
        placer = VKTrackPlacer1.getInstance();
        placer.setContainer(musicList);
        User user = User.createUser(138367346, "Alexander", "Bukata", "1", "74f3e89cfa870d8228c6ce4ddd8fdd4760a5d5fc16eee2e7f2034b71395914df54fdbe5c47bb670b181a5");
        vkapi = new VKApi(user);

    }

}
