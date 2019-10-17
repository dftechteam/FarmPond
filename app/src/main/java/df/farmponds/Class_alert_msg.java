package df.farmponds;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class Class_alert_msg
{
    private final Context mContext;

    private FragmentManager fragmentManager;

    public Class_alert_msg(Context context) {
        this.mContext = context;

    }






    public  static void alerts_dialog(String str_error, String ws, Context context)
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle("DF FarmPond");
        dialog.setMessage("No Internet! Data is stored in Mobile");

        Activity activity = (Activity) context;
       // fragmentManager = activity.getFragmentManager();


        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {



                dialog.dismiss();

            }
        });




        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();


    }





    





}
