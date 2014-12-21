package controller.zul.lib;

import static cococare.zk.CCZk.addListener;
import model.obj.lib.LibReport;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;

import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCEditor;

public class ZulReportListCtrl extends CFZkCtrl {
	private Button _btnView;

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
		super._initComponent();
		edtEntity = new CCEditor(getContainer(), LibReport.class);
	}

	@Override
	protected void _initListener() {
		super._initListener();
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