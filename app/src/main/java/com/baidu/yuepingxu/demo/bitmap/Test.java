package com.baidu.yuepingxu.demo.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baidu.yuepingxu.demo.R;

/**
 * @author xuyueping
 * @date 2020-03-23
 * @describe
 */
public class Test {
    public static void main(String[] args) {
        // 大图在分辨率低下不处理的话不会带来任何的视觉效果
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 不加载到内存 该对象的inJustDecodeBounds属性设置为true，decodeResource()方法就不会生成Bitmap对象，而仅仅是读取该图片的尺寸和类型信息
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_launcher_background, options);
        int width = options.outWidth;
        int height = options.outHeight;
        // 这样拿到图片的大小后预估下需要多少内存
        // 图片内存计算方式：如果是ARGB_8888格式的，那么就是有32位，再乘以图片的宽高分辨率，比如是480×800(这个分辨率是options的宽高)，那么大小就是480×800x32/8得到1536000个字节，再换成kb除以1024
        // 比如我们有一张2048*1536像素的图片，将inSampleSize的值设置为4，就可以把这张图片压缩成512*384像素。
        // 原本加载这张图片需要占用13M的内存，压缩后就只需要占用0.75M了
    }


    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    }
