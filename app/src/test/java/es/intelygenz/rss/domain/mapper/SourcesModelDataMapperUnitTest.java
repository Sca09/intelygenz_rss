package es.intelygenz.rss.domain.mapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.data.entity.response.entity.Source;
import es.intelygenz.rss.presentation.model.FeedModel;
import es.intelygenz.rss.presentation.model.SourceModel;

import static org.junit.Assert.assertEquals;

public class SourcesModelDataMapperUnitTest {

    public static final String FAKE_ID                          = "fakeId";
    public static final String FAKE_NAME                        = "fakeName";
    public static final String FAKE_DESCRIPTION                 = "fakeDescription";
    public static final String FAKE_URL                         = "fakeUrl";
    public static final String FAKE_CATEGORY                    = "fakeCategory";
    public static final String FAKE_LANGUAGE                    = "fakeLanguage";
    public static final String FAKE_COUNTRY                     = "fakeCountry";
    public static final List<String> FAKE_SORT_BYS_AVAILABLE    = new ArrayList<String>();

    private SourceModelDataMapper mapper = new SourceModelDataMapper();
    private Source source = new Source();

    @Before
    public void setUp() {
        source.setId(FAKE_ID);
        source.setName(FAKE_NAME);
        source.setDescription(FAKE_DESCRIPTION);
        source.setUrl(FAKE_URL);
        source.setCategory(FAKE_CATEGORY);
        source.setLanguage(FAKE_LANGUAGE);
        source.setCategory(FAKE_COUNTRY);
        source.setSortBysAvailable(FAKE_SORT_BYS_AVAILABLE);
    }

    @Test
    public void transformOneArticle_Success() {
        SourceModel sourceModel = mapper.transform(source);

        assertEquals(sourceModel.getId(), source.getId());
        assertEquals(sourceModel.getName(), source.getName());
        assertEquals(sourceModel.getSortBysAvailable().size(), source.getSortBysAvailable().size());
    }

    @Test
    public void transformSeveralArticles_Success() {
        List<Source> sourceList = new ArrayList<>();
        sourceList.add(source);
        sourceList.add(source);

        List<SourceModel> sourceModelList = mapper.transform(sourceList);

        assertEquals(sourceModelList.size(), sourceList.size());
    }

}