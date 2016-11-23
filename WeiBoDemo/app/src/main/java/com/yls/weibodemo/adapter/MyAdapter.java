package com.yls.weibodemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yls.weibodemo.R;
import com.yls.weibodemo.model.WeiBoGet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    List<WeiBoGet> lists;
    LayoutInflater layoutInflater;
    public MyAdapter(Context context, List<WeiBoGet> lists){
        this.context=context;
        this.lists=lists;
        this.layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHondler viewHondler=null;
        if (view==null){
            view=layoutInflater.inflate(R.layout.listview_homepage_item,null);
            viewHondler=new ViewHondler();
            viewHondler.created_at= (TextView) view.findViewById(R.id.tv_created_at);
            viewHondler.text= (TextView) view.findViewById(R.id.tv_text);
            viewHondler.source= (TextView) view.findViewById(R.id.tv_source);
//            viewHondler.followers_count= (TextView) view.findViewById(R.id.tv_followers_count);
//            viewHondler.friends_count= (TextView) view.findViewById(R.id.tv_friends_count);
//            viewHondler.statuses_count= (TextView) view.findViewById(R.id.tv_statuses_count);
//            viewHondler.screen_name= (TextView) view.findViewById(R.id.tv_screen_name);
//            viewHondler.profile_image_url= (ImageView) view.findViewById(R.id.iv_profile_image_url);
            view.setTag(viewHondler);
        } else {
            viewHondler= (ViewHondler) view.getTag();
        }
        WeiBoGet weiBoGet=lists.get(i);
        viewHondler.created_at.setText(weiBoGet.getCreated_at());
        viewHondler.text.setText(weiBoGet.getText());
        viewHondler.source.setText(weiBoGet.getSource());
//        viewHondler.statuses_count.setText(weiBoGet.getStatuses_count());
//        viewHondler.friends_count.setText(weiBoGet.getFriends_count());
//        viewHondler.followers_count.setText(weiBoGet.getFollowers_count());
//        viewHondler.screen_name.setText(weiBoGet.getScreen_name());




        MyHandler myHandler=new MyHandler();
        myHandler.setViewHondler(viewHondler);
        MyThread myThread=new MyThread();
        myThread.setMyHandler(myHandler);
        myThread.setIamgeURL(weiBoGet.getProfile_image_url());
        myThread.start();
        return view;
    }
    class MyThread extends Thread{
        String iamgeURL;
        MyHandler myHandler;
        public void setMyHandler(MyHandler myHandler){
            this.myHandler=myHandler;
        }
            public void setIamgeURL(String iamgeURL){
            this.iamgeURL=iamgeURL;
        }
        public void run(){//耗时操作在线程
            try {
                URL url=new URL(iamgeURL);
                Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
                if(bitmap!=null){
                    myHandler.setBitmap(bitmap);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            myHandler.sendEmptyMessage(0);
        }
    }
    class  MyHandler extends Handler{
        ViewHondler viewHondler;
        Bitmap bitmap;
        public void setBitmap(Bitmap bitmap){
            this.bitmap=bitmap;
        }
        public void setViewHondler(ViewHondler viewHondler){
            this.viewHondler=viewHondler;
        }
        public void handleMessage(Message message){
            if(bitmap!=null){
                viewHondler.profile_image_url.setImageBitmap(bitmap);
            }
        }
    }
    class ViewHondler{
        TextView created_at;
        TextView text;
        TextView source;
        TextView screen_name;
        ImageView profile_image_url;
        TextView followers_count;
        TextView friends_count;
        TextView statuses_count;
    }

}
