package io.github.nenepadi.djorna;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateEntryActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_REPLY_DATE = "io.github.nenepadi.djorna.REPLY_DATE";
    public static final String EXTRA_REPLY_STATUS = "io.github.nenepadi.djorna.REPLY_STATUS";
    public static final String EXTRA_REPLY_DETAIL = "io.github.nenepadi.djorna.REPLY_DETAIL";

    private TextView tvDateOfMonth;
    private TextView tvMonthYear;
    private TextView tvTime;
    private TextView tvDayOfWeek;
    private EditText etEntryEditor;
    private String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        tvDateOfMonth = findViewById(R.id.tv_date_of_month);
        tvDayOfWeek = findViewById(R.id.tv_day_of_week);
        tvMonthYear = findViewById(R.id.tv_month_and_year);
        tvTime = findViewById(R.id.tv_time_am_pm);
        etEntryEditor = findViewById(R.id.et_entry_editor);

        findViewById(R.id.btn_save).setOnClickListener(CreateEntryActivity.this);
        findViewById(R.id.btn_draft).setOnClickListener(CreateEntryActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_save:
                createEntry(2);
                break;

            case R.id.btn_draft:
                createEntry(1);
                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onStart() {
        super.onStart();
        Date newDate = new Date();

        tvDayOfWeek.setText(formatter("EEEE").format(newDate));
        tvDateOfMonth.setText(formatter("dd").format(newDate));
        tvMonthYear.setText(formatter("MMMM yyyy").format(newDate));
        tvTime.setText(formatter("h:mm a").format(newDate));
        stringDate = formatter("EEEE, MMMM dd, yyyy h:mm a").format(newDate);
    }

    private void createEntry(int status){
        Intent createIntent = new Intent();
        if(TextUtils.isEmpty(etEntryEditor.getText())){
            setResult(RESULT_CANCELED, createIntent);
        } else{
            String entryDetail = etEntryEditor.getText().toString();
            createIntent.putExtra(EXTRA_REPLY_DATE, stringDate);
            createIntent.putExtra(EXTRA_REPLY_STATUS, status);
            createIntent.putExtra(EXTRA_REPLY_DETAIL, entryDetail);
            setResult(RESULT_OK, createIntent);
        }

        finish();
    }

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat formatter(String pattern){
        return new SimpleDateFormat(pattern);
    }
}
