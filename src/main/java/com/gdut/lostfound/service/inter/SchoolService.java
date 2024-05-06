package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.service.dto.resp.SchoolListResp;

import java.util.List;

public interface SchoolService {
    /**
     * 获得学校列表
     */
    List<SchoolListResp> getSchools();
}
