package com.gdut.lostfound.dao.entity;

import com.gdut.lostfound.common.constant.annotation.ServiceEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_LOG")
public class Log {
    /**
     * 日志id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//是自动增长型
    @Column(nullable = false)
    private Long id;
    /**
     * 服务码，一个请求（接口)对应一个
     *
     * @see ServiceEnum
     */
    @Column(nullable = false, length = 64)
    private Integer serviceCode;
    /**
     * 用户id
     */
    @Column(nullable = false, length = 64)
    private String userId;
    /**
     * 动作码
     *
     * @see com.gdut.lostfound.common.constant.enums.ActionEnum
     */
    @Column(nullable = false, columnDefinition = "int(11)")
    private Integer actionCode;
    /**
     * 操作对象名
     */
    @Column(nullable = true, length = 64)
    private String targetName;
    /**
     * 操作对象主键id
     */
    @Column(nullable = true, length = 64)
    private String targetId;
    /**
     * 说明
     */
    @Column(nullable = true, length = 256)
    private String about;

    /**
     * 扩展
     */
    @Column(nullable = true, length = 256)
    private String ip;

    /**
     * 耗时
     */
    @Column(nullable = false)
    private Long timeCost;

    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
}
