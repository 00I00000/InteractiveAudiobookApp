package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

public class DisplayBooksActivity extends AppCompatActivity {

    public static boolean booksCall;

    private SharedPreferences checkFiles;

    public void hideNavBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.books);
        hideNavBar();
        GridView gridView = (GridView)findViewById(R.id.gridviewbooks);
        final BooksAdapter booksAdapter = new BooksAdapter(this, covers);
        boolean fileExists = false;
        for (int i = 0; i < 3; i++) {
            checkFiles = getSharedPreferences(Path.getBookName(), Context.MODE_PRIVATE);
            int value = checkFiles.getInt("saveFile" + (i + 1),-1);
            if (value != -1) {
                fileExists = true;
            }
        }
        final boolean temp = fileExists;
        gridView.setAdapter(booksAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Cover cover = covers[position];
                Path.bookIdentifier = position;
                if (temp) {
                    Intent loadIntent = new Intent(DisplayBooksActivity.this, PauseSaveActivity.class);
                    Bundle b = new Bundle();
                    booksCall = true;
                    b.putString("key","load");
                    loadIntent.putExtras(b);
                    startActivity(loadIntent);
                } else {
                    Intent newGameIntent = new Intent(DisplayBooksActivity.this, SaveFilesActivity.class);
                    startActivity(newGameIntent);
                }
            }
        });
    }

    private Cover[] covers = {
            new Cover(R.string.mountever, R.drawable.accomplishment),
            new Cover(R.string.comingsoon, R.drawable.comingsoon),
            new Cover(R.string.comingsoon, R.drawable.comingsoon),
            new Cover(R.string.comingsoon, R.drawable.comingsoon),
            new Cover(R.string.comingsoon, R.drawable.comingsoon),
            new Cover(R.string.comingsoon, R.drawable.comingsoon),
            new Cover(R.string.comingsoon, R.drawable.comingsoon),
            new Cover(R.string.comingsoon, R.drawable.comingsoon),
            new Cover(R.string.comingsoon, R.drawable.comingsoon),
    };

}
