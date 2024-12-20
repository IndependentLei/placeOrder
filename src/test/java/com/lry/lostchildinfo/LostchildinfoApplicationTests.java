package com.lry.lostchildinfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.lry.lostchildinfo.entity.JwtProperties;
import com.lry.lostchildinfo.entity.pojo.Article;
import com.lry.lostchildinfo.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class LostChildInfoApplicationTests {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RedisUtil redisUtil;

    @Test
    void contextLoads() {
        List<String> tables = new ArrayList<>();
//        tables.add("sys_admin");
//        tables.add("sys_admin_menu");
//        tables.add("sys_log");
//        tables.add("sys_menu");
//        tables.add("sys_role");
//        tables.add("sys_user");
//        tables.add("sys_user_role");
//        tables.add("tbL_children_info");
//        tables.add("tbL_children_info_attach");
//        tables.add("tbl_father_comment");
//        tables.add("tbl_son_comment");
//        tables.add("tbl_slideshow");
//        tables.add("tbl_article");

        FastAutoGenerator.create("jdbc:mysql://101.132.65.76/children_lost_dev","root","NewPassWord1.")
                .globalConfig(builder -> {
                    builder.author("jdl")               //作者
                            .outputDir(System.getProperty("user.dir")+"\\src\\main\\java")    //输出路径(写到java目录)
                            .enableSwagger()           //开启swagger
                            .commentDate("yyyy-MM-dd")
                            .fileOverride();            //开启覆盖之前生成的文件

                })
                .packageConfig(builder -> {
                    builder.parent("com.lry")
                            .moduleName("lostchildinfo")
                            .entity("entity")
                            .service("service")
                            .serviceImpl("serviceImpl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,System.getProperty("user.dir")+"\\src\\main\\resources\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            // 去前缀
                            .addTablePrefix("sys_","tbl")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .enableLombok()
                            .logicDeleteColumnName("deleted")
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .mapperBuilder()
                            .enableBaseResultMap()  //生成通用的resultMap
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    @Test
    public void test(){
        String name = bCryptPasswordEncoder.encode("111111");
        System.out.println(name);
    }

    @Test
    public void test1(){
        String date = new DateTime().toString("/yyyy/mm/dd");
        System.out.println(date);
    }

    @Test
    public void test2(){
        if (redisUtil.set("111","1111")){
            System.out.println("存入成功");
        }else {
            System.out.println("失败!");
        }

        if (redisUtil.hasKey("111")) {

            System.out.println(redisUtil.get("111"));
        }

        if (redisUtil.hasKey("PermissionsCache:1")){
            System.out.println(redisUtil.hasKey("PermissionsCache:1"));
            System.out.println(redisUtil.get("PermissionsCache:1"));
        }
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0MzIwODIxMCwiZXhwIjoxNjQzMjk0NjEwfQ.kjtQXdtNma4q207iNP5kcp6Jl1f_6-jwhwsgsq_cAZs";
        System.out.println("Bearer ".length());
        token = StringUtils.substring(token, "Bearer ".length());
        System.out.println(token);

        System.out.println(StringUtils.startsWith("Bearer ",token));
        System.out.println(StringUtils.startsWith(token,"Bearer "));
    }
    @Autowired
    JwtProperties jwtProperties;
    @Test
    public void test3(){
        System.out.println(jwtProperties.getPrefix());
        System.out.println(jwtProperties.getExpire());
        System.out.println(jwtProperties.getHeader());
        System.out.println(jwtProperties.getSecret());
        System.out.println(jwtProperties.tokenStart());
    }

    @Autowired
    ArticleService articleService;

    @Test
    public void test4(){
        Article article = new Article();
        article.setUserId(1L);
        article.setUserCode("admin");
        article.setTitle("小米11");
        article.setPicUrl("https://file.7b114.xyz/blog_avater/2022/04/16/1650102303395155.jpg");
        article.setCreateTime(LocalDateTime.now());
        article.setContent("性能怪物1111");
        articleService.saveOrUpdate(article,
                Wrappers.<Article>query().eq("title",article.getTitle()));
    }

}
