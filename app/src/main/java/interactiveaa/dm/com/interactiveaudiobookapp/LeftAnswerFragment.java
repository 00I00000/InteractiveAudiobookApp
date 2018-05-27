package interactiveaa.dm.com.interactiveaudiobookapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LeftAnswerFragment extends Fragment implements View.OnClickListener {

    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_answer_fragment, container, false);
        textView = (TextView) view.findViewById(R.id.leftAnswer);
        textView.setOnClickListener(this);
        return view;
    }

    public void setLeftAnswer(String Text) {
        TextView textView = (TextView) getView().findViewById(R.id.rightAnswer);
        textView.setText(Text);
    }

    @Override
    public void onClick(View view) {
        //start new Activity either here or in DisplaySlidesActivity
    }

}