package Graph;

public class dot {
    private String name="";
    private double x;
    private double y;
    private String position="";
    private int index=0;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public dot(String position, double x, double y){
        this.position=position;
        this.x=x;
        this.y=y;
    }


}

