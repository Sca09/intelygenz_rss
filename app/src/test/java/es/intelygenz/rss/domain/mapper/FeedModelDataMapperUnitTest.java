package es.intelygenz.rss.domain.mapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.presentation.model.FeedModel;

import static org.junit.Assert.assertEquals;

public class FeedModelDataMapperUnitTest {

    public static final String FAKE_AUTHOR          = "fakeAuthor";
    public static final String FAKE_TITLE           = "fakeTitle";
    public static final String FAKE_DESCRIPTION     = "fakeDescription";
    public static final String FAKE_URL             = "fakeUrl";
    public static final String FAKE_URL_TO_IMAGE    = "fakeUrlToImage";
    public static final String FAKE_PUBLISHED_AT    = "fakePublishedAt";

    private FeedModelDataMapper mapper = new FeedModelDataMapper();
    private Article article = new Article();

    @Before
    public void setUp() {
        article.setAuthor(FAKE_AUTHOR);
        article.setTitle(FAKE_TITLE);
        article.setDescription(FAKE_DESCRIPTION);
        article.setUrl(FAKE_URL);
        article.setUrlToImage(FAKE_URL_TO_IMAGE);
        article.setPublishedAt(FAKE_PUBLISHED_AT);
    }

    @Test
    public void transformOneArticle_Success() {
        FeedModel feedModel = mapper.transform(article);

        assertEquals(feedModel.getTitle(), article.getTitle());
        assertEquals(feedModel.getDescription(), article.getDescription());
        assertEquals(feedModel.getUrl(), article.getUrl());
        assertEquals(feedModel.getImageUrl(), article.getUrlToImage());
    }

    @Test
    public void transformSeveralArticles_Success() {
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);
        articleList.add(article);

        List<FeedModel> feedModelList = mapper.transform(articleList);

        assertEquals(feedModelList.size(), articleList.size());
    }

}