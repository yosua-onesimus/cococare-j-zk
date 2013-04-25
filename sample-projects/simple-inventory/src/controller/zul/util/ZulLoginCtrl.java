package controller.zul.util;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import cococare.framework.common.CFApplCtrl;
import cococare.framework.model.obj.util.UtilUser;
import cococare.framework.zk.CFZkCtrl;
import cococare.zk.CCMessage;
import cococare.zk.CCZk;

public class ZulLoginCtrl extends CFZkCtrl {
	private Textbox txtUsername;
	private Textbox txtPassword;
	private Button btnLogin;

	@Override
	protected Class _getEntity() {
		return UtilUser.class;
	}

	@Override
	protected BaseFunction _getBaseFunction() {
		return BaseFunction.FORM_FUNCTION;
	}

	@Override
	public boolean init() {
		CFApplCtrl.INSTANCE.logout();
		return super.init();
	}

	@Override
	protected void _initListener() {
		super._initListener();
		EventListener elLogin = new EventListener() {
			public void onEvent(Event arg0) throws Exception {
				_doLogin();
			}
		};
		CCZk.addEventListenerOnOk(txtUsername, elLogin);
		CCZk.addEventListenerOnOk(txtPassword, elLogin);
		CCZk.addEventListenerOnClick(btnLogin, elLogin);
	}

	protected void _doLogin() {
		if (_hasEdtEntity()) {
			if (_isValueValid()) {
				_getValueFromEditor();
				UtilUser user = (UtilUser) objEntity;
				if (updateCaller = CFApplCtrl.INSTANCE.login(user.getUsername(), user.getPassword())) {
					_logger(objEntity);
					_doCloseScreen();
				} else {
					CCMessage.showError();
				}
			}
		}
	}
}