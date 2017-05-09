package com.jeff.footballmanager.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.param.APIRetParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.User;
import com.jeff.footballmanager.param.UserParam;
import com.jeff.footballmanager.utils.GetApiAsyncTask;
import com.jeff.footballmanager.utils.GsonUtils;

public class RegisterUserActivity extends BaseActivity {

	private EditText userName,userPsd,userAge,userAddress;
	private Button addUser;
	private int TYPE = 0;
	private static final int REGISTER_USER=0;
	private static final int FORGET_PSD=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
		
		initView();
		
		addUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (TYPE) {
				case REGISTER_USER:
					doRegister();
					break;
				case FORGET_PSD:
					doForgetPsd();
				default:
					break;
				}
			}
		});
	}
	
	protected void doForgetPsd() {
		String name = userName.getText().toString();
		String age = userAge.getText().toString();
		String address = userAddress.getText().toString();

		if(name.equals("")){
			showToastSuccess(RegisterUserActivity.this,getString(R.string.userName),showToastTimeShort);
		}else if(age.equals("")){
			showToastSuccess(RegisterUserActivity.this,getString(R.string.userAge),showToastTimeShort);
		}else if(address.equals("")){
			showToastSuccess(RegisterUserActivity.this,getString(R.string.userAddress),showToastTimeShort);
		}
		else{
			UserParam userParam = new UserParam();
			userParam.setName(name);
			userParam.setAge(age);
			userParam.setAddress(address);
			GetApiAsyncTask apiAsyncTask = new GetApiAsyncTask(
				RegisterUserActivity.this,StaticParam.API+StaticParam.API_TO_FORGET_PSD+StaticParam.API_TOKEN);
			apiAsyncTask.execute(userParam.toString());
		}			
	}

	protected void doRegister() {
		String name = userName.getText().toString();
		String psd = userPsd.getText().toString();
		String age = userAge.getText().toString();
		String address = userAddress.getText().toString();

		if(name.equals("")){
			showToastSuccess(RegisterUserActivity.this,getString(R.string.userName),Toast.LENGTH_LONG);
		}else if(psd.equals("")){
			showToastSuccess(RegisterUserActivity.this,getString(R.string.userPsd),Toast.LENGTH_LONG);
		}else if(age.equals("")){
			showToastSuccess(RegisterUserActivity.this,getString(R.string.userAge),Toast.LENGTH_LONG);
		}else if(address.equals("")){
			showToastSuccess(RegisterUserActivity.this,getString(R.string.userAddress),Toast.LENGTH_LONG);
		}else{
			UserParam userParam = new UserParam();
			userParam.setName(name);
			userParam.setPsd(psd);
			userParam.setAge(age);
			userParam.setAddress(address);
			GetApiAsyncTask apiAsyncTask = new GetApiAsyncTask(
				RegisterUserActivity.this,StaticParam.API+StaticParam.API_TO_REGISTER+StaticParam.API_TOKEN);
			apiAsyncTask.execute(userParam.toString());
		}			
	}

	private void initView() {
		TYPE = getIntent().getIntExtra("num",0);
		userName = (EditText) findViewById(R.id.userNameRegi);
		userPsd = (EditText) findViewById(R.id.userPsdRegi);
		userAge = (EditText) findViewById(R.id.userAgeRegi);
		userAddress = (EditText) findViewById(R.id.userAddressRegi);
		addUser = (Button) findViewById(R.id.addUser);
		if (TYPE == FORGET_PSD) {
			initForgetPsdView();
		}
	}

	private void initForgetPsdView() {
		userPsd.setVisibility(View.GONE);
		addUser.setText(getString(R.string.forget_psd));
	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}

	@Override
	public void doAfterGetData(String data){
		APIRetParam<User> result = GsonUtils.getGson().fromJson(data,APIRetParam.class);
		if(result!=null){
			if(result.getMsg().equals(StaticParam.SUCCESS)){
				if(TYPE==REGISTER_USER){
					showToastSuccess(RegisterUserActivity.this,getString(R.string.register_ok),showToastTimeShort);
					onBackPressed();
				}else{
					showToastSuccess(RegisterUserActivity.this,getString(R.string.psd_info)+result.getData().get(0).getPsd(),showToastTime);
				}
			}else{
				showToastSuccess(RegisterUserActivity.this,result.getMsg(),showToastTimeShort);
			}
		}else{
			showToastSuccess(RegisterUserActivity.this,getString(R.string.errorconnect),showToastTimeShort);
		}
	}

}
