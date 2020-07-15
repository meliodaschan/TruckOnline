package com.example.user.truckonlinetaixe.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.Filter;
import android.widget.TextView;

import com.example.user.truckonlinetaixe.Model.DiaDiem;
import com.example.user.truckonlinetaixe.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteDiaDiemAdapter extends ArrayAdapter<DiaDiem> {
    private List<DiaDiem> diadiemListFull;

    public AutoCompleteDiaDiemAdapter(@NonNull Context Context, @NonNull List<DiaDiem> diadiemList) {
        super(Context, 0, diadiemList);
        diadiemListFull = new ArrayList<>(diadiemList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return diadiemFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
//            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            convertView = mInflater.inflate(R.layout.row_auto_complete_adapter, null);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_auto_complete_adapter, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.tv_name);
        DiaDiem diaDiem = getItem(position);

        if (diaDiem != null){
            textViewName.setText(diaDiem.getName());
        }

        return convertView;
    }

    private Filter diadiemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<DiaDiem> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length()==0){
                suggestions.addAll(diadiemListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DiaDiem item : diadiemListFull){
                    if (item.getName().toLowerCase().contains(filterPattern)){
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((DiaDiem)resultValue).getName();
        }
    };
}
