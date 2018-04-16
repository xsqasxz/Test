package test;

import java.util.*;

/**
 * @Authoc xueshiqi
 * @Date 2018/4/16 11:18
 */
public class 对集合排序比较 {

    public static void main(String [] s){
        List<String> list = new ArrayList();
        list.add("5");
        list.add("3");
        list.add("4");
        list.add("1");
        list.add("2");
//        getSort(list);
        getSort18V(list);
        list.forEach( t -> System.out.println(t));
    }

    public static Collection getSort(List list){
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return null;
    }

    /**
     * jdk1.8对list排序
     * @param list
     * @return
     */
    public static Collection getSort18V(List list){
        list.sort(Comparator.comparing(String :: toString));
        return list;
    }
}
