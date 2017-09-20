package wqj.com.oakdesigner.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coorchice.library.SuperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import wqj.com.oakdesigner.R;
import wqj.com.oakdesigner.utils.adjuster.RippleAdjuster;

public class SubscribeDesignActivity extends AppCompatActivity {

    @BindView(R.id.superTitle)
    SuperTextView mSuperTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_design);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        SuperTextView.Adjuster adjuster = new RippleAdjuster(R.color.opacity_3_white);
        mSuperTitle.setAdjuster(adjuster);
    }
}
