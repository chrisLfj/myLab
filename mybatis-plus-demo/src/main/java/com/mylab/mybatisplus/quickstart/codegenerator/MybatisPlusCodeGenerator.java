package com.mylab.mybatisplus.quickstart.codegenerator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class MybatisPlusCodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) throws URISyntaxException {
//        URL url = MybatisPlusCodeGenerator.class.getResource("");
//        String path = System.getProperty("user.dir");
//        System.out.println(System.getProperty("user.dir"));
//
//        File sysFile = new File("C:\\LifeAndWork\\sysFile.properties");
//        Properties sysPro = System.getProperties();
//        try {
//            sysPro.store(new FileOutputStream(sysFile), "Properties ClASS");
//        } catch (FileNotFoundException e1) {
//            e1.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 获取工程路径
        String projectPath = MybatisPlusCodeGenerator.class.getClassLoader().getResource("").getFile();
        File file = new File(projectPath);
        projectPath = file.getParentFile().getParentFile().getAbsolutePath();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("liufj");
        gc.setOpen(false);//生成后是否打开资源管理器
        gc.setFileOverride(true);//重新生成时文件是否覆盖，这里设置为true，让代码生成工具尽可能的做到自动化覆盖
        gc.setServiceName("%sDao");//Service接口重命名位Dao接口，更贴近开发习惯
        gc.setServiceImplName("%sDaoImpl");
        gc.setIdType(IdType.AUTO);//设置主键策略，这里使用自增策略
        gc.setDateType(DateType.TIME_PACK);//支持java8时间类型LocalDate和LocalDateTime
        gc.setBaseColumnList(true);//xml中生成查询结果列
        gc.setBaseResultMap(true);//xml中生成查询结果映射
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置，用于代码生成组件连接数据库将表转化为java代码
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/liufj?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setTypeConvert(new MySqlTypeConvertCustom());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.mylab.mybatisplus");
        pc.setModuleName("quickstart");
        pc.setService("dao");//自定义修改service目录，默认目录名是service
        pc.setServiceImpl("dao.impl");
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

//        templateConfig.setXml("");//配置不自动生成xml
        templateConfig.setController("");//配置不自动生成controller代码
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//配置驼峰命名方式
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperEntityClass("");//配置实体类的父类
        strategy.setSuperServiceClass("");//配置service类的父级接口，默认是IService，因为有些情况下还需要对service接口进行扩展和增强，这时就需要通过自定义service父级接口来实现
        strategy.setSuperServiceImplClass("");//配置自定义service父级实现类，用于对service的方法进行增强，默认是ServiceImpl类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
//        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段，这个配置需要配合setSuperEntityClass使用，数据库表设计是会有一些公共字段每个表中都有，比如id，create_time,update_time等
        // 这些公共属性需要事先配置到entity的父类中，这样配置之后代码生成器生成的java类中就不会有这些字段
        strategy.setSuperEntityColumns("id");
        strategy.setSuperEntityColumns("create_time");
        strategy.setSuperEntityColumns("update_time");
        strategy.setSuperEntityColumns("version");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setEntityTableFieldAnnotationEnable(true);//属性上加@TableField注解
        strategy.setLogicDeleteFieldName("isDelete");//设置哪个属性位对应表中的逻辑删除字段，并且加上@TableLogic注解
        strategy.setVersionFieldName("version");//属性上加乐观锁注解，注意这里的fieldName是大小写敏感的
        List<TableFill> tableFillList = new ArrayList<>();//设置自动填充属性，例如乐观锁字段需要在创建时自动填充为0
        TableFill version = new TableFill("version", FieldFill.INSERT);
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time等", FieldFill.INSERT_UPDATE);
        tableFillList.add(version);
        tableFillList.add(createTime);
        tableFillList.add(updateTime);
        strategy.setTableFillList(tableFillList);//属性上加自动填充注解
        mpg.setStrategy(strategy);
        //默认模板引擎是Velocity，还可以支持Freemarker，Beetl，如果不想使用默认引擎，就需要在AutoGenerator中设置模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
