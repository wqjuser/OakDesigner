package wqj.com.oakdesigner.activity.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import wqj.com.oakdesigner.R;
import wqj.com.oakdesigner.activity.bean.BeanBlueProcess;
import wqj.com.oakdesigner.utils.Constant;

/**
 * ================================================
 * 作    者：温清洁
 * 版    本：1.0
 * 创建日期：2017/9/25 15:47
 * 描    述：首页蓝钻工艺适配器
 * 修订历史：
 * ================================================
 */

public class Adapter_blue_process extends BaseQuickAdapter<BeanBlueProcess, BaseViewHolder> {

    public Adapter_blue_process(@LayoutRes int layoutResId, @Nullable List<BeanBlueProcess> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BeanBlueProcess item) {
        helper.setText(R.id.blue_img_title, item.getTitle());
        //加载网络图片
        Glide.with(Constant.mContext)
                .load(item.getImg())
                .centerCrop()
                .into((ImageView) helper.getView(R.id.blue_img));
    }
}
