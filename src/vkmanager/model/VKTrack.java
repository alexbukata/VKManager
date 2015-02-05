package vkmanager.model;

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
    private long lengthSec;
    private ImageView play;
    private ImageView pause;
    private URL trackUrl;
    private MediaPlayer player;
    private Button trackBut;
    

    public VKTrack(String name, URL trackUrl, Button trackBut){
        trackIndex = index++;
        this.name = name;
        this.trackBut = trackBut;
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
        playing = !playing;
    }

    public void play(){
        player.play();
        trackBut.setGraphic(pause);
    }
    
    public void pause(){
        player.pause();
        trackBut.setGraphic(play);
    }
    
    public void stop(){
        player.stop();
        trackBut.setGraphic(play);
    }
    
    public int getTrackIndex(){
        return trackIndex;
    }

}
