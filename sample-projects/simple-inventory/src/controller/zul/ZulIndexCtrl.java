package controller.zul;

import model.mdl.inv.InventoryMain;

import org.zkoss.zul.Window;

public class ZulIndexCtrl extends Window {
	public void onCreate() {
		new InventoryMain().showScreen();
	}
}