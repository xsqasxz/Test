package demo;

import org.dom4j.DocumentException;

import java.math.BigDecimal;

public class test {
    public static void main(String[] arg) throws DocumentException {
        BigDecimal zero = new BigDecimal(0);
        BigDecimal aa = new BigDecimal(-1);
        if(zero.compareTo(aa)>=0){
            System.out.println(1111111);
        }

    }
}
