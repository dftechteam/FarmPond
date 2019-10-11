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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import static com.df.farmpond.MainActivity.key_loginuserid;
//import static com.df.farmpond.MainActivity.sharedpreferenc_loginuserid;

public class Activity_ViewFarmers extends AppCompatActivity {

    public static final String sharedpreferenc_studentid = "studentid_edit";
    public static final String key_studentid = "str_studentID_edit";
    public static final String sharedpreferenc_studentid_pay = "studentid";
    public static final String key_studentid_pay = "str_studentID";
    private static final String JSON_LocationURL = "http://apps.dfindia.org/farmapi/location.php";
    private static final String JSON_FarmerDetailsURL = "http://apps.dfindia.org/farmapi/farm_list.php";
    private static final String JSON_FarmerImageURL = "http://apps.dfindia.org/farmapi/farmer_image.php";

    public static final String sharedpreferenc_camera = "cameraflag";
    public static final String key_flagcamera = "flag_camera";


    public static String compressedfilepaths = "";
    static Boolean digitalcamerabuttonpressed = false;
    static byte[] signimageinbytesArray = {0};
    public Context context;
    ListView farmer_listview;
    EditText searchStudent_et;
    Button search_BT;
    String str_loginuserID;
    Boolean isInternetPresent = false;
    Class_InternetDectector internetDectector;
    String str_searchStudent_et;
    int count1;
  /*  Class_StudentListDetails Objclass_studentListDetails;
    Class_StudentListDetails[] Arrayclass_studentListDetails;*/
    LinearLayout studentlist_LL, Nostudentlist_LL, application_LL, mainstuName_LL;
    ImageButton details_show_ib;
    // CustomAdapter adapter;
    Spinner yearlist_SP, statelist_SP, districtlist_SP, taluklist_SP, villagelist_SP, grampanchayatlist_SP;
  /*  Class_SandBoxDetails[] arrayObj_Class_sandboxDetails, arrayObj_Class_sandboxDetails2;
    Class_SandBoxDetails obj_Class_sandboxDetails;
    Class_academicDetails[] arrayObj_Class_academicDetails, arrayObj_Class_academicDetails2;
    Class_academicDetails obj_Class_academicDetails;
    Class_ClusterDetails[] arrayObj_Class_clusterDetails, arrayObj_Class_clusterDetails2;
    Class_ClusterDetails obj_Class_clusterDetails;
    Class_InsituteDetails[] arrayObj_Class_InstituteDetails, arrayObj_Class_InstituteDetails2;
    Class_InsituteDetails obj_Class_instituteDetails;
    Class_LevelDetails[] arrayObj_Class_LevelDetails, arrayObj_Class_LevelDetails2;
    Class_LevelDetails obj_Class_levelDetails;
    Class_StudentStatus[] arrayObj_Class_StudentStatus, arrayObj_Class_StudentStatus2;
    Class_StudentStatus Obj_Class_StudentStatus;
    Class_ViewStudentData[] arrayObj_Class_ViewStudentData, arrayObj_Class_ViewStudentData2;
    Class_ViewStudentData Obj_Class_ViewStudentData;*/
    String selected_studentstatus = "", sp_strsand_ID, selected_sandboxName,
            sp_straca_ID, selected_academicname, sp_strClust_ID, selected_clusterName,
            sp_strInst_ID, selected_instituteName, sp_strLev_ID, selected_levelName;
    LinearLayout spinnerlayout_ll;
    ImageButton search_ib, downarrow_ib, uparrow_ib;
    TextView viewspinner_tv;
    TableLayout tableLayout;
   // SoapPrimitive response_soapobj_studentStatus;
    boolean issubmitclickedOnAlertDialog = false;
    SharedPreferences sharedpref_loginuserid_Obj;
    SharedPreferences sharedpref_stuid_Obj;
    SharedPreferences sharedpref_stuid_pay_Obj;
    String str_studentid_sharedpreference, str_studentid_pay_sharedpreference;
    ////////////////////////////////ViewFarmer modifications sept 16th2019
    AutoCompleteTextView searchautofill;
    InputMethodManager imm;
    String[] FarmerNameArray;
    int intval_searchflag = 0, search_pos;
    String str_selected;
    LinearLayout mainviewfarmerlist_ll, farmernameandcodelist_ll, farmerimagelayout_ll, nofarmerRecords_ll;
    Toolbar toolbar;
   // ShowSearchView showSearchView;
    EditText search_et;
    FarmerListViewAdapter farmerListViewAdapter;
    ViewHolder holder;
    Class_YearListDetails[] arrayObj_Class_yearDetails, arrayObj_Class_yearDetails2;
    Class_YearListDetails Obj_Class_yearDetails;
    Class_StateListDetails[] arrayObj_Class_stateDetails, arrayObj_Class_stateDetails2;
    Class_StateListDetails Obj_Class_stateDetails;
    Class_DistrictListDetails[] arrayObj_Class_DistrictListDetails, arrayObj_Class_DistrictListDetails2;
    Class_DistrictListDetails Obj_Class_DistrictDetails;
    Class_TalukListDetails[] arrayObj_Class_TalukListDetails, arrayObj_Class_TalukListDetails2;
    Class_TalukListDetails Obj_Class_TalukDetails;
    Class_VillageListDetails[] arrayObj_Class_VillageListDetails, arrayObj_Class_VillageListDetails2;
    Class_VillageListDetails Obj_Class_VillageListDetails;
    Class_GrampanchayatListDetails[] arrayObj_Class_GrampanchayatListDetails, arrayObj_Class_GrampanchayatListDetails2;
    Class_GrampanchayatListDetails Obj_Class_GramanchayatDetails;
    Class_FarmerListDetails[] arrayObj_Class_FarmerListDetails, arrayObj_Class_FarmerListDetails2;
    Class_FarmerListDetails Obj_Class_FarmerListDetails;
    String selected_year, sp_stryear_ID, sp_strstate_ID, selected_district, selected_stateName, sp_strdistrict_ID, sp_strdistrict_state_ID, sp_strTaluk_ID, selected_taluk, sp_strVillage_ID, selected_village, sp_strgrampanchayat_ID, selected_grampanchayat;
    String sp_strstate_ID_new = "";
    String mCurrentPhotoPath = "", str_flagforcamera;
    Bitmap bitmap;
    String str_img = "";
    SharedPreferences sharedpref_camera_Obj;
    Uri imageUri = null;
    Bitmap scaledBitmap = null;
    HttpHandler sh;
    String str_status_msg;
    String[] StrArray_farmerimage,StrArray_farmerlist_panchayatid, StrArray_farmerlist_villageid, StrArray_farmerlist_talukid, StrArray_farmerlist_distid, StrArray_farmerlist_stateid, StrArray_farmerlist_yearid, StrArray_farmerlist_farmerid, StrArray_year_ID, StrArray_year, strArray_farmername, StrArray_farmercode, strArray_stateid, strArray_statename, strArray_districtid, strArray_districtname, strArray_selecteddistname, strArray_Dist_stateid, strArray_taluk_distid, strArray_talukid, strArray_talukname, strArray_village_talukid, strArray_villageid, strArray_villagename, strArray_panchayat_distid, strArray_panchayatid, strArray_panchayatname;
    List<Class_StateListDetails> stateList;
    List<Class_DistrictListDetails> districtList;
    List<Class_TalukListDetails> talukList;
    List<Class_VillageListDetails> villageList;
    List<Class_GrampanchayatListDetails> panchayatList;
    List<Class_FarmerListDetails> farmerList;
    List<Class_YearListDetails> yearList;
    private ArrayList<Class_FarmerListDetails> originalViewFarmerList = null;
    private ArrayList<Class_FarmerListDetails> ViewFarmerList_arraylist;


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String str_selected_farmerID,str_selected_farmerID_forimagesaving;

    public static final String sharedpreferenc_farmerid = "sharedpreference_farmer_id";
    public static final String Key_FarmerID = "farmer_id";
    SharedPreferences sharedpref_farmerid_Obj;

    private ImageLoadingListener imageListener;
    public DisplayImageOptions displayoption;
    ImageLoader imgLoader = ImageLoader.getInstance();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_farmers);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Farmer List");
        getSupportActionBar().setTitle("");
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Farmer List");*/
        initImageLoader(getApplicationContext());
        displayoption = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.profileimg)
                .showStubImage(R.drawable.profileimg)
                .showImageForEmptyUri(R.drawable.profileimg).cacheInMemory()
                .cacheOnDisc().build();

        imageListener = new ImageDisplayListener();


        sh = new HttpHandler();
        stateList = new ArrayList<>();
        districtList = new ArrayList<>();
        talukList = new ArrayList<>();
        villageList = new ArrayList<>();
        panchayatList = new ArrayList<>();
        farmerList = new ArrayList<>();
        yearList = new ArrayList<>();

        ViewFarmerList_arraylist = new ArrayList<Class_FarmerListDetails>();
        farmerListViewAdapter = new FarmerListViewAdapter(Activity_ViewFarmers.this, ViewFarmerList_arraylist);

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

//        sharedpref_loginuserid_Obj = getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpref_farmerid_Obj = getSharedPreferences(sharedpreferenc_farmerid, Context.MODE_PRIVATE);
        str_selected_farmerID = sharedpref_farmerid_Obj.getString(Key_FarmerID, "").trim();


        sharedpref_stuid_Obj = getSharedPreferences(sharedpreferenc_studentid, Context.MODE_PRIVATE);
        str_studentid_sharedpreference = sharedpref_stuid_Obj.getString(key_studentid, "").trim();


        sharedpref_stuid_pay_Obj = getSharedPreferences(sharedpreferenc_studentid_pay, Context.MODE_PRIVATE);
        str_studentid_pay_sharedpreference = sharedpref_stuid_pay_Obj.getString(key_studentid_pay, "").trim();

        sharedpref_camera_Obj = getSharedPreferences(sharedpreferenc_camera, Context.MODE_PRIVATE);
        str_flagforcamera = sharedpref_camera_Obj.getString(key_flagcamera, "").trim();


        //   searchautofill = (AutoCompleteTextView) findViewById(R.id.Search);
        //  searchautofill.setInputType(InputType.TYPE_NULL);
//        Typeface typefacereg = Typeface.createFromAsset(getAssets(), "fonts/laouiregular.ttf");
//        searchautofill.setTypeface(typefacereg);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout_stulist);

        farmer_listview = (ListView) findViewById(R.id.farmer_LISTVIEW);
        yearlist_SP = (Spinner) findViewById(R.id.yearlist_farmer_SP);
        statelist_SP = (Spinner) findViewById(R.id.statelist_farmer_SP);
        districtlist_SP = (Spinner) findViewById(R.id.districtlist_farmer_SP);
        taluklist_SP = (Spinner) findViewById(R.id.taluklist_farmer_SP);
        villagelist_SP = (Spinner) findViewById(R.id.villagelist_farmer_SP);
        grampanchayatlist_SP = (Spinner) findViewById(R.id.grampanchayatlist_farmer_SP);
        spinnerlayout_ll = (LinearLayout) findViewById(R.id.spinnerlayout_LL);
        viewspinner_tv = (TextView) findViewById(R.id.viewspinner_TV);

        downarrow_ib = (ImageButton) findViewById(R.id.downarrow_IB);
        uparrow_ib = (ImageButton) findViewById(R.id.uparrow_IB);

        search_et = (EditText) findViewById(R.id.search_et);

        search_et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = search_et.getText().toString().toLowerCase(Locale.getDefault());
                farmerListViewAdapter.filter(text, originalViewFarmerList);

            }
        });


        uparrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Hide");
                spinnerlayout_ll.setVisibility(View.VISIBLE);
                downarrow_ib.setVisibility(View.VISIBLE);
                uparrow_ib.setVisibility(View.GONE);

            }
        });

        downarrow_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewspinner_tv.setText("Show");
                spinnerlayout_ll.setVisibility(View.GONE);
                downarrow_ib.setVisibility(View.GONE);
                uparrow_ib.setVisibility(View.VISIBLE);

            }
        });
   /*     @SuppressLint("ResourceType")
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_in);
        animation1.setDuration(1000);
        spinnerlayout_ll.setAnimation(animation1);
        spinnerlayout_ll.animate();
        animation1.start();*/


//        if (isInternetPresent) {
//            deleteYearTable_B4insertion();
//            deleteStateTable_B4insertion();
//            deleteDistrictTable_B4insertion();
//            deleteTalukTable_B4insertion();
//            deleteVillageTable_B4insertion();
//            deleteGrampanchayatTable_B4insertion();
//            deleteViewFarmerlistTable_B4insertion();
//
//
//            GetDropdownValues();
//
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//        }
//
//        if (isInternetPresent) {
//
//            GetFarmerDetails();
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
//
//        }

        yearlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_yearDetails = (Class_YearListDetails) yearlist_SP.getSelectedItem();
                sp_stryear_ID = Obj_Class_yearDetails.getYearID().toString();
                selected_year = yearlist_SP.getSelectedItem().toString();
                // Update_stateid_spinner(sp_stryear_ID);
                //  Log.e("sp_stryear_ID", " : " + sp_stryear_ID);

                //Update_ids_farmerlist_listview(sp_stryear_ID,"","","","","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        statelist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Obj_Class_stateDetails = (Class_StateListDetails) statelist_SP.getSelectedItem();
                sp_strstate_ID = Obj_Class_stateDetails.getState_id().toString();
                selected_stateName = statelist_SP.getSelectedItem().toString();

                Update_districtid_spinner(sp_strstate_ID);
                //  Log.e("selected_stateName", " : " + selected_stateName);
                // Log.e("sp_strstate_ID", " : " + sp_strstate_ID);
                // Update_ids_farmerlist_listview(sp_stryear_ID,sp_strstate_ID,"","","","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        districtlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_DistrictDetails = (Class_DistrictListDetails) districtlist_SP.getSelectedItem();
                sp_strdistrict_ID = Obj_Class_DistrictDetails.getDistrict_id();
                sp_strdistrict_state_ID = Obj_Class_DistrictDetails.getState_id();
                selected_district = districtlist_SP.getSelectedItem().toString();

//                Log.i("selected_district", " : " + selected_district);
//                Log.i("sp_strdistrict_state_ID", " : " + sp_strdistrict_state_ID);
                //Log.e("sp_strdistrict_ID", " : " + sp_strdistrict_ID);
                // Log.i("sp_strstate_ID", " : " + sp_strstate_ID);

                Update_TalukId_spinner(sp_strdistrict_ID);
                // Update_TalukId_spinner("5623");
                Update_GramPanchayatID_spinner(sp_strdistrict_ID);
                // Update_ids_farmerlist_listview(sp_stryear_ID,sp_strstate_ID,sp_strdistrict_ID,"","","");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        taluklist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_TalukDetails = (Class_TalukListDetails) taluklist_SP.getSelectedItem();
                sp_strTaluk_ID = Obj_Class_TalukDetails.getTaluk_id();
                selected_taluk = taluklist_SP.getSelectedItem().toString();

                // Update_VillageId_spinner("5433");//5516,sp_strTaluk_ID
//                Log.i("selected_taluk", " : " + selected_taluk);
//
//                Log.e("sp_stryear_ID..", sp_stryear_ID);
//                Log.e("sp_strstate_ID..", sp_strstate_ID);
//                Log.e("sp_strdistrict_ID..", sp_strdistrict_ID);
                //Log.e("sp_strTaluk_ID..", sp_strTaluk_ID);
                Update_VillageId_spinner(sp_strTaluk_ID);
                // Update_ids_farmerlist_listview(sp_stryear_ID,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,"","");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        villagelist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_VillageListDetails = (Class_VillageListDetails) villagelist_SP.getSelectedItem();
                sp_strVillage_ID = Obj_Class_VillageListDetails.getVillage_id();
                selected_village = villagelist_SP.getSelectedItem().toString();
                // Log.i("selected_village", " : " + selected_village);

                //  Update_ids_farmerlist_listview(sp_stryear_ID,sp_strstate_ID,sp_strdistrict_ID,sp_strTaluk_ID,sp_strVillage_ID,"");

                // Update_ids_farmerlist_listview("3","1","539","5700","626790","60");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        grampanchayatlist_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Obj_Class_GramanchayatDetails = (Class_GrampanchayatListDetails) grampanchayatlist_SP.getSelectedItem();
                sp_strgrampanchayat_ID = Obj_Class_GramanchayatDetails.getGramanchayat_id().toString();
                selected_grampanchayat = Obj_Class_GramanchayatDetails.getGramanchayat_name().toString();

                Log.e("sp_stryear_ID..", sp_stryear_ID);
                Log.e("sp_strstate_ID..", sp_strstate_ID);
                Log.e("sp_strdistrict_ID..", sp_strdistrict_ID);
                Log.e("sp_strTaluk_ID..", sp_strTaluk_ID);
                Log.e("sp_strVillage_ID..", sp_strVillage_ID);
                Log.e("sp_strpachayat_ID..", sp_strgrampanchayat_ID);
//                Log.e("selected_grampanchayat", selected_grampanchayat);

                //Update_ViewFarmerListID_spinner(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID,sp_strVillage_ID);


                //Update_ids_farmerlist_listview("1","25","635","5663","626707","18");

                Update_ids_farmerlist_listview(sp_stryear_ID, sp_strstate_ID, sp_strdistrict_ID, sp_strTaluk_ID, sp_strVillage_ID, sp_strgrampanchayat_ID);
                // Update_ids_farmerlist_listview("2019","1","539","5700","626790","60");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        farmer_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                str_selected_farmerID = originalViewFarmerList.get(position).getFarmerid();
                Log.e("str_selected_farmerID", str_selected_farmerID);
                Intent i = new Intent(Activity_ViewFarmers.this, EachFarmPondDetails_Activity.class);
                SharedPreferences.Editor myprefs_farmerid = sharedpref_farmerid_Obj.edit();
                myprefs_farmerid.putString(Key_FarmerID, str_selected_farmerID);
                myprefs_farmerid.apply();
                startActivity(i);
               // finish();

            }
        });

        uploadfromDB_Yearlist();
        uploadfromDB_Statelist();
        uploadfromDB_Districtlist();
        uploadfromDB_Taluklist();
        uploadfromDB_Villagelist();
        uploadfromDB_Grampanchayatlist();
        uploadfromDB_Farmerlist();


    }//oncreate

    public static void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.profileimg)
                .showImageOnFail(R.drawable.profileimg)
                .resetViewBeforeLoading().cacheOnDisc()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(options)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);
    }







    /////////////////////////////sept18th2019//////////////////////////////////
    public void DBCreate_Yeardetails_insert_2SQLiteDB(String str_yearID, String str_yearname) {
        SQLiteDatabase db_yearlist = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_yearlist.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR);");


        String SQLiteQuery = "INSERT INTO YearList (YearID, YearName)" +
                " VALUES ('" + str_yearID + "','" + str_yearname + "');";
        db_yearlist.execSQL(SQLiteQuery);


        //Log.e("str_yearID DB", str_yearID);
        // Log.e("str_yearname DB", str_yearname);
        db_yearlist.close();
    }


    public void deleteYearTable_B4insertion() {

        SQLiteDatabase db_yearlist_delete = openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_yearlist_delete.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR);");
        Cursor cursor = db_yearlist_delete.rawQuery("SELECT * FROM YearList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_yearlist_delete.delete("YearList", null, null);

        }
        db_yearlist_delete.close();
    }


    public void DBCreate_Statedetails_insert_2SQLiteDB(String str_stateID, String str_statename, String str_yearid) {
        SQLiteDatabase db_statelist = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_statelist.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");


        String SQLiteQuery = "INSERT INTO StateList (StateID,StateName,state_yearid)" +
                " VALUES ('" + str_stateID + "','" + str_statename + "','" + str_yearid + "');";
        db_statelist.execSQL(SQLiteQuery);

//        Log.e("str_stateID DB", str_stateID);
//        Log.e("str_statename DB", str_statename);
//        Log.e("str_stateyearid DB", str_yearid);
        db_statelist.close();
    }

    public void deleteStateTable_B4insertion() {

        SQLiteDatabase db_statelist_delete = openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_statelist_delete.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor = db_statelist_delete.rawQuery("SELECT * FROM StateList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_statelist_delete.delete("StateList", null, null);

        }
        db_statelist_delete.close();
    }


    public void DBCreate_Districtdetails_insert_2SQLiteDB(String str_districtID, String str_districtname, String str_yearid, String str_stateid) {
        SQLiteDatabase db_districtlist = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_districtlist.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");


        String SQLiteQuery = "INSERT INTO DistrictList (DistrictID, DistrictName,Distr_yearid,Distr_Stateid)" +
                " VALUES ('" + str_districtID + "','" + str_districtname + "','" + str_yearid + "','" + str_stateid + "');";
        db_districtlist.execSQL(SQLiteQuery);

//        Log.e("str_districtID DB", str_districtID);
//        Log.e("str_districtname DB", str_districtname);
//        Log.e("str_yearid DB", str_yearid);
//        Log.e("str_stateid DB", str_stateid);
        db_districtlist.close();
    }

    public void deleteDistrictTable_B4insertion() {

        SQLiteDatabase db_districtlist_delete = openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_districtlist_delete.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db_districtlist_delete.rawQuery("SELECT * FROM DistrictList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_districtlist_delete.delete("DistrictList", null, null);

        }
        db_districtlist_delete.close();
    }


    public void DBCreate_Talukdetails_insert_2SQLiteDB(String str_talukID, String str_talukname, String str_districtid) {

        SQLiteDatabase db_taluklist = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_taluklist.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");


        String SQLiteQuery = "INSERT INTO TalukList (TalukID, TalukName,Taluk_districtid)" +
                " VALUES ('" + str_talukID + "','" + str_talukname + "','" + str_districtid + "');";
        db_taluklist.execSQL(SQLiteQuery);

//        Log.e("str_talukID DB", str_talukID);
//        Log.e("str_talukname DB", str_talukname);
//        Log.e("str_districtid DB", str_districtid);
        db_taluklist.close();
    }

    public void deleteTalukTable_B4insertion() {

        SQLiteDatabase db_taluklist_delete = openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_taluklist_delete.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db_taluklist_delete.rawQuery("SELECT * FROM TalukList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_taluklist_delete.delete("TalukList", null, null);

        }
        db_taluklist_delete.close();
    }

    public void DBCreate_Villagedetails_insert_2SQLiteDB(String str_villageID, String str_village, String str_talukid) {
        SQLiteDatabase db_villagelist = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_villagelist.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");


        String SQLiteQuery = "INSERT INTO VillageList (VillageID, Village,TalukID)" +
                " VALUES ('" + str_villageID + "','" + str_village + "','" + str_talukid + "');";
        db_villagelist.execSQL(SQLiteQuery);

//        Log.e("str_villageID DB", str_villageID);
//        Log.e("str_village DB", str_village);
//        Log.e("str_talukid DB", str_talukid);
        db_villagelist.close();
    }

    public void deleteVillageTable_B4insertion() {

        SQLiteDatabase db_villagelist_delete = openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_villagelist_delete.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        Cursor cursor1 = db_villagelist_delete.rawQuery("SELECT * FROM VillageList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_villagelist_delete.delete("VillageList", null, null);

        }
        db_villagelist_delete.close();
    }

    public void DBCreate_Grampanchayatdetails_insert_2SQLiteDB(String str_grampanchayatID, String str_grampanchayat, String str_distid) {

        SQLiteDatabase db_panchayatlist = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_panchayatlist.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatList(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");


        String SQLiteQuery = "INSERT INTO GrampanchayatList (GramanchayatID, Gramanchayat,Panchayat_DistID)" +
                " VALUES ('" + str_grampanchayatID + "','" + str_grampanchayat + "','" + str_distid + "');";
        db_panchayatlist.execSQL(SQLiteQuery);
        // }
//        Log.e("str_panchayatID DB", str_grampanchayatID);
//        Log.e("str_panchayat DB", str_grampanchayat);
//        Log.e("str_distid DB", str_distid);
        db_panchayatlist.close();
    }

    public void deleteGrampanchayatTable_B4insertion() {

        SQLiteDatabase db_grampanchayatlist_delete = openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_grampanchayatlist_delete.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatList(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db_grampanchayatlist_delete.rawQuery("SELECT * FROM GrampanchayatList", null);
        int x = cursor1.getCount();

        if (x > 0) {
            db_grampanchayatlist_delete.delete("GrampanchayatList", null, null);

        }
        db_grampanchayatlist_delete.close();
    }


    public void DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(String str_yearID, String str_stateID, String str_districtID, String str_talukid, String str_villageid, String str_grampanchayatid, String str_farmerid, String str_farmercode, String str_farmername, String str_farmerimage) {
        SQLiteDatabase db_viewfarmerlist = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_viewfarmerlist.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerList(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR,DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR,DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR,DispFarmerTable_FarmerName VARCHAR,DispFarmerTable_FarmerImage VARCHAR);");


        String SQLiteQuery = "INSERT INTO ViewFarmerList (DispFarmerTable_YearID,DispFarmerTable_StateID, DispFarmerTable_DistrictID,DispFarmerTable_TalukID,DispFarmerTable_VillageID,DispFarmerTable_GrampanchayatID,DispFarmerTable_FarmerID,DispFarmerTable_Farmer_Code,DispFarmerTable_FarmerName,DispFarmerTable_FarmerImage)" +
                " VALUES ('" + str_yearID + "','" + str_stateID + "','" + str_districtID + "','" + str_talukid + "','" + str_villageid + "','" + str_grampanchayatid + "','" + str_farmerid + "','" + str_farmercode + "','" + str_farmername + "','" + str_farmerimage + "');";
        db_viewfarmerlist.execSQL(SQLiteQuery);


//        Log.e("str_yearID DB", str_yearID);
//        Log.e("str_stateID DB", str_stateID);
//        Log.e("str_districtID DB", str_districtID);
//        Log.e("str_talukid DB", str_talukid);
//        Log.e("str_villageid DB", str_villageid);
//        Log.e("str_grampanchayatid DB", str_grampanchayatid);
//        Log.e("str_farmerid DB", str_farmerid);
//        Log.e("str_farmername DB", str_farmername);
//        Log.e("str_farmerimage DB", str_farmerimage);
        db_viewfarmerlist.close();
    }

    public void deleteViewFarmerlistTable_B4insertion() {

        SQLiteDatabase db_viewfarmerlist_delete = openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_viewfarmerlist_delete.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerList(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR,DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR,DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR,DispFarmerTable_FarmerName VARCHAR,DispFarmerTable_FarmerImage VARCHAR);");
        Cursor cursor = db_viewfarmerlist_delete.rawQuery("SELECT * FROM ViewFarmerList", null);
        int x = cursor.getCount();

        if (x > 0) {
            db_viewfarmerlist_delete.delete("ViewFarmerList", null, null);

        }
        db_viewfarmerlist_delete.close();
    }

    public void uploadfromDB_Yearlist() {

        SQLiteDatabase db_year = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_year.execSQL("CREATE TABLE IF NOT EXISTS YearList(YearID VARCHAR,YearName VARCHAR);");
        Cursor cursor = db_year.rawQuery("SELECT DISTINCT * FROM YearList", null);
        int x = cursor.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_yearDetails2 = new Class_YearListDetails[x];
        if (cursor.moveToFirst()) {

            do {
                Class_YearListDetails innerObj_Class_yearList = new Class_YearListDetails();
                innerObj_Class_yearList.setYearID(cursor.getString(cursor.getColumnIndex("YearID")));
                innerObj_Class_yearList.setYear(cursor.getString(cursor.getColumnIndex("YearName")));


                arrayObj_Class_yearDetails2[i] = innerObj_Class_yearList;
                i++;

            } while (cursor.moveToNext());


        }//if ends

        db_year.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_yearDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            yearlist_SP.setAdapter(dataAdapter);
        }

    }


    public void uploadfromDB_Statelist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new Class_StateListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_StateListDetails innerObj_Class_SandboxList = new Class_StateListDetails();
                innerObj_Class_SandboxList.setState_id(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_SandboxList.setState_name(cursor1.getString(cursor1.getColumnIndex("StateName")));
                innerObj_Class_SandboxList.setYear_id(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            statelist_SP.setAdapter(dataAdapter);
        }

    }

    public void Update_stateid_spinner(String str_yearids) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS StateList(StateID VARCHAR,StateName VARCHAR,state_yearid VARCHAR);");
//        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList WHERE YearId='" + str_yearids + "'", null);
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList WHERE state_yearid='" + str_yearids + "'", null);


        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_stateDetails2 = new Class_StateListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_StateListDetails innerObj_Class_AcademicList = new Class_StateListDetails();
                innerObj_Class_AcademicList.setState_id(cursor1.getString(cursor1.getColumnIndex("StateID")));
                innerObj_Class_AcademicList.setState_name(cursor1.getString(cursor1.getColumnIndex("StateName")));
                innerObj_Class_AcademicList.setYear_id(cursor1.getString(cursor1.getColumnIndex("state_yearid")));


                arrayObj_Class_stateDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            statelist_SP.setAdapter(dataAdapter);
        }

    }


    public void uploadfromDB_Districtlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new Class_DistrictListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_DistrictListDetails innerObj_Class_SandboxList = new Class_DistrictListDetails();
                innerObj_Class_SandboxList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_SandboxList.setDistrict_name(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_SandboxList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_SandboxList.setState_id(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            districtlist_SP.setAdapter(dataAdapter);
        }

    }

    public void Update_districtid_spinner(String str_stateid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS DistrictList(DistrictID VARCHAR,DistrictName VARCHAR,Distr_yearid VARCHAR,Distr_Stateid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM DistrictList WHERE Distr_Stateid='" + str_stateid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_DistrictListDetails2 = new Class_DistrictListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_DistrictListDetails innerObj_Class_AcademicList = new Class_DistrictListDetails();
                innerObj_Class_AcademicList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("DistrictID")));
                innerObj_Class_AcademicList.setDistrict_name(cursor1.getString(cursor1.getColumnIndex("DistrictName")));
                //innerObj_Class_AcademicList.setYear_id(cursor1.getString(cursor1.getColumnIndex("Distr_yearid")));
                innerObj_Class_AcademicList.setState_id(cursor1.getString(cursor1.getColumnIndex("Distr_Stateid")));


                arrayObj_Class_DistrictListDetails2[i] = innerObj_Class_AcademicList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            districtlist_SP.setAdapter(dataAdapter);
        }

    }

    public void uploadfromDB_Taluklist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_TalukListDetails innerObj_Class_SandboxList = new Class_TalukListDetails();
                innerObj_Class_SandboxList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_SandboxList.setTaluk_name(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                innerObj_Class_SandboxList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                arrayObj_Class_TalukListDetails2[i] = innerObj_Class_SandboxList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluklist_SP.setAdapter(dataAdapter);
        }

    }


    public void Update_TalukId_spinner(String str_distid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS TalukList(TalukID VARCHAR,TalukName VARCHAR,Taluk_districtid VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM TalukList WHERE Taluk_districtid='" + str_distid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_TalukListDetails innerObj_Class_talukList = new Class_TalukListDetails();
                innerObj_Class_talukList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));
                innerObj_Class_talukList.setTaluk_name(cursor1.getString(cursor1.getColumnIndex("TalukName")));
                innerObj_Class_talukList.setDistrict_id(cursor1.getString(cursor1.getColumnIndex("Taluk_districtid")));


                arrayObj_Class_TalukListDetails2[i] = innerObj_Class_talukList;
                //Log.e("taluk_name",cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            taluklist_SP.setAdapter(dataAdapter);
        }

    }

    public void uploadfromDB_Villagelist() {

        SQLiteDatabase db_village = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_village.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        Cursor cursor1 = db_village.rawQuery("SELECT DISTINCT * FROM VillageList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_VillageListDetails innerObj_Class_villageList = new Class_VillageListDetails();
                innerObj_Class_villageList.setVillage_id(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillage_name(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));


                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_village.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_SP.setAdapter(dataAdapter);
        }

    }

    public void Update_VillageId_spinner(String str_talukid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS VillageList(VillageID VARCHAR,Village VARCHAR,TalukID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM VillageList WHERE TalukID='" + str_talukid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_VillageListDetails innerObj_Class_villageList = new Class_VillageListDetails();
                innerObj_Class_villageList.setVillage_id(cursor1.getString(cursor1.getColumnIndex("VillageID")));
                innerObj_Class_villageList.setVillage_name(cursor1.getString(cursor1.getColumnIndex("Village")));
                innerObj_Class_villageList.setTaluk_id(cursor1.getString(cursor1.getColumnIndex("TalukID")));


                arrayObj_Class_VillageListDetails2[i] = innerObj_Class_villageList;
                // Log.e("village_name", cursor1.getString(cursor1.getColumnIndex("TalukName")));
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            villagelist_SP.setAdapter(dataAdapter);

        }

    }

    public void uploadfromDB_Grampanchayatlist() {

        SQLiteDatabase db_grampanchayat = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db_grampanchayat.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatList(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db_grampanchayat.rawQuery("SELECT DISTINCT * FROM GrampanchayatList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_GrampanchayatListDetails innerObj_Class_panchayatList = new Class_GrampanchayatListDetails();
                innerObj_Class_panchayatList.setGramanchayat_id(cursor1.getString(cursor1.getColumnIndex("GramanchayatID")));
                innerObj_Class_panchayatList.setGramanchayat_name(cursor1.getString(cursor1.getColumnIndex("Gramanchayat")));
                innerObj_Class_panchayatList.setDistrictid(cursor1.getString(cursor1.getColumnIndex("Panchayat_DistID")));


                arrayObj_Class_GrampanchayatListDetails2[i] = innerObj_Class_panchayatList;
                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db_grampanchayat.close();
        if (x > 0) {

            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            grampanchayatlist_SP.setAdapter(dataAdapter);
        }

    }


    public void Update_GramPanchayatID_spinner(String str_ditrictid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS GrampanchayatList(GramanchayatID VARCHAR,Gramanchayat VARCHAR,Panchayat_DistID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM GrampanchayatList WHERE Panchayat_DistID='" + str_ditrictid + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_GrampanchayatListDetails innerObj_Class_panchayatList = new Class_GrampanchayatListDetails();
                innerObj_Class_panchayatList.setGramanchayat_id(cursor1.getString(cursor1.getColumnIndex("GramanchayatID")));
                innerObj_Class_panchayatList.setGramanchayat_name(cursor1.getString(cursor1.getColumnIndex("Gramanchayat")));
                innerObj_Class_panchayatList.setDistrictid(cursor1.getString(cursor1.getColumnIndex("Panchayat_DistID")));


                arrayObj_Class_GrampanchayatListDetails2[i] = innerObj_Class_panchayatList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        db1.close();
        if (x > 0) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
            grampanchayatlist_SP.setAdapter(dataAdapter);

        }

    }

    public void uploadfromDB_Farmerlist() {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerList(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR,DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR,DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR,DispFarmerTable_FarmerName VARCHAR,DispFarmerTable_FarmerImage VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerList", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i = 0;
        arrayObj_Class_FarmerListDetails2 = new Class_FarmerListDetails[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_FarmerListDetails innerObj_Class_SandboxList = new Class_FarmerListDetails();
                innerObj_Class_SandboxList.setYearid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_YearID")));
                innerObj_Class_SandboxList.setStateid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_StateID")));
                innerObj_Class_SandboxList.setDistrictid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_DistrictID")));
                innerObj_Class_SandboxList.setTalukid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_TalukID")));
                innerObj_Class_SandboxList.setVillageid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_VillageID")));
                innerObj_Class_SandboxList.setGrampanchayatid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_GrampanchayatID")));
                innerObj_Class_SandboxList.setFarmerid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")));//DispFarmerTable_Farmer_Code VARCHAR
                innerObj_Class_SandboxList.setFarmercode(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code")));//DispFarmerTable_Farmer_Code VARCHAR
                innerObj_Class_SandboxList.setFarmername(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")));
                innerObj_Class_SandboxList.setFarmerimage(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage")));

                String str_FarmerName = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName"));
                String str_Farmercode = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code"));


                arrayObj_Class_FarmerListDetails2[i] = innerObj_Class_SandboxList;
                Class_FarmerListDetails item;

                //item = new Class_FarmerListDetails(str_Farmercode, str_FarmerName);
                item = new Class_FarmerListDetails(
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_YearID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_StateID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_DistrictID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_TalukID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_VillageID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_GrampanchayatID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")),cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage")));//farmer_image


                ViewFarmerList_arraylist.add(item);

                Log.e("str_FarmerName", str_FarmerName);

                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            cluster_new_sp.setAdapter(dataAdapter);
            //originalViewFarmerList.clear();
            //ViewFarmerList_arraylist.clear();
            originalViewFarmerList = new ArrayList<Class_FarmerListDetails>();
            originalViewFarmerList.addAll(ViewFarmerList_arraylist);

            farmerListViewAdapter.notifyDataSetChanged();
            farmer_listview.setAdapter(farmerListViewAdapter);


//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);

            // CustomAdapter_new dataAdapter=new CustomAdapter_new();
            //farmer_listview.setAdapter(dataAdapter);
        }

    }


    public void Update_ids_farmerlist_listview(String str_yearid, String str_stateid, String str_distid, String str_talukid, String str_villageid, String str_panchayatid) {

        SQLiteDatabase db1 = this.openOrCreateDatabase("FarmerListdb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ViewFarmerList(DispFarmerTable_YearID VARCHAR,DispFarmerTable_StateID VARCHAR,DispFarmerTable_DistrictID VARCHAR,DispFarmerTable_TalukID VARCHAR,DispFarmerTable_VillageID VARCHAR,DispFarmerTable_GrampanchayatID VARCHAR,DispFarmerTable_FarmerID VARCHAR,DispFarmerTable_Farmer_Code VARCHAR,DispFarmerTable_FarmerName VARCHAR,DispFarmerTable_FarmerImage VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM ViewFarmerList  WHERE DispFarmerTable_YearID='" + str_yearid + "' AND DispFarmerTable_StateID='" + str_stateid + "' AND DispFarmerTable_DistrictID='" + str_distid + "'  AND DispFarmerTable_TalukID='" + str_talukid + "' AND DispFarmerTable_VillageID='" + str_villageid + "' AND DispFarmerTable_GrampanchayatID='" + str_panchayatid + "'", null);
        // Cursor cursor1 = db1.rawQuery("SELECT DISTINCT * FROM StateList WHERE state_yearid='" + str_yearids + "'", null);


        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i = 0;
        arrayObj_Class_FarmerListDetails2 = new Class_FarmerListDetails[x];
        originalViewFarmerList.clear();
        ViewFarmerList_arraylist.clear();

        if (cursor1.moveToFirst()) {

            do {
                Class_FarmerListDetails innerObj_Class_SandboxList = new Class_FarmerListDetails();
                innerObj_Class_SandboxList.setYearid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_YearID")));
                innerObj_Class_SandboxList.setStateid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_StateID")));
                innerObj_Class_SandboxList.setDistrictid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_DistrictID")));
                innerObj_Class_SandboxList.setTalukid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_TalukID")));
                innerObj_Class_SandboxList.setVillageid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_VillageID")));
                innerObj_Class_SandboxList.setGrampanchayatid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_GrampanchayatID")));
                innerObj_Class_SandboxList.setFarmerid(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")));//DispFarmerTable_Farmer_Code VARCHAR
                innerObj_Class_SandboxList.setFarmercode(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code")));//DispFarmerTable_Farmer_Code VARCHAR
                innerObj_Class_SandboxList.setFarmername(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")));
                innerObj_Class_SandboxList.setFarmerimage(cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage")));

                String str_FarmerName = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName"));
                String str_Farmercode = cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code"));


                arrayObj_Class_FarmerListDetails2[i] = innerObj_Class_SandboxList;
                Class_FarmerListDetails item;

                //item = new Class_FarmerListDetails(str_Farmercode, str_FarmerName);
                item = new Class_FarmerListDetails(
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_YearID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_StateID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_DistrictID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_TalukID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_VillageID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_GrampanchayatID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerID")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_Farmer_Code")),
                        cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerName")),cursor1.getString(cursor1.getColumnIndex("DispFarmerTable_FarmerImage")));//farmer_image


                ViewFarmerList_arraylist.add(item);
                Log.e("str_FarmerName2id", str_FarmerName);
                Log.e("str_FarmerName2id", str_FarmerName);
                Log.e("str_FarmerName2id", str_FarmerName);
                Log.e("str_FarmerName2id", str_FarmerName);
                Log.e("str_FarmerName2id", str_FarmerName);
                Log.e("str_FarmerName2id", str_FarmerName);
                Log.e("str_FarmerName2id", str_FarmerName);

                i++;

            } while (cursor1.moveToNext());


        }//if ends

        db1.close();
        if (x > 0) {

//            originalViewFarmerList.clear();
//           // ViewFarmerList_arraylist.clear();
            originalViewFarmerList = new ArrayList<Class_FarmerListDetails>();
            originalViewFarmerList.addAll(ViewFarmerList_arraylist);

            if (ViewFarmerList_arraylist != null) {
                farmerListViewAdapter.notifyDataSetChanged();
                farmer_listview.setAdapter(farmerListViewAdapter);

            }
//            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_FarmerListDetails2);
//            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//            farmer_listview.setAdapter(dataAdapter);


//            CustomAdapter_new dataAdapter=new CustomAdapter_new();
//            farmer_listview.setAdapter(dataAdapter);

        }

    }


/////////////////////////////sept18th2019//////////////////////////////////

    public void alert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Activity_ViewFarmers.this);
        builder1.setMessage("No Data Found");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(Activity_ViewFarmers.this, Activity_MarketingHomeScreen.class);
                        startActivity(i);
                        finish();

                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_register, menu);
        menu.findItem(R.id.Sync)
                .setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:

                Intent i = new Intent(Activity_ViewFarmers.this, Activity_MarketingHomeScreen.class);
                startActivity(i);
                finish();
                break;

            case R.id.Sync:

                if (isInternetPresent) {


                    deleteYearTable_B4insertion();
                    deleteStateTable_B4insertion();
                    deleteDistrictTable_B4insertion();
                    deleteTalukTable_B4insertion();
                    deleteVillageTable_B4insertion();
                    deleteGrampanchayatTable_B4insertion();
                    deleteViewFarmerlistTable_B4insertion();
                    GetDropdownValues();
//                    GetFarmerDetails();


                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


                if (isInternetPresent) {
                    GetFarmerDetails();

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

                }


                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_ViewFarmers.this, Activity_MarketingHomeScreen.class);
        startActivity(i);
        finish();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//
//            if (resultCode == RESULT_OK) {
//
//                /*********** Load Captured Image And Data Start ****************/
//
//                String imageId = convertImageUriToFile(imageUri, ViewFarmers.this);
//
//
//                //  Create and excecute AsyncTask to load capture image
//
//                new LoadImagesFromSDCard().execute("" + imageId);
//
//                /*********** Load Captured Image And Data End ****************/
//
//
//            } else if (resultCode == RESULT_CANCELED) {
//
//                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
//            } else {
//
//                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
//         Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//       by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//       you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//       max Height and width values of the compressed image is taken as 816x612

         /*float maxHeight = 816.0f;
         float maxWidth = 612.0f;*/


        float maxHeight = 216.0f;
        float maxWidth = 212.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//       width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//       setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//       inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//       this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//           load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//       check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        compressedfilepaths = filename;

        try {
            out = new FileOutputStream(filename);

//           write the compressed bitmap at the destination specified by filename.
            //scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //    if (resultCode == RESULT_OK) {
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//
//
//                Log.d("CameraDemo", "Pic saved");
//                Uri imageUri = Uri.parse(mCurrentPhotoPath);
//                if (imageUri.getPath() != null) {
//                    File file = new File(imageUri.getPath());
//
//                    try {
//                        InputStream ims = new FileInputStream(file);
//                        bitmap = BitmapFactory.decodeStream(ims);
//                        rotatemethod(file.toString());
//                        BitMapToString(bitmap);
//
//                        holder.farmerimage_iv.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        return;
//                    }
//
//
//                } else if (resultCode == RESULT_CANCELED) {
//
//                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        } else if (requestCode == 2) {
//            if (resultCode == RESULT_OK) {
//
//                Uri selectedImage = data.getData();
//                String[] filePath = {MediaStore.Images.Media.DATA};
//                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                thumbnail = getResizedBitmap(thumbnail, 400);
//                Log.w(" gallery.", picturePath + "");
//                holder.farmerimage_iv.setImageBitmap(thumbnail);
//                BitMapToString(thumbnail);
//            }
//        }
//
//    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "DetSkillsSign/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        // String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        String uriSting = (file.getAbsolutePath() + "/" + "f" + System.currentTimeMillis() + ".png");
        return uriSting;

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        str_img = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("str_img..", str_img);
        return str_img;
    }

    public void rotatemethod(String path) {
        //       check the rotation of the image and display it properly
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                //matrix.postRotate(90);
                matrix.setRotate(90);
                Log.d("EXIF", "Exif90: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix,
                    true);

            // holder.farmerimage_iv.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void GetDropdownValues() {
        final ProgressDialog dialog = new ProgressDialog(Activity_ViewFarmers.this, R.style.AppCompatAlertDialogStyle);


        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        //  progressBar.setVisibility(View.VISIBLE);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_LocationURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
//                        dialog.dismiss();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray stateArray = obj.getJSONArray("States");
                            // Log.e("state", String.valueOf(stateArray));

                            JSONArray districtArray = obj.getJSONArray("District");
                            JSONArray talukArray = obj.getJSONArray("Taluks");
                            JSONArray villageArray = obj.getJSONArray("Village");
                            JSONArray panchayatArray = obj.getJSONArray("Panchayat");
                            JSONArray yearArray = obj.getJSONArray("Year");
                            // Log.e("talukArray",Arrays.toString(new JSONArray[]{talukArray}));
                            // Log.e("stateArray",Arrays.toString(new JSONArray[]{stateArray}));
                            //Log.e("districtArray",Arrays.toString(new JSONArray[]{districtArray}));
                            // Log.e("villageArray",Arrays.toString(new JSONArray[]{villageArray}));
                            // Log.e("panchayatArray",Arrays.toString(new JSONArray[]{panchayatArray}));
                            // Log.e("yearArray",Arrays.toString(new JSONArray[]{yearArray}));

                            //Log.e("villageArray", String.valueOf(villageArray.length()));
                            //now looping through all the elements of the json array,29811
                            strArray_statename = new String[stateArray.length()];
                            strArray_stateid = new String[stateArray.length()];
                            strArray_districtname = new String[districtArray.length()];
                            strArray_districtid = new String[districtArray.length()];
                            strArray_Dist_stateid = new String[districtArray.length()];
                            strArray_selecteddistname = new String[districtArray.length()];
                            strArray_talukid = new String[talukArray.length()];
                            strArray_talukname = new String[talukArray.length()];
                            strArray_taluk_distid = new String[talukArray.length()];
                            strArray_villageid = new String[villageArray.length()];
                            strArray_village_talukid = new String[villageArray.length()];
                            strArray_villagename = new String[villageArray.length()];
                            strArray_panchayatid = new String[panchayatArray.length()];
                            strArray_panchayatname = new String[panchayatArray.length()];
                            strArray_panchayat_distid = new String[panchayatArray.length()];
                            StrArray_year = new String[yearArray.length()];
                            StrArray_year_ID = new String[yearArray.length()];

                            arrayObj_Class_stateDetails2 = new Class_StateListDetails[stateArray.length()];
                            arrayObj_Class_DistrictListDetails2 = new Class_DistrictListDetails[districtArray.length()];
                            arrayObj_Class_TalukListDetails2 = new Class_TalukListDetails[talukArray.length()];
                            arrayObj_Class_VillageListDetails2 = new Class_VillageListDetails[villageArray.length()];
                            arrayObj_Class_GrampanchayatListDetails2 = new Class_GrampanchayatListDetails[panchayatArray.length()];
                            arrayObj_Class_yearDetails2 = new Class_YearListDetails[yearArray.length()];


                            for (int i = 0; i < yearArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject yearObject = yearArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object

                                Class_YearListDetails class_yearListDetails = new Class_YearListDetails(yearObject.getString("year_id"), yearObject.getString("year"));
                                StrArray_year[i] = yearObject.getString("year");
                                StrArray_year_ID[i] = yearObject.getString("year_id");
                                class_yearListDetails.setYear(StrArray_year[i]);
                                class_yearListDetails.setYearID(StrArray_year_ID[i]);
                                arrayObj_Class_yearDetails2[i] = class_yearListDetails;
                                // Log.e("StrArray_year",StrArray_year[i]);

                                yearList.add(class_yearListDetails);

                                DBCreate_Yeardetails_insert_2SQLiteDB(StrArray_year_ID[i], StrArray_year[i]);

                            }

                            for (int i = 0; i < stateArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject StateObject = stateArray.getJSONObject(i);
                                //JSONObject yearObject = yearArray.getJSONObject(i);
                                //creating a hero object and giving them the values from json object
                                Class_StateListDetails class_stateListDetails = new Class_StateListDetails(StateObject.getString("state_id"), StateObject.getString("state_name"));
                                strArray_statename[i] = StateObject.getString("state_name");
                                strArray_stateid[i] = StateObject.getString("state_id");

                                //StrArray_year_ID[i] = yearObject.getString("year_id");

                                class_stateListDetails.setState_name(strArray_statename[i]);
                                class_stateListDetails.setState_id(strArray_stateid[i]);
                                //class_stateListDetails.setYear_id(StrArray_year_ID[i]);
                                arrayObj_Class_stateDetails2[i] = class_stateListDetails;
                                //adding the hero to herolist
                                // Log.e("strArray_statename..",strArray_statename[i]);
                                stateList.add(class_stateListDetails);
                                DBCreate_Statedetails_insert_2SQLiteDB(strArray_stateid[i], strArray_statename[i], strArray_stateid[i]);

                            }
                            for (int i = 0; i < districtArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject districtObject = districtArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_DistrictListDetails class_districtListDetails = new Class_DistrictListDetails(districtObject.getString("district_id"), districtObject.getString("district_name"), districtObject.getString("st_id"));
                                strArray_districtname[i] = districtObject.getString("district_name");
                                strArray_districtid[i] = districtObject.getString("district_id");
                                strArray_Dist_stateid[i] = districtObject.getString("st_id");
                                class_districtListDetails.setDistrict_id(strArray_districtid[i]);
                                class_districtListDetails.setDistrict_name(strArray_districtname[i]);
                                class_districtListDetails.setState_id(strArray_Dist_stateid[i]);
                                arrayObj_Class_DistrictListDetails2[i] = class_districtListDetails;

                                //adding the hero to herolist
                                // Log.e("strArray_districtname..",strArray_districtname[i]);
                                districtList.add(class_districtListDetails);

                                DBCreate_Districtdetails_insert_2SQLiteDB(strArray_districtid[i], strArray_districtname[i], "Y1", strArray_Dist_stateid[i]);

                            }

                            for (int i = 0; i < talukArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject talukObject = talukArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_TalukListDetails class_talukListDetails = new Class_TalukListDetails(talukObject.getString("taluka_id"), talukObject.getString("taluk_name"), talukObject.getString("district_id"));
                                strArray_talukname[i] = talukObject.getString("taluk_name");
                                strArray_talukid[i] = talukObject.getString("taluka_id");//taluka_id,taluk_id
                                strArray_taluk_distid[i] = talukObject.getString("district_id");//district_id,dist_id
                                arrayObj_Class_TalukListDetails2[i] = class_talukListDetails;

                                //adding the hero to herolist
                                //Log.e("strArray_talukid..",strArray_talukid[i]);
                                //Log.e("strArray_taluk_distid..",strArray_taluk_distid[i]);
                                //Log.e("strArray_talukid..",strArray_talukid[i]);
                                talukList.add(class_talukListDetails);

                                DBCreate_Talukdetails_insert_2SQLiteDB(strArray_talukid[i], strArray_talukname[i], strArray_taluk_distid[i]);

                            }

                            //  for (int i = 0; i < 70; i++) {
                            for (int i = 0; i < villageArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject villageObject = villageArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_VillageListDetails class_villageListDetails = new Class_VillageListDetails(villageObject.getString("village_id"), villageObject.getString("village_name"), villageObject.getString("taluk_id"));
                                strArray_villagename[i] = villageObject.getString("village_name");
                                strArray_villageid[i] = villageObject.getString("village_id");
                                strArray_village_talukid[i] = villageObject.getString("taluk_id");
                                arrayObj_Class_VillageListDetails2[i] = class_villageListDetails;

                                //adding the hero to herolist
                                //  Log.e("str_villae_talukid..",strArray_village_talukid[i]);
                                villageList.add(class_villageListDetails);
                                DBCreate_Villagedetails_insert_2SQLiteDB(strArray_villageid[i], strArray_villagename[i], strArray_village_talukid[i]);


                            }

                            for (int i = 0; i < panchayatArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject panchayatObject = panchayatArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                Class_GrampanchayatListDetails class_grampanchayatListDetails = new Class_GrampanchayatListDetails(panchayatObject.getString("panchayat_id"), panchayatObject.getString("panchayat_name"), panchayatObject.getString("district_id"));
                                strArray_panchayatname[i] = panchayatObject.getString("panchayat_name");
                                strArray_panchayatid[i] = panchayatObject.getString("panchayat_id");
                                strArray_panchayat_distid[i] = panchayatObject.getString("district_id");
                                arrayObj_Class_GrampanchayatListDetails2[i] = class_grampanchayatListDetails;

                                //adding the hero to herolist
                                // Log.e("strArrhayatname",strArray_panchayatname[i]);
                                panchayatList.add(class_grampanchayatListDetails);

                                DBCreate_Grampanchayatdetails_insert_2SQLiteDB(strArray_panchayatid[i], strArray_panchayatname[i], strArray_panchayat_distid[i]);

                            }

                            uploadfromDB_Yearlist();
                            uploadfromDB_Statelist();
                            uploadfromDB_Districtlist();
                            uploadfromDB_Taluklist();
                            uploadfromDB_Villagelist();
                            uploadfromDB_Grampanchayatlist();

//                            ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_stateDetails2);
//                            dataAdapter.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            statelist_SP.setAdapter(dataAdapter);
//                            ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_DistrictListDetails2);
//                            dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            districtlist_SP.setAdapter(dataAdapter_dist);
//                            ArrayAdapter dataAdapter_taluk= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_TalukListDetails2);
//                            dataAdapter_taluk.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            taluklist_SP.setAdapter(dataAdapter_taluk);
//                            ArrayAdapter dataAdapter_village= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_VillageListDetails2);
//                            dataAdapter_village.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            villagelist_SP.setAdapter(dataAdapter_village);
//                            ArrayAdapter dataAdapter_panchayat= new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, arrayObj_Class_GrampanchayatListDetails2);
//                            dataAdapter_panchayat.setDropDownViewResource(R.layout.spinnercenterstyle);
//                            grampanchayatlist_SP.setAdapter(dataAdapter_panchayat);


//                            for(int i=0;i<strArray_Dist_stateid.length;i++) {
//                                if (sp_strstate_ID.equals(strArray_Dist_stateid[i])) {
//
//                                    strArray_selecteddistname[i]=strArray_districtname[i];
////                                    ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, strArray_districtname);
////                                    dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
////                                    districtlist_SP.setAdapter(dataAdapter_dist);
//                                }
//
//                                   ArrayAdapter dataAdapter_dist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, strArray_selecteddistname);
//                                   dataAdapter_dist.setDropDownViewResource(R.layout.spinnercenterstyle);
//                                   districtlist_SP.setAdapter(dataAdapter_dist);
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.e("errormsg_catch", e.getMessage());
                        }

                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        // error.printStackTrace();
                        Log.e("errormsg", error.getMessage().toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void GetFarmerDetails() {
        final ProgressDialog dialog = new ProgressDialog(Activity_ViewFarmers.this, R.style.AppCompatAlertDialogStyle);


        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        //  progressBar.setVisibility(View.VISIBLE);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_FarmerDetailsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
                        dialog.dismiss();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray farmerArray = obj.getJSONArray("Farmers");


                            //Log.e("farmerarraylength",String.valueOf(farmerArray.length()));
                            //Log.e("farmerArray",Arrays.toString(new JSONArray[]{farmerArray}));
                            //now looping through all the elements of the json array
                            strArray_farmername = new String[farmerArray.length()];
                            StrArray_farmercode = new String[farmerArray.length()];
                            StrArray_farmerlist_farmerid = new String[farmerArray.length()];
                            StrArray_farmerlist_yearid = new String[farmerArray.length()];
                            StrArray_farmerlist_stateid = new String[farmerArray.length()];
                            StrArray_farmerlist_distid = new String[farmerArray.length()];
                            StrArray_farmerlist_talukid = new String[farmerArray.length()];
                            StrArray_farmerlist_villageid = new String[farmerArray.length()];
                            StrArray_farmerlist_panchayatid = new String[farmerArray.length()];
                            StrArray_farmerimage= new String[farmerArray.length()];
                            arrayObj_Class_FarmerListDetails2 = new Class_FarmerListDetails[farmerArray.length()];

                            for (int i = 0; i < farmerArray.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject farmerObject = farmerArray.getJSONObject(i);

                                // Log.e("farmername.",farmerObject.getString("farmer_name"));

                                //creating a hero object and giving them the values from json object
                                Class_FarmerListDetails class_farmerListDetails = new Class_FarmerListDetails(farmerObject.getString("year_id"),
                                        farmerObject.getString("st_id"),
                                        farmerObject.getString("dist_id"),
                                        farmerObject.getString("tlk_id"),
                                        farmerObject.getString("village_id"),
                                        farmerObject.getString("panchayat_id"),
                                        farmerObject.getString("farmer_id"),
                                        farmerObject.getString("farmer_code"),
                                        farmerObject.getString("farmer_name"),farmerObject.getString("farmer_image"));//farmer_image

                                strArray_farmername[i] = farmerObject.getString("farmer_name");//farmer_name
                                StrArray_farmercode[i] = farmerObject.getString("farmer_code");
                                StrArray_farmerlist_farmerid[i] = farmerObject.getString("farmer_id");
                                StrArray_farmerlist_yearid[i] = farmerObject.getString("year_id");
                                StrArray_farmerlist_stateid[i] = farmerObject.getString("st_id");
                                StrArray_farmerlist_distid[i] = farmerObject.getString("dist_id");
                                StrArray_farmerlist_talukid[i] = farmerObject.getString("tlk_id");
                                StrArray_farmerlist_villageid[i] = farmerObject.getString("village_id");
                                StrArray_farmerlist_panchayatid[i] = farmerObject.getString("panchayat_id");
                                StrArray_farmerimage[i] = farmerObject.getString("farmer_image");

//                                Log.e("farmer_id.", farmerObject.getString("farmer_id"));
//                                Log.e("year_id.", farmerObject.getString("year_id"));
//                                Log.e("st_id.", farmerObject.getString("st_id"));
//                                Log.e("dist_id.", farmerObject.getString("dist_id"));
//                                Log.e("tlk_id.", farmerObject.getString("tlk_id"));
//                                Log.e("panchayat_id.", farmerObject.getString("panchayat_id"));
//                                Log.e("village_id.", farmerObject.getString("village_id"));
//                                Log.e("farmer_code.", farmerObject.getString("farmer_code"));
//                                Log.e("farmer_name.", farmerObject.getString("farmer_name"));

                                /*
                                [{"st_id":"25","dist_id":"635","tlk_id":"5663","village_id":"626707",
                                "panchayat_id":"18","farmer_id":"1","farmer_name":"Surender Reddy",
                                "farmer_code":"TS25TEMP282","year_id":"1","year":"2017","farmer_image":"null"},
                                 */
                                class_farmerListDetails.setFarmername(strArray_farmername[i]);
                                class_farmerListDetails.setFarmercode(StrArray_farmercode[i]);
                                class_farmerListDetails.setFarmerimage(StrArray_farmerimage[i]);
                                arrayObj_Class_FarmerListDetails2[i] = class_farmerListDetails;

                                Class_FarmerListDetails item;
                                item = new Class_FarmerListDetails(farmerObject.getString("year_id"),
                                        farmerObject.getString("st_id"),
                                        farmerObject.getString("dist_id"),
                                        farmerObject.getString("tlk_id"),
                                        farmerObject.getString("village_id"),
                                        farmerObject.getString("panchayat_id"),
                                        farmerObject.getString("farmer_id"),
                                        farmerObject.getString("farmer_code"),
                                        farmerObject.getString("farmer_name"),farmerObject.getString("farmer_image"));//farmer_image

                                //item = new Class_FarmerListDetails(strArray_farmername[i], StrArray_farmercode[i]);
                                ViewFarmerList_arraylist.add(item);


                                farmerList.add(class_farmerListDetails);
                                DBCreate_ViewFarmerlistdetails_insert_2SQLiteDB(StrArray_farmerlist_yearid[i], StrArray_farmerlist_stateid[i], StrArray_farmerlist_distid[i], StrArray_farmerlist_talukid[i], StrArray_farmerlist_villageid[i], StrArray_farmerlist_panchayatid[i], StrArray_farmerlist_farmerid[i], StrArray_farmercode[i], strArray_farmername[i], StrArray_farmerimage[i]);
                            }
                            uploadfromDB_Farmerlist();

//                            originalViewFarmerList = new ArrayList<Class_FarmerListDetails>();
//                            originalViewFarmerList.addAll(ViewFarmerList_arraylist);
//
//                            farmerListViewAdapter.notifyDataSetChanged();
//                            farmer_listview.setAdapter(farmerListViewAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Log.e("error", error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


    public void SaveFarmerImage() {
        final ProgressDialog dialog = new ProgressDialog(Activity_ViewFarmers.this, R.style.AppCompatAlertDialogStyle);


        //getting the progressbar
        // final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        //  progressBar.setVisibility(View.VISIBLE);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_FarmerImageURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
                        dialog.dismiss();

                        parse_farmpondresponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Log.e("error", error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

        @Override
        protected Map<String,String> getParams(){
            Map<String,String> params = new HashMap<String, String>();


//                params.put("farmer_id","1");
Log.e("img_farmerid",str_selected_farmerID_forimagesaving);
            params.put("Farmer_ID",str_selected_farmerID_forimagesaving);//str_selected_farmerID_forimagesaving,str_selected_farmerID_forimagesaving
            params.put("image_link",str_img);
            return params;
        }

    };
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void parse_farmpondresponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("statusMessage").equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
            }
            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Farmer_ID Empty")) {

                Toast.makeText(getApplicationContext(), "Sorry,could not save image", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
                e.printStackTrace();
                Log.e("error",e.toString());
            }

        }
        private class Holder {

        ///////////////////////sept16th2019
        TextView FarmerNamelabel_tv;
        TextView FarmerName_tv;
        TextView FarmerCodelabel_tv;
        TextView farmercode_tv;
        ImageView farmerimage_iv;
        Button farmpond_bt;

        ///////////////////////sept16th2019

    }



    public static class ViewHolder {


        public ImageView farmerimage_iv;
        ///////////////////////sept16th2019
        TextView FarmerNamelabel_tv;
        TextView FarmerName_tv;
        TextView FarmerCodelabel_tv;
        TextView farmercode_tv;
        Button farmpond_bt;

        ///////////////////////sept16th2019

    }

    public class FarmerListViewAdapter extends BaseAdapter {


        public ArrayList<Class_FarmerListDetails> projList;
        Activity activity;
        private ArrayList<Class_FarmerListDetails> mDisplayedValues = null;

        public FarmerListViewAdapter(Activity activity, ArrayList<Class_FarmerListDetails> projList) {
            super();
            this.activity = activity;
            this.projList = projList;
            this.mDisplayedValues = projList;
        }

        @Override
        public int getCount() {
            //return projList.size();
            return mDisplayedValues.size();
        }

        @Override
        public Class_FarmerListDetails getItem(int position) {

            //return projList.get(position);
            return mDisplayedValues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            ViewHolder holder;
            LayoutInflater inflater = activity.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.child_farmerlist_item, null);
                holder = new ViewHolder();

                holder.FarmerNamelabel_tv = (TextView) convertView.findViewById(R.id.FarmerNamelabel_tv);
                holder.FarmerName_tv = (TextView) convertView.findViewById(R.id.FarmerName_tv);
                holder.FarmerCodelabel_tv = (TextView) convertView.findViewById(R.id.FarmerCodelabel_tv);
                holder.farmercode_tv = (TextView) convertView.findViewById(R.id.farmercode_tv);
                holder.farmpond_bt = (Button) convertView.findViewById(R.id.farmpond_bt);
                holder.farmerimage_iv = (ImageView) convertView.findViewById(R.id.farmerimage_iv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Class_FarmerListDetails Obj_Class_farmerlistdetails = (Class_FarmerListDetails) getItem(position);

//            if (count1 == 0) {
//                mainviewfarmerlist_ll.setVisibility(View.GONE);
//                nofarmerRecords_ll.setVisibility(View.VISIBLE);
//            } else {
            if (Obj_Class_farmerlistdetails != null) {
                holder.FarmerName_tv.setText(Obj_Class_farmerlistdetails.getFarmername());
                holder.farmercode_tv.setText(Obj_Class_farmerlistdetails.getFarmercode());
                holder.farmerimage_iv.setTag(position);
                String str_fetched_imgfile=Class_URL.URL_farmerimageurl+Obj_Class_farmerlistdetails.getFarmerimage();
                Log.e("Obj_ClFarmerimage()",Obj_Class_farmerlistdetails.getFarmerimage());
                if(Obj_Class_farmerlistdetails.getFarmerimage()!=null) {
                    imgLoader.displayImage(str_fetched_imgfile, holder.farmerimage_iv, displayoption, imageListener);
                }
                holder.farmerimage_iv.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View v) {

                        int pos = (Integer) v.getTag();
                        Class_FarmerListDetails Obj_Class_farmerlistdetails = (Class_FarmerListDetails) getItem(pos);
                        Log.e("farmerimg", String.valueOf(pos));
                       // displayalert();
                        str_selected_farmerID_forimagesaving = Obj_Class_farmerlistdetails.getFarmerid();
                        Log.e("img_farmerid_onclick",str_selected_farmerID_forimagesaving);

                        selectImage();
                    }
                });


            }
            // }


            return convertView;
        }


        //////////////////////////////////////7thoct///////////////////////////////////


        private void selectImage() {
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ViewFarmers.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = Utility.checkPermission(Activity_ViewFarmers.this);
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


        //////////////////////////////////////7thoct///////////////////////////////////////

        public void filter(String charText, ArrayList<Class_FarmerListDetails> projectList) {
            charText = charText.toLowerCase(Locale.getDefault());
            this.mDisplayedValues.clear();

            if (charText != null) {
                if (projectList != null) {
                    if (charText.isEmpty() || charText.length() == 0) {
                        this.mDisplayedValues.addAll(projectList);
                    } else {
                        for (Class_FarmerListDetails wp : projectList) {
                            if (wp.getFarmername().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase())) {
                                this.mDisplayedValues.add(wp);
                            }
                        }
                    }
                    notifyDataSetChanged();

                    //FarmerListViewAdapter.updateList(mDisplayedValues);
                }
            }
        }


//    public void updateList(ArrayList<Class_FarmerListDetails> list){
//        projList = list;
//        notifyDataSetChanged();
//    }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
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
        holder.farmerimage_iv.setImageBitmap(bm);
        if(isInternetPresent){
            SaveFarmerImage();
        }else{

            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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
        holder.farmerimage_iv.setImageBitmap(thumbnail);
        BitMapToString(thumbnail);
        if(isInternetPresent){
            SaveFarmerImage();
        }else{


            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }






}

