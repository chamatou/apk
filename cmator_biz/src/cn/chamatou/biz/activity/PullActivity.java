package cn.chamatou.biz.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.recyclerview.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class PullActivity extends Activity {
	PtrClassicFrameLayout ptrClassicFrameLayout;
    RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<String>();
    private RecyclerAdapter adapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_test);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_recycler_view_frame);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.test_recycler_view);
        init();
    }

    private void init() {
        adapter = new RecyclerAdapter(this, mData);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        mData.clear();
                        for (int i = 0; i < 17; i++) {
                            mData.add(new String("  RecyclerView item  -" + i));
                        }
                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.refreshComplete();
                        ptrClassicFrameLayout.setLoadMoreEnable(true);
                    }
                }, 2000);
            }
        });
        ptrClassicFrameLayout.setLoadMoreHandler(new PtrFrameLayout.LoadMoreHandler() {
			@Override
			public void loadMore() {
				handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(new String("  RecyclerView item  - add " + page));
                        mAdapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.loadMoreComplete(true);
                        page++;
                        Toast.makeText(PullActivity.this, "load more complete", Toast.LENGTH_SHORT)
                                .show();
                    }
                }, 1000);
			}
		});
    }

    public class RecyclerAdapter extends Adapter<ViewHolder> {
        private List<String> datas;
        private LayoutInflater inflater;

        public RecyclerAdapter(Context context, List<String> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            ChildViewHolder holder = (ChildViewHolder) viewHolder;
            holder.itemTv.setText(datas.get(position));
            holder.itemTv.setTextColor(Color.BLACK);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
            View view = inflater.inflate(android.R.layout.simple_list_item_1, null);
            return new ChildViewHolder(view);
        }

    }

    public class ChildViewHolder extends ViewHolder {
        public TextView itemTv;

        public ChildViewHolder(View view) {
            super(view);
            itemTv = (TextView) view;
        }

    }
	
}
