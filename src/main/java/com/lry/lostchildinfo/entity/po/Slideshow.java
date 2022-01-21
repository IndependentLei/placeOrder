package com.lry.lostchildinfo.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author jdl
 * @since 2022-01-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Slideshow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("图片地址")
    private String pic;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人id")
    private Long createId;

    @ApiModelProperty("创建人")
    private String createCode;

    @ApiModelProperty("更新人id")
    private Long updateId;

    @ApiModelProperty("更新人")
    private String updateCode;

    @ApiModelProperty("datetime")
    private Integer updateTime;

    @ApiModelProperty("是否在轮播中(0在轮播中,1不在轮播中)")
    private String state;

    @ApiModelProperty("是否已删除(0未删除,1已删除)")
    private String deleted;


    /**
     * 开始页数
     */
    private Long startPage;
    /**
     * 页面容量
     */
    private Long pageSize;


}
