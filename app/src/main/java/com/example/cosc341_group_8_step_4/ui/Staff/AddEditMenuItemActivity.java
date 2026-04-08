package com.example.cosc341_group_8_step_4.ui.Staff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosc341_group_8_step_4.R;
import com.example.cosc341_group_8_step_4.data.AppData;
import com.example.cosc341_group_8_step_4.model.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddEditMenuItemActivity extends AppCompatActivity {

    private EditText etName, etDescription, etPrice, etOptions;
    private Spinner spinnerCategory;
    private Button btnSave, btnCancel, btnUploadFoodImage;
    private ImageView imgFoodPreview;
    private CheckBox cbItemAvailable;

    private String mode;
    private int itemIndex = -1;

    private Uri selectedImageUri = null;
    private String currentImagePath = null;
    private int currentImageResId = R.drawable.res_logo;

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgFoodPreview.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu_item);

        etName = findViewById(R.id.etItemName);
        etDescription = findViewById(R.id.etItemDescription);
        etPrice = findViewById(R.id.etItemPrice);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSaveItem);
        btnCancel = findViewById(R.id.btnCancelItem);
        btnUploadFoodImage = findViewById(R.id.btnUploadFoodImage);
        imgFoodPreview = findViewById(R.id.imgFoodPreview);
        etOptions = findViewById(R.id.etItemOptions);
        cbItemAvailable = findViewById(R.id.cbItemAvailable);

        String[] categories = {"Food", "Drinks", "Desserts"};
        spinnerCategory.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        ));

        mode = getIntent().getStringExtra("mode");
        itemIndex = getIntent().getIntExtra("itemIndex", -1);

        if ("edit".equals(mode) && itemIndex >= 0) {
            MenuItem item = AppData.menuItems.get(itemIndex);

            if (item.options != null && item.options.length > 0) {
                etOptions.setText(String.join(", ", item.options));
            }

            etName.setText(item.name);
            etDescription.setText(item.description);
            etPrice.setText(String.valueOf(item.price));
            cbItemAvailable.setChecked(item.isAvailable);

            currentImagePath = item.imagePath;
            currentImageResId = item.imageResId;

            if (item.imagePath != null && !item.imagePath.isEmpty()) {
                File file = new File(item.imagePath);
                if (file.exists()) {
                    imgFoodPreview.setImageURI(Uri.fromFile(file));
                } else {
                    imgFoodPreview.setImageResource(item.imageResId);
                }
            } else {
                imgFoodPreview.setImageResource(item.imageResId);
            }

            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(item.category)) {
                    spinnerCategory.setSelection(i);
                    break;
                }
            }
        } else {
            imgFoodPreview.setImageResource(R.drawable.res_logo);
        }

        btnUploadFoodImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        btnSave.setOnClickListener(v -> saveItem());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String priceText = etPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String optionsText = etOptions.getText().toString().trim();
        boolean isAvailable = cbItemAvailable.isChecked();

        if (name.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
            return;
        }

        String finalImagePath = currentImagePath;
        int finalImageResId = currentImageResId;

        if (selectedImageUri != null) {
            String savedPath = saveImageToInternalStorage(selectedImageUri);
            if (savedPath == null) {
                Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
                return;
            }
            finalImagePath = savedPath;
            finalImageResId = 0;
        }

        String[] optionsArray;

        if (optionsText.isEmpty()) {
            optionsArray = new String[0];
        } else {
            optionsArray = optionsText.split("\\s*,\\s*");
        }

        if ("edit".equals(mode) && itemIndex >= 0) {
            MenuItem item = AppData.menuItems.get(itemIndex);

            item.name = name;
            item.description = description;
            item.price = price;
            item.category = category;
            item.imagePath = finalImagePath;
            item.imageResId = finalImageResId;
            item.options = optionsArray;
            item.isAvailable = isAvailable;
        } else {
            MenuItem newItem = new MenuItem(
                    name,
                    description,
                    price,
                    category,
                    finalImageResId,
                    finalImagePath,
                    optionsArray
            );

            newItem.isAvailable = isAvailable;
            AppData.addMenuItem(newItem);
        }

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            if (inputStream == null) {
                return null;
            }

            File directory = new File(getFilesDir(), "menu_images");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = "menu_" + System.currentTimeMillis() + ".jpg";
            File imageFile = new File(directory, fileName);

            FileOutputStream outputStream = new FileOutputStream(imageFile);

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            return imageFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}