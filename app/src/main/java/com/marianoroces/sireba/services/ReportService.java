package com.marianoroces.sireba.services;

import com.google.gson.JsonObject;
import com.marianoroces.sireba.model.Report;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReportService {

    @GET("reports/{id}")
    Call<Report> getReport(@Path("id") int id);

    @GET("reports")
    Call<List<Report>> getAllReports();

    @DELETE("reports/{id}")
    Call<Report> deleteReport(@Path("id") int id);

    @PUT("reports")
    Call<Report> saveReport(@Body JsonObject body);

}