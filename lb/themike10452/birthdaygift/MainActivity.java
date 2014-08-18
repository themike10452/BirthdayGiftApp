package lb.themike10452.birthdaygift;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lb.themike10452.birthdaygift.Adapters.BigPlayer;


public class MainActivity extends Activity {

    public static MainActivity instance;
    public static int index = 1;
    private static BigPlayer player;
    private static StringBuffer buffer1;
    private static boolean showPlayMusic = false;

    protected static void showInfo(Context c, View v, int i) {
        String info = "";
        switch (i) {
            case 0:
                info = String.format(c.getString(R.string.msg_1), c.getString(R.string.the_name));
                break;
        }
        final TextView textView = (TextView) v.findViewById(R.id.inf);
        textView.setText(info);
        AnimationSet set = new AnimationSet(false);
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1200);
        set.addAnimation(animation);
        textView.setAnimation(set);
        textView.setVisibility(View.VISIBLE);
        textView.animate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = this;
        Fragment fragment;
        switch (index) {
            default:
                fragment = new Fragment1();
                break;
            case 2:
                fragment = new Fragment2();
                break;
            case 3:
                fragment = new Fragment3();
                break;
            case 4:
                fragment = new Fragment4();
                break;
            case 5:
                fragment = new Fragment5();
                break;
            case 6:
                fragment = new Fragment6();
                break;
            case 7:
                fragment = new Fragment7();
                break;
            case 8:
                fragment = new Fragment8();
        }
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.boss, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (buffer1 == null)
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    buffer1 = new StringBuffer();
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("AN.txt")));
                        String s;
                        while ((s = reader.readLine()) != null) {
                            try {
                                s = s.replace("\\n", "\n");
                            } catch (Exception ignored) {
                            } finally {
                                buffer1.append(s);
                            }
                        }
                        reader.close();

                    } catch (Exception e) {

                    } finally {
                        if (reader != null)
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                    return null;
                }
            }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu aMenu) {
        getMenuInflater().inflate(R.menu.main, aMenu);
        return showPlayMusic;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_playMusic) {
            try {
                if (player == null) {
                    player = new BigPlayer(this, R.raw.hpb);
                    player.start();
                    item.setIcon(getResources().getDrawable(R.drawable.ic_action_play_light));
                } else {
                    if (player.isPlaying()) {
                        player.pause();
                        item.setIcon(getResources().getDrawable(R.drawable.ic_action_play_light));
                    } else {
                        player.start();
                        item.setIcon(getResources().getDrawable(R.drawable.ic_action_pause_light));
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void notify(Identifier fragment) {
        onNotified(fragment);
    }

    private void onNotified(Identifier fragment) {
        FragmentManager manager = getFragmentManager();
        switch (fragment.getID()) {
            case 1:
                index = 2;
                //findViewById(R.id.boss).setBackground(getResources().getDrawable(R.drawable.background_butterfly));
                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.boss, new Fragment2())
                        .commit();
                break;
            case 2:
                index = 3;
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.boss, new Fragment3())
                        .commit();
                break;
            case 3:
                index = 4;
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
                        .replace(R.id.boss, new Fragment4())
                        .commit();
                break;
            case 4:
                index = 5;
                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.boss, new Fragment5())
                        .commit();
                break;
            case 5:
                index = 6;
                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.boss, new Fragment6())
                        .commit();
                break;
            case 6:
                index = 7;
                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.boss, new Fragment7())
                        .commit();
                break;
            case 7:
                index = 8;
                showPlayMusic = true;
                invalidateOptionsMenu();
                try {
                    if (player == null) {
                        player = new BigPlayer(this, R.raw.hpb);
                        player.start();
                    } else {
                        if (!player.isPlaying())
                            player.start();
                    }
                } catch (Exception ignored) {
                }
                manager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.boss, new Fragment8())
                        .commit();
        }
    }

    @Override
    public void onBackPressed() {
        /*if (index > 1) {
            index--;
            onStart();
        }*/
    }

}
