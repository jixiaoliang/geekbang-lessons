package org.geektimes.projects.user.service.dao.bean;

import java.math.BigDecimal;
import java.util.Date;

public class WacWaveReport {
    private Integer id;

    private Date recordDate;

    private String ownerId;

    private Integer syscode;

    private BigDecimal costPriceTotal;

    private BigDecimal qtyTotal;

    private BigDecimal unitCostTotal;

    private Date createdTime;

    private Date updatedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public Integer getSyscode() {
        return syscode;
    }

    public void setSyscode(Integer syscode) {
        this.syscode = syscode;
    }


    public BigDecimal getQtyTotal() {
        return qtyTotal;
    }

    public BigDecimal getCostPriceTotal() {
        return costPriceTotal;
    }

    public void setCostPriceTotal(BigDecimal costPriceTotal) {
        this.costPriceTotal = costPriceTotal;
    }

    public BigDecimal getUnitCostTotal() {
        return unitCostTotal;
    }

    public void setUnitCostTotal(BigDecimal unitCostTotal) {
        this.unitCostTotal = unitCostTotal;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }


    public void setQtyTotal(BigDecimal qtyTotal) {
        this.qtyTotal = qtyTotal;
    }

    public Date getRecordDate() {
        return recordDate;
    }
}