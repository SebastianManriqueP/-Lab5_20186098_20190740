package com.example.lab5_aiot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.lab5_aiot.databinding.ActivityPerfilDoctorBinding;
import com.squareup.picasso.Picasso;

public class Perfil_doctor extends AppCompatActivity {

    ActivityPerfilDoctorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPerfilDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.regresar.setOnClickListener(view -> {
            this.onBackPressed();
        });

        String nombreDoctor = new String();

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String nombre = parametros.getString("DoctorNombre");
            nombreDoctor = nombre;
            String apellido = parametros.getString("DoctorApellido");
            binding.nombreDoctor.setText("Dr."+nombre+" "+apellido);
            String email = parametros.getString("DoctorEmail");
            binding.correo.setText(email);
            String edad = parametros.getString("DoctorEdad");
            binding.edad.setText(edad+" años");
            String ubicacion = parametros.getString("DoctorUbicacion");
            binding.ubicacion.setText(ubicacion);
            String telefono = parametros.getString("DoctorTelefono");
            binding.telefono.setText(telefono);
            String foto = parametros.getString("DoctorFoto");
            /*Cambiar foto*/
            ImageView imageView = binding.fotoDoctor;
            Picasso.with(this)
                    .load(foto)
                    .fit()
                    .centerInside()
                    .into(imageView);
            String costo = parametros.getString("DoctorCosto");
            binding.costo.setText("S/ "+costo);
            String genero = parametros.getString("DoctorGenero");
            binding.genero.setText(genero);
            String nacion = parametros.getString("DoctorNacion");
            binding.nacionalidad.setText(nacion);
            String usuario = parametros.getString("DoctorUser");
            binding.username.setText(usuario);
        }

        String finalNombreDoctor = nombreDoctor;
        binding.button3.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, Ver_mas_doctores.class);
            intent.putExtra("DoctorNombre", finalNombreDoctor);
            finish();
            startActivity(intent);
        });
    }
}