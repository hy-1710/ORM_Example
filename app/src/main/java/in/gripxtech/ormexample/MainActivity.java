package in.gripxtech.ormexample;

import android.app.SearchManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import in.gripxtech.ormexample.adapter.MonthAdapter;
import in.gripxtech.ormexample.databinding.ActivityMainBinding;
import in.gripxtech.ormexample.orm.Month;

public class MainActivity extends AppCompatActivity {

    public static final String TAG;

    static {
        TAG = "MainActivity";
    }

    private ActivityMainBinding binding;
    private MonthAdapter adapter;
    private Loader<List<Month>> loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        insert record
//        Month month = new Month(0, "January", "It has 31 days");
//        month.save();

//        select record by id
//        Month month = Month.findById(Month.class, 1);

//        update record
//        Month month = Month.findById(Month.class, 1);
//        month.setDescription("January is First Month");
//        month.setNumber(1);
//        month.save();

//        delete record
//        Month month = Month.findById(Month.class, 1);
//        month.delete();

//        full query
//        List<Month> months = Month.find(Month.class, "NUMBER = ?", new String[]{"1"},
//                /*groupBy*/null, /*orderBy*/null, /*limit*/null);
//        for (Month month : months) {
//            Log.i(TAG, "onCreate: month is " + month);
//        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        fillTableMonth();

        adapter = new MonthAdapter(this, new ArrayList<Month>());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rv.setLayoutManager(linearLayoutManager);
        binding.rv.setAdapter(adapter);

        loader = getSupportLoaderManager().initLoader(
                0, savedInstanceState, new LoaderManager.LoaderCallbacks<List<Month>>() {
                    @Override
                    public Loader<List<Month>> onCreateLoader(int id, Bundle args) {
                        Log.i(TAG, "onCreateLoader: ");
                        return new AsyncTaskLoader<List<Month>>(MainActivity.this) {
                            @Override
                            public List<Month> loadInBackground() {
                                // Performing 'select * from month' query
                                return Month.listAll(Month.class);
                            }
                        };
                    }

                    @Override
                    public void onLoadFinished(Loader<List<Month>> loader, List<Month> data) {
                        Log.i(TAG, "onLoadFinished: ");
                        adapter.getMonths().addAll(data);
                        adapter.getMonthsClone().addAll(data);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLoaderReset(Loader<List<Month>> loader) {
                        Log.i(TAG, "onLoaderReset: ");
                    }
                }
        );
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loader.forceLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    private void fillTableMonth() {
        // inserting records
        // any kind of database operation should perform in background thread
        // like AsyncTask or AsyncTaskLoader
        // but, Right now I'm avoiding it for simplicity
        Month.deleteAll(Month.class);
        new Month(1, "January", "It has 31 days").save();
        new Month(2, "February", "It has 28 days").save();
        new Month(3, "March", "It has 31 days").save();
        new Month(4, "April", "It has 30 days").save();
        new Month(5, "May", "It has 31 days").save();
        new Month(6, "June", "It has 30 days").save();
        new Month(7, "July", "It has 31 days").save();
        new Month(8, "August", "It has 31 days").save();
        new Month(9, "September", "It has 30 days").save();
        new Month(10, "October", "It has 31 days").save();
        new Month(11, "November", "It has 30 days").save();
        new Month(12, "December", "It has 31 days").save();
    }
}
