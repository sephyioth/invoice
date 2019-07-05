package com.genesis.liteutills.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.genesis.liteutills.AppDefine;
import com.genesis.liteutills.R;
import com.genesis.liteutills.UtillManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * 类说明： 通用工具类
 *
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-7-11 下午8:44:54
 */
public class GeneralUtil {
    // ** 常量 **/
    /**
     * 调试字段
     */
    private static final String TAG       = "LDUtil";
    /**
     * 分号
     */
    private static final String SEMICOLON = ";";
    public static final  char   CHAR_ONE  = '1';// 字符类型的1，用于比较用户sid的相应位置上的值

    // ** 变量 **/
    /**
     * 屏幕宽度
     */
    public static        int    windowWidth      = 0;
    /**
     * 屏幕高度
     */
    public static        int    windowHeight     = 0;
    /**
     * 首页的Cached目录
     */
    public static final  String FIRST_CACHE_PATH = "firstpage";
    /**
     * 保存随机出来的设备号
     */
    public static final  String SP_DEVICE_ID     = "_sp_device_id";
    private final static int    FZ_KB            = 1024;
    private final static int    FZ_MB            = 1024 * FZ_KB;
    private final static int    FZ_GB            = 1024 * FZ_MB;
    private final static int    FZ_PB            = 1024 * FZ_GB;

    private final static int TS_SECOND = 1000;
    private final static int TS_MINUTE = 60 * TS_SECOND;
    private final static int TS_HOUR   = 60 * TS_MINUTE;

    public static String formatSize (long fileLength) {
        StringBuilder sb = new StringBuilder();
        if (fileLength < FZ_KB) {
            sb.append(formatDouble(fileLength, 1)).append(" B");
        } else if (fileLength <= FZ_MB) {
            sb.append(formatDouble(fileLength, FZ_KB)).append(" KB");
        } else if (fileLength <= FZ_GB) {
            sb.append(formatDouble(fileLength, FZ_MB)).append(" MB");
        } else if (fileLength <= FZ_PB) {
            sb.append(formatDouble(fileLength, FZ_GB)).append(" GB");
        } else {
            sb.append(formatDouble(fileLength, FZ_PB)).append(" PB");
        }
        return sb.toString();
    }

    public static String formatDouble (long value, int divider) {
        double result = value * 1.0 / divider;
        return String.format(Locale.getDefault(), "%.2f", result);
    }

    public static String formatMilliSeconds (long milliSeconds) {
        StringBuilder sb = new StringBuilder();
        long left = milliSeconds;
        if (left / TS_HOUR > 0) {
            sb.append(left / TS_HOUR).append("h ");
            left -= (left / TS_HOUR) * TS_HOUR;
        }
        if (left / TS_MINUTE > 0) {
            sb.append(left / TS_MINUTE).append("m ");
            left -= (left / TS_MINUTE) * TS_MINUTE;
        }
        if (left / TS_SECOND > 0) {
            sb.append(left / TS_SECOND).append("s ");
            left -= (left / TS_SECOND) * TS_SECOND;
        }
        sb.append(left).append("ms ");
        return sb.toString();
    }

    /**
     * 外部不允许构造
     */
    private GeneralUtil () {
    }

    public static boolean isListViewReachTopEdge (final ListView listView) {
        boolean result = false;
        if (listView != null) {
            if (listView.getFirstVisiblePosition() == 0) {
                final View topChildView = listView.getChildAt(0);
                result = topChildView.getTop() == 0;
            }
        }
        return result;
    }

    /**
     * 分割字符串，原理：检测字符串中的分割字符串，然后取子串
     *
     * @param src   需要分割的字符串
     * @param regex 分割字符串
     * @return 分割后生成的字符串数组
     */
    public static String[] split (String src, String regex) {
        if (src == null || regex == null) {
            return null;
        }
        if (src.indexOf(regex) < 0) {
            return new String[]{src};
        }
        // 搜索的起始位置
        int endIndex = 0;
        // 搜索的结束位置
        int beginIndex = 0;
        // 分隔符长度
        int splitLength = regex.length();
        // 分割的每个数据临时存放在Vector中
        Vector<String> temp = new Vector<String>();
        // 分割字符串
        do {
            endIndex = src.indexOf(regex, beginIndex);
            if (endIndex >= 0) {
                // 取子串
                String string = src.substring(beginIndex, endIndex);
                temp.addElement(string);
            } else if (endIndex < 0) {
                // 结束位置如果为负数，说明已经到字符串尾，需要结束循环
                endIndex = src.length();
                break;
            }
            // 下个开始搜索的开始位置
            beginIndex = endIndex + splitLength;
        } while (endIndex >= 0);
        // 取结束的子串
        if (beginIndex < src.length()) {
            String string = src.substring(beginIndex, endIndex);
            temp.addElement(string);
        }
        // 去除结尾连续多个的空字符串
        int count = temp.size();
        while (count > 0 && "".equals(temp.elementAt(count - 1))) {
            count--;
        }
        // 将Vector对象转换成数组
        String[] resultArray = null;
        if (count > 0) {
            resultArray = new String[count];
            for (int i = 0; i < count; i++) {
                resultArray[i] = temp.elementAt(i);
            }
        }
        temp = null;
        // 返回生成的数组
        return resultArray;
    }

    public static byte[] shortToByte (Short srcValue) {
        int temp = srcValue;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.valueOf(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return b;
    }

    /**
     * 字符串替换算法：与J2SE中string.replaceAll(String regex, String replacement)的方法效果一样
     *
     * @param src         源字符串
     * @param regex       用来匹配此的字符串
     * @param replacement 用来替换每个匹配项的字符串
     * @return 替换后的结果字符串
     */
    public static String replaceAll (String src, String regex, String replacement) {
        if (src == null || regex == null || replacement == null) {
            return null;
        }
        if (src.indexOf(regex) < 0 || regex.length() == 0) {
            return src;
        }
        // 搜索的起始位置
        int endIndex = 0;
        // 搜索的结束位置
        int beginIndex = 0;
        // 分隔符长度
        int splitLength = regex.length();
        // 分割的每个数据临时存放在StringBuffer中
        StringBuffer temp = new StringBuffer();
        // 分割字符串
        char[] array = src.toCharArray();
        do {
            endIndex = src.indexOf(regex, beginIndex);
            if (endIndex >= 0) {
                // 取子串
                temp.append(array, beginIndex, (endIndex - beginIndex));
                temp.append(replacement);
            } else if (endIndex < 0) {
                // 结束位置如果为负数，说明已经到字符串尾，需要结束循环
                endIndex = array.length;
                break;
            }
            // 下个开始搜索的开始位置
            beginIndex = endIndex + splitLength;
        } while (endIndex >= 0);
        // 取结束的子串
        if (beginIndex < array.length) {
            temp.append(array, beginIndex, (endIndex - beginIndex));
        }
        // 返回结果字符串
        String string = temp.toString();
        temp = null;
        return string;
    }

    /**
     * 字符串替换算法：与J2SE中string.replaceFirst(String regex, String
     * replacement)的方法效果一样，与C中format函数类似
     *
     * @param src         源字符串
     * @param regex       用来匹配此的字符串
     * @param replacement 用来替换每个匹配项的字符串
     * @return 替换后的结果字符串
     */
    public static String replaceFirst (String src, String regex, String replacement) {
        if (src == null || regex == null) {
            return null;
        }
        int index = src.indexOf(regex);
        if (src.length() > 0 && index >= 0 && replacement != null) {
            StringBuffer temp = new StringBuffer();
            char[] array = src.toCharArray();
            // 拷贝前面部分字符
            int offset = 0;
            int length = index;
            if (length > 0) {
                temp.append(array, offset, length);
            }
            temp.append(replacement);
            // 拷贝后面部分字符
            offset = index + regex.length();
            length = array.length - offset;
            if (length > 0) {
                temp.append(array, offset, length);
            }
            // 返回结果字符串
            String string = temp.toString();
            temp = null;
            return string;
        } else {
            return src;
        }
    }

    /**
     * 格式化字符串(实现C中的format函数)<br>
     * 目前支持"%s"，"%c"，"%f"，"%d"的格式化
     *
     * @param format 格式字符串
     * @param args   格式字符串中由格式说明符引用的参数
     * @return 一个格式化字符串
     */
    public static String formatString (String format, String args) {
        String result = format;
        if (format != null && format.length() > 0 && args != null) {
            String regex = null;
            if (format.indexOf("%s") >= 0) {
                regex = "%s";
            } else if (format.indexOf("%c") >= 0) {
                regex = "%c";
            } else if (format.indexOf("%f") >= 0) {
                regex = "%f";
            } else if (format.indexOf("%d") >= 0) {
                regex = "%d";
            }
            if (regex != null) {
                result = replaceFirst(format, regex, args);
            }
        }
        return result;
    }

    /**
     * 格式化资讯请求Url
     *
     * @param url
     * @return
     */
    public static String formatNewsUrl (String url, String stockCode) {
        url = url.replace('^', '&');
        if (stockCode != null) {
            url = url.replace("%s", stockCode);
        }
        return url;
    }

    /**
     * Sign 数据
     *
     * @param value
     * @param sb
     * @return
     * @author Sephyioth
     */
    public static String signValue (String value, StringBuffer sb) {
        String ret = "";
        sb.setLength(0);
        float f = 0f;
        try {
            if (value != null) {
                if (value.startsWith("+") || value.startsWith("-")) {
                    sb.append(value.substring(1));
                } else {
                    sb.append(value);
                }
                if (value.endsWith("%")) {
                    value = value.split("%")[0];
                }
                f = Float.parseFloat(value);
                if (f > 0.0000001f) {
                    ret = "+";
                } else if (f < -0.0000001f) {
                    ret = "-";
                }
                sb.insert(0, ret);
            }
        } catch (Throwable e) {
            sb.setLength(0);
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 插入符号
     *
     * @param value
     * @param sb
     * @return
     * @author Sephyioth
     */
    public static String sign (String value, StringBuffer sb) {
        String ret = "";
        sb.setLength(0);
        float f = 0f;
        try {
            if (value != null) {
                if (value.startsWith("+") || value.startsWith("-")) {
                    sb.append(value.substring(1));
                } else {
                    sb.append(value);
                }
                if (value.endsWith("%")) {
                    value = value.split("%")[0];
                }
                f = Float.parseFloat(value);
                if (f > 0.0000001f) {
                    ret = "+";
                } else if (f < -0.0000001f) {
                    ret = "-";
                }
            }
        } catch (Throwable e) {
            sb.setLength(0);
            sb.append(value);
        }
        return ret;
    }

    /**
     * 判断是否是电话号码
     *
     * @param phonenumber
     * @return
     * @author Sephyioth
     */
    public static boolean checkMobilePhonenumber (String phonenumber) {
        if (phonenumber == null || phonenumber.length() == 0) {
            return false;
        }
        String patternString = "^1[3,4,5,8]\\d{9}$";
        Pattern p = Pattern.compile(patternString);
        Matcher m = p.matcher(phonenumber);
        boolean flag = m.matches();
        return flag;
    }

    /**
     * 判断是否是Email 地址
     *
     * @param email
     * @return
     * @author Sephyioth
     */
    public static boolean checkEmail (String email) {
        boolean flag = false;
        String patternString = "\\w+([-+.']+\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(patternString);
        Matcher m = null;
        m = p.matcher(email);
        flag = m.matches();
        return flag;
    }


    /**
     * 测量字体高度
     *
     * @param paint
     * @return 字体高度
     */
    public static final int measureFontHeight (Paint paint) {
        int ascent = paint.getFontMetricsInt().ascent;
        int descent = paint.getFontMetricsInt().descent;
        int leading = paint.getFontMetricsInt().leading;
        return leading + descent - ascent;
    }

    /**
     * 测量字体高度
     *
     * @param fontSize
     * @return 字体高度
     */
    public static final int measureFontHeight (float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        return measureFontHeight(paint);
    }

    /**
     * 测量字体宽度
     *
     * @param paint
     * @param text
     * @return 字体宽度
     */
    public static final int mesureFontWidth (Paint paint, String text) {
        return (int) paint.measureText(text);
    }

    /**
     * 测量字体宽度
     *
     * @param fontSize
     * @param text
     * @return 字体宽度
     */
    public static final int measureFontWidth (float fontSize, String text) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        return mesureFontWidth(paint, text);
    }

    /**
     * // GENERAL_PUNCTUATION 判断中文的“号 // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号 //
     * HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
     *
     * @param c
     * @return
     * @author Sephyioth
     */
    public static boolean isChinese (char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 方法描述：拼接字符串，中间逗号隔开
     * <p/>
     * 创建时间：2016/5/4
     * 修改备注：
     *
     * @version
     */

    public static String listToString (List<String> buffer) {
        if (buffer != null && buffer.size() > 0) {
            StringBuffer techStr = new StringBuffer();
            for (int i = 0; i < buffer.size(); i++) {
                if (i < buffer.size() - 1) {
                    techStr.append(buffer.get(i)).append(",");
                    continue;
                }
                techStr.append(buffer.get(i));
            }
            return techStr.toString();
        }
        return null;
    }

    /**
     * 是否含有中文字符
     *
     * @param str
     * @return
     */
    public static boolean isContainsChinese (String str) {
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含中文字符
     *
     * @param string 待测字符串
     * @return 返回true-有，false-无
     */
    public static boolean containsChineseCode (String string) {
        if (string != null) {
            Pattern chinesePattern = Pattern.compile("[\u4e00-\u9fa5]+");
            Matcher matcher = chinesePattern.matcher(string);
            return matcher.find();
        }
        return false;
    }

    /**
     * 取得随机数
     *
     * @param start
     * @param end
     * @return 获取从start到end的随机数
     */
    public static int getRandom (int start, int end) {
        if (start >= end) {
            return start;
        }
        Random random = new Random();
        return random.nextInt(end) + start;
    }

    /**
     * sd卡是否加载
     *
     * @return
     */
    public static boolean isCanUseSdcard () {
        try {
            long time = System.currentTimeMillis();
            Log.e("SPEED", "startGetSdCardState time = " + time);
            boolean isCanUseSdcard =
                    Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
            ;
            long time2 = System.currentTimeMillis();
            Log.e("SPEED", "EndGetSdCardState time = " + time2 + "  耗时 =" + (time2 - time));

            return isCanUseSdcard;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 方法描述：判断SD卡是否存在
     * 参数：
     * 返回：
     * 创建时间：2016/5/4
     *
     * @version
     */

    public static boolean isSdcardExisting () {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取得 外部存储的路径/mnt/sdcard/Android/data/package/cache
     *
     * @return maybe null
     */
    public static File getExternalCacheDir () {
        File cache = null;
        try {
            cache = UtillManager.getContext().getCacheDir();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }

    /**
     * 是否为全数字
     *
     * @param string 待测字符串
     * @return 返回true-是，false-否
     */
    public static boolean isDigital (String string) {
        if (string != null) {
            Pattern digitalPattern = Pattern.compile("[0-9]+");
            Matcher matcher = digitalPattern.matcher(string);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 是否全部为字母
     *
     * @param string 待测字符串
     * @return 返回true-是，false-否
     * @author 贺晓
     */
    public static boolean isAllLetter (String string) {
        if (string != null) {
            Pattern letterOrDigitPattern = Pattern.compile("[a-zA-Z]+");
            Matcher matcher = letterOrDigitPattern.matcher(string);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 是否为数字或字母
     *
     * @param string 待测字符串
     * @return 返回true-是，false-否
     */
    public static boolean isLetterOrDigit (String string) {
        if (string != null) {
            Pattern letterOrDigitPattern = Pattern.compile("[a-zA-Z0-9]+");
            Matcher matcher = letterOrDigitPattern.matcher(string);
            return matcher.matches();
        }
        return false;
    }


    public static String getLocalIpAddress (Context context) {
        if (context == null) {
            return null;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();

        //返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }


    public static String getIpAddress () {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLocal3GIpAddress () {
        String ipAddress = null;
        try {
            List<NetworkInterface> interfaces =
                    Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface iface : interfaces) {
                if (iface.getDisplayName().equals("eth0")) {
                    List<InetAddress> addresses = Collections.list(iface.getInetAddresses());
                    for (InetAddress address : addresses) {
                        if (address instanceof Inet4Address) {
                            ipAddress = address.getHostAddress();
                        }
                    }
                } else if (iface.getDisplayName().equals("wlan0")) {
                    List<InetAddress> addresses = Collections.list(iface.getInetAddresses());
                    for (InetAddress address : addresses) {
                        if (address instanceof Inet4Address) {
                            ipAddress = address.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    /**
     * 判断一个字符串是否为一个数值，正负数、小数都可以验证
     *
     * @param string
     * @return
     */
    public static boolean isNumerical (String string) {
        if (string != null) {
            Pattern digitalPattern = Pattern.compile("^[-+]?[0-9]+(\\.[0-9]+)?$");
            Matcher matcher = digitalPattern.matcher(string);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 是否为整数类型字符串
     *
     * @param string
     * @return
     */
    public static boolean isInteger (String string) {
        if (string != null) {
            Pattern digitalPattern = Pattern.compile("^[-+]?[0-9]");
            Matcher matcher = digitalPattern.matcher(string);
            return matcher.matches();
        }
        return false;
    }


    /**
     * 将图片保存到缓存中
     *
     * @param bitmap
     * @param fileName
     */
    public static void saveImageToCache (Bitmap bitmap, String fileName, Context context) {
        if (bitmap != null && fileName != null && fileName.length() > 0 && context != null) {
            File dir = context.getCacheDir();
            File file = new File(dir, fileName);
            try {
                OutputStream os = new FileOutputStream(file);
                if (os != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    try {
                        os.close();
                    } catch (Exception e) {
                    }
                }
            } catch (FileNotFoundException e) {

            }
            file = new File(dir + File.separator + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存图片到本地
     * <p/>
     * 参数：bitmap,string
     * 返回：
     *
     * @version
     */

    public static String SaveBitmap (String bitName, Bitmap mBitmap) {
        File file = new File("/sdcard/huituapp/");
        if (!file.exists()) file.mkdir();
        File f = new File("/sdcard/huituapp/", bitName + ".png");
        try {
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }

    /**
     * 保存图片到指定目录
     *
     * @param bitmap
     * @param file
     * @param context
     */
    public static void saveImageToCache (Bitmap bitmap, File file, Context context) {
        if (bitmap != null && file != null && context != null) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                OutputStream os = new FileOutputStream(file);
                if (os != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    try {
                        os.close();
                    } catch (Exception e) {
                    }
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, "saveImageToCache():FileNotFoundException e=" + e.getMessage());
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "saveImageToCache():IOException e=" + e.getMessage());
                }
            }
        }
    }

    /**
     * 加载缓存中的图片
     *
     * @param fileName
     * @return
     */
    public static Bitmap loadImageFromCache (String fileName, Context context) {
        Bitmap bitmap = null;
        if (context != null) {
            File file = new File(context.getCacheDir(), fileName);
            if (!file.exists()) {
                return null;
            }
            try {
                InputStream is = new FileInputStream(file);
                if (is != null) {
                    bitmap = BitmapFactory.decodeStream(is);
                    try {
                        is.close();
                    } catch (IOException e) {
                        Log.v("Utils",
                                "loadHeadPotraitFromCache " + fileName + ", IOException " + e.getMessage());
                    }
                }
            } catch (FileNotFoundException e) {
                Log.v("Utils", "loadHeadPotraitFromCache " + fileName + ", FileNotFoundException "
                        + e.getMessage());
            }
        }
        return bitmap;
    }

    /**
     * 加载缓存中的图片
     *
     * @param fileName
     * @return
     */
    public static Bitmap loadImageFromAssets (String fileName, Context context) {
        if (context == null || fileName == null) {
            return null;
        }
        Bitmap bitmap = null;

        try {
            InputStream is = context.getAssets().open(fileName);
            if (is != null) {
                bitmap = BitmapFactory.decodeStream(is);
                try {
                    is.close();
                } catch (IOException e) {
                    Log.v("Utils",
                            "loadHeadPotraitFromCache " + fileName + ", IOException " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            Log.v("Utils",
                    "loadHeadPotraitFromCache " + fileName + ", FileNotFoundException " + e.getMessage());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 判断本地文件是否存在
     *
     * @param context
     * @param fileName
     * @return
     * @author Sephyioth
     */
    public static boolean checkLocalFileExits (Context context, String fileName) {
        // cache
        File file = new File(File.separator + FIRST_CACHE_PATH, fileName);
        if (file.exists()) {
            return true;
        }
        // assets
        try {
            InputStream is = context.getAssets().open(fileName);
            if (is != null && is.available() > 0) {
                is.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

//    /**
//     * 取得缓存图片
//     *
//     * @param url
//     * @return
//     */
//    public static Bitmap getUrlBitmap (final String url) {
//        if (url == null) {
//            return null;
//        }
//        String path = CommUtil.stringToMD5(url);
//        Bitmap bitmap = loadImageFromCache(path, UtillManager.getContext());
//        if (bitmap != null) {
//            return bitmap;
//        } else {
//            return downLoadImage(url);
//        }
//    }

//
//    /**
//     * Http下载图片
//     *
//     * @param url
//     * @return
//     */
//    public static Bitmap downLoadImage (final String url) {
//        int random = (int) (Math.random() * 10);
//        String path = CommUtil.stringToMD5(url);
//        InputStream is = null;
//        HttpURLConnection urlConnection = null;
//        Bitmap bitmap = null;
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(url).append("?").append(String.valueOf(random));
//        try {
//            HttpTools.ResponseStreamMessage msg =
//                    HttpTools.getStreamMessage(stringBuffer.toString().trim(), null, true);
//            if (msg != null) {
//                is = msg.contentStream;
//                urlConnection = msg.urlConnection;
//                if (is != null) {
//                    bitmap = BitmapFactory.decodeStream(is);
//                    saveImageToCache(bitmap, path, UtillManager.getContext());
//                } else {
//                    Log.w(TAG,
//                            "Utils_downLoadImage: msg.contentStream=null, responseCode=" + msg.responseCode);
//                }
//            } else {
//                Log.e(TAG, "Utils_downLoadImage: msg=null");
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "Utils_downLoadImage: Exception=" + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                is = null;
//            }
//
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//                urlConnection = null;
//            }
//        }
//        return bitmap;
//    }

    /**
     * 高亮显示
     *
     * @param value     源字符串
     * @param highLight 高亮显示的字符串
     * @param bgColor   高亮显示的背景色
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static CharSequence getHighLightValue (String value, String highLight, int bgColor) {
        if (highLight != null && value != null) {
            int start = value.toUpperCase(Locale.getDefault()).indexOf(highLight.toUpperCase());
            int end = start + highLight.length();
            if (start >= 0 && end > start && value.length() >= end) {
                SpannableStringBuilder styled = new SpannableStringBuilder(value);
                styled.setSpan(new BackgroundColorSpan(bgColor), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return styled;
            }
        }
        return value;
    }

    /**
     * 高亮显示
     *
     * @param value   源字符串
     * @param start   开始索引
     * @param end     结束索引
     * @param bgColor 高亮显示的背景色
     * @return
     */
    public static CharSequence getHighLightValue (String value, int start, int end, int bgColor) {
        if (value != null) {
            if (start >= 0 && end > start && value.length() >= end) {
                SpannableStringBuilder styled = new SpannableStringBuilder(value);
                styled.setSpan(new BackgroundColorSpan(bgColor), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return styled;
            }
        }
        return value;
    }

    /**
     * 格式化URL
     *
     * @param url   原始url
     * @param param 增加的参数
     * @param value 值
     */
    @SuppressLint("DefaultLocale")
    public static void formatURL (StringBuilder url, String param, String value) {
        if (url == null || url.length() <= 0) {
            return;
        }
        int index = url.indexOf(param);
        if (index < 0) {
            if (url.indexOf("?") > 0) {
                url.append("&").append(param).append(value);
            } else {
                url.append("?").append(param).append(value);
            }
        } else {
            int valueIndex = index + param.length();
            int checkIndex = url.indexOf("&", valueIndex);
            if (checkIndex > 0) {
                url.replace(valueIndex, checkIndex, value);
            } else {
                url.replace(valueIndex, url.length(), value);
            }
        }
    }

    public static String formatURL (String url, String param, String value) {
        if (url == null || url.length() <= 0) {
            return url;
        }
        int index = url.indexOf(param);
        if (index < 0) {
            if (url.indexOf("?") > 0) {
                url = url + "&" + param + value;
            } else {
                url = url + "?" + param + value;
            }
        } else {
            int valueIndex = index + param.length();
            int checkIndex = url.indexOf("&", valueIndex);
            if (checkIndex > 0) {
                url = url.substring(0, valueIndex) + value + url.substring(checkIndex);
            } else {
                url = url.substring(0, valueIndex) + value;
            }
        }
        return url;
    }

    /**
     * 过滤所有的数字
     *
     * @param str
     * @return
     */
    public static String filterNumber (String str) {
        String regEx = "[0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    /**
     * 设置在点击EditView时，不显示系统默认键盘
     *
     * @param view
     * @author Sephyioth
     */
    public static void disableSoftKeyBoradByView (EditText view) {
        // 使用反射的方式来使系统的软键盘对单个EditText输入框不其作用
        if (view instanceof EditText) { // setSoftInputShownOnFocus 15
            try { // setShowSoftInputOnFocus 16 17 18
                Method method = null;
                if (android.os.Build.VERSION.SDK_INT == 15) {
                    method = view.getClass().getMethod("setSoftInputShownOnFocus", boolean.class);
                    method.setAccessible(true);
                    method.invoke(view, false);
                } else if (android.os.Build.VERSION.SDK_INT > 15) {
                    method = view.getClass().getMethod("setShowSoftInputOnFocus", boolean.class);
                    method.setAccessible(true);
                    method.invoke(view, false);
                }
                // 小于4.0，直接禁止系统键盘弹出，4.0不能禁止，采用隐藏方式，不然输入框中没有光标(modify jiangbin)
                if (android.os.Build.VERSION.SDK_INT < 14) {
                    view.setRawInputType(InputType.TYPE_NULL);
                }
            } catch (Exception e) {
                Log.e(TAG, "" + e.getMessage());
            }
        }
    }

    /**
     * mac地址
     *
     * @param context
     * @return
     * @author Sephyioth
     */
    public static String getLocalMacAddress (Context context) {
        String macAddress = "";
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = null;
                try {
                    // 修改线上top异常，很多联想的机型会在此处报一个NULLPoint异常，详见SJCGAP-5702
                    wifiInfo = wifiManager.getConnectionInfo();
                } catch (Exception e) {
                    // do nothing
                }

                if (wifiInfo != null) {
                    macAddress = wifiInfo.getMacAddress();
                    if (macAddress == null) {
                        macAddress = "";
                    }
                }
            }
        }
        return macAddress;
    }

    /**
     * 如果输入的日期格式不是00000000 格式抛出异 比较传进来的时间和当前时间到相差天数
     *
     * @param preDate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int getBetweenDays (long preDate) {
        Calendar preCalendar = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();
        long t2 =
                nowCalendar.get(Calendar.YEAR) * 10000 + (nowCalendar.get(Calendar.MONTH) + 1) * 100 + nowCalendar.get(Calendar.DATE);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        int betweenDays = 0;
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(String.valueOf(preDate));
            d2 = format.parse(String.valueOf(t2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        preCalendar.setTime(d1);
        nowCalendar.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (preCalendar.after(nowCalendar)) {
            return -1;
        }
        int betweenYears = nowCalendar.get(Calendar.YEAR) - preCalendar.get(Calendar.YEAR);
        betweenDays = nowCalendar.get(Calendar.DAY_OF_YEAR) - preCalendar.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            preCalendar.set(Calendar.YEAR, (preCalendar.get(Calendar.YEAR) + 1));
            betweenDays += preCalendar.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    /**
     * 功能：把 sourceDir 目录下的所有文件进行 zip 格式的压缩，保存为指定 zip 文件 create date:2009- 6- 9
     * author:Administrator
     *
     * @param sourceDir
     * @param zipFile
     */
    @SuppressLint("SimpleDateFormat")
    public static void zip (String sourceDir, String zipFile) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(zipFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            ZipOutputStream zos = new ZipOutputStream(bos);
            File file = new File(sourceDir);
            String basePath = null;
            if (file.isDirectory()) {
                basePath = file.getPath();
            } else {
                basePath = file.getParent();
            }
            zipFile(file, basePath, zos);
            zos.closeEntry();
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 存储ZIP文件
     *
     * @param source
     * @param basePath
     * @param zos
     * @author Sephyioth
     */
    private static void zipFile (File source, String basePath, ZipOutputStream zos) {
        File[] files = new File[0];
        if (source.isDirectory()) {
            files = source.listFiles();
        } else {
            files = new File[1];
            files[0] = source;
        }
        String pathName;
        byte[] buf = new byte[1024];
        int length = 0;
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    pathName = file.getPath().substring(basePath.length() + 1) + "/";
                    zos.putNextEntry(new ZipEntry(pathName));
                    zipFile(file, basePath, zos);
                } else {
                    pathName = file.getPath().substring(basePath.length() + 1);
                    InputStream is = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    zos.putNextEntry(new ZipEntry(pathName));
                    while ((length = bis.read(buf)) > 0) {
                        zos.write(buf, 0, length);
                    }
                    is.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据SID位来判断,是否有权限
     *
     * @param sid
     * @return
     */
    public static boolean isUserHasPermission (String sid, int sidIndex) {
        boolean isCFXF = false;
        if (sid != null && sid.length() > sidIndex) {
            char c = sid.charAt(sidIndex);
            if (c == CHAR_ONE) {
                isCFXF = true;
            }
        }
        return isCFXF;
    }

    /**
     * 根据图片得到的options参数以及要求显示的图片大小，计算解码图片过程中压缩比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize (BitmapFactory.Options options, int reqWidth,
                                             int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee a final image
            // with both dimensions larger than or equal to the requested height
            // and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    /**
     * 对String的substring方法进行封装,原始方法较容易因为输入的问题而抛出异常导致程序崩溃
     *
     * @param value 待截取的字符串
     * @param start 开始位置 从0开始
     * @param end   结束位置 最大和待截取长度一样长
     * @return
     */
    public static String hxSubstring (String value, int start, int end) {

        if (value == null || value.trim().length() == 0) {
            return null;
        }
        // 待截取字符串长度
        int count = value.length();
        if (start >= 0 && start <= end && end <= count) {
            return value.substring(start, end);
        } else {
            return null;
        }
    }

    /**
     * 判断某个应用是否有安装
     *
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled (Context context, String packageName) {
        if (packageName == null || "".equals(packageName) || context == null) {
            return false;
        }

        PackageManager manager = context.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        int size = pkgList.size();
        for (int i = 0; i < size; i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断当前show的Activity的context是否是当前展示的
     */
    public static boolean isCurrentTokenProper (Context context, String activityPath) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        RunningTaskInfo rt = manager.getRunningTasks(1).get(0);
        if (!activityPath.equals(rt.topActivity.getClassName())) return false;
        return true;
    }


    /**
     * 判断一个要跳转的Activity是否公开的android:exported="true"
     *
     * @return
     */
    public static boolean isActivityExported (Intent intent, Context context) {
        if (intent == null || context == null) {
            return false;
        }

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        Log.i(TAG, "isActivityExported():list size=" + list.size());
        for (ResolveInfo resolveInfo : list) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo.exported) {
                Log.i(TAG, "isActivityExported():exported activity, " + activityInfo);
                return true;
            }
        }

        return false;
    }

    /**
     * 获取合适的字体大小
     *
     * @param width    有效的宽度
     * @param drawText 待绘制的文字
     * @param paint    画笔
     * @return
     */
    public static float getFitTextSize (int width, String drawText, float defaultTextSize,
                                        Paint paint) {
        if (drawText == null || defaultTextSize == 0 || paint == null) {
            return 0;
        }
        int textWidth = 0;
        float size = (int) defaultTextSize;
        for (; size > 0; size -= 0.5) {
            paint.setTextSize(size);
            textWidth = (int) paint.measureText(drawText);
            if (textWidth > width) {
                continue;
            }
            break;
        }
        return size;
    }

    /**
     * 取得网络文件的大小
     *
     * @param downloadUrl
     * @return
     * @author Sephyioth
     */
    public static long getFileSize (String downloadUrl) {
        int nFileLength = 0;

        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(10 * 1000);
            httpConnection.connect();
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                nFileLength = httpConnection.getContentLength();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                // 当图片的url地址为异常时，httpConnection对象没有被创建
                httpConnection.disconnect();
            }
        }
        return nFileLength;
    }


    /**
     * 判断一个对象是否为空
     *
     * @param obj
     * @return true则为空 false不为空
     */
    public static boolean isNullObject (Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }


    /**
     * 通过URL解析FileName
     *
     * @param URL
     * @return
     */
    public static String analysisURL (String URL) {
        String fileName = null;
        String[] temp = URL.split("/");
        for (int index = temp.length - 1; index >= 0; index--) {
            if (!"".equals(temp[index])) {
                fileName = temp[index];
                break;
            }
        }
        return fileName;
    }

    /**
     * 从指定的assets路径上读取字符串
     *
     * @param assetManager
     * @param fileName
     * @return
     * @author Sephyioth
     */
    public static String readStringFromAssets (AssetManager assetManager, String fileName) {
        if (assetManager == null || fileName == null || "".equals(fileName)) {
            return null;
        }
        InputStream in = null;
        int length = 0;
        try {
            in = assetManager.open(fileName);
            ;
            length = in.available();
            if (length > 0) {
                byte[] buffer = new byte[length];
                length = in.read(buffer);
                return new String(buffer, 0, length, "UTF-8");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 从指定的路径上读取json类型的缓存
     *
     * @param file 指定的文件
     * @return 返回json字符串
     */
    public static String readStringCache (File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        InputStream in = null;
        int length = 0;
        try {
            in = new FileInputStream(file);
            length = in.available();
            if (length > 0) {
                byte[] buffer = new byte[length];
                length = in.read(buffer);
                return new String(buffer, 0, length, "UTF-8");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 写json类型的缓存
     *
     * @param file
     * @param jsonString
     */
    public static boolean writeStringCache (File file, String jsonString) {
        boolean isSuccess = false;
        if (file == null || jsonString == null || jsonString.trim().length() == 0) {
            return isSuccess;
        }
        BufferedWriter buffWriter = null;
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            buffWriter = new BufferedWriter(new FileWriter(file, false)); // false表示不追加内容即每次会清空文件后再写
            buffWriter.write(jsonString);
            buffWriter.flush();
            isSuccess = true;
        } catch (FileNotFoundException e) {
            isSuccess = false;
            e.printStackTrace();
        } catch (IOException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            if (buffWriter != null) {
                try {
                    buffWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    /**
     * 判断今天是否周末
     *
     * @return true是周末，false 不是周末
     */
    public static boolean checkCdateIsInWeekend () {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0 || week == 6) {
            return true;
        }
        return false;
    }

    /**
     * 检查wifi是否链接着
     *
     * @param context
     * @return false wifi 没有链接者，true wifi开着
     */
    public static boolean isWifiConnect (Context context) {
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                @SuppressLint("MissingPermission") NetworkInfo networkInfo =
                        cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (networkInfo != null) {
                    return networkInfo.isConnected();
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有网路链接
     */
    public static int currentNetworkType (Context context) {
        int networkType = AppDefine.NONETWORK;
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        networkType = AppDefine.WIFI;
                    } else {
                        networkType = AppDefine.FWDATA;
                    }
                }
            }
        }
        return networkType;
    }


    /**
     * 调用外部浏览器打开程序
     */
    public static void openWithExternalWebView (String url, Context context) {
        if (context != null) {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uRL = Uri.parse(url);
                intent.setData(uRL);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 动态设置listview的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren (ListView listView) {
        // get the list view adapter, so this function must be invoked after set
        // the adapter.
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        // get the ListView count
        int count = listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // measure the child view
            listItem.measure(0, 0);
            // calculate the total height of items
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // get divider height for all items and add the total height
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 拨打电话的权限判断提示
     *
     * @param context
     * @param phoneNum
     */
    public static void startPhoneCallActivity (Context context, String phoneNum) {
        try {
            Intent mCallIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            context.startActivity(mCallIntent);
        } catch (ActivityNotFoundException ex) {
            String tips = context.getResources().getString(R.string.dial_tip);
            Toast.makeText(context, tips, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断支持最小版本号
     *
     * @param packageName
     * @param context
     * @return
     */
    public static boolean isMinAppSupported (String packageName, Context context,
                                             String minVersion) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info;
        try {
            int minCode = Integer.valueOf(minVersion);
            info = pm.getPackageInfo(packageName, 0);
            int code = info.versionCode;
            if (code < 0) {
                return false;
            }
            if (code >= minCode) {
                return true;
            }
        } catch (NameNotFoundException e1) {
            return false;
        } catch (Exception e2) {
            return false;
        }
        return false;
    }

    /**
     * 发送手机短信无返回结果
     *
     * @param context
     * @param smsTo
     * @param msg
     */
    public static void sendMessage (Context context, String smsTo, String msg) {
        if (context != null && smsTo != null && !"".equals(smsTo.trim())) {
            if (msg == null) msg = "";
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + smsTo));
            smsIntent.putExtra("sms_body", msg);
            // 判断手机是否有发短信组件
            if (smsIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(smsIntent);
            } else {
                Toast.makeText(context,
                        context.getResources().getString(R.string.mobile_no_sendmsg_component),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 发送手机短信有结果返回
     *
     * @param context
     * @param smsTo
     * @param msg
     */
    public static void sendMessageForResult (Context context, String smsTo, String msg,
                                             int requestCode) {
        if (context != null && smsTo != null && !"".equals(smsTo.trim())) {
            if (msg == null) msg = "";
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + smsTo));
            smsIntent.putExtra("sms_body", msg);
            // 判断手机是否有发短信组件
            if (smsIntent.resolveActivity(context.getPackageManager()) != null) {
                ((Activity) context).startActivityForResult(smsIntent, requestCode);
            } else {
                Toast.makeText(context,
                        context.getResources().getString(R.string.mobile_no_sendmsg_component),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 取得时间
     *
     * @param context
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getVerifyCode (Context context) {
        if (context == null) {
            return null;
        }
        try {
            byte[] md5Byte = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES).signatures[0].toByteArray();
            String str1 = md5Hex(md5Byte);
            String str2 =
                    new SimpleDateFormat("yyyy年MM月dd日HH").format(new Date(System.currentTimeMillis()));
            return md5Hex((str1 + str2).getBytes());
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String md5Hex (byte[] bytes) {
        return bytes.toString();
    }

    /**
     * 以一个url的MD5值作为图片的名字
     *
     * @param bitmapDownlaodUrl
     * @return
     */
    public static String createBitmapName (String bitmapDownlaodUrl) {
        return createFileName(bitmapDownlaodUrl, ".png");
    }

    /**
     * 从URL上取数据并且经过MD5解密
     *
     * @param url
     * @param suffix
     * @return
     * @author Sephyioth
     */
    public static String createFileName (String url, String suffix) {
        if (suffix != null) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(url.getBytes());
                byte[] m = md5.digest();
                StringBuffer buffer = new StringBuffer();
                for (byte b : m) {
                    buffer.append(b);
                }
                return buffer.toString() + suffix;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    /**
     * 获取程序versionCode值大小
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode (Context context) {
        int versionCode = 0;
        if (context == null) {
            return versionCode;
        }
        try {
            PackageInfo packageInfo =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static void saveLastUserName (String[] lastNames, Context context) {
        if (lastNames != null && lastNames.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (String lastName : lastNames) {
                sb.append(lastName).append(SEMICOLON);
            }

            SPConfig.saveStringSPValue(context, SPConfig.SP_LAST_USERNAME,
                    SPConfig.SP_KEY_USER_LIST, sb.toString());
        }
    }

    /**
     * 获取圆形图片
     *
     * @param sourceBitmap
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap createCircleBitmap (Bitmap sourceBitmap, int targetWidth,
                                             int targetHeight) {
        if (sourceBitmap != null && targetWidth > 0 && targetHeight > 0) {
            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(targetBitmap);
            Path path = new Path();
            path.addCircle((targetWidth - 1) / 2, (targetHeight - 1) / 2, Math.min(targetWidth,
                    targetHeight) / 2, Path.Direction.CCW);
            canvas.clipPath(path);
            canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
                    sourceBitmap.getHeight()), new Rect(0, 0, targetWidth, targetHeight), null);
            return targetBitmap;
        }
        return sourceBitmap;
    }

    /**
     * 取得设备ID
     *
     * @param context
     * @return
     */
    public static String getEquipId (Context context) {
        String imsi = getIMSI();
        String mac = getLocalMacAddress(context);
        String defaultVaule = null;
        if (imsi != null && !"".equals(imsi) && imsi.length() > 2 && imsi.indexOf("46") == 0) {
            defaultVaule = imsi;
        } else if (mac != null && !"".equals(mac) && mac.length() > 2) {
            defaultVaule = mac;
        } else {
            // 先从本地拿，如果没有就随机一个然后存下来
            String equipId = SPConfig.getStringSPValue(context, SP_DEVICE_ID,
                    SPConfig.SP_DEVICE_ID);
            if (equipId == null || "".equals(equipId)) {
                defaultVaule = UUID.randomUUID().toString();
                SPConfig.saveStringSPValue(context, SP_DEVICE_ID, SPConfig.SP_DEVICE_ID,
                        defaultVaule);
            } else {
                defaultVaule = equipId;
            }
        }
        return defaultVaule;
    }

    public static String getIMSI () {
        if (UtillManager.getContext() == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager =
                    (TelephonyManager) UtillManager.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String IMSI = telephonyManager.getSubscriberId();
            return IMSI;

        } catch (SecurityException e) {
            return null;
        }
    }

    /**
     * 取得最上层的页面名称
     *
     * @param context
     * @return
     * @author Sephyioth
     */
    public static ComponentName getTopActivity (Context context) {
        if (context == null) return null;
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        RunningTaskInfo rt = manager.getRunningTasks(1).get(0);
        return rt.topActivity;
    }

    public static String formatSpeed (double deltaSize, double deltaMillis) {
        double speed = deltaSize * 1000 / deltaMillis / FZ_KB;
        String result = String.format(Locale.getDefault(), "%.2f KB/s", speed);
        if ((int) speed > FZ_KB) {
            result = String.format(Locale.getDefault(), "%.2f MB/s", speed / FZ_KB);
        }
        return result;
    }


    /**
     * Get the Intent for selecting content to be used in an Intent Chooser.
     *
     * @return The intent for opening a file with Intent.createChooser()
     * @author paulburke
     */
    public static Intent createGetContentIntent () {
        // Implicitly allow the user to select a particular kind of data
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // The MIME data type filter
        intent.setType("*/*");
        // Only return URIs that can be opened with ContentResolver
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }


    public static boolean getCheckPermission (String string, int requestCode) {
        if (UtillManager.getContext() == null || string == null) {
            return false;
        }
        if (ContextCompat.checkSelfPermission(UtillManager.getContext(), string) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static void sendPic2Qiniu (String path) {

    }

    public static int getScreenHeight () {
        int screenHeight = 0;
        if (UtillManager.getContext() != null) {
            WindowManager wm =
                    (WindowManager) UtillManager.getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }
        return screenHeight;
    }


    /**
     * 反馈到App Store
     *
     * @author Sephyioth
     */
    public static void feedback2market () {
        if (UtillManager.getContext() == null) {
            return;
        }
        Uri uri = Uri.parse("market://details?id=" + UtillManager.getContext().getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (isIntentAvailable(intent)) {
            UtillManager.getContext().startActivity(intent);
        } else {

            Toast.makeText(UtillManager.getContext(), "no market", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 判断Intent是否有效
     *
     * @param intent
     * @return
     * @author Sephyioth
     */
    public static boolean isIntentAvailable (Intent intent) {
        if (UtillManager.getContext() == null) {
            return false;
        }
        PackageManager packageManager = UtillManager.getContext().getPackageManager();

        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        return list.size() > 0;
    }

    /**
     * 方法描述：
     * 高斯模糊图
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/9/9
     * 修改备注：
     *
     * @version
     */
    @SuppressLint("NewApi")
    public static Bitmap fastBlur (Bitmap sentBitmap, float radius) {

        if (radius <= 0) {
            radius = 0.5f;
        }
        if (radius > 25) {
            radius = 25;
        }

        if (Build.VERSION.SDK_INT > 16) {
            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

            final RenderScript rs = RenderScript.create(UtillManager.getContext());
            final Allocation input = Allocation.createFromBitmap(rs, sentBitmap,
                    Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(radius /* e.g. 3.f */);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(bitmap);

            rs.finish();
            rs.destroy();
            if (Build.VERSION.SDK_INT >= 23) RenderScript.releaseAllContexts();

            return bitmap;
        }
        return sentBitmap;
    }
}
