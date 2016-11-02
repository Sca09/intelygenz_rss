package es.intelygenz.rss.presentation.view.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import es.intelygenz.rss.R;
import es.intelygenz.rss.presentation.model.FeedModel;

public class FeedCard extends LinearLayout {

    private Context context;

    private CardView feedCardLayout;
    private ImageView feedImage;
    private TextView feedTitle;
    private TextView feedDescription;

    private FeedModel feedModel;
    private String imageUrl;
    private String title;
    private String description;

    private OnFeedCardListener listener;

    public FeedCard(Context context) {
        super(context, null);

        initClientTrack(context, null);
    }

    public FeedCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        initClientTrack(context, attrs);
    }

    private void initClientTrack(Context context, AttributeSet attrs) {
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FeedCard, 0, 0);

        try {
            imageUrl = a.getString(R.styleable.FeedCard_image_url);
            title = a.getString(R.styleable.FeedCard_title);
            description = a.getString(R.styleable.FeedCard_description);
        } finally {
            a.recycle();
        }
        
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.feed_card, this);

        feedCardLayout = (CardView) findViewById(R.id.feed_card_layout);
        feedImage = (ImageView) findViewById(R.id.feed_image);
        feedTitle = (TextView) findViewById(R.id.feed_title);
        feedDescription = (TextView) findViewById(R.id.feed_description);

        setLayout();
    }

    private void setLayout() {
        if(!Strings.isNullOrEmpty(imageUrl)) {
            Picasso.with(context).load(imageUrl).into(feedImage);

        }
        if(!Strings.isNullOrEmpty(title)) {
            feedTitle.setText(title);
        }

        if(!Strings.isNullOrEmpty(description)) {
            feedDescription.setText(description);
        }

        feedCardLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onFeedCardClicked(feedModel, feedImage);
                }
            }
        });
    }

    public void setData(FeedModel feedModel) {
        this.feedModel = feedModel;

        setData(feedModel.getImageUrl(), feedModel.getTitle(), feedModel.getDescription());
    }

    public void setData(String imageUrl, String title, String description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;

        invalidate();
        requestLayout();
    }

    @Override
    public void invalidate() {
        super.invalidate();

        setLayout();
    }

    public void setOnFeedCardListener(OnFeedCardListener listener) {
        this.listener = listener;
    }

    public interface OnFeedCardListener {
        void onFeedCardClicked(FeedModel feedModel, ImageView sharedView);
    }
}
