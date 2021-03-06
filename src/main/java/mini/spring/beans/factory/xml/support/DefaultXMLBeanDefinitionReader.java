package mini.spring.beans.factory.xml.support;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.ConstructorArgument;
import mini.spring.beans.PropertyValue;
import mini.spring.beans.factory.BeanFactory;
import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.config.TypedStringValue;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.GenericBeanDefinition;
import mini.spring.beans.factory.xml.XMLBeanDefinitionReader;
import mini.spring.context.annotation.ClassPathBeanDefinitionScanner;
import mini.spring.core.io.Resource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


/**
 * 读取XML并创建对应的BeanDefinition
 */
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
    private static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    private static final String TYPE_ATTRIBUTE = "type";


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
                if ("component-scan".equals(ele.getName())) {
                    String basePackage = ele.attributeValue("base-package");
//                    PackageResourceLoader loader = new PackageResourceLoader();
//                    loader.getResources(basePackage);
                    ClassPathBeanDefinitionScanner scanner =
                            new ClassPathBeanDefinitionScanner(beanDefRegistry);
                    scanner.doScan(basePackage);

                } else if ("bean".equals(ele.getName())) {
                    String id = ele.attributeValue(ID_ATTRIBUTE);
                    String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                    String scope = ele.attributeValue(SCOPE);
                    BeanDefinition bd = new GenericBeanDefinition(id, beanClassName, scope);
                    registerBeanDefinition(bd.getId(), bd);
                    this.parsePropertyElement(ele, bd);
                    this.parseConstructorElement(ele, bd);
                }
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
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        beanDefRegistry.registerBeanDefinition(beanId, beanDefinition);
    }


    /**
     * 解析<constructor-arg>标签，注册相关信息到BeanDefinition
     *
     * @param beanEle <bean>标签和其中的所有内容
     * @param bd      bean定义
     */
    private void parseConstructorElement(Element beanEle, BeanDefinition bd) {
        Iterator constructors = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (constructors.hasNext()) {
            Element constructor = (Element) constructors.next();
            parseConstructorArgElement(constructor, bd);
        }
    }

    private void parseConstructorArgElement(Element constructorEle, BeanDefinition bd) {
        String typeAttr = constructorEle.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = constructorEle.attributeValue(NAME_ATTRIBUTE);
        Object value = parsePropertyValue(constructorEle, bd, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (typeAttr != null && typeAttr.length() != 0) {
            valueHolder.setType(typeAttr);
        }
        if (nameAttr != null && nameAttr.length() != 0) {
            valueHolder.setName(nameAttr);
        }
        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    /**
     * 解析<property>标签，注册相关信息到BeanDefinition
     *
     * @param beanElem <bean>标签
     * @param bd       BeanDefinition
     */
    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator properties = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (properties.hasNext()) {
            Element propElem = (Element) properties.next();
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

    /**
     * 解析出标签定义对应的值
     *
     * @param ele      标签
     * @param bd       bean定义
     * @param propName 属性名称
     * @return
     */
    private Object parsePropertyValue(Element ele, BeanDefinition bd, String propName) {
        String elementName = (propName != null) ? "<property> element for property '" + propName + "'"
                : "<constructor-arg> element";
        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
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
