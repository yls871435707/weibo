package com.yls.weibodemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.yls.weibodemo.adapter.MyAdapter;
import com.yls.weibodemo.model.WeiBoGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


/**
 * Created by Administrator on 2016/11/23.
 */
public class HomePageActivity extends Activity {
Button button;
    private ListView listView;
    ArrayList<WeiBoGet> lists=new ArrayList<WeiBoGet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        listView = (ListView) findViewById(R.id.listview);
        button= (Button) findViewById(R.id.add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        addweibo();
                    }
                }.start();
            }
        });
    }


    public void addweibo(){
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
        // 2. 调用接口
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put("access_token", accessToken.getToken());

        RequestListener listener = new RequestListener() {
            @Override
            public void onComplete(String s) {
                Log.i("onComplete", "" + s);
                try {
                    JSONObject jsonObject= null;
                    jsonObject = new JSONObject(s);
                     Log.i("JSONObject",jsonObject.optString("statuses"));
                    JSONArray jsonArray=jsonObject.getJSONArray("statuses");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject obj=jsonArray.getJSONObject(i);
                        WeiBoGet weiBoGet=new WeiBoGet();
                        weiBoGet.setCreated_at(obj.getString("created_at"));
                        weiBoGet.setText(obj.getString("text"));
                        weiBoGet.setSource(obj.getString("source"));
                        lists.add(weiBoGet);
//                        JSONArray array=obj.getJSONArray("user");
//                        for(int j=0;j<obj.length();j++){
//                            JSONObject object=array.getJSONObject(j);
//                            weiBoGet.setScreen_name(object.getString("screen_name"));
//                            weiBoGet.setProfile_image_url(object.getString("profile_image_url"));
//                            weiBoGet.setStatuses_count(object.getInt("statuses_count"));
//                            weiBoGet.setFollowers_count(object.getInt("followers_count"));
//                            weiBoGet.setFriends_count(object.getInt("friends_count"));
//                            lists.add(weiBoGet);
//                        }

                        handler.sendEmptyMessage(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        };
        AsyncWeiboRunner asyncWeiboRunner = new AsyncWeiboRunner(this);
        asyncWeiboRunner.requestAsync(
                "https://api.weibo.com/2/statuses/home_timeline.json",
                params,
                "GET",
                listener);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyAdapter myAdapter=new MyAdapter(HomePageActivity.this,lists);
            listView.setAdapter(myAdapter);
        }
    };
}
