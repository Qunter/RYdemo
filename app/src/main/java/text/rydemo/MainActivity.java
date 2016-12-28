package text.rydemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,RongIM.UserInfoProvider{
    private static final String token1 = "dHGn5hbkp2uoaGNdM/ndPLU/IPKPq/4/rzu3rTMXUSRCNEJ9ciLWMJHPIuBZ/kIF0Ei/ZppjqKA82Y/G7o2WKw==";
    private static final String token2 = "6MIbG1VlvdWAiydrjMywsbU/IPKPq/4/rzu3rTMXUSRCNEJ9ciLWMBLZ8ctqCum3erxsHF4GjjM7yNcdn2DPuA==";
    private List<Friend> userIdList;
    private Button mUser1, mUser2;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser1 = (Button) findViewById(R.id.connect_10010);
        mUser2 = (Button) findViewById(R.id.connect_10086);
        mUser1.setOnClickListener(this);
        mUser2.setOnClickListener(this);
        initUserInfo();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.connect_10010) {
            connectRongServer(token1);
        } else if (v.getId() == R.id.connect_10086) {
            connectRongServer(token2);
        }
    }

    private void connectRongServer(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {


            @Override
            public void onSuccess(String userId) {

                if (userId.equals("10010")) {
                    mUser1.setText("用户1连接服务器成功");
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    Toast.makeText(MainActivity.this, "connect server success 10010", Toast.LENGTH_SHORT).show();

                } else {
                    mUser2.setText("用户2连接服务器成功");
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    Toast.makeText(MainActivity.this, "connect server success 10086", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                // Log.e("onError", "onError userid:" + errorCode.getValue());//获取错误的错误码
                Log.e(TAG, "connect failure errorCode is : " + errorCode.getValue());
            }


            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "token is error ,please check token and appkey");
            }
        });

    }


    private void initUserInfo() {
        userIdList = new ArrayList<Friend>();
        userIdList.add(new Friend("10010", "联通", "http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg"));//联通图标
        userIdList.add(new Friend("10086", "移动", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));//移动图标
        userIdList.add(new Friend("KEFU144542424649464","在线客服","http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));
        RongIM.setUserInfoProvider(this, true);
    }

    @Override
    public UserInfo getUserInfo(String s) {

        for (Friend i : userIdList) {
            if (i.getUserId().equals(s)) {
                Log.e(TAG, i.getPortraitUri());
                return new UserInfo(i.getUserId(), i.getName(), Uri.parse(i.getPortraitUri()));
            }
        }


        Log.e("MainActivity", "UserId is : " + s);
        return null;
    }
}
