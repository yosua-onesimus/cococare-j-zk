package cococare.framework.zk.controller.zul.note;

import static cococare.common.CCFormat.getBoolean;
import static cococare.common.CCLogic.isNotNull;
import static cococare.common.CCLogic.isNull;

import java.util.List;

import org.zkoss.zul.A;

import cococare.common.CCCustomField;
import cococare.database.CCHibernateFilter;
import cococare.framework.model.obj.note.NoteShortcut;
import cococare.framework.zk.CFZkCtrl;

public class ZulShortcutListCtrl extends CFZkCtrl {
	@Override
	protected Class _getEntity() {
		return NoteShortcut.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}

	@Override
	protected String _getSysRef(Object objEntity) {
		if (getBoolean(parameter.get("tracker_newEntity"))) {
			return objEntity.toString();
		} else {
			return super._getSysRef(objEntity);
		}
	}

	@Override
	protected void _initTable() {
		super._initTable();
		tblEntity.setVisibleField(false, "path");
		tblEntity.addField(2, new CCCustomField() {
			@Override
			public String getLabel() {
				return "Path";
			}

			@Override
			public Object getCustomView(Object object) {
				return new A(((NoteShortcut) object).getPath());
			}
		});
		tblEntity.setColumnWidth(2, 200);
		tblEntity.setEditableColumn(true, 2);
		if (isNotNull(parameter.get("tracker"))) {
			tblEntity.setHqlFilters(new CCHibernateFilter() {
				@Override
				public String getFieldName() {
					return "tracker";
				}

				@Override
				public Object getFieldValue() {
					return parameter.get("tracker");
				}
			});
		}
	}

	@Override
	protected void _doShowScreen() {
		if (isNull(parameter.get("tracker"))) {
			super._doShowScreen();
		}
	}

	@Override
	protected boolean _doDeleteEntity() {
		if (getBoolean(parameter.get("tracker_newEntity"))) {
			return ((List) parameter.get("shortcuts")).remove(_getSelectedItem());
		} else {
			return super._doDeleteEntity();
		}
	}

	@Override
	public void doUpdateTable() {
		if (getBoolean(parameter.get("tracker_newEntity"))) {
			tblEntity.setList((List) parameter.get("shortcuts"));
		} else {
			super.doUpdateTable();
		}
	}
}