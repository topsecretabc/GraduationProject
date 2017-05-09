package com.jeff.footballmanager.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeff.footballmanager.R;

public class HelpActivity extends BaseActivity{

	private static final String url = "content://com.forfootball.provider/help";
	private ImageView back;
	private TextView help,about;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		
//		help = (TextView) findViewById(R.id.help);
//		about = (TextView) findViewById(R.id.about);
		back = (ImageView) findViewById(R.id.imgMenuperson4);
		
//		getInfoFromProvider();

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	private void getInfoFromProvider() {
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(Uri.parse(url), null, null, null, null);
		if(cursor!=null && cursor.getCount()>0){
			cursor.moveToLast();
			String helpStr = cursor.getString(cursor.getColumnIndex("help_text"));
			String aboutStr = cursor.getString(cursor.getColumnIndex("about_us"));
			help.setText(helpStr+"");
			about.setText(aboutStr+"");
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.slide_from_bottom,
				R.anim.slide_to_top);
	}
	
}
