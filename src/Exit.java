/**
 * 出口类，实际上是建筑物，之所以新写一个类，是为了避免类的成员变量是自己，同时避开静态的限制.
 */
public class Exit {
    double[] mathCoordinate;
    int[] guiCorrdinate;
    int schoolNum;
    int floor;
    String nameOfExit;

    /**
     *
     * @param mathCoordinate 连接表的坐标
     * @param guiCorrdinate gui坐标
     * @param schoolNum 所在校区
     * @param nameOfBuilding 出口的名字
     */
    public Exit(double[] mathCoordinate, int[] guiCorrdinate, int schoolNum, String nameOfBuilding) {
        this.mathCoordinate = mathCoordinate.clone();
        this.guiCorrdinate = guiCorrdinate.clone();
        this.schoolNum = schoolNum;
        this.floor = 0;
        this.nameOfExit = nameOfBuilding;
    }


}
