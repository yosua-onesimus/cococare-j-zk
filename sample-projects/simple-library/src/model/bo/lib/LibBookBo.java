package model.bo.lib;

import java.util.List;

import model.dao.lib.LibBookDao;
import model.obj.lib.LibBook;
import cococare.database.CCHibernateBo;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibBookBo extends CCHibernateBo {
	private LibBookDao bookDao;

	public synchronized List<LibBook> getList() {
		return bookDao.getList();
	}
}