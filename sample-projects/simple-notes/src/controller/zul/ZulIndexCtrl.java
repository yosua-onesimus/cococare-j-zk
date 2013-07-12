package controller.zul;

import org.zkoss.zul.Window;

import cococare.framework.model.mdl.note.NotesMain4Zk;
import cococare.zk.datafile.CCFile;

public class ZulIndexCtrl extends Window {
	public void onCreate() {
		new NotesMain4Zk().showScreen();
	}

	public static void main(String args[]) {
		System.out.println(CCFile.getApplPath());
	}
}