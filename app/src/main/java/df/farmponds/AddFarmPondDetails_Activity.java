package df.farmponds;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class AddFarmPondDetails_Activity extends AppCompatActivity {


    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    EditText add_newpond_width_et,add_newpond_height_et,add_newpond_depth_et;
    TextView add_newpond_farmername_et;
    ImageView add_newpond_image1_iv,add_newpond_image2_iv,add_newpond_image3_iv;
    ImageButton removeimage1_ib,removeimage2_ib,removeimage3_ib;
    Button add_ponddetails_submit_bt,add_ponddetails_cancel_bt;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String str_base64imagestring = "";
    ArrayList<String> arraylist_image1_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image2_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image3_base64 = new ArrayList<>();

    String str_image1,str_image2,str_image3,str_cancelclicked;

    String str_farmer_id;


    Class_GPSTracker gpstracker_obj1,gpstracker_obj2;
    Double double_currentlatitude=0.0;
    Double double_currentlongitude=0.0;
    String str_latitude,str_longitude;


    TextView latitude_tv,longitude_tv;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;


    static Class_alert_msg obj_class_alert_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_farmponddetails);


        toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
        // Set upon the actionbar
        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        add_newfarmpond_iv=(ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
        title.setText("Add FarmPond Details");
        getSupportActionBar().setTitle("");
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);
        add_newfarmpond_iv.setVisibility(View.GONE);

        String str_farmername = getIntent().getStringExtra("farmername");
        str_farmer_id=getIntent().getStringExtra("farmer_id");



        add_newpond_farmername_et=(TextView)findViewById(R.id.add_newpond_farmername_et);
        add_newpond_width_et=(EditText)findViewById(R.id.add_newpond_width_et);
        add_newpond_height_et=(EditText)findViewById(R.id.add_newpond_height_et);
        add_newpond_depth_et=(EditText)findViewById(R.id.add_newpond_depth_et);


        add_newpond_image1_iv=(ImageView)findViewById(R.id.add_newpond_image1_iv);
       add_newpond_image2_iv=(ImageView)findViewById(R.id.add_newpond_image2_iv);
        add_newpond_image3_iv=(ImageView)findViewById(R.id.add_newpond_image3_iv);


       removeimage1_ib=(ImageButton) findViewById(R.id.removeimage1_ib);
       removeimage2_ib=(ImageButton) findViewById(R.id.removeimage2_ib);
       removeimage3_ib=(ImageButton) findViewById(R.id.removeimage3_ib);

        add_ponddetails_submit_bt=(Button)findViewById(R.id.add_ponddetails_submit_bt);
       add_ponddetails_cancel_bt=(Button)findViewById(R.id.add_ponddetails_cancel_bt);

        longitude_tv=(TextView)findViewById(R.id.longitude_tv);
        latitude_tv=(TextView)findViewById(R.id.latitude_tv);


        str_image1=str_image2=str_image3="false";
        str_cancelclicked="false";
        add_newpond_farmername_et.setText(str_farmername);

        obj_class_alert_msg = new Class_alert_msg(getApplicationContext());

        gpstracker_obj2 = new Class_GPSTracker(AddFarmPondDetails_Activity.this);
        if(gpstracker_obj2.canGetLocation())
        {
            double_currentlatitude = gpstracker_obj2.getLatitude();
            double_currentlongitude = gpstracker_obj2.getLongitude();


            str_latitude =Double.toString(double_currentlatitude);
            str_longitude =Double.toString(double_currentlongitude);


            latitude_tv.setText(str_latitude);
            longitude_tv.setText(str_longitude);

            Log.e("lat",str_latitude);
            Log.e("long",str_longitude);

            if(str_latitude.equals("0.0")||str_longitude.equals("0.0"))
            {
                alertdialog_refresh_latandlong();
            }


        }else
        {
            gpstracker_obj2.showSettingsAlert();
        }

        checkthecount();

        add_ponddetails_submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               if(validation())
               {
                   gpstracker_obj1 = new Class_GPSTracker(AddFarmPondDetails_Activity.this);
                   if(gpstracker_obj1.canGetLocation())
                   {
                       double_currentlatitude = gpstracker_obj1.getLatitude();
                       double_currentlongitude = gpstracker_obj1.getLongitude();

                       str_latitude =Double.toString(double_currentlatitude);
                       str_longitude =Double.toString(double_currentlongitude);


                       latitude_tv.setText(str_latitude);
                       longitude_tv.setText(str_longitude);


                       internetDectector = new Class_InternetDectector(getApplicationContext());
                       isInternetPresent = internetDectector.isConnectingToInternet();

                       if (isInternetPresent) {

                           AsyncTask_Add_farmponddetails();

                       }
                       {

                           insert_into_farmpondsDB();
                           Toast.makeText(getApplicationContext(),"No Internet Data Stored in your Mobile",Toast.LENGTH_SHORT).show();


                           Intent intent = new Intent(AddFarmPondDetails_Activity.this,EachFarmPondDetails_Activity.class);
                           startActivity(intent);
                           finish();
                       }
                   }
                   else{
                       gpstracker_obj1.showSettingsAlert();
                   }
               }
            }
        });


        add_ponddetails_cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_cancelclicked="true";
                onBackPressed();
            }
        });


        add_newpond_image1_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_image1="true";
                selectImage();
            }
        });
        add_newpond_image2_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image2="true";
                selectImage();
            }
        });

        add_newpond_image3_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image3="true";
                selectImage();
            }
        });

        removeimage1_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                arraylist_image1_base64.clear();
                removeimage1_ib.setVisibility(View.GONE);
                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                add_newpond_image1_iv.setImageDrawable(res);

            }
        });

        removeimage2_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image2_base64.clear();
                removeimage2_ib.setVisibility(View.GONE);

                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                add_newpond_image2_iv.setImageDrawable(res);
            }
        });
        removeimage3_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image3_base64.clear();
                removeimage3_ib.setVisibility(View.GONE);

                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                add_newpond_image3_iv.setImageDrawable(res);
            }
        });


    }// end of oncreate


    public boolean validation()
    {
        boolean b_pond_width1,b_pond_width2,b_pond_height1,b_pond_height2,b_pond_depth1,b_pond_depth2,b_pondimages;

        b_pond_width1=b_pond_width2=b_pond_height1=b_pond_height2=b_pond_depth1=b_pond_depth2=b_pondimages=true;

        if (add_newpond_width_et.getText().toString().length() == 0) {
            add_newpond_width_et.setError("Empty not allowed");
            add_newpond_width_et.requestFocus();
            b_pond_width1=false;
        }


        if (add_newpond_width_et.getText().toString().length() <= 1||(Integer.parseInt(add_newpond_width_et.getText().toString())<0)) {
            add_newpond_width_et.setError("Enter Valid Width");
            add_newpond_width_et.requestFocus();
            b_pond_width2=false;
        }

        if (add_newpond_height_et.getText().toString().length() == 0) {
            add_newpond_height_et.setError("Empty not allowed");
            add_newpond_height_et.requestFocus();
            b_pond_height1=false;
        }
        if (add_newpond_height_et.getText().toString().length() <= 1||(Integer.parseInt(add_newpond_height_et.getText().toString())<0)) {
            add_newpond_height_et.setError("Enter Valid Height");
            add_newpond_height_et.requestFocus();
            b_pond_height2=false;
        }


        if (add_newpond_depth_et.getText().toString().length() == 0) {
            add_newpond_depth_et.setError("Empty not allowed");
            add_newpond_depth_et.requestFocus();
            b_pond_depth1=false;
        }

        if (add_newpond_depth_et.getText().toString().length() <= 1||(Integer.parseInt(add_newpond_depth_et.getText().toString())<0)) {
            add_newpond_depth_et.setError("Enter Valid Depth");
            add_newpond_depth_et.requestFocus();
            b_pond_depth2=false;
        }

        if(arraylist_image1_base64.size()==0||arraylist_image2_base64.size()==0||arraylist_image3_base64.size()==0)
        {
            Toast.makeText(getApplication(),"Add the Pond Images",Toast.LENGTH_LONG).show();
            b_pondimages=false;
        }

        return ( b_pond_width1&&b_pond_width2&&b_pond_height1&&b_pond_height2&&b_pond_depth1&&b_pond_depth2&&b_pondimages);
    }


    //volley asynctask
    private void AsyncTask_Add_farmponddetails()
    {

        final ProgressDialog pdLoading = new ProgressDialog(AddFarmPondDetails_Activity.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();



        String str_fetchfarmponddetails_url = Class_URL.URL_Add_farmponddetails.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pdLoading.dismiss();

                        Log.e("volley response",response);//volley response: {"statusMessage":"success"}
                        parse_add_newfarmpond_response(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading.dismiss();
                        Toast.makeText(AddFarmPondDetails_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();


                /*String str_image1=arraylist_image1_base64.get(0).toString();
                String str_image2=arraylist_image2_base64.get(0).toString();
                String str_image3=arraylist_image3_base64.get(0).toString();*/

                params.put("Farmer_ID",str_farmer_id); // farmerID from previous screen
                params.put("Width",add_newpond_width_et.getText().toString());
                params.put("Height",add_newpond_height_et.getText().toString());
                params.put("Depth",add_newpond_depth_et.getText().toString());
                params.put("Latitude",str_latitude);
                params.put("Longitude",str_longitude);
                params.put("image_link_1",arraylist_image1_base64.get(0).toString());
                params.put("image_link_2",arraylist_image2_base64.get(0).toString());
                params.put("image_link_3",arraylist_image3_base64.get(0).toString());

                //Log.e("image", arraylist_image1_base64.get(0).toString());
              //Log.e("requesrt", String.valueOf(params));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.e("request",stringRequest.toString());
        requestQueue.add(stringRequest);
    }

    public void parse_add_newfarmpond_response(String response)
    {
     //volley response: {"statusMessage":"success"}
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Success"))
            {

                Intent intent = new Intent(AddFarmPondDetails_Activity.this,EachFarmPondDetails_Activity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplication(),jsonObject.getString("statusMessage").toString(),Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error",e.toString());
        }

    }




//images

    private void selectImage()
    {
        /*final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};*/
        final CharSequence[] items = {"Take Photo","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddFarmPondDetails_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Class_utility.checkPermission(AddFarmPondDetails_Activity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Class_utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                BitMapToString(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        add_newpond_image1_iv.setImageBitmap(bm);
       /* if(isInternetPresent){
            SaveFarmerImage();
        }else{

            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }*/

    }
    private void onCaptureImageResult(Intent data)
    {
        Bitmap bitmap_thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap_thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(str_image1.equals("true"))
        {
        add_newpond_image1_iv.setImageBitmap(bitmap_thumbnail);
        removeimage1_ib.setVisibility(View.VISIBLE);
        str_image1="false";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

            arraylist_image1_base64.add(str_base64imagestring);
           // BitMapToString(thumbnail);
        }
        if(str_image2.equals("true"))
        {
            add_newpond_image2_iv.setImageBitmap(bitmap_thumbnail);
            removeimage2_ib.setVisibility(View.VISIBLE);
            str_image2="false";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image2_base64.add(str_base64imagestring);
            //BitMapToString(thumbnail);
        }
        if(str_image3.equals("true"))
        {
            add_newpond_image3_iv.setImageBitmap(bitmap_thumbnail);
            removeimage3_ib.setVisibility(View.VISIBLE);
            str_image3="false";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image3_base64.add(str_base64imagestring);
           // BitMapToString(bitmap_thumbnail);
        }

//
        /*if(isInternetPresent){
            SaveFarmerImage();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
    }



    public String BitMapToString(Bitmap userImage1)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

        return str_base64imagestring;
    }

//images




    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(AddFarmPondDetails_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Are you sure want to go back");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(AddFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
                startActivity(i);
                finish();
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();
    }





    public void alertdialog_refresh_latandlong() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(AddFarmPondDetails_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Click Ok to fetch latitude and Longitude");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                Intent i = new Intent(AddFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
                startActivity(i);
                finish();
            }
        });

        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
              //  alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();
    }






    public void insert_into_farmpondsDB()
    {

        String str_width_4rdb=add_newpond_width_et.getText().toString();
        String str_height_4rdb=add_newpond_height_et.getText().toString();
        String str_depth_4rdb=add_newpond_depth_et.getText().toString();
        DBCreate_Farmpondsedetails_toserver_2SQLiteDB(str_width_4rdb,str_height_4rdb,str_depth_4rdb);
    }


    public void DBCreate_Farmpondsedetails_toserver_2SQLiteDB(String str_width_4rdb,String str_height_4rdb,String str_depth_4rdb)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("NewFarmPondDetails_DB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS NewFarmPondDetails_toServer(FID VARCHAR,WidthDB VARCHAR," +
                "HeightDB VARCHAR,DepthDB VARCHAR,LatDB VARCHAR,LongDB VARCHAR);");

        String SQLiteQuery = "INSERT INTO NewFarmPondDetails_toServer (FID,WidthDB,HeightDB,DepthDB,LatDB,LongDB)" +
                " VALUES ('"+str_farmer_id+"','"+str_width_4rdb+"','"+str_height_4rdb+"','"+str_depth_4rdb+"'," +
                "'"+str_latitude+"','"+str_longitude+"');";
        db1.execSQL(SQLiteQuery);
        db1.close();
    }







    public void checkthecount()
    {

        SQLiteDatabase db6 = openOrCreateDatabase("NewFarmPondDetails_DB", Context.MODE_PRIVATE, null);
        db6.execSQL("CREATE TABLE IF NOT EXISTS NewFarmPondDetails_toServer(FID VARCHAR,WidthDB VARCHAR," +
                "HeightDB VARCHAR,DepthDB VARCHAR,LatDB VARCHAR,LongDB VARCHAR);");

        Cursor cursor1 = db6.rawQuery("SELECT * FROM NewFarmPondDetails_toServer", null);
        int x = cursor1.getCount();

        Log.e("dbcount", String.valueOf(x));

        db6.close();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
           /* Intent i = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
            startActivity(i);
             finish();*/
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}//end of class
