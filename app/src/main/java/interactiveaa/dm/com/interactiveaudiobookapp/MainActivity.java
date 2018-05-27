package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("finish")) {
                finish();
            }
        }
    };

    /* needed for landscape orientation programatically
    public void fullscreenCall() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //fullscreenCall();
        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayBooksActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    /*public void buttonOnClick(View v) {
        switch (v.getId()) {
            case R.id.pauseBtn:
                ImageButton imageButton = findViewById(R.id.pauseBtn);
                mPopupMenu = new PopupMenu(this, imageButton);
                mPopupMenu.inflate(R.menu.menu_main);
                mPopupMenu.show();
                break;
            case R.id.start:
                Intent intent = new Intent(MainActivity.this, DisplaySlidesActivity.class);
                startActivity(intent);
                finish();
        }
    }*/

    /* onclick for pauseBtn
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                return true;
            case R.id.menu_load:
                return true;
            case R.id.menu_new:
                return true;
            default:
                return false;
        }
    }*/
}
