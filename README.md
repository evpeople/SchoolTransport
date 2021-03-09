
## 
在idea中使用git

1.  新建分支： 右下角可以点击新建分支
2.  也可以在上方的git处，点击新建分支

与github连接
1.  点击git，然后github，然后登录，可以直接登录也可以用token，直接登录简单

提交新分支的时候，不用管远端是否有对应分支，左侧commit处，可以直接提交并push到远端，若远端不存在新分支，则会自动新建一个


可以直接点击git- github view pr 查看当前的还没merge的pr，然后选择，会在左下角处出现更改过的的文件，点击之后直接出现与原文件的差别

同样也可以在此处创建 pr 创建pr时的base branch是合并到哪一个分支

对于pr 而言，创建了pr之后，只到pr 被合并前，当前分支的新增的内容（也就是新push进去的内容），都会追加当这个pr当中，所以对当前代码做微调，不用创建新的pr

建议：个别的拼写错误等，可以考虑在原pr分支中更改，如果是debug，建议新开分支，如 graph_debug 分支，作为graph 分支的新开分支，在新开分支做debug工作，然后把debug结束的代码，merge到graph （这一步可以在本地执行，也就是debug分支不一定要推到远端）

同时，鉴于我们的私人仓库没得高级功能，所以，如果在idea里创建的pr之后，需要再打开view pr， 点击自己的pr，然后再左上角指定review和 assignees。
## 画图
 1. 传给Ver 的是dpp
 2. 命名： Build 结尾是建筑物，无build则是服务设施
    1. 食堂  canteen
    2. 教学楼1 teachBuild1
     3. 理发店 barber
     4. 操场入口 playground
     5. 文具商店 stationery
     6. 宿舍楼1 dormBuild
     7. 实验楼 labBuild
     8. 眼镜店 optical
     9. 学生活动中心 stuBuild
     10. 教学楼2 teachBuild2
     11. 教学楼3 teachBuild3
     12. 宿舍楼2 dormBuild4
     13. 宿舍楼3 dormBuild5
     14. 图书馆 libBuild
     15. 办公楼 officeBuild
     16. 咖啡厅 cafe
     17. 水果店 fruitery
     18. 杂货店 grocery
     19. 足球场 soccer_field
     20. 游泳馆 swimming_pool
     21. 校医院 hospital
     22. 路口  crossing
     23. 乙子钟 clock
     24. 公交车站 bus_stop 
     25. 班车站 scheduled_bus_stop
     26. 校门口 gate
     27. 湖 lake
     28. 小树林 forest


层 
 1. 楼梯口  stair
 2. 教室 classroom
 3. 办公室 office
 4. 研讨间 workshop
 5. 阳台 balcony
 6. 打水房 water_room
 7. 会议厅 meeting_room
 8. 男厕所 maleWc
 9. 女厕所 femaleWc
 10. 报告厅 lectureHall
 11. 健身房 gym


# 还没写一点点方法

# ==负载均衡==

# ==–这时在考虑不同拥挤度的情况下时间最短；==

# ==食堂==

 ==暂时继承楼==

建筑物类

1. 楼，房间，服务设施都来自这个类
   1. 都有物理地址
      1. 坐标
         1. 就是两个精心算出的自然数 x y（目的是防止道路交叉 
         2. gui的坐标
      2. 和校区
      3. 名字
   2. 图片
      1. 用同样的gui 类 画
         1. 画的是详细信息的界面
   3. 构造器函数
      1. 通过参数传入坐标 （两个整形数据 一个字符串类型
      2. 通过参数传入校区 （一个枚举类型
      3. 通过传入的参数（确定楼的类型，比如教学楼，食堂，教室什么的
      4. new 一个gui类，参数是5 传入的参数
   4. 显示详细信息
      1. 一个switch 根据5的类型

楼的类型，


1. 继承自建筑物

2. 层数

3. 图的数组（每个元素是一个楼层的图

4. 道路的数组

5. 方法

   1. 初始化的方法

      1. 上面的属性全部初始化

         1. 通过参数传入层数（一个整形数据
         2. 通过层数，生成一个楼层的图的数组

            1. 调用图的构造器方法
   2. 从楼的入口到某层的最短路径

      1. 加一个楼梯高度
      2. 
    3. 不同的楼层之间最短路径 （只用加减
    4. 
    5. 

      ​      

房间的类型，tips ：通过楼的对象生成房间的对象

1. 继承建筑物类
2. 属于哪栋楼
3. 层数

服务设施

1. 根据是否满足经过楼的出入口，将服务设施分别做成房间or楼



地图  ==既是地图 也是层==   （一共5 个图  一个校区一种图 楼层三种图，随机选择一种

1. 成员1.
   1. 一个手动写的 邻接矩阵，以道路（其中有长度，有拥挤度）为元素
2. 成员2
   1. 一个数组，
      1. 如下 a[1]=教学楼一号 然后 邻接矩阵 1，3 代表从教学楼1 号到某个位置
3. 成员3
   1. 数组的长度
4. 方法
   1. 构造器
      1. 传入整形作为数组长度
      2. 传入枚举变量 表示是大地图还是小地图（层）
   2. gui  参数是以上的三个成员
      1. 缩略图的界面
   3. 求最短路径的算法
   4. 广度优先搜索的
      1. 参数 （建筑物 类   距离 浮点型
      2. 返回 建筑物 的数组 （搜索到所有建筑

算法策略类

1. 类方法（统筹性质的算法）
   1. 参数  一个建筑类（终点）， 学生==（一个图（起点所在图） 学生给的（图）， 学生的位置（起点））， 
      1. 结果是最短路径
      2. tips 输入的x y 只能是在路途中更改的时候用， 不能手动输入 x y的值来导入
   2. 具体描述
      1. 输入终点，
         1. 通过字符串
            1. 通过某方法得到建筑
         2. 直接点击一个建筑（?）
      2. 调用统筹性质算法
         1. 传入参数如上所示
            1. 先分析终点
               1. switch 一个枚举 
                  1. switch 一个起点的枚举
                     1. 然后再根据两次枚举的结果进行统筹
                     2. 例子如下
                        1. 调用地图的算法
                        2. 教室的话
                           1. 先 get 教室所在楼
                           2. 调用地图的算法求到这个楼的最短
                           3. 调用楼的入口到某层长度的最短路径的方法
                           4. 调用楼的层的地图的，求楼梯口到房间的最短路径
2. 类方法（统筹性质的算法）
   1. 参数 （ 学生  和距离
      1. 作用时寻找学生附近的东西
      2. switch  位置是道路坐标
         1. 按照道路的长度，对两头的建筑搜索，搜索时采用的距离减去学生到两头的距离
            1. 实际调用地图的算法
   2. 参数 （建筑 距离
      1. 作用是寻找建筑附近的东西


==可视化 行走过程==

道路（其实是图的一条边   也是楼梯

1. 长度
2. 拥挤度（按照时间改变，比如下课时间很挤，去吃饭的时候，食堂很挤
   1. 是一个以时间为参数的函数
3. 是否可通自行车
4. 起点（起始建筑物
5. 终点（终止建筑物
6. 方法
   1. 构造器方法



位置类（仅学生用

 	1.	在道路
 	  	1.	则包含当前所在道路，和距离某一端点的距离
 	2.	在建筑物
 		1.	则就是当前建筑物

学生

1. 位置  类
   1. 
2. 图片
3. 步行速度
4. 当前所在的地图
5. 要走的路径（默认是空集，）（有了数据之后，是道路的数组
6. 方法
   1. 构造器
   2. 更新自身位置（更新自身位置类的数据
   3. 获取当前位置

车类

1. 速度

2. 公交车
   1. 间隔发车的时间
   2. 首发时间
   3. 停运时间
3. 班车
   1. 开车时间的数组 date类型
4. 自行车

导航策略

日志

1. 含时间
2. 字符串





逻辑地址（哈希表

1. 逻辑地址 做键
2. 物理地址 做值

TIPS　位置查找，使用数据库查找，如教三楼111教室，在数据库主键中搜索教三

​	或者让用户来查找

# 模块

1. 地图的控件
   1. 可拓展性
      1. 楼层的地图
      2. 整个校园的地图
2. 后端的类
   1. 楼
   2. 路
   3. 房间
   4. 图
      1. 一个以道路为元素的邻接矩阵
3. 算法
   1. 四个导航其实是在同宗的
   2. 查询算法
      1. 对图做广度优先











# new problem

1. ~~层 每一层的显示~~
2. 
3. ~~把点击的建筑物设为终点~~
4. 数据库，构造器构造后存入数据库，再次打开时，从数据库中获取数据，
   1. solve 首次先把所有的数据存到数据库里，以后的构造器都是从数据库读取数据

# GUI类

## 开始模拟导航

1. 传输一个intent 包含 当前的起点，终点，和当前图，当前学生
2. 调运算法策略类最短路径
3. 

## 搜索附近

1. intent当前的起点，终点，和当前图
2. 调用算法策略类

## 建筑物

1. 点击建筑物，
   1. 将当前建筑物的信息放到终点的text里
   2. 在下面跳一个小窗口出来，显示详细信息
   3. 在跳一个，显示是否进入楼内

## 切换校区

​	1.	刷新背景图





# 开发顺序

1. 先把类的变量和函数头写出来
2. GUI 控件
3. 数据库 单独
4. 算法模块



我的想法

​	咱们先把地图设计好 

1. 5个地图（校区和楼层，这5个图又以下的图重复n次生成
   1. 具体指标
   2. 两个地图中，各有个建筑，
   3. ![img](https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Frucht_graph.dot.svg/200px-Frucht_graph.dot.svg.png)
   4. 剩下三个地图 
   5. 校园地图不少于100条边，
   6. 楼层地图15--20 左右
   7. 提交 坐标 的数组，原点在图中任取
   8. 提交邻接矩阵  
2. 就先行处理数据库
   1. 





DDL 

1. 3月14号 五个地图设计完成
