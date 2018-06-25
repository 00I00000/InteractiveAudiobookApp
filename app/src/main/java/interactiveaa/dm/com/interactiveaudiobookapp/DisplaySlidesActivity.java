package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DisplaySlidesActivity extends AppCompatActivity {

    private Button leftAnswer;
    private Button rightAnswer;
    private Button repeat;
    private TextView question;
    private TextView path;
    private ConstraintLayout mLayout;

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
        setContentView(R.layout.display_slides_activity);
        hideNavBar();
        mLayout = findViewById(R.id.display_slides_layout);
        switch (Path.pathIdentifier) {
            case 1:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.adventure408503));
                break;
            case 2:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.aircraft527));
                break;
            case 3:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.clouds879444));
                break;
            case 4:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.clouddark));
                break;
            case 5:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.clouds904111));
                break;
            case 6:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.adventure303040));
                break;
            case 7:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.cloudsdaylight));
                break;
            case 8:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.cloudslandscape));
                break;
            case 9:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.adventure303040));
                break;
            case 10:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.clouddark));
                break;
            case 11:
                mLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.adventure355747));
                break;
            default:
                break;
        }
        path = findViewById(R.id.path);
        path.setText("Entscheidung " + Path.pathIdentifier);
        int audioId = getResources().getIdentifier("entscheidung" + Integer.toString(Path.pathIdentifier), "raw", this.getPackageName());
        PlayAudioActivity.mediaPlayer = MediaPlayer.create(this, audioId);
        PlayAudioActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                PlayAudioActivity.mediaPlayer.stop();
                PlayAudioActivity.mediaPlayer.release();
                PlayAudioActivity.mediaPlayer = null;
            }
        });
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
                stopRunningMP();
                if (leftDeadEnds.contains(p)) {
                    Intent intent = new Intent(DisplaySlidesActivity.this, GameOverActivity.class);
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
        rightAnswer = findViewById(R.id.rightAnswer);
        rightAnswer.setText(pathLogic.getRightAnswer());
        if (rightDisabled.contains(p)) {
            rightAnswer.setEnabled(false);
        }
        rightAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRunningMP();
                if (rightDeadEnds.contains(p)) {
                    Intent intent = new Intent(DisplaySlidesActivity.this, GameOverActivity.class);
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
                stopRunningMP();
                Intent intent = new Intent(DisplaySlidesActivity.this, PlayAudioActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void stopRunningMP() {
        if (PlayAudioActivity.mediaPlayer != null) {
            PlayAudioActivity.mediaPlayer.stop();
            PlayAudioActivity.mediaPlayer.release();
            PlayAudioActivity.mediaPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavBar();
        if (PlayAudioActivity.mediaPlayer != null) {
            PlayAudioActivity.mediaPlayer.start();
        }
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


}
