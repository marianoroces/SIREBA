package com.marianoroces.sireba.utils;

import com.marianoroces.sireba.model.Report;

import java.util.List;

public interface OnReportResultCallback {
    void onResult(List<Report> reportsList);
    void onResult(Report report);
}
