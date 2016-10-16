package DOM4Jtest.test;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by james on 2016/10/15.
 */
public class DOM4JTest {

    private static ArrayList<Book> bookList = new ArrayList<Book>();

    public static ArrayList<Book> getBookList() {
        return bookList;
    }

    public static void setBookList(ArrayList<Book> bookList) {
        DOM4JTest.bookList = bookList;
    }

    /**
     * 解析xml文件
     */
    public static void dom4jXmlParser() {
        //解析book.xml文件
        //创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        //通过reader对象的read方法加载book.xml文件
        try {
            //通过reader对象的read方法加载book.xml文件，获取document对象
            Document document = reader.read(new File("book.xml"));
            //通过document对象获取根节点bookStore
            Element bookStore = document.getRootElement();
            //通过element对象的element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            //遍历迭代器、获取根节点中的信息（书籍）
            while (it.hasNext()) {
                Book bookEntity = new Book();
                System.out.println("=====开始遍历某一本书=====");
                Element book = (Element) it.next();
                //获取book的属性名以及属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr : bookAttrs) {
                    System.out.println("属性名：" + attr.getName() + "---属性值：" + attr.getValue());
                    if (attr.getName().equals("id")) {
                        bookEntity.setId(attr.getValue());
                    }
                }
                Iterator itt = book.elementIterator();
                while (itt.hasNext()) {
                    Element bookChild = (Element) itt.next();
                    System.out.println("节点名：" + bookChild.getName() + "---节点值：" + bookChild.getStringValue());
                    if (bookChild.getName().equals("name")) {
                        bookEntity.setName(bookChild.getStringValue());
                    } else if (bookChild.getName().equals("author")) {
                        bookEntity.setAuthor(bookChild.getStringValue());
                    } else if (bookChild.getName().equals("year")) {
                        bookEntity.setYear(bookChild.getStringValue());
                    } else if (bookChild.getName().equals("price")) {
                        bookEntity.setPrice(bookChild.getStringValue());
                    } else if (bookChild.getName().equals("language")) {
                        bookEntity.setLanguage(bookChild.getStringValue());
                    }
                }
                System.out.println("=====结束遍历这一本书=====");
                bookList.add(bookEntity);
                bookEntity = null;
                System.out.println(bookList.size());
                System.out.println(bookList.get(0).getId());
                System.out.println(bookList.get(0).getName());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //创建DOMTest对象
        DOM4JTest test = new DOM4JTest();
        //调用解析方法，解析xml文件
        test.dom4jXmlParser();
    }
}
