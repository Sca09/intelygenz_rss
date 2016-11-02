package es.intelygenz.rss.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.intelygenz.rss.R;
import es.intelygenz.rss.data.net.ConnectionManagerUtils;
import es.intelygenz.rss.presentation.model.FeedModel;
import es.intelygenz.rss.presentation.utils.Constants;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class FeedDetailsActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, FeedDetailsActivity.class);
        return callingIntent;
    }

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.feed_image) ImageView feedImage;
    @BindView(R.id.feed_title) TextView feedTitle;
    @BindView(R.id.feed_description) TextView feedDescription;
    @BindView(R.id.open_in_browser_button) FloatingActionButton fab;

    private FeedModel feedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);

        overrideOpenTransition();

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        showToolBarActionButton();

        feedModel = (FeedModel) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_FEED_MODEL);

        setLayout();
    }

    private void setLayout() {
        if(feedModel != null) {
            if(!Strings.isNullOrEmpty(feedModel.getImageUrl())) {
                Picasso.with(this).load(feedModel.getImageUrl()).into(feedImage);

            }
            if(!Strings.isNullOrEmpty(feedModel.getTitle())) {
                feedTitle.setText(feedModel.getTitle());
            }

            if(!Strings.isNullOrEmpty(feedModel.getDescription())) {
                feedDescription.setText(feedModel.getDescription());
            }
        }

        if(ConnectionManagerUtils.isNetworkAvailable(this)) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    private void showToolBarActionButton() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.open_in_browser_button)
    public void openInBrowser(View v) {
        if(!Strings.isNullOrEmpty(feedModel.getUrl())) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(feedModel.getUrl()));
            startActivity(i);
        }
    }

    private void overrideOpenTransition() {
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    private void overrideCloseTransition() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overrideCloseTransition();
    }
}