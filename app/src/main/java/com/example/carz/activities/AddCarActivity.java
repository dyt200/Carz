package com.example.carz.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carz.Entities.Car;
import com.example.carz.R;
import com.example.carz.repositories.CarRepository;
import com.example.carz.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;


/**
 * Add a new car!
 */
public class AddCarActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST_CODE = 44;
    private StorageReference mStorageRef;
    private String carUri;
    private Context context = AddCarActivity.this;
    private ImageView c_image;
    private int userId;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        userId = sharedpreferences.getInt("userKey", 0);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Building spinners
        Spinner typeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.type_array,
                android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        Spinner makeSpinner = findViewById(R.id.make_spinner);
        ArrayAdapter<CharSequence> makeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.make_array,
                android.R.layout.simple_spinner_item
        );
        makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setAdapter(makeAdapter);

        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1990; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        Spinner spinYear = findViewById(R.id.year_spinner);
        spinYear.setAdapter(adapter);

        c_image = (ImageView) findViewById(R.id.choosenImage);

        loadImage();

    }

    /**
     * Add a car via the submit button at the bottom of AddCar
     *
     * @param view The current view (passed automatically by button)
     */
    public void addCar(View view) {
        int pos;

        // get user id from shared preferences


        Spinner manufacturerT = findViewById(R.id.make_spinner);
        pos = manufacturerT.getSelectedItemPosition();
        int[] makeValues = getResources().getIntArray(R.array.make_values);
        int manufacturer = makeValues[pos];

        Spinner typeT = findViewById(R.id.type_spinner);
        pos = typeT.getSelectedItemPosition();
        int[] typeValues = getResources().getIntArray(R.array.make_values);
        int type = typeValues[pos];

        EditText modelT = findViewById(R.id.model);
        String model = modelT.getText().toString();

        EditText mileageT = findViewById(R.id.mileage);
        int mileage = Integer.parseInt(mileageT.getText().toString());

        Spinner yearSpinner = findViewById(R.id.year_spinner);
        int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());

        EditText descT = findViewById(R.id.description);
        String desc = descT.getText().toString();

        EditText conditionT = findViewById(R.id.condition);
        String condition = conditionT.getText().toString();

        EditText priceT = findViewById(R.id.price);
        int price = Integer.parseInt(priceT.getText().toString());



        //check that all fields have been completed
        if (manufacturer == 0
                || type == 0
                || model.equals("")
                || mileage == 0
                || year == 0
                || desc.equals("")
                || condition.equals("")
                || price == 0

        ) createToast("Please fill in all of the fields !");
        else
            insertCar(
                    new Car(
                            type,
                            manufacturer,
                            userId,
                            price,
                            year,
                            mileage,
                            model,
                            desc,
                            condition,
                            carUri,
                            ""
                    ),
                    view
            );
    }

    public void chooseImage(View view) {
        pickFromGallery();
    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
                Uri file = data.getData();
                StorageReference riversRef = mStorageRef.child("users/" + userId + "/" + UUID.randomUUID().toString() + ".jpg");


                riversRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                /*  Uri downloadUrl =  taskSnapshot;*/
                                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri downloadUrl = uri;
                                        carUri = downloadUrl.toString();
                                        System.out.println(downloadUrl);
                                        Toast.makeText(getBaseContext(), "Upload success! URL - " + downloadUrl.toString(), Toast.LENGTH_SHORT).show();
                                        loadImage();
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });


            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void loadImage() {
        Glide.with(context)
                .load(carUri)
                .into(c_image);
    }

    /**
     * Process to insert a car
     *
     * @param car  Car to be inserted
     * @param view Context
     */
    private void insertCar(Car car, View view) {
        CarRepository cr = CarRepository.getInstance();
        cr.insert(car, new OnAsyncEventListener() {

            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }

        }, view.getContext());
    }

    /**
     * Manages the response after inserting a car
     *
     * @param response boolean returned from OnAsyncEventListener
     */
    private void setResponse(boolean response) {
        if (response) {
            Intent intent = new Intent(this, CarListActivity.class);
            intent.putExtra("action", "my_cars");
            startActivity(intent);
            createToast("You car was added successfully!");
        } else
            createToast("Failed to create this car !");
    }

    /**
     * Creates a short toast
     *
     * @param text The toast message
     */
    private void createToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT
        );
        toast.show();
    }
}
