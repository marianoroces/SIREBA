package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reports);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reportRepository = new ReportRepository(this);
        reportsList = new ArrayList<Report>();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        reportRepository.getAllFilteredByUser(currentUser.getUid());

        reportsAdapter = new ReportRecyclerAdapter(reportsList, this);

        rvReports = findViewById(R.id.checkRpReciclerView);
        rvReports.setAdapter(reportsAdapter);
        rvReports.setLayoutManager(new LinearLayoutManager(this));
        rvReports.setHasFixedSize(true);
    }

    @Override
    public void onReportListResult(List<Report> reports) {
        reportsList = reports;
        showResults(reportsList);
        reportsAdapter.refreshData(reportsList);
        reportsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReportListResult(Report report) {

    }

    private void showResults(List<Report> reports) {
        Log.d("DEBUG", String.valueOf(reports.size()));
        for(Report aux : reports) {
            Log.d("DEBUG", aux.getCategory().getCategoryName());
        }
    }
}