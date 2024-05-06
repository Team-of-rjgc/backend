package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.service.dto.resp.SchoolListResp;

import java.util.List;

/**
 * lost-found
 * com.gdut.backend.service.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/08 13:39 Monday
 */
public interface SchoolService {
    /**
     * 获得学校列表
     */
    List<SchoolListResp> getSchools();
}
