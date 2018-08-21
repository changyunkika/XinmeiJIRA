package xinmei.test.com.xinmeijira;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import xinmei.test.com.xinmeijira.tools.Datas;

public class DetailActivity extends AppCompatActivity {

    private TextView mProject, mBugid, mSummary, mComponents, mAffects, mFix, mAssignee, mreporter, mDescription, xiugai;
    private DetailBean detailBean;
    private ImageView back, mAttachments;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //拿点击 list 传的id,用于请求对应 bug id 的detail
        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        xiugai = findViewById(R.id.xiugai);
        xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "此功能暂未开通", Toast.LENGTH_LONG).show();
            }
        });

//        Gson gson = new Gson();
//        String originalFundData = Datas.getOriginalFundDatas(DetailActivity.this);
//        detailBean = gson.fromJson(originalFundData, DetailBean.class);

//        mProject = findViewById(R.id.project);
//        mProject.setText(detailBean.getFields().getProject().getName() + "");
//        mBugid = findViewById(R.id.bugid);
//        mBugid.setText(detailBean.getKey() + "");
//        mSummary = findViewById(R.id.summary);
//        mSummary.setText(detailBean.getFields().getSummary() + "");
//        mComponents = findViewById(R.id.components);
//        mComponents.setText(detailBean.getFields().getComponents() + "");
//        mAffects = findViewById(R.id.affects);
//        mAffects.setText(detailBean.getFields().getVersions().get(0).getName());
//        mFix = findViewById(R.id.fix);
//        mFix.setText(detailBean.getFields().getFixVersions().get(0).getName());
//        mAssignee = findViewById(R.id.assignee);
//        mAssignee.setText(detailBean.getFields().getAssignee().getDisplayName());
//        mDescription = findViewById(R.id.description);
//        mDescription.setText(detailBean.getFields().getDescription());
//        mAttachments = findViewById(R.id.attachments);
//        Glide.with(DetailActivity.this)
//                .load(detailBean.getFields().getAttachment().get(0).getContent())
//                .into(mAttachments);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setokhttp();
    }

    public void setokhttp() {
        OkHttpUtils
                .get()
                .url("http://192.168.100.13:8090/rest/api/2/issue/" + key)
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
                        detailBean = gson.fromJson(response, DetailBean.class);
                        if (detailBean == null || detailBean.getFields() == null) {
                            Toast.makeText(DetailActivity.this, "必要字段为null", Toast.LENGTH_SHORT).show();
                            return;
                        }
//                        Toast.makeText(DetailActivity.this, "" + response, Toast.LENGTH_LONG).show();
                        mProject = findViewById(R.id.project);
                        if (detailBean.getFields().getProject() != null
                                && detailBean.getFields().getProject().getName() != null) {
                            mProject.setText(detailBean.getFields().getProject().getName() + "");
                        }

                        mBugid = findViewById(R.id.bugid);
                        if (detailBean.getKey() != null) {
                            mBugid.setText(detailBean.getKey() + "");
                        }


                        mSummary = findViewById(R.id.summary);
                        if (detailBean.getFields().getSummary() != null) {
                            mSummary.setText(detailBean.getFields().getSummary() + "");
                        }

                        mComponents = findViewById(R.id.components);
                        if (detailBean.getFields().getComponents().size() > 0 &&
                                detailBean.getFields().getComponents().get(0) != null) {
                            mComponents.setText(detailBean.getFields().getComponents().get(0).getName() + "");
                        }

                        mAffects = findViewById(R.id.affects);
                        if (detailBean.getFields().getVersions().size() > 0 &&
                                detailBean.getFields().getVersions().get(0) != null) {
                            mAffects.setText(detailBean.getFields().getVersions().get(0).getName());
                        }

                        mFix = findViewById(R.id.fix);
                        if (detailBean.getFields().getFixVersions().size() > 0 &&
                                detailBean.getFields().getFixVersions().get(0) != null) {
                            mFix.setText(detailBean.getFields().getFixVersions().get(0).getName());
                        }

                        mAssignee = findViewById(R.id.assignee);
                        if (detailBean.getFields().getAssignee() != null) {
                            mAssignee.setText(detailBean.getFields().getAssignee().getDisplayName());
                        }

                        mreporter = findViewById(R.id.reporter);
                        if (detailBean.getFields().getReporter() != null) {
                            mreporter.setText(detailBean.getFields().getReporter().getDisplayName());
                        }

                        mDescription = findViewById(R.id.description);
                        if (detailBean.getFields().getDescription() != null) {
                            mDescription.setText(detailBean.getFields().getDescription());
                        }

                        mAttachments = findViewById(R.id.attachments);
                        if (detailBean.getFields().getAttachment().size() > 0 &&
                                detailBean.getFields().getAttachment().get(0) != null) {
                            Glide.with(DetailActivity.this)
                                    .load(detailBean.getFields().getAttachment().get(0).getContent())
                                    .into(mAttachments);
                        }
                    }

                });
    }
}
