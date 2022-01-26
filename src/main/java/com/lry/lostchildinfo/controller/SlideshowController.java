package com.lry.lostchildinfo.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lry.lostchildinfo.common.Result;
import com.lry.lostchildinfo.entity.PageVo;
import com.lry.lostchildinfo.entity.po.SlideshowPo;
import com.lry.lostchildinfo.entity.po.UserPo;
import com.lry.lostchildinfo.entity.pojo.Slideshow;
import com.lry.lostchildinfo.service.SlideshowService;
import com.lry.lostchildinfo.utils.FileUtil;
import com.lry.lostchildinfo.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jdl
 * @since 2022-01-21
 */
@Slf4j
@RestController
@RequestMapping("/lostchildinfo/slideshow")
public class SlideshowController {

    @Autowired
    SlideshowService slideshowService;


    /**
     * 分页查找
     * @param slideshowPo
     * @return
     */
    @PostMapping("list")
    public Result list(@RequestBody SlideshowPo slideshowPo){
        if(slideshowPo.getStartPage() == null)
            slideshowPo.setPageSize(1L);
        if (slideshowPo.getState().equals(""))
            slideshowPo.setState(null);
        if (slideshowPo.getStartPage() == null || slideshowPo.getPageSize() == null){
            return Result.error("参数出错");
        }
        PageVo pageVo = slideshowService.listByPage(slideshowPo);
        return Result.success(pageVo);
    }

    /**
     * 添加轮播图
     * @param slideshowPo
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody SlideshowPo slideshowPo){
        System.out.println(slideshowPo.getContext());
        System.out.println(slideshowPo.getPic());
        System.out.println(slideshowPo.getState());
        if (StringUtils.isNotBlank(slideshowPo.getContext()) && StringUtils.isNotBlank(slideshowPo.getPic()) && slideshowPo.getState() != null){
            Slideshow slideshow = new Slideshow();
            BeanUtil.copyProperties(slideshowPo,slideshow);
            slideshow.setCreateId(SecurityUtil.getCurrentUser().getUserId());
            slideshow.setCreateCode(SecurityUtil.getCurrentUser().getUserCode());
            if (slideshowService.save(slideshow))
                return  Result.success("操作成功");
            return Result.error("操作失败");
        }
        return Result.error("参数出错");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/del/{ids}")
    public Result delete(@PathVariable("ids") Long[] ids){
        if (ids.length < 1){
            return Result.error("参数错误");
        }
        if (slideshowService.removeByIds(Arrays.asList(ids)))
            return Result.success("删除成功");
        return Result.error("删除失败");
    }

    /**
     * 更具id获取详细信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result getSlideshowById(@PathVariable("id") Long id){
        if (id > 0){
            Slideshow slideshow = slideshowService.getOne(new LambdaQueryWrapper<Slideshow>().eq(Slideshow::getId,id));
            if (ObjectUtil.isNotEmpty(slideshow)){
                return Result.success(slideshow);
            }else {
                return Result.error("查询失败");
            }
        }
        return Result.error("参数错误");
    }

    /**
     * 更新
     * @param slideshowPo
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody SlideshowPo slideshowPo){
        if (StringUtils.isNotBlank(slideshowPo.getContext()) && StringUtils.isNotBlank(slideshowPo.getPic()) && slideshowPo.getState() != null){
            Slideshow slideshow = new Slideshow();
            BeanUtil.copyProperties(slideshowPo,slideshow);
            slideshow.setUpdateId((SecurityUtil.getCurrentUser().getUserId()));
            slideshow.setUpdateCode(SecurityUtil.getCurrentUser().getUserCode());
            if (slideshowService.updateById(slideshow))
                return  Result.success("操作成功");
            System.out.println(1111);
            return Result.error("操作失败");
        }
        return Result.error("参数出错");
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("import")
    public Result slideshowImport(MultipartFile file){
        if (FileUtil.checkFileName(file,"xls","xlsx")){
            ImportParams params = new ImportParams();
            params.setHeadRows(1); // 设置标题头为第一行
            try {
                List<Slideshow> slideshows = ExcelImportUtil.importExcel(file.getInputStream(), Slideshow.class, params);
                slideshowService.saveBatch(slideshows);
                if (slideshowService.saveBatch(slideshows)) {
                    return Result.success("成功导入" + slideshows.size() + "数据");
                } else {
                    return Result.error("导入失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("导入失败");
            }
        } else {
            return Result.error("文件类型错误,请重新上传");
        }
    }
}