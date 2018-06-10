package interactiveaa.dm.com.interactiveaudiobookapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DisplaySlidesActivity extends AppCompatActivity {

    private Button leftAnswer;
    private Button rightAnswer;
    private Button repeat;
    private TextView question;
    private TextView path;

    private Path pathLogic;

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
        setContentView(R.layout.slides);
        hideNavBar();
        path = findViewById(R.id.path);
        path.setText("Entscheidung " + Path.pathIdentifier);
        question = findViewById(R.id.question);
        int questionId = getResources().getIdentifier("entscheidung" + Path.pathIdentifier, "string", getPackageName());
        question.setText(getString(questionId));
        pathLogic = new Path(this);
        final int p = Path.pathIdentifier;
        final Set<Integer> leftDeadEnds = new HashSet<Integer>(Arrays.asList(2, 7, 8));
        final Set<Integer> rightDeadEnds = new HashSet<Integer>(Arrays.asList(6, 10));
        Set<Integer> leftDisabled = new HashSet<Integer>(Arrays.asList(1, 3, 4));
        Set<Integer> rightDisabled = new HashSet<Integer>(Arrays.asList(5, 9));
        leftAnswer = findViewById(R.id.leftAnswer);
        leftAnswer.setText(pathLogic.getLeftAnswer());
        if (leftDisabled.contains(p)) {
            leftAnswer.setEnabled(false);
        }
        leftAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftDeadEnds.contains(p)) {
                    Intent intent = new Intent(DisplaySlidesActivity.this, GameOverActivity.class);
                    startActivity(intent);
                    //todo: pretty sure somewhere here is a bug
                    finish();
                } else {
                    Path.pathIdentifier++;
                    Intent intent = new Intent(DisplaySlidesActivity.this, PlayAudioActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        rightAnswer = findViewById(R.id.rightAnswer);
        rightAnswer.setText(pathLogic.getRightAnswer());
        if (rightDisabled.contains(p)) {
            rightAnswer.setEnabled(false);
        }
        rightAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightDeadEnds.contains(p)) {
                    Intent intent = new Intent(DisplaySlidesActivity.this, PlayAudioActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Path.pathIdentifier++;
                    Intent intent = new Intent(DisplaySlidesActivity.this, PlayAudioActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        repeat = findViewById(R.id.repeat);
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplaySlidesActivity.this, PlayAudioActivity.class);
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
