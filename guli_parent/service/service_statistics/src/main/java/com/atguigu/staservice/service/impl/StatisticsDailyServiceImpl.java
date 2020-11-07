package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author qjj
 * @since 2020-11-03
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;


    @Override
    public void registerCount(String day) {

        R registerR = ucenterClient.countRegister(day);

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        Integer countRegister = (Integer) registerR.getData().get("count");
//        把获取的数据添加到数据
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);
        sta.setDateCalculated(day);

//        这里因为是模拟环境，所以采用的随机数
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> getShowDate(String type, String begin, String end) {
//        根据条件查询对应的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);

        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
//        因为返回有两部分数据：日期和日期对应数量
//        前端要求数组json结构
//        所以我们需要使用两个list集合，用日期list和数量listt进行封装
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        for(int i=0;i<staList.size();i++){
            StatisticsDaily statisticsDaily = staList.get(i);
            date_calculatedList.add(statisticsDaily.getDateCalculated());
            if(type.equals("login_num")){
                numDataList.add(statisticsDaily.getLoginNum());
            }
            else if(type.equals("register_num")){
                numDataList.add(statisticsDaily.getRegisterNum());
            }
            else if(type.equals("video_view_num")){
                numDataList.add(statisticsDaily.getVideoViewNum());
            }
            else if(type.equals("course_num")){
                numDataList.add(statisticsDaily.getCourseNum());
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
