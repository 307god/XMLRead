package SAXtest.test;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Created by james on 2016/10/13.
 */
public class SAXTest {

    /**
     * 解析xml文件
     */
    public static void saxXmlParser() {
        //获取SAXParserFactory的实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            //通过factory获取SAXParser实例
            SAXParser parser = factory.newSAXParser();
            //创建SAXParseHandler对象
            SAXParserHandler handler= new SAXParserHandler();
            parser.parse("book.xml", handler);
            System.out.println("共有" + handler.getBookList().size() + "本书");
            for (Book book : handler.getBookList()) {
                System.out.println(book.getId());
                System.out.println(book.getName());
                System.out.println(book.getAuthor());
                System.out.println(book.getYear());
                System.out.println(book.getYear());
                System.out.println(book.getPrice());
                System.out.println(book.getLanguage());
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //创建DOMTest对象
        SAXTest test = new SAXTest();
        //调用解析方法，解析xml文件
        test.saxXmlParser();
    }
}
