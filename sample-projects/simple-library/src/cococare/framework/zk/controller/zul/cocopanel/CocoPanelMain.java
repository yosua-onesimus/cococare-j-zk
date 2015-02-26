package cococare.framework.zk.controller.zul.cocopanel;

import cococare.framework.common.CFApplUae;
import cococare.framework.zk.CFZkMain;
import cococare.framework.zk.CFZkMap;
import cococare.zk.CCZk;

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class CocoPanelMain extends CFZkMain {
	@Override
	protected void _loadInternalSetting() {
		CCZk.setImageContent(CFZkMap.getApplLogo(), "/cococare/resource/icon-cococare.jpg");
		CFZkMap.getApplName().setValue("CocoPanel");
		CFZkMap.getCompName().setValue("Yggdrasil Solution");
		CCZk.setImageContent(CFZkMap.getCompLogo(), "/cococare/resource/icon-cococare.jpg");
		// cek cp user

		APPL_LOGO = "/cococare/resource/icon-cococare.jpg";
		APPL_VER = "1.0.130317";
		APPL_CODE = "xxx";
		APPL_NAME = "yyy";
		super._loadInternalSetting();
	}

	@Override
	protected void _loadExternalSetting() {

	}

	@Override
	protected void _initDatabaseEntity() {

	}

	@Override
	protected void _initInitialUaeBody(CFApplUae uae) {

	}

	@Override
	protected void _applyUserConfigUaeBody(CFApplUae uae) {

	}
}