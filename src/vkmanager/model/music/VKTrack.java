package vkmanager.model.music;

import java.net.URL;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;

public class VKTrack{

    private static int index = 0;
    
    private URL trackURL;
    private String name;
    private Media mp3;
    private VKTrackPlayer player;
    private VKTrackPlacer placer;
    private int trackIndex;
    private boolean playing;

    public VKTrack(String name,URL trackURL){
        trackIndex = index++;
        this.trackURL = trackURL;
        this.name = name;
        mp3 = new Media(trackURL.toString());
        player = VKTrackPlayer.getInstance();
        placer = new VKTrackPlacer(this);
    }

    public String getName(){
        return name;
    }

    public void setPlaying(boolean playing){
        this.playing = playing;
    }

    public void place(){
        placer.place();
    }    

    public int getTrackIndex(){
        return trackIndex;
    }

    public boolean isPlaying(){
        return playing;
    }

    public Media getMp3(){
        return mp3;
    }

    public VKTrackPlacer getPlacer(){
        return placer;
    }
    
    
}
