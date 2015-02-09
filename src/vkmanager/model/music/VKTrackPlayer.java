package vkmanager.model.music;

import java.util.ArrayList;
import javafx.scene.media.MediaPlayer;
import vkmanager.model.music.VKTrack;

public class VKTrackPlayer{

    private static VKTrackPlayer instance;

    private MediaPlayer player;
    private ArrayList<VKTrack> tracks;
    private VKTrack lastTrack;

    private VKTrackPlayer(){
        tracks = new ArrayList<>();
    }

    public static VKTrackPlayer getInstance(){
        if (instance == null) {
            instance = new VKTrackPlayer();
        }
        return instance;
    }

    public void add(VKTrack track){
        tracks.add(track);
    }

    public void playOrStop(int trackIndex){
        VKTrack currTrack = tracks.get(trackIndex);
        if (player != null) {
            player.stop();
            if (lastTrack.getTrackIndex() != currTrack.getTrackIndex()) {
                lastTrack.setPlaying(false);
                lastTrack.getPlacer().repaintTrack();
            }
        }

        if (currTrack.isPlaying()) {
            player.stop();
            currTrack.setPlaying(false);

        } else {
            currTrack.setPlaying(true);
            player = new MediaPlayer(currTrack.getMp3());
            player.play();
        }
        lastTrack = currTrack;
    }

    public MediaPlayer getPlayer(){
        return player;
    }

    public VKTrack getNext(int index){
        return tracks.get(index+1);
    }
    
    void playNext(int trackIndex){
        playOrStop(trackIndex+1);
    }
}
