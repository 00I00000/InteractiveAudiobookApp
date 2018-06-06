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
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_save:
                        Intent saveIntent = new Intent(getActivity(), PauseSaveActivity.class);
                        Bundle bSave = new Bundle();
                        bSave.putString("key", "save");
                        saveIntent.putExtras(bSave);
                        getActivity().startActivity(saveIntent);
                        return true;
                    case R.id.menu_load:
                        Intent loadIntent = new Intent(getActivity(), PauseSaveActivity.class);
                        Bundle bLoad = new Bundle();
                        bLoad.putString("key", "load");
                        loadIntent.putExtras(bLoad);
                        getActivity().startActivity(loadIntent);
                        return true;
                    case R.id.menu_new:
                        new AlertDialog.Builder(getContext())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("New Game")
                                .setMessage("Starting a new game will cause loss of unsaved progress")
                                .setPositiveButton("Start a new game", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getActivity(), PlayAudioActivity.class);
                                        Bundle b = new Bundle();
                                        b.putInt("key", 1);
                                        intent.putExtras(b);
                                        getActivity().startActivity(intent);
                                        getActivity().finish();
                                    }

                                })
                                .setNegativeButton("Back", null)
                                .show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        mPopupMenu.show();
    }

}
