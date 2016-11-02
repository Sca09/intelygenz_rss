package es.intelygenz.rss.presentation.utils;

import java.util.ArrayList;
import java.util.List;

import es.intelygenz.rss.presentation.model.FeedModel;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class Utils {

    public static List<FeedModel> filterByWords(String words, List<FeedModel> feedModelList) {
        List<FeedModel> result = new ArrayList<>();

        for(FeedModel feedModel : feedModelList) {
            if(feedModel.getTitle().toLowerCase().contains(words.toLowerCase())) {
                result.add(feedModel);
            }
        }

        return result;
    }
}
