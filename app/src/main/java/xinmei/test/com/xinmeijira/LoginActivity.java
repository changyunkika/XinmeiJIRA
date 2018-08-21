package xinmei.test.com.xinmeijira;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText mEd_address, mEd_user, mEd_pass;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEd_address = findViewById(R.id.ed_address);
        mEd_user = findViewById(R.id.ed_user);
        mEd_pass = findViewById(R.id.ed_pass);
        mLogin = findViewById(R.id.login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setokhttp();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    public void setokhttp() {
        OkHttpUtils
                .post()
                .url("http://" + mEd_address.getText().toString() + "/rest/auth/1/session")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .addHeader("Cookie", "AJS.conglomerate.cookie=\"|streams.view.10003=full-view\"; seraph.rememberme.cookie=10525%3A5cf0b074f72d4d47270d312840e9b8f43d6aef4b")
                .addParams("username", mEd_user.getText().toString())
                .addParams("password", mEd_pass.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this, "" + response, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                });
    }
}
