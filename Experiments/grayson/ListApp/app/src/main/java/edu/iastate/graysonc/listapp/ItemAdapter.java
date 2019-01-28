package edu.iastate.graysonc.listapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ItemAdapter extends BaseAdapter {
    LayoutInflater mInflater;

    private String[] people, towns;

    public ItemAdapter(Context c, String[] p, String[] t) {
        people = p;
        towns = t;

        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return people.length;
    }

    @Override
    public Object getItem(int position) {
        return people[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.my_listview_detail, null);
        TextView nameTextView = (TextView)v.findViewById(R.id.nameTextView);
        TextView townTextView = (TextView)v.findViewById(R.id.townTextView);

        nameTextView.setText(people[position]);
        townTextView.setText(towns[position]);

        return v;
    }
}
