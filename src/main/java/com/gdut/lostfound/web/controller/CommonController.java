package com.gdut.lostfound.web.controller;

import com.gdut.lostfound.common.constant.annotation.AuthCheck;
import com.gdut.lostfound.common.constant.enums.UserKindEnum;
import com.gdut.lostfound.dao.entity.User;
import com.gdut.lostfound.service.dto.resp.CategoryListResp;
import com.gdut.lostfound.service.dto.resp.base.ResponseDTO;
import com.gdut.lostfound.service.inter.CategoryService;
import com.gdut.lostfound.service.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/common")
public class CommonController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获得物品类别列表
     */
    @PostMapping("/category")
    @AuthCheck(level = UserKindEnum.STUDENT)
    public ResponseDTO categoryList(HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        List<CategoryListResp> list = categoryService.getCategoryList(user.getSchoolId());
        return ResponseDTO.successObj("list", list);
    }

}
