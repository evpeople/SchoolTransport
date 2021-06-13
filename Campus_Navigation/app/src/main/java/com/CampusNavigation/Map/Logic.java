package com.CampusNavigation.Map;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @see Logic 导览系统的物理-逻辑地址相对应逻辑类
 * @see Logic#Logic(Map, Map, Context) 导入两个校区的地图初始化导览逻辑类
 * @see Logic#findPhyAddr(String) 通过传入的逻辑地址（字符串）寻到并返回其对应的物理地址集合
 * @see Logic#campusOne 校区一
 * @see Logic#campusTwo 校区二
 * @see Logic#searcher 全局逻辑地址-逻辑关系对应表
 * @see Logic.logicRelation 存储逻辑关系的结构体
 * @see Logic.logicRelation#type 对应建筑物类型
 * @see Logic.logicRelation#campusNum 对应所在的校区
 * @see Logic.logicRelation#indexOfBuild 对应建筑物所在大楼的地图内序号
 * @see Logic.logicRelation#floorNum 对应建筑物所在层号
 * @see Logic.logicRelation#indexOfRoom 对应建筑物所在楼层地图内的序号
 * */
public class Logic {
    public final static int NULL = 0;
    public final static int BUILD = 1;
    public final static int ROOM = 2;

    private final Map campusOne;
    private final Map campusTwo;
    private HashMap<String, HashSet<logicRelation>> searcher = new HashMap<>();

    /**
     * @param campusOne 传入校区一
     * @param campusTwo 传入校区二
     * @param context 读文件用
     * */
    public Logic(Map campusOne, Map campusTwo, Context context) {               //使用方法：主流程内先实例化一个logic对象
        this.campusOne = campusOne;                            //调用方法：在需要找到逻辑地址对应建筑时
        this.campusTwo = campusTwo;                            //调用方法：调用findPhyAddr(逻辑地址)

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("logicAddr")));
            String nextStr;
            String currCampusNum;
            String currBuildNum;
            String currFloorNum;
            String currLogicAddr;
            logicRelation currRelation;
            HashSet<logicRelation> currSet;
            nextStr = in.readLine();                            //阅读文件库第一行
            while(nextStr != null) {
                currLogicAddr = nextStr + "";                     //赋值curr逻辑地址
                currSet = new HashSet<>();
                while(((nextStr = in.readLine()) != null) && (nextStr.contains("*"))) {//若下一行包含“*”，也就说明接下来有赋值逻辑关系类的操作
                    if (nextStr.equals("*")) {//一颗*代表是大楼
                        nextStr = in.readLine();
                        currCampusNum = nextStr + "";           //存储校区号
                        nextStr = in.readLine();                //读取大楼号
                        currRelation = new logicRelation(Integer.parseInt(currCampusNum),Integer.parseInt(nextStr));
                        currSet.add(currRelation);
                    } else if (nextStr.equals("**")) {//两颗*代表是房间
                        nextStr = in.readLine();
                        currCampusNum = nextStr + "";           //存储校区号
                        nextStr = in.readLine();
                        currBuildNum = nextStr + "";            //存储大楼号
                        nextStr = in.readLine();
                        currFloorNum = nextStr + "";            //存储楼层号
                        nextStr = in.readLine();
                        currRelation = new logicRelation(Integer.parseInt(currCampusNum),
                                Integer.parseInt(currBuildNum),Integer.parseInt(currFloorNum),
                                Integer.parseInt(nextStr));
                        currSet.add(currRelation);
                    }
                }
                this.searcher.put(currLogicAddr,currSet);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param logicAddr 传入的逻辑地址
     * */
    public HashSet<Building> findPhyAddr(String logicAddr) {
        HashSet<Building> retBuild = new HashSet<>();
        HashSet<logicRelation> currFound = this.searcher.get(logicAddr);
        logicRelation curr;
        if(currFound == null) return null;

        Iterator<logicRelation> iterator = currFound.iterator();                    //初始化迭代器
        Building searchedBuild;
        while(iterator.hasNext()) {                                                 //如果还存在待遍历目标
            curr = iterator.next();                                                 //赋值curr这个目标
            if(curr.getCampusNum() == 1)                                            //若在一校区
                searchedBuild = this.campusOne.getBuilding(curr.getIndexOfBuild()); //搜索到大楼位置
            else
                searchedBuild = this.campusTwo.getBuilding(curr.getIndexOfBuild());
            if(curr.getType() == BUILD) {
                retBuild.add(searchedBuild);                                        //若目标就是大楼，返回本身
            }
            else {
                retBuild.add((((SpecificBuild)searchedBuild).getMapOfFloor(curr.getFloorNum()))
                        .getBuilding(curr.getIndexOfRoom()));                       //若目标是房间，在大楼里搜到对应房间返回
            }
        }
        return retBuild;
    }

    static class logicRelation {                                //存储逻辑地址关系，对应：
        private final int type;                                 //类型，是楼还是房间？
        private final int campusNum;                            //校区，是一校区还是二校区？
        private final int indexOfBuild;                         //所在大楼的地图内序号
        private final int floorNum;                             //所在大楼内的楼层号（仅房间使用）
        private final int indexOfRoom;                          //所在楼层地图内的序号（仅房间使用）

        public logicRelation(int campusNum, int indexOfBuild, int floorNum, int indexOfRoom) {//房间
            this.type = ROOM;
            this.campusNum = campusNum;
            this.indexOfBuild = indexOfBuild;
            this.floorNum = floorNum;
            this.indexOfRoom = indexOfRoom;
        }

        public logicRelation(int campusNum, int indexOfBuild) {//楼
            this.type = BUILD;
            this.campusNum = campusNum;
            this.indexOfBuild = indexOfBuild;
            this.floorNum = NULL;
            this.indexOfRoom = NULL;
        }

        public int getType() {
            return type;
        }

        public int getCampusNum() {
            return campusNum;
        }

        public int getIndexOfBuild() {
            return indexOfBuild;
        }

        public int getFloorNum() {
            return floorNum;
        }

        public int getIndexOfRoom() {
            return indexOfRoom;
        }
    }
}
