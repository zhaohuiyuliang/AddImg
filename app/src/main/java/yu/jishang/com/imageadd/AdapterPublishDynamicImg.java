package yu.jishang.com.imageadd;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangliang on 2016/11/23.
 */

public class AdapterPublishDynamicImg extends BaseListAdapter<Drawable> {


    private int selectPosition = -1;


    public AdapterPublishDynamicImg(Callback context) {
        super(context);
    }

    @Override
    protected int getLayoutId(int position, Drawable item) {
        return R.layout.item_publish_dynamic_img;
    }

    @Override
    protected void convert(ViewHolder localHoldView, Drawable item, final int position) {
        ImageView img_delete = localHoldView.getView(R.id.img_delete);
        ImageView img_publish_dynamic_img = localHoldView.getView(R.id.img_publish_dynamic_img);
        img_publish_dynamic_img.setImageDrawable(item);
        if (selectPosition == position) {
            img_delete.setVisibility(View.VISIBLE);
        } else {
            img_delete.setVisibility(View.INVISIBLE);
        }
        img_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });

    }

    /**
     * 获取图片，除去添加标示图片
     *
     * @return
     */
    public List<Drawable> getListUriImgs() {
        List<Drawable> localUri = new ArrayList<>();
        if (getDatas() != null && getDatas().size() > 0) {
            localUri.addAll(getDatas());
            localUri.remove(getDatas().size() - 1);
        }
        return localUri;
    }

    public void selectPosition(int position) {
        selectPosition = position;
        notifyDataSetChanged();
    }
}
