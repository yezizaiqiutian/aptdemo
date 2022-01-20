package com.gh.apt_processor;

import com.gh.apt_annotation.BindView;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Elements mElementUtils;
    private Map<String, ClassCreatorProxy> mProxyMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(BindView.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "processing...");
        mProxyMap.clear();
        //得到所有的注解

        System.out.println("ggg---开始.......");
        //备注接的变量名
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        System.out.println("ggg---elements......." + elements.size() + elements);
        for (Element element : elements) {
            System.out.println("ggg---element......." + element);
            VariableElement variableElement = (VariableElement) element;
            System.out.println("ggg---variableElement......." + variableElement);
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            System.out.println("ggg---classElement......." + classElement);
            String fullClassName = classElement.getQualifiedName().toString();
            System.out.println("ggg---fullClassName......." + fullClassName);
            ClassCreatorProxy proxy = mProxyMap.get(fullClassName);
            if (proxy == null) {
                proxy = new ClassCreatorProxy(mElementUtils, classElement);
                mProxyMap.put(fullClassName, proxy);
            }
            BindView bindAnnotation = variableElement.getAnnotation(BindView.class);
            System.out.println("ggg---bindAnnotation......." + bindAnnotation);
            int id = bindAnnotation.value();
            System.out.println("ggg---id......." + id);
            proxy.putElement(id, variableElement);
            System.out.println("ggg---proxy......." + proxy);
        }
        //通过遍历mProxyMap，创建java文件
        System.out.println("ggg---mProxyMap.keySet()......." + mProxyMap.keySet().size() + mProxyMap.keySet());
        System.out.println("ggg---第二个for...");
        for (String key : mProxyMap.keySet()) {
            System.out.println("ggg---key......." + key);
            ClassCreatorProxy proxyInfo = mProxyMap.get(key);
            try {
                mMessager.printMessage(Diagnostic.Kind.NOTE, " --> create " + proxyInfo.getProxyClassFullName());
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.getTypeElement());
                System.out.println("ggg---proxyInfo.getProxyClassFullName()......." + proxyInfo.getProxyClassFullName());
                System.out.println("ggg---proxyInfo.getTypeElement()......." + proxyInfo.getTypeElement());

                Writer writer = jfo.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                System.out.println("ggg---proxyInfo.generateJavaCode()......." + proxyInfo.generateJavaCode());

                writer.flush();
                writer.close();
            } catch (IOException e) {
                mMessager.printMessage(Diagnostic.Kind.NOTE, " --> create " + proxyInfo.getProxyClassFullName() + "error");
            }
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE, "process finish ...");
        return true;
    }
}