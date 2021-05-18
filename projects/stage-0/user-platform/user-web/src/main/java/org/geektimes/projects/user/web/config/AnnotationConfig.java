package org.geektimes.projects.user.web.config;

import org.geektimes.projects.user.mybatis.annotation.EnableMyBatis;
import org.geektimes.projects.user.service.WacWaveReportService;
import org.geektimes.projects.user.service.dao.bean.WacWaveReport;
import org.geektimes.serializer.jackson.JacksonSerializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author jixiaoliang
 * @since 2021/05/18
 **/
@EnableMyBatis(configLocation = "classpath:mybatis/mybatis-config.xml",
        dataSource = "dataSource",
        mapperLocations = "classpath*:mappers/*.xml",
        daoBasePackage = "org.geektimes.projects.user.service.dao"
)
@Component
public class AnnotationConfig implements ApplicationContextAware {

    Logger logger = Logger.getLogger(AnnotationConfig.class.getName());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private ApplicationContext applicationContext;

    @Resource
    private WacWaveReportService wacWaveReportService;

    @Value("${date}")
    private String date;

    @PostConstruct
    public void afterProcess() {
        try {
            logger.info("date:"+date);
            List<WacWaveReport> wacWaveReports = wacWaveReportService.queryList(dateFormat.parse(date));
            logger.info("wacWaveReports:" + JacksonSerializer.toJson(wacWaveReports));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
