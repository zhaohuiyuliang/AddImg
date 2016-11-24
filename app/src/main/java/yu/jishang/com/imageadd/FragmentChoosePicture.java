package yu.jishang.com.imageadd;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by wangliang on 2016/11/23.
 */

public class FragmentChoosePicture extends BaseFragment implements BaseListAdapter.Callback,
        PopupWindowsImg.IModelPopupWindows {
    /**
     * 拍照按钮点击事件返回后的标示
     */
    private static final int PHOTO_CAMERA = 10;
    private static final int PHOTO_PICK = 20;
    private GridViewNonRolling grid_view_project_evaluation;
    private AdapterPublishDynamicImg adapterDynamicImg;//BP
    private File file;

    @Override
    protected int getLayoutId() {
        return R.layout.new_fragment_add_bp;
    }

    @Override
    public Date getSystemTime() {
        return null;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        grid_view_project_evaluation = findView(R.id.grid_view_project_evaluation);
        adapterDynamicImg = new AdapterPublishDynamicImg(this);
        grid_view_project_evaluation.setAdapter(adapterDynamicImg);
        grid_view_project_evaluation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == adapterDynamicImg.getCount() - 1) {
                    PopupWindowsImg popupWindows = new PopupWindowsImg(getActivity());
                    popupWindows.setPHOTO_CAMERA(PHOTO_CAMERA);
                    popupWindows.setPHOTO_PICK(PHOTO_PICK);
                    popupWindows.setModel(FragmentChoosePicture.this);
                }
            }


        });
        grid_view_project_evaluation.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < adapterDynamicImg.getCount() - 1) {
                    adapterDynamicImg.selectPosition(i);
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        setPicToView(null);
    }

    private Drawable loadImageFromResId(int resID) {
        Drawable image = getResources().getDrawable(resID);
        return image;
    }

    private Drawable loadImageFromFile(File file) {
        Drawable drawable = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            drawable = Drawable.createFromStream(inputStream, null);
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        return drawable;
    }

    private Drawable loadImageFromNetwork(Uri urladdr) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(getActivity().getContentResolver().openInputStream(urladdr), null);
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        return drawable;
    }


    private void setPicToView(Drawable drawable) {
        List<Drawable> listUriImgs = adapterDynamicImg.getListUriImgs();
        if (drawable == null) {
            drawable = loadImageFromResId(R.drawable.btn_add_pic);
            listUriImgs.add(drawable);
        } else {
            listUriImgs.add(drawable);
            if (listUriImgs != null && listUriImgs.size() == 9) {// 最多发布文件的总数九个

            } else {
                drawable = loadImageFromResId(R.drawable.btn_add_pic);
                listUriImgs.add(drawable);
            }
        }
        adapterDynamicImg.setData(listUriImgs);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CAMERA: {// 拍照UI返回
                // 返回的Intent为null
                setPicToView(loadImageFromFile(file));
            }
            break;
            case PHOTO_PICK: {// 从相册获取图片UI返回
                if (null != data) {
                    setPicToView(loadImageFromNetwork(data.getData()));
                }
            }
            break;
        }
    }

    @Override
    public void setUri(File file) {
        this.file = file;
    }
}
