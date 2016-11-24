package yu.jishang.com.imageadd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调用相机/相册UI
 *
 * @author wangliang Jul 16, 2016
 */
public class PopupWindowsImg extends PopupWindow implements OnClickListener {
    private int PHOTO_CAMERA;

    private int PHOTO_PICK;


    private Context mContext;
    private IModelPopupWindows model;
    View view;
    public PopupWindowsImg(Context mContext) {
        this.mContext = mContext;
        view = View.inflate(mContext, R.layout.item_popupwindow_img, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(460);// 这个地方去如果设置为match_parent会销毁 不了
        ColorDrawable cd = new ColorDrawable(-0000);
        setBackgroundDrawable(cd);
        setFocusable(true);
        setOutsideTouchable(true);

        setContentView(view);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // update();

        Button item_popupwindows_camera = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button item_popupwindows_Photo = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button item_popupwindows_cancel = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        /**
         * 拍照按钮设置点击事件监听
         */
        item_popupwindows_camera.setOnClickListener(this);
        /**
         * 从相册中选取按钮设置点击事件监听
         */
        item_popupwindows_Photo.setOnClickListener(this);
        item_popupwindows_cancel.setOnClickListener(this);

    }

    public void setPHOTO_CAMERA(int PHOTO_CAMERA) {
        this.PHOTO_CAMERA = PHOTO_CAMERA;
    }

    public void setPHOTO_PICK(int PHOTO_PICK) {
        this.PHOTO_PICK = PHOTO_PICK;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_popupwindows_Photo: {// 从相册中获取图片
                /**
                 * 调用系统相册
                 */
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((Activity) mContext).startActivityForResult(intent, PHOTO_PICK);
                dismiss();
            }
            break;
            case R.id.item_popupwindows_camera: {// 拍照
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openCameraIntent.putExtra("camerasensortype", 2); // 调用前置摄像头
                openCameraIntent.putExtra("autofocus", true); // 自动对焦
                openCameraIntent.putExtra("fullScreen", false); // 全屏
                openCameraIntent.putExtra("showActionIcons", false);
                // 指定调用相机拍照后照片的存储路径
                File tempFile = new File(Environment.getExternalStorageDirectory(),
                        getPhotoFileName());
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                model.setUri(tempFile);
                ((Activity) mContext).startActivityForResult(openCameraIntent, PHOTO_CAMERA);
                dismiss();
            }
            break;
            case R.id.item_popupwindows_cancel: {// 取消
                dismiss();
            }
            break;
            default: {

            }
            break;
        }
    }



    /**
     * 使用系统当前日期加以调整作为照片的名称
     *
     * @return
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    public void setModel(IModelPopupWindows model) {
        this.model = model;
    }

    public interface IModelPopupWindows {
        void setUri(File file);
    }
}
