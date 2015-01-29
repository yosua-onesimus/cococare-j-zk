package controller.zul.lib;

import model.obj.lib.LibMember;
import cococare.framework.zk.CFZkCtrl;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulMemberListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return LibMember.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}
}