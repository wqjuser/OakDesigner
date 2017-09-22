package wqj.com.oakdesigner.activity.bean;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：温清洁
 * 版    本：1.0
 * 创建日期：2017/9/25 15:49
 * 描    述：首页蓝钻工艺实体类
 * 修订历史：
 * ================================================
 */

public class BeanBlueProcess implements Serializable {
    private String img;
    private String title;

    public BeanBlueProcess(String img, String title) {
        this.img = img;
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
