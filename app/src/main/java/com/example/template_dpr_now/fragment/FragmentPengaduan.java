package com.example.template_dpr_now.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.template_dpr_now.Adapter.PengaduanAdapter;
import com.example.template_dpr_now.MainActivity;
import com.example.template_dpr_now.Model.GetPengaduan;
import com.example.template_dpr_now.Model.Pengaduan;
import com.example.template_dpr_now.R;
import com.example.template_dpr_now.Rest.API_Client;
import com.example.template_dpr_now.Rest.API_Interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPengaduan extends Fragment {
    API_Interface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static FragmentPengaduan ma;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengaduan, container, false);
        mRecyclerView = view.findViewById(R.id.rcFragmentPengaduan);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = API_Client.getClient().create(API_Interface.class);
        //ma.getActivity();
        ma = this;
        Call<GetPengaduan> PengaduanCall = mApiInterface.getPengaduan();
        PengaduanCall.enqueue(new Callback<GetPengaduan>() {
            @Override
            public void onResponse(Call<GetPengaduan> call, Response<GetPengaduan>
                    response) {
                List<Pengaduan> PengaduanList = response.body().getListDataPengaduan();
                Log.d("Retrofit Get", "Jumlah data Pengaduan: " +
                        String.valueOf(PengaduanList.size()));
                mAdapter = new PengaduanAdapter(PengaduanList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetPengaduan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });

        //getActivity();
        //refresh();
        return view;
    }
    public void refresh() {
        Call<GetPengaduan> PengaduanCall = mApiInterface.getPengaduan();
        PengaduanCall.enqueue(new Callback<GetPengaduan>() {
            @Override
            public void onResponse(Call<GetPengaduan> call, Response<GetPengaduan>
                    response) {
                List<Pengaduan> PengaduanList = response.body().getListDataPengaduan();
                Log.d("Retrofit Get", "Jumlah data Pengaduan: " +
                        String.valueOf(PengaduanList.size()));
                mAdapter = new PengaduanAdapter(PengaduanList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetPengaduan> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

}
