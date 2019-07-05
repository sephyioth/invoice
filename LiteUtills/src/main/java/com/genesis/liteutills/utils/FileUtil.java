package com.genesis.liteutills.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.genesis.liteutills.tools.LocalStorageProvider;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 类说明：
 * <p/>
 * File Config 类
 *
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-7-11 下午10:23:01
 */
public class FileUtil {
    // ** 常量 **/
    public static final long CACHE_MIN_SDCARD_SPACE = 10 * 1024 * 1024; // 大于10M的空间
    public final static long ExpiredTime = 7 * 24 * 60 * 60 * 1000; // 7天
    public static final String MIME_TYPE_AUDIO = "audio/*";
    public static final String MIME_TYPE_TEXT = "text/*";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String MIME_TYPE_VIDEO = "video/*";
    public static final String MIME_TYPE_APP = "application/*";
    public static final String HIDDEN_PREFIX = ".";
    /**
     * TAG for log messages.
     */
    static final String TAG = "FileUtils";
    private static final boolean DEBUG = false; // Set to true to enable logging
    /**
     * 无效的数据
     */
    public static final int INVALID = -1;
    /**
     * 无效的Id(可用于判断
     */
    public static final int INVALID_ID = 0;
    /**
     * 无效的算术
     */
    public static final int INVALID_MERTICS = -1;

    /**
     * 编码格式
     */
    public static final String CHARSET_NAME_UTF_8 = "utf-8";
    // ** 变量 **/

    // ** 构造函数 **/

    // ** 成员方法 **/

    // ** 静态方法 **/

    /**
     * 获取文件输入流
     *
     * @param filename 文件名
     */
    public static InputStream openFileInputStream(Context context,
                                                  String filename) {
        filename = checkFilePrefix(filename);
        InputStream is = null;
        if (context != null) {
            try {
                is = context.openFileInput(filename);
            } catch (FileNotFoundException e1) {
                is = openAssetsFileInputStream(context, filename);
            } catch (IllegalArgumentException e2) {
                try {
                    is = context.getAssets().open(filename);
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return is;
    }

    /**
     * 读取默认assets目录下到default文件
     *
     * @param context
     * @param filename
     * @return
     */
    public static InputStream openAssetsFileInputStream(Context context,
                                                        String filename) {
        try {
            return context.getAssets().open(filename);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件输出流
     *
     * @param filename 文件名
     */
    @SuppressWarnings("deprecation")
    public static OutputStream openFileOutputStream(Context context,
                                                    String filename) {
        filename = checkFilePrefix(filename);
        OutputStream os = null;
        if (context != null) {
            try {
                os = context.openFileOutput(filename,
                        Context.MODE_WORLD_WRITEABLE);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return os;
    }

    private static String checkFilePrefix(String filename) {
        if (filename.startsWith("/")) {
            filename = filename.substring(1);
        }
        return filename;
    }

    /**
     * 删除文件
     *
     * @param filename 文件名
     * @return true-删除成功，false-删除失败
     */
    public static boolean deleteFileInSdcard(String filename) {
        filename = checkFilePrefix(filename);
        try {
            String path = getKlineSdcardpath() + filename;
            File file = new File(path);
            // 文件存在就删除
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param filename 文件名
     * @return true-删除成功，false-删除失败
     */
    public static boolean deleteCacheFileWithName(String filename) {
        try {
            File file = new File(filename);
            // 文件存在就删除
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据指定文件名，打开缓存目录下的数据输入流-需要平台实现
     *
     * @param filename 文件名
     * @return 数据流 OutputStream
     */
    public static InputStream openCacheFileInputStream(Context context,
                                                       String filename) {
        if (filename == null) {
            return null;
        }
        InputStream is = null;
        if (context != null) {
            File dir = context.getCacheDir();
            File file = new File(dir, filename);
            if (!file.exists()) {
                return null;
            }
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return is;
    }

    /**
     * 根据文件名是否含有nameFlag，来取得缓存下所有的指标缓存文件名称
     *
     * @param context
     * @param nameFlag
     * @return
     */
    public static List<String> getTechCacheFileNames(Context context,
                                                     String nameFlag) {
        List<String> fileNames = new ArrayList<String>();
        if (context != null) {
            File dir = context.getCacheDir();
            File file[] = dir.listFiles();
            if (file != null && file.length > 0) {
                for (File f : file) {
                    if (f.getName().contains(nameFlag)) {
                        fileNames.add(f.getName());
                    }
                }
            }
        }
        return fileNames;
    }

    /**
     * 获取某路径下的所有文件名称
     *
     * @param context
     * @param path
     * @return
     */
    public static List<String> getCacheFileNames(Context context, String path) {
        List<String> fileNames = new ArrayList<String>();
        if (context != null) {
            File dir = new File(path);
            if (dir.exists()) {
                File file[] = dir.listFiles();
                if (file != null && file.length > 0) {
                    for (File f : file) {
                        fileNames.add(f.getName());
                    }
                }
            }
        }
        return fileNames;
    }

    /**
     * 通过文件名删除缓存文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static boolean deleteCacheWithFileName(Context context,
                                                  String fileName) {
        if (context != null) {
            File dir = context.getCacheDir();
            File file = new File(dir, fileName);
            if (file.exists()) {
                file.delete();
                return true;
            }
        }
        return false;
    }

    /**
     * cache目录下是否存在这个文件
     *
     * @param fileName
     * @return
     */
    public static boolean isExistFileInSdcard(String fileName) {
        if (checkSdcardAvailable()) {
            File file = new File(getKlineSdcardpath() + File.separator
                    + fileName);
            return file.exists();
        }
        return false;
    }

    /**
     * 创建目录
     */
    public static void createMkdir() {
        if (checkSdcardAvailable()) {
            File file = new File(getKlineSdcardpath());
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    /**
     * 根据指定文件名，打开缓存目录下的数据输出流-需要平台实现
     *
     * @param filename 文件名
     * @return 数据流 OutputStream
     */
    public static OutputStream openSdcardFileOutputStream(String filename) {
        if (filename == null) {
            return null;
        }
        OutputStream os = null;
        String dir = getKlineSdcardpath();
        File file = new File(dir, filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    /**
     * 根据指定文件名，打开缓存目录下的数据输出流-需要平台实现
     *
     * @param filename 文件名
     * @return 数据流 OutputStream
     */
    public static OutputStream openNetModelOutputStream(String filename) {
        if (GeneralUtil.getExternalCacheDir() == null && filename == null) {
            return null;
        }
        OutputStream os = null;
        String dir = getNetModelSdcardpath();
        if (checkSdcardAvailable()) {
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        File file = new File(dir, filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    /**
     * 根据指定文件名，打开缓存目录下的数据输入流-需要平台实现
     *
     * @param filename 文件名
     * @return 数据流 OutputStream
     */
    public static InputStream openSdcardFileInputStream(String filename) {
        if (filename == null) {
            return null;
        }
        InputStream is = null;
        String dir = getKlineSdcardpath() + File.separator + filename;
        File file = new File(dir);
        if (!file.exists()) {
            return null;
        }
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 根据指定文件名，打开缓存目录下的数据输入流-需要平台实现
     *
     * @param filename 文件名
     * @return 数据流 OutputStream
     */
    public static InputStream openNetModelFileInputStream(String filename) {
        if (GeneralUtil.getExternalCacheDir() == null && filename == null) {
            return null;
        }
        InputStream is = null;
        String dir = getNetModelSdcardpath() + File.separator + filename;
        File file = new File(dir);
        if (!file.exists()) {
            return null;
        }
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 根据指定文件名，打开缓存目录下的数据输出流-需要平台实现
     *
     * @param filename 文件名
     * @return 数据流 OutputStream
     */
    public static OutputStream openCacheFileOutputStream(Context context,
                                                         String filename) {
        if (filename == null) {
            return null;
        }
        OutputStream os = null;
        if (context != null) {
            File dir = context.getCacheDir();
            File file = new File(dir, filename);
            try {
                os = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return os;
    }

    /**
     * 获取缓存目录下指定文件前缀的文件个数
     *
     * @param prefix 前缀
     * @return 返回文件个数，返回-1则为无效
     */
    public static int getFileCountInCacheDir(final String prefix, final File d) {
        if (prefix == null) {
            return -1;
        }
        // final File d = context.getCacheDir();
        File[] list = d.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (dir == d && filename.startsWith(prefix)) {
                    return true;
                }
                return false;
            }
        });
        int count = (list != null) ? list.length : 0;
        return count;
    }

    /**
     * 获取程序目录下指定文件前缀的文件个数
     *
     * @param prefix 前缀
     * @return 返回符合的文件个数
     */
    public static int getFileCount(Context context, final String prefix) {
        if (prefix == null) {
            return -1;
        }
        if (context != null) {
            final File d = context.getFilesDir();
            File[] list = d.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (dir == d && filename.startsWith(prefix)) {
                        return true;
                    }
                    return false;
                }
            });
            int count = (list != null) ? list.length : 0;
            return count;
        }
        return -1;
    }

    /**
     * 清除缓存目录
     */
    public static boolean clearCacheDir(Context context) {
        if (context == null) {
            return false;
        }
        boolean success = false;
        File dir = context.getCacheDir();
        File[] list = dir.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                success |= list[i].delete();
            }
        }
        return success;
    }

    /**
     * 清除缓存目录下的指定文件
     *
     * @param prefix 文件前缀
     * @param count  删除的最大数量
     * @return true-删除成功，false-删除失败
     */
    public static boolean deleteOldCacheFile(final File d, final String prefix,
                                             int count) {
        // final File d = context.getCacheDir();
        File[] list = d.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (dir == d && filename.startsWith(prefix)) {
                    return true;
                }
                return false;
            }
        });
        if (list != null && list.length > 0) {
            List<File> temp = new ArrayList<File>(list.length);
            for (int i = 0; i < list.length; i++) {
                temp.add(list[i]);
            }
            // 按修改时间升序排列
            Collections.sort(temp, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    long l1 = f1.lastModified();
                    long l2 = f2.lastModified();
                    long diff = l1 - l2;
                    if (diff > 0) {
                        return 1;
                    } else if (diff == 0) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
            // 删除文件
            int size = Math.min(temp.size(), count);
            for (int i = 0; i < size; i++) {
                File file = temp.get(i);
                file.delete();
            }
            return true;
        }
        return false;
    }

    /**
     * 清除程序目录
     */
    public static boolean clearFileDir(Context context) {
        if (context == null) {
            return false;
        }
        boolean success = false;
        File dir = context.getFilesDir();
        File[] list = dir.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                success |= list[i].delete();
            }
        }
        return success;
    }

    /**
     * 获取文件字节缓存
     *
     * @param name 文件名
     * @return 返回字节
     */
    public static byte[] getFileBuffer(Context context, String name) {
        InputStream in = openFileInputStream(context, name);
        if (in == null) {
            return null;
        }
        try {
            // 读取文件
            int length = in.available();
            if (length == 0) {
                return null;
            }
            byte[] buf = new byte[length];
            in.read(buf);
            // 返回
            return buf;
        } catch (IOException e) {
            // do nothing
        } finally {
            // 关闭流，释放资源
            try {
                in.close();
            } catch (IOException e) {
                // do nothing
            }
            in = null;
        }
        return null;
    }

    /**
     * 检查SDcard是否可用
     *
     * @return 如果空间够用返回true，不够用返回false
     */
    public static boolean checkSdcardAvailable() {
        if (GeneralUtil.getExternalCacheDir() != null
                && Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                && getSdcardAvailableSpace() >= CACHE_MIN_SDCARD_SPACE) {
            return true;
        }
        return false;
    }

    /**
     * 计算sdcard的剩余空间 如果没有的话返回-1
     */
    @SuppressWarnings("deprecation")
    public static long getSdcardAvailableSpace() {
        if (GeneralUtil.getExternalCacheDir() == null) {
            return INVALID;
        }
        File path = GeneralUtil.getExternalCacheDir();
        StatFs statFs = new StatFs(path.getPath());

        long blockSize = statFs.getBlockSize();
        long avaliableBlocks = statFs.getAvailableBlocks();
        return blockSize * avaliableBlocks;
    }

    /**
     * 获取SDcard目录
     *
     * @return
     */
    public static String getKlineSdcardpath() {
        // 有sdcard就返回其路径，没有就返回null
        if (GeneralUtil.getExternalCacheDir() != null) {
            return GeneralUtil.getExternalCacheDir().getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 获取netModel的目录
     *
     * @return
     */
    public static String getNetModelSdcardpath() {
        // 有sdcard就返回其路径，没有就返回null
        if (GeneralUtil.getExternalCacheDir() != null) {
            return GeneralUtil.getExternalCacheDir().getAbsolutePath();
        } else {
            return null;
        }
    }

    public static String getKlineEasyReadSdCardPath() {
        if (GeneralUtil.getExternalCacheDir() != null) {
            return GeneralUtil.getExternalCacheDir().getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 保存buffer到FileCache的fileName中
     *
     * @param buffer
     * @param fileName
     */
    @SuppressWarnings("deprecation")
    public static void saveFileBuffer(Context context, byte[] buffer,
                                      String fileName) {
        OutputStream out = null;
        try {
            out = context
                    .openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        if (out == null) {
            return;
        }
        try {
            // 写入文件
            out.write(buffer);
            out.flush();
        } catch (IOException e) {
            // do nothing
        } finally {
            // 关闭流，释放资源
            try {
                out.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    /**
     * 获取数据
     *
     * @param fileName
     * @param context
     * @return
     */
    public static String getCacheData(String fileName, Context context) {
        InputStream is = null;
        ByteArrayOutputStream outputStream = null;
        try {
            is = FileUtil.openCacheFileInputStream(context, fileName);
            if (is != null) {
                DataInputStream stream = null;
                stream = new DataInputStream(is);
                // 读取字节缓存
                byte[] buffer = null;
                int length = stream.available();
                if (length <= 0) {
                    return null;
                }
                buffer = new byte[length];
                stream.read(buffer);
                if (buffer != null) {
                    return new String(buffer, CHARSET_NAME_UTF_8);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * File (not directories) filter.
     *
     * @author paulburke
     */
    public static FileFilter sFileFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String fileName = file.getName();
            // Return files only (not directories) and skip hidden files
            return file.isFile() && !fileName.startsWith(HIDDEN_PREFIX);
        }
    };
    /**
     * Folder (directories) filter.
     *
     * @author paulburke
     */
    public static FileFilter sDirFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            final String fileName = file.getName();
            // Return directories only and skip hidden directories
            return file.isDirectory() && !fileName.startsWith(HIDDEN_PREFIX);
        }
    };

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     * null if uri was null.
     */
    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * @return Whether the URI is a local one.
     */
    public static boolean isLocal(String url) {
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            return true;
        }
        return false;
    }

    /**
     * @return True if Uri is a MediaStore Uri.
     * @author paulburke
     */
    public static boolean isMediaUri(Uri uri) {
        return "media".equalsIgnoreCase(uri.getAuthority());
    }

    /**
     * Convert File into Uri.
     *
     * @param file
     * @return uri
     */
    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;
    }

    /**
     * Returns the path only (without file name).
     *
     * @param file
     * @return
     */
    public static File getPathWithoutFilename(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // no file to be split off. Return everything
                return file;
            } else {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();

                // Construct path without file name.
                String pathwithoutname = filepath.substring(0,
                        filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length() - 1);
                }
                return new File(pathwithoutname);
            }
        }
        return null;
    }

    /**
     * @return The MIME type for the given file.
     */
    public static String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }

    /**
     * @return The MIME type for the give Uri.
     */
    public static String getMimeType(Context context, Uri uri) {
        File file = new File(getPath(context, uri));
        return getMimeType(file);
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is {@link LocalStorageProvider}.
     * @author paulburke
     */
    public static boolean isLocalStorageDocument(Uri uri) {
        return LocalStorageProvider.AUTHORITY.equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     * @author paulburke
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author paulburke
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                if (DEBUG)
                    DatabaseUtils.dumpCursor(cursor);

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static String getPath(final Context context, final Uri uri) {

        if (DEBUG)
            Log.d(TAG + " File -",
                    "Authority: " + uri.getAuthority() +
                            ", Fragment: " + uri.getFragment() +
                            ", Port: " + uri.getPort() +
                            ", Query: " + uri.getQuery() +
                            ", Scheme: " + uri.getScheme() +
                            ", Host: " + uri.getHost() +
                            ", Segments: " + uri.getPathSegments().toString()
            );

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // LocalStorageProvider
            if (isLocalStorageDocument(uri)) {
                // The path is the id
                return DocumentsContract.getDocumentId(uri);
            }
            // ExternalStorageProvider
            else if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    // ** 内部类接口 **/
}
