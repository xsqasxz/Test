package demo;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.swing.text.Style;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 * @Authoc xueshiqi
 * @Date 2017/9/21 14:05
 */
public class TestXml {


    public static Document parse() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("C:\\Users\\darendai\\Desktop\\main_menu.xml"));
        return document;
    }

    public static void main(String[] arg) throws DocumentException {
        bar(parse());
    }
    public static void bar(Document document) throws DocumentException {

        Element root = document.getRootElement();

        // iterate through child elements of root
//        for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
//            Element element = it.next();
////            System.out.println(element.getStringValue());
//            System.out.println(element.attributeValue("caption"));
//            // do something
//        }

        // iterate through child elements of root with element name "foo"
        for (Iterator<Element> it = root.elementIterator("value"); it.hasNext();) {
            Element foo = it.next();
            foo.getQName("caption").toString();
            Attribute name = foo.attribute("caption");
//            System.out.println(foo.getQName("key").getQualifiedName());
            System.out.println(foo.getStringValue());
        }

//        FileWriter out = null;
//        try {
//            out = new FileWriter("C:\\Users\\darendai\\Desktop\\main_menu1.xml");
//            document.write(out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
