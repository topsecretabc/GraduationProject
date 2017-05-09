package com.jeff.footballmanager.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.adapter.MyAdapter;
import com.jeff.footballmanager.adapter.MyPagerAdapter;
import com.jeff.footballmanager.adapter.NewsAdapter;
import com.jeff.footballmanager.domain.MenuItem;
import com.jeff.footballmanager.fragment.BaseFragment;
import com.jeff.footballmanager.fragment.FragmentOne;
import com.jeff.footballmanager.fragment.FragmentSix;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.service.ClearCacheImageService;
import com.jeff.footballmanager.utils.ActivityController;
import com.jeff.footballmanager.utils.ImageCacheUtils;
import com.jeff.footballmanager.utils.ImageViewAsyncTask;
import com.jeff.footballmanager.utils.RoundImageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity implements OnClickListener{

	private ImageLoader mImageLoader;

	private ViewPager viewPager;
	private ImageView setting,bgImg;
	private SlidingMenu menu;
	private RoundImageView imgMenu;
	private ListView menuListView;
	private FragmentManager fm;
	private GridView newsGridView;

	private FragmentOne fragmentOne,fragmentTwo,fragmentThree,fragmentFour,
	fragmentFive,fragmentSeven,fragmentEight;
	private FragmentSix fragmentSix;

	private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
	private int currentItem = 0;
	private boolean showMenu=false;
	private int screenWidth,screenHeight;
	
	private int CURRENT_ITEM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		//屏幕宽度和高度
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
//		clearOldImage();
		Intent service = new Intent(this,ClearCacheImageService.class);
		startService(service);
		
		//初始化侧滑菜单
		initSlidingMenu();
		//初始化控件
		initView();
		//添加到适配器
		setAdapter();
		//初始化控件监听
		initListener();
		
		Editor edit= getSharedPreferences("userInfo",MODE_PRIVATE).edit();
		edit.putString("userNo",StaticParam.USER_NO);
		
	}

//	private void clearOldImage() {
//		ImageCacheUtils cacheUtils = new ImageCacheUtils(this);
//		Integer size = ImageCacheUtils.getFolderSize();
//		Log.i("jeff", "图片已缓存大小："+size+"B"+"-----");
//		
//		if(size>1024*1024*60){
//			ImageCacheUtils.clearLocalBitmap(0.6);
//		}
//		if(size>1024*1024*40){
//			ImageCacheUtils.clearLocalBitmap(0.4);
//		}
//		if(size>1024*1024*30){
//			ImageCacheUtils.clearLocalBitmap(0.2);
//		}
//		
//		Integer size2 = ImageCacheUtils.getFolderSize();
//		Log.i("jeff", "清除旧照片后已缓存大小："+size2+"B"+"-----");		
//	}

	private void setAdapter() {
		
		List<MenuItem> data = new ArrayList<MenuItem>();
		MenuItem item = new MenuItem(R.drawable.home165,getString(R.string.title));
		MenuItem item2 = new MenuItem(R.drawable.footballsetting,getString(R.string.footballmanager));
		MenuItem item7 = new MenuItem(R.drawable.personpage,getString(R.string.personmanager));
		MenuItem item8 = new MenuItem(R.drawable.help,getString(R.string.help_about));
		data.add(item);
		data.add(item2);
		data.add(item7);
		data.add(item8);
		MyAdapter adapter = new MyAdapter(MainActivity.this,R.layout.menu_listview,data);
		menuListView.setAdapter(adapter);
		
		fragments.add(fragmentOne);
		fragments.add(fragmentTwo);
		fragments.add(fragmentThree);
		fragments.add(fragmentFour);
		fragments.add(fragmentFive);
		fragments.add(fragmentSix);
		fragments.add(fragmentSeven);
		fragments.add(fragmentEight);
		fm = getSupportFragmentManager();
		MyPagerAdapter myadapter = new MyPagerAdapter(fm,fragments); 
		viewPager.setAdapter(myadapter);
		
		List<String> newsTitle = new ArrayList<String>();
		newsTitle.add(getString(R.string.toutiao));
		newsTitle.add(getString(R.string.yule));
		newsTitle.add(getString(R.string.junshi));
		newsTitle.add(getString(R.string.qiche));
		newsTitle.add(getString(R.string.caijing));
		newsTitle.add(getString(R.string.xiaohua));
		newsTitle.add(getString(R.string.tiyu));
		newsTitle.add(getString(R.string.keji));
		
		//设置GridView的列数，让其一行显示完
		int columnWidth = screenWidth/newsTitle.size();
		newsGridView.setColumnWidth(columnWidth);
		newsGridView.setNumColumns(newsTitle.size());
		NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, R.layout.news_item_title,newsTitle);
		newsGridView.setAdapter(newsAdapter);
	}

	private void initListener() {
		
		imgMenu.setOnClickListener(this);
		
		menuListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 1:
					startMyActivity(MainActivity.this,new FootManagerActivity(),null,0);				
					break;
				case 2:
					startMyActivity(MainActivity.this,new PersonManagerActivity(),null,0);
					break;
				case 3:
					startMyActivity(MainActivity.this,new HelpActivity(),null,0);
//					showToastSuccess(MainActivity.this,"疑问帮助功能尚未开放！",1000);
					break;
				default:
					menu.toggle();
					break;
				}
				overridePendingTransition(R.anim.slide_top_to_bottom,
						R.anim.slide_bottom_to_under);
			}
		});
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				showGridViewItem(arg0);
				BaseFragment baseFragment = fragments.get(CURRENT_ITEM);
				baseFragment.clearList();
				CURRENT_ITEM = arg0;
				BaseFragment currentFragment = fragments.get(CURRENT_ITEM);
				currentFragment.getData();
			}
		});
		
		newsGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				viewPager.setCurrentItem(position);
				BaseFragment baseFragment = fragments.get(CURRENT_ITEM);
				CURRENT_ITEM = position;
				baseFragment.clearList();
				BaseFragment currentFragment = fragments.get(CURRENT_ITEM);
				currentFragment.getData();
			}
		});
	}

	protected void showGridViewItem(int i) {
		TextView view = (TextView) ((ViewGroup)newsGridView.getChildAt(i)).getChildAt(0);
		TextView currentView = (TextView) ((ViewGroup)newsGridView.getChildAt(currentItem)).getChildAt(0);
		view.setTextColor(this.getResources().getColor(R.color.red));
		currentView.setTextColor(this.getResources().getColor(R.color.black));
		currentItem = i;
	}

	private void initView() {
		fragmentOne = new FragmentOne(1);
		fragmentTwo = new FragmentOne(2);
		fragmentThree = new FragmentOne(3);
		fragmentFour = new FragmentOne(4);
		fragmentFive = new FragmentOne(5);
		fragmentSix = new FragmentSix();
		fragmentSeven = new FragmentOne(7);
		fragmentEight = new FragmentOne(8);

//		fragmentFour = new FragmentFour();
//		fragmentFive = new FragmentFive();
//		fragmentSix = new FragmentSix();
//		fragmentEight = new FragmentEight();
		setting = (ImageView) findViewById(R.id.backTo);
		setting.setVisibility(View.GONE);
		imgMenu = (RoundImageView) findViewById(R.id.imgMenu);
		menuListView = (ListView) findViewById(R.id.menu_listview);
		viewPager = (ViewPager) findViewById(R.id.viewPagerMain);
		newsGridView = (GridView) findViewById(R.id.news_gridview);
		bgImg = (ImageView) findViewById(R.id.headImg);
		if("".equals(StaticParam.BG_PATH.toString())){
			bgImg.setImageBitmap(null);
			bgImg.setImageResource(R.drawable.bgpic);
		}else{
			bgImg.setTag(StaticParam.BG_PATH);
			ImageViewAsyncTask asyncTask = new ImageViewAsyncTask(bgImg, StaticParam.BG_PATH);
			asyncTask.execute();
		}
		//设置头像
		if("".equals(StaticParam.HEAD_PATH))
		{
			imgMenu.setImageBitmap(null);
			imgMenu.setImageResource(R.drawable.head_icon);
		}else{
			mImageLoader = initImageLoader(this, mImageLoader, "test");
			mImageLoader.displayImage(StaticParam.HEAD_PATH,imgMenu);
		}
	}
	
	private void initSlidingMenu() {
		//设置侧滑菜单
		menu = new SlidingMenu(this);
		//设置宽度
		menu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
//		menu.setBehindWidth(400);
		//设置侧滑模式
		menu.setMode(SlidingMenu.LEFT);
		//设置触发全屏方式 因为添加了滑动fragment，所以这里去除滑动触发侧滑菜单
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setFadeDegree(0.35f); 
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.slidingmenu);		
	}

	private boolean isExit=false;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(!isExit){
				isExit = true;
				Toast.makeText(this,getString(R.string.exit_app),Toast.LENGTH_LONG).show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}finally{
							isExit = false;
						}
					}
				});
			}else{
				ActivityController.finishAllActivity();
			}
			return false;
		}else
			return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgMenu:
			menu.toggle();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 初始化图片下载器
	 */
	public ImageLoader initImageLoader(Context context,
			ImageLoader imageLoader, String dirName) {
		imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) {
			// 重新初始化ImageLoader时,需要释放资源.
			imageLoader.destroy();
		}
		imageLoader.init(initImageLoaderConfig(context, dirName));
		return imageLoader;
	}

	/**
	 * 配置图片下载器
	 * 
	 * @param dirName
	 *            文件名
	 */
	private ImageLoaderConfiguration initImageLoaderConfig(
			Context context, String dirName) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(new File(dirName)))
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		return config;
	}

	private int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
															// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}
}
