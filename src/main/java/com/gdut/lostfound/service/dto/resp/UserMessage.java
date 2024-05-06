package com.gdut.lostfound.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class UserMessage {
    private String id;

    private String userId;

    private String icon;

    private String username;

    private Date time;

    private String title;

    private String lostFoundId;

    private String content;

}
