package mini.spring.core.io.support;


import mini.spring.core.io.Resource;
import mini.spring.util.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 * <p>
 * Created by lzm on  2018-11-01 .
 */
public class PackageResourceLoader {


    private Logger logger = LogManager.getLogger(PackageResourceLoader.class);


    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        classLoader = ClassUtils.getDefaultClassLoader();
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public Resource[] getResources(String basePackage) throws IOException {
        basePackage = ClassUtils.convertClassNameToResourcePath(basePackage);
        URL url = getClassLoader().getResource(basePackage);
        File rootDir = new File(url.getFile());

        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] resources = new Resource[matchingFiles.size()];
        int i = 0;
        for (File file : matchingFiles) {
            resources[i++] = new FileSystemResource(file);
        }
        return resources;
    }

    private Set<File> retrieveMatchingFiles(File rootDir) throws IOException {
        if (!rootDir.exists()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [{}] because is dose not exist",
                        rootDir.getAbsolutePath());
            }
            return Collections.emptySet();
        }

        if (!rootDir.isDirectory()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Skipping [{}] because it dose not denote a directory",
                        rootDir.getAbsolutePath());
            }
            return Collections.emptySet();
        }

        if (!rootDir.canRead()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Cannot search for matching files underneath directory [{}]  " +
                                "because the application is not allowed to read the directory",
                        rootDir.getAbsolutePath());
            }
            return Collections.emptySet();
        }


        Set<File> set = new HashSet<>();
        doRetrieveMatchingFiles(rootDir, set);
        return set;
    }

    private void doRetrieveMatchingFiles(File dir, Set<File> result) {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Could not retrieve contents of directory [{}]", dir.getAbsolutePath());
            }
            return;
        }


        for (File content : dirContents) {
            if (content.isDirectory()) {
                if (!content.canRead()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Skipping subdirectory [{}] because the application " +
                                "is not allowed to read the directory");
                    }
                } else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                result.add(content);
            }
        }

    }

}
