package io.eberlein.insane.bluepwn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationsActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView recycler;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.query) EditText query;
    @BindView(R.id.addRecyclerItem) FloatingActionButton addRecyclerItem;

    private static final String[] searchSpinnerItems = {
            "longitude", "latitude", "timestamp", "zip", "city", "address"
    };

    private LocationAdapter locations;
    private Gson gson;

    @OnClick(R.id.addRecyclerItem)
    public void addRecyclerItemBtnClicked(){
        Intent i = new Intent(this, LocationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.onCreate(this.getClass());
        setContentView(R.layout.objectlist_search);
        ButterKnife.bind(this);
        setTitle("locations");
        gson = new Gson();
        locations = new LocationAdapter();
        String e = getIntent().getStringExtra("locations");
        if(e != null) populateWithSuppliedLocations(e);
        else locations.addAll(Location.get());
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setAdapter(locations);
        locations.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int p) {
                Intent i = new Intent(getApplicationContext(), LocationActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("uuid", locations.get(p).uuid);
                startActivity(i);
            }
        });
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, searchSpinnerItems));
    }

    private void populateWithSuppliedLocations(String data){
        List<Location> locations = gson.fromJson(data, new TypeToken<List<Location>>(){}.getType());
        this.locations.addAll(locations);
    }
}
