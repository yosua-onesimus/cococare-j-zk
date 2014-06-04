package controller.zul.lib;

import static cococare.common.CCClass.getIds;
import static cococare.zk.CCZk.getCCBandbox;
import model.bo.lib.LibReturningBo;
import model.obj.lib.LibReturning;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;

import cococare.common.CCFieldConfig.Accessible;
import cococare.database.CCHibernateFilter;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCBandbox;

public class ZulReturningCtrl extends CFZkCtrl {
	private LibReturningBo returningBo;
	private Datebox dtpDate;
	private CCBandbox bndMember;
	private Intbox txtTotalItem;
	private Doublebox txtTotalFine;

	@Override
	protected Class _getEntity() {
		return LibReturning.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		parameter.put(toString() + "dtpDate", dtpDate);
		parameter.put(toString() + "bndMember", bndMember);
		parameter.put(toString() + "txtTotalItem", txtTotalItem);
		parameter.put(toString() + "txtTotalFine", txtTotalFine);
		parameter.put(toString() + "returningInfo-MANDATORY", new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doUpdateReturningInfo(Accessible.MANDATORY);
			}
		});
		parameter.put(toString() + "returningInfo-MANDATORY_READONLY", new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doUpdateReturningInfo(Accessible.MANDATORY_READONLY);
			}
		});
		_addChildScreen("returning", new ZulReturningItemListCtrl(), "zulReturningItem");
	}

	@Override
	protected void _initEditor() {
		super._initEditor();
		bndMember = getCCBandbox(getContainer(), "bndMember");
		bndMember.getTable().setHqlFilters(new CCHibernateFilter() {
			@Override
			public String getFieldName() {
				return "id";
			}

			@Override
			public String getParameterName() {
				return "ids";
			}

			@Override
			public Object getFieldValue() {
				return getIds(returningBo.getUnlimitedBorrowingMembers());
			}
		});
	}

	@Override
	protected boolean _doSaveEntity() {
		return returningBo.saveOrUpdate((LibReturning) objEntity, _getEntityChilds());
	}

	private void _doUpdateReturningInfo(Accessible accessible) {
		edtEntity.setAccessible(dtpDate, accessible);
		edtEntity.setAccessible(bndMember.getBandbox(), accessible);
	}
}