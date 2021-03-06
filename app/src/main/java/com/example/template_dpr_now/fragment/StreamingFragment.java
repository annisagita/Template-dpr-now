package com.example.template_dpr_now.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.template_dpr_now.Streaming_Activity.Streaming_PaginationScrollListener;
import com.example.template_dpr_now.R;
import com.example.template_dpr_now.Rest.API_Interface;
import com.example.template_dpr_now.Streaming_Activity.VideoRecyclerAdapter;
import com.example.template_dpr_now.Streaming_Activity.YouTubeModel;

import com.example.template_dpr_now.api.ApiClient;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StreamingFragment extends Fragment {

    String TAG = "YouTube";
    //Id Channel YouTube
    String YouTubeAPIChannelId = "UCejL25NjyNxlMR0JqlFX4Dg";

    //Key API YouTube
    String YouTubeAPIKey =  "AIzaSyBxjQAD8hgRa3C4RvhUQQTow0OrpejmrxY";
    String YouTubeAPIUrl = "https://www.googleapis.com/youtube/v3/search?" +
            "part=snippet&" +
            "part=statistics&" +
            "channelId=" + YouTubeAPIChannelId + "&" +
            "&maxResults=25&" +
            "order=date&" +
            "key=" + YouTubeAPIKey;

    Context context;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int RECOVERY_REQUEST = 1;
    private int selected = 0;

    TextView txtTotalVideo;
    ProgressBar progressBar;
    RecyclerView mRecyclerView;
    VideoRecyclerAdapter adapter;
    YouTubeModel model;
    YouTubePlayer youTubePlayer;
    YouTubePlayerSupportFragment youtubePlayerFragment;

    // Memanggil layout fragment_streaming.xml
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_streaming, container, false);

        try {
            context = getContext();
            mRecyclerView = view.findViewById(R.id.recycler_view_streaming);
            progressBar = view.findViewById(R.id.progress_bar);
            txtTotalVideo = view.findViewById(R.id.txt_total_video);

            adapter = new VideoRecyclerAdapter(context);
            youtubePlayerFragment = (YouTubePlayerSupportFragment) this.getChildFragmentManager().findFragmentById(R.id.frame_fragment);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.addOnScrollListener(new Streaming_PaginationScrollListener(linearLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    if (!isLastPage) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadVideos(YouTubeAPIUrl + "&pageToken=" + model.getNextPageToken());
                            }
                        }, 200);
                    }
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }
            });

            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClicklListener(new VideoRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    selected = position;
                    if (adapter.getItems().size() > 0 && adapter.getItems().get(selected) != null && adapter.getItems().get(selected).getVideoId() != null) {
                        youTubePlayer.cueVideo(adapter.getItems().get(selected).getVideoId());
                        youTubePlayer.play();
                    }
                }
            });

            loadVideos(YouTubeAPIUrl);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

        return view;
    }

    private void loadVideos(String url) {
        // apiService menggunakan class API_Interface di package Rest
        API_Interface apiService = ApiClient.createService(API_Interface.class);
        if (apiService == null) return;

        showProgressBar();
        Call<YouTubeModel> call = apiService.getVideos(url);
        call.enqueue(new Callback<YouTubeModel>() {
            @Override
            public void onResponse(Call<YouTubeModel> call, Response<YouTubeModel> response) {
                YouTubeModel serverResponse = response.body();
                hideProgressBar();
                loadDataAction(serverResponse);
            }

            @Override
            public void onFailure(Call<YouTubeModel> call, Throwable t) {
                Log.e(TAG, t.toString());
                hideProgressBar();
            }
        });
    }

    //Menampilkan progressbar
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    //Menampilkan progressbar
    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    //Menghitung jumlah video pada channel YouTube
    private void loadDataAction(YouTubeModel item) {
        isLoading = false;

        if (item != null && item.getVideoModels() != null) {
            model = item;
            txtTotalVideo.setText("Total Videos: " + item.getTotalViedeos());

            adapter.addAll(model.getVideoModels());
            adapter.notifyDataSetChanged();

            youtubePlayerFragment.initialize(YouTubeAPIKey, onInitializedListener);

            if (model.getNextPageToken() == null) {
                isLastPage = true;
            }
        }
    }

    YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
            if (!b) {
                youTubePlayer = player;
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                if (adapter != null && adapter.getItems().size() > 0) {
                    if (adapter.getItems().get(selected) != null && adapter.getItems().get(selected).getVideoId() != null) {
                        youTubePlayer.cueVideo(adapter.getItems().get(selected).getVideoId());
                        youTubePlayer.loadVideo(adapter.getItems().get(selected).getVideoId());
                        youTubePlayer.play();
                    }
                }

                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {
                        Log.e(TAG, "Video Started");
                    }

                    @Override
                    public void onVideoEnded() {
                        Log.e(TAG, "Video End");
                        if (selected >= adapter.getItems().size()) {
                            selected = 0;
                        } else {
                            selected++;
                        }

                        youTubePlayer.cueVideo(adapter.getItems().get(selected).getVideoId());
                        youTubePlayer.play();
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            if (youTubeInitializationResult.isUserRecoverableError()) {
                youTubeInitializationResult.getErrorDialog((Activity) context, RECOVERY_REQUEST).show();
            } else {
                Toast.makeText(context, "Memuat Video Gagal", Toast.LENGTH_LONG).show();
            }
        }
    };
}
