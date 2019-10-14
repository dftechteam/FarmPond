package df.farmponds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EditFarmPondDetails_Activity extends AppCompatActivity {


    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    EditText edit_ponddepth_et,edit_pondwidth_et,edit_pondheight_et;
    TextView edit_ponddetails_farmername_et;
    ImageView edit_pond_image1_iv,edit_pond_image2_iv,edit_pond_image3_iv;
    ImageButton edit_removeimage1_ib,edit_removeimage2_ib,edit_removeimage3_ib;

    Button edit_ponddetails_submit_bt,edit_ponddetails_cancel_bt;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String str_base64imagestring = "";
    ArrayList<String> arraylist_image1_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image2_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image3_base64 = new ArrayList<>();

    ArrayList<String> arraylist_image1_ID_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image2_ID_base64 = new ArrayList<>();
    ArrayList<String> arraylist_image3_ID_base64 = new ArrayList<>();

    String str_image1,str_image2,str_image3;
    String str_Is_image1,str_Is_image2,str_Is_image3;
    Bitmap mIcon11;

    Class_farmponddetails class_farmponddetails_obj;
    String str_farmpondbaseimage_url,str_cancelclicked;

    SharedPreferences sharedpref_farmerid_Obj;
    String str_farmerID;
    public static final String sharedpreferenc_farmerid = "sharedpreference_farmer_id";
    public static final String Key_FarmerID = "farmer_id";
    String str_farmpondimageurl;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farmponddetails);


        toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
        // Set upon the actionbar
        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        add_newfarmpond_iv=(ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
        title.setText("Edit FarmPond Details");
        getSupportActionBar().setTitle("");
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);
        add_newfarmpond_iv.setVisibility(View.GONE);


        sharedpref_farmerid_Obj=getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_farmerID = sharedpref_farmerid_Obj.getString(Key_FarmerID, "").trim();


    //fetch the details previous activity
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("jsonObject");
        Log.e("intent",jsonString);
        class_farmponddetails_obj = new Gson().fromJson(jsonString, Class_farmponddetails.class);
        //fetch the details previous activity

        String str_farmername =class_farmponddetails_obj.getFarmer_Name().toString() ;


        edit_ponddetails_farmername_et=(TextView)findViewById(R.id.edit_ponddetails_farmername_et);
        edit_pondwidth_et=(EditText)findViewById(R.id.edit_pondwidth_et);
        edit_pondheight_et=(EditText)findViewById(R.id.edit_pondheight_et);
        edit_ponddepth_et=(EditText)findViewById(R.id.edit_ponddepth_et);

        edit_pond_image1_iv=(ImageView)findViewById(R.id.edit_pond_image1_iv);
        edit_pond_image2_iv=(ImageView)findViewById(R.id.edit_pond_image2_iv);
        edit_pond_image3_iv=(ImageView)findViewById(R.id.edit_pond_image3_iv);




        edit_removeimage1_ib=(ImageButton) findViewById(R.id.edit_removeimage1_ib);
        edit_removeimage2_ib=(ImageButton) findViewById(R.id.edit_removeimage2_ib);
        edit_removeimage3_ib=(ImageButton) findViewById(R.id.edit_removeimage3_ib);

        edit_ponddetails_submit_bt=(Button)findViewById(R.id.edit_ponddetails_submit_bt);
        edit_ponddetails_cancel_bt=(Button)findViewById(R.id.edit_ponddetails_cancel_bt);




        str_image1=str_image2=str_image3="false";
        str_Is_image1=str_Is_image2=str_Is_image3="false";//for conversion to byte64 string
        str_cancelclicked="false";
        edit_ponddetails_farmername_et.setText(str_farmername);







       Assign_farmponddetails(); // Assign the data to fields


     //  Convert_urlimagetobyte64();



        edit_pond_image1_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_image1="true";
                selectImage();
            }
        });
        edit_pond_image2_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image2="true";
                selectImage();
            }
        });

        edit_pond_image3_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_image3="true";
                selectImage();
            }
        });



        edit_removeimage1_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                arraylist_image1_base64.clear();
                edit_removeimage1_ib.setVisibility(View.GONE);
                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                edit_pond_image1_iv.setImageDrawable(res);

            }
        });

        edit_removeimage2_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image2_base64.clear();
                edit_removeimage2_ib.setVisibility(View.GONE);

                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                edit_pond_image2_iv.setImageDrawable(res);
            }
        });
        edit_removeimage3_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraylist_image3_base64.clear();
                edit_removeimage3_ib.setVisibility(View.GONE);

                String str_imagefromdrawable = "@drawable/add_farmpond_image";
                int int_imageResource = getResources().getIdentifier(str_imagefromdrawable, null, getPackageName());
                Drawable res = getResources().getDrawable(int_imageResource);
                edit_pond_image3_iv.setImageDrawable(res);
            }
        });


        edit_ponddetails_submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(validation())
                {
                    AsyncTask_submit_edited_farmponddetails();
                }
            }
        });


        edit_ponddetails_cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_cancelclicked="true";
                onBackPressed();
            }
        });


    }// end of oncreate




 // End of assign
    public void Assign_farmponddetails()
    {
        edit_pondwidth_et.setText(class_farmponddetails_obj.getFarmpond_Width().toString());
        edit_pondheight_et.setText(class_farmponddetails_obj.getFarmpond_Height().toString());
        edit_ponddepth_et.setText(class_farmponddetails_obj.getFarmpond_Depth().toString());

        if(class_farmponddetails_obj.getClass_farmpondimages_obj().size()>0)
        {
            for(int j=0;j<class_farmponddetails_obj.getClass_farmpondimages_obj().size();j++)
            {

                Log.e("edit_imageurl", str_farmpondbaseimage_url + class_farmponddetails_obj.getClass_farmpondimages_obj().get(j).getImage_url().toString());

                str_farmpondimageurl=str_farmpondbaseimage_url + class_farmponddetails_obj.getClass_farmpondimages_obj().get(j).getImage_url().toString();


                if (j == 0)
                {

                    Picasso.get()
                            .load(str_farmpondimageurl)
                            .into(edit_pond_image1_iv, new Callback() {
                                @Override
                                public void onSuccess()
                                {

                                    edit_removeimage1_ib.setVisibility(View.VISIBLE);
                                    arraylist_image1_ID_base64.add(class_farmponddetails_obj.getClass_farmpondimages_obj().get(0).getImage_ID().toString());


                                  /*  str_Is_image1="true";
                                    AsyncCallWS_imagetobyte64 task = new AsyncCallWS_imagetobyte64(EditFarmPondDetails_Activity.this);
                                    task.execute();*/


                                  /*  try {
                                        URL url = new URL(str_farmpondimageurl);
                                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                        connection.setDoInput(true);
                                        connection.connect();
                                        InputStream input = connection.getInputStream();
                                        Bitmap myBitmap = BitmapFactory.decodeStream(input);

                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        myBitmap.compress(Bitmap.CompressFormat.PNG, 60, baos);
                                        byte[] byteArray = baos.toByteArray();
                                        str_base64imagestring = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                        arraylist_image1_base64.add(str_base64imagestring);

                                    } catch (IOException e) {

                                    }*/


                                }
                                @Override
                                public void onError(Exception e) {
                                }
                            });
                }
                if(j==1)
            {
                Picasso.get()
                        .load(str_farmpondimageurl)
                        .into(edit_pond_image2_iv, new Callback() {
                            @Override
                            public void onSuccess() {
                                edit_removeimage2_ib.setVisibility(View.VISIBLE);
                                arraylist_image2_ID_base64.add(class_farmponddetails_obj.getClass_farmpondimages_obj().get(1).getImage_ID().toString());


                             /*   str_Is_image2="true";
                                AsyncCallWS_imagetobyte64 task = new AsyncCallWS_imagetobyte64(EditFarmPondDetails_Activity.this);
                                task.execute();*/
                            }
                            @Override
                            public void onError(Exception e) {
                            }
                        });
            }
                if(j==2)
                {
                    Picasso.get()
                            .load(str_farmpondimageurl)
                            .into(edit_pond_image3_iv, new Callback() {
                                @Override
                                public void onSuccess() {
                                    edit_removeimage3_ib.setVisibility(View.VISIBLE);
                                    arraylist_image3_ID_base64.add(class_farmponddetails_obj.getClass_farmpondimages_obj().get(2).getImage_ID().toString());

                                    Log.e("imageid",class_farmponddetails_obj.getClass_farmpondimages_obj().get(2).getImage_ID().toString());
                                  /* str_Is_image3="true";
                                    AsyncCallWS_imagetobyte64 task = new AsyncCallWS_imagetobyte64(EditFarmPondDetails_Activity.this);
                                    task.execute();*/

                                }
                                @Override
                                public void onError(Exception e) {
                                }
                            });
                }
            } // end for 1
        }// end if 2
        else{

        }

        Convert_urlimagetobyte64();

    }
// End of assign


    public boolean validation()
    {
        boolean b_pond_width1,b_pond_width2,b_pond_height1,b_pond_height2,b_pond_depth1,b_pond_depth2,b_pondimages;

        b_pond_width1=b_pond_width2=b_pond_height1=b_pond_height2=b_pond_depth1=b_pond_depth2=b_pondimages=true;

        if (edit_pondwidth_et.getText().toString().length() == 0) {
            edit_pondwidth_et.setError("Empty not allowed");
            edit_pondwidth_et.requestFocus();
            b_pond_width1=false;
        }

        if (edit_pondwidth_et.getText().toString().length() <= 1) {
            edit_pondwidth_et.setError("Enter Valid Width");
            edit_pondwidth_et.requestFocus();
            b_pond_width2=false;
        }

        if (edit_pondheight_et.getText().toString().length() == 0) {
            edit_pondheight_et.setError("Empty not allowed");
            edit_pondheight_et.requestFocus();
            b_pond_height1=false;
        }
        if (edit_pondheight_et.getText().toString().length() <= 1) {
            edit_pondheight_et.setError("Enter Valid Height");
            edit_pondheight_et.requestFocus();
            b_pond_height2=false;
        }


        if (edit_ponddepth_et.getText().toString().length() == 0) {
            edit_ponddepth_et.setError("Empty not allowed");
            edit_ponddepth_et.requestFocus();
            b_pond_depth1=false;
        }

        if (edit_ponddepth_et.getText().toString().length() <= 1) {
            edit_ponddepth_et.setError("Enter Valid Depth");
            edit_ponddepth_et.requestFocus();
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
    private void AsyncTask_submit_edited_farmponddetails()
    {

        final ProgressDialog pdLoading = new ProgressDialog(EditFarmPondDetails_Activity.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();



        String str_fetchfarmponddetails_url = Class_URL.URL_Edit_farmponddetails.toString().trim();

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
                        Toast.makeText(EditFarmPondDetails_Activity.this,"WS:"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {



                Map<String,String> params = new HashMap<String, String>();



                String str_imageID_1,str_imageID_2,str_imageID_3;

               if(arraylist_image1_ID_base64.size()>0)
               { str_imageID_1=arraylist_image1_ID_base64.get(0).toString();}
               else{ str_imageID_1="";}

                if(arraylist_image2_ID_base64.size()>0)
                { str_imageID_2=arraylist_image2_ID_base64.get(0).toString();}
                else{ str_imageID_2="";}

                if(arraylist_image3_ID_base64.size()>0)
                { str_imageID_3=arraylist_image3_ID_base64.get(0).toString();}
                else{ str_imageID_3="";}

                Log.e("imageid1",str_imageID_1);
                Log.e("imageid2",str_imageID_2);
                Log.e("imageid3",str_imageID_3);


               try{


                params.put("Farmer_ID",str_farmerID); // farmerID from previous screen
                params.put("Width",edit_pondwidth_et.getText().toString());
                params.put("Height",edit_pondheight_et.getText().toString());
                params.put("Depth",edit_ponddepth_et.getText().toString());
                params.put("Latitude","");
                params.put("Longitude","");

                   params.put("image_id1",str_imageID_1);
                   params.put("image_id2",str_imageID_2);
                   params.put("image_id3",str_imageID_3);

                params.put("image_link_1",arraylist_image1_base64.get(0).toString());
                params.put("image_link_2",arraylist_image2_base64.get(0).toString());
                params.put("image_link_3",arraylist_image3_base64.get(0).toString());




               }
               catch (Exception e)
               {
                   Log.e("upload error", e.getMessage());
                   e.printStackTrace();
               }

                //Log.e("image", arraylist_image1_base64.get(0).toString());
              Log.e("Edit request", String.valueOf(params));
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

                Toast.makeText(getApplicationContext(),"Edition Updated Successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditFarmPondDetails_Activity.this,EachFarmPondDetails_Activity.class);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(EditFarmPondDetails_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Class_utility.checkPermission(EditFarmPondDetails_Activity.this);
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


    //selection of gallery on hold
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
        //add_newpond_image1_iv.setImageBitmap(bm);
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
            str_image1="false";
            arraylist_image1_base64.clear();
        edit_pond_image1_iv.setImageBitmap(bitmap_thumbnail);
        edit_removeimage1_ib.setVisibility(View.VISIBLE);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);

            arraylist_image1_base64.add(str_base64imagestring);
           // BitMapToString(thumbnail);
        }
        if(str_image2.equals("true"))
        {
            arraylist_image2_base64.clear();
            edit_pond_image2_iv.setImageBitmap(bitmap_thumbnail);
            edit_removeimage2_ib.setVisibility(View.VISIBLE);
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
            arraylist_image3_base64.clear();
            edit_pond_image3_iv.setImageBitmap(bitmap_thumbnail);
            edit_removeimage3_ib.setVisibility(View.VISIBLE);
            str_image3="false";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_thumbnail.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
            arraylist_image3_base64.add(str_base64imagestring);
           // BitMapToString(bitmap_thumbnail);
        }
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

        AlertDialog.Builder dialog = new AlertDialog.Builder(EditFarmPondDetails_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.app_name);

        if(str_cancelclicked.equalsIgnoreCase("true")) {
            dialog.setMessage("Are you sure want to exit from Edit");
        }else{
            dialog.setMessage("Are you sure want to go back");
        }

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent i = new Intent(EditFarmPondDetails_Activity.this, EachFarmPondDetails_Activity.class);
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




    public void Convert_urlimagetobyte64() {

        if (class_farmponddetails_obj.getClass_farmpondimages_obj().size() > 0) {
            for (int j = 0; j < class_farmponddetails_obj.getClass_farmpondimages_obj().size(); j++)
            {

                Log.e("imageurl", str_farmpondbaseimage_url + class_farmponddetails_obj.getClass_farmpondimages_obj().get(j).getImage_url().toString());

                str_farmpondimageurl = str_farmpondbaseimage_url + class_farmponddetails_obj.getClass_farmpondimages_obj().get(j).getImage_url().toString();

                if(j==0) {
                    str_Is_image1 = "true";
                    AsyncCallWS_imagetobyte64 task = new AsyncCallWS_imagetobyte64(EditFarmPondDetails_Activity.this);
                    task.execute();
                }else if(j==1)
                {
                    str_Is_image2 = "true";
                    AsyncCallWS_imagetobyte64 task = new AsyncCallWS_imagetobyte64(EditFarmPondDetails_Activity.this);
                    task.execute();
                }else if(j==2)
                {
                    str_Is_image3 = "true";
                    AsyncCallWS_imagetobyte64 task = new AsyncCallWS_imagetobyte64(EditFarmPondDetails_Activity.this);
                    task.execute();
                }
            }

        }
    }



    private class AsyncCallWS_imagetobyte64 extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("DFFarmPond", "doInBackground");


            urltobyte64();


            return null;
        }

        public AsyncCallWS_imagetobyte64(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();


          /*  if(str_Is_image1.equalsIgnoreCase("true"))
            {

                str_Is_image1="false";

                arraylist_image1_base64.clear();
                edit_pond_image1_iv.setImageBitmap(mIcon11);
                edit_removeimage1_ib.setVisibility(View.VISIBLE);
                str_image1 = "false";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mIcon11.compress(Bitmap.CompressFormat.PNG, 60, baos);
                byte[] b = baos.toByteArray();
                str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
                arraylist_image1_base64.add(str_base64imagestring);


            }else if(str_Is_image2.equalsIgnoreCase("true"))
            {

                str_Is_image2="false";

                arraylist_image2_base64.clear();
                edit_pond_image2_iv.setImageBitmap(mIcon11);
                edit_removeimage2_ib.setVisibility(View.VISIBLE);
                str_image2 = "false";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mIcon11.compress(Bitmap.CompressFormat.PNG, 60, baos);
                byte[] b = baos.toByteArray();
                str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
                arraylist_image2_base64.add(str_base64imagestring);

            }
            else if(str_Is_image3.equalsIgnoreCase("true"))
            {
                str_Is_image3="false";

                arraylist_image3_base64.clear();
                edit_pond_image3_iv.setImageBitmap(mIcon11);
                edit_removeimage3_ib.setVisibility(View.VISIBLE);
                str_image3 = "false";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mIcon11.compress(Bitmap.CompressFormat.PNG, 60, baos);
                byte[] b = baos.toByteArray();
                str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
                arraylist_image3_base64.add(str_base64imagestring);
            }else{

            }
*/

        }//end of OnPostExecute

    }// end Async task


    public void urltobyte64()
    {

        try {

            InputStream in = new java.net.URL(str_farmpondimageurl).openStream();
            Bitmap mIcon12 = BitmapFactory.decodeStream(in);


            if(str_Is_image1.equalsIgnoreCase("true"))
            {

                str_Is_image1="false";

                arraylist_image1_base64.clear();

                /*edit_pond_image1_iv.setImageBitmap(mIcon11);
                edit_removeimage1_ib.setVisibility(View.VISIBLE);*/
                str_image1 = "false";

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                byte[] b = baos.toByteArray();
                str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
                arraylist_image1_base64.add(str_base64imagestring);




            }else if(str_Is_image2.equalsIgnoreCase("true"))
            {

                str_Is_image2="false";

                arraylist_image2_base64.clear();

                /*edit_pond_image2_iv.setImageBitmap(mIcon11);
                edit_removeimage2_ib.setVisibility(View.VISIBLE);*/
                str_image2 = "false";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                byte[] b = baos.toByteArray();
                str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
                arraylist_image2_base64.add(str_base64imagestring);

            }
            else if(str_Is_image3.equalsIgnoreCase("true"))
            {
                str_Is_image3="false";

                arraylist_image3_base64.clear();

                /*edit_pond_image3_iv.setImageBitmap(mIcon11);
                edit_removeimage3_ib.setVisibility(View.VISIBLE);*/
                str_image3 = "false";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mIcon12.compress(Bitmap.CompressFormat.PNG, 60, baos);
                byte[] b = baos.toByteArray();
                str_base64imagestring = Base64.encodeToString(b, Base64.DEFAULT);
                arraylist_image3_base64.add(str_base64imagestring);
            }

        } catch (Exception e) {
            Log.e("image Error", e.getMessage());
            e.printStackTrace();
        }
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
