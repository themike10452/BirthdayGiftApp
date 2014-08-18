package lb.themike10452.birthdaygift.Adapters;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Mike on 8/13/2014.
 */
public class BigPlayer {

    private static BigPlayer instance;
    private static MediaPlayer player;
    private boolean isFaded;

    public BigPlayer(Context context, int resId) {
        player = MediaPlayer.create(context, resId);
        instance = this;
        isFaded = false;
    }

    public static BigPlayer getInstance() {
        return instance;
    }

    public void fadeOut() {
        float volume = 0.1f;
        player.setVolume(volume, volume);
        isFaded = true;
    }

    public void fadeIn() {
        float volume = 1.0f;
        player.setVolume(volume, volume);
        isFaded = false;
    }

    public void start() {
        player.start();
    }

    public void pause() {
        player.pause();
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public boolean isFaded() {
        return isFaded;
    }

}
