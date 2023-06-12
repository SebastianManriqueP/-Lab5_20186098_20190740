package com.example.lab5_aiot.Model;



import retrofit2.Call;
import retrofit2.http.GET;

public interface DoctorRepository {

    @GET("/api/")
    Call<Doctor> getDoctor();
}
