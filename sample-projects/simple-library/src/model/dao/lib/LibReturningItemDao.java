package model.dao.lib;

import java.util.List;

import model.mdl.lib.LibraryDao;
import model.obj.lib.LibReturning;
import model.obj.lib.LibReturningItem;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibReturningItemDao extends LibraryDao {
	@Override
	protected Class getEntity() {
		return LibReturningItem.class;
	}

	public List<LibReturningItem> getUnlimitedReturningItems(LibReturning returning) {
		return getListUnlimitedByField("returning", returning, false);
	}
}