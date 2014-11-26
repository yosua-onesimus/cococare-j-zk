package controller.zul;

import model.mdl.lib.LibraryMain;

import org.zkoss.zul.Window;

public class ZulIndexCtrl extends Window {
	public void onCreate() {
		new LibraryMain().showScreen();
	}
}