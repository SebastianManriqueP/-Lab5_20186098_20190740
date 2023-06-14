package com.example.lab5_aiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lab5_aiot.databinding.ActivityPerfilBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Perfil extends AppCompatActivity {
    ActivityPerfilBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.regresar.setOnClickListener(view -> {
            this.onBackPressed();
        });
        binding.cerrarsesion.setOnClickListener(view -> {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("msg-test","Logout exitoso");
                        }
                    });
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);

        });
    }
}