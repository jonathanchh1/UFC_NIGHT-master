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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonat.services.Models.Events;
import com.example.jonat.services.Queries;
import com.example.jonat.services.R;
import com.example.jonat.services.data.UFCContract;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonat on 10/8/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.UFCViewHolder> implements Filterable {

    private final Callbacks mCallbacks;
    private final Events mEvents = new Events();
    private List<Events> itemsList;
    private List<Events> mListfilterable;
    private int rowLayout;
    private Context context;

    public EventAdapter(List<Events> events, int rowLayout, Context context, Callbacks mCallbacks) {
        this. itemsList = events;
        this.rowLayout = rowLayout;
        this.context = context;
        this.mCallbacks = mCallbacks;
        this.mListfilterable = events;
    }

    public void setData(List<Events> data) {
        remove();
        for (Events events : data) {
            add(events);
        }
    }

    private void remove() {
        synchronized (mEvents) {
            mListfilterable.clear();
        }
        notifyDataSetChanged();
    }

    private void add(Events events) {
        synchronized (mEvents) {
            mListfilterable.add(events);
        }
        notifyDataSetChanged();
    }

    @Override
    public EventAdapter.UFCViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new UFCViewHolder(view);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final UFCViewHolder holder, final int position) {

        final Events mItems = mListfilterable.get(position);
        holder.items = mItems;

        String poster = mItems.getFeaturedImage();


        if (!TextUtils.isEmpty(mItems.getFeaturedImage())) {
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

        holder.ufcTitle.setText(mItems.getBase_title());
        holder.title_fighters.setText(mItems.getTitle_tag());


        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, mItems.getBase_title());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, mItems.getUrl_name());
                context.startActivity(Intent.createChooser(sharingIntent, "sharing Option"));
            }
        });

        //set  icon on toolbar for favorited items
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return Queries.isFavorited(context, mItems.getId());
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
                        return Queries.isFavorited(context, mItems.getId());
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
                                            UFCContract.UFCEntry.CONTENT_URI,
                                            UFCContract.UFCEntry.COLUMN_EVENT_ID + " = ?",
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
                                    values.put(UFCContract.UFCEntry.COLUMN_EVENT_ID, mItems.getId());
                                    values.put(UFCContract.UFCEntry.COLUMN_EVENT_DATE, mItems.getEventdate());
                                    values.put(UFCContract.UFCEntry.COLUMN_SECONDARY_IMAGE, mItems.getImage_feature());
                                    values.put(UFCContract.UFCEntry.COLUMN_FEATURE_IMAGE, mItems.getFeaturedImage());
                                    values.put(UFCContract.UFCEntry.COLUMN_TICKET_IMAGE, mItems.getTicket_image());
                                    values.put(UFCContract.UFCEntry.COLUMN_EVENTS_TIME, mItems.getEvent_time());
                                    values.put(UFCContract.UFCEntry.COLUMN_DESCRIPTION, mItems.getShort_desc());
                                    values.put(UFCContract.UFCEntry.COLUMN_END_EVENT, mItems.getEndeventdate());
                                    values.put(UFCContract.UFCEntry.COLUMN_TICKET_URL, mItems.getTicketurl());
                                    values.put(UFCContract.UFCEntry.COLUMN_TICKET_SELLER, mItems.getEventseller());
                                    values.put(UFCContract.UFCEntry.COLUMN_TITLE, mItems.getBase_title());
                                    values.put(UFCContract.UFCEntry.COLUMN_TITLE_TAG, mItems.getTitle_tag());
                                    values.put(UFCContract.UFCEntry.COLUMN_TICKET, mItems.getTicket_general_sales_text());
                                    values.put(UFCContract.UFCEntry.COLUMN_SUBTITLE, mItems.getSubtitle());
                                    values.put(UFCContract.UFCEntry.COLUMN_EVENTS_STATUS, mItems.getEventstatus());
                                    values.put(UFCContract.UFCEntry.COLUMN_CORNER_AUDIO, mItems.getCorneraudio());
                                    values.put(UFCContract.UFCEntry.COLUMN_ARENA, mItems.getArena());
                                    values.put(UFCContract.UFCEntry.COLUMN_LOCATION, mItems.getLocation());


                                    return context.getContentResolver().insert(UFCContract.UFCEntry.CONTENT_URI, values);
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
        return mListfilterable.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mListfilterable = itemsList;
                } else {

                    ArrayList<Events> filteredList = new ArrayList<>();

                    for (Events event : itemsList) {

                        if (event.getBase_title().toUpperCase().toLowerCase().contains(charString) ||
                                event.getFeaturedImage().toUpperCase().toLowerCase().contains(charString) ||
                                event.getTitle_tag().toUpperCase().toLowerCase().contains(charString)) {

                            filteredList.add(event);
                        }
                    }

                    mListfilterable = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListfilterable;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListfilterable = (ArrayList<Events>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }


    public interface Callbacks {
        void onItemCompleted(Events items, int position);

    }

    public static class UFCViewHolder extends RecyclerView.ViewHolder {
        public Events items;
        TextView ufcTitle;
        ImageView thumbnail;
        TextView title_fighters;
        ImageButton shareButton;
        ImageButton FavoriteButton;
        View mView;


        public UFCViewHolder(View v) {
            super(v);
            mView = v;
            title_fighters = v.findViewById(R.id.sub_title);
            ufcTitle = v.findViewById(R.id.title);
            thumbnail = v.findViewById(R.id.thumbnail);
            FavoriteButton = v.findViewById(R.id.favorite_button);
            shareButton = v.findViewById(R.id.share_button);

        }
    }
}

