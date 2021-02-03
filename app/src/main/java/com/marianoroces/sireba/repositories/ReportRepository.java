package com.marianoroces.sireba.repositories;

import android.util.Log;

import com.marianoroces.sireba.model.Report;
import com.marianoroces.sireba.services.MyRetrofit;
import com.marianoroces.sireba.services.ReportService;
import com.marianoroces.sireba.utils.OnReportResultCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportRepository implements OnReportResultCallback {

    private ReportService reportService;
    private List<Report> reportsList = new ArrayList<>();
    private OnReportResultCallback reportCallback;

    public ReportRepository(OnReportResultCallback context){
        reportService = MyRetrofit.getInstance().createReportService();
        reportCallback = context;
    }

    public void getAll(){
        Call<List<Report>> callReports = reportService.getAllReports();

        callReports.enqueue(
                new Callback<List<Report>>() {
                    @Override
                    public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                        if(response.code() == 200){
                            Log.d("DEBUG", "Buscar todos los reportes exitoso.");
                            Log.d("DEBUG", response.body().toString());
                            reportsList.clear();
                            reportsList.addAll(response.body());
                            reportCallback.onResult(reportsList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Report>> call, Throwable t) {
                        Log.d("DEBUG", "Buscar todos los resportes fallido");
                        Log.d("DEBUG", t.getMessage());
                    }
                }
        );
    }

    @Override
    public void onResult(List<Report> report) {
        reportCallback.onResult(report);
    }

    @Override
    public void onResult(Report report) {
        reportCallback.onResult(report);
    }
}
