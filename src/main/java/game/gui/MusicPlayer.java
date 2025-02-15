package game.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MusicPlayer {

    public MusicPlayer() {
        // Load the audio file
        String filePath = "src/main/resources/Songs/GameSong.mp3";
        Media media = new Media(new File(filePath).toURI().toString());

        // Create the MediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Set cycle count to indefinitely to make it repeat
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Play the audio
        mediaPlayer.play();

        // set volume
        mediaPlayer.setVolume(0.4);
    }
}
