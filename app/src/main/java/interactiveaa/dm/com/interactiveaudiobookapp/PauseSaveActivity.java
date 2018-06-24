package interactiveaa.dm.com.interactiveaudiobookapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PauseSaveActivity extends AppCompatActivity {

    private SharedPreferences checkFiles;

    private Activity callingActivity;

    private Button saveBtn1;
    private Button saveBtn2;
    private Button saveBtn3;

    private Button newGameBtn;

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
        if (!DisplayBooksActivity.booksCall) {
            newGameBtn = findViewById(R.id.new_game_books);
            newGameBtn.setVisibility(View.GONE);
        } else {
            DisplayBooksActivity.booksCall = false;
            newGameBtn = findViewById(R.id.new_game_books);
            newGameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PauseSaveActivity.this, PlayAudioActivity.class);
                    startActivity(intent);
                }
            });
        }
        hideNavBar();
        Bundle b = getIntent().getExtras();
        String state = b.getString("key");
        Button back = findViewById(R.id.backBtn);
        saveBtn1 = findViewById(R.id.saveFile1);
        saveBtn2 = findViewById(R.id.saveFile2);
        saveBtn3 = findViewById(R.id.saveFile3);
        Button[] btnContainer = {saveBtn1, saveBtn2, saveBtn3};
        checkFiles = getSharedPreferences(Path.getBookName(), Context.MODE_PRIVATE);
        if (state.equals("save")) {
            for (int i = 0; i < btnContainer.length; i++) {
                int value = checkFiles.getInt("saveFile" + (i + 1), -1);
                if (value != -1) {
                    btnContainer[i].setText("Kapitel " + value);
                    btnContainer[i].setTransformationMethod(null);
                }
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
                int value = checkFiles.getInt("saveFile" + (i + 1), -1);
                if (value == -1) {
                    btnContainer[i].setEnabled(false);
                } else {
                    final int temp = i;
                    btnContainer[i].setText("Kapitel " + value);
                    btnContainer[i].setTransformationMethod(null);
                    btnContainer[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            load(temp + 1);
                        }
                    });
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
        SharedPreferences sharedPref = getSharedPreferences(Path.getBookName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("saveFile" + savePosition, Path.pathIdentifier);
        editor.apply();
        finish();
    }

    public void load(int savePosition) {
        checkFiles = getSharedPreferences(Path.getBookName(), Context.MODE_PRIVATE);
        int pathIdentifier = checkFiles.getInt("saveFile" + savePosition, -1);
        Intent loadIntent = new Intent(PauseSaveActivity.this, DisplaySlidesActivity.class);
        Path.pathIdentifier = pathIdentifier;
        loadIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        if (PlayAudioActivity.mediaPlayer != null) {
            PlayAudioActivity.mediaPlayer.stop();
            PlayAudioActivity.mediaPlayer.release();
        }
        startActivity(loadIntent);
        finish();
    }

    private boolean overwritesFile(int savePosition) {
        checkFiles = getSharedPreferences(Path.getBookName(), Context.MODE_PRIVATE);
        int value = checkFiles.getInt("saveFile" + savePosition, -1);
        return value != -1;
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

    @Override
    public void onBackPressed() {
        if (PlayAudioActivity.mediaPlayer != null) {
            PlayAudioActivity.mediaPlayer.pause();
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Close App")
                    .setMessage("Do you want to exit the app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PlayAudioActivity.mediaPlayer.stop();
                            PlayAudioActivity.mediaPlayer.release();
                            finish();
                        }

                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PlayAudioActivity.mediaPlayer.start();
                        }
                    })
                    .show();
        }
    }

}
