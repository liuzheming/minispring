package mini.spring.core.annotation;

import mini.spring.beans.BeanDefinition;
import mini.spring.beans.factory.BeanDefinitionStoreException;
import mini.spring.beans.factory.support.BeanDefinitionRegistry;
import mini.spring.beans.factory.support.BeanNameGenerator;
import mini.spring.beans.factory.support.DefaultBeanDefinition;
import mini.spring.core.io.Resource;
import mini.spring.core.io.support.PackageResourceLoader;
import mini.spring.core.type.classreading.MetadataReader;
import mini.spring.core.type.classreading.SimpleMetadataReader;
import mini.spring.stereotype.Component;
import mini.spring.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class ClassPathBeanDefinitionScanner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packageToScan) {
        String[] basePackages = StringUtils.tokenizeToStringArray(packageToScan, ",");

        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponent(basePackage);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                registry.registerBeanDefinition(candidate.getId(), candidate);
            }
        }
        return beanDefinitions;
    }

    public Set<BeanDefinition> findCandidateComponent(String basePackage) {
        Set<BeanDefinition> candidate = new LinkedHashSet<>();
        try {
            Resource[] resources = this.resourceLoader.getResources(basePackage);

            for (Resource resource : resources) {
                MetadataReader reader = new SimpleMetadataReader(resource);
                if (reader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {

                    BeanDefinition bd = new DefaultBeanDefinition();
                }
            }

        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }

        return candidate;
    }

}
