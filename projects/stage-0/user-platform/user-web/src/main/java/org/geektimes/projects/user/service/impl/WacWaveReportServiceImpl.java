package org.geektimes.projects.user.service.impl;

import org.geektimes.projects.user.service.WacWaveReportService;
import org.geektimes.projects.user.service.dao.WacWaveReportDao;
import org.geektimes.projects.user.service.dao.bean.WacWaveReport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jixiaoliang
 * @since 2021/05/18
 **/
@Service
public class WacWaveReportServiceImpl implements WacWaveReportService {

    @Resource
    private WacWaveReportDao wacWaveReportDao;

    @Override
    public List<WacWaveReport> queryList(Date date) {
        return wacWaveReportDao.queryList(date);
    }
}
