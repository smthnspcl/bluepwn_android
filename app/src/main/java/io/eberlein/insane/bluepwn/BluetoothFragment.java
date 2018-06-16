package io.eberlein.insane.bluepwn;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;



public class BluetoothFragment extends Fragment {
    @BindView(R.id.scanBtn) FloatingActionButton scanBtn;
    @BindView(R.id.devicesRecycler) RecyclerView deviceRecycler;
    @BindView(R.id.continuousScanningCheckbox) CheckBox continuousScanningCheckbox;

    @OnCheckedChanged(R.id.continuousScanningCheckbox)
    void continuousScanningCheckboxCheckedChanged(){
        if(bound) scanService.setContinuousScanning(!scanService.getContinuousScanning());
        else continuousScanningCheckbox.setChecked(!continuousScanningCheckbox.isChecked());
    }

    @OnClick(R.id.scanBtn)
    void scanBtnClicked(){
        if(bound) {
            if (scanService.isScanning()) {
                continuousScanningCheckbox.setChecked(false);
                scanService.cancelScanning();
            } else {
                scanService.scan();
            }
        }
    }

    private ScanService scanService;
    private DeviceAdapter devices;
    private Scan scan;
    private Boolean bound = false;

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onDeviceDiscovered(EventDeviceDiscovered e){
        // scan service must be bound
        devices.empty();
        devices.addAll();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onDiscoveryFinished(EventDiscoveryFinished e){
        scanBtn.setImageResource(R.drawable.ic_update_white_48dp);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onDiscoveryStarted(EventDiscoveryStarted e){
        scanBtn.setImageResource(R.drawable.ic_clear_white_48dp);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onToScanDevicesEmpty(EventToScanDevicesEmpty e){
        System.out.println("to scan devices empty");
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ScanService.ScanBinder binder = (ScanService.ScanBinder) service;
            scanService = binder.getService();
            bound = true;
            if (scanService.getContinuousScanning() || scanService.isScanning()){
                scanBtn.setImageResource(R.drawable.ic_clear_white_48dp);
            } else {
                scanBtn.setImageResource(R.drawable.ic_update_white_48dp);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
        devices = new DeviceAdapter();
    }

    void startService(){
        Intent i = new Intent(getActivity(), ScanService.class);
        getContext().bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if(scan == null) {scan = new Scan();}
        devices.addAll(scan.getDevices());
        startService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_bluetooth, container, false);
        ButterKnife.bind(this, v);
        scanBtn.setImageResource(R.drawable.ic_update_white_48dp);
        deviceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        deviceRecycler.setAdapter(devices);
        devices.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int p) {
                Intent i = new Intent(getContext(), DeviceActivity.class);
                i.putExtra("address", devices.get(p).address);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getContext().unbindService(serviceConnection);
    }

    private void requestPermissions(){
        if(!(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED))
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH}, Static.BLUETOOTH_RESULT);
        if(!(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Static.LOCATION_RESULT);
    }
}
