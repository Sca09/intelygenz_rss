package es.intelygenz.rss.data.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import es.intelygenz.rss.data.entity.response.entity.Source;

/**
 * Created by davidtorralbo on 03/11/16.
 */

public class SourcesResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("sources")
    @Expose
    private List<Source> sources;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
