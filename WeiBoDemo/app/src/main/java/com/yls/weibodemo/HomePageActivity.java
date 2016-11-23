package com.yls.weibodemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

/**
 * Created by Administrator on 2016/11/23.
 */
public class HomePageActivity extends Activity {
    private ListView listView;
    StatusesAPI mStatusesAPI;
    private Oauth2AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        listView= (ListView) findViewById(R.id.listview);
        accessToken=new Oauth2AccessToken(accessToken.getToken(),"");
        mStatusesAPI=new StatusesAPI(this, Constants.APP_KEY,accessToken);
        RequestListener listener=new RequestListener() {
            @Override
            public void onComplete(String s) {

            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        };
        mStatusesAPI.friendsTimeline(0,0,50,1,false,0,false,listener);
    }


}
