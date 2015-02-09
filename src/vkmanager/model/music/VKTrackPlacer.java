package vkmanager.model.music;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class VKTrackPlacer{

    private final VKTrack track;
    private Button trackBut;
    private Label name;
    private ProgressBar progress;
    private VKTrackPlayer player;
    private final ImageView play;
    private final ImageView pause;
    private static GridPane musicList;

    private static Button globalPlay;
    private static Label globalName;
    private static ProgressBar globalProgress;

    public VKTrackPlacer(VKTrack track){
        this.track = track;
        player = VKTrackPlayer.getInstance();
        play = new ImageView(new Image("/res/img/play.gif"));
        pause = new ImageView(new Image("/res/img/pause.gif"));
    }

    public static void setMusicList(GridPane musicList){
        VKTrackPlacer.musicList = musicList;
    }

    public void place(){
        trackBut = new Button();
        trackBut.setStyle("-fx-background-color: transparent");
        trackBut.setGraphic(play);

        progress = new ProgressBar();
        progress.setPrefSize(333, 11);
        progress.setMinHeight(11);
        TrackTimer progressTimer = new TrackTimer(track.getPlacer());

        trackBut.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event){
                progressTimer.setTrack(track);
                player.playOrStop(track.getTrackIndex());
                repaintTrack();
                progressTimer.start();
            }
        });

        name = new Label(track.getName());
        /*name.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event){
                progressTimer.setTrack(track);
                player.playOrStop(track.getTrackIndex());
                repaintTrack();
                progressTimer.start();
            }
        */

        //globalPlay.setOnAction(trackBut.getOnAction());

        ToolBar trackBar = new ToolBar(trackBut, name);
        trackBar.setStyle("-fx-background-color: transparent");

        musicList.addRow(track.getTrackIndex(), trackBar);
    }

    public void repaintTrack(){
        globalName.setText(name.getText());
        globalPlay.setGraphic(track.isPlaying() ? new ImageView(new Image("/res/img/pause.gif")) : new ImageView(new Image("/res/img/play.gif")));
        globalProgress.progressProperty().bind(progress.progressProperty());

        BorderPane pane = new BorderPane(null, null, null, progress, name);
        ToolBar trackBar;
        if (track.isPlaying()) {
            trackBar = new ToolBar(trackBut, pane);
            trackBut.setGraphic(pause);
        } else {
            trackBar = new ToolBar(trackBut, name);
            trackBut.setGraphic(play);
        }
        trackBar.setStyle("-fx-background-color: transparent");
        musicList.add(trackBar, 0, track.getTrackIndex());
    }

    public VKTrack getTrack(){
        return track;
    }

    public ProgressBar getProgress(){
        return progress;
    }

    public VKTrackPlacer getNextPlacer(){
        return player.getNext(track.getTrackIndex()).getPlacer();
    }

    public static void setGlobalComponents(Button globalPlay, Label globalName, ProgressBar globalProgress){
        VKTrackPlacer.globalName = globalName;
        VKTrackPlacer.globalPlay = globalPlay;
        VKTrackPlacer.globalProgress = globalProgress;
    }

}
