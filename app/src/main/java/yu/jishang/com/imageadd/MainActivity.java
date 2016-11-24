package yu.jishang.com.imageadd;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static yu.jishang.com.imageadd.PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE;

public class MainActivity extends BaseFragmentActivity implements PermissionUtils.PermissionGrant{

    FragmentTransaction ft;
    FragmentManager manager;
    Fragment fragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        PermissionUtils.requestPermission(this, CODE_WRITE_EXTERNAL_STORAGE,this);
        manager = getSupportFragmentManager();
        ft = manager.beginTransaction();
        fragment = Fragment.instantiate(this, FragmentChoosePicture.class.getName(), null);
        ft.add(R.id.fragment_content, fragment, fragment.getClass().getName());
        ft.commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment*/
        Fragment f = manager.findFragmentByTag(fragment.getClass().getName());
        /*然后在碎片中调用重写的onActivityResult方法*/
        f.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }
}
