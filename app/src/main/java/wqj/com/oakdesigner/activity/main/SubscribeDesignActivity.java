package wqj.com.oakdesigner.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.coorchice.library.SuperTextView;
import com.gw.swipeback.SwipeBackLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import wqj.com.oakdesigner.R;
import wqj.com.oakdesigner.utils.Constant;
import wqj.com.oakdesigner.utils.adjuster.RippleAdjuster;

public class SubscribeDesignActivity extends AppCompatActivity {

    @BindView(R.id.superTitle)
    SuperTextView mSuperTitle;
    private EditText et_name, et_area, et_phone;
    private CheckBox new_House, old_House;
    private Button btn_submit;
    private String status = "新房";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_design);
        ButterKnife.bind(this);
        SwipeBackLayout mSwipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipeBackLayout);
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);
        mSwipeBackLayout.isSwipeFromEdge();
        mSwipeBackLayout.setSwipeBackListener(new SwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void onViewPositionChanged(View mView, float swipeBackFraction, float SWIPE_BACK_FACTOR) {
            }

            @Override
            public void onViewSwipeFinished(View mView, boolean isEnd) {
                SubscribeDesignActivity.this.finish();
            }
        });
        initView();
    }

    public void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_area = (EditText) findViewById(R.id.et_area);
        et_phone = (EditText) findViewById(R.id.et_phone);
        new_House = (CheckBox) findViewById(R.id.checkbox_new);
        old_House = (CheckBox) findViewById(R.id.checkbox_old);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        SuperTextView.Adjuster adjuster = new RippleAdjuster(R.color.purple);
        mSuperTitle.setAdjuster(adjuster);
        ImmersionBar.with(this).init();
        new_House.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    old_House.setChecked(false);
                    status = "新房";
                } else {
                    old_House.setChecked(true);
                    status = "旧房";
                }
            }
        });
        old_House.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new_House.setChecked(false);
                    status = "旧房";
                } else {
                    new_House.setChecked(true);
                    status = "新房";
                }
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtn_submit();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    public void setBtn_submit() {
        OkGo.<String>post(Constant.BaseUrl + "saveReserve")
                .tag(this)
                .retryCount(3)
                .params("ifcheck", "1")
                .params("fast_name", et_name.getText().toString())
                .params("fast_area", et_area.getText().toString())
                .params("fast_colour", status)
                .params("fast_phone", et_phone.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject js = new JSONObject(response.body());
                            String message = response.message();
                            Log.w("onSuccess: ", message);
                            String status = js.getString("status");
                            Log.w("onSuccess: ", status);
                            if (message.equals("OK") && status.equals("1")) {
                                String result = js.getString("result");
                                if (result.equals("预约成功")) {
                                    Toast.makeText(SubscribeDesignActivity.this, "预约成功", Toast.LENGTH_SHORT).show();
                                    SubscribeDesignActivity.this.finish();
                                } else {
                                    Toast.makeText(SubscribeDesignActivity.this, "预约失败，请重新预约", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }
}
