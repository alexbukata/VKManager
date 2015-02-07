package vkmanager.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.*;

public class VKTrackPlayer{

    private static VKTrackPlayer instance;
    private ArrayList<VKTrack> tracks;
    private MediaPlayer player;
    private static VKTrack last;
    private Iterator<VKTrack> iterator;
    private VKTrackPlayer(){
        tracks = new ArrayList<>();
        iterator = tracks.iterator();
    }

    public static synchronized VKTrackPlayer getInstance(){
        if (instance == null){
            instance = new VKTrackPlayer();
        }
        return instance;
    }

    public ArrayList<VKTrack> getTracks(){
        return tracks;
    }
    
    
    
    public void invertStatus(int index){
        
        VKTrack currTrack = tracks.get(index);
        if (last != null && last.getTrackIndex() != currTrack.getTrackIndex()){
            last.stop();
        }
        currTrack.invertStatus();
        last = currTrack;
    }
    
    public VKTrack getTrack(int index){
        return tracks.get(index);
    }
    
    public void addTrack(VKTrack track){
        if (track != null) {
            tracks.add(track);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addAllMusic(ArrayList<VKTrack> tracks){
        this.tracks.addAll(tracks);
    }

    public MediaPlayer getPlayer(){
        return player;
    }

    public Iterator<VKTrack> getIterator(){
        return tracks.iterator();
    }
    
}
