package com.trs.ibook.service.dto;

import com.trs.ibook.core.dto.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import static com.trs.ibook.service.example.BookInfoExample.*;

/**
 * Title:新增电子书信息参数DTO
 * Description:新增电子书信息参数DTO
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-28 17:37
 */
@ApiModel(description = "新增【电子书信息】的参数")
public class BookInfoAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_TITLENAME, example = E_TITLENAME)
    @NotBlank(message = "电子书标题不能为空")
    @Length(max = 30, message = "titleName最大长度不能超过{max}")
    private String titleName;

    @ApiModelProperty(notes = N_INTRODUCTION, example = E_INTRODUCTION)
    @NotBlank(message = "简介不能为空")
    @Length(max = 100, message = "introduction最大长度不能超过{max}")
    private String introduction;

    @ApiModelProperty(notes = N_AUTHOR, example = E_AUTHOR)
    @NotBlank(message = "作者不能为空")
    @Length(max = 10, message = "author最大长度不能超过{max}")
    private String author;

    @ApiModelProperty(notes = N_LABELCATEGORY, example = E_LABELCATEGORY)
    @NotBlank(message = "标签分类不能为空")
    @Length(max = 20, message = "labelCategory最大长度不能超过{max}")
    private String labelCategory;

    @ApiModelProperty(notes = N_PERIODICAL, example = E_PERIODICAL)
    private Integer periodical;

    @ApiModelProperty(notes = N_LOCATIONNAME, example = E_LOCATIONNAME)
    private String locationName;

    @ApiModelProperty(notes = N_PDFURL, example = E_PDFURL)
    private String pdfUrl;

    @ApiModelProperty(notes = N_COVERURL, example = E_COVERURL)
    private String coverUrl;

    @ApiModelProperty(notes = N_SITEID, example = E_SITEID)
    @NotBlank(message = "站点id不能为空")
    @Length(max = 10, message = "siteId最大长度不能超过{max}")
    private Integer siteId;

    @ApiModelProperty(notes = N_STATUS, example = E_STATUS)
    private Integer status;

    @ApiModelProperty(notes = N_CREATETIME, example = E_CREATETIME)
    private Date createTime;

    @ApiModelProperty(notes = N_CREATEUSERID, example = E_CREATEUSERID)
    private Long createUserId;

    @ApiModelProperty(notes = N_ISDELETE, example = E_ISDELETE)
    private Integer isDelete;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLabelCategory() {
        return labelCategory;
    }

    public void setLabelCategory(String labelCategory) {
        this.labelCategory = labelCategory;
    }

    public Integer getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Integer periodical) {
        this.periodical = periodical;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
