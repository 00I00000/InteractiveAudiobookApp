package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class PlayAudioActivity extends AppCompatActivity {

    private ImageButton playPause;
    private Button skip;
    private Handler handler = new Handler();

    private Button chapter;

    private float currTime;
    public static MediaPlayer mediaPlayer;
    //this needs to be static because PauseSaveActivity accesses this, too lazy to change

    private TextThumbSeekBar seekbar;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.play_audio_activity);
        if (Path.pathIdentifier == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.rueckblende);
        } else {
            int pathId = Path.pathIdentifier;
            int audioId = getResources().getIdentifier("file" + Integer.toString(pathId), "raw", this.getPackageName());
            mediaPlayer = MediaPlayer.create(this, audioId);
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
                if (Path.pathIdentifier == 0) {
                    Path.pathIdentifier++;
                    Intent firstIntent = new Intent(PlayAudioActivity.this, PlayAudioActivity.class);
                    startActivity(firstIntent);
                    finish();
                } else {
                    Intent slidesIntent = new Intent(PlayAudioActivity.this, DisplaySlidesActivity.class);
                    startActivity(slidesIntent);
                    finish();
                }
            }
        });

        chapter = (Button) findViewById(R.id.crt_chapter);
        chapter.setText("Kapitel " + Path.pathIdentifier);
        chapter.setTextColor(Color.parseColor("#CF000000"));
        skip = (Button) findViewById(R.id.skip_btn);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Path.pathIdentifier == 0) {
                    Path.pathIdentifier++;
                    stopPlaying();
                    Intent intent = new Intent(PlayAudioActivity.this, PlayAudioActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    stopPlaying();
                    Intent intent = new Intent(PlayAudioActivity.this, DisplaySlidesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        seekbar = findViewById(R.id.seekBar);
        seekbar.setMax(mediaPlayer.getDuration() / 1000);
        handler.postDelayed(UpdateSongTime, 1000);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if (mediaPlayer != null && fromUser) {
                   mediaPlayer.seekTo(progress * 1000);
               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        playPause = findViewById(R.id.pause_play_btn);
        playPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
        playPause.setTag(R.drawable.baseline_pause_circle_outline_white_48);
        //android:background="?android:selectableItemBackground" used to make button transparent
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentTag = (Integer) playPause.getTag();
                Integer pauseId = new Integer(R.drawable.baseline_pause_circle_outline_white_48);
                if (currentTag.equals(pauseId)) {
                    mediaPlayer.pause();
                    playPause.setImageResource(R.drawable.baseline_play_circle_outline_white_48);
                    playPause.setTag(R.drawable.baseline_play_circle_outline_white_48);
                } else {
                    mediaPlayer.start();
                    playPause.setImageResource(R.drawable.baseline_pause_circle_outline_white_48);
                    playPause.setTag(R.drawable.baseline_pause_circle_outline_white_48);
                }
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                currTime = mediaPlayer.getCurrentPosition() / 1000;
                seekbar.setProgress((int)currTime);
            }
            handler.postDelayed(UpdateSongTime, 1000);
        }
    };

    public void stopPlaying() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.pause();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Close App")
                .setMessage("Do you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        handler.removeCallbacks(UpdateSongTime);
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.start();
                    }
                })
                .show();
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
        } else {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavBar();
    }
}
