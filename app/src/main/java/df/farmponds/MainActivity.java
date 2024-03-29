package df.farmponds;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class MainActivity extends AppCompatActivity
{



    private static final int RC_SIGN_IN = 234;////a constant for detecting the login intent result
    private static final String TAG = "dffarmpond";
    GoogleSignInClient googlesigninclient_obj;
    FirebaseAuth firebaseauth_obj;
    SignInButton google_signin_bt;

    public final static String COLOR = "#1565C0";


    GoogleSignInAccount account;

    String str_googletokenid;


    EditText username_et;
    Button normallogin_bt;
    String str_gmailid;

   Context context_obj;


    public static final String sharedpreferencebook_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_employeeid = "KeyValue_employeeid";
    public static final String KeyValue_employeename = "KeyValue_employeename";
    public static final String KeyValue_employee_mailid = "KeyValue_employee_mailid";
    public static final String KeyValue_employeecategory = "KeyValue_employeecategory";
    public static final String KeyValue_employeesandbox = "KeyValue_employeesandbox";

    SharedPreferences sharedpreferencebook_usercredential_Obj;
    SharedPreferences.Editor editor_obj;
    String str_token;
    String str_tokenfromprefrence;


    //Added by shivaleela
    public static final String sharedpreferenc_username = "googlelogin_name";
    public static final String Key_username = "name_googlelogin";
    SharedPreferences sharedpref_userimage_Obj;
    public static final String sharedpreferenc_userimage = "googlelogin_img";
    public static final String key_userimage = "profileimg_googlelogin";
    SharedPreferences sharedpref_username_Obj;

    String str_loginusername,str_profileimage;


    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Added by shivaleela
        sharedpref_username_Obj=getSharedPreferences(sharedpreferenc_username, Context.MODE_PRIVATE);
        str_loginusername = sharedpref_username_Obj.getString(Key_username, "").trim();
        sharedpref_userimage_Obj=getSharedPreferences(sharedpreferenc_userimage, Context.MODE_PRIVATE);
        str_profileimage = sharedpref_userimage_Obj.getString(key_userimage, "").trim();

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            // call Login Activity
        }
        else
        {
            Intent i=new Intent(MainActivity.this,Activity_HomeScreen.class);
            startActivity(i);
            finish();

            // Stay at the current activity.
        }
        /////////////////////////////////////////


        normallogin_bt =(Button)findViewById(R.id.normallogin_bt);
        username_et =(EditText) findViewById(R.id.username_et);
        google_signin_bt =(SignInButton)findViewById(R.id.google_signin_bt);
        google_signin_bt.setColorScheme(SignInButton.COLOR_DARK);
        setGooglePlusButtonText(google_signin_bt,"  Sign in with DF mail  ");

        context_obj=this.getApplicationContext();

        //

        sharedpreferencebook_usercredential_Obj=this.getSharedPreferences(sharedpreferencebook_usercredential, Context.MODE_PRIVATE);


        //Google Sign initializing
        firebaseauth_obj = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_ID_type3))
                .requestEmail()
                .build();
        googlesigninclient_obj = GoogleSignIn.getClient(this, gso);
      //Google Sign initializing



        if (checkPermissions())
        {
            //  permissions  granted.
        }



      // Signout function

        Intent myIntent = getIntent();

        if(myIntent!=null)
        {

            String logout="no";
            logout = myIntent.getStringExtra("Key_Logout");
            if(logout!=null && (logout.equalsIgnoreCase("yes")))
            {
                signOut();
            }
        }

        // Signout function


        google_signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                google_sign();
            }
        });







        //normal login comment while releasing apk

        normallogin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                str_gmailid=username_et.getText().toString();
                AsyncTask_loginverify();
            }
        });

        //normal login comment while releasing apk

    }// end of onCreate()





    private void google_sign() {
        //getting the google signin intent
        Intent signInIntent = googlesigninclient_obj.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {
            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);
                //authenticating with firebase
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct)
    {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        str_gmailid=acct.getEmail().toString();

        //Now using firebase we are signing in the user here
        firebaseauth_obj.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseauth_obj.getCurrentUser();

              //Added by shivaleela
                            SharedPreferences.Editor myprefs_Username = sharedpref_username_Obj.edit();
                            myprefs_Username.putString(Key_username, acct.getDisplayName());
                            myprefs_Username.apply();
                            SaveSharedPreference.setUserName(MainActivity.this,account.getDisplayName());

                            SharedPreferences.Editor myprefs_UserImg = sharedpref_userimage_Obj.edit();
                            myprefs_UserImg.putString(key_userimage, String.valueOf(acct.getPhotoUrl()));
                            myprefs_UserImg.apply();
//////////////////////////////////////////


                            Toast.makeText(MainActivity.this, "User Signed In:"+str_gmailid, Toast.LENGTH_SHORT).show();




                            AsyncTask_loginverify();



                            /*try {
                                postRequest();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }










    //volley asynctask
    private void AsyncTask_loginverify()
    {

        final ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();



        String str_loginurl = Class_URL.URL_Login.toString().trim();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, str_loginurl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        pdLoading.dismiss();

                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.e("volley response",response);
                        parseloginresponse(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pdLoading.dismiss();
                        Log.e("error",error.toString());
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

               params.put("email",str_gmailid);// for dynamic
               // params.put("email","eventtest464@gmail.com");

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    public void parseloginresponse(String response)
    {


        //response: {"employeeid":"6","name":"Testing",
        // "email":"eventtest464@gmail.com","employeecategory":"Marketing",
        // "Sandbox":"Hubli","statusMessage":"Successfull"}
        editor_obj = sharedpreferencebook_usercredential_Obj.edit();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("statusMessage").equalsIgnoreCase("Successfull"))
            {

                String str_employeeid=jsonObject.getString("employeeid");
                String str_employeename=jsonObject.getString("name");
                String str_employeemailid=jsonObject.getString("email");
                String str_employeecategory=jsonObject.getString("employeecategory");
                String str_employeesandbox=jsonObject.getString("Sandbox");

                editor_obj.putString(KeyValue_employeeid, str_employeeid);
                editor_obj.putString(KeyValue_employeename, str_employeename);
                editor_obj.putString(KeyValue_employee_mailid, str_employeemailid);
                editor_obj.putString(KeyValue_employeecategory, str_employeecategory);
                editor_obj.putString(KeyValue_employeesandbox, str_employeesandbox);
                editor_obj.commit();


               /* Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);*/

                Intent intent = new Intent(MainActivity.this,Activity_HomeScreen.class);
                startActivity(intent);
            }
            else{
                signOut_InvalidUser();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    private void signOut() {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MainActivity.this,"Sigined Out Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
    }




    private void signOut_InvalidUser() {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MainActivity.this,"Sigined Out: InValid User",Toast.LENGTH_SHORT).show();
                    }
                });
    }





    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                // tv.setBackgroundColor(Color.CYAN);
                tv.setBackgroundDrawable(
                        new ColorDrawable(Color.parseColor(COLOR)));
                tv.setTextColor(Color.WHITE);
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/laouibold.ttf"));
                return;
            }
        }
    }

}// end of class
