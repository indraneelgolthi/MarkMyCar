package thatbiriyaniguy.markmycar;


import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class LandingActivity extends AppCompatActivity {

    private static final String TAG = "LandingActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landing);

        if(isServicesOK()){
            init();
        }
    }

    private void init(){

        ImageView mark = findViewById(R.id.btn_Mark);
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mark = new Intent(LandingActivity.this, MarkActivity.class);
                startActivity(mark);
            }
        });

        ImageView find = findViewById(R.id.btnFind);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent find = new Intent (LandingActivity.this, FindActivity.class);
                startActivity(find);
            }
        });
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google play services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(LandingActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //version is good and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services are working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //there is an error, but it can be resolved
            Log.d(TAG, "isServicesOK: there is an error but it can be resovled");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(LandingActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You can't make map requests!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
