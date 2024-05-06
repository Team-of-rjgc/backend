package com.gdut.lostfound.service.dto.resp;

import com.gdut.lostfound.common.utils.CommonUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class StudentRecognizeResp {
    private String studentNum;
    private String realName;
    private String icon;
    private String schoolName;
    private Integer kind;
    private String email;
    private String phoneNumber;
    private Integer gender;
    private String createTime;
    private String lastLogin;

    public StudentRecognizeResp setCreateTime(Date date) {
        this.createTime = CommonUtils.getFormatDateTime(date);
        return this;
    }

    public StudentRecognizeResp setLastLogin(Date date) {
        this.lastLogin = CommonUtils.getFormatDateTime(date);
        return this;
    }
}
