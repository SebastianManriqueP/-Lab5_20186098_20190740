package com.example.lab5_aiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.lab5_aiot.Adapter.DoctoresAdapter;
import com.example.lab5_aiot.Model.Doctor;
import com.example.lab5_aiot.Model.DoctorDB;
import com.example.lab5_aiot.Model.DoctorRepository;
import com.example.lab5_aiot.databinding.ActivityDoctoresBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctoresActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ActivityDoctoresBinding binding;

    DatabaseReference db;
    DoctoresAdapter doctoresAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseDatabase.getInstance().getReference();
        binding.perfil.setOnClickListener(view -> {
            Intent intent = new Intent(this, Perfil.class);
            startActivity(intent);
        });
        //Buscador
        SearchView buscador = binding.searchView;
        buscador.setOnQueryTextListener(this);

        //Recycler View
        //recibir la lista de doctores
        db.child("doctores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<DoctorDB> listaDoctores = new ArrayList<>();
                for(DataSnapshot snapshot : datasnapshot.getChildren()) {
                    DoctorDB doctorDB = snapshot.getValue(DoctorDB.class);
                    listaDoctores.add(doctorDB);
                }
                //enviar al adapter
                doctoresAdapter = new DoctoresAdapter(listaDoctores);
                doctoresAdapter.setContext(DoctoresActivity.this);

                binding.recyclerView.setAdapter(doctoresAdapter);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(DoctoresActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("msg-tst", "ERROR");
            }
        });

        //Agregar Doctor
        binding.imageMas.setOnClickListener(view -> {
            DoctorRepository doctorRepository = new Retrofit.Builder()
                    .baseUrl("https://randomuser.me")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DoctorRepository.class);

            doctorRepository.getDoctor().enqueue(new Callback<Doctor>() {
                @Override
                public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                    Doctor doctor = response.body();
                    //Enviar Doctor a DB
                    DoctorDB doctorDB = new DoctorDB();
                    doctorDB.setTitle(doctor.getResult().get(0).getName().getTitle());
                    doctorDB.setFirst(doctor.getResult().get(0).getName().getFirst());
                    doctorDB.setLast(doctor.getResult().get(0).getName().getLast());
                    doctorDB.setCity(doctor.getResult().get(0).getLocation().getCity());
                    doctorDB.setState(doctor.getResult().get(0).getLocation().getState());
                    doctorDB.setCountry(doctor.getResult().get(0).getLocation().getCountry());
                    doctorDB.setEmail(doctor.getResult().get(0).getEmail());
                    doctorDB.setAge(doctor.getResult().get(0).getDob().getAge());
                    doctorDB.setPhone(doctor.getResult().get(0).getPhone());
                    doctorDB.setPicture(doctor.getResult().get(0).getPicture().getLarge());
                    //
                    db.child("doctores").push().setValue(doctorDB);
                    crearToast("Se ha agregado a Dr. "+ doctor.getResult().get(0).getName().getFirst());
                }

                @Override
                public void onFailure(Call<Doctor> call, Throwable t) {
                    Log.d("msg-test", "error en la respuesta del webservice");
                }
            });

        //Actualizar Recycler View
        db.child("doctores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<DoctorDB> listaDoctores = new ArrayList<>();
                for(DataSnapshot snapshot : datasnapshot.getChildren()) {
                    DoctorDB doctorDB = snapshot.getValue(DoctorDB.class);
                    listaDoctores.add(doctorDB);
                }
                //enviar al adapter
                doctoresAdapter = new DoctoresAdapter(listaDoctores);
                doctoresAdapter.setContext(DoctoresActivity.this);

                binding.recyclerView.setAdapter(doctoresAdapter);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(DoctoresActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("msg-tst", "ERROR");
            }
        });



        });

    }


    private void crearToast(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        doctoresAdapter.filtrado(s);
        return false;
    }
}