package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import static cococare.common.CCLogic.isNotNullAndNotEmpty;
import static cococare.datafile.CCFile.getApplPath;
import cococare.datafile.CCSetup;
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
        setup.setSetupPath(isNotNullAndNotEmpty(setupPath) ? setupPath : (getApplPath() + "\\WebRoot"));
        setup.initSetupMap();
        return setup.execute();
    }

    public static void main(String[] args) {
        if (args.length > 0 && "WebRoot".equalsIgnoreCase(args[0])) {
            executeWebRootFile(args.length > 1 ? args[1] : null);
        }
    }
}