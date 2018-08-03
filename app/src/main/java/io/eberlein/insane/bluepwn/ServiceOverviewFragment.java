package io.eberlein.insane.bluepwn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceOverviewFragment extends Fragment {
    @BindView(R.id.name) EditText name;
    @BindView(R.id.uuid) EditText uuid;

    private Service service;

    public static ServiceOverviewFragment newInstance(int p, Service s){
        ServiceOverviewFragment sof = new ServiceOverviewFragment();
        Bundle b = new Bundle();
        b.putString("uuid", s.uuid);
        b.putString("name", s.name);
        sof.setArguments(b);
        return sof;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service_overview_tab, container, false);
        ButterKnife.bind(this, v);
        name.setText(getArguments().getString("name"));
        uuid.setText(getArguments().getString("uuid"));
        return v;
    }
}