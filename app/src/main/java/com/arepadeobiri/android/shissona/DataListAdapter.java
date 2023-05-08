package com.arepadeobiri.android.shissona;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.arepadeobiri.android.shissona.database.Expense;

import java.util.ArrayList;

public class DataListAdapter extends ArrayAdapter<Expense> {
    DataListAdapter(Activity context, ArrayList<Expense> dataList){
        super(context,0,dataList);

    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_data_list, parent, false);
        }

        Expense currentExpense = getItem(position);

        ImageView iconImageView = convertView.findViewById(R.id.itemsImageView);
        TextView dateTextView =  convertView.findViewById(R.id.dateTextView);
        dateTextView.setText(Util.Companion.getFullDateAndTime(currentExpense.getEntryTime()));

        return convertView;

    }
}
