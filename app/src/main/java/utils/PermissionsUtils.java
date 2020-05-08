package utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hi on 2019.6.4.
 * Android 授权工具类
 * 有思维的农民工
 */

public class PermissionsUtils {
    //权限请求码
    private int mRequestCode = 1;
    //是否调用系统设置权限窗口
    public static boolean showSystemSetting = true;
    private static volatile PermissionsUtils permissionsUtils;

    private PermissionsUtils() {

    }

    public static PermissionsUtils getInstance() {
        if (permissionsUtils == null) {
            synchronized (PermissionsUtils.class) {
                if (permissionsUtils == null) {
                    permissionsUtils = new PermissionsUtils();
                }
            }
        }
        return permissionsUtils;
    }

    /**
     * 检查是否需要授权
     *
     * @param context
     * @param permissions
     * @return
     */
    public String[] checkPermissions(Activity context, String[] permissions) {
        if (Build.VERSION.SDK_INT < 23) {//6.0才用动态权限
            System.out.println("当前设备API小于23，请静态设置权限");
            return new String[0];
        }
        //创建一个mPermissionList,防止修改原始数据。
        String[] mPermissionList = permissions;
        int len = mPermissionList.length;
        List<String> noPermissionList = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < len; i++) {
            if (ActivityCompat.checkSelfPermission(context, mPermissionList[i]) != PackageManager.PERMISSION_GRANTED) {
                noPermissionList.add(mPermissionList[i]);
            }
        }

        String[] passPermisson=noPermissionList.toArray(new String[noPermissionList.size()]);
        noPermissionList=null;
        return passPermisson;
    }

    /**
     * 授权
     * @param context
     * @param permissions
     */
    public void grantPermissions(Activity context, String[] permissions) {
        this.grantPermissions(context,permissions,1);
    }


    public void grantPermissions(Activity context, String[] permissions, int mRequestCode) {
        String[] gPermissons = checkPermissions(context, permissions);
        if (gPermissons.length> 0){
            ActivityCompat.requestPermissions(context,gPermissons,mRequestCode);
        }

    }
}
