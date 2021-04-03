package myTools;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyMatcher {
    private String mainString=null;
    public MyMatcher(String mainString){
        this.mainString=mainString;
    }
    public String theFirst(String reExp){
        Pattern pattern=Pattern.compile(reExp);
        Matcher matcher=pattern.matcher(mainString);
        if(matcher.find()) return matcher.group(0);
        else return null;
    }
    public ArrayList<String> theAll(String reExp){
        ArrayList<String> ans=new ArrayList<>();
        Pattern pattern=Pattern.compile(reExp);
        Matcher matcher=pattern.matcher(mainString);
        int i=0;
        while(matcher.find(i)) {
           ans.add(i,matcher.group(i));
        }
        return ans;
    }
    public void setMainString(String string){this.mainString=string;}
}
