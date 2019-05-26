package com.Provendor.Provendor;

import android.os.Bundle;

import android.view.SearchEvent;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.algolia.instantsearch.core.helpers.Searcher;
import com.algolia.instantsearch.ui.helpers.InstantSearch;
import com.algolia.instantsearch.ui.helpers.InstantSearch;
import com.algolia.instantsearch.ui.helpers.InstantSearch;
import com.algolia.instantsearch.ui.utils.ItemClickSupport;
import com.algolia.instantsearch.ui.views.Hits;
import com.algolia.search.saas.Query;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;


import com.algolia.instantsearch.ui.viewmodels.SearchBoxViewModel;
import com.algolia.instantsearch.ui.views.SearchBox;



public class Searchpagevideos extends AppCompatActivity {

    private static final String ALGOLIA_APP_ID = "AK3H70NKN5";
    private static final String ALGOLIA_SEARCH_API_KEY = "2c8a8880eff5c3664adaa84af4b2bfad";
    private static final String ALGOLIA_INDEX_NAME = "�Provendor�";
    private Searcher searcher;


    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }

    private InstantSearch helper;
    public static String query;

    public static boolean comingfromother = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searchpagevideos);
        final Hits hits = (Hits) findViewById(R.id.hits);

        searcher = searcher.create(ALGOLIA_APP_ID, ALGOLIA_SEARCH_API_KEY, ALGOLIA_INDEX_NAME);
        helper = new InstantSearch(this, searcher);
        helper.search();

        hits.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {


            public void onItemClick(RecyclerView recyclerView, int position, View view) {
                JSONObject hit = hits.get(position);
                VideoView.fromSearchy=true;
                VideoView.videoy=hit;
                startActivity(new Intent(Searchpagevideos.this, VideoView.class));

            }
        });


    }

    @Override
    protected void onDestroy() {
        searcher.destroy();
        super.onDestroy();
    }
}



