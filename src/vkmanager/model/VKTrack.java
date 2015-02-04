package vkmanager.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class VKTrack{
    
    private String name;
    private boolean playing;    
    private long lengthSec;
    private ImageView play;
    private ImageView pause;

    public ImageView getPlay(){
        return play;
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
    
    public VKTrack(String name, long lengthSec){
        this.name = name;
        this.playing = false;
        this.lengthSec = lengthSec;
    }

    public boolean isPlaying(){
        return playing;
    }
    
    public void invertStatus(){
        playing = !playing;
    }

   
    
}
