package com.jeff.footballmanager.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.jeff.footballmanager.activity.NewsActivity;
import com.jeff.footballmanager.adapter.MyNewsListViewAdapter;
import com.jeff.footballmanager.domain.Result;
import com.jeff.footballmanager.domain.news;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.utils.HttpUtils;
import com.jeff.footballmanager.utils.ImageCacheUtils;
import com.jeff.footballmanager.utils.RefreshView;
import com.jeff.footballmanager.utils.RefreshView.PullToRefreshListener;

public class FragmentOne extends BaseFragment{
	
	ImageCacheUtils cacheUtils = null;
	private ListView fragmentOneListView;
	private List<news> list_data = new ArrayList<news>();
	private LinearLayout appendTip; 
	private boolean onGetData = false,onGetBitMap=false;
	/**
	 * 当前页数
	 */
	private int page = 1;
	/**
	 * 当前项
	 */
	private int tableNum;
	/**
	 * 每页显示数量
	 */
	private static final int pagesize = 6;
	
	public FragmentOne(int item) {
		tableNum = item;
	}
	
	private MyNewsListViewAdapter fragemntAdapter;
	private RefreshView refreshView;
	
	@SuppressLint("HandlerLeak")
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
				Toast.makeText(getActivity(),getString(R.string.errorconnect),Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}

	};
	
	
	//将获取到的新闻和控件适配 并开始获取图片
	private void setNews() {
		if(null==list_data.get(list_data.size()-1).getTopmap()){
			onGetBitMap = true;
			getBitMapThread();
		}
		//清除已经添加的tip 并将获取信息状态置为fasle
		appendTip.removeAllViews();
		fragemntAdapter.notifyDataSetChanged();
		onGetData = false;
	}
	
	private void getBitMapThread(){
		cacheUtils = new ImageCacheUtils(getActivity());
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bitmap map = null;
				HttpUtils httpUtils = new HttpUtils();
				for(int i=(page-1)*pagesize;i<list_data.size();i++){
					String http = list_data.get(i).getTop_image();
					map = cacheUtils.disPlayImage(http);
					if(map==null){
						map = httpUtils.getBitMap(http);
						cacheUtils.saveBitmap(map,http);
					}
					list_data.get(i).setTopmap(map);
				}
				map = null;
				Message msg = new Message();
				msg.what = StaticParam.MESSAGE_SHOW;
				handler.sendMessage(msg);
				onGetBitMap = false;
			}
		}).start();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		//初始化碎片及其控件
		initView(view);
		fragemntAdapter = new MyNewsListViewAdapter(getActivity(), R.layout.news_list_item, list_data);
		fragmentOneListView.setAdapter(fragemntAdapter);
		getData();
		initListener();
		
		return view;
	}

	private void initListener() {
		
		fragmentOneListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), NewsActivity.class);
				intent.putExtra("news_id",list_data.get(position).getNews_id());
				intent.putExtra("tableNum",tableNum+"");
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
			
		});
		
		fragmentOneListView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//上拉加载
				if(firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount>0){
					if(!onGetData && !onGetBitMap){
						page++;
						getAPIData();
						appendTip.addView(appendTip());
						onGetData = true;
						Log.i("jeff","load--more");
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
		fragmentOneListView = (ListView) v.findViewById(R.id.news_list_listview_one);
		appendTip = (LinearLayout) v.findViewById(R.id.appendTip);
		refreshView = (RefreshView) v.findViewById(R.id.refresh_view);
		//下拉刷新
		refreshView.setOnRefreshListener(new PullToRefreshListener(){
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(2000);
					//重新初始化加载条件
					if(!onGetBitMap && !onGetData)
					{
						initGetData();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshView.finishRefreshing();
			}
		},0);
	}

	protected void initGetData() {
		list_data.clear();
		page=1;
		getAPIData();		
	}

	//请求接口数据
	private void getAPIData() {
		Log.i("jeff","tableNum="+tableNum+" -- page="+page);
		if(!onGetData){
			new Thread(new Runnable() {
				@Override
				public void run() {
					HttpUtils httpUtils = new HttpUtils();
					Result result = httpUtils.doGet(StaticParam.NEWS_URL+"&tableNum="+tableNum+"&pagesize="+pagesize+"&page="+page);
					Message msg = new Message();
					if(result!=null){
						msg.what = StaticParam.MESSAGE_SHOW;
						for(int i=0;i<result.getData().size();i++)
						{
							list_data.add(result.getData().get(i));
						}
					}
					else{
						msg.what = StaticParam.MESSAGE_ERROR;
					}
					handler.sendMessage(msg);
				}
			}).start();
		}
	}
	
	public void sendMessage(int i,Object obj){
		Message msg = new Message();
		msg.what = i;
		if(null!=obj)
			msg.obj = obj;
		handler.sendMessage(msg);
	}
	
	@Override
	public void clearList() {
		if(!onGetBitMap && !onGetData && page>3){
			list_data.clear();
			page=1;
			fragemntAdapter.notifyDataSetChanged();
			System.gc();
			Log.i("jeff","clear---list_data");
		}else{
			
		}
	}
	
	@Override
	public void getData() {
		if(list_data.size()==0)
		getAPIData();
	}
}
