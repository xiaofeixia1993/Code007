package com.wyh.init;

import com.wyh.Service.ArcTypeService;
import com.wyh.entity.ArcType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * 初始化加载数据
 * @author wyh
 */
@Component("initSystem")
public class InitSystem implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 加载数据到application缓存中
     * @param application
     */
    public void loadData(ServletContext application) {
        ArcTypeService arcTypeService = (ArcTypeService) applicationContext.getBean("arcTypeService");
        List<ArcType> allArcTypeList = arcTypeService.listAll(Sort.Direction.ASC, "sort");
        application.setAttribute("allArcTypeList", allArcTypeList);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.loadData(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
