package vkmanager.model.music;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class VKTrackPlacer1{

    private static VKTrackPlacer1 instance;
    private ArrayList<VKTrack> tracks;
    private VKTrackPlayer player;
    private ArrayList<Button> playPause;
    private ArrayList<ProgressBar> time;
    private ArrayList<Label> name;
    private GridPane container;
    private TrackTimer progressTimer;

    private VKTrackPlacer1(){
        tracks = new ArrayList<VKTrack>();
        playPause = new ArrayList<Button>();
        name = new ArrayList<Label>();
        time = new ArrayList<ProgressBar>();
        progressTimer = new TrackTimer();
        player = VKTrackPlayer.getInstance();
        tracks = player.getTracks();
    }

    public static synchronized VKTrackPlacer1 getInstance(){
        if (instance == null) {
            instance = new VKTrackPlacer1();
        }
        return instance;
    }

    public void placeAll(){
        // tracks = player.getTracks();
        for (VKTrack track : player.getTracks()) {
            Button currPlayPause = new Button();
            currPlayPause.setGraphic(track.getPlay());
            currPlayPause.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    player.invertStatus(track.getTrackIndex());
                    invertTimeProgress(track.getTrackIndex());
                    progressTimer.initialize(track);

                    progressTimer.start();
                }
            });
            currPlayPause.setStyle("-fx-background-color: white");
            track.setTrackBut(currPlayPause);
            playPause.add(currPlayPause);

            Label currName = new Label(track.getName());
            currName.setOnMouseReleased(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event){
                    player.invertStatus(track.getTrackIndex());
                    invertTimeProgress(track.getTrackIndex());
                    progressTimer.initialize(track);

                    progressTimer.start();
                }
            });
            name.add(currName);

            ProgressBar currTime = new ProgressBar();
            currTime.setPrefSize(333, 11);
            currTime.setMinHeight(11);
            time.add(currTime);

            ToolBar currTrackBar = new ToolBar(currPlayPause, currName);
            currTrackBar.setStyle("-fx-background-color: white");

            container.add(currTrackBar, 0, track.getTrackIndex() + 1);

        }
    }

    public void updateProgress(int index, double value){
        time.get(index).setProgress(value);
    }

    public void invertTimeProgress(int index){
        BorderPane pane = new BorderPane(null, null, null, time.get(index), name.get(index));
        ToolBar trackBar = null;
        if (!tracks.get(index).isPlaying()) {
            trackBar = new ToolBar(playPause.get(index), name.get(index));
        } else {
            trackBar = new ToolBar(playPause.get(index), pane);
        }
        trackBar.setStyle("-fx-background-color: white");
        container.add(trackBar, 0, index + 1);
        System.out.println(index + 2);
    }

    public void setContainer(GridPane container){
        this.container = container;
    }

}
