package controller.zul.sample;

import model.obj.lib.LibBook;
import cococare.framework.zk.CFZkCtrl;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulBook2ListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return LibBook.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}