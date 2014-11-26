package model.bo.lib;

import static cococare.common.CCFormat.getNextDate;

import java.util.Date;
import java.util.List;

import model.dao.lib.LibBorrowingItemDao;
import model.obj.lib.LibBook;
import cococare.database.CCHibernateBo;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibBorrowingItemBo extends CCHibernateBo {
	private LibBorrowingItemDao borrowingItemDao;

	public synchronized List<LibBook> getUnlimitedBorrowedBooks() {
		return borrowingItemDao.getUnlimitedBorrowedBooks();
	}

	public synchronized Date calculateDateReturn(Date borrowedDate, Integer borrowingLimit) {
		return getNextDate(borrowedDate, borrowingLimit);
	}
}