package org.gunn.gemini.console;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.osgi.service.blueprint.container.NoSuchComponentException;


public abstract class AbstractBlueprintActivator implements BundleActivator  {

	private static AbstractBlueprintActivator instance = null;
	private BundleContext context = null;
	private ServiceTracker bluePrintContainerTracker;
	
	
	public static AbstractBlueprintActivator getDefault(){
		return instance;
	}
	
	public BundleContext getContext(){
		return context;
	}
	
	private ServiceTracker getbluePrintContainerTracker() throws Exception{
		if( bluePrintContainerTracker != null ){
			
			return bluePrintContainerTracker;
		}else{
			throw new Exception("Can not found BlueprintContainer");
		}
	}
	
	
	
	
	private BlueprintContainer[] getBlueprintContainer() throws Exception{
		if( getbluePrintContainerTracker().getService() != null ){
			
			BlueprintContainer[] containers = new BlueprintContainer[getbluePrintContainerTracker().getServices().length]  ;
			
			int i = 0;
			for(Object container : getbluePrintContainerTracker().getServices()){
				if(container instanceof BlueprintContainer){
					containers[i++] =  (BlueprintContainer) container;
				}
			}
			
			return containers;
		}else{
			throw new Exception("Can not found BlueprintContainer");
		}
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		this.instance = this;
		this.context = context;
		
		//here i will build a service tracker to access blueprintContainer
		bluePrintContainerTracker = new ServiceTracker( getContext() , BlueprintContainer.class.getName() , null );
		bluePrintContainerTracker.open(true);
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		getbluePrintContainerTracker().close();
	}
	
	public Object getBean(String id) throws Exception{
		NoSuchComponentException notFoundException = null;
		for(BlueprintContainer container : getBlueprintContainer()){
			try{
				return container.getComponentInstance(id);
			}catch(NoSuchComponentException e){
				notFoundException = e;
			}
		}
		
		throw notFoundException;
	}
	
	
}
