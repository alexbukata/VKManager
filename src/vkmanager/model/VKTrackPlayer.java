package vkmanager.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.*;

public class VKTrackPlayer{

    private static VKTrackPlayer instance = new VKTrackPlayer();
    private ArrayList<VKTrack> tracks;
    private MediaPlayer player;
    private static VKTrack last;
    
    private VKTrackPlayer(){
        tracks = new ArrayList<>();
    }

    public static VKTrackPlayer getInstance(){
        return instance;
    }

    public ArrayList<VKTrack> getTracks(){
        return tracks;
    }
    
    
    
    public void invertStatus(int index){
        if (last != null){
            last.pause();            
        }
        VKTrack currTrack = tracks.get(index);
        currTrack.invertStatus();
        last = currTrack;
    }
    
    public void addTrack(VKTrack track){
        if (track != null) {
            tracks.add(track);
        } else {
            throw new IllegalArgumentException();
        }
    }

}
