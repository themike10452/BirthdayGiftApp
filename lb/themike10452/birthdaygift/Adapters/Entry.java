package lb.themike10452.birthdaygift.Adapters;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Mike on 7/31/2014.
 */
public class Entry {
    public MediaPlayer sound;
    private String text, name;
    private int audioResId = -1;

    public Entry(String Text, String Name) {
        name = Name;
        text = Text;
    }

    public Entry(Context context, String Text, String Name, int soundResId) {
        name = Name;
        text = Text;
        sound = MediaPlayer.create(context, soundResId);
    }

    public void setSound(Context context, int resId) {
        sound = MediaPlayer.create(context, resId);
        audioResId = resId;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
