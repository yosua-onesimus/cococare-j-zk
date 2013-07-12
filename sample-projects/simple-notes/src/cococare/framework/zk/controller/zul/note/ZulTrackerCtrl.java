package cococare.framework.zk.controller.zul.note;

import cococare.framework.model.obj.note.NoteTracker;
import cococare.framework.zk.CFZkCtrl;

public class ZulTrackerCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return NoteTracker.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}
}