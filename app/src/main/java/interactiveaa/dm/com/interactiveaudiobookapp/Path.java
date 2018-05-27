package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;

public class Path {

    private String story;
    private Path leftP;
    private Path rightP;
    private int identifier;

    Context mContext;

    public Path(int resIdentifier, int identifier) {
        this.identifier = identifier;
        story = App.getContext().getResources().getString(resIdentifier);
    }

    public void addLeft(int identifier) {
        leftP.story = App.getContext().getResources().getString(identifier);
    }

    public void addRight(int identifier) {
        rightP.story = App.getContext().getResources().getString(identifier);
    }

    public String getStory() {
        return story;
    }

    public Path getLeftP() {
        return leftP;
    }


    public Path getRightP() {
        return rightP;
    }

    public int getIdentifier() {
        return identifier;
    }
}
