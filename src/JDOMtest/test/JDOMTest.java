package JDOMtest.test;

import Entity.Book;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 2016/10/14.
 */
public class JDOMTest {

    private static ArrayList<Book> booksList = new ArrayList<Book>();

    public static ArrayList<Book> getBooksList() {
        return booksList;
    }

    public static void setBooksList(ArrayList<Book> booksList) {
        JDOMTest.booksList = booksList;
    }

    /**
     * 解析xml文件
     */
    public static void jdomXmlParser() {
        //进行对books.xml文件的JDOM解析
        //准备工作
        //1、创建一个SAXBuilder的对象
        SAXBuilder saxBuilder = new SAXBuilder();
        InputStream in = null;
        try {
            //2、创建一个输入流，将xml文件加载到输入流中
            in = new FileInputStream("book.xml");
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");//防止中文乱码问题
            //3、通过saxBuilder的build方法，将输入流加载到saxBuild中
            Document document = saxBuilder.build(isr);
            //4/通过document对象获取xml文件的根节点
            Element rootElement = document.getRootElement();
            //5/获取根节点下的子节点的List集合
            List<Element> bookList = rootElement.getChildren();
            //继续进行解析
            for (Element book : bookList) {
                Book bookEntity = new Book();
                System.out.println("=====开始解析第" + (bookList.indexOf(book) + 1) + "本书=====");
                //解析book的属性集合
                List<Attribute> attrList = book.getAttributes();
//                //知道节点下属性名称时，获取节点值
//                book.getAttribute("id");
                //遍历attrList（针对不清楚book节点下属性的名字及数量）
                for (Attribute attr : attrList) {
                    //获取属性名
                    String attrName = attr.getName();
                    //获取属性值
                    String attrValue = attr.getValue();
                    System.out.println("属性名：" + attrName + "---属性值：" + attrValue);
                    if (attrName.equals("id")) {
                        bookEntity.setId(attrValue);
                    }
                }
                //对book节点的子节点的节点名以及节点值的遍历
                List<Element> bookChilds = book.getChildren();
                for (Element child : bookChilds) {
                    System.out.println("节点名：" + child.getName() + "---节点值：" + child.getValue());
                    if (child.getName().equals("name")) {
                        bookEntity.setName(child.getValue());
                    } else if (child.getName().equals("author")) {
                        bookEntity.setAuthor(child.getValue());
                    } else if (child.getName().equals("year")) {
                        bookEntity.setYear(child.getValue());
                    } else if (child.getName().equals("price")) {
                        bookEntity.setPrice(child.getValue());
                    } else if (child.getName().equals("language")) {
                        bookEntity.setLanguage(child.getValue());
                    }
                }
                System.out.println("=====结束解析第" + (bookList.indexOf(book) + 1) + "本书=====");
                booksList.add(bookEntity);
                bookEntity = null;
                System.out.println(booksList.size());
                System.out.println(booksList.get(0).getId());
                System.out.println(booksList.get(0).getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成xml文件
     */
    public static void jdomXmlCreate() {
        //1、生成一个根节点
        Element rss = new Element("rss");
        //2、为节点添加属性
        rss.setAttribute("version", "2.0");
        //3、生成一个document对象
        Document document = new Document(rss);

        Element channel = new Element("channel");
        rss.addContent(channel);
        Element title = new Element("title");
        title.setText("<<国内最新新闻>>");
        title.addContent(new CDATA("国内最新新闻"));
        channel.addContent(title);

        Format format = Format.getCompactFormat();
        format.setIndent("");
        format.setEncoding("GBK");

        //4、创建XMLOutputter的对象
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat()){
            @Override
            public String escapeElementEntities(String str) {
                //return super.escapeElementEntities(str);
                return str;
            }
        };

        //5、利用outputer将document对象转换成xml文档
        try {
            outputter.output(document, new FileOutputStream(new File("rssnews1.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //创建DOMTest对象
        JDOMTest test = new JDOMTest();
        //调用解析方法，解析xml文件
//        test.jdomXmlParser();
        //调用生成方法，生成xml文件
        test.jdomXmlCreate();
    }
}
