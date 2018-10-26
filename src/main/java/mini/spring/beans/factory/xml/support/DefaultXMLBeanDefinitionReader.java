package mini.spring.beans.factory.xml.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.PropertyValue;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.config.TypedStringValue;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.GenericBeanDefinition;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.bean.BeanElement;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


public class DefaultXMLBeanDefinitionReader implements XMLBeanDefinitionReader {

    private Logger logger = LoggerFactory.getLogger(DefaultXMLBeanDefinitionReader.class);

    private BeanDefinitionRegistry beanDefRegistry;

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String SCOPE = "scope";
    private static final String SCOPE_ATTRIBUTE = "scope";
    private static final String PROPERTY_ELEMENT = "property";
    private static final String REF_ATTRIBUTE = "ref";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String NAME_ATTRIBUTE = "name";

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
            while (ite.hasNext()) { // 循环读出<bean>标签
                Element ele = ite.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                String scope = ele.attributeValue(SCOPE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName, scope);
                registerBeanDefinition(bd);
                this.parsePropertyElement(ele, bd);
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

    /**
     * 解析<property>标签，注册相关信息到BeanDefinition
     *
     * @param beanElem <bean>标签
     * @param bd       BeanDefinition
     */
    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator ite = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (ite.hasNext()) {
            Element propElem = (Element) ite.next();
            String propName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (propName == null || propName.length() == 0) {
                logger.error("Tag 'property' must have a 'name' attribute [Bean={}]", propName);
                return;
            }

            Object val = parsePropertyValue(propElem, bd, propName);
            PropertyValue pv = new PropertyValue(propName, val);
            bd.getPropValues().add(pv);
        }
    }

    private Object parsePropertyValue(Element ele, BeanDefinition bd, String propName) {
        String elementName = (propName != null) ? "<property> element for property '" + propName + "'"
                : "<constructor-arg> element";
        boolean hasRefAttribute = (ele.attribute(NAME_ATTRIBUTE) != null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);
        if (hasRefAttribute) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (refName == null || refName.length() == 0) {
                logger.error(propName + "contains empty 'ref' attribute");
            }
            return new RuntimeBeanReference(refName);
        } else if (hasValueAttribute) {
            return new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
        } else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }

}
