package com.example.android.demo.nine_palace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;
import com.example.android.imview.assninegridview.AssNineGridView;
import com.example.android.imview.assninegridview.AssNineGridViewClickAdapter;
import com.example.android.imview.assninegridview.ImageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *  9  宫格
 * @author assionhonty
 * Created on 2018.08.08.
 * Email：assionhonty@126.com
 * Function:demo
 */
public class    DemoActivity extends AppCompatActivity {
    private List<DemoBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        //添加30个随机假数据
        mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            List<String> img = new ArrayList<>();
            int random = new Random().nextInt(UrlData.getImageLists().size()) + 1;
            for (int j = 0; j < random; j++) {
                img.add(UrlData.getImageLists().get(j));
            }
            DemoBean demoBean = new DemoBean();
            demoBean.setImages(img);
            mDatas.add(demoBean);
        }

        RecyclerView mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRv.setAdapter(new MyAdapter());

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DemoActivity.this).inflate(R.layout.demo_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            holder.tv.setText("条目 " + position);
            List<ImageInfo> imageInfos = getImageInfos(position);
            holder.angv.setAdapter(new AssNineGridViewClickAdapter(DemoActivity.this, imageInfos));


        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        private List<ImageInfo> getImageInfos(int position) {
            List<ImageInfo> imageInfos = new ArrayList<>();
            List<String> images = mDatas.get(position).getImages();
            for (String url : images) {
                ImageInfo imageInfo = new ImageInfo();

                imageInfo.setBigImageUrl(url);
                imageInfo.setThumbnailUrl(url);
                imageInfos.add(imageInfo);
            }
            return imageInfos;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            AssNineGridView angv;
            TextView tv;

            MyViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                angv = itemView.findViewById(R.id.angv);
            }
        }

    }


}
