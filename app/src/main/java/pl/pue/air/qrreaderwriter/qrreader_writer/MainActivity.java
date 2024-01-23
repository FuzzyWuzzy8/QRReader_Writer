package pl.pue.air.qrreaderwriter.qrreader_writer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import androidx.annotation.NonNull;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    //public static final int CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    //widgets
    private Button CameraPermissionsButton;
    private Button GenerateQRButton;
    private Button scan;

    //camera permission
    private static final int PERMISSION_REQUEST_CAMERA = 1;

    private boolean checkCameraPermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }


    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA }, PERMISSION_REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permgrant), Toast.LENGTH_SHORT).show(); //
                // Camera permission granted
            } else {
                Toast.makeText(this, getString(R.string.permdeny), Toast.LENGTH_SHORT).show(); //
                // Camera permission denied
            }
        }
        //storage
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permgrant), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, getString(R.string.permgrant), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.permdeny), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, getString(R.string.permdeny), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!checkCameraPermission()) {
            requestCameraPermission();
        }
    }
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //locale language
        loadLocale();

        //
        ActionBar actionBar = getSupportActionBar(); //change actionbar title
        actionBar.setTitle(getResources().getString(R.string.app_name));


        //language
        TextView changeLang = findViewById(R.id.language);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
        //


        CameraPermissionsButton = findViewById(R.id.camera);
        GenerateQRButton = findViewById(R.id.generate);


        scan = findViewById(R.id.scanQRM); //QRb

        //
        ///*


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanQRCode.class);
                //
                //checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                //
                startActivity(intent);
            }
        });

        // */
        //

//permission check moved to auto check. Still left the button.
        CameraPermissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });


        GenerateQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateQRCode.class);
                //
                //checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                //
                startActivity(intent);
            }
        });

    }
    //
    /*
    public void checkPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {permission},
                    requestCode);
        }
        else{
            Toast.makeText(this, getString(R.string.alreadygrant), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, getString(R.string.permgrant), Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, getString(R.string.permdeny), Toast.LENGTH_SHORT).show();
            }
        }
    }
    */
    //

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "Polish", "French", "German", "Spanish", "Turkish", "Russian"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose language");
        //Add custom theme later
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_list_item, R.id.text1, listItems);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                switch (i){
        case 0:
            // English
            setLocale("en");
            recreate();
            break;
        case 1:
            // Polish
            setLocale("pl");
            recreate();
            break;
        case 2:
            // French
            setLocale("fr");
            recreate();
            break;
        case 3:
            // German
            setLocale("de");
            recreate();
            break;
        case 4:
            // Spanish
            setLocale("es");
            recreate();
            break;
        case 5:
            // Turkish
            setLocale("tr");
            recreate();
            break;
        case 6:
            // Russian
            setLocale("ru");
            recreate();
            break;
            }

        dialogInterface.dismiss();   //dismiss alert dialog when language is selected
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();                     //show alert dialog
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        //save data to preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    //load language from shared preference
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    //
    //Check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            //Requesting permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(MainActivity.this, (R.string.alreadygrant), Toast.LENGTH_SHORT).show();
            //Permission already granted
        }
    }

    //Function when accepts or decline permission
    //Request Code defined in mainactivity
    //Code provided when user prompts for permission


//moved to auto check function
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, getString(R.string.permgrant), Toast.LENGTH_SHORT) .show();
                //Camera permission granted
            }
            else {
                Toast.makeText(MainActivity.this, getString(R.string.permdeny), Toast.LENGTH_SHORT) .show();
                //Camera permission denied
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, getString(R.string.permgrant), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.permdeny), Toast.LENGTH_SHORT).show();
            }
        }
    }
 */
}
