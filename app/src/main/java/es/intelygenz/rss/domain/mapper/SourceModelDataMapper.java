package es.intelygenz.rss.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.data.entity.response.entity.Source;
import es.intelygenz.rss.presentation.internal.di.PerActivity;
import es.intelygenz.rss.presentation.model.FeedModel;
import es.intelygenz.rss.presentation.model.SourceModel;

/**
 * Created by davidtorralbo on 02/11/16.
 */

@PerActivity
public class SourceModelDataMapper {

    @Inject
    public SourceModelDataMapper() {}

    public SourceModel transform(Source source) {
        SourceModel sourceModel = new SourceModel();
        sourceModel.setId(source.getId());
        sourceModel.setName(source.getName());
        sourceModel.setSortBysAvailable(source.getSortBysAvailable());

        return sourceModel;
    }

    public List<SourceModel> transform(List<Source> sourceList) {
        List<SourceModel> sourceModelList = new ArrayList<>();

        for(Source source : sourceList) {
            sourceModelList.add(transform(source));
        }

        return sourceModelList;
    }

}
