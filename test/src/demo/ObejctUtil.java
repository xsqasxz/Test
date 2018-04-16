package demo;

/**
 * @Authoc xueshiqi
 * @Date 2018/3/5 15:03
 */
public class ObejctUtil {
    public static Long getStringTransformationLong(Object o){
        if(o==null||"".equals(String.valueOf(o)))
            return 0l;
        else
            return Long.valueOf(String.valueOf(o));
    }
}
