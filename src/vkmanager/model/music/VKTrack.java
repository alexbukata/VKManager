package vkmanager.model.music;

import java.net.URL;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class VKTrack{

    private static int index = 0;
    
    private int trackIndex; 
    private String name;
    private boolean playing;
    private ImageView play;
    private ImageView pause;
    private URL trackUrl;
    private MediaPlayer player;
    private Button trackBut;
    private VKTrackPlacer placer;
    

    public VKTrack(String name, URL trackUrl, Button trackBut, VKTrackPlacer placer){
        trackIndex = index++;
        this.name = name;
        this.trackBut = trackBut;
        this.playing = false;
        this.trackUrl = trackUrl;
        this.placer=placer;
        Media mp3 = new Media(trackUrl.toString());
        player = new MediaPlayer(mp3);
    }
    
    public VKTrack(String name, URL trackUrl){
        trackIndex = index++;
        this.name = name;
        this.playing = false;
        this.trackUrl = trackUrl;
        Media mp3 = new Media(trackUrl.toString());
        player = new MediaPlayer(mp3);
    }

    public ImageView getPlay(){
        return play;
    }

    public URL getTrackUrl(){
        return trackUrl;
    }

    public void setTrackUrl(URL trackUrl){
        this.trackUrl = trackUrl;
    }

    public void setPlay(ImageView play){
        this.play = play;
    }

    public ImageView getPause(){
        return pause;
    }

    public void setPause(ImageView pause){
        this.pause = pause;
    }

    public boolean isPlaying(){
        return playing;
    }

    public void invertStatus(){
        if (playing){
            pause();
        } else {
            play();
        }
    }

    public void play(){
        player.play();
        playing = true;
        trackBut.setGraphic(pause);
    }
    
    public void pause(){
        player.pause();
        playing = false;
        trackBut.setGraphic(play);
    }
    
    public void stop(){
        player.stop();
        playing = false;
        trackBut.setGraphic(play);
    }
    
    public int getTrackIndex(){
        return trackIndex;
    }

    public String getName(){
        return name;
    }

    public void setTrackBut(Button trackBut){
        this.trackBut = trackBut;
    }

    public MediaPlayer getPlayer(){
        return player;
    }

    public Button getTrackBut(){
        return trackBut;
    }

    public void setPlacer(VKTrackPlacer placer){
        this.placer = placer;
    }

    public VKTrackPlacer getPlacer(){
        return placer;
    }
    
    
}
