package baidufanyi.com.baidu.translate.demo;
 
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class A {
    private String name;
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
     
    public static void main(String[] args) {
        try{

            Class<A> c=A.class;
            Method mtd = c.getMethod("setName", new Class[]{String.class});
            Object obj = (Object)c.newInstance();
            mtd.invoke(obj, new Object[]{"Erica"});
            System.out.println(((A)obj).getName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}