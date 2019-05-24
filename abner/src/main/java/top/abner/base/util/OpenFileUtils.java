package top.abner.base.util;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.util.Locale;

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/5/23 9:48
 */
public class OpenFileUtils {

    /**
     * 声明各种类型文件的dataType
     * */
    /**
     * 未指定明确的文件类型，不能使用精确类型的工具打开，需要用户选择
     * */
    private static final String DATA_TYPE_ALL = "*/*";
    private static final String DATA_TYPE_APK = "application/vnd.android.package-archive";
    private static final String DATA_TYPE_VIDEO = "video/*";
    private static final String DATA_TYPE_AUDIO = "audio/*";
    private static final String DATA_TYPE_HTML = "text/html";
    private static final String DATA_TYPE_IMAGE = "image/*";
    private static final String DATA_TYPE_PPT = "application/vnd.ms-powerpoint";
    private static final String DATA_TYPE_EXCEL = "application/vnd.ms-excel";
    private static final String DATA_TYPE_WORD = "application/msword";
    private static final String DATA_TYPE_CHM = "application/x-chm";
    private static final String DATA_TYPE_TXT = "text/plain";
    private static final String DATA_TYPE_PDF = "application/pdf";

    /**
     * 打开文件
     * */
    @TargetApi(Build.VERSION_CODES.N)
    public static Intent openFile(Uri uri) {
        return openFileAdapter(uri, null);
    }

    /**
     * 打开文件
     * */
    public static Intent openFile(String filePath) {
        return openFileAdapter(null, filePath);
    }

    /**
     *
     * */
    private static Intent openFileAdapter(Uri uri, String filePath) {
        String fileSuffix = "";
        if (null != uri) {
            String uriStr = uri.toString();
            fileSuffix = uriStr.substring(uriStr.lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());
        } else if (null != filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            // 获取扩展名
            fileSuffix = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());
        }
        switch (fileSuffix) {
            case "m4a":
            case "mp3":
            case "mid":
            case "xmf":
            case "ogg":
            case "wav": {
                return getAudioFileIntent(uri, filePath);
            }
            case "3gp":
            case "mp4": {
                return getVideoFileIntent(uri, filePath);
            }
            case "jpg":
            case "gif":
            case "png":
            case "jpeg":
            case "bmp": {
                return getImageFileIntent(uri, filePath);
            }
            case "apk": {
                return getApkFileIntent(uri, filePath);
            }
            case "ppt": {
                return getPptFileIntent(uri, filePath);
            }
            case "xls": {
                return getExcelFileIntent(uri, filePath);
            }
            case "doc": {
                return getWordFileIntent(uri, filePath);
            }
            case "pdf": {
                return getPdfFileIntent(uri, filePath);
            }
            case "chm": {
                return getChmFileIntent(uri, filePath);
            }
            case "txt": {
                return getTextFileIntent(uri, filePath, false);
            }
            default: {
                return getAllIntent(uri, filePath);
            }
        }
    }

    /**
     * Android获取一个用于打开 APK 文件的 intent
     * */
    private static Intent getAllIntent(Uri uri, String param) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            uri = Uri.fromFile(new File(param));
        }
        intent.setDataAndType(uri, DATA_TYPE_ALL);
        return intent;
    }

    /**
     * Android 获取一个用于打开 APK 文件的 intent
     * */
    public static Intent getApkFileIntent(Uri uri, String param) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            uri = Uri.fromFile(new File(param));
        }
        intent.setDataAndType(uri, DATA_TYPE_APK);
        return intent;
    }

    /**
     * Android获取一个用于打开VIDEO文件的intent
     * */
    public static Intent getVideoFileIntent(Uri uri, String param) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        if (null == uri) {
            uri = Uri.fromFile(new File(param));
        }
        intent.setDataAndType(uri, DATA_TYPE_VIDEO);
        return intent;
    }

    /**
     * Android 获取一个用于打开 AUDIO 文件的 intent
     * */
    private static Intent getAudioFileIntent(Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        if (null == uri) {
            uri = Uri.fromFile(new File(filePath));
        }
        intent.setDataAndType(uri, DATA_TYPE_AUDIO);
        return intent;
    }

    /**
     * Android 获取一个用于打开 Html 文件的 intent
     * */
    public static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, DATA_TYPE_HTML);
        return intent;
    }

    /**
     *  Android 获取一个用于打开图片文件的 intent
     * */
    public static Intent getImageFileIntent(Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            uri = Uri.fromFile(new File(filePath));
        }
        intent.setDataAndType(uri, DATA_TYPE_IMAGE);
        return intent;
    }

    /**
     * Android 获取一个用于打开 PPT 文件的 intent
     * */
    public static Intent getPptFileIntent(Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            uri = Uri.fromFile(new File(filePath));
        }
        intent.setDataAndType(uri, DATA_TYPE_PPT);
        return intent;
    }

    /**
     * Android获取一个用于打开Excel文件的intent
     * */
    private static Intent getExcelFileIntent(Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            uri = Uri.fromFile(new File(filePath));
        }
        intent.setDataAndType(uri, DATA_TYPE_EXCEL);
        return intent;
    }

    // Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(Uri uri, String filePath) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            uri = Uri.fromFile(new File(filePath));
        }
        intent.setDataAndType(uri, DATA_TYPE_WORD);
        return intent;
    }

    /**
     * Android获取一个用于打开CHM文件的intent
     * */
    private static Intent getChmFileIntent(Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            uri = Uri.fromFile(new File(filePath));
        }
        intent.setDataAndType(uri, DATA_TYPE_CHM);
        return intent;
    }

    /**
     * Android获取一个用于打开文本文件的intent
     * */
    private static Intent getTextFileIntent(Uri uri, String param, boolean paramBoolean) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            if (paramBoolean) {
                uri = Uri.parse(param);
            } else {
                uri = Uri.fromFile(new File(param));
            }
        }
        intent.setDataAndType(uri, DATA_TYPE_TXT);
        return intent;
    }

    /**
     * Android获取一个用于打开PDF文件的intent
     * */
    private static Intent getPdfFileIntent(Uri uri, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (null == uri) {
            uri = Uri.fromFile(new File(filePath));
        }
        intent.setDataAndType(uri, DATA_TYPE_PDF);
        return intent;
    }
}
