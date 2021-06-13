package com.CampusNavigation.Map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Logic {
    public final static int NULL = 0;
    public final static int BUILD = 1;
    public final static int ROOM = 2;

    private final Map campusOne;
    private final Map campusTwo;
    private HashMap<String, HashSet<logicRelation>> searcher = new HashMap<>();

    public Logic(Map campusOne, Map campusTwo) {
        this.campusOne = campusOne;
        this.campusTwo = campusTwo;
        //todo
    }

    public HashSet<Building> findPhyAddr(String logicAddr) {
        HashSet<Building> retBuild = new HashSet<>();
        HashSet<logicRelation> currFound = this.searcher.get(logicAddr);
        logicRelation curr;
        if(currFound == null) return null;

        Iterator<logicRelation> iterator = currFound.iterator();
        Building searchedBuild;
        while(iterator.hasNext()) {
            curr = iterator.next();
            if(curr.getCampusNum() == 1)
                searchedBuild = this.campusOne.getBuilding(curr.getIndexOfBuild());
            else
                searchedBuild = this.campusTwo.getBuilding(curr.getIndexOfBuild());
            if(curr.getType() == BUILD) {
                retBuild.add(searchedBuild);
            }
            else {
                retBuild.add((((SpecificBuild)searchedBuild).getMapOfFloor(curr.getFloorNum()))
                        .getBuilding(curr.getIndexOfRoom()));
            }
        }
        return retBuild;
    }

    static class logicRelation {
        private final int type;
        private final int campusNum;
        private final int indexOfBuild;
        private final int floorNum;
        private final int indexOfRoom;

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
