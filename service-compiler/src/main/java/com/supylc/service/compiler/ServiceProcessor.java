package com.supylc.service.compiler;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.supylc.service.annotation.ServiceRoute;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * create by elileo on 2019/2/27
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ServiceProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;

    private final static String PACKAGE_NAME = "com.supylc.arch.route.service.manager";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    //TODO dangers must not update this code
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty()) {
            return false;
        }
        TypeElement typeElement = set.iterator().next();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(typeElement);
        for (Element element : elements) {
            TypeName typeName = ClassName.get((TypeElement) element);

            ServiceRoute classAnnotation = element.getAnnotation(ServiceRoute.class);
            if (classAnnotation != null) {

                TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(element.getSimpleName() + "_").addModifiers(Modifier.PUBLIC);
                /**
                for (TypeMirror typeMirror : ((TypeElement) element).getInterfaces()) {
                    typeBuilder.addSuperinterface(TypeName.get(typeMirror));
                }

                for (VariableElement e : ElementFilter.fieldsIn(element.getEnclosedElements())) {
                    typeBuilder.addField(TypeName.get(e.asType()), e.getSimpleName().toString(), e.getModifiers().toArray(new Modifier[e.getModifiers().size()]));
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "============" + e.toString());
                }

                                AnnotationSpec annotationSpec = AnnotationSpec.builder(Route.class)
                        .addMember("path", "$S", "/service/" + typeName.toString().replaceAll("\\.",""))
                        .addMember("group", "$S", "service")
                        .build();
                typeBuilder.addAnnotation(annotationSpec);

                for (ExecutableElement executableElement : ElementFilter.methodsIn(element.getEnclosedElements())) {
                    String methodName = executableElement.getSimpleName().toString();
                    MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName);
                    methodBuilder.addModifiers(Modifier.PUBLIC);
                    TypeMirror typeMirror = executableElement.getReturnType();
                    methodBuilder.returns(TypeName.get(typeMirror));
                    List<? extends VariableElement> variableElements = executableElement.getParameters();

                    StringBuilder methodParamStr = new StringBuilder("(");
                    for(VariableElement variableElement : variableElements){
                        methodBuilder.addParameter(TypeName.get(variableElement.asType()), variableElement.getSimpleName().toString());
                        methodParamStr.append(variableElement.getSimpleName().toString());
                        methodParamStr.append(",");
                    }
                    if(methodParamStr.toString().contains(",")){
                        methodParamStr.replace(methodParamStr.toString().lastIndexOf(","), methodParamStr.length(), "");
                    }
                    methodParamStr.append(")");

                    typeBuilder.addMethod(methodBuilder.build());
                    //mMessager.printMessage(Diagnostic.Kind.NOTE, "============" + e.toString());
                }
                 */

                AnnotationSpec annotationSpec = AnnotationSpec.builder(Route.class)
                        .addMember("path", "$S", "/service/" + typeName.toString().replaceAll("\\.",""))
                        .addMember("group", "$S", "service")
                        .build();

                typeBuilder.addAnnotation(annotationSpec);
                typeBuilder.superclass(typeName);


                JavaFile javaFile = JavaFile.builder(((ClassName) typeName).packageName(), typeBuilder.build()).build();
                try {
                    javaFile.toJavaFileObject().delete();
                    javaFile.writeTo(mFiler);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                /*
                String className = classAnnotation.value();
                if(!className.isEmpty()){
                    TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);
                    List<? extends Element> listMethodElements = element.getEnclosedElements();
                    List<ExecutableElement> methodList = ElementFilter.methodsIn(listMethodElements);
                    for(ExecutableElement executableElement : methodList){
                        String methodName = executableElement.getSimpleName().toString();
                        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(methodName);
                        methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
                        TypeMirror typeMirror = executableElement.getReturnType();
                        methodBuilder.returns(TypeName.get(typeMirror));
                        List<? extends VariableElement> variableElements = executableElement.getParameters();

                        StringBuilder methodParamStr = new StringBuilder("(");
                        for(VariableElement variableElement : variableElements){
                            methodBuilder.addParameter(TypeName.get(variableElement.asType()), variableElement.getSimpleName().toString());
                            methodParamStr.append(variableElement.getSimpleName().toString());
                            methodParamStr.append(",");
                        }
                        if(methodParamStr.toString().contains(",")){
                            methodParamStr.replace(methodParamStr.toString().lastIndexOf(","), methodParamStr.length(), "");
                        }
                        methodParamStr.append(")");
                        methodBuilder.addStatement("$T iService = null", element);
                        methodBuilder.addStatement("iService =  com.zzc.platformsdk.service.RouteServiceManager.provide($T.class)", element);
                        if(typeMirror.getKind() != TypeKind.VOID){
                            methodBuilder.beginControlFlow("if(iService != null)");
                            methodBuilder.addStatement("return iService.$N$L", methodName, methodParamStr);
                            methodBuilder.endControlFlow();
                        }else{
                            methodBuilder.beginControlFlow("if(iService != null)");
                            methodBuilder.addStatement("iService.$N$L", methodName, methodParamStr);
                            methodBuilder.endControlFlow();
                        }
                        if(typeMirror.getKind() == TypeKind.INT ||
                                typeMirror.getKind() == TypeKind.LONG ||
                                typeMirror.getKind() == TypeKind.SHORT ||
                                typeMirror.getKind() == TypeKind.FLOAT ||
                                typeMirror.getKind() == TypeKind.DOUBLE){
                            methodBuilder.addStatement("return 0");
                        }else if(typeMirror.getKind() == TypeKind.BOOLEAN){
                            methodBuilder.addStatement("return false");
                        } else if(typeMirror.toString().contains("io.reactivex.Observable")){
                            methodBuilder.addStatement("Observable observable = Observable.create(new io.reactivex.ObservableOnSubscribe() {\n" +
                                    "            @Override\n" +
                                    "            public void subscribe(io.reactivex.ObservableEmitter emitter) throws Exception {\n" +
                                    "                emitter.onError(new Throwable(\"not found\"));\n" +
                                    "            }\n" +
                                    "        })");
                            methodBuilder.addStatement("return observable");
                        } else if(typeMirror.getKind() !=  TypeKind.VOID){
                            methodBuilder.addStatement("return null");
                        }
                        if(executableElement.getThrownTypes() != null){
                            for(TypeMirror exceptionType : executableElement.getThrownTypes()){
                                methodBuilder.addException(TypeName.get(exceptionType));
                            }
                        }
                        typeBuilder.addMethod(methodBuilder.build());
                    }
                    JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, typeBuilder.build()).build();
                    try{
                        javaFile.toJavaFileObject().delete();
                        javaFile.writeTo(mFiler);
                    }catch (Exception e){
                        e.printStackTrace();
                        return false;
                    }

                }
                */
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ServiceRoute.class.getCanonicalName());
        return types;
    }
}
