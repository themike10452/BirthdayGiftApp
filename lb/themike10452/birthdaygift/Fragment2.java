package lb.themike10452.birthdaygift;


import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import lb.themike10452.birthdaygift.Stacks.IntegerStack;


public class Fragment2 extends Fragment implements Identifier {

    public View view;
    private int speedFactor = 1;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment2_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    AssetManager manager = getResources().getAssets();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(manager.open("VN.txt")));
                    StringBuffer text = new StringBuffer();
                    String tmp;
                    while ((tmp = reader.readLine()) != null) {
                        try {
                            tmp = tmp.replace("\\n", "\n");
                        } finally {
                            text.append(tmp);
                        }

                    }

                    new AsyncTask<StringBuffer, Void, Void>() {
                        SpannableString string;

                        @Override
                        protected Void doInBackground(StringBuffer... strings) {
                            string = proc(strings[0].toString());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            update(string);
                        }
                    }.execute(text);


                } catch (Exception e) {

                }
            }
        };

        view.findViewById(R.id.textView).post(runnable);


        view.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.notify(Fragment2.this);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        view.findViewById(R.id.bottom_btn).setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF, 0
        );
        animation.setStartOffset(1000 * speedFactor);
        animation.setDuration(800 * speedFactor);
        AlphaAnimation animation1 = new AlphaAnimation(0, 1);
        animation1.setDuration(1500 * speedFactor);
        animation1.setStartOffset(500);
        view.findViewById(R.id.textView).startAnimation(animation1);
        view.findViewById(R.id.bottom_btn).startAnimation(animation);
    }

    @Override
    public int getID() {
        return 2;
    }

    private SpannableString proc(String builder) {

        int ind;

        while ((ind = builder.indexOf("[")) > -1) {
            builder = builder.replaceFirst("\\[", " ");
            int ind2 = builder.indexOf("]");
            builder = builder.replaceFirst("\\]", "");
            IntegerStack.getInstance().push(ind, ind2);
        }

        SpannableString spannableString = new SpannableString(builder);
        while (IntegerStack.getInstance().getSize() > 0) {
            int i1, i2;
            i1 = IntegerStack.getInstance().pullFirst();
            i2 = IntegerStack.getInstance().pullFirst();
            spannableString.setSpan(new URLSpan(""), i1, i2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), i1, i2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(1.3f), i1, i2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    private void update(SpannableString spannableString) {
        ((TextView) view.findViewById(R.id.textView)).setText(spannableString);
    }
}
