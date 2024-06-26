package com.gdut.lostfound.service.inter.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdut.lostfound.common.constant.enums.*;
import com.gdut.lostfound.common.utils.CommonUtils;
import com.gdut.lostfound.common.utils.EnumUtils;
import com.gdut.lostfound.common.utils.ExceptionUtils;
import com.gdut.lostfound.dao.entity.Category;
import com.gdut.lostfound.dao.entity.LostFound;
import com.gdut.lostfound.dao.entity.User;
import com.gdut.lostfound.dao.inter.CategoryDAO;
import com.gdut.lostfound.dao.inter.CommentDAO;
import com.gdut.lostfound.dao.inter.LostFoundDAO;
import com.gdut.lostfound.dao.inter.UserDAO;
import com.gdut.lostfound.service.dto.req.PublicationAddReq;
import com.gdut.lostfound.service.dto.req.PublicationListReq;
import com.gdut.lostfound.service.dto.resp.PublicationDetail;
import com.gdut.lostfound.service.dto.resp.PublicationItem;
import com.gdut.lostfound.service.dto.resp.PublicationPageResp;
import com.gdut.lostfound.service.inter.LostFoundService;
import com.gdut.lostfound.service.utils.ImageUtils;
import com.gdut.lostfound.service.utils.SessionUtils;
import jetbrick.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@Service
public class LostFoundServiceImpl implements LostFoundService {

    @Autowired
    private ImageUtils imageUtils;
    @Autowired
    private LostFoundDAO lostFoundDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommentDAO commentDAO;

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 发布启事
     */
    @Override
    public void add(PublicationAddReq req, HttpSession session) throws Exception {
        //校验申请类型
        EnumUtils.checkAndGetCode(req.getApplyKind(), ApplyKindEnum.values());

        User user = SessionUtils.checkAndGetUser(session);
        //用户状态是否正常
        if (!AccountStatusEnum.NORMAL.equals(user.getStatus())) {
            throw ExceptionUtils.createException(ErrorEnum.USER_STATUS_ERROR, user.getUsername(), user.getStatus());
        }

        List<String> imageList = new ArrayList<>(req.getImages().size());
        Base64.Decoder decoder = Base64.getDecoder();

        for (String img : req.getImages()) {
            String image = imageUtils.getBase64Image(img);
            if (StringUtils.isEmpty(image)) {
                continue;
            }
            byte[] bytes = decoder.decode(image);
            String fileName = imageUtils.copyFileToResource(bytes).getFilename();
            imageList.add(fileName);
        }

        String json = mapper.writeValueAsString(imageList);

        LostFound lostFound = new LostFound();
        lostFound.setId(CommonUtils.getUUID())
                .setUserId(user.getId())
                .setCampusId(user.getCampusId())
                .setKind(req.getApplyKind())
                .setTitle(req.getTitle())
                .setAbout(req.getAbout())
                .setLocation(req.getLocation())
                .setImages(json)
                .setCategoryId(req.getCategoryName())
                .setFixTop(YesNoEnum.NO.getCode())
                .setLookCount(0)
                .setCreateTime(new Date())
                .setStatus(PublicationStatusEnum.FINDING.getCode())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());

        lostFoundDAO.saveAndFlush(lostFound);
    }

    /**
     * 分页查询招领启事
     *
     * @param req     请求参数
     * @param session 会话
     * @return 招领信息
     */
    @Override
    public PublicationPageResp page(PublicationListReq req, HttpSession session) throws Exception {
        LostFound lostFoundEx = new LostFound();
        //kind
        if (ApplyKindEnum.LOST_PUBLISH.equals(req.getKind()) || ApplyKindEnum.FOUND_PUBLISH.equals(req.getKind())) {
            lostFoundEx.setKind(req.getKind());
        }
        //category
        if (!StringUtils.isBlank(req.getCategory())) {
            Category category = categoryDAO.findByNameEquals(req.getCategory());
            if (category != null) {
                lostFoundEx.setCategoryId(req.getCategory());
            }
        }
        //keyword
        ExampleMatcher matcher = null;
        if (!StringUtils.isBlank(req.getKeyword())) {
            matcher = ExampleMatcher.matching()
                    .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains());//模糊查询，即%{title}%
            lostFoundEx.setTitle(req.getKeyword());
        }
        //同校区的信息
        User user = SessionUtils.checkAndGetUser(session);
        lostFoundEx.setRecordStatus(RecordStatusEnum.EXISTS.getCode())
                .setCampusId(user.getCampusId());

        //some one, can only find self
        if (!StringUtils.isBlank(req.getUsername())) {
            lostFoundEx.setUserId(user.getId());
        }

        Example<LostFound> lostFoundExample =
                matcher == null ? Example.of(lostFoundEx) : Example.of(lostFoundEx, matcher);


        Page<LostFound> page = lostFoundDAO.findAll(lostFoundExample,
                PageRequest.of(req.getPageNum() < 0 ? 0 : req.getPageNum(), req.getPageSize(),
                        new Sort(Sort.Direction.DESC, "createTime", "fixTop")));

        PublicationPageResp resp = new PublicationPageResp();
        resp.setPageNum(page.getNumber())
                .setTotal(page.getTotalElements())
                .setTotalPage(page.getTotalPages())
                .setPageSize(page.getSize());
        resp.setList(convert(page.getContent()));

        return resp;
    }

    /**
     * 对象转换
     */
    private List<PublicationItem> convert(List<LostFound> lostFoundList) throws IOException {
        List<PublicationItem> list = new ArrayList<>(lostFoundList.size());
        if (CollectionUtils.isEmpty(lostFoundList)) {
            return list;
        }

        Set<String> userIdSet = new HashSet<>(lostFoundList.size());
        lostFoundList.forEach(i -> userIdSet.add(i.getUserId()));

        List<User> userList = userDAO.findAllById(userIdSet);

        Map<String, User> userMap = new HashMap<>(userList.size());
        userList.forEach(i -> userMap.put(i.getId(), i));

        Set<String> lostIdSet = new HashSet<>(lostFoundList.size());
        lostFoundList.forEach(i -> lostIdSet.add(i.getId()));

        List<String> idList = commentDAO.findCommentIdInAndRecordStatusEquals(lostIdSet,
                RecordStatusEnum.EXISTS.getCode());

        Map<String, Long> commentMap = new HashMap<>(idList.size());
        for (String id : idList) {
            if (commentMap.containsKey(id)) {
                commentMap.put(id, commentMap.get(id) + 1);
            } else {
                commentMap.put(id, 1L);
            }
        }

        PublicationItem item;
        User user;
        for (LostFound lostFound : lostFoundList) {
            item = new PublicationItem();
            item.setId(lostFound.getId())
                    .setUserId(lostFound.getUserId());
            user = userMap.get(lostFound.getUserId());
            if (user != null) {
                item.setIcon(user.getIcon())
                        .setUsername(user.getUsername())
                        .setRealName(user.getRealName());
            }
            item.setKind(lostFound.getKind())
                    .setStatus(lostFound.getStatus())
                    .setClaimantId(lostFound.getClaimantId())
                    .setTime(lostFound.getCreateTime())
                    .setLocation(lostFound.getLocation())
                    .setTitle(lostFound.getTitle())
                    .setAbout(lostFound.getAbout())
                    .setImages(mapper.readValue(lostFound.getImages(), List.class))
                    .setCategory(lostFound.getCategoryId())
                    .setLookCount(lostFound.getLookCount());
            item.setCommentCount(commentMap.get(lostFound.getId()) == null ? 0L : commentMap.get(lostFound.getId()));
            list.add(item);
        }
        return list;
    }

    /**
     * 查看启事详情
     */
    @Override
    public PublicationDetail detail(String id, HttpSession session) throws Exception {
        Optional<LostFound> lostFoundOptional = lostFoundDAO.findById(id);
        if (!lostFoundOptional.isPresent() || //不存在
                RecordStatusEnum.DELETED.equals(lostFoundOptional.get().getRecordStatus())) {//已删除
            throw ExceptionUtils.createException(ErrorEnum.LOST_FOUND_NOT_EXISTS, id);
        }
        LostFound lostFound = lostFoundOptional.get();

        // todo 可能的并发问题，但这个数量似乎也不一定要求百分百准确
        lostFound.setLookCount(lostFound.getLookCount() + 1);
        lostFoundDAO.saveAndFlush(lostFound);

        Optional<User> userOptional = userDAO.findById(lostFound.getUserId());
        PublicationDetail detail = new PublicationDetail();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            detail.setUserId(user.getId())
                    .setIcon(user.getIcon())
                    .setUsername(user.getUsername())
                    .setRealName(user.getRealName())
                    .setEmail(user.getEmail())
                    .setPhoneNumber(user.getPhoneNumber());
            //当前访问者是否为发布者
            User currentUser = SessionUtils.getUser(session);
            detail.setIsSelf(SessionUtils.getUser(session) == null ?
                    false : currentUser.getId().equals(lostFound.getUserId()));
        }

        detail.setId(lostFound.getId())
                .setKind(lostFound.getKind())
                .setTime(lostFound.getCreateTime())
                .setLocation(lostFound.getLocation())
                .setTitle(lostFound.getTitle())
                .setAbout(lostFound.getAbout())
                .setImages(mapper.readValue(lostFound.getImages(), List.class))
                .setCategory(lostFound.getCategoryId())
                .setLookCount(lostFound.getLookCount())
                .setStatus(lostFound.getStatus())
                .setDealTime(lostFound.getDealTime());

        return detail;
    }

    /**
     * 删除启事（软删除）
     */
    @Override
    public void removeLostFound(List<String> idList, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        List<LostFound> lostFoundList = lostFoundDAO.findAllById(idList);
        if (CollectionUtils.isEmpty(lostFoundList)) {
            return;
        }
        //管理员
        if (UserKindEnum.MANAGER.equals(user.getKind())) {
            lostFoundList.forEach(item -> item.setRecordStatus(RecordStatusEnum.DELETED.getCode()));
            lostFoundDAO.saveAll(lostFoundList);
            return;
        }
        //学生，只能操作自己的
        List<LostFound> mine = new ArrayList<>(lostFoundList.size());
        lostFoundList.forEach(item -> {
            if (item.getUserId().equals(user.getId())) {
                mine.add(item.setRecordStatus(RecordStatusEnum.DELETED.getCode()));
            }
        });
        if (CollectionUtils.isEmpty(mine)) {
            return;
        }
        lostFoundDAO.saveAll(mine);
    }

    /**
     * 认领物品
     */
    @Override
    public void claim(String id, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        Optional<LostFound> lostFoundOptional = lostFoundDAO.findById(id);
        if (!lostFoundOptional.isPresent()) {
            throw ExceptionUtils.createException(ErrorEnum.LOST_FOUND_NOT_EXISTS, id);
        }
        LostFound lostFound = lostFoundOptional.get();
        if (lostFound.getClaimantId() != null) {
            throw ExceptionUtils.createException(ErrorEnum.LOST_FOUND_CLAIMED);
        }
        lostFound.setClaimantId(user.getId())
                .setStatus(PublicationStatusEnum.CLAIMED.getCode())
                .setDealTime(new Date());
        lostFoundDAO.saveAndFlush(lostFound);
    }
}

