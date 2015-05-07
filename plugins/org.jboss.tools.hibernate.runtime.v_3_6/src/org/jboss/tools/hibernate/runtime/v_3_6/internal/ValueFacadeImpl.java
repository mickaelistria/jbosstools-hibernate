package org.jboss.tools.hibernate.runtime.v_3_6.internal;

import org.hibernate.mapping.Value;
import org.jboss.tools.hibernate.runtime.common.AbstractValueFacade;
import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;

public class ValueFacadeImpl extends AbstractValueFacade {
	
	public ValueFacadeImpl(IFacadeFactory facadeFactory, Value value) {
		super(facadeFactory, value);
	}

	public Value getTarget() {
		return (Value)super.getTarget();
	}

}
