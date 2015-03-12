package model.dao.lib;

import java.util.List;

import model.mdl.lib.LibraryDao;
import model.obj.lib.LibBook;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibBookDao extends LibraryDao {
	@Override
	protected Class getEntity() {
		return LibBook.class;
	}

	public List<LibBook> getBooks() {
		hql.start();
		parameters.start();
		return getListBy(hql.value(), parameters.value());
	}

	public boolean saveOrUpdateBook(LibBook book) {
		return saveOrUpdate(book);
	}
}