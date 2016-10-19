package SAXtest.test;

import Entity.Book;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by james on 2016/10/13.
 */
public class SAXParserHandler extends DefaultHandler{

    int bookIndex = 0;

    String value = null;

    Book book = null;

    private ArrayList<Book> bookList = new ArrayList<Book>();

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    /**
     * 用来标识解析开始
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("SAX解析开始");
    }

    /**
     * 用来标识解析结束
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("SAX解析结束");
    }

    /**
     * 用来遍历xml文件的开始标签
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //调用DefaultHandler类的startElement方法
        super.startElement(uri, localName, qName, attributes);
        //开始解析book元素的属性
        if (qName.equals("book")){
            //创建一个book对象
            book = new Book();

            bookIndex++;
            System.out.println("==========结束遍历第" + bookIndex + "本书的内容==========");
            //已知book元素下属性的名称，根绝属性名称获取属性值
            String value = attributes.getValue("id");
            System.out.println("book的属性值是：" + value);

            //不知道book元素下属性的名称以及个数，如何获取属性名以及属性值
            int num = attributes.getLength();
            for (int i = 0; i < num; i++){
                System.out.print("book元素的第" + (i + 1) + "个属性名是" + attributes.getQName(i));
                System.out.println("---属性值是：" + attributes.getValue(i));
                if (attributes.getQName(i).equals("id"))
                    book.setId(attributes.getValue(i));
            }
        } else if (!qName.equals("book") && !qName.equals("bookstore")){
          System.out.print("节点名是：" + qName + "---节点值是：");
        }
    }

    /**
     * 用来遍历xml文件的结束标签
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //调用DefaultHandler类的endElement方法
        super.endElement(uri, localName, qName);
        //判断是否针对一本书已经遍历结束
        if (qName.equals("book")){
            bookList.add(book);
            book = null;
            System.out.println("==========结束遍历第" + bookIndex + "本书的内容==========");
        }//qName.equals("name")时，向book对象中setName
        else if (qName.equals("name"))
            book.setName(value);
        else if (qName.equals("author"))
            book.setAuthor(value);
        else if (qName.equals("year"))
            book.setYear(value);
        else if (qName.equals("price"))
            book.setPrice(value);
        else if (qName.equals("language"))
            book.setLanguage(value);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        value = new String(ch, start, length);
        if (!value.trim().equals(""))
            System.out.println(value);
    }
}
