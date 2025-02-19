package com.skilrock.retailapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;


@SuppressLint("UseSparseArrays")
//@SuppressWarnings("deprecation")
public class SoundUtils {
    /**
     * TODO 上下文
     */
    private Context context;
    /**
     * TODO 声音池
     */
    private SoundPool soundPool;
    /**
     * TODO 添加的声音资源参数
     */
    private HashMap<Integer, Integer> soundPoolMap;
    /**
     * TODO 声音音量类型，默认为多媒体
     */
    private int soundVolType = 3;
    /**
     * TODO 无限循环播放
     */
    public static final int INFINITE_PLAY = -1;
    /**
     * TODO 单次播放
     */
    public static final int SINGLE_PLAY = 0;
    /**
     * TODO 铃声音量
     */
    public static final int RING_SOUND = 2;
    /**
     * TODO 媒体音量
     */
    public static final int MEDIA_SOUND = 3;

    /**
     * TODO 构造器内初始化
     *
     * @param context      上下文
     * @param soundVolType 声音音量类型，默认为多媒体
     * @author Leonardo
     * @date 2015-8-20 下午4:13:54
     */
    public SoundUtils(Context context, int soundVolType) {
        this.context = context;
        this.soundVolType = soundVolType;
        // 初始化声音池和声音参数map
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundPoolMap = new HashMap<>();
    }

    /**
     * TODO 添加声音文件进声音池
     *
     * @param order    所添加声音的编号，播放的时候指定
     * @param soundRes 添加声音资源的id
     * @author Leonardo
     * @date 2015-8-20 下午3:50:53
     * @see
     */
    public void putSound(int order, int soundRes) {
        // 上下文，声音资源id，优先级
        soundPoolMap.put(order, soundPool.load(context, soundRes, 1));
    }

    /**
     * TODO 播放声音
     *
     * @param order 所添加声音的编号
     * @param times 循环次数，0无不循环，-1无永远循环
     * @author Leonardo
     * @date 2015-8-20 下午3:52:44
     * @see
     */
    @SuppressWarnings("static-access")
    public void playSound(int order, int times) {
        // 实例化AudioManager对象
        AudioManager am = (AudioManager) context
                .getSystemService(context.AUDIO_SERVICE);
        // 返回当前AudioManager对象播放所选声音的类型的最大音量值
        float maxVolumn = am.getStreamMaxVolume(soundVolType);
        // 返回当前AudioManager对象的音量值
        float currentVolumn = am.getStreamVolume(soundVolType);
        // 比值
        float volumnRatio = currentVolumn / maxVolumn;
        soundPool.play(soundPoolMap.get(order), volumnRatio, volumnRatio, 1,
                times, 1);
    }

    /**
     * TODO 设置 soundVolType 的值
     */
    public void setSoundVolType(int soundVolType) {
        this.soundVolType = soundVolType;
    }

    public void destroy() {
        if (soundPool != null) {
            soundPool.release();
        }
    }
}