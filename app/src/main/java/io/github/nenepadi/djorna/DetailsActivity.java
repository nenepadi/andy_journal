package io.github.nenepadi.djorna;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import io.github.nenepadi.djorna.database.DjornaEntry;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvEntryDetail;
    private EditText editText;
    private int mEntryId;
    private DjornaDetailModel viewModel;
    private DjornaEntry mDjornaEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvEntryDetail = findViewById(R.id.tv_entry_detail);
        Intent intent = getIntent();
        mEntryId = intent.getIntExtra("entry_id", 0);

        editText = findViewById(R.id.et_entry_editor);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_draft).setOnClickListener(this);

        setupViewModel();
    }

    private void setupViewModel(){
        DjornaDetailModelFactory vmFactory = new DjornaDetailModelFactory(
                this.getApplication(), mEntryId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(DjornaDetailModel.class);
        viewModel.getEntry().observe(this, new Observer<DjornaEntry>() {
            @Override
            public void onChanged(@Nullable DjornaEntry djornaEntry) {
                if (djornaEntry != null) {
                    mDjornaEntry = djornaEntry;
                    updateUI(true, djornaEntry);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit_entry:
                updateUI(false, mDjornaEntry);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI(boolean display_or_edit, DjornaEntry djornaEntry) {
        String text1 = (djornaEntry.getStatus() == 2) ? "felt" : "was feeling";
        String text2 = formatter("dd/MM/yy").format(djornaEntry.getCreatedAt());
        String text3 = formatter("hh:mm a").format(djornaEntry.getCreatedAt());

        String title = getString(R.string.detail_app_bar_title, text1, text2, text3);
        getSupportActionBar().setTitle(title);

        if(display_or_edit){
            findViewById(R.id.linear_layout).setVisibility(View.GONE);
            findViewById(R.id.scroll_view).setVisibility(View.VISIBLE);
            tvEntryDetail.setText(djornaEntry.getDetails());
        } else{
            findViewById(R.id.scroll_view).setVisibility(View.GONE);
            findViewById(R.id.linear_layout).setVisibility(View.VISIBLE);
            editText.setText(djornaEntry.getDetails());
        }
    }

    private void editEntry(int status){
        mDjornaEntry.setDetails(editText.getText().toString());
        mDjornaEntry.setStatus(status);
        viewModel.update(mDjornaEntry);
        updateUI(true, mDjornaEntry);
        Toast.makeText(DetailsActivity.this, "Entry successfully updated", Toast.LENGTH_LONG).show();
    }


    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat formatter(String pattern){
        return new SimpleDateFormat(pattern);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_save:
                editEntry(2);
                break;
            case R.id.btn_draft:
                editEntry(1);
                break;
            default:
                break;
        }
    }
}
