/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vkmanager.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import vkmanager.model.User;
import vkmanager.model.VKApi;
import vkmanager.model.music.VKTrackPlayer;
import vkmanager.model.music.VKTrack;

/**
 * FXML Controller class
 *
 * @author i-mad_000
 */
public class AudioPlayerController implements Initializable{

    @FXML
    private Button but1;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane musicList;

    private VKTrackPlayer player;
    private VKApi vkapi;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        musicList.prefHeight(scroll.getHeight());
        musicList.maxHeight(scroll.getHeight());
        player = VKTrackPlayer.getInstance();
        vkapi = new VKApi(User.getCurrentUser());
        ArrayList<VKTrack> tracks = vkapi.getAllUserMusic();
        for (VKTrack track : tracks) {
            player.add(track);
            track.getPlacer().setMusicList(musicList);
            track.place();
        }
    }
    
    @FXML
    private void button1Pressing(ActionEvent event){
    }

}
