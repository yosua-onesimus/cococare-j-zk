package controller.zul.lib;

import model.obj.lib.LibReport;
import model.obj.lib.LibReport.Report;

import org.zkoss.zul.Row;

import cococare.common.CCFieldConfig;
import cococare.framework.zk.controller.zul.ZulReportDefaultListCtrl;
import cococare.zk.CCBandbox;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulReportListCtrl extends ZulReportDefaultListCtrl {

	private Row rowBook;
	private CCBandbox bndBook;
	private Row rowMember;
	private CCBandbox bndMember;

	@Override
	protected Class _getEntity() {
		return LibReport.class;
	}

	@Override
	protected Class _getReportEnum() {
		return Report.class;
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
}