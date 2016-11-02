package es.intelygenz.rss.data.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import es.intelygenz.rss.data.entity.response.entity.Article;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class FeedsResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("sortBy")
    @Expose
    private String sortBy;

    @SerializedName("articles")
    @Expose
    List<Article> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
