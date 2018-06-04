package interactiveaa.dm.com.interactiveaudiobookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BooksAdapter extends BaseAdapter {

    private final Context mContext;
    private final Cover[] covers;

    public BooksAdapter(Context context, Cover[] covers) {
        this.mContext = context;
        this.covers = covers;
    }

    @Override
    public int getCount() {
        return covers.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Cover cover = covers[position];
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_covers, null);
        }
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.coverpic);
        final TextView nameTextView = (TextView) convertView.findViewById(R.id.textviewname);
        imageView.setImageResource(cover.getImageResource());
        nameTextView.setText(mContext.getString(cover.getName()));
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position != 0) {
            return false;
        }
        return true;
    }
}
