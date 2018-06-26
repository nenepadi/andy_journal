package io.github.nenepadi.djorna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String mPhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mTest = findViewById(R.id.txt_testing);
        Intent mIntent = getIntent();
        String mDisplayName = mIntent.getStringExtra("profile_name");
        String mEmail = mIntent.getStringExtra("profile_email");

        mTest.setText(String.format("%s: %s", mDisplayName, mEmail));
    }
}
