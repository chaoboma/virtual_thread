package com.application.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.application.dynamic.DataSourceInfo;
import com.application.dynamic.DataSourceUtils;
import com.application.dynamic.DynamicDataSourceHolder;
import com.application.entity.DemoUser;
import com.application.mapper.CommonMapper;
import com.application.service.TileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.Objects;

@Component
@Service
@Slf4j
public class TileServiceImpl implements TileService {
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    DataSourceUtils dataSourceUtils;
    //@Autowired
    //private JdbcTemplate jdbcTemplate;
    @Value("${image.directory}")
    private String imageDirectory;

    @Value("${terrain.directory}")
    private String terrainDirectory;

    @Override
    public Object getImageType(String layer,String type,String grid,Integer z, Integer x, Integer y){
        String dirSeparator = System.getProperty("file.separator");
        //log.debug("dirSeparator:"+dirSeparator);
        String dirLastStr = imageDirectory.substring(imageDirectory.length() - 1);
        String sqlitePath = "";
        if(dirSeparator.equals(dirLastStr)){
            sqlitePath = imageDirectory+layer+dirSeparator+type+".sqlite";
        }else{

            sqlitePath = imageDirectory+dirSeparator+layer+dirSeparator+type+".sqlite";


        }
        if(!new File(sqlitePath).exists()){
            return null;
        }
        Object res = null;
        if(grid.equals("wmts")){
            y = (1<<(z)) -1 - y;
        }


        String sql = "select tile_data from tiles where zoom_level="+z+" and tile_column="+x+" and tile_row="+y+" limit 1";
        //log.debug("sql:"+sql);

        String datasourceKey = layer+"_"+type;
        DruidDataSource druidDataSource = dataSourceUtils.findDataSource(datasourceKey);

        if(druidDataSource == null){
            //3、从数据库获取连接信息，然后获取数据
            //模拟从数据库中获取的连接
            DataSourceInfo dataSourceInfo = new DataSourceInfo(
                    "jdbc:sqlite:"+sqlitePath,
                    "",
                    "",
                    datasourceKey,
                    "org.sqlite.JDBC");
            //测试数据源连接
            log.debug("this datasource is null");
            druidDataSource = dataSourceUtils.createDataSourceConnection(dataSourceInfo,datasourceKey);

        }
        //log.debug("this datasource is not null");
        if (Objects.nonNull(druidDataSource)){

            //设置当前线程数据源名称-----代码形式
            DynamicDataSourceHolder.setDynamicDataSourceKey(datasourceKey);
            //在新的数据源中查询用户信息

            try{
                log.debug("query start:"+System.currentTimeMillis());
                res = commonMapper.querySql(sql);
                //Thread.sleep(100);

            }catch(Exception e){
                e.printStackTrace();
            }
            log.debug("query end:"+System.currentTimeMillis());

            //关闭数据源连接
            //druidDataSource.close();
        }
        return res;
    }
    @Override
    //@DS("sqlite")
    public Object getTerrainType(String type,Integer z, Integer x, Integer y){
        String dirSeparator = System.getProperty("file.separator");
        log.debug("dirSeparator:"+dirSeparator);
        String dirLastStr = terrainDirectory.substring(terrainDirectory.length() - 1);
        String sqlitePath = "";
        if(dirSeparator.equals(dirLastStr)){
            sqlitePath = terrainDirectory+type+".sqlite";
        }else{

            sqlitePath = terrainDirectory+dirSeparator+type+".sqlite";


        }
        if(!new File(sqlitePath).exists()){
            return null;
        }
        String tableName = "";
        Object res = null;
        if(z < 10){
            tableName = "blocks";
        }else{
            tableName = "blocks_"+String.valueOf(z)+"_"+String.valueOf(x/512)+"_"+String.valueOf(y/512);
        }
        String sql = "select tile from "+tableName+" where z="+z+" and x="+x+" and y="+y+" limit 1";
        log.debug("sql:"+sql);

        DruidDataSource druidDataSource = dataSourceUtils.findDataSource(type);

        if(druidDataSource == null){
            //3、从数据库获取连接信息，然后获取数据
            //模拟从数据库中获取的连接
            DataSourceInfo dataSourceInfo = new DataSourceInfo(
                    "jdbc:sqlite:"+sqlitePath,
                    "",
                    "",
                    type,
                    "org.sqlite.JDBC");
            //测试数据源连接
            log.debug("this datasource is null");
            druidDataSource = dataSourceUtils.createDataSourceConnection(dataSourceInfo,type);
        }
        //log.debug("this datasource is not null");
        if (Objects.nonNull(druidDataSource)){

            //设置当前线程数据源名称-----代码形式
            DynamicDataSourceHolder.setDynamicDataSourceKey(type);
            //在新的数据源中查询用户信息

            try{
                log.debug("query start:"+System.currentTimeMillis());
                res = commonMapper.querySql(sql);


            }catch(Exception e){
                e.printStackTrace();
            }
            log.debug("query end:"+System.currentTimeMillis());
            
            //关闭数据源连接
            //druidDataSource.close();
        }
        return res;

    }

    public static void main(String[] args) {
        System.out.println(1<<(3));
        int y2 = (1<<(3)) - 1 - 3;
        System.out.println(y2);
    }

}
