package interactiveaa.dm.com.interactiveaudiobookapp;

public class Cover {
    private final int name;
    private final int imageResource;

    public Cover(int name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public int getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
