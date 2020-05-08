package com.dongnao.dnplayer;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dongnao.live.LiveManager;
import com.dongnao.live.list.Data;
import com.dongnao.live.list.Items;
import com.dongnao.live.list.LiveList;
import com.dongnao.live.list.Pictures;
import com.dongnao.live.room.Room;
import com.dongnao.live.room.Videoinfo;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import utils.PermissionsUtils;

public class MainActivity extends RxAppCompatActivity implements TabLayout
        .BaseOnTabSelectedListener, LiveAdapter.OnItemClickListener {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private LiveAdapter liveAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // 配置recycleview
        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        liveAdapter = new LiveAdapter(this);
        liveAdapter.setItemClickListener(this);
        recyclerView.setAdapter(liveAdapter);

        //配置tab
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);
        //添加标签
        addTabs();
        //动态授权
        PermissionsUtils pu = PermissionsUtils.getInstance();
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET};
        pu.grantPermissions(MainActivity.this, permissions);
    }

    private void addTabs() {
        addTab("lol", "英雄联盟");
        addTab("acg", "二次元");
        addTab("food", "美食");
    }

    private void addTab(String tag, String title) {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setTag(tag);
        tab.setText(title);
        tabLayout.addTab(tab);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tabLayout.removeOnTabSelectedListener(this);
    }

    /**
     * 切换标签回调
     *
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //请求获取房间 todo 显示加载等待
        LiveManager.getInstance()
                .getLiveList(tab.getTag().toString())
                .compose(this.<LiveList>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<LiveList>() {

                    @Override
                    public void onNext(LiveList liveList) {
                        liveAdapter.setLiveList(liveList);
                        liveAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("请求错误");
                        LiveList liveList=new LiveList();
                        List<Items> list=new ArrayList<Items>();
                        Items items=new Items();
                        items.setId("111");
                        items.setName("temp");
                        Pictures p=new Pictures();
                        p.setImg(""+Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" +R.mipmap.ic_launcher));
                        items.setPictures(p);
                        list.add(items);
                        Data data=new Data();
                        data.setItems(list);
                        liveList.setData(data);
                        liveAdapter.setLiveList(liveList);
                        liveAdapter.notifyDataSetChanged();
//                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("请求完成");
                    }
                });
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onItemClick(String id) {
        LiveManager.getInstance()
                .getLiveRoom(id)
                .compose(this.<Room>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Room>() {


                    @Override
                    public void onNext(Room room) {
                        Videoinfo info = room.getData().getInfo().getVideoinfo();
                        String[] plflags = info.getPlflag().split("_");
                        String room_key = info.getRoom_key();
                        String sign = info.getSign();
                        String ts = info.getTs();
                        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                        String v = "3";
                        if (null != plflags && plflags.length > 0) {
                            v = plflags[plflags.length - 1];
                        }
//                        intent.putExtra("url", "http://pl" + v + ".live" +
//                                ".panda.tv/live_panda/" + room_key
//                                + "_mid" +
//                                ".flv?sign=" + sign +
//                                "&time=" + ts);
                        intent.putExtra("url","https://www.apple.com/105/media/us/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-tpl-cc-us-20170912_1280x720h.mp4");
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable t)
                    {
                        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                        intent.putExtra("url","https://www.apple.com/105/media/us/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-tpl-cc-us-20170912_1280x720h.mp4");
                        startActivity(intent);
//                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
