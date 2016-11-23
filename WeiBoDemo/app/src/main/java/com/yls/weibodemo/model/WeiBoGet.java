package com.yls.weibodemo.model;


/**
 * Created by Administrator on 2016/11/23.
 */
public class WeiBoGet {
//    created_at	string	微博创建时间
//    text	string	微博信息内容
//    source	string	微博来源
    String created_at;
    String text;
    String source;
//    screen_name	string	用户昵称
//    profile_image_url	string	用户头像地址（中图），50×50像素
    String screen_name;
    String profile_image_url;
    //    reposts_count	int	转发数
//    comments_count	int	评论数
//    attitudes_count	int	表态数
    int followers_count;
    int friends_count;
    int statuses_count;

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(int statuses_count) {
        this.statuses_count = statuses_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
