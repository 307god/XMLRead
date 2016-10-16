package Test;

import DOM4Jtest.test.DOM4JTest;
import DOMtest.test.DOMTest;
import JDOMtest.test.JDOMTest;

import SAXtest.test.SAXTest;

import org.junit.Test;

/**
 * Created by james on 2016/10/15.
 */
public class ParseTest {

    @Test
    public void testPerformance() throws Exception {
        System.out.println("性能测试：");
        long start;
        //测试DOM的性能
        start = System.currentTimeMillis();
        DOMTest.domXmlParser();
        long dom = System.currentTimeMillis() - start;

        //测试SAX的性能
        start = System.currentTimeMillis();
        SAXTest.saxXmlParser();
        long sax = System.currentTimeMillis() - start;

        //测试JDOM的性能
        start = System.currentTimeMillis();
        JDOMTest.jdomXmlParser();
        long jdom = System.currentTimeMillis() - start;

        //测试DOM4J的性能
        start = System.currentTimeMillis();
        DOM4JTest.dom4jXmlParser();
        long dom4j = System.currentTimeMillis() - start;

        System.out.println("DOM:" + dom);
        System.out.println("SAX:" + sax);
        System.out.println("JDOM:" + jdom);
        System.out.println("DOM4J:" + dom4j);
    }
}
