package com.example.jonat.servicehs.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jonat.services.Activities.EventDetailActivity;
import com.example.jonat.services.Adapters.MediaAdapter;
import com.example.jonat.services.ApiClient;
import com.example.jonat.services.ApiInterface;
import com.example.jonat.services.Models.Medias;
import com.example.jonat.services.R;
import com.example.jonat.services.data.UFCContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jonat on 10/11/2017.
 */

public class MediasFragment extends Fragment {

    public static final String TAG = MediasFragment.class.getSimpleName();
    public final static String MEDIA = "media";
    private final static String FAVORITE = "favorite";
    private static final String[] MEDIA_COLUMNS = {
            UFCContract.MediaEntry._ID,
            UFCContract.MediaEntry.COLUMN_MEDIA_ID,
            UFCContract.MediaEntry.COLUMN_MEDIA_DATE,
            UFCContract.MediaEntry.COLUMN_TYPE,
            UFCContract.MediaEntry.COLUMN_DESCR,
            UFCContract.MediaEntry.COLUMN_MORE_LINK,
            UFCContract.MediaEntry.COLUMN_THUMBNAIL,
            UFCContract.MediaEntry.COLUMN_INTERNAL_URL,
            UFCContract.MediaEntry.COLUMN_MEDIA_TITLE,
            UFCContract.MediaEntry.COLUMN_MORE_LINKURL,
            UFCContract.MediaEntry.COLUMN_LAST_MODIFIED,
            UFCContract.MediaEntry.COLUMN_URL_NAME,
            UFCContract.MediaEntry.COLUMN_PUBLISHED,
    };
    public ProgressBar progressBar;
    private MediaAdapter.Callbacks mCallbacks;
    private String mSortBy = MEDIA;
    private ApiInterface apiService;
    private RecyclerView recyclerView;
    private List<Medias> items;
    private MediaAdapter mAdapter;

    public MediasFragment() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.recyclerviewlist, container, false);


        recyclerView = rootView.findViewById(R.id.mrecyclerview);
        recyclerView.setHasFixedSize(true);
        //setup span size expanding.
        //Create a grid layout with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                getResources().getInteger(R.integer.number));

        //setup span size expanding.
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        progressBar = rootView.findViewById(R.id.progress_bar);


        mCallback();
        fetchMedia(mSortBy);

        return rootView;

    }

    private void fetchMedia(String mSortBy) {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Medias>> call = apiService.getMedia(mSortBy);
        call.enqueue(new Callback<List<Medias>>() {
            @Override
            public void onResponse(Call<List<Medias>> call, Response<List<Medias>> response) {
                int statusCode = response.code();
                items = response.body();
                recyclerView.setAdapter(mAdapter = new MediaAdapter(items, R.layout.content_media, getActivity(), mCallbacks));
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<Medias>> call, Throwable t) {
                // Log error here since request failed
                Throwable error = t;
                progressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, call + String.valueOf(t) + getResources().getString(R.string.noconnection));
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.media_list, menu);

        switch (mSortBy) {
            case MEDIA:
                menu.findItem(R.id.sort_by_media).setChecked(true);
                break;
            case FAVORITE:
                menu.findItem(R.id.sort_by_favorite).setChecked(true);
                break;
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_media:
                mSortBy = MEDIA;
                SortByMedia(mSortBy);
                item.setChecked(true);
                break;

            case R.id.sort_by_favorite:
                mSortBy = FAVORITE;
                Log.d(TAG, "favorite pressed");
                SortByMedia(mSortBy);
                item.setChecked(true);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void SortByMedia(String mSortBy) {
        if (!mSortBy.contentEquals(FAVORITE)) {
            fetchMedia(mSortBy);
        } else {
            new FetchFav(getContext()).execute(FAVORITE);
        }
    }


    public void mCallback() {
        mCallbacks = new MediaAdapter.Callbacks() {

            @Override
            public void onItemCompleted(Medias items, int position) {
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra(EventDetailActivity.Args, items);
                startActivity(intent);

            }

        };
    }

    public class FetchFav extends AsyncTask<String, Void, List<Medias>> {

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
        protected List<Medias> doInBackground(String... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    UFCContract.MediaEntry.CONTENT_URI,
                    MEDIA_COLUMNS,
                    null,
                    null,
                    null
            );

            return getFavMediaFromCursor(cursor);
        }

        @Override
        protected void onPostExecute(List<Medias> medias) {
            //we got Fav movies so let's show them
            if (medias != null) {
                if (mAdapter != null) {
                    mAdapter.setData(medias);
                }
                items = new ArrayList<>();
                items.addAll(medias);

                Log.d(TAG, "Favorites :" + items.toString());
            } else {
                Log.d(TAG, getString(R.string.nofav));
            }
        }

        private List<Medias> getFavMediaFromCursor(Cursor cursor) {
            //if we have data in database for Fav. movies.
            List<Medias> results = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Medias medias = new Medias(cursor);
                    results.add(medias);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return results;
        }
    }
}
