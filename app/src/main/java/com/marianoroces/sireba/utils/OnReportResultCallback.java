package com.marianoroces.sireba.utils;

import com.marianoroces.sireba.model.Report;

import java.util.List;

public interface OnReportResultCallback {
    void onReportListResult(List<Report> reportsList);
    void onReportListResult(Report report);
}
