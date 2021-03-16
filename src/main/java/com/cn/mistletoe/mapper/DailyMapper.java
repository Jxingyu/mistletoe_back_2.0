package com.cn.mistletoe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.mistletoe.model.Daily;

import java.util.Vector;

public interface DailyMapper extends BaseMapper<Daily> {
    Vector<Daily> selectDailyAll(Integer userId);

    Integer selectDailyId(String username);

    Integer selectNumbersByUserId(Integer userId);

    Vector<Daily> selectDailyEditMes(Integer numbers);

    int insertDaily(Daily daily);

    int saveDailyInRedis(Daily daily);

    Vector<Daily> selectDailyById(int id);

    int updateDraftDaily(Daily daily);
}
