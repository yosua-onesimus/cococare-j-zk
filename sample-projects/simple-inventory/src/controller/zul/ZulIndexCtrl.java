package controller.zul;

import model.mdl.InventoryMain;

import org.zkoss.zul.Window;

public class ZulIndexCtrl extends Window {
	public void onCreate() {
		if (InventoryMain.INSTANCE == null) {
			InventoryMain.INSTANCE = new InventoryMain();
		}
		InventoryMain.INSTANCE.showScreen();
	}
}