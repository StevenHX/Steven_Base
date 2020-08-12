package com.steven.updatetool;

import android.app.Activity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单网络请求管理类
 *
 * @author hx
 */
public class SimpleNetManager {
    private static final String TAG = "SimpleNetManager";
    private static SimpleNetManager instance;

    public static SimpleNetManager getInstance() {
        if (instance == null) {
            synchronized (SimpleNetManager.class) {
                if (instance == null) {
                    instance = new SimpleNetManager();
                }
            }
        }
        return instance;
    }

    /**
     * 发送get请求
     */
    public String doGetRequest(String urlPath) {
        URL url;
        try {
            url = new URL(urlPath);
            //得到connection对象。
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            //连接
            connection.connect();
            //得到响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //得到响应流
                InputStream inputStream = connection.getInputStream();
                //将响应流转换成字符串
                String result = Input2StrByArr(inputStream, "UTF-8");//将流转换为字符串。
                Log.e(TAG, "result by get =============>\n" + result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 发送get请求
     */
    public String doGetRequestWithHead(String urlPath, HashMap<String, String> heads) {
        URL url;
        try {
            url = new URL(urlPath);
            //得到connection对象。
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 添加请求头
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            //连接
            connection.connect();
            //得到响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //得到响应流
                InputStream inputStream = connection.getInputStream();
                //将响应流转换成字符串
                String result = Input2StrByArr(inputStream, "UTF-8");//将流转换为字符串。
                Log.e(TAG, "result by get =============>\n" + result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 异步发送get请求
     */
    public void doGetRequestAsync(String urlPath, NetResultCallBackListener listener) {
        ThreadPoolManager.getInstance().execute(() -> {
            URL url;
            try {
                url = new URL(urlPath);
                //得到connection对象。
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                //连接
                connection.connect();
                //得到响应码
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //得到响应流
                    InputStream inputStream = connection.getInputStream();
                    //将响应流转换成字符串
                    String result = Input2StrByArr(inputStream, "UTF-8");//将流转换为字符串。
                    Log.e(TAG, "result by get async =============>\n" + result);
                    listener.netResultCallBack(true, result);
                } else {
                    listener.netResultCallBack(false, "" + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listener.netResultCallBack(false, e.getMessage());
            }
        });
    }

    public void doGetRequestAsyncByte(String urlPath, NetBytesResultCallBackListener listener) {
        ThreadPoolManager.getInstance().execute(() -> {
            URL url;
            try {
                url = new URL(urlPath);
                //得到connection对象。
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                //连接
                connection.connect();
                //得到响应码
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //得到响应流
                    InputStream inputStream = connection.getInputStream();
                    //将响应流转换成字符串
                    byte[] result = Input2ByteArr(inputStream, "UTF-8");//将流转换为字符串。
                    listener.netBytesResultCallBack(true, result);
                } else {
                    listener.netBytesResultCallBack(false, new byte[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listener.netBytesResultCallBack(false, new byte[0]);
            }
        });
    }

    /**
     * 异步发送get请求,添加请求头
     */
    public void doGetRequestAsyncWithHead(String urlPath, HashMap<String, String> heads, NetResultCallBackListener listener) {
        ThreadPoolManager.getInstance().execute(() -> {
            URL url;
            try {
                url = new URL(urlPath);
                //得到connection对象。
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                // 添加请求头
                for (Map.Entry<String, String> entry : heads.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
                //连接
                connection.connect();
                //得到响应码
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //得到响应流
                    InputStream inputStream = connection.getInputStream();
                    //将响应流转换成字符串
                    String result = Input2StrByArr(inputStream, "UTF-8");//将流转换为字符串。
                    Log.e(TAG, "result by get async =============>\n" + result);
                    listener.netResultCallBack(true, result);
                } else {
                    listener.netResultCallBack(false, "" + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listener.netResultCallBack(false, e.getMessage());
            }
        });
    }

    public void doGetRequestAsyncWithHeadByte(String urlPath, HashMap<String, String> heads, NetBytesResultCallBackListener listener) {
        ThreadPoolManager.getInstance().execute(() -> {
            URL url;
            try {
                url = new URL(urlPath);
                //得到connection对象。
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                // 添加请求头
                for (Map.Entry<String, String> entry : heads.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
                //连接
                connection.connect();
                //得到响应码
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //得到响应流
                    InputStream inputStream = connection.getInputStream();
                    //将响应流转换成字符串
                    byte[] result = Input2ByteArr(inputStream, "UTF-8");//将流转换为字符串。
                    listener.netBytesResultCallBack(true, result);
                } else {
                    listener.netBytesResultCallBack(false, new byte[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listener.netBytesResultCallBack(false, new byte[0]);
            }
        });
    }

    /**
     * 发送post请求
     */
    public String doPostRequest(String urlPath, String body) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 设置Content-Type
            connection.setRequestProperty("Content-Type", "application/json");
            // 设置是否向httpUrlConnection输出，post请求设置为true，默认是false
            connection.setDoOutput(true);

            // 设置RequestBody
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.write(body);
            printWriter.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                //将响应流转换成字符串
                String result = Input2StrByArr(inputStream, "UTF-8");//将流转换为字符串。
                Log.e(TAG, "result by post =============>\n" + result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 发送post请求，添加请求头
     */
    public String doPostRequestWithHead(String urlPath, String body, HashMap<String, String> heads) {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 设置Content-Type
            connection.setRequestProperty("Content-Type", "application/json");
            // 添加请求头
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            // 设置是否向httpUrlConnection输出，post请求设置为true，默认是false
            connection.setDoOutput(true);

            // 设置RequestBody
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.write(body);
            printWriter.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                //将响应流转换成字符串
                String result = Input2StrByArr(inputStream, "UTF-8");//将流转换为字符串。
                Log.e(TAG, "result by post =============>\n" + result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 异步发送post请求
     */
    public void doPostRequestAsync(String urlPath, String body, NetResultCallBackListener listener) {
        ThreadPoolManager.getInstance().execute(() -> {
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                // 设置Content-Type
                connection.setRequestProperty("Content-Type", "application/json");
                // 设置是否向httpUrlConnection输出，post请求设置为true，默认是false
                connection.setDoOutput(true);

                // 设置RequestBody
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                printWriter.write(body);
                printWriter.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    //将响应流转换成字符串
                    String result = Input2StrByArr(inputStream, "UTF-8");//将流转换为字符串。
                    Log.e(TAG, "result by post async =============>\n" + result);
                    listener.netResultCallBack(true, result);
                } else {
                    listener.netResultCallBack(false, "" + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listener.netResultCallBack(false, e.getMessage());
            }
        });

    }

    /**
     * 异步发送post请求，添加请求头
     */
    public void doPostRequestAsyncWithHead(String urlPath, String body, HashMap<String, String> heads, NetResultCallBackListener listener) {
        ThreadPoolManager.getInstance().execute(() -> {
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(20000);
                // 设置Content-Type
                connection.setRequestProperty("Content-Type", "application/json");
                // 添加请求头
                for (Map.Entry<String, String> entry : heads.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
                // 设置是否向httpUrlConnection输出，post请求设置为true，默认是false
                connection.setDoOutput(true);

                // 设置RequestBody
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
                printWriter.write(body);
                printWriter.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    //将响应流转换成字符串
                    String result = Input2StrByArr(inputStream, "UTF-8");//将流转换为字符串。
                    Log.e(TAG, "result by post async =============>\n" + result);
                    listener.netResultCallBack(true, result);
                } else {
                    listener.netResultCallBack(false, "" + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
                listener.netResultCallBack(false, e.getMessage());
            }
        });

    }

    public void downloadFile(Activity activity,String urlPath, String appId, String fileName, NetDownloadCallBackListener listener) {
        ThreadPoolManager.getInstance().execute(() -> {
            URL url = null;
            int count = 0;
            try {
                url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();//建立连接
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) return;
                int totalSize = connection.getContentLength();//获取文件总大小
                InputStream is = connection.getInputStream();
                OutputStream os = new FileOutputStream(FileUtil.getSaveFile(activity.getApplication(), fileName));
                int len = 0;
                byte bs[] = new byte[1024];
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);//写入文件
                    count += len;
                    listener.getProgress(count == totalSize, String.format("%.1f", count * 1f / totalSize * 100));
                }
                os.flush();
                is.close();
                os.close();//最后关闭输入输出流
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 转换输入流为字符串
     *
     * @param in
     * @param encode
     * @return
     */
    private String Input2StrByArr(InputStream in, String encode) {
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[1024];
        int len;
        try {
            if (encode == null || encode.equals("")) {
                // 默认以utf-8形式
                encode = "utf-8";
            }
            while ((len = in.read(b)) != -1) {
                sb.append(new String(b, 0, len, encode));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换输入流为字节数组
     *
     * @param in
     * @param encode
     * @return
     */
    private byte[] Input2ByteArr(InputStream in, String encode) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = in.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public interface NetResultCallBackListener {
        void netResultCallBack(boolean isSuccess, String result);
    }

    public interface NetBytesResultCallBackListener {
        void netBytesResultCallBack(boolean isSuccess, byte[] result);
    }

    public interface NetDownloadCallBackListener {
        void getProgress(boolean isDone, String present);
    }
}