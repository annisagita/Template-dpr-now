package com.example.template_dpr_now.Pengaduan_Activity;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.template_dpr_now.R;
import com.example.template_dpr_now.fragment.KomisiItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PengaduanAdapter extends RecyclerView.Adapter<PengaduanAdapter.MyViewHolder> implements ListAdapter {
    List<Pengaduan> mPengaduanList;
    ArrayList<Pengaduan> mdataModelArrayList;
    private Context mContext;
    private ArrayList<KomisiItem> mF_K1_List;

    public PengaduanAdapter(List<Pengaduan> PengaduanList) {
        mPengaduanList = PengaduanList;
//        mContext = context;
//        mF_K1_List = F_K1_List;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }


    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pengaduan_list_layout, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){

        //memasukkan data ke holder
//        String tgl = mPengaduanList.get(position).getTanggal();
//        DateFormat outputFormat = new SimpleDateFormat("MM/yyyy");
//        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
//        Log.d("tanggal : ",tgl);
//        //String inputText = "2012-11-17T00:00:00.000-05:00";
//        Date date = null;
//        try {
//            date = inputFormat.parse(mPengaduanList.get(position).getTanggal());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String outputText = outputFormat.format(date);
//
//        Pengaduan message = mPengaduanList.get(position);
//
//        Date createdAt = mPengaduanList.get(position).getTanggal();
//        long now = new Date().getTime();
//        String convertedDate = DateUtils.getRelativeTimeSpanString(
//                createdAt.getTime(),
//                now,
//                DateUtils.SECOND_IN_MILLIS).toString();
//
//        holder.timeLabel.setText(convertedDate);
//        //memasukkan data ke holder
//        Log.d("tanggal : ",outputText);

        //String ambilTanggal = getDate(mPengaduanList.get(position).getTanggal());
        holder.mTextViewAduan.setText("Aduan : "+mPengaduanList.get(position).getIsi_aduan());

        holder.mTextViewNama.setText(mPengaduanList.get(position).getNama());

        holder.mTextViewTanggal.setText(mPengaduanList.get(position).getTanggal());
        holder.mTextViewWaktu.setText(mPengaduanList.get(position).getWaktu());
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), MainActivity.class);

                mIntent.putExtra("Nama", mPengaduanList.get(position).getNama());

                mIntent.putExtra("Aduan", mPengaduanList.get(position).getIsi_aduan());
                mIntent.putExtra("Tanggal", mPengaduanList.get(position).getTanggal());

                view.getContext().startActivity(mIntent);
            }
        });*/
    }

    @Override
    public int getItemCount () {
        return mPengaduanList.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewEmail, mTextViewNama, mTextViewNomor,mTextViewAduan,mTextViewTanggal,mTextViewWaktu;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewNama = (TextView) itemView.findViewById(R.id.tvNama);
            mTextViewAduan = (TextView) itemView.findViewById(R.id.tvAduan);
            mTextViewTanggal = (TextView) itemView.findViewById(R.id.datetime);
            mTextViewWaktu = (TextView) itemView.findViewById(R.id.waktu);
        }
    }
}
