package wqj.com.oakdesigner.activity.others;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wqj.com.oakdesigner.R;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.title_back_layout)
    LinearLayout mTitleBackLayout;
    @BindView(R.id.title_name_tv)
    TextView mTitleNameTv;
    @BindView(R.id.title_name_right)
    TextView mTitleNameRight;
    @BindView(R.id.title_layout_right)
    LinearLayout mTitleLayoutRight;
    @BindView(R.id.public_function_layout)
    LinearLayout mPublicFunctionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        mTitleBackLayout.setBackgroundResource(R.drawable.ic_chevron_left_black_24dp);
    }

    @OnClick(R.id.title_back_layout)
    public void onViewClicked() {
        this.finish();

    }
}
