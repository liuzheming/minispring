package mini.spring.beans.factory.xml.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.GenericBeanDefinition;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


public class DefaultXMLBeanDefinitionReader implements XMLBeanDefinitionReader {

    private BeanDefinitionRegistry beanDefRegistry;

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE = "scope";

    public DefaultXMLBeanDefinitionReader(BeanFactory beanDefRegistry) {
        this.beanDefRegistry = (BeanDefinitionRegistry) beanDefRegistry;
    }

    @Override
    public void loadBeanDefinition(Resource resource) {
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Iterator<Element> ite = doc.getRootElement().elementIterator();
            while (ite.hasNext()) {
                Element ele = ite.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                String scope = ele.attributeValue(SCOPE);
                registerBeanDefinition(new GenericBeanDefinition(id, beanClassName, scope));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        beanDefRegistry.registerBeanDefinition(beanDefinition);
    }


}
