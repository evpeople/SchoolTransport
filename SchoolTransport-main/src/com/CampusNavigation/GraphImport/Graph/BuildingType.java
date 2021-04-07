package com.CampusNavigation.GraphImport.Graph;

import java.beans.Beans;
import java.io.Serializable;
import java.sql.Time;
import java.util.Random;

public enum BuildingType implements Serializable {
    canteen,//餐厅
    crossing,//路口
    exit,//大门//出口
    buildingCrossing,//建筑路口
    Default,//未定义
    lake,//湖
    studentCenter,//学活
    tree,//小树林
    soccer,//足球场中心
    runway,//跑道
    swim,//游泳池
    service,//服务设施
    hospital,//校医院
    office,//办公楼
    teach,//教学楼
    dorm,//宿舍
    bus,//公交车站
    car,//班车站
    coffee,//咖啡厅
    librariy,//图书馆
    lab;//实验楼


    public int getFloorNum(){
        int ans=0;
        switch (this){
            case car:case bus:case crossing:case soccer:case runway:case  exit:case buildingCrossing:
            case lake:case Default:case service:case tree:ans=0;break;
            case swim:case coffee:case hospital:ans=1;break;
            case lab:case dorm:case librariy:case canteen:case teach:case office:case studentCenter:ans=new Random(4).nextInt()+1;
        }
        return ans;
    }

}
