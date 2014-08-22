package lb.themike10452.birthdaygift.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import lb.themike10452.birthdaygift.R;

/**
 * Created by Mike on 7/31/2014.
 */
public class ListViewAdapter extends ArrayAdapter<Entry> {

    public static ListViewAdapter instance;
    public ArrayList<View> buttons;
    public ArrayList<Entry> obj;
    private Context mContext;

    public ListViewAdapter(Context context, int resource, int textViewResourceId, ArrayList<Entry> objects) {
        super(context, resource, textViewResourceId, objects);
        obj = objects;
        mContext = context;
        instance = this;
        buttons = new ArrayList<View>();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        //super.getView(position, view, parent);
        View mView = view;
        if (mView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(R.layout.text_layout, null);
        }

        ((TextView) mView.findViewById(R.id.sample)).setText(obj.get(position).getText());
        ((TextView) mView.findViewById(R.id.name)).setText(obj.get(position).getName());
        ((TextView) mView.findViewById(R.id.sample)).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "quote.ttf"));
        ((TextView) mView.findViewById(R.id.leftQuote)).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "elephnt.ttf"));
        ((TextView) mView.findViewById(R.id.rightQuote)).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "elephnt.ttf"));

        if (obj.get(position).sound != null) {

            mView.findViewById(R.id.btn_action_play).setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        Drawable d = getContext().getResources().getDrawable(R.drawable.ic_action_play);
                        d.mutate().setAlpha(50);
                        view.setBackground(d);
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.setBackground(getContext().getResources().getDrawable(R.drawable.ic_action_play));
                    }
                    return false;
                }
            });

            final SeekBar seekBar = (SeekBar) mView.findViewById(R.id.soundSeekbar);

            mView.findViewById(R.id.btn_action_play).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    final MediaPlayer thisPlayer = obj.get(position).sound;

                    if (thisPlayer.isPlaying()) {
                        thisPlayer.pause();
                        view.setBackground(getContext().getResources().getDrawable(R.drawable.ic_action_play));
                    } else {

                        for (int i = 0; i < obj.size(); i++) {
                            MediaPlayer temp;
                            if ((temp = obj.get(i).sound) != null && temp.isPlaying()) {
                                temp.pause();
                                try {
                                    buttons.get(i).setBackground(getContext().getResources().getDrawable(R.drawable.ic_action_play));
                                } catch (Exception ignored) {
                                } catch (Error ignored) {
                                }
                            }
                        }

                        final BigPlayer bigPlayer = BigPlayer.getInstance();

                        if (bigPlayer != null && bigPlayer.isPlaying() && !bigPlayer.isFaded())
                            bigPlayer.fadeOut();

                        thisPlayer.start();
                        view.setBackground(getContext().getResources().getDrawable(R.drawable.ic_action_pause));

                        thisPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {

                                thisPlayer.pause();
                                view.setBackground(getContext().getResources().getDrawable(R.drawable.ic_action_play));

                                seekBar.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        seekBar.setProgress(0);
                                    }
                                }, 300);

                                if (bigPlayer != null)
                                    bigPlayer.fadeIn();

                            }
                        });

                        thisPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                            @Override
                            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                            }
                        });

                        final int duration = thisPlayer.getDuration();

                        seekBar.setMax(duration);

                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                if (b) {
                                    thisPlayer.seekTo(seekBar.getProgress());
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                //thisPlayer.seekTo(seekBar.getProgress());
                            }
                        });

                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {

                                while (thisPlayer.isPlaying()) {
                                    seekBar.setProgress(thisPlayer.getCurrentPosition());
                                }

                                view.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (bigPlayer != null) {
                                            boolean b = true;
                                            for (Entry anObj : obj) {
                                                MediaPlayer temp;
                                                if ((temp = anObj.sound) != null && temp.isPlaying()) {
                                                    b = false;
                                                    break;
                                                }
                                            }
                                            if (b)
                                                bigPlayer.fadeIn();
                                        }
                                    }
                                }, 300);

                                return null;
                            }
                        }.execute();
                    }
                }
            });

            mView.findViewById(R.id.btn_action_play).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    final MediaPlayer thisPlayer = obj.get(position).sound;

                    if (thisPlayer != null) {
                        thisPlayer.pause();
                        thisPlayer.seekTo(0);
                        seekBar.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                seekBar.setProgress(0);
                            }
                        }, 300);
                        return true;
                    }

                    view.setBackground(getContext().getResources().getDrawable(R.drawable.ic_action_play));

                    return false;
                }
            });

        } else {
            mView.findViewById(R.id.audioZone).setVisibility(View.GONE);
        }


        return mView;

    }

}
