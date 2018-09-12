package com.zhitou.job.main.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Administrator on 2017/11/9.
 */

public class CommonUtils {

    private static String TAG = "CommonUtils";
    //线程池
    public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    //全局弹吐司
    public static void showToast(final Object object, final Context activity) {

//        MyToast.show(""+object);

//        cachedThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                        Toast.makeText(activity, "" + object, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
    }


    //MD5加密
    public static String md5(String string) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = string.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    //手机号码正则判断  返回boolean值
    public static boolean isMobileNO(String mobiles) {
//        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[6])|(17[013678])|(18[0,3,5-9])|(13[0-9]))\\d{8}$";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
        return true;
    }


    //设置界面透明度
    public static void setActivityAlpha(Activity activity, float f) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = f;
        activity.getWindow().setAttributes(params);

    }

    //图片地址 中文转码
    public static String utf8ToGB2312(String str) {
        String data = "";
        try {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c + "".getBytes().length > 1 && c != ':' && c != '/') {
                    data = data + java.net.URLEncoder.encode(c + "", "utf-8");
                } else {
                    data = data + c;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
        }
        return data;
    }


    //设置字符串的大小为12
    public static SpannableString setFrontSize(String str, int start, int end) {
        SpannableString spanString = new SpannableString(str);
        AbsoluteSizeSpan spanSize = new AbsoluteSizeSpan(16);
        spanString.setSpan(spanSize, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }


//    //通知
//    public static void showNotifiction(Context context) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        Intent intent = new Intent(context, LotDetailsActivity.class);//将要跳转的界面
//        builder.setAutoCancel(true);//点击后消失
//        builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知栏消息标题的头像
//        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
//        builder.setTicker("你好");
//        builder.setContentText("今天天气真好");//通知内容
//        builder.setContentTitle("天气");
//        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
//        PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(intentPend);
//        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }


    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean GPSIsOpen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    //判断locationProvider
    public static String getProvider(Activity activity, LocationManager manager) {
        String locationProvider;
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
            return locationProvider;
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
            return locationProvider;
        } else {
            CommonUtils.showToast("无法定位，请打开定位设置", activity);
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(i);
            return null;
        }
    }

    //获取经纬度
    public static List<Double> showLocation(Location location) {
        List<Double> list = new ArrayList<>();
        double Latitude = location.getLatitude();
        double Longitude = location.getLongitude();
        list.add(Latitude);
        list.add(Longitude);
        return list;
    }

    //检查输入框内容是否不为空
    public static boolean strNNCheck(String str) {
        boolean a;
        if (str == null || TextUtils.isEmpty(str)) {
            a = false;
        } else {
            a = true;
        }
        return a;
    }


    //传一个值的activity跳转
    public static void jumpActivityOnlyOne(Activity activity, Class clazz, String key, Object obj) {
        Intent intent = new Intent(activity, clazz);
        if (obj instanceof String) {
            intent.putExtra(key, (String) obj);
        } else if (obj instanceof Integer) {
            intent.putExtra(key, (Integer) obj);
        } else if (obj instanceof Float) {
            intent.putExtra(key, (Float) obj);
        } else if (obj instanceof Long) {
            intent.putExtra(key, (Long) obj);
        } else if (obj instanceof Double) {
            intent.putExtra(key, (Double) obj);
        } else {
            intent.putExtra(key, obj.toString());
        }

        activity.startActivity(intent);
    }

    //传一个值的activity跳转

    //传集合的activity跳转
    public static void jumpActivity(Activity activity, Class clazz, String key, List<Object> list) {
        Intent intent = new Intent(activity, clazz);
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, (Serializable) list);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * @param activity
     * @param initSeconds
     * @param tv
     * @return
     */
    public static TimerTask countDown(final Activity activity, final Timer timer, final int initSeconds, final TextView tv) {
        final int[] second = {initSeconds};
        //倒计时
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        second[0]--;
                        tv.setEnabled(false);
                        tv.setClickable(false);
                        tv.setText("重新获取(" + second[0] + ")");
                        if (second[0] < 0) {
                            timer.cancel();
                            tv.setEnabled(true);
                            tv.setClickable(true);
                            tv.setText("获取验证码");
                        }
                    }
                });
            }
        };
        return task;
    }

    //字符串转为list
    public static List<String> strToList(String str) {
        List<String> list = new ArrayList<>();
        if (str.contains(";")) {
            String[] split2 = str.split("\\;");
            for (int i = 0; i < split2.length; i++) {
                list.add(split2[i]);
            }
            return list;
        } else {
            list.add(str);
            return list;
        }
    }


    //将包含.0的string转化为Int
    public static int string2Int(Object obj) {

        if (obj != null) {
            String string = obj.toString();
            if (string != null && !TextUtils.isEmpty(string)) {
                if (string.contains(".0")) {
                    return Integer.parseInt(string.substring(0, string.indexOf(".")));
                } else {
                    return Integer.parseInt(string);
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }

    //将包含.0的string转化为Int
    public static double string2IntNew(Object obj) {
        if (obj != null) {
            String string = obj.toString();
            if (string != null && !TextUtils.isEmpty(string)) {
                String price_CNY = string;
                Double cny = Double.parseDouble(price_CNY);//转换成Double
                DecimalFormat df = new DecimalFormat("0.00");//格式化
                String CNY = df.format(cny);
                return Double.parseDouble(CNY);
            } else {
                return 0.00;
            }
        } else {
            return 0.00;
        }

    }

    //将包含.0的string转化为Int
    public static String string2Double(Object obj) {
        if (obj != null) {
            String string = obj.toString();
            if (string != null && !TextUtils.isEmpty(string)) {
                String price_CNY = string;
                Double cny = Double.parseDouble(price_CNY);//转换成Double
                DecimalFormat df = new DecimalFormat("0.00");
//                String CNY = df.format(cny);
               String CNY = df.format(cny);
                return CNY;
            } else {
                return "0.00";
            }
        } else {
            return "0.00";
        }

    }

    public static double string2DoubleNum(Object obj) {
        if (obj != null) {
            String string = obj.toString();
            if (string != null && !TextUtils.isEmpty(string)) {
                String price_CNY = string;
                Double cny = Double.parseDouble(price_CNY);//转换成Double
                DecimalFormat df = new DecimalFormat("#.00");
//                String CNY = df.format(cny);
               String CNY = df.format(cny);
                return Double.parseDouble(CNY);
            } else {
                return 0.00;
            }
        } else {
            return 0.00;
        }

    }

    //生成随机数
    public static String getRandomNum() {
        HashSet integerHashSet = new HashSet();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int randomInt = random.nextInt(2048);
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
            }
        }

        Iterator iterator = integerHashSet.iterator();
        if (iterator.hasNext()) {
            return iterator.next() + "";
        }
        return null;
    }


    /**
     * 生成随机字符串
     * @param length
     * @return
     */
    //获取指定位数的随机字符串(包含小写字母、大写字母、数字,0<length)
    public static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();

    }

    public static double getTwoDecimal(double num) {
        BigDecimal bg = new BigDecimal(num);
        double d3 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();



//        DecimalFormat dFormat=new DecimalFormat("#.00");
//        String yearString=dFormat.format(num);
//        Double temp= Double.valueOf(yearString);
        return d3;
    }

    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     *
     * @param v     需要格式化的数字
     * @return
     */
    public static String roundByScale(double v) {
        int scale = 2;
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if(scale == 0){
            return new DecimalFormat("0").format(v);
        }

        String formatStr = "0.";
        for(int i=0;i<scale;i++){
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }

}
