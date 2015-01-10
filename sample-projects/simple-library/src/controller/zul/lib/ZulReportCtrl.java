package controller.zul.lib;

import static cococare.zk.CCZk.setIframeContent;
import model.obj.lib.LibReport;

import org.zkoss.zul.Iframe;

import cococare.framework.zk.CFZkCtrl;

public class ZulReportCtrl extends CFZkCtrl {
	private Iframe frmReport;

	@Override
	protected Class _getEntity() {
		return LibReport.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected ShowMode _getShowMode() {
		return (callerCtrl instanceof ZulReportListCtrl) ? ShowMode.TAB_MODE : ShowMode.DIALOG_MODE;
	}

	@Override
	protected void _initComponent() {
		super._initComponent();
		setIframeContent(frmReport, ((LibReport) objEntity).newReport().getPdfStream());
	}

	@Override
	protected String _getSysRef(Object objEntity) {
		return (callerCtrl instanceof ZulReportListCtrl) ? parameter.get(toString()).toString() : "";
	}

	@Override
	protected String _getTabTitle() {
		return ((LibReport) objEntity).getReportName();
	}
}