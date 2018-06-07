package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Path {

    private HashMap<Integer, List<String>> path = new HashMap<Integer, List<String>>();
    private List<String> answers = new ArrayList<>();
    public static int bookIdentifier;
    public static int pathIdentifier;

    public Path(int bookIdentifier, int pathIdentifier) {
        this.bookIdentifier = bookIdentifier;
        this.pathIdentifier = pathIdentifier;
        if (bookIdentifier == 0) {

        }
    }

    public int getPathIdentifier() {
        return pathIdentifier;
    }

    public String getLeftAnswer() {
        Integer cast = new Integer(pathIdentifier);
        return path.get(cast).get(0);
    }

    public String getRightAnswer() {
        Integer cast = new Integer(pathIdentifier);
        return path.get(cast).get(1);
    }

    public int getBookIdentifier() {
        return bookIdentifier;
    }

    public static String getBookName () {
        switch (bookIdentifier) {
            case 0: return "MountEverest";
            default: return "Error";
        }
    }
}
