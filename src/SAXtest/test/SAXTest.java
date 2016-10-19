package SAXtest.test;

import Entity.Book;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by james on 2016/10/13.
 */
public class SAXTest {

    /**
     * 解析xml文件
     */
    public static ArrayList<Book> saxXmlParser() {
        //获取SAXParserFactory的实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParserHandler handler = null;
        try {
            //通过factory获取SAXParser实例
            SAXParser parser = factory.newSAXParser();
            //创建SAXParseHandler对象
            handler= new SAXParserHandler();
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
        return handler.getBookList();
    }

    /**
     * 生成xml文件
     */
    public static void saxXmlCreate() {
        ArrayList<Book> bookList = saxXmlParser();
        //生成xml
        //1、创建一个TransformerFactory类的对象
        SAXTransformerFactory tff = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        try {
            //2、通过SAXTransformerFactory对象创建一个TransformerFactory对象
            TransformerHandler handler = tff.newTransformerHandler();
            //3、通过handler对象创建一个Transformer对象
            Transformer tr = handler.getTransformer();
            //4、通过Transformer对象对生成的xml文件进行设置
            //设置xml的编码
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //设置xml的“是否换行”
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            //5、创建一个Result对象
            File f = new File("books2.xml");
            if (!f.exists()){
                f.createNewFile();
            }
            //6、创建Result对象，并且使其与handler关联
            Result result = new StreamResult(new FileOutputStream(f));
            handler.setResult(result);
            //7、利用handler对象进行xml文件内容的编写
            //打开document
            handler.startDocument();
            AttributesImpl attr = new AttributesImpl();
            handler.startElement("", "", "bookstore", attr);
            for (Book book : bookList) {
                attr.clear();
                attr.addAttribute("", "", "id", "", book.getId());
                handler.startElement("", "", "book", attr);
                //创建name节点
                if (book.getName() != null && !book.getName().trim().equals("")){
                    attr.clear();
                    handler.startElement("", "", "name", attr);
                    handler.characters(book.getName().toCharArray(), 0, book.getName().length());
                    handler.endElement("", "", "name");
                }
                //创建year节点
                if (book.getYear() != null && !book.getYear().trim().equals("")) {
                    attr.clear();
                    handler.startElement("", "", "year", attr);
                    handler.characters(book.getYear().toCharArray(), 0, book.getYear().length());
                    handler.endElement("", "", "year");
                }
                //创建author节点
                if (book.getAuthor() != null && !book.getAuthor().trim().equals("")) {
                    attr.clear();
                    handler.startElement("", "", "author", attr);
                    handler.characters(book.getAuthor().toCharArray(), 0, book.getAuthor().length());
                    handler.endElement("", "", "author");
                }
                //创建price节点
                if (book.getPrice() != null && !book.getPrice().trim().equals("")) {
                    attr.clear();
                    handler.startElement("", "", "price", attr);
                    handler.characters(book.getPrice().toCharArray(), 0, book.getPrice().length());
                    handler.endElement("", "", "price");
                }
                //创建language节点
                if (book.getLanguage() != null && !book.getLanguage().trim().equals("")) {
                    attr.clear();
                    handler.startElement("", "", "language", attr);
                    handler.characters(book.getLanguage().toCharArray(), 0, book.getLanguage().length());
                    handler.endElement("", "", "language");
                }

                handler.endElement("", "", "book");
            }
            handler.endElement("", "", "bookstore");

            //关闭document
            handler.endDocument();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //创建DOMTest对象
        SAXTest test = new SAXTest();
        //调用解析方法，解析xml文件
//        test.saxXmlParser()
        //调用生成方法，生成xml文件
        test.saxXmlCreate();
    }
}
