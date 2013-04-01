package controller.zul.util;

import static cococare.common.CCLogic.isNotNull;
import static cococare.common.CCLogic.isNotNullAndNotEmpty;
import static cococare.zk.CCZk.addEventListenerOnClick;
import static cococare.zk.CCZk.newCCTable;
import static cococare.zk.CCZk.selectRowElement;
import static cococare.zk.CCZk.setStyle;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;

import cococare.common.CCCustomField;
import cococare.framework.model.bo.util.UtilUserGroupBo;
import cococare.framework.model.obj.util.UtilPrivilege;
import cococare.framework.model.obj.util.UtilUserGroup;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCTable;

public class ZulUserGroupCtrl extends CFZkCtrl {
	private UtilUserGroupBo userGroupBo;
	private CCTable tblPrivilege;
	private EventListener elSelect = new EventListener() {
		public void onEvent(Event event) throws Exception {
			selectRowElement(event.getTarget());
			_doSelect(((Checkbox) event.getTarget()).isChecked());
		}
	};

	@Override
	protected Class _getEntity() {
		return UtilUserGroup.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initObject() {
		super._initObject();
		// privilege
		userGroupBo.load((UtilUserGroup) objEntity);
	}

	@Override
	protected void _initEditor() {
		super._initEditor();
		// privilege
		_initTblPrivilege();
	}

	protected void _initTblPrivilege() {
		tblPrivilege = newCCTable(getContainer(), "tblPrivilege", UtilPrivilege.class);
		tblPrivilege.setVisibleField(false, "name");
		tblPrivilege.addField(0, new CCCustomField() {
			@Override
			public Object getCustomView(Object object) {
				UtilPrivilege privilege = (UtilPrivilege) object;
				Checkbox checkbox = new Checkbox();
				checkbox.setChecked(privilege.isSelected());
				addEventListenerOnClick(checkbox, elSelect);
				return checkbox;
			}
		});
		tblPrivilege.addField(1, new CCCustomField() {
			@Override
			public String getLabel() {
				return "Privilege";
			}

			@Override
			public Object getCustomView(Object object) {
				UtilPrivilege privilege = (UtilPrivilege) object;
				Label label = new Label(privilege.getName());
				if (isNotNull(privilege.getParent())) {
					setStyle(label, "padding-left:10px;");
				}
				return label;
			}
		});
		tblPrivilege.setCheckboxColumn(true, 0);
		tblPrivilege.setColumnWidth(0, 40);
	}

	@Override
	protected void _doUpdateAccessible() {
		super._doUpdateAccessible();
		// privilege
		tblPrivilege.setEditableColumn(!readonly, 0);
	}

	@Override
	protected void _doUpdateComponent() {
		super._doUpdateComponent();
		// privilege
		_doUpdateTblPrivilege();
	}

	protected void _doUpdateTblPrivilege() {
		tblPrivilege.setList(userGroupBo.getPrivileges());
	}

	protected void _doSelect(boolean selected) {
		UtilPrivilege privilege = (UtilPrivilege) tblPrivilege.getSelectedItem();
		privilege.setSelected(selected);
		tblPrivilege.reloadSelectedItem();
		if (selected) {
			if (isNotNull(privilege.getParent())) {
				UtilPrivilege parent = privilege.getParent();
				parent.setSelected(selected);
				tblPrivilege.reloadItem(userGroupBo.getPrivilegeIndex(parent));
			}
		} else {
			if (isNotNullAndNotEmpty(privilege.getChilds())) {
				for (UtilPrivilege child : privilege.getChilds()) {
					child.setSelected(selected);
					tblPrivilege.reloadItem(userGroupBo.getPrivilegeIndex(child));
				}
			}
		}
	}

	@Override
	protected boolean _doSaveEntity() {
		return userGroupBo.save();
	}
}