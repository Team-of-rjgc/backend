package com.gdut.lostfound.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class UserInfoResp implements Comparable<UserInfoResp> {
    private String userId;
    private String name;
    private String username;
    private String gender;
    private String email;
    private String phoneNumber;
    private String classNum;
    private String major;
    private String academy;
    private String campus;
    private Date lastLogin;
    private String status;
    private Integer kind;

    /**
     * 按用户类别降序，管理员在前
     */
    @Override
    public int compareTo(UserInfoResp o) {
        return o.getKind() - this.getKind();
    }
}
