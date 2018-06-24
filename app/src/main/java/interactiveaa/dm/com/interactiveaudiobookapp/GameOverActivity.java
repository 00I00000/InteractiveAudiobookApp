package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button loadFiles;
    private Button newGame;
    private Button startGame;
    private TextView gameOverText;
    private TextView gameWonText;
    private ConstraintLayout layout;

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

    //todo: fix game over activity layout probably.
    //todo: add game over text

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_over_activity);
        hideNavBar();
        if (Path.pathIdentifier == 11) {
            gameOverText = findViewById(R.id.game_over_text);
            gameOverText.setVisibility(View.GONE);
            layout = findViewById(R.id.game_over_layout);
            layout.setBackground(ContextCompat.getDrawable(this, R.drawable.adventure753772));
        } else {
            gameWonText = findViewById(R.id.game_won);
            gameWonText.setVisibility(View.GONE);
        }
        loadFiles = findViewById(R.id.load_files);
        loadFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, PauseSaveActivity.class);
                Bundle b = new Bundle();
                b.putString("key", "load");
                intent.putExtras(b);
                startActivity(intent); }
        });
        newGame = findViewById(R.id.new_game);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, PlayAudioActivity.class);
                Path.pathIdentifier = 0;
                startActivity(intent);
                finish();
            }
        });
        startGame = findViewById(R.id.start_screen);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                Path.pathIdentifier = 0;
                startActivity(intent);
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
