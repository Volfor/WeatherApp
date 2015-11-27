package com.vtrifonov.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vtrifonov.weatherapp.R;
import com.vtrifonov.weatherapp.activities.MainActivity;
import com.vtrifonov.weatherapp.model.Adapter;
import com.vtrifonov.weatherapp.model.Forecast;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListFragment extends Fragment {

    private OnListItemSelectedListener itemSelectedListener;

    private ListView listView;
    private Adapter adapter;

    public interface OnListItemSelectedListener {
        void onItemSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            itemSelectedListener = (OnListItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnListItemSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.list_titles);

        if (savedInstanceState != null) {
            setupListView();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelectedListener.onItemSelected(position);
            }
        });
    }

    public void setupListView() {
        Realm realm = Realm.getInstance(MainActivity.realmConfiguration);
        RealmResults<Forecast> forecasts = realm.where(Forecast.class).findAll();

        if (adapter == null) {
            adapter = new Adapter(getActivity(), forecasts);
            listView.setAdapter(adapter);
        } else {
            listView.invalidate();
            adapter.notifyDataSetChanged();
        }
    }

}
