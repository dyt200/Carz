package com.example.carz.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.google.firebase.database.Exclude;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Entity(
    tableName = "car",
    foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "user",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("user")}
)
public class Car {
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    @ColumnInfo(name = "manufacturer")
    private int manufacturer;

    @ColumnInfo(name = "type")
    private int type;

    @ColumnInfo(name = "user")
    private String user;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "mileage")
    private int mileage;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "condition")
    private String condition;

    @ColumnInfo(name = "images")
    private List<String> images;

    public Car() {
    }

    @Ignore
    public Car(int type, int manufacturer, String user, int price, int year, int mileage, String model, String description, String condition, List<String> images) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.user = user;
        this.price = price;
        this.year = year;
        this.mileage = mileage;
        this.model = model;
        this.description = description;
        this.condition = condition;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) { this.type = type; }

    public String getUser() { return user; }

    public void setUser(String user) {
        this.user = user;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getModel() { return model; }

    public void setModel(String model) { this.model = model; }

    public String getTitle() {
        return getManufacturerString()+" "+getTypeString()+" "+model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    //Returns the mileage in Swiss style
    @Exclude
    public String getFormattedMileageString() {
        String mileString = NumberFormat.getNumberInstance(Locale.UK).format(mileage) + " km";
        return mileString.replaceAll(",", "'");
    }

    //Returns the price in Swiss style and in CHF
    @Exclude
    public String getFormattedPriceString() {
        String priceString = NumberFormat.getNumberInstance(Locale.UK).format(price) + ".- CHF";
        return priceString.replaceAll(",", "'");
    }

    //returns the name of the manufacturer
    @Exclude
    public String getManufacturerString() {
        String string;
        switch(this.manufacturer) {
            case 1:     string = "Skoda";   break;
            case 2:     string = "BMW";     break;
            case 3:     string = "Opel";    break;
            case 4:     string = "Audi";    break;
            case 5:     string = "VW";      break;
            case 6:     string = "Mercedes-Benz";   break;
            case 7:     string = "Alfa Romeo";   break;
            case 8:     string = "Tesla";   break;
            case 9:     string = "Toyota";   break;
            case 10:    string = "Suzuki";   break;
            case 11:    string = "Subaru";   break;
            case 12:    string = "Porsche";   break;
            case 13:    string = "Mitsubishi";   break;
            case 14:    string = "Kia";   break;
            case 15:    string = "Jeep";   break;
            case 16:    string = "Honda";   break;
            case 17:    string = "Hyundai";   break;
            default:    string = "ERROR";
        }
        return string;
    }

    //returns the name of the type
    @Exclude
    public String getTypeString() {
        String string;
        switch(this.type) {
            case 1:     string = "Hatchback";   break;
            case 2:     string = "Sedan";     break;
            case 3:     string = "SUV";    break;
            case 4:     string = "Crossover";    break;
            case 5:     string = "Coupe";    break;
            case 6:     string = "Convertible";    break;
            default:    string = "ERROR";
        }
        return string;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("type", type);
        result.put("manufacturer", manufacturer);
        result.put("user", user);
        result.put("year", year);
        result.put("mileage", mileage);
        result.put("model", model);
        result.put("description", description);
        result.put("condition", condition);
        result.put("price", price);
        result.put("images", images);
        return result;
    }

}
