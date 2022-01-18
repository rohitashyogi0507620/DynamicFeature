package com.yogify.dynamicfeature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity {

    Button btnaddfeature;
    int resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnaddfeature = findViewById(R.id.btn_addfeature);
        btnaddfeature.setOnClickListener(view -> {

            SplitInstallManager splitInstallManager = SplitInstallManagerFactory.create(getApplicationContext());
            SplitInstallRequest splitInstallRequest = SplitInstallRequest.newBuilder().addModule(getString(R.string.title_dynamicfeature)).build();
            splitInstallManager.startInstall(splitInstallRequest)
                    .addOnSuccessListener(new OnSuccessListener<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            Toast.makeText(getApplicationContext(), "Install SuccessFully", Toast.LENGTH_SHORT).show();
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {

                    Toast.makeText(getApplicationContext(), "Failed ..", Toast.LENGTH_SHORT).show();

                }
            });
        });
    }
}