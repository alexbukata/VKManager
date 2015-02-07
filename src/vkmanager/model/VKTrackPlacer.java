package vkmanager.model;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class VKTrackPlacer{

    private static int rowsNumber = 1;
    private VKTrack track;
    private VKTrackPlayer player;
    private Button playPause;
    private ProgressBar time;
    private boolean isTimeVisible;
    private Slider volume;
    private Label name;
    private GridPane container;
    private AnimationTimer progressTimer

    public VKTrackPlacer(VKTrack track, GridPane pane){
        this.track = track;
        this.container = pane;
        name = new Label(track.getName());
        playPause = new Button();

        isTimeVisible = false;
        player = VKTrackPlayer.getInstance();
        System.out.println(player.getTracks());
    }

    public void place(){
        playPause.setGraphic(track.getPlay());
        playPause.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                player.invertStatus(track.getTrackIndex());
                invertTimeProgress();
            }
        });
        track.setTrackBut(playPause);
        playPause.setStyle("-fx-background-color: white");
        time = new ProgressBar();

        time.setPrefSize(333, 11);
        time.setMinHeight(11);

        //BorderPane playPane = new BorderPane(null, playPause, null, null, null);
        //playPane.setPrefSize(26, 30);
        ToolBar trackBar = new ToolBar(playPause, name);
        trackBar.setStyle("-fx-background-color: white");
        container.add(trackBar, 0, rowsNumber++);
    }

    public void updateProgress(double value){
        time.setProgress(value);
    }

    public void invertTimeProgress(){
        BorderPane pane = new BorderPane(null, null, null, time, name);
        ToolBar trackBar = null;
        Node node = new Button();
        if (isTimeVisible) {
            trackBar = new ToolBar(playPause, name);
        } else {
            trackBar = new ToolBar(playPause, pane);
        }
        trackBar.setStyle("-fx-background-color: white");
        isTimeVisible = !isTimeVisible;
        container.add(trackBar, 0, track.getTrackIndex()+1);
        System.out.println(track.getTrackIndex()+2);
    }
}