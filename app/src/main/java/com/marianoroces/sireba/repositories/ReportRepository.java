package com.marianoroces.sireba.repositories;

import android.util.Log;

import com.google.gson.JsonObject;
import com.marianoroces.sireba.model.Report;
import com.marianoroces.sireba.services.MyRetrofit;
import com.marianoroces.sireba.services.ReportService;
import com.marianoroces.sireba.utils.OnReportResultCallback;

import java.text.SimpleDateFormat;
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
                            reportCallback.onReportListResult(reportsList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Report>> call, Throwable t) {
                        Log.d("DEBUG", "Buscar todos los reportes fallido");
                        Log.d("DEBUG", t.getMessage());
                    }
                }
        );
    }

    public void save(Report report){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        JsonObject aux = new JsonObject();
        JsonObject auxCategory = new JsonObject();

        auxCategory.addProperty("id", report.getCategory().getId());
        auxCategory.addProperty("categoryName", report.getCategory().getCategoryName());

        aux.addProperty("date", dateFormat.format(report.getDate()));
        aux.add("category", auxCategory);
        aux.addProperty("description", report.getDescription());
        aux.addProperty("location", report.getLocation());
        aux.addProperty("locationLat", report.getLocationLat());
        aux.addProperty("locationLng", report.getLocationLng());
        aux.addProperty("pictureURI", report.getPictureURI());

        Log.d("REPORT", aux.toString());

        Call<Report> callReport = reportService.saveReport(aux);

        callReport.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                Log.d("DEBUG", "Report guardado");
                reportCallback.onReportListResult(report);
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                Log.d("DEBUG", "Report no guardado");
            }
        });
    }

    @Override
    public void onReportListResult(List<Report> report) {
        reportCallback.onReportListResult(report);
    }

    @Override
    public void onReportListResult(Report report) {
        reportCallback.onReportListResult(report);
    }
}
