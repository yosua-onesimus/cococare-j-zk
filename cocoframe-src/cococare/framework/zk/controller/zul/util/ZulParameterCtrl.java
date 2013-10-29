package cococare.framework.zk.controller.zul.util;

import static cococare.common.CCLogic.isNotNull;

import org.zkoss.zul.Grid;

import cococare.framework.model.obj.util.UtilConfig;
import cococare.framework.zk.CFZkCtrl;

public class ZulParameterCtrl extends CFZkCtrl {
	private Grid pnlGenerator;

	@Override
	protected Class _getEntity() {
		if (isNotNull(objEntity)) {
			return objEntity.getClass();
		} else {
			return UtilConfig.class;
		}
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initEditor() {
		super._initEditor();
		//
		edtEntity.generateDefaultEditor(pnlGenerator);
	}
}