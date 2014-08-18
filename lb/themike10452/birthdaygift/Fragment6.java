package lb.themike10452.birthdaygift;

import android.app.Fragment;
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
import java.io.IOException;
import java.io.InputStreamReader;

import lb.themike10452.birthdaygift.Stacks.IntegerStack;

/**
 * Created by Mike on 8/11/2014.
 */
public class Fragment6 extends Fragment implements Identifier {

    public View view;
    private int speedFactor = 1;
    private StringBuffer aBuffer;

    public Fragment6() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment6_layout, container, false);
        view.findViewById(R.id.bottom).setVisibility(View.INVISIBLE);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                final TextView textView = (TextView) view.findViewById(R.id.textView);
                final StringBuffer buffer = new StringBuffer();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("CP.txt")));
                    String s;
                    while ((s = reader.readLine()) != null) {
                        try {
                            s = s.replace("\\n", "\n");
                        } catch (Exception ignored) {
                        } finally {
                            buffer.append(s);
                        }
                    }

                    new AsyncTask<StringBuffer, Void, SpannableString>() {

                        @Override
                        protected SpannableString doInBackground(StringBuffer... strings) {
                            return proc(strings[0].toString());
                        }

                        @Override
                        protected void onPostExecute(final SpannableString ss) {
                            super.onPostExecute(ss);
                            textView.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(ss);
                                    view.findViewById(R.id.bottom).setVisibility(View.VISIBLE);
                                    TranslateAnimation animation = new TranslateAnimation(
                                            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                                            Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF, 0
                                    );
                                    animation.setDuration(800 * speedFactor);

                                    AlphaAnimation animation1 = new AlphaAnimation(0, 1);
                                    animation1.setDuration(600);

                                    view.findViewById(R.id.textView).startAnimation(animation1);
                                    view.findViewById(R.id.bottom).startAnimation(animation);
                                }
                            });
                        }
                    }.execute(buffer);

                } catch (Exception e) {

                } finally {
                    if (reader != null)
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                }
            }
        }, 500);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.notify(Fragment6.this);
            }
        });
        //MainActivity.instance.findViewById(R.id.boss).setBackgroundColor(getResources().getColor(android.R.color.white));
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

    @Override
    public int getID() {
        return 6;
    }
}
