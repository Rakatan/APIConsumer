package com.rakatan.apiconsumer.web.requests;

public class ReqGetUsers {
    public static final int USERS_PER_PAGE = 20;
    private int page;
    private int resultsPerPage;
    private String seed;

    public ReqGetUsers() {
        this.page = 0;
        this.resultsPerPage = USERS_PER_PAGE;
        this.seed = "abc";
    }

    public ReqGetUsers(int page, int resultsPerPage) {
        this.page = page;
        this.resultsPerPage = resultsPerPage;
        this.seed = "abc";
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public String getSeed() {
        return seed;
    }
}
