package io.github.nenepadi.djorna;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.github.nenepadi.djorna.database.DjornaEntry;


public class MainActivity extends AppCompatActivity implements DjornaEntryAdapter.ItemClickListener {
    private DjornaEntryAdapter adapter;
    private DjornaViewModel viewModel;
    private Intent authIntent;

    public static final int CREATE_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    /*private GoogleSignInClient mClient;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        authIntent = getIntent();

        FloatingActionButton btnCreate = findViewById(R.id.btn_add);
        RecyclerView recyclerView = findViewById(R.id.rv_entries);
        adapter = new DjornaEntryAdapter(this, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration deco = new DividerItemDecoration(MainActivity.this, manager.getOrientation());
        recyclerView.addItemDecoration(deco);

        initViewModel();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEntryIntent = new Intent(MainActivity.this, CreateEntryActivity.class);
                startActivityForResult(addEntryIntent, CREATE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void initViewModel() {
        DjornaViewModelFactory vMFactory = new DjornaViewModelFactory(
                this.getApplication(), authIntent.getStringExtra("profile_email"));

        viewModel = ViewModelProviders.of(this, vMFactory).get(DjornaViewModel.class);
        viewModel.getAllEntries().observe(this, new Observer<List<DjornaEntry>>() {
            @Override
            public void onChanged(@Nullable List<DjornaEntry> djornaEntries) {
                adapter.setEntries(djornaEntries);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CREATE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
            String dateInString = data.getStringExtra(CreateEntryActivity.EXTRA_REPLY_DATE);

            try{
                Date actualDate = formatter.parse(dateInString);
                DjornaEntry djornaEntry = new DjornaEntry(
                        data.getStringExtra(CreateEntryActivity.EXTRA_REPLY_DETAIL),
                        authIntent.getStringExtra("profile_email"),
                        data.getIntExtra(CreateEntryActivity.EXTRA_REPLY_STATUS, 1),
                        actualDate);

                viewModel.insert(djornaEntry);
            } catch(ParseException e){
               Log.d(TAG, dateInString + " => Date not formatted!");
            }
        } else{
            Toast.makeText(getApplicationContext(), "Entry was not saved!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClickLister(int itemId) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("entry_id", itemId);
        startActivity(intent);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                signOut();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        mClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                 startActivity(intent);
            }
        });
    }*/
}
