package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCClass;
import static cococare.common.CCClass.instanceOf;
import static cococare.common.CCFinal.*;
import static cococare.common.CCLogic.isNull;
import cococare.common.CCTrackable;
import cococare.zk.CCZk;
import static cococare.zk.CCZk.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.*;
//</editor-fold>

/**
 * CFZkMap is a class that contains a mapping for the zk so easy retrieval of common components.
 *
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class CFZkMap {

// <editor-fold defaultstate="collapsed" desc=" private enum ">
    private enum ClassType {

        MODEL_OBJ("model.obj", ""),
        MODEL_DAO("model.dao", "Dao"),
        MODEL_BO("model.bo", "Bo"),
        VIEW_ZUL("view/zul", ".zul"),
        VIEW_ZUL_LIST("view/zul", "List.zul"),
        CONTROLLER_ZUL("controller.zul", "Ctrl"),
        CONTROLLER_ZUL_LIST("controller.zul", "ListCtrl");
        private String prefix;
        private String suffix;

        private ClassType(String prefix, String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }

        private String getPrefix() {
            return prefix;
        }

        private String getSuffix() {
            return suffix;
        }
    }
// </editor-fold>
    //
//<editor-fold defaultstate="collapsed" desc=" private object ">
    private static String PATH_MAIN_SCREEN = "//pgIndex/ZulIndex/";
//</editor-fold>

// <editor-fold defaultstate="collapsed" desc=" zk map ">
    private static boolean _isClassType(String className, ClassType classType) {
        return className.contains(classType.getPrefix()) && className.endsWith(classType.getSuffix());
    }

    /**
     * @param clazz CONTROLLER_ZUL | VIEW_ZUL_LIST | VIEW_ZUL
     * @return VIEW_ZUL
     */
    public static String getZul(Class clazz) {
        String className = clazz.getName();
        String viewForm = null;
        if (_isClassType(className, ClassType.CONTROLLER_ZUL)) {
            viewForm = className.replace(".", "/").replaceFirst(ClassType.CONTROLLER_ZUL.getPrefix(), ClassType.VIEW_ZUL.getPrefix()).replaceFirst(ClassType.CONTROLLER_ZUL.getSuffix(), ClassType.VIEW_ZUL.getSuffix());
        } else if (_isClassType(className, ClassType.VIEW_ZUL_LIST)) {
            viewForm = className.replaceFirst(ClassType.VIEW_ZUL_LIST.getSuffix(), ClassType.VIEW_ZUL.getSuffix());
        } else if (_isClassType(className, ClassType.VIEW_ZUL)) {
            viewForm = className.replaceFirst(ClassType.VIEW_ZUL.getSuffix(), ClassType.VIEW_ZUL_LIST.getSuffix());
        }
        return viewForm;
    }

    /**
     * @param controllerClass CONTROLLER_ZUL | CONTROLLER_ZUL_LIST
     * @return Component
     */
    public static Component newContainer(Class controllerClass) {
        Component container = createComponents(getZul(controllerClass), null, null);
        while (isNull(container) && instanceOf(CCTrackable.class, controllerClass.getSuperclass())) {
            container = createComponents(getZul(controllerClass = controllerClass.getSuperclass()), null, null);
        }
        return container;
    }

    /**
     * @param clazz VIEW_ZUL | CONTROLLER_ZUL_LIST | CONTROLLER_ZUL
     * @return CONTROLLER_ZUL
     */
    public static Class<? extends CFZkCtrl> getControllerZul(Class clazz) {
        String className = clazz.getName();
        String controllerForm = null;
        if (_isClassType(className, ClassType.VIEW_ZUL)) {
            controllerForm = className.replaceFirst(ClassType.VIEW_ZUL.getSuffix(), ClassType.CONTROLLER_ZUL.getSuffix()).replaceFirst(ClassType.VIEW_ZUL.getPrefix(), ClassType.CONTROLLER_ZUL.getPrefix()).replace("/", ".");
        } else if (_isClassType(className, ClassType.CONTROLLER_ZUL_LIST)) {
            controllerForm = className.replaceFirst(ClassType.CONTROLLER_ZUL_LIST.getSuffix(), ClassType.CONTROLLER_ZUL.getSuffix());
        } else if (_isClassType(className, ClassType.CONTROLLER_ZUL)) {
            controllerForm = className.replaceFirst(ClassType.CONTROLLER_ZUL.getSuffix(), ClassType.CONTROLLER_ZUL_LIST.getSuffix());
        }
        return CCClass.getClass(controllerForm);
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc=" zk path ">
    /**
     * Initial a main screen.
     *
     * @param zulMainPath the zulMainPath.
     */
    public static void initMainScreen(String zulMainPath) {
        PATH_MAIN_SCREEN = zulMainPath;
    }

    /**
     * Returns a main screen.
     *
     * @return the main screen.
     */
    public static Component getMainScreen() {
        return Path.getComponent(PATH_MAIN_SCREEN);
    }

    /**
     * Returns a header.
     *
     * @return the header.
     */
    public static Component getHeader() {
        return getComponent(getMainScreen(), aHeader);
    }

    /**
     * Returns a appl logo.
     *
     * @return the appl logo.
     */
    public static Image getApplLogo() {
        return getImage(getMainScreen(), aApplLogo);
    }

    /**
     * Returns a appl name.
     *
     * @return the appl name.
     */
    public static Label getApplName() {
        return getLabel(getMainScreen(), aApplName);
    }

    /**
     * Returns a appl ver.
     *
     * @return the appl ver.
     */
    public static Label getApplVer() {
        return getLabel(getMainScreen(), aApplVer);
    }

    /**
     * Returns a comp logo.
     *
     * @return the comp logo.
     */
    public static Image getCompLogo() {
        return getImage(getMainScreen(), aCompLogo);
    }

    /**
     * Returns a comp name.
     *
     * @return the comp name.
     */
    public static Label getCompName() {
        return getLabel(getMainScreen(), aCompName);
    }

    /**
     * Returns a menu bar.
     *
     * @return the menu bar.
     */
    public static Menubar getMenubarH() {
        return getMenubar(getMainScreen(), aMenuBarH);
    }

    /**
     * Returns a menu bar.
     *
     * @return the menu bar.
     */
    public static LayoutRegion getMenubarV() {
        return getLayoutRegion(getMainScreen(), aMenuBarV);
    }

    /**
     * Returns a content.
     *
     * @return the content.
     */
    public static Component getContent() {
        return getComponent(getMainScreen(), aContent);
    }

    /**
     * Returns a footer.
     *
     * @return the footer.
     */
    public static Component getFooter() {
        return getComponent(getMainScreen(), aFooter);
    }

    /**
     * Returns a file transfer.
     *
     * @return the file transfer.
     */
    public static A getFileTransfer() {
        return getA(getMainScreen(), aFileTransfer);
    }

    /**
     * Returns a send mail.
     *
     * @return the send mail.
     */
    public static A getSendMail() {
        return getA(getMainScreen(), aSendMail);
    }

    /**
     * Returns a bug report.
     *
     * @return the bug report.
     */
    public static A getBugReport() {
        return getA(getMainScreen(), aBugReport);
    }

    /**
     * Returns a progressmeter.
     *
     * @return the progressmeter.
     */
    public static Progressmeter getProgressmeter() {
        return CCZk.getProgressmeter(getMainScreen(), aProgressBar);
    }
// </editor-fold>
}