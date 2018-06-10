package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;

import interactiveaa.dm.com.interactiveaudiobookapp.R;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Path {

    private HashMap<Integer, ArrayList<String>> path = new HashMap<Integer, ArrayList<String>>();
    public static int bookIdentifier;
    public static int pathIdentifier;

    public Path(Context context) {
        if (bookIdentifier == 0) {
            for (int i = 1; i < 11; i++) {
                ArrayList<String> answers = new ArrayList<>();
                int leftAnswerId = 0;
                int rightAnswerId = 0;
                try {
                    Class res = R.string.class;
                    Field field = res.getField("a" + pathIdentifier + "_" + 1);
                    leftAnswerId = field.getInt(null);
                    Field field2 = res.getField("a" + pathIdentifier + "_" + 2);
                    rightAnswerId = field2.getInt(null);
                } catch (Exception e) {

                }
                String left = context.getResources().getString(leftAnswerId);
                String right = context.getResources().getString(rightAnswerId);
                answers.add(left);
                answers.add(right);
                path.put(i, answers);
                //importing (namespace).R is crucial to extracting strings from strings.xml
            }
        }
    }

    public String getLeftAnswer() {
        Integer cast = new Integer(pathIdentifier);
        return path.get(cast).get(0);
    }

    public String getRightAnswer() {
        Integer cast = new Integer(pathIdentifier);
        return path.get(cast).get(1);
    }

    public static String getBookName () {
        switch (bookIdentifier) {
            case 0: return "MountEverest";
            default: return "Error";
        }
    }
}
