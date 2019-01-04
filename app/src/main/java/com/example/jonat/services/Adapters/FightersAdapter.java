package com.example.jonat.services.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonat.services.Models.Fighters;
import com.example.jonat.services.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jonat on 10/11/2017.
 */

public class FightersAdapter extends RecyclerView.Adapter<FightersAdapter.FightersViewHolder> {

    private List<Fighters> itemsList;
    private int rowLayout;
    private Context context;
    private final Callbacks mCallbacks;



    public static class FightersViewHolder extends RecyclerView.ViewHolder {
        TextView ufcTitle;
        ImageView thumbnail;
        ImageButton FavoriteButton;
        public Fighters items;
        View mView;


        public FightersViewHolder(View v) {
            super(v);
            mView = v;
            ufcTitle = v.findViewById(R.id.title);
            thumbnail = v.findViewById(R.id.thumbnail);

        }
    }

    public FightersAdapter(List<Fighters> fighters, int rowLayout, Context context, Callbacks mCallbacks) {
        this. itemsList = fighters;
        this.rowLayout = rowLayout;
        this.context = context;
        this.mCallbacks = mCallbacks;
    }

    @Override
    public FightersAdapter.FightersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FightersAdapter.FightersViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final FightersAdapter.FightersViewHolder holder, final int position) {

        final Fighters mItems =  itemsList.get(position);
        holder.items = mItems;

        String poster = mItems.getThumbnail();


        if (!TextUtils.isEmpty(mItems.getThumbnail())) {
            Picasso.with(context).load(poster)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail,
                            new Callback() {
                                @Override
                                public void onSuccess() {
                                    if (holder.thumbnail != null) {
                                        holder.thumbnail.setVisibility(View.VISIBLE);
                                    } else {
                                        holder.thumbnail.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onError() {
                                    holder.thumbnail.setVisibility(View.VISIBLE);
                                }
                            });


        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onItemCompleted(mItems, holder.getAdapterPosition());
            }
        });

        holder.ufcTitle.setText(mItems.getLast_name());

    }


    @Override
    public int getItemCount() {
        return  itemsList.size();
    }




    public interface Callbacks {
        void onItemCompleted(Fighters items, int position);


    }
}

