package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCLogic.isNullOrEmpty;
import static cococare.datafile.CCFile.delete;
import static cococare.datafile.CCFile.getApplPath;
import cococare.datafile.CCSetup;
import java.io.File;
//</editor-fold>

public class CFSetup {

    /**
     * Execute setup for WebRoot file.
     *
     * @return true if success; false if fail.
     */
    public static boolean executeWebRootFile(String setupPath) {
        CCSetup setup = new CCSetup(CFSetup.class);
        setup.setResourcePath("WebRoot");
        if (isNullOrEmpty(setupPath)) {
            setupPath = getApplPath() + "\\WebRoot";
        }
        setup.initSetupMap(setupPath);
        return setup.execute() && delete(new File(getApplPath(), "files"));
    }
}