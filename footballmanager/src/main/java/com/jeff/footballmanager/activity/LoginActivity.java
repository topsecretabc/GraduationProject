package com.jeff.footballmanager.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeff.footballmanager.R;
import com.jeff.footballmanager.param.APIRetParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.User;
import com.jeff.footballmanager.param.UserParam;
import com.jeff.footballmanager.param.thirdParam.QQresultParam;
import com.jeff.footballmanager.utils.ActivityController;
import com.jeff.footballmanager.utils.GetApiAsyncTask;
import com.jeff.footballmanager.utils.GsonUtils;
import com.jeff.footballmanager.utils.LoginAsyncTask;
import com.jeff.footballmanager.utils.StringUtils;
import com.tencent.connect.UserInfo;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends BaseActivity {

    private Button login;
    private EditText userName,userPsd;
    private APIRetParam<User> user = new APIRetParam();
    private CheckBox checkBox;
    private TextView forgetPsd,register;
    private ImageView qqLogin,weixinLogin,weiboLogin;
    private Tencent mTencent;
    private IUiListener mListener;
    private UserInfo userInfo;
    private boolean isToLogin;

    private APIRetParam<User> qqUser = null;
    private static final String ALL = "all";

    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        api = WXAPIFactory.createWXAPI(this, StaticParam.WX_APP_ID,true);
        api.registerApp(StaticParam.WX_APP_ID);

        initView();

        initData();

        initListener();

    }

    private boolean isExit = false;
    @Override
    public void onBackPressed() {
        if(!isExit){
            isExit = true;
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
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
    }

    private void initListener() {
        //正常登录
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = userName.getText().toString();
                String strPsd = userPsd.getText().toString();
                if(!StringUtils.isNullOrEmpty(strName)){
                    userName.requestFocus();
                    Toast.makeText(LoginActivity.this,getString(R.string.userName),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!StringUtils.isNullOrEmpty(strName)){
                    userPsd.requestFocus();
                    Toast.makeText(LoginActivity.this,getString(R.string.userPsd),Toast.LENGTH_SHORT).show();
                    return;
                }
                saveInfo(strName,strPsd,false);

                UserParam param = new UserParam();
                param.setName(strName);
                param.setPsd(strPsd);
                if(!isToLogin)
                {
                    isToLogin = true;
                    doLogin(param);
                }
            }
        });

        if(!isToLogin)
            //qq登录
            qqLogin.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mTencent.isSessionValid()){
                        mTencent.login(LoginActivity.this,ALL,mListener);
                    }else{
                        mTencent.logout(LoginActivity.this);
                        mTencent.loginServerSide(LoginActivity.this,ALL,mListener);
                    }
                }
            });

        weixinLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doWxLogin();
            }
        });

        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyActivity(LoginActivity.this,new RegisterUserActivity(), null,0);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });

        forgetPsd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyActivity(LoginActivity.this,new RegisterUserActivity(), null,1);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });

    }

    private void doWxLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    private void initView() {
        login = (Button) findViewById(R.id.login);
        userName = (EditText) findViewById(R.id.userName);
        userPsd = (EditText) findViewById(R.id.userPsd);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        forgetPsd =(TextView) findViewById(R.id.remepassword);
        register =(TextView) findViewById(R.id.register);
        qqLogin = (ImageView) findViewById(R.id.qqLogin);
        weixinLogin = (ImageView) findViewById(R.id.weixinLogin);
//		weiboLogin = (ImageView) findViewById(R.id.weiboLogin);
        mTencent = Tencent.createInstance(StaticParam.QQ_APP_ID,LoginActivity.this);
        mListener = new IUiListener() {
            @Override
            public void onError(UiError arg0) {
            }

            @Override
            public void onComplete(Object arg0) {
                Gson gson = new Gson();
                QQresultParam result = gson.fromJson(arg0.toString(),QQresultParam.class);
                mTencent.setAccessToken(result.getAccess_token(),result.getExpires_in());
                mTencent.setOpenId(result.getOpenid());
                userInfo = new UserInfo(LoginActivity.this,mTencent.getQQToken());
                userInfo.getUserInfo(new UserInfoListener());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this,getString(R.string.cancel_login), Toast.LENGTH_SHORT).show();
            }
        };
    }

    class UserInfoListener implements IUiListener{
        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,getString(R.string.login_fault), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onComplete(Object arg0) {
            Log.i("user", arg0.toString());
            JSONObject jo = (JSONObject) arg0;

            String nickname = "";
            String province = "";
            String age = "0";
            String headPath = "";
            String bgPath = "";
            String openid = mTencent.getOpenId();

            try {
                nickname = jo.getString("nickname");
                province = jo.getString("province")+jo.getString("city");
//				age = jo.getString("nickname");
                headPath = jo.getString("figureurl_qq_2");
                bgPath = jo.getString("figureurl_2");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            UserParam userParam = new UserParam();
            userParam.setName(nickname);
            userParam.setAge(age);
            userParam.setOpenid(openid);
            userParam.setAddress(province);
            userParam.setHeadPath(headPath);
            userParam.setPsd("");
            userParam.setBgPath(bgPath);
            doRegister(userParam);
        }
        @Override
        public void onError(UiError arg0) {
            Toast.makeText(LoginActivity.this,getString(R.string.login_fault),Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
        boolean checkStatus = pref.getBoolean("checkStatus",false);
        checkBox.setChecked(checkStatus);
        boolean qqLogin = pref.getBoolean("login",false);
        if(!qqLogin){
            userName.setText(pref.getString("name",""));
            userPsd.setText(pref.getString("psd",""));
        }else{
            doLogin(getInfo());
        }
    }

    //第三方登录服务器端自动注册用户
    public void doRegister(UserParam userParam) {
        GetApiAsyncTask apiAsyncTask = new GetApiAsyncTask(
                LoginActivity.this,StaticParam.API+StaticParam.API_TO_QQ_REGISTER+StaticParam.API_TOKEN);
        apiAsyncTask.execute(userParam.toString());
    }

    protected void saveInfo(String strName, String strPsd,boolean flag) {
        Editor edit= getSharedPreferences("userInfo",MODE_PRIVATE).edit();
        edit.putBoolean("checkStatus",checkBox.isChecked());
        edit.putString("name", strName);
        edit.putString("psd", strPsd);
        edit.putBoolean("login",flag);

        edit.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //应用调用Andriod_SDK接口时，如果要成功接收到回调，
        //需要在调用接口的Activity的onActivityResult方法中增加如下代码：
        Tencent.onActivityResultData(requestCode,resultCode,data,mListener);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void backLogin() {
        isToLogin = false;
    }

    @Override
    public void doAfterGetData(String data) {
        qqUser = GsonUtils.getGson().fromJson(data,APIRetParam.class);
        if(qqUser!=null){
            if(qqUser.getMsg().equals(StaticParam.SUCCESS)){
                UserParam param = new UserParam();
                String name = qqUser.getData().get(0).getName();
                String psd = qqUser.getData().get(0).getPsd();
                param.setName(name);
                param.setPsd(psd);
                saveInfo(name,psd,true);
                if(!isToLogin)
                {
                    isToLogin = true;
                    doLogin(param);
                }
            }else if(qqUser.getMsg().equals(StaticParam.FAULT)){
                UserParam param = new UserParam();
                String name = qqUser.getData().get(0).getName();
                String psd = qqUser.getData().get(0).getPsd();
                param.setName(name);
                param.setPsd(psd);
                saveInfo(name,psd,true);
                doLogin(getInfo());
            }else{
                showToastSuccess(LoginActivity.this, qqUser.getMsg(),showToastTimeShort);
            }
        }else{
            showToastSuccess(LoginActivity.this,getString(R.string.errorconnect),showToastTimeShort);
        }
    }

    private UserParam getInfo() {
        SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
        UserParam user = new UserParam();
        user.setName(pref.getString("name",""));
        user.setPsd(pref.getString("psd",""));
        return user;
    }

    public void doLogin(UserParam param){
        LoginAsyncTask<User> asyncTask = new LoginAsyncTask(user,LoginActivity.this,MainActivity.class);
        asyncTask.execute(param);
    }

}
