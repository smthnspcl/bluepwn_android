package io.eberlein.insane.bluepwn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.eberlein.insane.bluepwn.R;
import io.eberlein.insane.bluepwn.object.Scan;


public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder>{
    private List<Scan> scans;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View v, int p);
    }

    public ScanAdapter() {
        scans = new ArrayList<>();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.deviceCountLabel) TextView deviceCountLabel;
        @BindView(R.id.locationCountLabel) TextView locationCountLabel;

        Context context;

        ViewHolder(Context context, View v){
            super(v);
            this.context = context;
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int p = getAdapterPosition();
                        if(p != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, p);
                        }
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ScanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        return new ViewHolder(parent.getContext(), i.inflate(R.layout.viewholder_scans_item, parent, false));
    }

    public void onBindViewHolder(ScanAdapter.ViewHolder holder, int position) {
        Scan s = scans.get(position);
        holder.deviceCountLabel.setText(String.valueOf(s.getDevices().size()));
        holder.locationCountLabel.setText(String.valueOf(s.getLocations().size()));
    }

    @Override
    public int getItemCount() {
        return scans.size();
    }

    public void addAll(List<Scan> scans) {
        this.scans = scans;
    }

    public void add(Scan scan) {
        for(Scan s : scans){
            if (s.getUuid().equals(scan.getUuid())) {
                scans.set(scans.indexOf(s), scan);
                notifyItemChanged(scans.indexOf(scan));
                return;
            }
        }
        scans.add(scan);
        notifyItemChanged(scans.indexOf(scan));
    }

    public Scan get(int index) {
        return scans.get(index);
    }

    public List<Scan> get() {
        return scans;
    }

    public void empty() {
        scans = new ArrayList<>();
    }
}

