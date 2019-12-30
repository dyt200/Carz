package com.example.carz.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.carz.Entities.Car;
import com.example.carz.Entities.CarImage;
import com.example.carz.R;
import com.example.carz.db.entities.FCar;
import com.example.carz.db.repo.CarRepo;
import com.example.carz.repositories.CarRepository;
import com.example.carz.repositories.ImageRepository;
import com.example.carz.util.OnAsyncEventListener;
import com.example.carz.util.OnAsyncInsertEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Add a new car!
 */
public class AddCarActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST_CODE = 44;
    private StorageReference mStorageRef;
    private Context context = AddCarActivity.this;
    private String userId;
    private List<String> addedImageUrls = new ArrayList<>();
    private Boolean successImageUpload = false;

    LinearLayout imgLl;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

        imgLl = findViewById(R.id.imgRl);

        //get user id from session
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userKey", "");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        //get back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    }

    /**
     * Add a car via the submit button at the bottom of AddCar
     *
     * @param view The current view (passed automatically by button)
     */
    public void addCar(View view) {
        int pos;

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
        String mileString = mileageT.getText().toString();
        int mileage;

        if (mileString.equals(""))
            mileage = 0;
        else
            mileage = Integer.parseInt(mileString);

        Spinner yearSpinner = findViewById(R.id.year_spinner);
        int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());

        EditText descT = findViewById(R.id.description);
        String desc = descT.getText().toString();

        EditText conditionT = findViewById(R.id.condition);
        String condition = conditionT.getText().toString();

        EditText priceT = findViewById(R.id.price);
        int price;
        String priceString = priceT.getText().toString();

        if(priceString.equals(""))
            price = 0;
        else
            price = Integer.parseInt(priceT.getText().toString());


        //check that all fields have been completed
        if (manufacturer == 0
                || type == 0
                || model.equals("")
                || mileage == 0
                || year == 0
                || desc.equals("")
                || condition.equals("")
                || price == 0
        ) {
            createToast("Please fill in all of the fields !");
        }
        else if (addedImageUrls.size() == 0){
            createToast("Hey, you need to add at least one image!");
        }
        else {
            // TODO Delete when firebase works
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
                            condition
                    ),
                    view
            );
            insertFCar(
                    new FCar(
                            type,
                            manufacturer,
                            userId,
                            price,
                            year,
                            mileage,
                            model,
                            desc,
                            condition,
                            addedImageUrls
                    ),
                    view
            );
        }
    }

    public void chooseImage(View view) {
        pickFromGallery();
    }

    /**
     * Select an image from the gallery
     */
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

                if(file != null) {
                    riversRef.putFile(file).addOnSuccessListener(taskSnapshot -> {
                        // Get a URL to the uploaded content
                        riversRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String carUri = uri.toString();
                            addedImageUrls.add(carUri);
                            createToast("Upload successful");
                            loadImage(carUri);
                        });
                    })
                    .addOnFailureListener(exception -> createToast("Upload unsuccessful"));
                }
            }
        } catch (Exception ex) {
            Log.i("AddCarActivity upload", ex.toString());
        }
    }

    /**
     * Load an image from uri
     * @param carUri the image uri
     */
    public void loadImage(String carUri) {
        ImageView iv = new ImageView(getApplicationContext());
        imgLl.addView(iv);
        Glide.with(context)
                .load(carUri)
                .into(iv);
        iv.setPadding(0,20,0,20);
        iv.setOnLongClickListener(view -> {
            deleteConfirmation(view, carUri);
            return true;
        });
    }

    /**
     * Display delete confirmation for image
     * @param view current view
     * @param carUri the URI of the image
     */
    public void deleteConfirmation(View view, String carUri) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

        alertBuilder.setMessage(R.string.delete_car_message)
                .setTitle(R.string.delete_car_title)
                .setPositiveButton("YES", (dialog, id) -> {
                    addedImageUrls.remove(carUri);
                    imgLl.removeView(view);
                })
                .setNegativeButton("NO", (dialog, id) -> createToast("FCar deletion cancelled"));
        alertBuilder.show();

    }

    /**
     * Process to insert a car
     * @param car  FCar to be inserted
     * @param view Context
     */
    // TODO delete when firebase works
    private void insertCar(Car car, View view) {
        CarRepository cr = CarRepository.getInstance();
        cr.insert(car, new OnAsyncInsertEventListener() {
            @Override
            public void onSuccessResult(Long id) {
                System.out.println("CAR ADDED :" + car.getUser() + car.getModel() + id);
                insertImages(id, addedImageUrls, view);
            }
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        }, view.getContext());
    }

    private void insertFCar(FCar car, View view) {
        CarRepo carRepo = CarRepo.getInstance();
        carRepo.insert(car, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                //Log.d(TAG, "createUserWithEmail: success");
                System.out.println("---------succes");
            }

            @Override
            public void onFailure(Exception e) {
                // Log.d(TAG, "createUserWithEmail: failure", e);
                System.out.println("---------fail");
            }
        });

    }

    /**
     * Insert images into database
     * @param carId id of the car
     * @param addedImageUrls list of uris
     * @param view current view
     */
    private void insertImages(long carId, List<String> addedImageUrls, View view) {
        ImageRepository ir = ImageRepository.getInstance();
        for (String imageUrl : addedImageUrls) {
            System.out.println(imageUrl);
            CarImage ci = new CarImage((int) carId, imageUrl);
            ir.insert(ci, new OnAsyncEventListener() {

                @Override
                public void onSuccess() {
                    successImageUpload = true;
                }

                @Override
                public void onFailure(Exception e) {
                    successImageUpload = false;
                }

            }, view.getContext());
        }
        if (successImageUpload = true)
            setResponse(true);
        else
            setResponse(false);
    }

    /**
     * Manages the response after inserting a car
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
