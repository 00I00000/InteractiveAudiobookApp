package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SaveFilesActivity extends AppCompatActivity {

    private SharedPreferences checkFiles;

    private Button saveBtn1;
    private Button saveBtn2;
    private Button saveBtn3;

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
        Bundle b = getIntent().getExtras();
        int book = b.getInt("key");
        Path.bookIdentifier = book;
        saveBtn1 = findViewById(R.id.saveFile1);
        saveBtn2 = findViewById(R.id.saveFile2);
        saveBtn3 = findViewById(R.id.saveFile3);
        Button[] btnContainer = {saveBtn1, saveBtn2, saveBtn3};
        for (int i = 0; i < btnContainer.length; i++) {
            checkFiles = getSharedPreferences("saveFiles", Context.MODE_PRIVATE);
            final int value = checkFiles.getInt("saveFile" + (i + 1), -1);
            if (value != -1) {
                btnContainer[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SaveFilesActivity.this, DisplaySlidesActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("key", value);
                        intent.putExtras(b);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                btnContainer[i].setEnabled(false);
            }
        }
        Button newGame = findViewById(R.id.newGameBtn);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaveFilesActivity.this, PlayAudioActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 1);
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavBar();
    }

}
