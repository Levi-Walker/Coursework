package edu.andrews.cptr252.leviwalker.pantrytracker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private ImageButton photo;
    private EditText name;
    private TextView expiration;
    private EditText quantity;
    private InfoItem item;
    private Button save;
    private Button dateChange;
    private final int CAMERA = 1;
    private final int GALLERY = 2;
    private final String IMAGE_DIR = "/itemPhotos";
    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        item = getIntent().getParcelableExtra("item");

        photo = findViewById(R.id.imageButton);
        name = findViewById(R.id.editName);
        expiration = findViewById(R.id.editTextExpirationDate);
        quantity = findViewById(R.id.editTextQuantity);
        save = findViewById(R.id.saveButton);
        dateChange = findViewById(R.id.btnDateChange);


        name.setText(item.getName());
        quantity.setText(item.getQuantity());
        if (item.getExpiration().isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            expiration.setText(String.format("%s/%s/%s", month + 1, day, year));
        } else {
            expiration.setText(item.getExpiration());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerResult();
        }


        save.setOnClickListener(v -> {
            item.setName(name.getText().toString());
            item.setQuantity(quantity.getText().toString());
            String[] datePortions = expiration.getText().toString().split("/");
            String mMonth, mDay, mYear;
            if (datePortions[0].length() == 1) {
                mMonth = String.format("0%s", datePortions[0]);
            } else {
                mMonth = datePortions[0];
            }
            if (datePortions[1].length() == 1) {
                mDay = String.format("0%s", datePortions[1]);
            } else {
                mDay = datePortions[1];
            }
            mYear = datePortions[2];
            String formattedDate = String.format("%s-%s-%s 00:00:00", mYear, mMonth, mDay);
            item.setExpiration(formattedDate);
            if (item.getName().equals("")) {
                Toast.makeText(EditActivity.this, "No name entered", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent();
            i.putExtra("item", item);
            setResult(RESULT_OK, i);
            finish();
        });

        dateChange.setOnClickListener(v -> {
            openCalendar();
        });

        expiration.setOnClickListener(v -> {
            openCalendar();
        });

        photo.setOnClickListener(v -> imageAlert());

        File imgFile = new File(item.getPhoto());
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            photo.setImageBitmap(bitmap);
        } else {
            photo.setImageResource(R.drawable.apple_icon);
        }
    }

    private void imageAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the image source");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takePicture();
            }
        });
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    pickImage();
                } else {
                    loadImage();
                }
            }
        });
        builder.create().show();
    }

    private void takePicture() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission(findViewById(R.id.mainLayout));
        } else {
            showCamera();
        }
    }

    private void requestCameraPermission(View view) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(view, "Camera permission needed", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(EditActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA);

        }

    }

    private ActivityResultLauncher<Void> cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
            new ActivityResultCallback<Bitmap>() {
                @Override
                public void onActivityResult(Bitmap result) {
                    if (result != null) {
                        item.setPhoto(saveImage(result));
                        photo.setImageBitmap(result);
                    }
                }
            });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                }
                break;
            case GALLERY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImage();
                }
                break;
        }
    }

    private void showCamera() {

        cameraLauncher.launch(null);
    }

    private void loadImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestGalleryPermissionAPI33(findViewById(R.id.mainLayout));
            } else {
                requestGalleryPermission(findViewById(R.id.mainLayout));
            }
        } else {
            showGallery();
        }

    }

    private void requestGalleryPermission(View view) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Snackbar.make(view, "Gallery access permission needed", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(EditActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            GALLERY);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    GALLERY);

        }

    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(EditActivity.this.getContentResolver(), imageUri);
                            saveImage(bitmap);
                            item.setPhoto(saveImage(bitmap));
                            photo.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            Toast.makeText(EditActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(EditActivity.this.getContentResolver(), result);
                            saveImage(bitmap);
                            item.setPhoto(saveImage(bitmap));
                            photo.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

    private void showGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pickImage();
        } else {
            galleryLauncher.launch("image/*");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestGalleryPermissionAPI33(View view) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
            Snackbar.make(view, "Gallery access permission needed", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(EditActivity.this,
                            new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            GALLERY);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    GALLERY);

        }

    }


    private String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        String imagePath = "";
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + IMAGE_DIR);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Toast.makeText(this, "Error: Failed to create directory", Toast.LENGTH_SHORT).show();
                return "";
            }
        }
        try {
            File f = new File(directory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            imagePath = f.getAbsolutePath();
        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return imagePath;
    }

    public void openCalendar() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                expiration.setText(String.format("%s/%s/%s", month + 1, dayOfMonth, year));
            }
        }, year, month, day);
        dialog.show();
    }

}
