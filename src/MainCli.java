import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCli {

  final static Logger logger = LoggerFactory.getLogger(MainCli.class);
  public static void main(String[] args) throws IOException {
    Student JackYang=new Student();
    System.out.println("欢迎来到导航系统1.0版\n你打算干什么呢\n1.导航\n2.查询目前的位置");
    Scanner scanner=new Scanner(System.in);
    String answer= scanner.next();
    switch (answer)
    {
      case "1":
        break;
      case "2":navigation(JackYang);
        break;
    }


  }
  private static void navigation(Student JackYang){
    System.out.println("请输入起点，终点,起点输入0，则以你当前的位置为起点哦");
    HashMap<String,String>UsrData=new HashMap<String,String>();
    String []a=new String[3];
    int i=0;
    Scanner scanner=new Scanner(System.in);
    while (i!=2)
    {
      a[i]= scanner.next();
      logger.debug(a[i]);
      i++;
    }
    UsrData.put("start",a[0]);
    UsrData.put("destination",a[1]);
    System.out.println("请输入导航策略，1代表最短路径，2代表最短时间，3代表途径最短距离，4代表交通工具最短");

    UsrData.put("strategy",scanner.next());
    logger.debug(UsrData.get("strategy"));

//    String NameOfDestination=scanner.next();
//    logger.debug(NameOfDestination);
    if (UsrData.get("destination").equals("0"))
    {
      Route [] myPath=JackYang.position.nowBuilding.getShortestRoute(JackYang.position,toBuilding(UsrData.get("destination")),UsrData.get("strategy"));
    }else
    {
      Route [] myPath=toPosition(UsrData.get("start")).nowBuilding.getShortestRoute(toPosition(UsrData.get("start")),toBuilding(UsrData.get("destination")),UsrData.get("strategy"));
    }
  }
  private static void walk(Route[]routes){

  }
  private static void Inquire(Student JackYang){

    String[]answer= JackYang.currentMap.Inquire(JackYang.position);
    //输出完字符串后要给用户一个机会选择干啥，就是这些最短距离。
  }
  private static Building toBuilding(String name){
    int []a={1,2};
    double []b={2.0,3.0};
    Exit ex= new Exit();
    Building test= new Building("我是测试",1,a,b,ex);
    return test;
  }
  private static Position toPosition(String name){
    return new Position();
  }
}
