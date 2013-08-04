package controller.zul;

import model.mdl.bljr.BljrMain;

import org.zkoss.zul.Window;

public class ZulIndexCtrl extends Window {
	public void onCreate() {
		new BljrMain().showScreen();
	}
}