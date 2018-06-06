package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.view.MenuItem;

public class PauseButtonFragment extends Fragment implements View.OnClickListener{

    public void hideNavBar() {
        final Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    ImageButton imageButton;
    private PopupMenu mPopupMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pause_button_fragment, container, false);
        imageButton = (ImageButton) view.findViewById(R.id.pauseBtn);
        imageButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        mPopupMenu = new PopupMenu(getContext(), imageButton);
        mPopupMenu.inflate(R.menu.menu_main);
        mPopupMenu.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                Intent saveIntent = new Intent(this.getActivity(), PauseSaveActivity.class);
                Bundle bSave = new Bundle();
                bSave.putString("key", "save");
                saveIntent.putExtras(bSave);
                startActivity(saveIntent);
                return true;
            case R.id.menu_load:
                Intent loadIntent = new Intent(this.getActivity(), PauseSaveActivity.class);
                Bundle bLoad = new Bundle();
                bLoad.putString("key", "load");
                loadIntent.putExtras(bLoad);
                startActivity(loadIntent);
                return true;
            case R.id.menu_new:
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Close App")
                        .setMessage("Do you want to exit the app?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity(), PlayAudioActivity.class);
                                Bundle b = new Bundle();
                                b.putInt("key", 1);
                                intent.putExtras(b);
                                startActivity(intent);
                                getActivity().finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
