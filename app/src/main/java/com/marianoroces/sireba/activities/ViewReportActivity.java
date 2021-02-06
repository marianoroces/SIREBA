package com.marianoroces.sireba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.marianoroces.sireba.R;

public class ViewReportActivity extends AppCompatActivity {

    EditText etDate, etCategory, etDescription, etLocation;
    Button btnViewOnMap;
    ImageView ivPicture;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etDate = findViewById(R.id.viewRpTxtDate);
        etCategory = findViewById(R.id.viewRpTxtCategory);
        etDescription = findViewById(R.id.viewRpTxtDescription);
        etLocation = findViewById(R.id.viewRpTxtLocation);
        btnViewOnMap = findViewById(R.id.viewRpButton);
        ivPicture = findViewById(R.id.viewRpImageView);

        etDate.setText(getIntent().getStringExtra("date"));
        etCategory.setText(getIntent().getStringExtra("category"));
        etDescription.setText(getIntent().getStringExtra("description"));
        etLocation.setText(getIntent().getStringExtra("location"));

        downloadImage(getIntent().getStringExtra("pictureURI"));

        btnViewOnMap.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapLocationIntent = new Intent(ViewReportActivity.this, ReportMapActivity.class);
                mapLocationIntent.putExtra("startedFrom", "View Report");
                mapLocationIntent.putExtra("lat", getIntent().getDoubleExtra("locationLat", 00.000000000000000));
                mapLocationIntent.putExtra("lng", getIntent().getDoubleExtra("locationLng", 00.000000000000000));
                startActivity(mapLocationIntent);
            }
        });
    }

    private void downloadImage(String uri) {
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(uri);

        final long THREE_MEGABYTES = 3 * 1024 * 1024;
        storageReference.getBytes(THREE_MEGABYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getRealMetrics(dm);

                ivPicture.setMinimumHeight(dm.heightPixels);
                ivPicture.setMinimumWidth(dm.widthPixels);
                ivPicture.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("DEBUG", e.getMessage());
            }
        });
    }
}