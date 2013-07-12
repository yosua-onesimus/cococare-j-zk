package controller.zul;

import org.zkoss.zul.Window;

import cococare.framework.model.mdl.note.NotesMain4Zk;

public class ZulIndexCtrl extends Window {
	public void onCreate() {
		// cococare-j-zk has application notes that can be used for notes about
		// the making of the application.
		new NotesMain4Zk().showScreen();
	}
}