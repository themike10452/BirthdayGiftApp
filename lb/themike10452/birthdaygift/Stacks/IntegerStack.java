package lb.themike10452.birthdaygift.Stacks;

import java.util.ArrayList;

/**
 * Created by Mike on 8/4/2014.
 */
public class IntegerStack {

    private static IntegerStack instance;

    ArrayList<Integer> list;

    public IntegerStack() {
        list = new ArrayList<Integer>();
    }

    public static IntegerStack getInstance() {
        if (instance != null)
            return instance;
        else
            return instance = new IntegerStack();
    }

    public void push(Integer... in) {
        for (Integer integer : in) {
            list.add(integer);
        }
    }

    public Integer pullFirst() {
        return list.remove(0);
    }

    public Integer pull() {
        return list.remove(list.size() - 1);
    }

    public Integer getSize() {
        return list.size();
    }

}
