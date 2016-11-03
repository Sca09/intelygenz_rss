package es.intelygenz.rss.presentation.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.intelygenz.rss.presentation.model.FeedModel;

import static org.junit.Assert.*;

public class UtilsUnitTest {

    public static final String FAKE_WORDS           = "test";
    public static final String FAKE_TITLE_TEST      = "test";
    public static final String FAKE_TITLE_OTHER     = "other";

    @Test
    public void filterByWords_ZeroResults() {
        List<FeedModel> feedModelList = new ArrayList<>();
        FeedModel feedModel = new FeedModel();
        feedModel.setTitle(FAKE_TITLE_OTHER);
        feedModelList.add(feedModel);

        List<FeedModel> result = Utils.filterByWords(FAKE_WORDS, feedModelList);

        assertEquals(result.size(), 0);
    }

    @Test
    public void filterByWords_NonZeroResults() {
        List<FeedModel> feedModelList = new ArrayList<>();
        FeedModel feedModel = new FeedModel();
        feedModel.setTitle(FAKE_TITLE_TEST);
        feedModelList.add(feedModel);

        List<FeedModel> result = Utils.filterByWords(FAKE_WORDS, feedModelList);

        assertNotEquals(result.size(), 0);
    }
}