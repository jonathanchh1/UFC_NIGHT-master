package com.example.jonat.services.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jonat.services.Activities.EventDetailActivity;
import com.example.jonat.services.Adapters.EventAdapter;
import com.example.jonat.services.ApiClient;
import com.example.jonat.services.ApiInterface;
import com.example.jonat.services.Models.Events;
import com.example.jonat.services.R;
import com.example.jonat.services.data.UFCContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.jonat.services.Fragments.MediasFragment.TAG;

/**
 * Created by jonat on 10/11/2017.
 */

public class EventFragment extends Fragment {

    public final static String EVENTS = "events";
    private final static String FAVORITE = "favorite";
    private static final String[] EVENTS_COLUMNS = {
            UFCContract.UFCEntry._ID,
            UFCContract.UFCEntry.COLUMN_EVENT_ID,
            UFCContract.UFCEntry.COLUMN_EVENT_DATE,
            UFCContract.UFCEntry.COLUMN_SECONDARY_IMAGE,
            UFCContract.UFCEntry.COLUMN_FEATURE_IMAGE,
            UFCContract.UFCEntry.COLUMN_TICKET_IMAGE,
            UFCContract.UFCEntry.COLUMN_EVENTS_TIME,
            UFCContract.UFCEntry.COLUMN_DESCRIPTION,
            UFCContract.UFCEntry.COLUMN_END_EVENT,
            UFCContract.UFCEntry.COLUMN_TICKET_URL,
            UFCContract.UFCEntry.COLUMN_TICKET_SELLER,
            UFCContract.UFCEntry.COLUMN_TITLE,
            UFCContract.UFCEntry.COLUMN_TITLE_TAG,
            UFCContract.UFCEntry.COLUMN_TICKET,
            UFCContract.UFCEntry.COLUMN_SUBTITLE,
            UFCContract.UFCEntry.COLUMN_EVENTS_STATUS,
            UFCContract.UFCEntry.COLUMN_CORNER_AUDIO,
            UFCContract.UFCEntry.COLUMN_ARENA,
            UFCContract.UFCEntry.COLUMN_LOCATION
    };
    public ProgressBar progressBar;
    private EventAdapter.Callbacks mCallbacks;
    private String mSortBy = EVENTS;
    private EventAdapter mAdapter;
    private List<Events> items;
    private ApiInterface apiService;
    private RecyclerView recyclerView;

    public EventFragment() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.recyclerviewlist, container, false);


        recyclerView = rootView.findViewById(R.id.mrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = rootView.findViewById(R.id.progress_bar);

        mCallback();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EVENTS)) {
                mSortBy = savedInstanceState.getString(EVENTS);
            }

            if (savedInstanceState.containsKey(EVENTS)) {
                items = savedInstanceState.getParcelableArrayList(EVENTS);
                mAdapter.setData(items);
            } else {
                fetchEvent(mSortBy);
            }
        } else {
            fetchEvent(mSortBy);
        }
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ufc_list, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        switch (mSortBy) {
            case EVENTS:
                menu.findItem(R.id.sort_by_events).setChecked(true);
                break;
            case FAVORITE:
                menu.findItem(R.id.sort_by_favorites).setChecked(true);
                break;
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_events:
                mSortBy = EVENTS;
                SortByEvent(mSortBy);
                item.setChecked(true);
                break;

            case R.id.sort_by_favorites:
                mSortBy = FAVORITE;
                Log.d(TAG, "favorite pressed");
                SortByEvent(mSortBy);
                item.setChecked(true);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void SortByEvent(String mSortBy) {
        if (!mSortBy.contentEquals(FAVORITE)) {
            fetchEvent(mSortBy);
        } else {
            new FetchFav(getContext()).execute(FAVORITE);
        }
    }

    private void fetchEvent(String mSortBy) {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Events>> call = apiService.getEvents(mSortBy);
        call.enqueue(new Callback<List<Events>>() {
            @Override
            public void onResponse(Call<List<Events>> call, Response<List<Events>> response) {
                int statusCode = response.code();
                items = response.body();
                recyclerView.setAdapter(mAdapter = new EventAdapter(items, R.layout.content_container, getActivity(), mCallbacks));
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<Events>> call, Throwable t) {
                // Log error here since request failed
                progressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, t.toString() + getResources().getString(R.string.noconnection));
            }
        });


    }

    public void mCallback() {
        mCallbacks = new EventAdapter.Callbacks() {

            @Override
            public void onItemCompleted(Events items, int position) {
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra(EventDetailActivity.Args, items);
                startActivity(intent);

            }

        };
    }

    public class FetchFav extends AsyncTask<String, Void, List<Events>> {

        private Context mContext;


        //constructor
        public FetchFav(Context context) {
            mContext = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Events> doInBackground(String... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    UFCContract.UFCEntry.CONTENT_URI,
                    EVENTS_COLUMNS,
                    null,
                    null,
                    null
            );

            return getFavEventsFromCursor(cursor);
        }

        @Override
        protected void onPostExecute(List<Events> events) {
            //we got Fav movies so let's show them
            if (events != null) {
                if (mAdapter != null) {
                    mAdapter.setData(events);
                }
                items = new ArrayList<>();
                items.addAll(events);

                Log.d(TAG, "Favorites :" + items.toString());
            } else {
                Log.d(TAG, getString(R.string.nofav));
            }
        }

        private List<Events> getFavEventsFromCursor(Cursor cursor) {
            List<Events> results = new ArrayList<>();
            //if we have data in database for Fav. movies.
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Events events = new Events(cursor);
                    results.add(events);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return results;
        }
    }
}
