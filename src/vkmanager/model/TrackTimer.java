package vkmanager.model;

import javafx.animation.AnimationTimer;

public class TrackTimer extends AnimationTimer{
    private VKTrack track;
    private VKTrackPlacer trackPlacer;
    private VKTrackPlayer trackPlayer;

    public TrackTimer(VKTrack track, VKTrackPlacer trackPlacer, VKTrackPlayer trackPlayer){
        this.track = track;
        this.trackPlacer = trackPlacer;
        this.trackPlayer = trackPlayer;
    }

    
    public VKTrack getTrack(){
        return track;
    }

    public void setTrack(VKTrack track){
        this.track = track;
    }

    public VKTrackPlacer getTrackPlacer(){
        return trackPlacer;
    }

    public void setTrackPlacer(VKTrackPlacer trackPlacer){
        this.trackPlacer = trackPlacer;
    }

    public VKTrackPlayer getTrackPlayer(){
        return trackPlayer;
    }

    public void setTrackPlayer(VKTrackPlayer trackPlayer){
        this.trackPlayer = trackPlayer;
    }

    
    
    @Override
    public void handle(long now){
        double length = track.getPlayer().getTotalDuration().toSeconds();
        double currTime = track.getPlayer().getCurrentTime().toSeconds();
        double progress = currTime / length;
        /*if (1 - progress > 0.05){
            progress = 0;
            track = trackPlayer.getTrack(track.getTrackIndex() + 1);
            trackPlayer.invertStatus(track.getTrackIndex() + 1);
        }*/
        trackPlacer.updateProgress(progress);
    }
    
    
}
