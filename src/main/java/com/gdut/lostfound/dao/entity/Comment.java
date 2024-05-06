package com.gdut.lostfound.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_COMMENT",
        indexes = {
                @Index(name = "INDEX_CMT_LF", columnList = "lost_found_id"),
                @Index(name = "INDEX_CMT_U", columnList = "user_id")
        }
)
public class Comment {
    /**
     * id
     */
    @Id
    @Column(nullable = false, length = 64)
    private String id;
    /**
     * 寻招id
     *
     * @see LostFound#id
     */
    @Column(name = "lost_found_id", nullable = false, length = 64)
    private String lostFoundId;
    /**
     * 用户id
     *
     * @see User#id
     */
    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;
    /**
     * 内容
     */
    @Column(nullable = false, length = 128)
    private String content;

    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 记录状态：0：已删除1：有效
     *
     * @see com.gdut.lostfound.common.constant.enums.RecordStatusEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default 1")
    private Integer recordStatus;
}
