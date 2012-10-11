package org.gunn.gemini.console.classloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
<<<<<<< HEAD

import org.osgi.framework.Bundle;
import org.eclipse.osgi.framework.internal.core.BundleHost;
=======
import groovy.lang.GroovyClassLoader;
import org.osgi.framework.Bundle;

>>>>>>> 24564b312d9bded7cbde581dc821c12a2a5a2800
/**
 * 将作为GroovyScript的ClassLoader。
 * <p>
 * 整个平台中用一个这个ClassLoader应该就够了。
 * 
 * @author gengjet
 * 
 */
<<<<<<< HEAD
public class GunnScriptClassLoader extends ClassLoader {

    private final List<ClassLoader> loaders = new ArrayList<ClassLoader>();

    private final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

    public GunnScriptClassLoader(ClassLoader cl , Bundle[] bundles) {
        super(cl);
        for(Bundle bundle:bundles) {
            if( bundle instanceof BundleHost ) {
                loaders.add(((BundleHost)bundle).getClassLoader());
            }
        }
=======
public class GunnScriptClassLoader extends GroovyClassLoader {

    private Bundle[] bundles ;
    private final Map< String, Class< ? > > cachedClasses = new HashMap<String,Class<?>>();

    
    public GunnScriptClassLoader(Bundle[] bundles) {
        this.bundles = bundles;
>>>>>>> 24564b312d9bded7cbde581dc821c12a2a5a2800
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.ClassLoader#loadClass(java.lang.String)
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
<<<<<<< HEAD
        
        ClassNotFoundException notFoundException = null;
        // 先从容器里面找。
        if (classes.containsKey(name)) {
            return classes.get(name);
        }

        for ( final ClassLoader loader : this.loaders) {
            if (loader == null) {
                continue;
            }
          try {
              Class<?> clazz = loader.loadClass(name);
            if (clazz != null) {
                classes.put(name, clazz);
                return clazz;
            }
          }catch( final ClassNotFoundException e ) {
              notFoundException = e;
          }
        }
        
        final Class<?> clazz = super.findClass(name);
        if( clazz != null ) {
            return clazz;
        }
        throw notFoundException;
    }

    public void add(ClassLoader loader) {
        loaders.add(loader);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
     */
    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
=======
        return askBundles(name);
    }
    
    private Class<?> askBundles (final String name) throws ClassNotFoundException{
        synchronized(cachedClasses) {
            if( cachedClasses.containsKey(name)) {
                return cachedClasses.get(name);
            }
            
            for(final Bundle bundle :bundles) {
                try {
                    final Class<?> clazz = bundle.loadClass(name);
                    if( clazz == null ) {
                        continue;
                    }
                    cachedClasses.put(name, clazz);
                    return clazz;
                }catch(final ClassNotFoundException cnfe ) {}
            }
            throw new ClassNotFoundException( "Class " + name + " not found in bundles: " +  bundles  );
        }
    }

    @Override
    protected Class< ? > findClass( final String name )
    throws ClassNotFoundException
    {
        return askBundles( name );
    }
    
>>>>>>> 24564b312d9bded7cbde581dc821c12a2a5a2800

}
