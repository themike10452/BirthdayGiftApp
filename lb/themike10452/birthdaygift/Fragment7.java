package lb.themike10452.birthdaygift;


import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment7 extends Fragment implements Identifier {

    public View view;

    public Fragment7() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment7_layout, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final AlphaAnimation animation = new AlphaAnimation(0, 1);
        final AlphaAnimation animation0 = new AlphaAnimation(0, 1);
        animation0.setDuration(1000);
        animation0.setStartOffset(1000);
        animation.setDuration(1000);

        view.findViewById(R.id.t1).postDelayed(new Runnable() {
            @Override
            public void run() {
                view.findViewById(R.id.t2).setVisibility(View.VISIBLE);
                view.findViewById(R.id.t2).postOnAnimationDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.findViewById(R.id.input).startAnimation(animation);
                        view.findViewById(R.id.input).setVisibility(View.VISIBLE);
                    }
                }, 1500);

                view.findViewById(R.id.t2).startAnimation(animation);
            }
        }, 3000);

        view.findViewById(R.id.t1).postOnAnimation(new Runnable() {
            @Override
            public void run() {
                view.findViewById(R.id.t1).setVisibility(View.VISIBLE);
            }
        });

        view.findViewById(R.id.t1).startAnimation(animation0);

        ((EditText) view.findViewById(R.id.input)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.toString().equalsIgnoreCase(getString(R.string.the_word))) {
                    InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrator.vibrate(new long[]{0, 200, 100, 200, 100, 500, 300, 200, 100, 200, 100, 500, 300, 200, 100, 200, 100, 500, 300, 200, 100, 200, 100, 500, 300, 200, 100, 200, 100, 500}, -1);
                                    return null;
                                }
                            }.execute();
                            MainActivity.instance.notify(Fragment7.this);
                        }
                    }, 500);
                } else {
                    if (charSequence.length() > 6) {
                        Toast.makeText(getActivity(), "Sorry, try again.", Toast.LENGTH_SHORT).show();
                        ((EditText) view.findViewById(R.id.input)).setText("");
                        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(500);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //MainActivity.instance.notify(Fragment5.this);

    }

    @Override
    public int getID() {
        return 7;
    }
}
