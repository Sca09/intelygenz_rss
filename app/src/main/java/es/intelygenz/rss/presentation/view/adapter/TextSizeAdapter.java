package es.intelygenz.rss.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import es.intelygenz.rss.R;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class TextSizeAdapter extends BaseAdapter {

    private Context context;
    private List<String> items;

    public  TextSizeAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = (LayoutInflater.from(context)).inflate(R.layout.custom_spinner_items, null);
        TextView itemText = (TextView) convertView.findViewById(R.id.item_text);
        itemText.setText(items.get(position));
        return convertView;
    }
}
