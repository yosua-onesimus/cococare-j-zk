package model.bo.lib;

import java.util.List;

import model.dao.lib.LibReturningItemDao;
import model.obj.lib.LibMember;
import model.obj.lib.LibReturningItem;
import cococare.database.CCHibernateBo;

public class LibReturningItemBo extends CCHibernateBo {
	private LibReturningItemDao returningItemDao;

	public synchronized List<LibReturningItem> getUnlimitedReturningItems(LibMember member) {
		return returningItemDao.getUnlimitedReturningItems(member);
	}
}