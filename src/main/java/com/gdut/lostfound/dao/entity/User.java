package com.gdut.lostfound.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_USER",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE_USER", columnNames = {"username", "email"})}
)
public class User {
    /**
     * 用户id
     */
    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;
    /**
     * 用户登录名: 学生为学号，教职工为工号，管理员为邮箱
     */
    @Column(name = "username", nullable = false, length = 64)
    private String username;
    /**
     * 登录密码（独立）md5加密后存储
     */
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    /**
     * 激活码
     */
    @Column(name = "activate_code", nullable = false, length = 64)
    private String activateCode;
    /**
     * 邮箱
     */
    @Column(name = "email", nullable = false, length = 256)
    private String email;
    /**
     * 手机号码
     */
    @Column(name = "phone_number", length = 16)
    private String phoneNumber;
    /**
     * 真实姓名
     */
    @Column(name = "real_name", nullable = false, length = 256)
    private String realName;

    /**
     * 性别：-1未知，0女，1男
     *
     * @see com.gdut.lostfound.common.constant.enums.GenderEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default -1")
    private Integer gender;
    /**
     * 头像
     */
    @Column(name = "icon", length = 256)
    private String icon;
    /**
     * 学校id
     */
    @Column(name = "school_id", nullable = false, length = 64)
    private String schoolId;
    /**
     * 校区id
     */
    @Column(name = "campus_id", length = 64)
    private String campusId;
    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 用户最后登录时间
     */
    @Column(name = "last_login")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLogin;
    /**
     * 用户类型：0：学生,1：教职工,2：管理员
     *
     * @see com.gdut.lostfound.common.constant.enums.UserKindEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private Integer kind;
    /**
     * 用户状态：0：无效（未激活, 1：正常（已激活）, 2：已冻结, 3：已注销, 4：审核中
     *
     * @see com.gdut.lostfound.common.constant.enums.AccountStatusEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private Integer status;
    /**
     * 记录状态：0：已删除1：有效
     *
     * @see com.gdut.lostfound.common.constant.enums.RecordStatusEnum
     */
    @Column(name = "record_status", nullable = false, columnDefinition = "int(11) default 1")
    private Integer recordStatus;
}
