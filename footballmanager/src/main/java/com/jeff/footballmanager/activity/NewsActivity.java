package com.jeff.footballmanager.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.domain.DataDetails;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.utils.HttpUtils;
import com.jeff.footballmanager.utils.MyImageGetter;

public class NewsActivity extends BaseActivity implements OnTouchListener{

	private String news_id,tableNum;
	private com.jeff.footballmanager.domain.news news;
	private TextView title,source,content;
	private ImageView topImg,loadImg;
	private AbsoluteLayout absoluteLay;
	private Animation animation,animation2;
	private float startX;
	private LinearLayout line_content;
	private ScrollView scrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		
		initViews();
		
		animation = AnimationUtils.loadAnimation(this, R.anim.upnews);
		animation2 = AnimationUtils.loadAnimation(this, R.anim.tip);
		//设置匀速效果
		//setInterpolator表示设置旋转速率。
		//LinearInterpolator为匀速效果，
		//Accelerateinterpolator为加速效果、
		//DecelerateInterpolator为减速效果
		LinearInterpolator interpolator = new LinearInterpolator();
		animation.setInterpolator(interpolator);
		animation.setFillAfter(true);
		animation2.setInterpolator(interpolator);
		loadImg.startAnimation(animation2);
		animation2.start();
		
		line_content.setOnTouchListener(this);
		content.setOnTouchListener(this);
		getNewsDetails();
	}

	private void initViews() {
		title = (TextView) findViewById(R.id.news_details_title);
		source = (TextView) findViewById(R.id.news_details_source);
		content = (TextView) findViewById(R.id.news_details_content);
		topImg = (ImageView) findViewById(R.id.news_details_top_img);
		absoluteLay = (AbsoluteLayout) findViewById(R.id.absl);
		loadImg = (ImageView) findViewById(R.id.loadImg);
		line_content = (LinearLayout) findViewById(R.id.line_content);
	}

	private void getNewsDetails() {
		
		news_id = getIntent().getStringExtra("news_id");
		tableNum = getIntent().getStringExtra("tableNum");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpUtils httpUtils = new HttpUtils();
				DataDetails result = httpUtils.doGetNew(StaticParam.NEWS_DETAILS_URL+"&news_id="+news_id+"&tableNum="+tableNum);
				if(result!=null){
					if(result.getStatus()==200){
						result.getData().setTopmap(
							httpUtils.getBitMap(result.getData().getTop_image()));
						sendMessage(result.getData());
					}else{
						sendMessage(null);
					}
				}else{
					sendMessage(null);
				}
			}
		}).start();
	}

	private void sendMessage(Object object) {
		Message msg = new Message();
		if(object==null){
			msg.what = StaticParam.MESSAGE_ERROR;
		}else
		{
			msg.what = StaticParam.MESSAGE_SHOW;
			news = (com.jeff.footballmanager.domain.news) object;
		}
		handler.sendMessage(msg);
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case StaticParam.MESSAGE_SHOW:
				initNew();
				break;
			case StaticParam.MESSAGE_ERROR:
				stopAnim();
				Toast.makeText(NewsActivity.this,getString(R.string.errorconnect),showToastTimeShort).show();
			default:
				break;
			}
		}
	};
	
	protected void initNew() {
		//开启动画
		stopAnim();
		title.setText(news.getTitle());
		source.setText("["+news.getSource().toString()+"]");
		//设置超链接可以打开网页
		content.setMovementMethod(LinkMovementMethod.getInstance());
		String html = "";
		if(null!=news.getContent() && (!"".equals(news.getContent())))
			html = news.getContent();
		else
			html = news.getDigest();
		content.setText(Html.fromHtml(html,new MyImageGetter(content,NewsActivity.this),null));
		topImg.setImageBitmap(news.getTopmap());
	}
	
	protected void stopAnim() {
		//开启动画
		absoluteLay.startAnimation(animation);
		animation.start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			this.finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			return false;
		}else{
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float endX=0;
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		int width = outMetrics.widthPixels;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//getRawX是相对于屏幕的位置，getX是相对于父控件的位置，
			//getTop，getLeft，getHeight，getWidth等都是相对于父控件的位置，即得到自己的相应值
			startX = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			endX = event.getRawX();
			int distance = (int) (endX-startX);
			if(distance>=(width/7)){
				NewsActivity.this.finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
			break;
		default:
			break;
		}
		return true;
	}
}
