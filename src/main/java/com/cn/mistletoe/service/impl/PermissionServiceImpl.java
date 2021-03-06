package com.cn.mistletoe.service.impl;

import com.cn.mistletoe.common.TimerTest;
import com.cn.mistletoe.mapper.RolePermissionRelationMapper;
import com.cn.mistletoe.model.Permission;
import com.cn.mistletoe.mapper.PermissionMapper;
import com.cn.mistletoe.model.RolePermissionRelation;
import com.cn.mistletoe.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.mistletoe.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 后台用户权限表 服务实现类
 * </p>
 *
 * @author xingyu
 * @since 2021-01-21
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    /**
     * 注入基于原生redisTemplate
     * 而封装在redisServiceImpl里的redisTemplate
     * 接口: RedisService
     */
    private RedisService redisService;

    @Autowired
    RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List selectPermission() {
        return permissionMapper.selectPermission();
    }


    /**
     *
     * @param id
     * @return
     */

    @Override
    public ArrayList selectPmsByRoleId(Long id) {
        // 查询出该ID对应的角色信息
        String roleName = permissionMapper.selectRoleById(id);
        // 查询该角色当前权限
        ArrayList list = permissionMapper.selectPmsByRoleId(id);
        return list;
    }


    @Override
    public Integer insertRolePms(List<RolePermissionRelation> rpr) {
        //@Transactional 添加在实现层 和 启动类后开启事务 实现混滚 commit 去插入数据
        /*for(RolePermissionRelation relation:rpr){
            rolePermissionRelationMapper.insert(relation);
        }
        return rpr.size();*/
        return rolePermissionRelationMapper.insertRolePms(rpr);
    }

    @Override
    public List selectRpr(Long id) {
        return permissionMapper.selectRpr(id);
    }

    @Override
    public Integer deleteRpr(List<RolePermissionRelation> rpr) {
        return rolePermissionRelationMapper.deleteRpr(rpr);
    }

    @Override
    public ArrayList<Permission> findPermissionsByRoleId(Integer roleId) {
        return rolePermissionRelationMapper.findPermissionsByRoleId(roleId);
    }

    @Override
    public ArrayList<Permission> findAllPermission() {
        ArrayList<Permission> permissions;
        if (redisService.hasKey("RBAC_SYSTEM:PERMISSION:ALL_PERMISSIONS")) {
            permissions = (ArrayList<Permission>) redisService.get("RBAC_SYSTEM:PERMISSION:ALL_PERMISSIONS");
        } else {
            permissions = rolePermissionRelationMapper.findAllPermission();
            redisService.set("RBAC_SYSTEM:PERMISSION:ALL_PERMISSIONS", permissions);
        }
        return permissions;
    }


}
