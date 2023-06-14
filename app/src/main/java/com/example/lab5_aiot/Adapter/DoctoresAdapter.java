package com.example.lab5_aiot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_aiot.Model.DoctorDB;
import com.example.lab5_aiot.Perfil_doctor;
import com.example.lab5_aiot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctoresAdapter extends RecyclerView.Adapter<DoctoresAdapter.DoctorViewHolder> {
    private List<DoctorDB> listaDoctores;
    private Context context;

    private List<DoctorDB> listaDoctores2;

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

    public DoctoresAdapter() {
    }

    public DoctoresAdapter(List<DoctorDB> listaDoctores) {
        this.listaDoctores = listaDoctores;
        this.listaDoctores2 = new ArrayList<>();
        listaDoctores2.addAll(listaDoctores);
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
        //Cambiar foto
        /*Cambiar foto*/
        ImageView imageView = holder.itemView.findViewById(R.id.imageView3);
        Picasso.with(context)
                .load(doc.getPicture())
                .fit()
                .centerInside()
                .into(imageView);
        //Cambiar nombre
        TextView textViewNombre = holder.itemView.findViewById(R.id.textNombre);
        textViewNombre.setText("Dr."+doc.getFirst());
        //Cambiar Location
        TextView textViewLocation = holder.itemView.findViewById(R.id.textUbicacion);
        textViewLocation.setText(doc.getCountry()+" - "+doc.getState()+" - "+doc.getCity());
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaDoctores.clear();
            listaDoctores.addAll(listaDoctores2);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<DoctorDB> collecion = listaDoctores.stream()
                        .filter(i -> i.getFirst().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaDoctores.clear();
                listaDoctores.addAll(collecion);
            } else {
                for (DoctorDB c : listaDoctores2) {
                    if (c.getFirst().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaDoctores.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return listaDoctores.size();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        DoctorDB doctorDB;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            Button botonInfo = itemView.findViewById(R.id.buttonInfo);
            botonInfo.setOnClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, Perfil_doctor.class);
                intent.putExtra("DoctorNombre", doctorDB.getFirst());
                intent.putExtra("DoctorEmail", doctorDB.getEmail());
                intent.putExtra("DoctorApellido", doctorDB.getLast());
                intent.putExtra("DoctorEdad", doctorDB.getAge());
                intent.putExtra("DoctorUbicacion", doctorDB.getCountry()+" - "+doctorDB.getState()+" - "+doctorDB.getCity());
                intent.putExtra("DoctorTelefono", doctorDB.getPhone());
                intent.putExtra("DoctorFoto", doctorDB.getPicture());
                intent.putExtra("DoctorUser", doctorDB.getLogin());
                intent.putExtra("DoctorNacion", doctorDB.getNat());
                //costo
                int costo = Integer.parseInt(doctorDB.getAge())*5;
                intent.putExtra("DoctorCosto", costo+"");
                //genero
                String genero = "";
                if(doctorDB.getGender().equals("female")){
                    genero = "Mujer";
                }else{
                    genero = "Hombre";
                }
                intent.putExtra("DoctorGenero", genero);
                //////
                context.startActivity(intent);
            });
        }
    }
}
