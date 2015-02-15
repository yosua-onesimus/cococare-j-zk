package controller.zul.lib;

import static cococare.zk.CCZk.addListener;
import static cococare.zk.CCZk.initSpecialComponent;
import model.obj.lib.LibReport;
import model.obj.lib.LibReport.Report;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Row;

import cococare.common.CCFieldConfig;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCBandbox;
import cococare.zk.CCEditor;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulReportListCtrl extends CFZkCtrl {

	private Combobox cmbReport;
	private Button _btnView;
	private Row rowBook;
	private CCBandbox bndBook;
	private Row rowMember;
	private CCBandbox bndMember;

	@Override
	protected Class _getEntity() {
		return LibReport.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.LIST_FUNCTION;
	}

	@Override
	protected void _initComponent() {
		edtEntity = new CCEditor(getContainer(), LibReport.class);
		initSpecialComponent(getContainer(), this);
		super._initComponent();
	}

	@Override
	protected void _doUpdateAccessible() {
		int selectedIndex = cmbReport.getSelectedIndex();
		edtEntity.setAccessible(bndBook.getBandbox(), selectedIndex == Report.BOOK_HISTORY.ordinal() ? CCFieldConfig.Accessible.MANDATORY : CCFieldConfig.Accessible.NORMAL);
		edtEntity.setAccessible(bndMember.getBandbox(), selectedIndex == Report.MEMBER_HISTORY.ordinal() ? CCFieldConfig.Accessible.MANDATORY : CCFieldConfig.Accessible.NORMAL);
		rowBook.setVisible(selectedIndex == Report.BOOK_HISTORY.ordinal());
		bndBook.getBandbox().setVisible(selectedIndex == Report.BOOK_HISTORY.ordinal());
		rowMember.setVisible(selectedIndex == Report.MEMBER_HISTORY.ordinal());
		bndMember.getBandbox().setVisible(selectedIndex == Report.MEMBER_HISTORY.ordinal());
	}

	@Override
	protected void _initListener() {
		super._initListener();
		addListener(cmbReport, new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doUpdateAccessible();
			}
		});
		addListener(_btnView, new EventListener() {
			public void onEvent(Event event) throws Exception {
				_doView();
			}
		});
	}

	@Override
	protected void _doView() {
		if (edtEntity.isValueValid()) {
			ZulReportCtrl reportCtrl = new ZulReportCtrl();
			parameter.put(reportCtrl.toString(), zkView.getTabEntity().getTabs().getChildren().size());
			reportCtrl.with(parameter).with(this).init(edtEntity.getValueFromEditor());
		}
	}
}