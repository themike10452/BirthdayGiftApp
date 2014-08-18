package lb.themike10452.birthdaygift;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Mike on 8/11/2014.
 */
public class Fragment5 extends Fragment implements Identifier {

    public View view;
    private int speedFactor = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment5_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                int i = view.findViewById(R.id.q1).getMeasuredWidth();
                view.findViewById(R.id.qbox).setLayoutParams(new LinearLayout.LayoutParams(i, i));
                //view.findViewById(R.id.e1_holder).setLayoutParams(new LinearLayout.LayoutParams(i, -1));
            }
        }, 1);
        ((EditText) view.findViewById(R.id.e1)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.toString().equalsIgnoreCase("green")) {
                    view.findViewById(R.id.e1).setEnabled(false);
                    proceed();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void proceed() {

        final LinearLayout lay = (LinearLayout) view.findViewById(R.id.e1_holder);

        final TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, -1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);

        animation.setDuration(800 * speedFactor);
        animation.setStartOffset(500 * speedFactor);

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
                                MainActivity.instance.notify(Fragment5.this);
                            }
                        }, 500 * speedFactor);
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
        return 5;
    }
}
