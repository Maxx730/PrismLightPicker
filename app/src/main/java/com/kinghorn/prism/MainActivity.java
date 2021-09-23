package com.kinghorn.prism;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static int PICK_IMAGE = 1;
    private Button openImageButton, getColorsButton;
    private LinearLayout colorsLayout;
    private View Color1, Color2, Color3, Color4;
    private ImageView previewImage;
    private Bitmap sourceImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewImage = findViewById(R.id.PreviewImage);
        getColorsButton = findViewById(R.id.GetColors);
        openImageButton = findViewById(R.id.OpenImage);
        openImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        getColorsButton.setVisibility(View.GONE);
        getColorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Palette pal = Palette.from(sourceImage).generate();
                Log.d("Color ::: ", String.valueOf(pal.getVibrantColor(Color.RED)));
                Color1.setBackgroundColor(pal.getDominantColor(Color.RED));
                Color2.setBackgroundColor(pal.getVibrantColor(Color.RED));
                Color3.setBackgroundColor(pal.getMutedColor(Color.RED));
                Color4.setBackgroundColor(pal.getDarkMutedColor(Color.RED));
            }
        });

        Color1 = findViewById(R.id.Colors1);
        Color2 = findViewById(R.id.Colors1);
        Color3 = findViewById(R.id.Colors1);
        Color4 = findViewById(R.id.Colors1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            if (data != null) {
                Uri uri = data.getData();
                try {
                    sourceImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    previewImage.setImageURI(uri);
                    previewImage.getLayoutParams().height = 200;
                    getColorsButton.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: Unable to read selected image.", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error: Unable to open selected image.", Toast.LENGTH_LONG).show();
        }
    }
}