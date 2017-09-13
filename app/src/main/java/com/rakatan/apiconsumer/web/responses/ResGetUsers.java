package com.rakatan.apiconsumer.web.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rakatan.apiconsumer.models.User;

import java.util.ArrayList;

public class ResGetUsers {
    @SerializedName("results")
    @Expose
    private ArrayList<User> users = null;
    @SerializedName("info")
    @Expose
    private Info info;

    public ArrayList<User> getResults() {
        return users;
    }

    public void setResults(ArrayList<User> results) {
        this.users = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public class Info {

        @SerializedName("seed")
        @Expose
        private String seed;
        @SerializedName("results")
        @Expose
        private Integer results;
        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("version")
        @Expose
        private String version;

        public String getSeed() {
            return seed;
        }

        public void setSeed(String seed) {
            this.seed = seed;
        }

        public Integer getResults() {
            return results;
        }

        public void setResults(Integer results) {
            this.results = results;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

    }
}
