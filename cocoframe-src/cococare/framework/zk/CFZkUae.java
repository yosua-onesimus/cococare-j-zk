package cococare.framework.zk;

//<editor-fold defaultstate="collapsed" desc=" import ">
import cococare.common.CCAccessibleListener;
import static cococare.common.CCClass.newObject;
import static cococare.zk.CCFinal.iconMenuChild;
import static cococare.zk.CCFinal.iconMenuParent;
import static cococare.common.CCFormat.nextSequence;
import static cococare.common.CCFormat.toHumanizeCase;
import static cococare.common.CCLanguage.Not_supported_yet;
import static cococare.common.CCLanguage.turn;
import static cococare.common.CCLogic.*;
import static cococare.common.CCMessage.logp;
import static cococare.database.CCLoginInfo.INSTANCE_isCompAccessible;
import cococare.framework.common.CFApplUae;
import cococare.framework.common.CFViewCtrl;
import cococare.framework.model.bo.util.UtilPrivilegeBo;
import cococare.framework.model.obj.util.UtilPrivilege;
import static cococare.framework.zk.CFZkMap.newContainer;
import cococare.zk.CCMenubar;
import static cococare.zk.CCZk.*;
import java.util.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.impl.LabelImageElement;
//</editor-fold>

/**
 * @author Yosua Onesimus
 * @since 13.03.17
 * @version 13.03.17
 */
public class CFZkUae extends CFApplUae {

//<editor-fold defaultstate="collapsed" desc=" private class ">
//<editor-fold defaultstate="collapsed" desc=" MenuCandidate ">
    private static class MenuCandidate {

//<editor-fold defaultstate="collapsed" desc=" private object ">
        private Integer parentCode;
        private int code;
        private String label;
        private String icon;
        private Class<? extends CFViewCtrl> controllerClass;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" MenuCandidate ">
        public MenuCandidate(Integer parentCode, int code, String label, String icon, Class<? extends CFViewCtrl> controllerClass) {
            this.parentCode = parentCode;
            this.code = code;
            this.label = label;
            this.icon = icon;
            this.controllerClass = controllerClass;
        }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" getter-setter ">
        public Integer getParentCode() {
            return parentCode;
        }

        public int getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        public String getIcon() {
            return icon;
        }

        public Class<? extends CFViewCtrl> getControllerClass() {
            return controllerClass;
        }
//</editor-fold>
    }
//</editor-fold>
//</editor-fold>
    //
//<editor-fold defaultstate="collapsed" desc=" private object ">
//<editor-fold defaultstate="collapsed" desc=" register controllerClass to create privilege ">
    private String screenCode = "00";
    private String actionCode = "00";
    protected List<UtilPrivilege> privileges = new ArrayList();
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc=" register controllerClass to menuBar ">
    private CCMenubar menubar;
    private List<Class<? extends CFViewCtrl>> menuRoot = new ArrayList();
    private int pc = 1;
    private int cc = 1;
    private boolean leftSide = true;
    private HashMap<Integer, MenuCandidate> leftSideMenus = new LinkedHashMap();
    private HashMap<Integer, MenuCandidate> rightSideMenus = new LinkedHashMap();
    private List<Integer> separatorMenus = new ArrayList();
    private Integer separatorParentCode = null;
    private HashMap<MenuCandidate, Boolean> accessibles = new HashMap();
//</editor-fold>
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" private method ">
//<editor-fold defaultstate="collapsed" desc=" register controllerClass to create privilege ">
    private String _getScreenComp(Class controllerClass) {
        return controllerClass.getName();
    }

    private String _getScreenCode(String moduleCode) {
        screenCode = nextSequence(screenCode, false);
        return moduleCode + "." + screenCode + ".00";
    }

    private String _getActionComp(Class controllerClass, Component component) {
        return _getScreenComp(controllerClass) + "." + component.getId();
    }

    private String _getActionCode(String moduleCode) {
        actionCode = nextSequence(actionCode, false);
        return moduleCode + "." + screenCode + "." + actionCode;
    }

    private String _getActionName(Component component) {
        if (component instanceof Button) {
            return toHumanizeCase(component.getId().replaceFirst("btn", ""));
        } else if (component instanceof Menuitem) {
            return toHumanizeCase(component.getId().replaceFirst("mi", ""));
        } else {
            throw logp(new UnsupportedOperationException(turn(Not_supported_yet)));
        }
    }

    private boolean _isValidAction(Component component) {
        return (component instanceof Button && component.getId().startsWith("btn"))
                || (component instanceof Menuitem && component.getId().startsWith("mi"));
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" register controllerClass to menubar ">
    private MenuCandidate _addMenuCandidate(Integer parentCode, int code, String label, String icon, Class<? extends CFViewCtrl> controllerClass) {
        MenuCandidate menuCandidate = new MenuCandidate(parentCode, code, label, icon, controllerClass);
        if (leftSide) {
            leftSideMenus.put(code, menuCandidate);
        } else {
            rightSideMenus.put(code, menuCandidate);
        }
        return menuCandidate;
    }

    private void _addMenu(final MenuCandidate menuCandidate) {
        if (accessibles.get(menuCandidate)) {
            if (isNotNull(separatorParentCode) && separatorParentCode.equals(menuCandidate.getParentCode())) {
                menubar.addMenuSeparator(separatorParentCode);
            }
            menubar.addMenu(menuCandidate.getParentCode(), menuCandidate.getCode(),
                    menuCandidate.getLabel(), menuCandidate.getIcon(),
                    isNull(menuCandidate.getControllerClass()) ? null
                    : new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    newObject(menuCandidate.getControllerClass()).init();
                }
            });
            separatorParentCode = null;
        }
        if (isNull(separatorParentCode) && separatorMenus.contains(menuCandidate.getCode())) {
            separatorParentCode = menuCandidate.getParentCode();
        }
    }
//</editor-fold>
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" public method ">
//<editor-fold defaultstate="collapsed" desc=" register controllerClass to create privilege ">
    @Override
    public void reg(String moduleCode, String screenName, Class<? extends CFViewCtrl> controllerClass) {
        screenName = turn(screenName);
        Component container = newContainer(controllerClass);
        if (isNotNull(container)) {
            UtilPrivilege screen = new UtilPrivilege(_getScreenComp(controllerClass), _getScreenCode(moduleCode), screenName);
            for (Component component : container.getFellows()) {
                if (_isValidAction(component)) {
                    UtilPrivilege action = new UtilPrivilege(_getActionComp(controllerClass, component), _getActionCode(moduleCode), _getActionName(component));
                    screen.addChilds(action);
                }
            }
            privileges.add(screen);
            container.detach();
        }
    }

    @Override
    public boolean compile() {
        return new UtilPrivilegeBo().initInitialData(privileges);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" register controllerClass to menubar ">
    public void initMenuBar(CCMenubar menubar) {
        this.menubar = menubar;
        menubar.setVisible(true);
        menubar.clearMenu();
        menuRoot.clear();
        pc = 1;
        cc = 1;
        leftSide = true;
        leftSideMenus.clear();
        rightSideMenus.clear();
        separatorMenus.clear();
        separatorParentCode = null;
        accessibles.clear();
    }

    @Override
    public void addMenuRoot(Class<? extends CFViewCtrl>... controllerClasses) {
        menuRoot.addAll(Arrays.asList(controllerClasses));
    }

    public void addMenu(Integer parentCode, int code, String label, String icon, Class<? extends CFViewCtrl> controllerClass) {
        label = turn(label);
        if (isNull(parentCode)) {
            boolean isAccessible = false;
            if (isNotNull(controllerClass)) {
                if (!(isAccessible = menuRoot.contains(controllerClass) || isAccessible(controllerClass))) {
                    return;
                }
            }
            MenuCandidate menuCandidate = _addMenuCandidate(parentCode, code, label, icon, controllerClass);
            accessibles.put(menuCandidate, isAccessible);
        } else {
            MenuCandidate parent = (leftSide ? leftSideMenus.get(parentCode) : rightSideMenus.get(parentCode));
            if (isNotNull(controllerClass)) {
                boolean isAccessible = menuRoot.contains(controllerClass) || isAccessible(controllerClass);
//                if (!isAccessible) {
//                    return;
//                }
                MenuCandidate menuCandidate = _addMenuCandidate(parentCode, code, label, icon, controllerClass);
                accessibles.put(menuCandidate, isAccessible);
                accessibles.put(parent, accessibles.get(parent) || isAccessible);
            }
        }
    }

    @Override
    public void addMenuParent(String label, String icon, Class<? extends CFViewCtrl> controllerClass) {
        addMenu(null, pc = cc++, label, coalesce(icon, iconMenuParent), controllerClass);
    }

    @Override
    public void addMenuChild(String label, String icon, Class<? extends CFViewCtrl> controllerClass) {
        addMenu(pc, cc++, label, coalesce(icon, iconMenuChild), controllerClass);
    }

    public void addMenuSeparator(int code) {
        if (!separatorMenus.contains(code)) {
            separatorMenus.add(code);
        }
    }

    @Override
    public void addMenuSeparator() {
        addMenuSeparator(cc - 1);
    }

    @Override
    public void changeMenuSide() {
        leftSide = !leftSide;
    }

    @Override
    public void compileMenu() {
        for (MenuCandidate menuCandidate : leftSideMenus.values()) {
            _addMenu(menuCandidate);
        }
        menubar.addMenuGap();
        for (MenuCandidate menuCandidate : rightSideMenus.values()) {
            _addMenu(menuCandidate);
        }
    }
//</editor-fold>

    public boolean isAccessible(Class controllerClass) {
        return INSTANCE_isCompAccessible(_getScreenComp(controllerClass));
    }

    public boolean isAccessible(Class controllerClass, Component container) {
        boolean isAccessible = isAccessible(controllerClass);
        if (isAccessible) {
            for (Component component : container.getFellows()) {
                if (_isValidAction(component)) {
                    LabelImageElement labelImageElement = (LabelImageElement) component;
                    List accessibleListeners = getAccessibleListeners(labelImageElement);
                    if (isNull(accessibleListeners)) {
                        accessibleListeners = new ArrayList();
                    }
                    if (!INSTANCE_isCompAccessible(_getActionComp(controllerClass, component))) {
                        accessibleListeners.add(CCAccessibleListener.nonAccessible);
                    }
                    setAccessibleListeners(labelImageElement, accessibleListeners);
                    applyAccessible(labelImageElement);
                }
            }
        }
        return isAccessible;
    }
//</editor-fold>
}