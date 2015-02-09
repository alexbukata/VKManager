package vkmanager.model.music;

import javafx.animation.AnimationTimer;
import javafx.scene.control.ProgressBar;

public class TrackTimer extends AnimationTimer{
 
    private ProgressBar progress;
    private VKTrackPlacer placer;
    private VKTrackPlayer player;

    public TrackTimer(VKTrackPlacer placer){
        this.placer = placer;
        progress = placer.getProgress();
        player = VKTrackPlayer.getInstance();
    }

    public void setTrack(VKTrack track){
        placer = track.getPlacer();
        progress = placer.getProgress();
    }

    @Override
    public void handle(long now){
        double duration = player.getPlayer().getTotalDuration().toSeconds();
        double currTime = player.getPlayer().getCurrentTime().toSeconds();

        double progress = currTime / duration;
        if (1 - progress < 0.01) {
            player.playOrStop(placer.getTrack().getTrackIndex());
            placer.repaintTrack();
            player.playOrStop(placer.getTrack().getTrackIndex() + 1);
            placer.getNextPlacer().repaintTrack();
            placer = placer.getNextPlacer();
            this.progress = placer.getProgress();

        }
        this.progress.setProgress(progress);
    }

}
