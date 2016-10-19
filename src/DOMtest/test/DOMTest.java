package DOMtest.test;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by james on 2016/10/2.
 * 应用DOM方式解析XML
 */
public class DOMTest {

    public static DocumentBuilder getDocumentBuilder() {
        // 创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // 创建一个DocumentBuilder的对象
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return db;
    }

    /**
     * 解析xml文件
     */
    public static void domXmlParser() {
        try {
            // 通过DocumentBuilder对象的parser方法加载book.xml文件到当前项目下
            Document document = getDocumentBuilder().parse("book.xml");
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
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成xml文件
     */
    public static void domXmlCreate() {
        DocumentBuilder db = getDocumentBuilder();
        Document document = db.newDocument();
        document.setXmlStandalone(true);
        Element bookstore = document.createElement("bookStore");
        //向bookstore根节点中添加子节点book
        Element book = document.createElement("book");
        Element name = document.createElement("name");
        name.setTextContent("小王子");
        book.appendChild(name);
        book.setAttribute("id", "1");
        //将book节点添加到bookstore根节点中
        bookstore.appendChild(book);
        //将bookstore节点（已经包含了book）添加到dom树中
        document.appendChild(bookstore);

        //创建TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();
        try {
            //创建Transformer对象
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(document), new StreamResult(new File("books1.xml")));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //创建DOMTest对象
        DOMTest test = new DOMTest();
        //调用解析方法，解析xml文件
//        test.domXmlParser();
        //调用生成方法，生成xml文件
        test.domXmlCreate();
    }
}
