package org.gunn.gemini.console;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import org.apache.commons.lang.StringUtils;
import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.gunn.gemini.console.test.common.BeanTestEngine;
import org.gunn.gemini.console.test.common.ParamParaserResult;
import org.gunn.gemini.console.test.common.TestCommandParamHandle;
//import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator extends AbstractBlueprintActivator  implements CommandProvider{

	private BeanTestEngine engine;
	private Map<String , Object> scriptParam;
	private TestCommandParamHandle paramHandle;
	public final static String Main_Method_Name = "main";
	
	public Activator(){
		scriptParam = new HashMap<String, Object>();
		scriptParam.put("act", this);
		paramHandle = new TestCommandParamHandle();
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		
		engine = new BeanTestEngine();
		
		getContext().registerService(CommandProvider.class.getName(), this, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
	}

	@Override
	public String getHelp() {
		String helptips = Messages.Activator_Help;
		return helptips;
	}
	
	
	/**
	 * 通过Groovy代码来查看bean的内容。
	 * @param interpreter
	 * @return
	 */
	public Object _invokegv( CommandInterpreter interpreter){
		ParamParaserResult result = paramHandle.paraseParam(interpreter);
		
		switch(result.commandType()){
			case ScriptCode :
					engine.run(getCommandCode(result.getScriptCode()), Main_Method_Name, getParam());
				break;
			case FileCode :
				URL url;
				try {
					url = new URL(result.getScriptFileUrl());
					File scriptFile = new File(url.toURI());
					engine.run( scriptFile ,Main_Method_Name, getParam() , "");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
				break;
		}
		
		return null;
	}
	
	private String getCommandCode(String arg){
		StringBuffer strBuff = new StringBuffer(" def "+Main_Method_Name+"(){").append("\n\r");
		strBuff.append(arg);
		strBuff.append("}");
		return strBuff.toString();
	}
	
	private Map<String,Object> getParam(){
		return scriptParam;
	}
	

}
