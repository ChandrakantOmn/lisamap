package com.lisa.datamanager.map;


import android.util.Log;

import com.lisa.datamanager.wrap.WholeTask;

import org.dom4j.DocumentException;

import java.io.File;

import srs.Layer.ILayer;

/**
 * Created by lisa on 2016/12/15.
 */
public class MapShapeManager {
    /**
     * 任务包数据
     */
     private static WholeTask mTASK = null;

    /**
     * 矢量数据是否存在
     * @return
     */
    public static boolean hasTask(){
        if(mTASK!=null){
            return  true;
        }
        return false;
    }

    /**
     * 提取矢量数据
     * @throws Exception
     */
    public static void loadDataFromTCF() throws Exception {
        MapsUtil.LayerIDs_SHAPE.clear();
        if(MapsUtil.PATH_TCF_SHAPE==null||MapsUtil.PATH_TCF_SHAPE.trim().equalsIgnoreCase("")){
            //若尚未设置路径，则直接返回
            Log.i("MAP","无矢量shape数据");
            return;
        }
        //获取当前共多少图层
        int addedLayerCount = MapsManager.getMap().getLayerCount();

        loadTCF(MapsUtil.PATH_TCF_SHAPE);

        if (mTASK == null)
            return;
        int count = mTASK.getLayersCount();
        MapsUtil.LayerIDs_SHAPE.clear();
        for (int i = 0; i < count; i++) {
            ILayer layer = mTASK.GetLayer(i);
            if (layer != null /*&& layer.getVisible()*/) {
                MapsManager.getMap().AddLayer(layer);
                MapsUtil.LayerIDs_SHAPE.add(addedLayerCount);
                addedLayerCount += 1;
            }
        }
    }


    /**
     * 从tcf文件加载RASTER数据
     * @param   taskPath    地图配置文件路径
     * @throws  Exception
     */
    public static void loadTCF(String taskPath) throws Exception {
        File file = new File(taskPath);
        if (file.exists()
                && taskPath.substring(taskPath.lastIndexOf(".")).toUpperCase().equalsIgnoreCase(
                ".TCF")) {
            mTASK = new WholeTask();
            try {
                mTASK.LoadFromFile(taskPath);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 按分组控制图层的显示状态
     * @param mc	分组标志
     * @param flag	是否显示。true：显示；false：隐藏
     */
    public static void show(String mc,boolean flag) {
        mTASK.showLayer(mc,flag);
    }

    public static void dispose(){
        if(mTASK != null){
            mTASK.dispose();
            mTASK = null;
        }
    }
}
