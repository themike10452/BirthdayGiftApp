package lb.themike10452.birthdaygift;


import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

import lb.themike10452.birthdaygift.Adapters.Entry;
import lb.themike10452.birthdaygift.Adapters.ListViewAdapter;


public class Fragment8 extends Fragment implements Identifier {

    public View view;
    private int speedFactor = 1;

    public Fragment8() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment8_layout, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.layout_background));
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Field[] fields = R.drawable.class.getDeclaredFields();
        for (final Field field : fields) {
            if (field.getName().contains("_img"))
                new AsyncTask<Void, Void, View>() {
                    @Override
                    protected View doInBackground(Void... voids) {
                        final int resId;
                        ImageView view1 = null;
                        try {
                            resId = field.getInt(new R.drawable());
                            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), resId));
                            bitmapDrawable.setAntiAlias(true);
                            view1 = new ImageView(getActivity());
                            view1.setBackground(bitmapDrawable);
                            view1.setLayoutParams(new ActionBar.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.view_size), getResources().getDimensionPixelOffset(R.dimen.view_size)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return view1;
                    }

                    @Override
                    protected void onPostExecute(View aView) {
                        super.onPostExecute(aView);
                        if (aView != null) {
                            ((LinearLayout) view.findViewById(R.id.container1)).addView(aView);
                        }
                    }
                }.execute();
        }

        ArrayList<Entry> list = new ArrayList<Entry>();

        list.add(new Entry(getActivity(), "Sample phrase 1 :)", "Mike", R.raw.happybirthday));
        list.add(new Entry(getActivity(), getString(R.string.iffo), "Iffo", R.raw.iffo2));
        list.add(new Entry("Sample phrase 3 :)", "Stephou"));
        list.add(new Entry(getActivity(), "Hey Vani, today it's your birthday. I want to wish you a happy birthday, from your brother Peter.", "Peter", R.raw.peter));

        final ListViewAdapter adapter = new ListViewAdapter(getActivity(), R.layout.text_layout, R.id.sample, list);
        final LinearLayout listView = (LinearLayout) view.findViewById(R.id.listView);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int count = adapter.getCount();
                for (int i = 0; i < count; i++) {
                    View v = adapter.getView(i, null, null);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(0, 0, 0, 15);
                    listView.addView(v, params);
                }
            }
        };

        listView.post(runnable);

        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(2000 * speedFactor);
        animation.setStartOffset(1000);

        view.findViewById(R.id.container).startAnimation(animation);

    }

    @Override
    public void onPause() {
        super.onPause();
        for (Entry entry : ListViewAdapter.instance.obj)
            if (entry != null)
                if (entry.sound != null)
                    if (entry.sound.isPlaying())
                        entry.sound.pause();
    }

    @Override
    public int getID() {
        return 8;
    }

}
