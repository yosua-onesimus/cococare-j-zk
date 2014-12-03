package model.dao.lib;

import java.util.List;

import model.mdl.lib.LibraryDao;
import model.obj.lib.LibBorrowing;
import model.obj.lib.LibBorrowingItem;
import model.obj.lib.LibMember;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibBorrowingItemDao extends LibraryDao {
	@Override
	protected Class getEntity() {
		return LibBorrowingItem.class;
	}

	public List<LibBorrowingItem> getUnlimitedBorrowingItems(LibBorrowing borrowing) {
		return getListUnlimitedByField("borrowing", borrowing, false);
	}

	public List<LibMember> getUnlimitedBorrowingMembers() {
		hql.start().alias("borrowingItem").select("borrowingItem.borrowing.member_").where("borrowingItem.returned = FALSE");
		return getListUnlimitedBy(hql.value(), parameters.value());
	}
}