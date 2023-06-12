package com.example.lab5_aiot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.lab5_aiot.databinding.ActivityRegistroBinding;
import com.example.lab5_aiot.entitites.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroActivity extends AppCompatActivity {

    ActivityRegistroBinding binding;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseDatabase.getInstance().getReference();

        binding.buttonRegistrar.setOnClickListener(view -> {

            
            //Validación de todos los campos:
            if( TextUtils.isEmpty(binding.editNombre.getText().toString().trim()) ){
                Toast.makeText(this, "Ingresar Nombre", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.editTextCorreo.getText().toString().trim())) {
                Toast.makeText(this, "Ingresar Correo", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.editTextTextPassword.getText().toString().trim())) {
                Toast.makeText(this, "Ingresar contraseña", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.editTextNumero.getText().toString().trim())) {
                Toast.makeText(this, "Ingresar Numero de telefono", Toast.LENGTH_SHORT).show();
            }else{
                //Agregar el usuario
                Usuario usuario = new Usuario();
                usuario.setNombre(binding.editNombre.getText().toString());
                usuario.setCorreo(binding.editTextCorreo.getText().toString());
                usuario.setContrasenha(binding.editTextTextPassword.getText().toString());
                usuario.setNumero(binding.editTextNumero.getText().toString());

                db.child("usuarios").push().setValue(usuario);
                Toast.makeText(this, "Usuario Creado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        binding.textInicio.setOnClickListener(view -> {
            finish();
        });
    }
}