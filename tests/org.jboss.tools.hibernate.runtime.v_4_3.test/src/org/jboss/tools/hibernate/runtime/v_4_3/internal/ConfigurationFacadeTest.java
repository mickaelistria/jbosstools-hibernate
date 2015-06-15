package org.jboss.tools.hibernate.runtime.v_4_3.internal;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.jboss.tools.hibernate.runtime.common.AbstractConfigurationFacade;
import org.jboss.tools.hibernate.runtime.spi.IConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.EntityResolver;
import org.xml.sax.helpers.DefaultHandler;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class ConfigurationFacadeTest {
	
	private String methodName = null;
	private Object[] arguments = null;
	
	private IConfiguration configuration = null;
	
	@Before
	public void setUp() throws Exception {
		methodName = null;
		arguments = null;
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(Configuration.class);
		Class<?> proxyClass = proxyFactory.createClass();
		ProxyObject proxy = (ProxyObject)proxyClass.newInstance();
		proxy.setHandler(new MethodHandler() {		
			@Override
			public Object invoke(
					Object self, Method m, Method proceed, Object[] args) throws Throwable {
				methodName = m.getName();
				arguments = args;
				return proceed.invoke(self, args);
			}
		});
		configuration = new AbstractConfigurationFacade(null, proxy) {};
	}
	
	@Test
	public void testGetProperty() {
		configuration.setProperty("foo", "bar");
		Assert.assertEquals("bar", configuration.getProperty("foo"));
		Assert.assertEquals("getProperty", methodName);
		Assert.assertArrayEquals(new Object[] { "foo" }, arguments);
	}
	
	@Test
	public void testAddFile() {
		File testFile = new File("");
		Assert.assertSame(configuration, configuration.addFile(testFile));
		Assert.assertEquals("addFile", methodName);
		Assert.assertArrayEquals(new Object[] { testFile }, arguments);
	}
	
	@Test
	public void testSetProperty() {
		configuration.setProperty("foo", "bar");
		Assert.assertEquals("setProperty", methodName);
		Assert.assertArrayEquals(new Object[] { "foo", "bar" },  arguments);
		Assert.assertEquals("bar", configuration.getProperty("foo"));
	}
	
	@Test 
	public void testSetProperties() {
		Properties testProperties = new Properties();
		Assert.assertSame(configuration, configuration.setProperties(testProperties));
		Assert.assertEquals("setProperties", methodName);
		Assert.assertArrayEquals(new Object[] { testProperties }, arguments);
		Assert.assertSame(testProperties, configuration.getProperties());
	}
	
	@Test
	public void testSetEntityResolver() {
		EntityResolver testResolver = new DefaultHandler();
		configuration.setEntityResolver(testResolver);
		Assert.assertEquals("setEntityResolver", methodName);
		Assert.assertArrayEquals(new Object[] { testResolver }, arguments);
	}
	
	public void testGetProperties() {
		Properties testProperties = new Properties();
		configuration.setProperties(testProperties);
		Assert.assertSame(testProperties, configuration.getProperties());
		Assert.assertEquals("getProperties", methodName);
		Assert.assertArrayEquals(new Object[] {}, arguments);
	}
	
}
