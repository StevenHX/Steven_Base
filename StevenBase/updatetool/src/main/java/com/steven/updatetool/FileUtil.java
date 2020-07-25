package com.steven.updatetool;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;

/**
 * 文件计算工具类
 */
public class FileUtil {

    /**
     * 判断sd卡是否挂载
     */
    private static boolean isSDMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isSaveFileExits(Application app, String fileName) throws Exception{
        File appDir  =  app.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (appDir == null) throw new Exception("sdCard not mounted");
        File dataFile = new File(appDir, fileName);
        return dataFile.exists();
    }
    /**
     * 获取保存的文件路径
     */
    public static File getSaveFile(Application app, String fileName) throws Exception {
        File appDir  =  app.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (appDir == null) throw new Exception("sdCard not mounted");
        return new File(appDir, fileName);
    }

    public static boolean deleteSaveFile(Application app, String fileName) throws Exception {
        File appDir  =  app.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (appDir == null) throw new Exception("sdCard not mounted");
        File dataFile = new File(appDir, fileName);
        if (dataFile.exists()) {
            return dataFile.delete();
        }
        return false;
    }
    /**
     * 保存文件到根目录app名称文件夹下的目录
     */
    public static void saveFile2ExternalAppDirectory(Application app, String fileName, String content) throws Exception {
        File file  =  getSaveFile(app,fileName);
        String strContent = content + "\r\n";
        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
        raf.seek(file.length());
        raf.write(strContent.getBytes());
        raf.close();
    }

    /**
     * 从指定目录，读取文件内容 默认从根目录appID文件夹下读取
     */
    public static String getContentFromAppDirectory(Application app, String fileName) throws IOException {
        File file  =  app.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File dataFile = new File(file, fileName);
        RandomAccessFile raf = new RandomAccessFile(dataFile, "rw");
        StringBuilder builder = new StringBuilder();
        String content;
        while ((content = raf.readLine()) != null) {
            builder.append(content);
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     */
    public static long getFolderSize(File file) {
        long size = 0;
        File[] fileList = file.listFiles();
        for (File aFileList : fileList) {
            if (aFileList.isDirectory()) {
                size = size + getFolderSize(aFileList);
            } else {
                size = size + aFileList.length();
            }
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(long size) {
        long kiloByte = size / 1024;
        long megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Long.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        long gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Long.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        long teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Long.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}