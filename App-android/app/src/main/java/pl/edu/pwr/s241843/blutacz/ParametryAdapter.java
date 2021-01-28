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

class ParametryAdapter extends ArrayAdapter<Wartosci> {
    private Context kontekst;
    private int _resource;
    public ParametryAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Wartosci> objects) {
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

        Wartosci obiekt = new Wartosci(parametr,wartosc);

        LayoutInflater inflater = LayoutInflater.from(kontekst);
        convertView=inflater.inflate(_resource,parent,false);

        TextView TextParameter = (TextView) convertView.findViewById(R.id.textView);
        TextView TextWartosc = (TextView) convertView.findViewById(R.id.textView2);

        TextParameter.setText(parametr);
        TextWartosc.setText(wartosc);

        return convertView;
    }
}
