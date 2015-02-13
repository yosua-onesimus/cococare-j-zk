package cococare.framework.zk.controller.zul.cocopanel;

import org.zkoss.zul.Window;

public class ZulIndexCtrl extends Window {
	public void onCreate() {
		new CocoPanelMain().showScreen();
	}
}