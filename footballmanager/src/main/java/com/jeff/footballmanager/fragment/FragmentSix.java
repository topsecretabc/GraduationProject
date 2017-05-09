package com.jeff.footballmanager.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.adapter.NewsXiaoHuaAdapter;
import com.jeff.footballmanager.domain.Result;
import com.jeff.footballmanager.domain.news;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.utils.HttpUtils;
import com.jeff.footballmanager.utils.RefreshView;
import com.jeff.footballmanager.utils.RefreshView.PullToRefreshListener;

public class FragmentSix extends BaseFragment{

	private ListView fragmentSixListView;
	private List<news> list_data = new ArrayList<news>();
	private LinearLayout appendTip; 
	private boolean onGetData = false;
	private int page = 2;
	private static int pageSize = 6;
	private NewsXiaoHuaAdapter fragemntAdapter;
	private RefreshView refreshView;
	private boolean notShowPage = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_six, container, false);
		//初始化碎片及其控件
		initView(view);
		getData();
		fragemntAdapter = new NewsXiaoHuaAdapter(getActivity(), R.layout.news_xiaohua, list_data);
		fragmentSixListView.setAdapter(fragemntAdapter);

		initListener();
		return view;
	}
	
	private void initListener() {
		fragmentSixListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		
		fragmentSixListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//上拉加载
				if(firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount>0){
					if(!onGetData){
						page++;
						getAPIData();
						appendTip.addView(appendTip());
						onGetData = true;
					}
				}
			}
		});
	}

	//添加加载提示
	protected View appendTip() {
		TextView appendView = new TextView(getActivity());
		LinearLayout.LayoutParams layoutParams = 
			new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0,30,0,30);
		appendView.setLayoutParams(layoutParams);
		appendView.setText(getString(R.string.loadding_hard));
		return appendView;
	}
	
	private void initView(View v) {
		fragmentSixListView = (ListView) v.findViewById(R.id.news_list_listview_six);
		appendTip = (LinearLayout) v.findViewById(R.id.appendTip);
		refreshView = (RefreshView) v.findViewById(R.id.refresh_view);
		refreshView.setOnRefreshListener(new PullToRefreshListener(){
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(2000);
					if(!onGetData){
						list_data.clear();
						page=1;
						getAPIData();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshView.finishRefreshing();
			}
		},5);
	}
	
	//请求接口数据
	private void getAPIData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpUtils httpUtils = new HttpUtils();
				Result result = httpUtils.doGet(StaticParam.NEWS_URL+"&tableNum=6&pagesize="+pageSize+"&page="+page);
				Message msg = new Message();
				if(result!=null){
					msg.what = StaticParam.MESSAGE_SHOW;
					for(int i=0;i<result.getData().size();i++)
					list_data.add(result.getData().get(i));
				}
				else{
					msg.what = StaticParam.MESSAGE_ERROR;
				}
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case StaticParam.MESSAGE_SHOW:
				setNews();
				break;
			case StaticParam.MESSAGE_ERROR:
				appendTip.removeAllViews();
				Toast.makeText(getActivity(),getString(R.string.errorconnect), 1000).show();
				break;
			default:
				break;
			}
		}
	};
	
	private void setNews() {
		appendTip.removeAllViews();
		onGetData = false;
		fragemntAdapter.notifyDataSetChanged();
	}

	@Override
	public void clearList() {
		if(!onGetData && page>5){
			list_data.clear();
			page=1;
			fragemntAdapter.notifyDataSetChanged();
		}else{
			
		}
	}
	
	@Override
	public void getData() {
		if(list_data.size()==0)
		getAPIData();
	}
}
