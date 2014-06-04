package controller.zul.lib;

import model.obj.lib.LibMember;
import cococare.framework.zk.CFZkCtrl;

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