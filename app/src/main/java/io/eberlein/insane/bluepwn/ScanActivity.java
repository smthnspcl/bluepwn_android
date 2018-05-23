package io.eberlein.insane.bluepwn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import butterknife.BindView;
import butterknife.OnClick;

public class ScanActivity extends AppCompatActivity {

    @BindView(R.id.locationLabel) TextView location;
    @BindView(R.id.devicesRecycler) RecyclerView devices;
    @BindView(R.id.filterSpinner) Spinner filters;

    private DeviceAdapter deviceAdapter;
    private static final String[] selectionSpinnerAdapterItems = {
            "person unk.", "location unk."
    };

    @OnClick(R.id.locationLabel)
    public void locationLabelClicked(){
        // todo open location activity
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Scan s = SQLite.select().from(Scan.class).where(Scan_Table.id.eq(Long.valueOf(getIntent().getStringExtra("id")))).querySingle();
        location.setText(String.valueOf(s.location.latitude) + ", " + String.valueOf(s.location.longitude));
        deviceAdapter = new DeviceAdapter();
        deviceAdapter.addAll(s.devices);
        devices.setLayoutManager(new LinearLayoutManager(this));
        devices.setAdapter(deviceAdapter);
        filters.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, selectionSpinnerAdapterItems));
    }
}