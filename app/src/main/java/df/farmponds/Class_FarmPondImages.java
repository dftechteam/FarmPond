package df.farmponds;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//"images":[{"image_id":"10","image_link":"image\/5d91c1f2b9b50.png"}]
public class Class_FarmPondImages
{



    @SerializedName("image_id")
    @Expose
    private String Image_ID;

    @SerializedName("image_link")
    @Expose
    private String Image_url;


    public String getImage_ID() {
        return Image_ID;
    }

    public void setImage_ID(String image_ID) {
        Image_ID = image_ID;
    }

    public String getImage_url() {
        return Image_url;
    }

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }

    public String toString()
    {
        return( this.Image_url );
    }



}
