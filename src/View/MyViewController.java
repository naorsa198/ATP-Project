package View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MyViewController implements IView {

    protected MediaPlayer mediaPlayer;
    protected Media sound;

    public MyViewController(){
        mediaPlayer=null;
        sound=null;
    }

    protected MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    protected void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    protected Media getSound() {
        return sound;
    }

    protected void setSound(Media sound) {
        this.sound = sound;
    }

    protected void setNewMusic(String filename) {

        String location = "resources/music/" +filename +".mp3";
        sound = new Media(new File(location).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);

    }

    protected void setNewMusicWithIndex(String filename, int index) {

        String location = "resources/music/" +filename + index + ".mp3";
        sound = new Media(new File(location).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);

    }

    protected void playMusic() {
        mediaPlayer.play();
    }

    protected void stopMusic() {
        mediaPlayer.stop();
    }


    protected boolean isPlayingMusic() {
        boolean playing = false;
        if (mediaPlayer!=null) {
             playing = mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);

        }
        return playing;

    }



    @Override
    public void exitCorrectly() {
        //nothing
    }
}
