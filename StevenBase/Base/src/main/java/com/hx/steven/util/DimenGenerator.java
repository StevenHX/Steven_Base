package com.hx.steven.util;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;

/**
 * 用于生成sw限定符文件
 * Created by Steven on 2018/11/6.
 */


public class DimenGenerator {

    private static final int MAX_SIZE = 360;

    private static final int DESIGN_WIDTH = 360;

    public static void main(String[] args) {
        int[] arr = new int[]{300, 320, 360, 384, 411, 450};
        for (int swdp : arr) {
            makeAll(DESIGN_WIDTH, swdp, "C:\\Users\\Steven\\Desktop\\sw");
        }
    }

    public static float px2dip(float pxValue, int sw, int designWidth) {
        float dpValue = (pxValue / (float) designWidth) * sw;
        BigDecimal bigDecimal = new BigDecimal(dpValue);
        float finDp = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return finDp;
    }

    private static String makeAllDimens(int swdp, int designWidth) {
        float dpValue;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
            sb.append("<resources>\r\n");
            sb.append(String.format("<dimen name=\"base_dpi\">%ddp</dimen>\r\n", swdp));
            for (int i = 0; i <= MAX_SIZE; i++) {
                dpValue = px2dip((float) i, swdp, designWidth);
                sb.append(String.format("<dimen name=\"dp_%1$d\">%2$.2fdp</dimen>\r\n", i, dpValue));
            }
            sb.append("</resources>\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void makeAll(int designWidth, int swdp, String buildDir) {
        try {
            if (swdp < 0) {
                return;
            }
            String folderName = "values-sw" + swdp + "dp";
            File file = new File(buildDir + File.separator + folderName);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath() + "/dimens.xml");
            fos.write(makeAllDimens(swdp, designWidth).getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
