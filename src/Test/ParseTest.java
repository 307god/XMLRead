package Test;

import DOM4Jtest.test.DOM4JTest;
import JDOMtest.test.JDOMTest;
import SAXtest.test.Book;
import SAXtest.test.SAXParserHandler;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * Created by james on 2016/10/15.
 */
public class ParseTest {

    public void domXmlParser() {
        // 创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // 创建一个DocumentBuilder的对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 通过DocumentBuilder对象的parser方法加载book.xml文件到当前项目下
            Document document = db.parse("book.xml");
            // 获取所有book节点的集合
            NodeList bookList = document.getElementsByTagName("book");
            // 遍历每一个book节点
            for (int i = 0; i < bookList.getLength(); i++){
                System.out.println("==========下面开始遍历第" + (i + 1) + "本书的内容==========");
                // 通过item(i)方法获取一个book节点，nodeList的索引值从0开始
                Node book = bookList.item(i);
                // 获取book节点的所有属性集合
                NamedNodeMap attrs = book.getAttributes();
                System.out.println("第" + (i+1) + "本书共有" + attrs.getLength() + "个属性");
                // 遍历book的属性
                for (int j = 0; j < attrs.getLength(); j++){
                    // 通过item(index)方法获取book节点的某一个属性
                    Node attr = attrs.item(j);
                    // 获取属性名
                    System.out.print("属性名： " + attr.getNodeName());
                    // 获取属性值
                    System.out.println("--属性值" + attr.getNodeValue());
                }

//                // 前提：已经知道book节点有且只有1个id属性
//                // 将book节点进行强制类型转换，转换成Element类型
//                Element book = (Element) bookList.item(i);
//                // 通过getAttribute("id")方法获取属性值
//                String attrValue = book.getAttribute("id");
//                System.out.println("id属性的属性值为" + attrValue);

                // 解析book节点的子节点
                NodeList childNodes = book.getChildNodes();
                // 遍历childNodes获取每个节点的节点名和节点值
                System.out.println("第" + (i + 1) + "本书共有" +
                        childNodes.getLength() + "个子节点");
                for (int k = 0; k < childNodes.getLength(); k++){
                    // 区分出text类型的node以及element类型的node
                    if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        // 获取Element类型节点的节点名
                        System.out.print("第" + (k + 1) + "个节点的节点名：" +
                                childNodes.item(k).getNodeName());
                        // 获取Element类型节点的节点值
                        System.out.println("--节点值是：" + childNodes.item(k).getFirstChild().getNodeValue());
                        //System.out.println("--节点值是：" + childNodes.item(k).getTextContent());
                    }
                }
                System.out.println("==========结束遍历第" + (i + 1) + "本书的内容==========");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saxXmlParser() {
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

    public void jdomXmlParser() {
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
            org.jdom.Document document = saxBuilder.build(isr);
            //4/通过document对象获取xml文件的根节点
            Element rootElement = document.getRootElement();
            //5/获取根节点下的子节点的List集合
            List<Element> bookList = rootElement.getChildren();
            //继续进行解析
            for (Element book : bookList) {
                JDOMtest.test.Book bookEntity = new JDOMtest.test.Book();
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
                JDOMTest.getBooksList().add(bookEntity);
                bookEntity = null;
                System.out.println(JDOMTest.getBooksList().size());
                System.out.println(JDOMTest.getBooksList().get(0).getId());
                System.out.println(JDOMTest.getBooksList().get(0).getName());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dom4jXmlParser() {
        //解析book.xml文件
        //创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        //通过reader对象的read方法加载book.xml文件
        try {
            //通过reader对象的read方法加载book.xml文件，获取document对象
            org.dom4j.Document document = reader.read(new File("book.xml"));
            //通过document对象获取根节点bookStore
            org.dom4j.Element bookStore = document.getRootElement();
            //通过element对象的element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            //遍历迭代器、获取根节点中的信息（书籍）
            while (it.hasNext()) {
                DOM4Jtest.test.Book bookEntity = new DOM4Jtest.test.Book();
                System.out.println("=====开始遍历某一本书=====");
                org.dom4j.Element book = (org.dom4j.Element) it.next();
                //获取book的属性名以及属性值
                List<org.dom4j.Attribute> bookAttrs = book.attributes();
                for (org.dom4j.Attribute attr : bookAttrs) {
                    System.out.println("属性名：" + attr.getName() + "---属性值：" + attr.getValue());
                    if (attr.getName().equals("id")) {
                        bookEntity.setId(attr.getValue());
                    }
                }
                Iterator itt = book.elementIterator();
                while (itt.hasNext()) {
                    org.dom4j.Element bookChild = (org.dom4j.Element) itt.next();
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
                DOM4JTest.getBookList().add(bookEntity);
                bookEntity = null;
                System.out.println(DOM4JTest.getBookList().size());
                System.out.println(DOM4JTest.getBookList().get(0).getId());
                System.out.println(DOM4JTest.getBookList().get(0).getName());

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPerformance() throws Exception {
        System.out.println("性能测试：");
        long start;
        //测试DOM的性能
        start = System.currentTimeMillis();
        domXmlParser();
        long dom = System.currentTimeMillis() - start;

        //测试SAX的性能
        start = System.currentTimeMillis();
        saxXmlParser();
        long sax = System.currentTimeMillis() - start;

        //测试JDOM的性能
        start = System.currentTimeMillis();
        jdomXmlParser();
        long jdom = System.currentTimeMillis() - start;

        //测试DOM4J的性能
        start = System.currentTimeMillis();
        dom4jXmlParser();
        long dom4j = System.currentTimeMillis() - start;

        System.out.println("DOM:" + dom);
        System.out.println("SAX:" + sax);
        System.out.println("JDOM:" + jdom);
        System.out.println("DOM4J:" + dom4j);
    }
}
