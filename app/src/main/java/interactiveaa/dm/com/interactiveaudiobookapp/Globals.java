package interactiveaa.dm.com.interactiveaudiobookapp;

import android.app.Application;

public class Globals extends Application {

    private int[][] path;

    public int[][] getPath() {
        return this.path;
    }

    //path has to be already set from start, probably final
    //probably dont need these

}
