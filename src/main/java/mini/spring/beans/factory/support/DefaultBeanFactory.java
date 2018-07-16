package mini.spring.beans.factory.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.utils.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {


    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";

    private final Map<String, BeanDefinition> beanDefMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory(String path) {
        loadBeanDefinition(path);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefMap.get(beanName);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd = beanDefMap.get(beanId);
        if (bd == null) return null;
        Object bean = null;
        try {
            Class clazz = Class.forName(bd.getBeanClassName());
            bean = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }


    private void loadBeanDefinition(String configFile) {
        InputStream is;

        try {
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Iterator<Element> ite = doc.getRootElement().elementIterator();

            while (ite.hasNext()) {
                Element ele = ite.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                beanDefMap.put(id, new GenericBeanDefinition(id, beanClassName));
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


}
