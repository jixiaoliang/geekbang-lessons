package org.geektimes.projects.user.service.dao;

import org.apache.ibatis.annotations.Param;
import org.geektimes.projects.user.service.dao.bean.WacWaveReport;

import java.util.Date;
import java.util.List;

/**
 * @author jixiaoliang
 * @since 2021/05/18
 **/
public interface WacWaveReportDao {

    List<WacWaveReport> queryList(@Param("date") Date date);

}
