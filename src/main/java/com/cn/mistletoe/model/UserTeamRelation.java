package com.cn.mistletoe.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserTeamRelation {
    /**
     * 用户（队员）团队关联表id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户（队员）团队关联表
     * 团队编号
     */
    private Integer numbers;
}
