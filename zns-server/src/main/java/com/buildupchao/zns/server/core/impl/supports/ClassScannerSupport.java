package com.buildupchao.zns.server.core.impl.supports;

import com.buildupchao.zns.server.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * Class scanner template. Provide a serial of scan strategy so as to simplify operation.
 *
 * @author buildupchao
 *         Date: 2019/1/31 22:11
 * @since JDK 1.8
 */
public abstract class ClassScannerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassScannerSupport.class);

    protected final String packageName;

    public ClassScannerSupport(String packageName) {
        this.packageName = packageName;
    }

    public final List<Class<?>> getClassList() {
        List<Class<?>> classList = new ArrayList<>();

        try {
            Enumeration<URL> resources = ClassUtils.getClassLoader().getResources(packageName.replace(".", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url == null) {
                    continue;
                }
                String protocol = url.getProtocol();
                if (protocol.equals("file")) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    addClass(classList, packagePath, packageName);
                } else if (protocol.equals("jar")) {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    Enumeration<JarEntry> entries = jarURLConnection.getJarFile().entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        String jarEntryName = jarEntry.getName();
                        if (jarEntryName.endsWith(".class")) {
                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll
                                    ("/", ".");
                            doAddClass(classList, className);
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("{}#getClassList error!", this.getClass().getSimpleName(), e);
        }
        return classList;
    }

    private void addClass(List<Class<?>> classList, String packagePath, String packageName) {
        try {
            File[] files = new File(packagePath)
                    .listFiles((file) -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
            for (File file : files) {
                String fileName = file.getName();
                if (file.isFile()) {
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    if (StringUtils.isNotEmpty(packageName)) {
                        className = packageName + "." + className;
                    }
                    doAddClass(classList, className);
                } else {
                    String subPackagePath = fileName;
                    if (StringUtils.isNotEmpty(packagePath)) {
                        subPackagePath = packagePath + "/" + subPackagePath;
                    }

                    String subPackageName = fileName;
                    if (StringUtils.isNotEmpty(packageName)) {
                        subPackageName = packageName + "." + subPackageName;
                    }
                    addClass(classList, subPackagePath, subPackageName);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("{}#addClass generated an error!", this.getClass().getSimpleName(), ex);
        }
    }

    private void doAddClass(List<Class<?>> classList, String className) {
        Class<?> cls = ClassUtils.loadClass(className);
        if (checkIfAddClass(cls)) {
            classList.add(cls);
        }
    }

    protected abstract boolean checkIfAddClass(Class<?> cls);
}
