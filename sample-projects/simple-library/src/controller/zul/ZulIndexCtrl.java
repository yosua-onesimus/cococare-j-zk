package controller.zul;

import model.mdl.lib.LibraryMain;

import org.zkoss.zul.Window;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class ZulIndexCtrl extends Window {
	public void onCreate() {
		new LibraryMain().showScreen();
	}
}