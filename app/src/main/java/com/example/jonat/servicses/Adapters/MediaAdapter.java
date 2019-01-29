package com.example.jonat.servicses.Adapters;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonat.services.Models.Medias;
import com.example.jonat.services.Queries;
import com.example.jonat.services.R;
import com.example.jonat.services.data.UFCContract;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jonat on 10/11/2017.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    private final Callbacks mCallbacks;
    private final Medias mMedias = new Medias();
    private List<Medias> itemsList;
    private int rowLayout;
    private Context context;

    public MediaAdapter(List<Medias> medias, int rowLayout, Context context, Callbacks mCallbacks) {
        this. itemsList = medias;
        this.rowLayout = rowLayout;
        this.context = context;
        this.mCallbacks = mCallbacks;
    }

    public void setData(List<Medias> data) {
        remove();
        for (Medias medias : data) {
            add(medias);
        }
    }

    private void remove() {
        synchronized (mMedias) {
            itemsList.clear();
        }
        notifyDataSetChanged();
    }

    private void add(Medias medias) {
        synchronized (mMedias) {
            itemsList.add(medias);
        }
        notifyDataSetChanged();
    }

    @Override
    public MediaAdapter.MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MediaAdapter.MediaViewHolder(view);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final MediaAdapter.MediaViewHolder holder, final int position) {

        final Medias mItems =  itemsList.get(position);
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

        holder.ufcTitle.setText(mItems.getTitle());
        holder.description.setText(mItems.getDescription());
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, mItems.getTitle());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, mItems.getDescription());
                context.startActivity(Intent.createChooser(sharingIntent, "sharing Option"));

            }
        });
        //set  icon on toolbar for favorited items
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return Queries.isFavoritedMedia(context, mItems.getId());
            }

            @Override
            protected void onPostExecute(Integer isFavored) {
                holder.FavoriteButton.setImageResource(isFavored == 1 ?
                        R.drawable.ic_favorite_black_24dp :
                        R.drawable.ic_favorite_border_black_24dp);
            }
        }.execute();

        holder.FavoriteButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                // check if events is favorited or not
                new AsyncTask<Void, Void, Integer>() {

                    @Override
                    protected Integer doInBackground(Void... params) {
                        return Queries.isFavoritedMedia(context, mItems.getId());
                    }

                    @Override
                    protected void onPostExecute(Integer isFavored) {
                        // if it is in favorites
                        if (isFavored == 1) {
                            // delete from favorites
                            new AsyncTask<Void, Void, Integer>() {
                                @Override
                                protected Integer doInBackground(Void... params) {
                                    return context.getContentResolver().delete(
                                            UFCContract.MediaEntry.CONTENT_URI,
                                            UFCContract.MediaEntry.COLUMN_MEDIA_ID + " = ?",
                                            new String[]{Integer.toString(mItems.getId())}
                                    );
                                }

                                @Override
                                protected void onPostExecute(Integer rowsDeleted) {
                                    holder.FavoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                                }
                            }.execute();
                        }
                        // if it is not in favorites
                        else {
                            // add to favorites
                            new AsyncTask<Void, Void, Uri>() {
                                @Override
                                protected Uri doInBackground(Void... params) {
                                    ContentValues values = new ContentValues();
                                    values.put(UFCContract.MediaEntry.COLUMN_MEDIA_ID, mItems.getId());
                                    values.put(UFCContract.MediaEntry.COLUMN_MEDIA_DATE, mItems.getMedia_date());
                                    values.put(UFCContract.MediaEntry.COLUMN_TYPE, mItems.getType());
                                    values.put(UFCContract.MediaEntry.COLUMN_DESCR, mItems.getDescription());
                                    values.put(UFCContract.MediaEntry.COLUMN_MORE_LINK, mItems.getMore_link_text());
                                    values.put(UFCContract.MediaEntry.COLUMN_THUMBNAIL, mItems.getThumbnail());
                                    values.put(UFCContract.MediaEntry.COLUMN_INTERNAL_URL, mItems.getInternal_url());
                                    values.put(UFCContract.MediaEntry.COLUMN_MEDIA_TITLE, mItems.getTitle());
                                    values.put(UFCContract.MediaEntry.COLUMN_MORE_LINKURL, mItems.getMore_linkurl());
                                    values.put(UFCContract.MediaEntry.COLUMN_LAST_MODIFIED, mItems.getLast_modified());
                                    values.put(UFCContract.MediaEntry.COLUMN_URL_NAME, mItems.getUrl_name());
                                    values.put(UFCContract.MediaEntry.COLUMN_PUBLISHED, mItems.getPublished_start_date());
                                    return context.getContentResolver().insert(UFCContract.MediaEntry.CONTENT_URI, values);
                                }

                                @Override
                                protected void onPostExecute(Uri returnUri) {
                                    holder.FavoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                                }
                            }.execute();
                        }
                    }
                }.execute();
            }
        });

    }

    @Override
    public int getItemCount() {
        return  itemsList.size();
    }


    public interface Callbacks {
        void onItemCompleted(Medias items, int position);


    }

    public static class MediaViewHolder extends RecyclerView.ViewHolder {
        public Medias items;
        TextView ufcTitle;
        ImageView thumbnail;
        TextView description;
        ImageButton shareButton;
        ImageButton FavoriteButton;
        View mView;


        public MediaViewHolder(View v) {
            super(v);
            mView = v;
            description = v.findViewById(R.id.sub_title);
            ufcTitle = v.findViewById(R.id.title);
            thumbnail = v.findViewById(R.id.thumbnail);
            FavoriteButton = v.findViewById(R.id.favorite_button);
            shareButton = v.findViewById(R.id.share_button);

        }
    }
}


