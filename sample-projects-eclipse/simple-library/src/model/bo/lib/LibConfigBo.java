package model.bo.lib;

import model.obj.lib.LibConfig;
import cococare.database.CCHibernateBo;
import cococare.framework.model.dao.util.UtilConfigDao;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class LibConfigBo extends CCHibernateBo {
	private UtilConfigDao configDao;

	public synchronized LibConfig loadLibConfig() {
		return configDao.loadHash(LibConfig.class);
	}

	public synchronized boolean saveConf(Object object) {
		return configDao.saveHash(object);
	}
}