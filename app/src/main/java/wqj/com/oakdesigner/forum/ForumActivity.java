package wqj.com.oakdesigner.forum;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wqj.com.oakdesigner.R;

public class ForumActivity extends AppCompatActivity {

    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_test)
    public void onViewClicked() {
    }
}
