package Graph;
//
//enum edgeType{
//    bikeOk;
//
//        }
public class edge {
    private dot start=null;
    private dot end=null;
    private boolean bikeOk=false;

    public edge(dot start,dot end){
        this.start=start;
        this.end=end;
    }
    public void setBikeOk(boolean bikeOk) {
        this.bikeOk = bikeOk;
    }

    public boolean isBikeOk() {
        return bikeOk;
    }
}
