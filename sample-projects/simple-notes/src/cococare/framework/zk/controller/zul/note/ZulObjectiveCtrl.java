package cococare.framework.zk.controller.zul.note;

import cococare.framework.model.obj.note.NoteObjective;
import cococare.framework.zk.CFZkCtrl;

public class ZulObjectiveCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return NoteObjective.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}
}