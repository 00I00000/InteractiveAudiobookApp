package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PauseSaveActivity extends AppCompatActivity {

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
        int state = b.getInt("key");
        Button back = findViewById(R.id.backBtn);
        Button save1 = findViewById(R.id.saveFile1);
        if (state == 0) {
            save1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testForOverwrite(1);
                }
            });
            Button save2 = findViewById(R.id.saveFile2);
            save2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testForOverwrite(2);
                }
            });
            Button save3 = findViewById(R.id.saveFile3);
            save3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testForOverwrite(3);
                }
            });
        } else {
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
        }
        for (int i = 1; i <= 3; i++) {
            checkFiles = getSharedPreferences("saveFile" + i, Context.MODE_PRIVATE);
            if (checkFiles == null) {
                switch (i) {
                    case 1: saveBtn1 = findViewById(R.id.saveFile1);
                            saveBtn1.setEnabled(false);
                            break;
                    case 2: saveBtn2 = findViewById(R.id.saveFile2);
                            saveBtn2.setEnabled(false);
                            break;
                    case 3: saveBtn3 = findViewById(R.id.saveFile3);
                            saveBtn3.setEnabled(false);
                            break;
                }
            }
        }
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
        editor.apply();
    }

    public void load(int savePosition) {
    }

    private void testForOverwrite(final int savePosition) {
        checkFiles = getSharedPreferences("saveFile" + savePosition, Context.MODE_PRIVATE);
        if (checkFiles != null) {
            AlertDialog.Builder prompt = new AlertDialog.Builder(PauseSaveActivity.this);
            prompt.setTitle("Warning!");
            prompt.setMessage("You are about to overwrite an existing save file");
            prompt.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    save(savePosition);
                }
            });
            prompt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            prompt.show();
        }
    }

}
