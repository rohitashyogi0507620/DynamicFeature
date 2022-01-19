package com.onlineaavedan.dynamicfeature;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    Button btnaddfeature, btn_startactivity;

    private int mySessionId;
    private static final String TAG = "MainActivity";
    private SplitInstallManager splitInstallManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnaddfeature = findViewById(R.id.btn_addfeature);
        btn_startactivity = findViewById(R.id.btn_activity);

        splitInstallManager = SplitInstallManagerFactory.create(getApplicationContext());

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Module Downloading ....");
        btnaddfeature.setOnClickListener(view -> {
            progressDialog.show();
            downloadDynamicModule();
        });

        btn_startactivity.setOnClickListener(V ->
        {
            Intent i = new Intent();
            i.setClassName(BuildConfig.APPLICATION_ID, "com.onlineaavedan.capturephotos.CaptureActivity");
            i.putExtra("ExtraInt", 3); // Test intent for Dynamic feature
            startActivity(i);

        });
    }

    private void downloadDynamicModule() {


        SplitInstallRequest request = SplitInstallRequest.newBuilder().addModule(getString(R.string.title_capturephotos)).build();

        SplitInstallStateUpdatedListener listener = new SplitInstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(SplitInstallSessionState splitInstallSessionState) {
                if (splitInstallSessionState.sessionId() == mySessionId) {
                    switch (splitInstallSessionState.status()) {
                        case SplitInstallSessionStatus.DOWNLOADING:
                            Toast.makeText(MainActivity.this, "Dynamic Module downloading", Toast.LENGTH_SHORT).show();
                            // Update progress bar.
                            break;
                        case SplitInstallSessionStatus.INSTALLED:
                            Log.d(TAG, "Dynamic Module downloaded");
                            Toast.makeText(MainActivity.this, "Dynamic Module downloaded", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Session Not Created", Toast.LENGTH_SHORT).show();
                }
            }
        };

        splitInstallManager.registerListener(listener);

        splitInstallManager.startInstall(request).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Failed to Install " + e, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "ExceptionV: " + e);
            }
        }).addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer sessionId) {
                mySessionId = sessionId;
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Success" + sessionId, Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setClassName(BuildConfig.APPLICATION_ID, "com.onlineaavedan.capturephotos.CaptureActivity");
                i.putExtra("ExtraInt", 3); // Test intent for Dynamic feature
                startActivity(i);
            }
        });
    }
}
