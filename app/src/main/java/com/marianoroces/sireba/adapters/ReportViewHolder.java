package com.marianoroces.sireba.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.marianoroces.sireba.R;

public class ReportViewHolder extends RecyclerView.ViewHolder {

    CardView cvReport;
    TextView tvCategory, tvDate, tvDescription;
    Button btnCheckReport;

    public CardView getCvReport() {
        return cvReport;
    }

    public void setCvReport(CardView cvReport) {
        this.cvReport = cvReport;
    }

    public TextView getTvCategory() {
        return tvCategory;
    }

    public void setTvCategory(TextView tvCategory) {
        this.tvCategory = tvCategory;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    public void setTvDate(TextView tvDate) {
        this.tvDate = tvDate;
    }

    public TextView getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(TextView tvDescription) {
        this.tvDescription = tvDescription;
    }

    public Button getBtnCheckReport() {
        return btnCheckReport;
    }

    public void setBtnCheckReport(Button btnCheckReport) {
        this.btnCheckReport = btnCheckReport;
    }

    public ReportViewHolder (@NonNull View itemView) {
        super(itemView);
        cvReport = itemView.findViewById(R.id.cvReport);
        tvCategory = itemView.findViewById(R.id.cvReportCategory);
        tvDate = itemView.findViewById(R.id.cvReportDate);
        tvDescription = itemView.findViewById(R.id.cvReportDescription);
        btnCheckReport = itemView.findViewById(R.id.cvReportButton);
    }
}
