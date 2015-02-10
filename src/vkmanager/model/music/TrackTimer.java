package vkmanager.model.music;

import javafx.animation.AnimationTimer;

public class TrackTimer extends AnimationTimer{
    
    private VKTrack track;
    private VKTrackPlacer1 trackPlacer;
    private VKTrackPlayer trackPlayer;
    
    public TrackTimer(){
    }
    
    public VKTrack getTrack(){
        return track;
    }
    
    public void initialize(VKTrack track){
        trackPlacer = VKTrackPlacer1.getInstance();
        trackPlayer = VKTrackPlayer.getInstance();
        
        this.track = track;
    }
    
    @Override
    public void handle(long now){
        double length = track.getPlayer().getTotalDuration().toSeconds();
        double currTime = track.getPlayer().getCurrentTime().toSeconds();
        double progress = currTime / length;
        if (1 - progress < 0.001) {
            progress = 0;
            trackPlayer.invertStatus(track.getTrackIndex() + 1);
            trackPlacer.invertTimeProgress(track.getTrackIndex() + 1);
            initialize(trackPlayer.getTrack(track.getTrackIndex() + 1));
        }
        trackPlacer.updateProgress(track.getTrackIndex(), progress);
    }
    
}
