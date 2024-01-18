package com.example.studypartner.Matches;

public class MatchesObject {
    private String name;
    private String profileImageUrl;
    private String userId;
    public MatchesObject(String userId,String name,String profileImageUrl){
        this.userId=userId;
        this.name=name;
        this.profileImageUrl=profileImageUrl;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
