package org.jboss.tools.hibernate.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.PrimaryKey;
import org.jboss.tools.hibernate.runtime.common.AbstractPrimaryKeyFacade;
import org.jboss.tools.hibernate.runtime.spi.IColumn;
import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.ITable;

public class PrimaryKeyProxy extends AbstractPrimaryKeyFacade {

	private List<IColumn> columns = null;
	private ITable table = null;

	public PrimaryKeyProxy(
			IFacadeFactory facadeFactory,
			PrimaryKey primaryKey) {
		super(facadeFactory, primaryKey);
	}

	public PrimaryKey getTarget() {
		return (PrimaryKey)super.getTarget();
	}

	@Override
	public void addColumn(IColumn column) {
		assert column instanceof ColumnProxy;
		getTarget().addColumn(((ColumnProxy)column).getTarget());
	}

	@Override
	public int getColumnSpan() {
		return getTarget().getColumnSpan();
	}

	@Override
	public List<IColumn> getColumns() {
		if (columns == null) {
			initializeColumns();
		}
		return columns;
	}
	
	private void initializeColumns() {
		columns = new ArrayList<IColumn>();
		Iterator<?> origin = getTarget().getColumns().iterator();
		while (origin.hasNext()) {
			columns.add(new ColumnProxy(getFacadeFactory(), (Column)origin.next()));
		}
	}

	@Override
	public IColumn getColumn(int i) {
		if (columns == null) {
			initializeColumns();
		}
		return columns.get(i);
	}

	@Override
	public ITable getTable() {
		if (table == null && getTarget().getTable() != null) {
			table = new TableProxy(getFacadeFactory(), getTarget().getTable());
		}
		return table;
	}

	@Override
	public boolean containsColumn(IColumn column) {
		assert column instanceof ColumnProxy;
		return getTarget().containsColumn(((ColumnProxy)column).getTarget());
	}

	@Override
	public Iterator<IColumn> columnIterator() {
		if (columns == null) {
			initializeColumns();
		}
		return columns.iterator();
	}

	@Override
	public String getName() {
		return getTarget().getName();
	}

}
