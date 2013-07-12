package controller.zul.inv;

import static cococare.common.CCFormat.formatInteger;
import static cococare.common.CCFormat.parseInt;
import static cococare.zk.CCZk.addEventListenerOnChanging2nd;
import model.bo.inv.InvInventoryBo;
import model.obj.inv.InvInventory;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Intbox;

import cococare.framework.zk.CFZkCtrl;

public class ZulInventoryCtrl extends CFZkCtrl {
	private InvInventoryBo inventoryBo;
	private Intbox txtQuantityTotal;
	private Intbox txtQuantityUsed;
	private Intbox txtQuantityAvailable;

	@Override
	protected Class _getEntity() {
		return InvInventory.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	protected void _initObject() {
		super._initObject();
		//
		inventoryBo.load((InvInventory) objEntity);
	}

	@Override
	protected void _initListener() {
		super._initListener();
		//
		addEventListenerOnChanging2nd(txtQuantityTotal, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				txtQuantityAvailable.setText(formatInteger(parseInt(txtQuantityTotal.getText()) - parseInt(txtQuantityUsed.getText())));
			}
		});
	}

	@Override
	protected boolean _doSaveEntity() {
		return inventoryBo.save();
	}
}