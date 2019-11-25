package edu.ktu.labaratorywork1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Vector;

public class DanceDetailsActivity extends YouTubeBaseActivity {

    private static final String TAG = "DanceDetails";

    private YouTubePlayerView mYoutubePlayerView;
    private Button buttonPlay;
    private TextView title;
    private TextView description;
    private ImageView image;
    private RecyclerView recyclerView;
    private Vector<YouTubeVideos> youTubeVideos = new Vector<>();

    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.dance_details_activity_design);
        Log.d(TAG,"onCreate: Starting" );
        //buttonPlay = (Button) findViewById(R.id.DanceDetailsButton);
        title = (TextView) findViewById(R.id.DanceDetailsTitle);
        description = (TextView) findViewById(R.id.DanceDetailsDescription);
        image = (ImageView) findViewById(R.id.DanceDetailsImage);
        //mYoutubePlayerView = (YouTubePlayerView) findViewById(R.id.DanceDetailsYoutube);

        Bundle bundleItems = getIntent().getExtras();
        final ListItem item = (ListItem) bundleItems.getSerializable("item");

        title.setText(item.getTitle());
        description.setText(item.getDescription());
        image.setImageResource(item.getImagedId());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        youTubeVideos.add(new YouTubeVideos("<iframe width =\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+item.getYoutubeLink()+"\" frameborder=\"0\" allowfullscrean></iframe>"));

        VideoAdapter videoAdapter = new VideoAdapter(youTubeVideos);

        recyclerView.setAdapter(videoAdapter);
    }

    public void initializeYoutube(final ListItem items){
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG,"onClick: Done initializing" );
                youTubePlayer.loadVideo(items.getYoutubeLink());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                Log.d(TAG,"onClick: Initializing failed" );
            }
        };

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: Initializing youtube player" );
                mYoutubePlayerView.initialize(YouTubeConfig.getApiKey(), mOnInitializedListener);
            }
        });
    }
}
