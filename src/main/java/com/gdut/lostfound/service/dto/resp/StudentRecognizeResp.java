package com.gdut.lostfound.service.dto.resp;

import com.gdut.lostfound.common.utils.CommonUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class StudentRecognizeResp {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 头像,这里返回的只是图片名，如果要得到图片需要调用下载文件的接口
     */
    private String icon;
    /**
     * 用户类型
     */
    private Integer kind;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 性别
     */
    private Integer gender;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 最后登录时间
     */
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
