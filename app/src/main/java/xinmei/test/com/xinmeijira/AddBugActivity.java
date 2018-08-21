package xinmei.test.com.xinmeijira;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddBugActivity extends AppCompatActivity {

    private ImageView mBack;
    private TextView mWancheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_bug);
        mBack = findViewById(R.id.back);
        mWancheng = findViewById(R.id.wancheng);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddBugActivity.this, "此功能暂未开通", Toast.LENGTH_LONG).show();
            }
        });
    }
}
