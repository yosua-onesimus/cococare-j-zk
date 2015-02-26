package model.dao.lib;

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
}