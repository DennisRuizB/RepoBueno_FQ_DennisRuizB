package com.example.restclientservweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class MensajeAdapter extends ArrayAdapter<Mensaje> {




    public MensajeAdapter(Context context, List<Mensaje> mensajes) {
        super(context, 0, mensajes);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_mensajes, parent, false);
        }

        Mensaje mensaje = getItem(position);

        TextView textViewMensaje = convertView.findViewById(R.id.textViewMensaje);
        textViewMensaje.setText("message: "+ mensaje.getMensaje());

        return convertView;
    }

}
