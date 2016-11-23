package com.yls.weibodemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
        button = (Button) findViewById(R.id.add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addweibo();
            }
        });

    }

    public void addtu() {
        // 1. 获取要发送的图片
        Drawable drawable = getResources().getDrawable(R.drawable.ic_com_sina_weibo_sdk_logo);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
        // 2. 调用接口发送微博
        WeiboParameters params = new WeiboParameters(Constants.APP_KEY);
        params.put("access_token", accessToken.getToken());
        params.put("status", "通过API发送微博-upload");
        params.put("visible", "0");
        params.put("list_id", "");
        params.put("pic", bitmap);
        params.put("lat", "14.5");
        params.put("long", "23.0");
        params.put("annotations", "");
        RequestListener listener = new RequestListener() {
            @Override
            public void onComplete(String s) {
                Log.i("onComplete", "" + s);
            }

            @Override
            public void onWeiboException(WeiboException e) {

            }
        };
        AsyncWeiboRunner asyncWeiboRunner = new AsyncWeiboRunner(this);
        asyncWeiboRunner.requestAsync(
                "https://api.weibo.com/2/statuses/upload.json",
                params,
                "POST",
                listener);
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
                     Log.i("JSONObject",jsonObject.optString("msg"));
                    JSONArray jsonArray=jsonObject.getJSONArray("statuses");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject obj=jsonArray.getJSONObject(i);
                        WeiBoGet weiBoGet=new WeiBoGet();
                        weiBoGet.setCreated_at(obj.getString("created_at"));
                        weiBoGet.setText(obj.getString("text"));
                        weiBoGet.setSource(obj.getString("soure"));
                        weiBoGet.setOriginal_pic(obj.getString("original_pic"));
                        lists.add(weiBoGet);
                        MyAdapter myAdapter=new MyAdapter(HomePageActivity.this,lists);
                        listView.setAdapter(myAdapter);
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

}
