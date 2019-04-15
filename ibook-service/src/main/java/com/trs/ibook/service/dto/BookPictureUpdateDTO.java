package com.trs.ibook.service.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.trs.ibook.service.example.BookPictureExample.*;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-9 14:04
 */
public class BookPictureUpdateDTO {

    @ApiModelProperty(notes = N_ID, example = E_ID)
    @NotNull
    private Integer id;

    @ApiModelProperty(notes = N_CATALOGID, example = E_CATALOGID)
    @NotNull
    private Integer catalogId;

    @ApiModelProperty(notes = N_PICURL, example = E_PICURL)
    @NotNull
    private String picUrl;

    @ApiModelProperty(notes = N_PAGEINDEX, example = E_PAGEINDEX)
    @NotNull
    private Integer pageIndex;

    @ApiModelProperty(notes = N_SERIALNO, example = E_SERIALNO)
    @NotNull
    private Integer serialNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }
}
