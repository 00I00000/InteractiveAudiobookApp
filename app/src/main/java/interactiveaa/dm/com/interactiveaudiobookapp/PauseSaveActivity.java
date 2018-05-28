package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PauseSaveActivity extends AppCompatActivity {

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
        setContentView(R.layout.save_files);
        hideNavBar();
        Button back = findViewById(R.id.backBtn);
        Button save1 = findViewById(R.id.saveFile1);
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(0);
            }
        });
        Button save2 = findViewById(R.id.saveFile2);
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(1);
            }
        });
        Button save3 = findViewById(R.id.saveFile3);
        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(2);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void save(int savePosition) {
        SharedPreferences sharedPref = getSharedPreferences("saveFiles", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("saveFile" + savePosition, Path.pathIdentifier);
    }

}
