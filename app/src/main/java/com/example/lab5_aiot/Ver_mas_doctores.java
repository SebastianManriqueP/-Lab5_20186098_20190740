package com.example.lab5_aiot;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lab5_aiot.databinding.ActivityPerfilBinding;
import com.example.lab5_aiot.databinding.ActivityVerMasDoctoresBinding;

public class Ver_mas_doctores extends AppCompatActivity {

    ActivityVerMasDoctoresBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerMasDoctoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null) {
            String nombre = parametros.getString("DoctorNombre");
            binding.nombreDoctor.setText("Se agendo su cita con el Dr. "+nombre);
        }
        binding.button3.setOnClickListener(view -> {
            this.onBackPressed();
        });
    }
}