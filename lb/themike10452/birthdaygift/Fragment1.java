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

/**
 * Created by Mike on 7/10/2014.
 */

public class Fragment1 extends Fragment implements Identifier {

    private View view;
    private AnimationSet animationSet_IN, animationSet_OUT;
    private int speedFactor = 1;

    private Runnable ANIMATION_2 = new Runnable() {
        @Override
        public void run() {
            AlphaAnimation animation = new AlphaAnimation(0, 1);
            animation.setDuration(1000 * speedFactor);
            view.findViewById(R.id.e1_holder).setVisibility(View.VISIBLE);
            view.findViewById(R.id.e1_holder).startAnimation(animation);
        }
    };
    private Runnable ANIMATION_1 = new Runnable() {
        @Override
        public void run() {
            int i = view.findViewById(R.id.q1).getMeasuredWidth();
            view.findViewById(R.id.qbox).setLayoutParams(new LinearLayout.LayoutParams(i, i));
            view.findViewById(R.id.e1_holder).setLayoutParams(new LinearLayout.LayoutParams(i, -1));

            view.findViewById(R.id.master).setVisibility(View.VISIBLE);
            view.findViewById(R.id.master).startAnimation(animationSet_IN);
            view.findViewById(R.id.master).postOnAnimationDelayed(ANIMATION_2, 1000 * speedFactor);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment1_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        animate();
        EditText editText = (EditText) view.findViewById(R.id.e1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.toString().equalsIgnoreCase(getString(R.string.the_name)))
                    proceed();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void animate() {

        view.findViewById(R.id.master).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.e1_holder).setVisibility(View.INVISIBLE);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF, 0
        );
        translateAnimation.setDuration(500 * speedFactor);

        animationSet_IN = new AnimationSet(true);
        animationSet_IN.addAnimation(translateAnimation);


        view.findViewById(R.id.bottom).startAnimation(animationSet_IN);
        view.findViewById(R.id.master).postOnAnimationDelayed(ANIMATION_1, 100 * speedFactor);

    }

    private void proceed() {

        view.findViewById(R.id.e1).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0);
        scaleAnimation.setDuration(700 * speedFactor);

        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(600 * speedFactor);

        animationSet_OUT = new AnimationSet(false);
        animationSet_OUT.addAnimation(scaleAnimation);
        animationSet_OUT.addAnimation(animation);

        view.findViewById(R.id.e1).setEnabled(false);
        view.findViewById(R.id.qbox).setAnimation(animationSet_OUT);
        view.findViewById(R.id.qbox).postOnAnimation(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_PARENT, -1, Animation.RELATIVE_TO_SELF, 0
                );
                translateAnimation.setDuration(800 * speedFactor);

                AlphaAnimation animation = new AlphaAnimation(0, 1);
                animation.setDuration(300 * speedFactor);

                animationSet_OUT = new AnimationSet(false);
                animationSet_OUT.addAnimation(translateAnimation);
                animationSet_OUT.addAnimation(animation);
                view.findViewById(R.id.qbox).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.proceed).setAnimation(animationSet_OUT);
                view.findViewById(R.id.proceed).setVisibility(View.VISIBLE);
                view.findViewById(R.id.proceed).animate();
                MainActivity.showInfo(getActivity(), view, 0);
            }
        });
        view.findViewById(R.id.qbox).animate();
        view.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.notify(Fragment1.this);
            }
        });

    }

    @Override
    public int getID() {
        return 1;
    }
}
