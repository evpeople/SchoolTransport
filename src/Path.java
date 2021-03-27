/**
 * 道路类，不同于路径类.
 */
public class Path {
    private int length;
    private double crowdDegree;
    private boolean isBike;//自行车道为1
    Building start;
    Building end;

    /**
     * 构造器方法.
     *
     * @param length      路径长度
     * @param crowdDegree 拥挤度
     * @param isBike      是否为自行车道
     * @param start       起点建筑
     * @param end         终点建筑
     */
    public Path(int length, boolean isBike, Building start, Building end){
        this.length = length;
        this.crowdDegree = 0.0;
        this.isBike = isBike;
        this.start = start;
        this.end = end;
    }

    /**
     * 目前暂定将路径分为几个不同的type，分别有自己不同的拥挤度函数，到时候就可以由functionOfCrowd根据type抉择算法了
     */
    public void setCrowdDegree(int timeHour, int timeMin, int typeOfPath){
        this.crowdDegree = functionOfCrowd(timeHour, timeMin, typeOfPath);//TODO 拥挤度随时间变化的函数
    }

    private double functionOfCrowd(int timeHour, int timeMin, int typeOfPath) {
        return 0.0;
    }

}
