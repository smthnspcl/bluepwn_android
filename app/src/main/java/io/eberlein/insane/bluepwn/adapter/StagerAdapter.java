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
import io.eberlein.insane.bluepwn.object.Stager;

public class StagerAdapter extends RecyclerView.Adapter<StagerAdapter.ViewHolder>{
    private List<Stager> stagers;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View v, int p);
    }

    public StagerAdapter() {
        stagers = new ArrayList<>();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;

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

    public StagerAdapter(List<Stager> stagers) {
        this.stagers = stagers;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public StagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        return new ViewHolder(parent.getContext(), i.inflate(R.layout.viewholder_stagers_item, parent, false));
    }

    public void onBindViewHolder(StagerAdapter.ViewHolder holder, int position) {
        Stager a = stagers.get(position);
        holder.name.setText(a.getName());
    }

    @Override
    public int getItemCount() {
        return stagers.size();
    }

    public void add(Stager stager) {
        for(Stager s : stagers){
            if (s.getUuid().equals(stager.getUuid())) {
                stagers.set(stagers.indexOf(s), stager);
                notifyItemChanged(stagers.indexOf(stager));
                return;
            }
        }
        stagers.add(stager);
        notifyItemChanged(stagers.indexOf(stager));
    }

    public void addAll(List<Stager> stagers) {
        for(Stager s : stagers) add(s);
    }

    public void empty() {
        stagers = new ArrayList<>();
    }

    public Stager get(int index) {
        return stagers.get(index);
    }
}

