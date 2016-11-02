package es.intelygenz.rss.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.intelygenz.rss.R;
import es.intelygenz.rss.presentation.model.FeedModel;
import es.intelygenz.rss.presentation.view.component.FeedCard;
import es.intelygenz.rss.presentation.view.component.FeedCard.OnFeedCardListener;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class FeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FeedModel> feedModelList;

    private OnFeedCardListener listener;

    public FeedListAdapter(Context context, List<FeedModel> feedModelList) {
        this.context = context;
        this.feedModelList = feedModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_entry, parent, false);
        return new EstimationHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FeedModel item = feedModelList.get(position);

        ((EstimationHolder) holder).feedCard.setData(item);
        if(listener != null) {
            ((EstimationHolder) holder).feedCard.setOnFeedCardListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return (feedModelList != null ? feedModelList.size() : 0);
    }

    public void setOnFeedCardListener(OnFeedCardListener listener) {
        this.listener = listener;
    }

    class EstimationHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.feed_card) FeedCard feedCard;

        public EstimationHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
