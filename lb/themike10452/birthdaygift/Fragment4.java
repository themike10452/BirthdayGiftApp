package lb.themike10452.birthdaygift;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment implements Identifier {

    public View view;
    private int speedFactor = 1;

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4_layout, container, false);
        view.findViewById(R.id.bottom).setVisibility(View.INVISIBLE);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("AN.txt")));
            String s;
            while ((s = reader.readLine()) != null) {
                try {
                    s = s.replace("\\n", "\n");
                } catch (Exception ignored) {
                } finally {
                    buffer.append(s);
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
            textView.setText(buffer.toString());
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.notify(Fragment4.this);
            }
        });
        //MainActivity.instance.findViewById(R.id.boss).setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void onStart() {
        super.onStart();
        view.findViewById(R.id.bottom).setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF, 0
        );
        animation.setStartOffset(500 * speedFactor);
        animation.setDuration(800 * speedFactor);
        view.findViewById(R.id.bottom).startAnimation(animation);
    }

    @Override
    public int getID() {
        return 4;
    }
}
