package kz.novatron.weatherchannel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.novatron.weatherchannel.mainscreen.WeatherContract;
import kz.novatron.weatherchannel.models.CityWeather;

/**
 * Created by smustafa on 25.01.2018.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.MyViewHolder> {
    private List<CityWeather> cityWeathers = null;
    private ArrayList<String> arraylistNames = new ArrayList<>();
    private WeatherContract.Presenter presenter;
    public CitiesAdapter(List<CityWeather> cityWeatherList, WeatherContract.Presenter presenter) {
        this.cityWeathers = cityWeatherList;
        this.presenter = presenter;

        arraylistNames.add("Almaty");
        arraylistNames.add("Astana");
        arraylistNames.add("Aktau");
        arraylistNames.add("Taraz");
        arraylistNames.add("Shymkent");
        arraylistNames.add("Taldykorgan");
        arraylistNames.add("Kyzylorda");
        arraylistNames.add("Semey");
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_weather, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CityWeather cityWeather = cityWeathers.get(position);
        holder.cityName.setText(cityWeather.getCityName());
        holder.degree.setText(cityWeather.getDegree());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cityWeathers.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        cityWeathers.clear();
        for (String name : arraylistNames) {
            if (name.toLowerCase(Locale.getDefault()).contains(charText)) {
                cityWeathers.add(new CityWeather(name, ""));
            }
        }
        presenter.truncateTable();
        presenter.subscribe(cityWeathers);

        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.text_city_name)
        TextView cityName;
        @BindView(R.id.text_degree)
        TextView degree;
        public MyViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
