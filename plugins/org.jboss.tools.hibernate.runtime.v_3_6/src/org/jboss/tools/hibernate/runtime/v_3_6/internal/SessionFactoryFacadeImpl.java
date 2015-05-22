package org.jboss.tools.hibernate.runtime.v_3_6.internal;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.jboss.tools.hibernate.runtime.common.AbstractSessionFactoryFacade;
import org.jboss.tools.hibernate.runtime.spi.IClassMetadata;
import org.jboss.tools.hibernate.runtime.spi.ICollectionMetadata;
import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.ISession;

public class SessionFactoryFacadeImpl extends AbstractSessionFactoryFacade {
	
	private Map<String, IClassMetadata> allClassMetadata = null;
	private Map<String, ICollectionMetadata> allCollectionMetadata = null;

	public SessionFactoryFacadeImpl(
			IFacadeFactory facadeFactory, 
			SessionFactory sessionFactory) {
		super(facadeFactory, sessionFactory);
	}

	public SessionFactory getTarget() {
		return (SessionFactory)super.getTarget();
	}

	@Override
	public Map<String, IClassMetadata> getAllClassMetadata() {
		if (allClassMetadata == null) {
			initializeAllClassMetadata();
		}
		return allClassMetadata;
	}
	
	private void initializeAllClassMetadata() {
		Map<String, ClassMetadata> origin = getTarget().getAllClassMetadata();
		allClassMetadata = new HashMap<String, IClassMetadata>(origin.size());
		for (Map.Entry<String, ClassMetadata> entry : origin.entrySet()) {
			allClassMetadata.put(
					entry.getKey(), 
					getFacadeFactory().createClassMetadata(entry.getValue()));
		}
	}

	@Override
	public Map<String, ICollectionMetadata> getAllCollectionMetadata() {
		if (allCollectionMetadata == null) {
			initializeAllCollectionMetadata();
		}
		return allCollectionMetadata;
	}
	
	@SuppressWarnings("unchecked")
	private void initializeAllCollectionMetadata() {
		Map<String, CollectionMetadata> origin = getTarget().getAllCollectionMetadata();
		allCollectionMetadata = new HashMap<String, ICollectionMetadata>(origin.size());
		for (Map.Entry<String, CollectionMetadata> entry : origin.entrySet()) {
			allCollectionMetadata.put(
					entry.getKey(), 
					getFacadeFactory().createCollectionMetadata(entry.getValue()));
		}
	}

	@Override
	public ISession openSession() {
		return getFacadeFactory().createSession(getTarget().openSession());
	}
	
	@Override
	public IClassMetadata getClassMetadata(Class<?> clazz) {
		if (allClassMetadata == null) {
			initializeAllClassMetadata();
		}
		return allClassMetadata.get(clazz.getName());
	}

	@Override
	public IClassMetadata getClassMetadata(String entityName) {
		if (allClassMetadata == null) {
			initializeAllClassMetadata();
		}
		return allClassMetadata.get(entityName);
	}

	@Override
	public ICollectionMetadata getCollectionMetadata(String string) {
		if (allCollectionMetadata == null) {
			initializeAllCollectionMetadata();
		}
		return allCollectionMetadata.get(string);
	}

}
