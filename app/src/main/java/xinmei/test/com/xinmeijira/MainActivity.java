package xinmei.test.com.xinmeijira;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import xinmei.test.com.xinmeijira.tools.Datas;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private ListBean bean;
    private TextView mUsername, mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
//        Gson gson = new Gson();
//        String originalFundData = Datas.getOriginalFundData(MainActivity.this);
//        bean = gson.fromJson(originalFundData, ListBean.class);
//        setOK();
        mAdd = findViewById(R.id.add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddBugActivity.class));
            }
        });
    }

    public void setOK() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMyAdapter = new MyAdapter(MainActivity.this, bean);
        mRecyclerView.setAdapter(mMyAdapter);
        /**
         * 调用item的点击和长按事件
         * */
        mMyAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("key", bean.getIssues().get(position).getKey() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setokhttp();
    }

    public void setokhttp() {
        OkHttpUtils
                .get()
                .url("http://192.168.100.13:8090/rest/api/2/search?jql=%20ORDER%20BY%20created%20DESC,%20summary%20ASC&maxResults=100")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                .addHeader("Cookie", "AJS.conglomerate.cookie=\"|streams.view.10003=full-view\"; seraph.rememberme.cookie=10525%3A5cf0b074f72d4d47270d312840e9b8f43d6aef4b")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        bean = gson.fromJson(response, ListBean.class);
                        setOK();
//                        Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
