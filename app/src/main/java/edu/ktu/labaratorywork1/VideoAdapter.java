package edu.ktu.labaratorywork1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.VideoView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YouTubeVideos> youTubeVideosList;

    public VideoAdapter() {
    }

    public VideoAdapter(List<YouTubeVideos> youTubeVideosList) {

        this.youTubeVideosList = youTubeVideosList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.youtube_video_view, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position){
        holder.videoWeb.loadData(youTubeVideosList.get(position).getVideoUrl(), "text/html", "utf-8");
    }

    @Override
    public int getItemCount(){return youTubeVideosList.size();}

    public List<YouTubeVideos> getYouTubeVideosList() {
        return youTubeVideosList;
    }

    public void setYouTubeVideosList(List<YouTubeVideos> youTubeVideosList) {
        this.youTubeVideosList = youTubeVideosList;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        WebView videoWeb;

        public VideoViewHolder(View itemView){
            super(itemView);

            videoWeb = (WebView) itemView.findViewById(R.id.videoWebView);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient());
        }
    }
}
