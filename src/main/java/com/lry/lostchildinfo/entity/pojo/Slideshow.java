package com.lry.lostchildinfo.entity.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author jdl
 * @since 2022-01-21
 */
@Getter
@Setter
@TableName("tbl_slideshow")
@ApiModel(value = "Slideshow对象", description = "")
public class Slideshow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("图片地址")
    @TableField("pic")
    @Excel(name = "图片地址")
    private String pic;

    @ApiModelProperty("内容")
    @TableField("context")
    @Excel(name = "内容")
    private String context;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("创建人id")
    @TableField("create_id")
    private Long createId;

    @ApiModelProperty("创建人")
    @TableField("create_code")
    private String createCode;

    @ApiModelProperty("更新人id")
    @TableField("update_id")
    private Long updateId;

    @ApiModelProperty("更新人")
    @TableField(value = "update_code")
    private String updateCode;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否在轮播中(0在轮播中,1不在轮播中)")
    @TableField("state")
    @Excel(name = "是否轮播(0在轮播,1不轮播)")
    private String state;

    @ApiModelProperty("是否已删除(0未删除,1已删除)")
    @TableField("deleted")
    @TableLogic
    private String deleted;
}
