package model.mdl.lib;

import java.util.List;

import cococare.database.CCHibernate;
import cococare.database.CCHibernateDao;
import cococare.database.CCHibernateFilter;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public abstract class LibraryDao extends CCHibernateDao {
	@Override
	protected CCHibernate getCCHibernate() {
		return LibraryModule.INSTANCE.getCCHibernate();
	}

	@Override
	protected List<CCHibernateFilter> getFilters() {
		return LibraryModule.INSTANCE.getFilters();
	}
}