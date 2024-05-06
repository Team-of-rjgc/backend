package com.gdut.lostfound.service.inter;

import com.gdut.lostfound.dao.entity.User;
import com.gdut.lostfound.service.dto.req.CategoryAddReq;
import com.gdut.lostfound.service.dto.resp.CategoryListResp;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CategoryService {
    /**
     * 新增类别
     */
    void addCategory(CategoryAddReq req, User user) throws Exception;

    /**
     * 查询类别列表
     */
    List<CategoryListResp> getCategoryList(String targetId);

    /**
     * 删除类别
     */
    void deleteCategory(String name, HttpSession session) throws Exception;
}
