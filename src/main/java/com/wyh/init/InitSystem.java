package com.wyh.init;

import com.wyh.Service.ArcTypeService;
import com.wyh.Service.LinkService;
import com.wyh.entity.ArcType;
import com.wyh.entity.Link;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化加载数据
 * @author wyh
 */
@Component("initSystem")
public class InitSystem implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Map<Integer, ArcType> arcTypeMap = new HashMap<Integer, ArcType>();

    /**
     * 加载数据到application缓存中
     * @param application
     */
    public void loadData(ServletContext application) {
        ArcTypeService arcTypeService = (ArcTypeService) applicationContext.getBean("arcTypeService");
        LinkService linkService = (LinkService) applicationContext.getBean("linkService");
        List<ArcType> allArcTypeList = arcTypeService.listAll(Sort.Direction.ASC, "sort");
        List<Link> allLinkList = linkService.listAll(Sort.Direction.ASC, "sort");
        for (ArcType arcType : allArcTypeList) {
            arcTypeMap.put(arcType.getId(), arcType);
        }
        application.setAttribute("allArcTypeList", allArcTypeList);
        application.setAttribute("allLinkList", allLinkList);
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
