package mini.spring.aop.config;

import mini.spring.aop.aspect.AspectJAfterReturningAdvice;
import mini.spring.aop.aspect.AspectJAfterThrowingAdvice;
import mini.spring.aop.aspect.AspectJBeforeAdvice;
import mini.spring.aop.aspect.AspectJExpressionPointcut;
import mini.spring.beans.BeanDefinition;
import mini.spring.beans.ConstructorArgument;
import mini.spring.beans.PropertyValue;
import mini.spring.beans.factory.config.RuntimeBeanReference;
import mini.spring.beans.factory.support.BeanDefinitionReaderUtils;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.GenericBeanDefinition;
import mini.spring.util.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 负责读取xml文件的Aop相关信息，并生成对应的BeanDefinition
 * <p>
 * Created by lzm on  2018-12-10.
 */
public class ConfigBeanDefinitionParser {


    private static final String ASPECT = "aspect";

    private static final String EXPRESSION = "expression";

    private static final String ID = "id";

    private static final String POINTCUT = "pointcut";

    private static final String POINTCUT_REF = "pointcut-ref";

    private static final String REF = "ref";

    private static final String BEFORE = "before";

    private static final String AFTER = "after";

    private static final String AFTER_RETURNING_ELEMENT = "after-returning";

    private static final String AFTER_THROWING_ELEMENT = "after-throwing";

    private static final String AROUND = "around";

    private static final String ASPECT_NAME_PROPERTY = "aspectName";


    public void parse(Element ele, BeanDefinitionRegistry registry) {
        List<Element> children = ele.elements();
        for (Element child : children) {
            /*if (POINTCUT.equals(localName)) {
				parsePointcut(elt, registry);
			}*/
			/*else if (ADVISOR.equals(localName)) {
				parseAdvisor(elt, registry);
			}*/

            if (ASPECT.equals(child.getName())) {
                parseAspect(ele, registry);
            }
        }
    }


    public void parseAspect(Element aspectEle, BeanDefinitionRegistry registry) {
        String aspectId = aspectEle.attributeValue(ID);
        String aspectName = aspectEle.attributeValue(REF);

        List<Element> elements = aspectEle.elements();

        List<RuntimeBeanReference> beanRefs = new ArrayList<>();
        List<BeanDefinition> bds = new ArrayList<>();

        boolean adviceFoundAlready = false;
        for (int i = 0; i < elements.size(); i++) {
            Element ele = elements.get(i);
            if (isAdviceNode(ele)) {
                if (!adviceFoundAlready) {
                    adviceFoundAlready = true;
                    if (!StringUtils.hasLength(aspectName)) {
                        return;
                    }
                    beanRefs.add(new RuntimeBeanReference(aspectName));
                }
                bds.add(parseAdvice(aspectName, i, aspectEle, ele, registry, bds, beanRefs));
            }
        }

        List<Element> pointcuts = aspectEle.elements(POINTCUT);
        for (Element pointcutElement : pointcuts) {
            parsePointcut(pointcutElement, registry);
        }


    }


    private GenericBeanDefinition parseAdvice(String aspectName, int order, Element aspectElement,
                                              Element adviceElement, BeanDefinitionRegistry registry,
                                              List<BeanDefinition> bds, List<RuntimeBeanReference> beanRefs) {

        GenericBeanDefinition methodDefinition = new GenericBeanDefinition(MethodLocatingFactory.class);
        methodDefinition.getPropValues()
                .add(new PropertyValue("targetBeanName", aspectName));
        methodDefinition.getPropValues()
                .add(new PropertyValue("methodName", adviceElement.attributeValue("method")));
        methodDefinition.setSynthetic(true);

        // create instance factory definition
        GenericBeanDefinition aspectBeanDef = new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectBeanDef.getPropValues().add(new PropertyValue("aspectBeanName", aspectName));
        aspectBeanDef.setSynthetic(true);

        // registry the pointcut
        GenericBeanDefinition adviceDef = createAdviceDefinition(adviceElement, registry, aspectName,
                order, methodDefinition, aspectBeanDef, bds, beanRefs);
        adviceDef.setSynthetic(true);

        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);

        return adviceDef;
    }

    private boolean isAdviceNode(Element node) {
        String name = node.getName();
        return POINTCUT.equals(name) || AFTER.equals(name) || AFTER_THROWING_ELEMENT.equals(name)
                || AFTER_RETURNING_ELEMENT.equals(name) || AROUND.equals(name);
    }

    private GenericBeanDefinition createAdviceDefinition(Element adviceElement, BeanDefinitionRegistry registry,
                                                         String aspectName, int order,
                                                         GenericBeanDefinition methodDef, GenericBeanDefinition aspectFactoryDef,
                                                         List<BeanDefinition> bds, List<RuntimeBeanReference> beanRefs) {


        GenericBeanDefinition adviceDefinition = new GenericBeanDefinition(getAdviceClass(adviceElement));
        adviceDefinition.getPropValues().add(new PropertyValue(ASPECT_NAME_PROPERTY, aspectName));

        ConstructorArgument cav = adviceDefinition.getConstructorArgument();
        cav.addArgumentValue(methodDef);

        Object pointcut = parsePointcutProperty(adviceElement);
        if (pointcut instanceof BeanDefinition) {
            cav.addArgumentValue(pointcut);

            bds.add((BeanDefinition) pointcut);
        } else if (pointcut instanceof String) {
            RuntimeBeanReference pointcutRef = new RuntimeBeanReference((String) pointcut);
            cav.addArgumentValue(pointcutRef);
            beanRefs.add(pointcutRef);
        }
        cav.addArgumentValue(aspectFactoryDef);

        return adviceDefinition;
    }

    private Object parsePointcutProperty(Element element/*, ParserContext parserContext*/) {
        if ((element.attribute(POINTCUT) == null) && (element.attribute(POINTCUT_REF) == null)) {
			/*parserContext.getReaderContext().error(
					"Cannot define both 'pointcut' and 'pointcut-ref' on <advisor> tag.",
					element, this.parseState.snapshot());*/
            return null;
        } else if (element.attribute(POINTCUT) != null) {
            // Create a pointcut for the anonymous pc and register it.
            String expression = element.attributeValue(POINTCUT);
            GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
            //pointcutDefinition.setSource(parserContext.extractSource(element));
            return pointcutDefinition;
        } else if (element.attribute(POINTCUT_REF) != null) {
            String pointcutRef = element.attributeValue(POINTCUT_REF);
            if (!StringUtils.hasText(pointcutRef)) {
				/*parserContext.getReaderContext().error(
						"'pointcut-ref' attribute contains empty value.", element, this.parseState.snapshot());*/
                return null;
            }
            return pointcutRef;
        } else {/*
			parserContext.getReaderContext().error(
					"Must define one of 'pointcut' or 'pointcut-ref' on <advisor> tag.",
					element, this.parseState.snapshot());*/
            return null;
        }
    }


    private GenericBeanDefinition parsePointcut(Element pointcutElement, BeanDefinitionRegistry registry) {
        String id = pointcutElement.attributeValue(ID);
        String expression = pointcutElement.attributeValue(EXPRESSION);

        GenericBeanDefinition pointcutDefinition = null;


        //this.parseState.push(new PointcutEntry(id));
        pointcutDefinition = createPointcutDefinition(expression);
        //pointcutDefinition.setSource(parserContext.extractSource(pointcutElement));

        String pointcutBeanName = id;
        if (StringUtils.hasText(pointcutBeanName)) {
            registry.registerBeanDefinition(pointcutBeanName, pointcutDefinition);
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(pointcutDefinition, registry);

        }


        return pointcutDefinition;
    }

    protected GenericBeanDefinition createPointcutDefinition(String expression) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition(AspectJExpressionPointcut.class);
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        beanDefinition.setSynthetic(true);
        beanDefinition.getPropValues().add(new PropertyValue(EXPRESSION, expression));
        return beanDefinition;
    }


    private Class<?> getAdviceClass(Element element) {
        String elementName = element.getName();
        if (BEFORE.equals(elementName)) {
            return AspectJBeforeAdvice.class;
        } else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
            return AspectJAfterReturningAdvice.class;
        } else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
            return AspectJAfterThrowingAdvice.class;
        } else {
            throw new IllegalArgumentException("Unknown advice kind [" + elementName + "]. ");
        }
    }

}
