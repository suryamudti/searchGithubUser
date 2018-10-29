package com.smile.searchgithubuser.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users implements Serializable
{

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<GithubUser> GithubUsers = null;
    private final static long serialVersionUID = -2629155362930861584L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Users() {
    }

    /**
     *
     * @param GithubUsers
     * @param totalCount
     * @param incompleteResults
     */
    public Users(Integer totalCount, Boolean incompleteResults, List<GithubUser> GithubUsers) {
        super();
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.GithubUsers = GithubUsers;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<GithubUser> getGithubUsers() {
        return GithubUsers;
    }

    public void setGithubUsers(List<GithubUser> GithubUsers) {
        this.GithubUsers = GithubUsers;
    }

}
