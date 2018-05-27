package interactiveaa.dm.com.interactiveaudiobookapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

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
        mPopupMenu.show();
    }
}
