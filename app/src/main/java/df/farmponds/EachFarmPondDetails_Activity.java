package df.farmponds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.BaseAdapter;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class EachFarmPondDetails_Activity extends AppCompatActivity {


    Toolbar toolbar;
    ImageView add_newfarmpond_iv;

    Class_response_farmponddetails  class_response_farmponddetails_object;
    Class_farmponddetails[] class_farmponddetails_array_obj;

    ArrayList<Class_response_farmponddetails> farmpond_List = new ArrayList<>();
    private ListView farmpondlist_listview;

    String str_farmpondbaseimage_url;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachfarmponddetails);


        toolbar = (Toolbar) findViewById(R.id.toolbar_farmponddetails);
        // Set upon the actionbar
        setSupportActionBar(toolbar);
        // Now use actionbar methods to show navigation icon and title
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        str_farmpondbaseimage_url = Class_URL.URL_farmpondbaselink.toString().trim();
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        add_newfarmpond_iv=(ImageView) toolbar.findViewById(R.id.add_newfarmpond_iv);
        title.setText("FarmPond Details");
        getSupportActionBar().setTitle("");
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorwhite), PorterDuff.Mode.SRC_ATOP);


        farmpondlist_listview = (ListView) findViewById(R.id.farmpondlist_listview);

        AsyncTask_fetch_farmponddetails();




        add_newfarmpond_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent_addfarmpondactivity = new Intent(EachFarmPondDetails_Activity.this,AddFarmPondDetails_Activity.class);
                intent_addfarmpondactivity.putExtra("farmername", class_farmponddetails_array_obj[0].getFarmer_Name().toString());
                startActivity(intent_addfarmpondactivity);
                finish();

            }
        });

    }// end of oncreate




    //volley asynctask
    private void AsyncTask_fetch_farmponddetails()
    {

        final ProgressDialog pdLoading = new ProgressDialog(EachFarmPondDetails_Activity.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();



        String str_fetchfarmponddetails_url = Class_URL.URL_fetchfarmponddetails.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_fetchfarmponddetails_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pdLoading.dismiss();

                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.e("volley response",response);
                        parse_farmpondresponse(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading.dismiss();
                        Toast.makeText(EachFarmPondDetails_Activity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("farmer_id","1");

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void parse_farmpondresponse(String response)
    {


        /*"Farmers": [
        {
            "farmer_id": "1",
                "farmerpond_id": "1",
                "farmer_name": "Surender Reddy",
                "width": "40",
                "height": "40",
                "depth": "10",
                "image-1": "null",
                "image-2": "null",
                "image-3": "null"
        },
         ],
    "statusMessage": "Success"
}*/

       /* {"Farmers":
            [
               {
                   "farmer_id":"1",
                   "farmerpond_id":"1",
                   "farmer_name":"Surender Reddy",
                   "width":"40",
                   "height":"40",
                   "depth":"10",
                   "images":[{"image_id":"10","image_link":"image\/5d91c1f2b9b50.png"}
                   ]
               },{ }]
            "statusMessage": "Success"
        }*/

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Success"))
            {

                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();

                JSONArray response_jsonarray = jsonObject.getJSONArray("Farmers");
                Log.e("framer_array", String.valueOf(response_jsonarray.length()));

                int int_jsonarraylength=response_jsonarray.length();

                class_farmponddetails_array_obj = new Class_farmponddetails[int_jsonarraylength];

                for(int i=0;i<int_jsonarraylength;i++)
                {
                    Class_farmponddetails class_farmponddetails_innerobj = new Gson().fromJson(String.valueOf(response_jsonarray.get(i).toString()), Class_farmponddetails.class);
                    class_farmponddetails_array_obj[i]=class_farmponddetails_innerobj;

                    //Incase of Offline
                   /* if(class_farmponddetails_innerobj.getClass_farmpondimages_obj().size()>0)
                    {
                        for(int j=0;j<class_farmponddetails_innerobj.getClass_farmpondimages_obj().size();j++)
                        {

                        }

                    }*/
                    //Incase of Offline


                }

                if (class_farmponddetails_array_obj != null)
                {
                    CustomAdapter adapter = new CustomAdapter();
                    farmpondlist_listview.setAdapter(adapter);

                    int x = class_farmponddetails_array_obj.length;

                    System.out.println("Inside the if list adapter" + x);
                } else {
                    Log.d("onPostExecute", "class_farmponddetails_array_obj == null");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error",e.toString());
        }

    }






    private class Holder {
        TextView holder_farmername;
        TextView holder_pondwidth;
        TextView holder_pondheight;
        TextView holder_ponddepth;
        TextView holder_farmer_id;
        TextView holder_farmpond_id;
        ImageView holder_farmpond_image1;
        ImageView holder_farmpond_image2;
        ImageView holder_farmpond_image3;
    }



    public class CustomAdapter extends BaseAdapter {


        public CustomAdapter() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(class_farmponddetails_array_obj.length);
            System.out.println("class_farmponddetails_array_obj.length" + x);
            return class_farmponddetails_array_obj.length;
        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            System.out.println("getItem position" + x);
            Log.d("getItem position", "x");
            return class_farmponddetails_array_obj[position];
        }


        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);
            System.out.println("getItemId position" + x);
            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Holder holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_row_item_farmponddetails, parent, false);



                //


                holder.holder_farmername = (TextView) convertView.findViewById(R.id.farmername_tv);
                holder.holder_pondheight = (TextView) convertView.findViewById(R.id.pond_height);
                holder.holder_pondwidth = (TextView) convertView.findViewById(R.id.pond_width);
                holder.holder_ponddepth = (TextView) convertView.findViewById(R.id.pond_depth);

                holder.holder_farmpond_image1 = (ImageView) convertView.findViewById(R.id.farmpondimage1_iv);
                holder.holder_farmpond_image2 = (ImageView) convertView.findViewById(R.id.farmpondimage2_iv);
                holder.holder_farmpond_image3 = (ImageView) convertView.findViewById(R.id.farmpondimage3_iv);
                holder.holder_farmer_id = (TextView) convertView.findViewById(R.id.farmer_id_tv);
                holder.holder_farmpond_id = (TextView) convertView.findViewById(R.id.farmpond_id_tv);

                Log.d("Inside If convertView", "Inside If convertView");

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
                Log.d("Inside else convertView", "Inside else convertView");
            }

            Class_farmponddetails farmponddetails_obj = (Class_farmponddetails) getItem(position);



            if (farmponddetails_obj != null)
            {
                holder.holder_farmername.setText(farmponddetails_obj.getFarmer_Name());
                holder.holder_pondwidth.setText(farmponddetails_obj.getFarmpond_Width());
                holder.holder_pondheight.setText(farmponddetails_obj.getFarmpond_Height());
                holder.holder_ponddepth.setText(farmponddetails_obj.getFarmpond_Depth());

                if(farmponddetails_obj.getClass_farmpondimages_obj().size()>0)
                {
                    for(int j=0;j<farmponddetails_obj.getClass_farmpondimages_obj().size();j++)
                    {

                        Log.e("imageurl", str_farmpondbaseimage_url + farmponddetails_obj.getClass_farmpondimages_obj().get(j).getImage_url().toString());

                        String str_farmpondimageurl=str_farmpondbaseimage_url + farmponddetails_obj.getClass_farmpondimages_obj().get(j).getImage_url().toString();

                        if (j == 0)
                        {
                            Picasso.get()
                                    .load(str_farmpondimageurl)
                                    .into(holder.holder_farmpond_image1, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }
                                        @Override
                                        public void onError(Exception e) {
                                        }
                                    });
                        }if(j==1)
                        {
                            Picasso.get()
                                    .load(str_farmpondimageurl)
                                    .into(holder.holder_farmpond_image2, new Callback() {
                                        @Override
                                        public void onSuccess() {
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
                                    .into(holder.holder_farmpond_image3, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }
                                        @Override
                                        public void onError(Exception e) {
                                        }
                                    });
                        }
                    } // end for 1
                }// end if 2




            }// end if 1

            return convertView;

        }//End of custom getView
    }//End of CustomAdapter

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            // Toast.makeText(CalenderActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

                SaveSharedPreference.setUserName(getApplicationContext(),"");

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("logout_key1", "yes");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

//                Intent i = new Intent(getApplicationContext(), NormalLogin.class);
//                i.putExtra("logout_key1", "yes");
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
                //finish();
                //}
            }
            else{
                Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if(id== android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EachFarmPondDetails_Activity.this, Activity_HomeScreen.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(EachFarmPondDetails_Activity.this, ViewFarmers.class);
        startActivity(i);
        finish();

    }



}//end of class
