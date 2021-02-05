package com.marianoroces.sireba.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marianoroces.sireba.R;
import com.marianoroces.sireba.activities.ViewReportActivity;
import com.marianoroces.sireba.model.Report;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReportRecyclerAdapter extends RecyclerView.Adapter<ReportViewHolder> {

    private List<Report> reportsList;
    private Context context;

    public ReportRecyclerAdapter(List<Report> reports, Context activityContext) {
        reportsList = reports;
        context = activityContext;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_card_view, parent, false);
        ReportViewHolder viewHolder = new ReportViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder reportHolder, int position){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Report reportAux = reportsList.get(position);

        reportHolder.tvCategory.setText(reportAux.getCategory().getCategoryName());
        reportHolder.tvDate.setText(dateFormat.format(reportAux.getDate()));
        reportHolder.tvDescription.setText(reportAux.getDescription());

        reportHolder.getBtnCheckReport().setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentViewReport = new Intent(context, ViewReportActivity.class);
                intentViewReport.putExtra("date", dateFormat.format(reportAux.getDate()));
                intentViewReport.putExtra("category", reportAux.getCategory().getCategoryName());
                intentViewReport.putExtra("description", reportAux.getDescription());
                intentViewReport.putExtra("location", reportAux.getLocation());
                intentViewReport.putExtra("locationLat", reportAux.getLocationLat());
                intentViewReport.putExtra("locationLng", reportAux.getLocationLng());

                if(reportAux.getPictureURI() != null){
                    intentViewReport.putExtra("pictureURI", reportAux.getPictureURI());
                }

                context.startActivity(intentViewReport);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportsList.size();
    }

    public void refreshData(List<Report> list) {
        reportsList.clear();
        reportsList.addAll(list);
    }
}
