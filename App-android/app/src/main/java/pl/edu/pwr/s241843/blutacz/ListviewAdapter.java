package pl.edu.pwr.s241843.blutacz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class ListviewAdapter extends ArrayAdapter<ListviewData> {
    private Context kontekst;
    private int _resource;
    public ListviewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ListviewData> objects) {
        super(context, resource, objects);
        kontekst=context;
        _resource=resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String parametr = getItem(position).getParametr();
        String wartosc = getItem(position).getWartosc();

        ListviewData obiekt = new ListviewData(parametr,wartosc);

        LayoutInflater inflater = LayoutInflater.from(kontekst);
        convertView=inflater.inflate(_resource,parent,false);

        TextView TextParameter = (TextView) convertView.findViewById(R.id.textView);
        TextView TextWartosc = (TextView) convertView.findViewById(R.id.textView2);

        TextParameter.setText(parametr);
        TextWartosc.setText(wartosc);

        return convertView;
    }
}
