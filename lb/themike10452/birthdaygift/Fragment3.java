package lb.themike10452.birthdaygift;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Fragment3 extends Fragment implements Identifier {

    private View view;
    private int speedFactor = 1;

    public Fragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment3_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        view.findViewById(R.id.e1_holder).setVisibility(View.INVISIBLE);
        animate();

        EditText editText = (EditText) view.findViewById(R.id.e1);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() == 1)
                    MainActivity.instance.findViewById(R.id.boss).post(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.instance.findViewById(R.id.boss).setBackground(getResources().getDrawable(R.drawable.background_paper));
                        }
                    });
                else if (charSequence.toString().equalsIgnoreCase(getString(R.string.the_number))) {
                    view.findViewById(R.id.e1).setEnabled(false);
                    proceed();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void animate() {

        AlphaAnimation animation1 = new AlphaAnimation(0, 1);
        animation1.setDuration(600 * speedFactor);

        ScaleAnimation animation2 = new ScaleAnimation(0, 1, 0, 1, 0, 0);
        animation2.setDuration(700 * speedFactor);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(animation1);
        set.addAnimation(animation2);
        set.setStartOffset(200 * speedFactor);

        final RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.qbox);

        rl.postOnAnimationDelayed(new Runnable() {
            @Override
            public void run() {
                int i = view.findViewById(R.id.q1).getMeasuredWidth();
                rl.setLayoutParams(new LinearLayout.LayoutParams(i, i));

                LinearLayout lay = (LinearLayout) view.findViewById(R.id.e1_holder);
                //lay.requestFocus();

                TranslateAnimation animation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_PARENT, 1,
                        Animation.RELATIVE_TO_SELF, 0);
                animation.setDuration(800 * speedFactor);
                animation.setStartOffset(300 * speedFactor);
                lay.setVisibility(View.VISIBLE);
                lay.startAnimation(animation);
            }
        }, 100 * speedFactor);

        rl.startAnimation(set);

    }

    private void proceed() {

        final LinearLayout lay = (LinearLayout) view.findViewById(R.id.e1_holder);

        final TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, -1);

        animation.setDuration(1000 * speedFactor);
        animation.setStartOffset(1000 * speedFactor);

        view.findViewById(R.id.qbox).postOnAnimationDelayed(new Runnable() {
            @Override
            public void run() {
                final TranslateAnimation animation2 = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_PARENT, -1);
                animation2.setDuration(800 * speedFactor);
                lay.postOnAnimationDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final TranslateAnimation animation3 = new TranslateAnimation(
                                Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_PARENT, 1
                        );
                        animation3.setDuration(800 * speedFactor);

                        view.findViewById(R.id.qbox).setVisibility(View.GONE);
                        lay.setVisibility(View.GONE);
                        view.findViewById(R.id.bottom).setVisibility(View.INVISIBLE);
                        view.findViewById(R.id.bottom).postOnAnimationDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.instance.notify(Fragment3.this);
                            }
                        }, 400 * speedFactor);
                        view.findViewById(R.id.bottom).startAnimation(animation3);
                    }
                }, 400 * speedFactor);
                lay.startAnimation(animation2);
            }
        }, 200 * speedFactor);

        view.findViewById(R.id.qbox).startAnimation(animation);

        //((EditText) view.findViewById(R.id.e2)).setTextColor(getResources().getColor(android.R.color.black));
        //view.findViewById(R.id.e2).setBackgroundColor(getResources().getColor(android.R.color.transparent));

    }

    @Override
    public int getID() {
        return 3;
    }
}
