package com.web.ext.mvc;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.web.annotation.ControllerBind;
import com.web.core.Routes;
import com.web.kit.StrKit;
import com.web.log.Logger;
import com.web.mvc.Controller;

public class AutoBindRoutes extends Routes {

    private boolean autoScan = true;

    private List<Class<? extends Controller>> excludeClasses = Lists.newArrayList();

    private boolean includeAllJarsInLib;

    private List<String> includeJars = Lists.newArrayList();

    protected final Logger logger = Logger.getLogger(getClass());

    private String suffix = "Controller";

	private String libDir;
    
    public AutoBindRoutes(){}
    
    public AutoBindRoutes(boolean auto){
    	
    	this.autoScan=auto;
    }

    public AutoBindRoutes autoScan(boolean autoScan) {
        this.autoScan = autoScan;
        return this;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void config() {
        List<Class<? extends Controller>> controllerClasses = ClassSearcher.of(Controller.class).libDir(libDir)
                .includeAllJarsInLib(includeAllJarsInLib).injars(includeJars).search();
        logger.info(" serach  controller class ="+controllerClasses);
        
        ControllerBind controllerBind = null;
        for (Class controller : controllerClasses) {
            if (excludeClasses.contains(controller)) {
                continue;
            }
            controllerBind = (ControllerBind) controller.getAnnotation(ControllerBind.class);
            if (controllerBind == null) {
                if (!autoScan) {
                    continue;
                }
                
                this.add(controllerKey(controller), controller);
                logger.debug("routes.add(" + controllerKey(controller) + ", " + controller.getName() + ")");
            } else if (StrKit.isBlank(controllerBind.viewPath())) {
                this.add(controllerBind.controllerKey(), controller);
                logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller.getName() + ")");
            } else {
                this.add(controllerBind.controllerKey(), controller, controllerBind.viewPath());
                logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller + ","
                        + controllerBind.viewPath() + ")");
            }
        }
    }

    private String controllerKey(Class<Controller> clazz) {
    	
        Preconditions.checkArgument(clazz.getSimpleName().endsWith(suffix),
                " does not has a @ControllerBind annotation and it's name is not end with " + suffix);
        String controllerKey = "/" + StrKit.firstCharToLowerCase(clazz.getSimpleName());
        
        controllerKey = controllerKey.substring(0, controllerKey.indexOf(suffix));
        
        return controllerKey;
    }
    
    
    public AutoBindRoutes includeAllJarsInLib(boolean includeAllJarsInLib) {
        this.includeAllJarsInLib = includeAllJarsInLib;
        return this;
    }

    public AutoBindRoutes suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

}
