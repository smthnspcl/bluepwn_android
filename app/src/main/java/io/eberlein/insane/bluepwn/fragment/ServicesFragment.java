package io.eberlein.insane.bluepwn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.eberlein.insane.bluepwn.R;
import io.eberlein.insane.bluepwn.Static;
import io.eberlein.insane.bluepwn.activity.ServiceActivity;
import io.eberlein.insane.bluepwn.adapter.ServiceAdapter;
import io.eberlein.insane.bluepwn.object.Service;

public class ServicesFragment extends Fragment {

    @BindView(R.id.query) AutoCompleteTextView query;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.recycler) RecyclerView recycler;

    private ServiceAdapter serviceAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("services");
        serviceAdapter = new ServiceAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.objectlist_search, container, false);
        ButterKnife.bind(this, v);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceAdapter.addAll(Service.get());
        recycler.setAdapter(serviceAdapter);
        serviceAdapter.setOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int p) {
                Intent i = new Intent(getContext(), ServiceActivity.class);
                // switch between actions using this uuid and devices having that uuid
                // display list in recycler
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("uuid", serviceAdapter.get(p).getUuid());
                startActivity(i);
            }
        });
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, Static.SERVICE_KEYS));
        return v;
    }
}
