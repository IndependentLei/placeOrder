package com.lry.lostchildinfo.mapper;

import com.lry.lostchildinfo.entity.pojo.SonComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 子评论表 Mapper 接口
 * </p>
 *
 * @author jdl
 * @since 2022-01-21
 */
@Mapper
@Repository
public interface SonCommentMapper extends BaseMapper<SonComment> {

}