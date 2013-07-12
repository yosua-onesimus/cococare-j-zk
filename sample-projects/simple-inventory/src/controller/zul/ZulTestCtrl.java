package controller.zul;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import cococare.zk.CCSession;
import cococare.zk.CCTable;
import cococare.zk.CCZk;

public class ZulTestCtrl extends Window {
	private Textbox txtUsername;
	private Button btnLogin;
	private Button btnRefresh;
	private CCTable tblTest;

	public void onCreate() {
		init(this);
	}

	private void init(Component zulIndex) {
		CCZk.initComponent(this, this);
		CCZk.addEventListenerOnClick(btnLogin, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				doLogin();
			}
		});
		CCZk.addEventListenerOnClick(btnRefresh, new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				doRefresh();
			}
		});
		tblTest = new CCTable(CCZk.getMeshElement(this, "tblTest"), "server name", "ip", "username");
	}

	private void doLogin() {
		CCSession.regSession();
		CCSession.setAttribute("username", txtUsername.getText());
	}

	private void doRefresh() {
		tblTest.removeRows();
		for (Session session : CCSession.getRegSession()) {
			tblTest.addRow(session.getServerName(), session.getRemoteAddr(), session.getAttribute("username"));
		}
	}
}