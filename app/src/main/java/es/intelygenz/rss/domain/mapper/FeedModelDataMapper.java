package es.intelygenz.rss.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.presentation.internal.di.PerActivity;
import es.intelygenz.rss.presentation.model.FeedModel;

/**
 * Created by davidtorralbo on 02/11/16.
 */

@PerActivity
public class FeedModelDataMapper {

    @Inject
    public FeedModelDataMapper() {}

    public FeedModel transform(Article article) {
        FeedModel feedModel = new FeedModel();
        feedModel.setTitle(article.getTitle());
        feedModel.setDescription(article.getDescription());
        feedModel.setImageUrl(article.getUrlToImage());
        feedModel.setUrl(article.getUrl());

        return feedModel;
    }

    public List<FeedModel> transform(List<Article> articleList) {
        List<FeedModel> feedModelList = new ArrayList<>();

        for(Article article : articleList) {
            feedModelList.add(transform(article));
        }

        return feedModelList;
    }

}
