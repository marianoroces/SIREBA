package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.marianoroces.sireba.R;
import com.marianoroces.sireba.adapters.ReportRecyclerAdapter;
import com.marianoroces.sireba.model.Report;
import com.marianoroces.sireba.repositories.ReportRepository;
import com.marianoroces.sireba.utils.OnReportResultCallback;

import java.util.ArrayList;
import java.util.List;

public class CheckReportsActivity extends AppCompatActivity implements OnReportResultCallback {

    RecyclerView rvReports;
    ReportRecyclerAdapter reportsAdapter;
    List<Report> reportsList;
    ReportRepository reportRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reports);

        reportRepository = new ReportRepository(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reportsList = new ArrayList<Report>();

        reportRepository.getAll();

        reportsAdapter = new ReportRecyclerAdapter(reportsList, this);

        rvReports = findViewById(R.id.checkRpReciclerView);
        rvReports.setAdapter(reportsAdapter);
        rvReports.setLayoutManager(new LinearLayoutManager(this));
        rvReports.setHasFixedSize(true);
    }

    @Override
    public void onResult(List<Report> reports) {
        reportsList = reports;
        showResults(reportsList);
        reportsAdapter.refreshData(reportsList);
        reportsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResult(Report report) {

    }

    private void showResults(List<Report> reports) {
        Log.d("DEBUG", String.valueOf(reports.size()));
        for(Report aux : reports) {
            Log.d("DEBUG", aux.getCategory().getCategoryName());
        }
    }
}