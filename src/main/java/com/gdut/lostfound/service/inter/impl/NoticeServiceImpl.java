package com.gdut.lostfound.service.inter.impl;

import com.gdut.lostfound.common.constant.enums.*;
import com.gdut.lostfound.common.utils.CommonUtils;
import com.gdut.lostfound.common.utils.ExceptionUtils;
import com.gdut.lostfound.dao.entity.Notice;
import com.gdut.lostfound.dao.entity.User;
import com.gdut.lostfound.dao.inter.NoticeDAO;
import com.gdut.lostfound.service.dto.req.NoticeAddReq;
import com.gdut.lostfound.service.dto.resp.NoticeListResp;
import com.gdut.lostfound.service.inter.NoticeService;
import com.gdut.lostfound.service.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * lost-found
 * com.gdut.backend.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/21 19:40 Sunday
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDAO noticeDAO;

    /**
     * 新增通知
     */
    @Override
    public void noticeAdd(NoticeAddReq req, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        Notice notice = new Notice();
        notice.setId(CommonUtils.getUUID())
                .setKind(NoticeKindEnum.TEXT_NOTICE.getCode())
                .setTitle(req.getTitle().trim())
                .setContent(req.getContent().trim())
                .setLevel(LevelEnum.SCHOOL.getCode())
                .setFixTop(req.getFixTop() ? YesNoEnum.YES.getCode() : YesNoEnum.NO.getCode())
                .setTargetId(user.getSchoolId())
                .setCreateTime(new Date())
                .setCreatorId(user.getId())
                .setStatus(EffectEnum.VALID.getCode())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        noticeDAO.saveAndFlush(notice);
    }

    /**
     * 查询通知列表
     */
    @Override
    public List<NoticeListResp> noticeList(HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        List<Notice> list = noticeDAO.listNotice(user.getSchoolId());
        List<NoticeListResp> respList = new ArrayList<>(list.size());
        NoticeListResp resp;
        for (Notice notice : list) {
            resp = new NoticeListResp();
            resp.setId(notice.getId())
                    .setTitle(notice.getTitle())
                    .setContent(notice.getContent())
                    .setTime(notice.getCreateTime())
                    .setFixTop(notice.getFixTop());
            respList.add(resp);
        }
        return respList;
    }

    /**
     * 删除通知
     */
    @Override
    public void noticeDelete(String id) throws Exception {
        Optional<Notice> noticeOptional = noticeDAO.findById(id);
        if (!noticeOptional.isPresent()) {
            throw ExceptionUtils.createException(ErrorEnum.NOTICE_NOT_EXISTS, id);
        }
        Notice notice = noticeOptional.get();
        notice.setRecordStatus(RecordStatusEnum.DELETED.getCode());
        noticeDAO.saveAndFlush(notice);
    }

    /**
     * 通知置顶切换
     */
    @Override
    public void noticeSwitch(String id) throws Exception {
        Optional<Notice> noticeOptional = noticeDAO.findById(id);
        if (!noticeOptional.isPresent()) {
            throw ExceptionUtils.createException(ErrorEnum.NOTICE_NOT_EXISTS, id);
        }
        Notice notice = noticeOptional.get();
        notice.setFixTop(YesNoEnum.YES.equals(notice.getFixTop())
                ? YesNoEnum.NO.getCode() : YesNoEnum.YES.getCode());
        noticeDAO.saveAndFlush(notice);
    }
}
