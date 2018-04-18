/*
 * Copyright (c) 2018.
 * Created by shentupenghui
 * Last modified 18-4-18 下午1:46
 */

package cn.shentupenghui.zto.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.shentupenghui.zto.R;
import cn.shentupenghui.zto.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {

    //默认管理账号
    private String DEFAULT_USERNAME = "123456";
    private String DEFAULT_PASSWD = "admin";
    //输入的用户名和密码
    private String username, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        //获取相关信息
        final EditText editText_username = (EditText) findViewById(R.id.username);
        final EditText editText_passwd = (EditText) findViewById(R.id.passwd);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        //获得SharedPreferences,创建文件shentupenghui
        final SharedPreferences sharedPreferences = getSharedPreferences("shentupenghui", MODE_PRIVATE);
        //判断账号密码和后台相同时，直接登录
        if (sharedPreferences.getString("username", username) != null && sharedPreferences.getString("passwd", passwd) != null) {
            if (sharedPreferences.getString("username", username).equals(DEFAULT_USERNAME) && sharedPreferences.getString("passwd", passwd).equals(DEFAULT_PASSWD)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }//如果文件中账号密码不存在时，需要手动登录，并存储账号和密码
        } else {
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = editText_username.getText().toString();//获得输入的账号
                    passwd = editText_passwd.getText().toString();//获得输入的密码
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获得Editor对象，用于存储账号和密码信息
                    //TODO 这里验证用户名和密码用的是本地数据，之后开发需要替换本处代码
                    if (username.equals(DEFAULT_USERNAME) && passwd.equals(DEFAULT_PASSWD)) {
                        Toast.makeText(LoginActivity.this, "欢迎回来", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        editor.putString("username", username);
                        editor.putString("passwd", passwd);
                        editor.commit();
                    } else {
                        Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //从sharePreferences根据用户名读取密码
    private String readPsw(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sharedPreferences.getString(username, "");
    }

    //保存登录状态和用户名到sharedpreferences中
    private void saveLoginStatus(boolean status, String username) {
        //loginInfo表示文件名
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean("isLogin", status);
        editor.putString("loginUserName", username);//存入登录时的用户名
        editor.commit();//提交修改
    }
}
