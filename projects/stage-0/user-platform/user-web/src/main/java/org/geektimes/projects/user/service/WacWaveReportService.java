package org.geektimes.projects.user.service;

import org.geektimes.projects.user.service.dao.bean.WacWaveReport;

import java.util.Date;
import java.util.List;

/**
 * @author jixiaoliang
 * @since 2021/05/18
 **/
public interface WacWaveReportService {

    List<WacWaveReport> queryList(Date date);

}
