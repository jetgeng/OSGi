package org.gunn.gemini.console.test.common;


import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils; 
<<<<<<< HEAD
import org.codehaus.groovy.control.CompilationFailedException;
import org.gunn.gemini.console.Activator;
import org.gunn.gemini.console.classloader.GunnScriptClassLoader;
import org.osgi.framework.Bundle;

import org.gunn.gemini.console.api.engine.GeminiConsoleGroovyEngine;
=======
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.gunn.gemini.console.Activator;
import org.gunn.gemini.console.classloader.GunnScriptClassLoader;
import org.osgi.framework.Bundle;
>>>>>>> 24564b312d9bded7cbde581dc821c12a2a5a2800

public class BeanTestEngine implements GeminiConsoleGroovyEngine {
	
	private GroovyShell shell;

<<<<<<< HEAD
=======
	public void init(){
		engine = new GroovyScriptEngineImpl();
		Bundle[] bundles = Activator.getDefault().getContext().getBundles();
		GunnScriptClassLoader classLoader = new GunnScriptClassLoader(bundles);
		((GroovyScriptEngineImpl)engine).setClassLoader(classLoader);
	}
>>>>>>> 24564b312d9bded7cbde581dc821c12a2a5a2800
	
	private GroovyShell getShell() {
		if( shell == null ){
			Bundle[] bundles = Activator.getDefault().getContext().getBundles();
			GunnScriptClassLoader classLoader = new GunnScriptClassLoader(this.getClass().getClassLoader() ,bundles);
			shell = new GroovyShell(classLoader);
		}
		return shell;
	}
	
	public Class<?> parseClass(File source) throws CompilationFailedException, IOException{
		return getShell().getClassLoader().parseClass(new GroovyCodeSource(source, "UTF-8"),false);
	}
	
	/*
	 * 初始化，输入参数。
	 */
	private void initPara(Binding bind ,  Map<String,Object> para ){
		for(String key: para.keySet()){
			bind.setVariable(key, para.get(key));
		}
	}
	
	public Object run(File sourceFile ,String functionName , Map<String,Object> para , String param){
		
		Script sc;
		try {
			sc = getShell().parse(new FileReader(sourceFile));
			return runScript(sc, functionName, para , param);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public Object run(String code , String functionName , Map<String,Object> para ){
		Script sc = getShell().parse(code); //shell.parse(code);
		return runScript(sc , functionName , para, null);
	}
	
	private Object runScript(Script sc , String functionName , Map<String,Object> para , String param ){
		initPara(sc.getBinding(),para);
		if( StringUtils.isNotEmpty(functionName) ){
			return  sc.invokeMethod(functionName, param); 
		}
		return null;
	}

	@Override
	public Object run(File sourceFile , List args) throws CompilationFailedException, IOException {
		return getShell().run(sourceFile, args);
	}
	
	
}
