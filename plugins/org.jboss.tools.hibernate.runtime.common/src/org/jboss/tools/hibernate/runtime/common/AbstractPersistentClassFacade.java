package org.jboss.tools.hibernate.runtime.common;

import java.util.HashSet;

import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.IPersistentClass;
import org.jboss.tools.hibernate.runtime.spi.IProperty;

public abstract class AbstractPersistentClassFacade 
extends AbstractFacade 
implements IPersistentClass {

	protected IProperty identifierProperty = null;
	protected IPersistentClass rootClass = null;
	protected HashSet<IProperty> propertyClosures = null;

	public AbstractPersistentClassFacade(
			IFacadeFactory facadeFactory, 
			Object target) {
		super(facadeFactory, target);
	}

	@Override
	public String getClassName() {
		return (String)Util.invokeMethod(
				getTarget(), 
				"getClassName", 
				new Class[] {}, 
				new Object[] {});
	}

	@Override
	public String getEntityName() {
		return (String)Util.invokeMethod(
				getTarget(), 
				"getEntityName", 
				new Class[] {}, 
				new Object[] {});
	}

	@Override
	public boolean isAssignableToRootClass() {
		return getRootClassClass().isAssignableFrom(getTarget().getClass());
	}
	
	@Override
	public boolean isRootClass() {
		return getTarget().getClass() == getRootClassClass();
	}

	@Override
	public IProperty getIdentifierProperty() {
		if (identifierProperty == null) {
			Object targetIdentifierProperty = Util.invokeMethod(
					getTarget(), 
					"getIdentifierProperty", 
					new Class[] {}, 
					new Object[] {});
			if (targetIdentifierProperty != null) {
				identifierProperty = getFacadeFactory().createProperty(targetIdentifierProperty);
			}
		}
		return identifierProperty;
	}

	@Override
	public boolean hasIdentifierProperty() {
		return (boolean)Util.invokeMethod(
				getTarget(), 
				"hasIdentifierProperty", 
				new Class[] {}, 
				new Object[] {});
	}

	@Override
	public boolean isInstanceOfRootClass() {
		return getRootClassClass().isAssignableFrom(getTarget().getClass());
	}

	@Override
	public boolean isInstanceOfSubclass() {
		return getSubclassClass().isAssignableFrom(getTarget().getClass());
	}

	@Override
	public String getNodeName() {
		return (String)Util.invokeMethod(
				getTarget(), 
				"getNodeName", 
				new Class[] {}, 
				new Object[] {});
	}

	@Override
	public IPersistentClass getRootClass() {
		if (rootClass == null) {
			Object targetRootClass = Util.invokeMethod(
					getTarget(), 
					"getRootClass", 
					new Class[] {}, 
					new Object[] {});
			if (targetRootClass != null) {
				rootClass = getFacadeFactory().createPersistentClass(targetRootClass);
			}
		}
		return rootClass;
	}

	protected Class<?> getRootClassClass() {
		return Util.getClass(getRootClassClassName(), getFacadeFactoryClassLoader());
	}
	
	protected Class<?> getSubclassClass() {
		return Util.getClass(getSubclassClassName(), getFacadeFactoryClassLoader());
	}
	
	protected String getRootClassClassName() {
		return "org.hibernate.mapping.RootClass";
	}

	protected String getSubclassClassName() {
		return "org.hibernate.mapping.Subclass";
	}

}
