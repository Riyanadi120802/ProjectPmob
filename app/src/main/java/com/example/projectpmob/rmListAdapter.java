package com.example.projectpmob;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.projectpmob.R;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class rmListAdapter extends ArrayAdapter<rmList>{
        public rmListAdapter(Activity context, ArrayList<rmList> rMnya) {
                super(context, 0,rMnya);
        }
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                        View listItemView = convertView;
                        listItemView = null;
                        if(listItemView == null) {
                                listItemView = LayoutInflater.from(getContext()).inflate(
                                        R.layout.custom_layout, parent, false);
                        }else{
                        }
                        rmList currentrsList = getItem(position);
                        TextView nameTextView = listItemView.findViewById(R.id.tvNamaCustom);
                        nameTextView.setText(currentrsList.getnamaRM());
                        TextView jarakTextView = listItemView.findViewById(R.id.tvJarak);
                        jarakTextView.setText(new DecimalFormat("##.##").format(currentrsList.getJarakRM()) + "KM ");
                        return listItemView;
                }

        }
