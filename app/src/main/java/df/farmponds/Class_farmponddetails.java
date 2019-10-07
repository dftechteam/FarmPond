package df.farmponds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
/*{
        "farmer_id": "1",
        "farmerpond_id": "1",
        "farmer_name": "Surender Reddy",
        "width": "40",
        "height": "40",
        "depth": "10",
        "image-1": "null",
        "image-2": "null",
        "image-3": "null"
        },*/


//"images":[{"image_id":"10","image_link":"image\/5d91c1f2b9b50.png"}]

public class Class_farmponddetails
{

    @SerializedName("farmer_id")
    @Expose
    private String Farmer_Id;

    @SerializedName("farmerpond_id")
    @Expose
    private String Farmpond_Id;

    @SerializedName("farmer_name")
    @Expose
    private String Farmer_Name;

    @SerializedName("width")
    @Expose
    private String Farmpond_Width;


    @SerializedName("height")
    @Expose
    private String Farmpond_Height;

    @SerializedName("depth")
    @Expose
    private String Farmpond_Depth;


    @SerializedName("images")
    @Expose
    private List<Class_FarmPondImages> class_farmpondimages_obj = new ArrayList<Class_FarmPondImages>();


    public String getfarmer_id() {
        return Farmer_Id;
    }

    public void setfarmer_id(String farmer_id) {
        Farmer_Id = farmer_id;
    }

    public String getFarmpond_Id() {
        return Farmpond_Id;
    }

    public void setFarmpond_Id(String farmpond_Id) {
        Farmpond_Id = farmpond_Id;
    }

    public String getFarmer_Name() {
        return Farmer_Name;
    }

    public void setFarmer_Name(String farmer_Name) {
        Farmer_Name = farmer_Name;
    }

    public String getFarmpond_Width() {
        return Farmpond_Width;
    }

    public void setFarmpond_Width(String farmpond_Width) {
        Farmpond_Width = farmpond_Width;
    }

    public String getFarmpond_Height() {
        return Farmpond_Height;
    }

    public void setFarmpond_Height(String farmpond_Height) {
        Farmpond_Height = farmpond_Height;
    }

    public String getFarmpond_Depth() {
        return Farmpond_Depth;
    }

    public void setFarmpond_Depth(String farmpond_Depth) {
        Farmpond_Depth = farmpond_Depth;
    }


    public List<Class_FarmPondImages> getClass_farmpondimages_obj() {
        return class_farmpondimages_obj;
    }

    public void setClass_farmpondimages_obj(List<Class_FarmPondImages> class_farmpondimages_obj) {
        this.class_farmpondimages_obj = class_farmpondimages_obj;
    }
}
