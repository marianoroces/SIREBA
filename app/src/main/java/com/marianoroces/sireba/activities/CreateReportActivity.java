package com.marianoroces.sireba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marianoroces.sireba.R;
import com.marianoroces.sireba.dialogs.DatePickerFragment;
import com.marianoroces.sireba.model.Category;
import com.marianoroces.sireba.model.Report;
import com.marianoroces.sireba.repositories.CategoryRepository;
import com.marianoroces.sireba.repositories.ReportRepository;
import com.marianoroces.sireba.utils.MyIntentService;
import com.marianoroces.sireba.utils.MyReceiver;
import com.marianoroces.sireba.utils.OnCategoryResultCallback;
import com.marianoroces.sireba.utils.OnReportResultCallback;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateReportActivity extends AppCompatActivity
        implements OnCategoryResultCallback, OnReportResultCallback, DatePickerFragment.DatePickerFragmentListener {

    EditText etDate;
    EditText etDescription;
    EditText etLocation;
    Button btnSelectDate;
    Button btnSelectLocation;
    Button btnTakePicture;
    Button btnContinue;
    Spinner spCategories;
    List<String> categoryNames;
    List<Category> categoryObjects;
    CategoryRepository categoryRepository;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reportImageReference;
    byte[] imageData;
    Report report = new Report();
    ReportRepository reportRepository;
    BroadcastReceiver myReceiver;

    private int NEXT_REPORT_ID;

    static final int CAMERA_REQUEST = 1;
    static final int GALLERY_REQUEST = 2;
    public final int SELECT_LOCATION_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        categoryRepository = new CategoryRepository(this);
        reportRepository = new ReportRepository(this);

        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyReceiver.REPORT_CREATED_EVENT);
        this.registerReceiver(myReceiver, filter);

        initializeComponents();

        categoryNames = new ArrayList<String>();

        categoryRepository.getAll();
        reportRepository.getAll();

        btnTakePicture.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        btnSelectDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnSelectLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(CreateReportActivity.this, ReportMapActivity.class);
                mapIntent.putExtra("startedFrom", "Create Report");
                startActivityForResult(mapIntent, SELECT_LOCATION_CODE);
            }
        });

        btnContinue.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                report.setId(NEXT_REPORT_ID);
                report.setDescription(etDescription.getText().toString());
                for(Category aux : categoryObjects) {
                    if(aux.getCategoryName() == spCategories.getSelectedItem()) {
                        report.setCategory(new Category(aux.getId(), aux.getCategoryName()));
                        Log.d("DEBUG", String.valueOf(report.getCategory().getId()));
                        Log.d("DEBUG", report.getCategory().getCategoryName());
                    }
                }

                Log.d("REPORT", report.getDescription());
                Log.d("REPORT", report.getDate().toString());
                Log.d("REPORT", report.getLocation());
                Log.d("REPORT", String.valueOf(report.getLocationLat()));
                Log.d("REPORT", String.valueOf(report.getLocationLng()));
                Log.d("REPORT", report.getPictureURI());
                Log.d("REPORT", String.valueOf(report.getId()));

                reportRepository.save(report);
            }
        });
    }

    private void initializeComponents(){
        etDate = findViewById(R.id.createRpTxtDate);
        etDescription = findViewById(R.id.createRpTxtDescription);
        etLocation = findViewById(R.id.createRpTxtLocation);
        spCategories = findViewById(R.id.createRpSpinnerCategory);
        btnSelectDate = findViewById(R.id.createRpBtnDate);
        btnContinue = findViewById(R.id.createRpBtnContinue);
        btnSelectLocation = findViewById(R.id.createRpBtnSelectOnMap);
        btnTakePicture = findViewById(R.id.createRpBtnTakePicture);
    }

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void storage(){
        StorageReference storageReference = storage.getReference();
        reportImageReference = storageReference.child("images/report_id_"+NEXT_REPORT_ID+".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_LOCATION_CODE:
                    Double latitude = data.getDoubleExtra("mapLat", 00.000000000000000);
                    Double longitude = data.getDoubleExtra("mapLng", 00.000000000000000);
                    String location = data.getStringExtra("address");

                    report.setLocation(location);
                    report.setLocationLat(latitude);
                    report.setLocationLng(longitude);

                    etLocation.setText(location);
                    break;
                default:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    imageData = baos.toByteArray();

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateReportActivity.this);
                    builder.setMessage("Agregar foto al reporte?")
                            .setTitle("Nuevo reporte")
                            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    storage();
                                    saveReportPicture();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.show();
            }
        }
    }

    private void saveReportPicture() {
        UploadTask uploadTask = reportImageReference.putBytes(imageData);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return reportImageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d("DEBUG", "URI: "+downloadUri.toString());
                    report.setPictureURI(downloadUri.toString());
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            etDate.setText(dateFormat.format(date));
            report.setDate(date);
    }

    @Override
    public void onCategoryListResult(List<Category> categoriesList) {
        categoryObjects = categoriesList;
        for(Category aux : categoriesList) {
            categoryNames.add(aux.getCategoryName());
        }

        ArrayAdapter<String> adapterCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);

        spCategories.setAdapter(adapterCategorias);

        adapterCategorias.notifyDataSetChanged();
    }

    @Override
    public void onCategoryListResult(Category category) {

    }

    @Override
    public void onReportListResult(List<Report> reportsList) {
        NEXT_REPORT_ID = reportsList.size()+1;

        Log.d("DEBUG", "REPORTS: "+reportsList.size());
        Log.d("DEBUG", "NEXT ID: "+(reportsList.size()+1));
        Log.d("DEBUG", "PICTURE URL: images/report_id_"+NEXT_REPORT_ID+".jpg");
    }

    @Override
    public void onReportListResult(Report report) {
        if(this.report.getId() == report.getId()) {
            Log.d("NOTIFICACION", "Intent creado y enviado - CreateReport");

            Intent notify = new Intent(CreateReportActivity.this, MyIntentService.class);
            startService(notify);
            finish();
        }
    }
}