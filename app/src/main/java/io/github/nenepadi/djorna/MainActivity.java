package io.github.nenepadi.djorna;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.github.nenepadi.djorna.database.DjornaEntry;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DjornaEntryAdapter adapter;
    private DjornaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_entries);
        adapter = new DjornaEntryAdapter(MainActivity.this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DjornaViewModel.class);
        viewModel.getAllEntries().observe(this, new Observer<List<DjornaEntry>>() {
            @Override
            public void onChanged(@Nullable List<DjornaEntry> djornaEntries) {
                adapter.setEntries(djornaEntries);
            }
        });
    }
}
