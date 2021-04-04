

/**学生类的基础架子.*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Student {

  Position position; //todo 凑数的位置类
  private double walkSpeed;
  Map currentMap;
  Path[] pathsToGo;
  int numberOfPath;

  /**
   *
   * @param position   学生位置
   * @param currentMap 所处地图
   * @param pathsToGo  即将走过的路径
   */
  public Student(Position position, Map currentMap, Path[] pathsToGo){
    this.position = position;
    this.walkSpeed = 60;//初始速度60米每分钟
    this.currentMap = currentMap;
    this.pathsToGo = pathsToGo.clone();
  }
  public Student(){
    this.position = new Position();
    this.walkSpeed = 60;//初始速度60米每分钟
    this.currentMap = null;
    this.pathsToGo = null;
  }
  public void changeSpeed(int newSpeed){
    this.walkSpeed = newSpeed;
  }

  public void setNumberOfPath(int numberOfPath) {
    this.numberOfPath = numberOfPath;
  }

  public void rePosition(Position currentPosition){
    position = currentPosition;
  }
}
