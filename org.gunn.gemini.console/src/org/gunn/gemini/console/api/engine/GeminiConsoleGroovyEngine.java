package org.gunn.gemini.console.api.engine;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;

public interface GeminiConsoleGroovyEngine {
	
	/**
	 * 解析给定的Groovy文件，返回Class。
	 * @param source
	 * @return
	 * @throws CompilationFailedException
	 * @throws IOException
	 */
	public Class<?> parseClass(File source) throws CompilationFailedException , IOException;

	/**
	 * 通过文件的方式执行脚本。
	 * @param sourceFile
	 * @param functionName 将要执行的函数名称。
	 * @param para 通过Bingding方式设定到脚本中的变量。
	 * @param param 函数的参数选项。
	 * @return
	 */
	public Object run(File sourceFile ,String functionName , Map<String,Object> para , String param);
	
	/**
	 * 运行通过字符串方式给定的Groovy脚本。
	 * @param code  Groovy 代码
	 * @param functionName 将要执行的函数名称。
	 * @param para 通过Bingding方式设定到脚本中的变量。
	 * @return 执行的结果。
	 */
	public Object run(String code , String functionName , Map<String,Object> para );
	
	public Object run(File sourceFile , List args) throws CompilationFailedException, IOException;
}
