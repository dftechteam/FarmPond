package df.farmponds;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class Activity_HomeScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    ConnectionDetector internetDectector;
    Boolean isInternetPresent = false;

    TextView dislay_UserName_tv;
    ImageView displ_Userimg_iv;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    String str_Googlelogin_Username, str_Googlelogin_UserImg, str_loginuserID;
    String str_flag;



  //  SharedPreferences sharedpref_username_Obj;
   // SharedPreferences sharedpref_userimage_Obj;
    SharedPreferences sharedpref_loginuserid_Obj;
   // SharedPreferences sharedpref_flag_Obj;
   Toolbar toolbar;

    //Added by shivaleela
    public static final String sharedpreferenc_username = "googlelogin_name";
    public static final String Key_username = "name_googlelogin";
    SharedPreferences sharedpref_userimage_Obj;
    public static final String sharedpreferenc_userimage = "googlelogin_img";
    public static final String key_userimage = "profileimg_googlelogin";
    SharedPreferences sharedpref_username_Obj;

    SharedPreferences sharedpref_flag_Obj;
    public static final String sharedpreferenc_flag = "flag_sharedpreference";
    public static final String key_flag = "flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fstpage);

    /*    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Home");*/
        toolbar = (Toolbar) findViewById(R.id.toolbar_n_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView title= (TextView) toolbar.findViewById(R.id.title_name);
        title.setText("Home");
        getSupportActionBar().setTitle("");

        //Added by shivaleela on aug 14th 2019
        //  SaveSharedPreference.getUserName(Activity_HomeScreen.this);

//        SharedPreferences myprefs_name = this.getSharedPreferences("googlelogin_name", Context.MODE_PRIVATE);
//        str_Googlelogin_Username = myprefs_name.getString("name_googlelogin", "nothing");

//        SharedPreferences myprefs_img = this.getSharedPreferences("googlelogin_img", Context.MODE_PRIVATE);
//        str_Googlelogin_UserImg = myprefs_img.getString("profileimg_googlelogin", "nothing");

//        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
//        str_loginuserID = myprefs.getString("login_userid", "nothing");

//        SharedPreferences myprefs_flag = this.getSharedPreferences("flag", Context.MODE_PRIVATE);
//        str_flag = myprefs_flag.getString("flag", "nothing");


        sharedpref_username_Obj=getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_Googlelogin_Username = sharedpref_username_Obj.getString(Key_username, "").trim();

        sharedpref_userimage_Obj=getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_Googlelogin_UserImg = sharedpref_userimage_Obj.getString(key_userimage, "").trim();

//        sharedpref_loginuserid_Obj=getSharedPreferences(sharedpreferenc_loginuserid, Context.MODE_PRIVATE);
//        str_loginuserID = sharedpref_loginuserid_Obj.getString(key_loginuserid, "").trim();

        sharedpref_flag_Obj=getSharedPreferences(sharedpreferenc_flag, Context.MODE_PRIVATE);
        str_flag = sharedpref_flag_Obj.getString(key_flag, "").trim();


        if(!str_flag.equals("1")) {
            if (SaveSharedPreference.getUserName(Activity_HomeScreen.this).length() == 0) {
//                Intent i = new Intent(Activity_HomeScreen.this, NormalLogin.class);
//                startActivity(i);
//                finish();
//                // call Login Activity
            } else {
                // Stay at the current activity.
            }

        }else {

            if (SaveSharedPreference.getUserName(Activity_HomeScreen.this).length() == 0) {
                Intent i = new Intent(Activity_HomeScreen.this, MainActivity.class);
                startActivity(i);
                finish();
                // call Login Activity
            } else {
                // Stay at the current activity.
            }
        }


        AsyncCallWS2_Login task = new AsyncCallWS2_Login(Activity_HomeScreen.this);
        task.execute();
       /* LinearLayout homepagelayout_LL = (LinearLayout) findViewById(R.id.homepagelayout_ll);
        homepagelayout_LL.setVisibility(LinearLayout.VISIBLE);
        @SuppressLint("ResourceType") Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation3.setDuration(100);
        homepagelayout_LL.setAnimation(animation3);
        homepagelayout_LL.animate();
        animation3.start();*/


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        dislay_UserName_tv = (TextView) findViewById(R.id.dislay_UserName_TV);
        displ_Userimg_iv = (ImageView) findViewById(R.id.displ_Userimg_IV);
       // dislay_UserName_tv.setText(str_Googlelogin_Username);

        if(str_flag.equals("1")){
            dislay_UserName_tv.setText("");
        }else {
            dislay_UserName_tv.setText(str_Googlelogin_Username);
            try {
                Glide.with(this).load(str_Googlelogin_UserImg).into(displ_Userimg_iv);
            } catch (NullPointerException e) {
                // Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RelativeLayout stuRegistration_relativeLayout = (RelativeLayout) findViewById(R.id.student_registration_RL);
                stuRegistration_relativeLayout.setVisibility(LinearLayout.VISIBLE);
               /* @SuppressLint("ResourceType") Animation stuRegistration_animation = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.translate);
                stuRegistration_animation.setDuration(500);
                stuRegistration_relativeLayout.setAnimation(stuRegistration_animation);
                stuRegistration_relativeLayout.setAnimation(stuRegistration_animation);
                stuRegistration_relativeLayout.animate();
                stuRegistration_animation.start();*/

                stuRegistration_relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(Activity_HomeScreen.this, Activity_MarketingHomeScreen.class);
                        startActivity(i);
                      //  overridePendingTransition(R.animator.slide_right, R.animator.slide_right);

                    }
                });

               /* final Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        RelativeLayout documentUpload_relativelayout = (RelativeLayout) findViewById(R.id.documentUpload_RL);
                        documentUpload_relativelayout.setVisibility(LinearLayout.VISIBLE);
                        @SuppressLint("ResourceType")
                        Animation animation_docupload = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.anim);
                        animation_docupload.setDuration(500);
                        documentUpload_relativelayout.setAnimation(animation_docupload);
                        documentUpload_relativelayout.animate();
                        animation_docupload.start();

                        documentUpload_relativelayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), "Currently document upload is disabled", Toast.LENGTH_LONG).show();

                               *//* Intent i = new Intent(Activity_HomeScreen.this, Activity_SO_DocumentUpload.class);
                                startActivity(i);
                                overridePendingTransition(R.animator.slide_right, R.animator.slide_right);*//*

                            }
                        });*/

                       /* final Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                RelativeLayout scheduler_relativelayout = (RelativeLayout) findViewById(R.id.scheduler_RL);
                                scheduler_relativelayout.setVisibility(LinearLayout.VISIBLE);
                                @SuppressLint("ResourceType")
                                Animation animation_scheduler = AnimationUtils.loadAnimation(Activity_HomeScreen.this, R.anim.right_slide);
                                animation_scheduler.setDuration(500);
                                scheduler_relativelayout.setAnimation(animation_scheduler);
                                scheduler_relativelayout.animate();
                                animation_scheduler.start();

                                scheduler_relativelayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        *//*Intent i = new Intent(Activity_HomeScreen.this, Activity_SO_DocumentUpload.class);
                                        startActivity(i);*//*

                                       //Added isInternetPresent by shivaleela on Aug 21st 2019
                                        internetDectector = new ConnectionDetector(getApplicationContext());
                                        isInternetPresent = internetDectector.isConnectingToInternet();

                                       if(isInternetPresent) {
                                           Date date = new Date();
                                           Log.i("Tag_time", "date1=" + date);
                                           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                           String PresentDayStr = sdf.format(date);
                                           Log.i("Tag_time", "PresentDayStr=" + PresentDayStr);

                                           cal_adapter1.getPositionList(PresentDayStr, Activity_HomeScreen.this);
                                           overridePendingTransition(R.animator.slide_right, R.animator.slide_right);
                                       }else{
                                           Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();

                                       }
                                    }
                                });
                            }
                        }, 300);*/
                    /*}
                }, 200);*/
            }
        }, 100);


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
//        if (opr.isDone()) {
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
//                    //handleSignInResult(googleSignInResult);
//                }
//            });
//        }
//    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            dislay_UserName_tv.setText(account.getDisplayName());
            try {
                Glide.with(this).load(account.getPhotoUrl()).into(displ_Userimg_iv);
            } catch (NullPointerException e) {
                // Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
            }

        } else {
            //gotoMainActivity();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {

           *//* case R.id.next:
                Intent in =new Intent(Activity_HomeScreen.this,Home1.class);
                startActivity(in);
                finish();
                break;*//*
            case R.id.share:
                Toast.makeText(Activity_HomeScreen.this, "Action clicked", Toast.LENGTH_LONG).show();
                internetDectector = new ConnectionDetector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (isInternetPresent) {


                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("logout_key1", "yes");
                    startActivity(i);
                    finish();
                    //}
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
                }
            case android.R.id.home:
                Intent i =new Intent(Activity_HomeScreen.this,MainActivity.class);
                startActivity(i);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }*/

    private class AsyncCallWS2_Login extends AsyncTask<String, Void, Void>
    {
        // ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute()
        {
            Log.i("tag", "onPreExecute---tab2");
            /*dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("tag", "onProgressUpdate---tab2");
        }

        public AsyncCallWS2_Login(Activity_HomeScreen activity) {
            context =  activity;
            //  dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("tag", "doInBackground");


			/*  if(!login_result.equals("Fail"))
			  {
				  GetAllEvents(u1,p1);
			  }*/

            //GetAllEvents(u1,p1);



            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            //  dialog.dismiss();

           /* Intent i = new Intent(Activity_HomeScreen.this, Activity_HomeScreen.class);
            SharedPreferences myprefs = Activity_HomeScreen.this.getSharedPreferences("user", MODE_PRIVATE);
            myprefs.edit().putString("login_userid", str_loginuserID).apply();
            SharedPreferences myprefs_scheduleId = Activity_HomeScreen.this.getSharedPreferences("scheduleId", MODE_PRIVATE);
            myprefs_scheduleId.edit().putString("scheduleId", Schedule_ID).apply();
            startActivity(i);
            finish();*/
        }
    }


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
            internetDectector = new ConnectionDetector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {

//                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editor = settings.edit();
//                editor.remove("login_userEmail");
                SaveSharedPreference.setUserName(Activity_HomeScreen.this, "");

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("Key_Logout", "yes");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

//                Intent i = new Intent(getApplicationContext(), NormalLogin.class);
//                i.putExtra("logout_key1", "yes");
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);

                //}
            } else {
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == android.R.id.home) {
            //  Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Activity_HomeScreen.this, Activity_HomeScreen.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
