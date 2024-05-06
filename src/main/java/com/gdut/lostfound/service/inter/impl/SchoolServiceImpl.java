package com.gdut.lostfound.service.inter.impl;

import com.gdut.lostfound.common.utils.FieldUtils;
import com.gdut.lostfound.dao.entity.School;
import com.gdut.lostfound.dao.inter.SchoolDAO;
import com.gdut.lostfound.service.dto.resp.SchoolListResp;
import com.gdut.lostfound.service.inter.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDAO schoolDAO;

    @Override
    public List<SchoolListResp> getSchools() {
        List<School> schoolList = schoolDAO.getSchools();
        return FieldUtils.copyProperties(schoolList, SchoolListResp.class);
    }
}
