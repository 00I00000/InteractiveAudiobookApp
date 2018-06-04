package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    private View newGameBtn;

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
        setContentView(R.layout.pause_save_activity);
        newGameBtn = findViewById(R.id.newGameBtn);
        newGameBtn.setVisibility(View.GONE);
        hideNavBar();
        Bundle b = getIntent().getExtras();
        String state = b.getString("key");
        Button back = findViewById(R.id.backBtn);
        saveBtn1 = findViewById(R.id.saveFile1);
        saveBtn2 = findViewById(R.id.saveFile2);
        saveBtn3 = findViewById(R.id.saveFile3);
        Button[] btnContainer = {saveBtn1, saveBtn2, saveBtn3};
        if (state.equals("save")) {
            for (int i = 0; i < btnContainer.length; i++) {
                final int temp = i;
                if (overwritesFile(i + 1)) {
                    btnContainer[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            overwrite(temp + 1);
                        }
                    });
                } else {
                    btnContainer[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            save(temp + 1);
                        }
                    });
                }
            }
        }
        if (state.equals("load")) {
            for (int i = 0; i < btnContainer.length; i++) {
                checkFiles = getSharedPreferences("saveFiles", Context.MODE_PRIVATE);
                int value = checkFiles.getInt("saveFile" + (i + 1), -1);
                if (value == -1) {
                    btnContainer[i].setEnabled(false);
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
        checkFiles = getSharedPreferences("saveFiles", Context.MODE_PRIVATE);
        int pathIdentifier = checkFiles.getInt("saveFile" + savePosition, -1);
        Intent loadIntent = new Intent(PauseSaveActivity.this, DisplaySlidesActivity.class);
        Bundle b = new Bundle();
        b.putInt("key", pathIdentifier);
        loadIntent.putExtras(b);
        startActivity(loadIntent);
        finish();
    }

    private boolean overwritesFile(int savePosition) {
        checkFiles = getSharedPreferences("saveFiles", Context.MODE_PRIVATE);
        int value = checkFiles.getInt("saveFile" + savePosition, -1);
        if (value != -1) {
            return true;
        }
        return false;
    }

    private void overwrite(final int savePosition) {
        AlertDialog.Builder prompt = new AlertDialog.Builder(PauseSaveActivity.this);
        prompt.setTitle("Warning!");
        prompt.setMessage("You are about to overwrite an existing save file");
        prompt.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save(savePosition);
            }
        });
        prompt.setNegativeButton("Cancel", null);
        prompt.show();
    }

}
