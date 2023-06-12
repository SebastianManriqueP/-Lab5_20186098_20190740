package com.example.lab5_aiot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_aiot.Model.DoctorDB;
import com.example.lab5_aiot.R;

import java.util.List;

public class DoctoresAdapter extends RecyclerView.Adapter<DoctoresAdapter.DoctorViewHolder> {
    private List<DoctorDB> listaDoctores;
    private Context context;

    public List<DoctorDB> getListaDoctores() {
        return listaDoctores;
    }

    public void setListaDoctores(List<DoctorDB> listaDoctores) {
        this.listaDoctores = listaDoctores;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor,parent,false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctoresAdapter.DoctorViewHolder holder, int position) {
        DoctorDB doc = listaDoctores.get(position);
        holder.doctorDB = doc;
        //Cambiar nombre
        TextView textViewNombre = holder.itemView.findViewById(R.id.textNombre);
        textViewNombre.setText("Dr."+doc.getFirst());
        //Cambiar Location
        TextView textViewLocation = holder.itemView.findViewById(R.id.textUbicacion);
        textViewLocation.setText(doc.getCountry()+" - "+doc.getState()+" - "+doc.getCity());
    }

    @Override
    public int getItemCount() {
        return listaDoctores.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        DoctorDB doctorDB;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
