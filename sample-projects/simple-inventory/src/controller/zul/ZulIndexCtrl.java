package controller.zul;

import model.mdl.inv.InventoryMain;

import org.zkoss.zul.Window;

import cococare.zk.datafile.CCFile;

public class ZulIndexCtrl extends Window {
	public void onCreate() {
		new InventoryMain().showScreen();
	}

	public static void main(String args[]) {
		System.out.println(CCFile.getApplPath());
	}
}