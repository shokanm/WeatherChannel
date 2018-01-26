package kz.novatron.weatherchannel.mainscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.novatron.weatherchannel.CitiesAdapter;
import kz.novatron.weatherchannel.R;
import kz.novatron.weatherchannel.SharedPreferencesManager;
import kz.novatron.weatherchannel.models.CityWeather;

public class MainActivity extends AppCompatActivity implements WeatherContract.View, SearchView.OnQueryTextListener{

    @BindView(R.id.recycler_cities)
    RecyclerView recyclerCities;
    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private List<CityWeather> cityWeathers = new ArrayList<>();
    private CitiesAdapter citiesAdapter;
    private WeatherContract.Presenter presenter;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        search.setOnQueryTextListener(this);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        presenter = new PresenterImpl(this, this, sharedPreferencesManager);

        initRecyclerView();

    }
    private void initRecyclerView() {
        citiesAdapter = new CitiesAdapter(cityWeathers, presenter);
        recyclerCities.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerCities.setLayoutManager(layoutManager);
        recyclerCities.setAdapter(citiesAdapter);
    }
    @Override
    public void displayCities(List<CityWeather> cityWeatherList) {
        cityWeathers.addAll(cityWeatherList);
        citiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void displaySearchQuery(String queryText, boolean submit) {
        search.setQuery(queryText, submit);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Something went wrong. Please, try again...", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        citiesAdapter.filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCacheValues();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferencesManager.setLastQueryText(search.getQuery());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
