import java.io.File;

/**学生类的基础架子.*/
public class Student {

  Position position; //todo 凑数的位置类
  File imgOfStu;//todo 凑数的图片类
  private int walkSpeed;
  Map currentMap;
  Path[] pathsToGo;

  /**
   *
   * @param position   学生位置
   * @param imgOfStu   学生图片
   * @param currentMap 所处地图
   * @param pathsToGo  即将走过的路径
   */
  public Student(Position position, File imgOfStu, Map currentMap, Path[] pathsToGo){
    this.position = position;
    this.imgOfStu = imgOfStu;//总感觉这两个赋值怪怪的，具体实现留作todo
    this.walkSpeed = 60;//初始速度60米每分钟
    this.currentMap = currentMap;
    this.pathsToGo = pathsToGo;
  }

  public void changeSpeed(int newSpeed){
    this.walkSpeed = newSpeed;
  }
  
  public void rePosition(Position currentPosition){
    position = currentPosition;
  }
}
