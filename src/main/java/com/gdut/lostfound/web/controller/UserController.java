package com.gdut.lostfound.web.controller;

import com.gdut.lostfound.common.constant.annotation.ActionLog;
import com.gdut.lostfound.common.constant.annotation.AuthCheck;
import com.gdut.lostfound.common.constant.annotation.MatchModeEnum;
import com.gdut.lostfound.common.constant.annotation.ServiceEnum;
import com.gdut.lostfound.common.constant.enums.ActionEnum;
import com.gdut.lostfound.common.constant.enums.UserKindEnum;
import com.gdut.lostfound.service.dto.req.CommentAddReq;
import com.gdut.lostfound.service.dto.req.FeedbackAddReq;
import com.gdut.lostfound.service.dto.req.PublicationAddReq;
import com.gdut.lostfound.service.dto.req.PublicationListReq;
import com.gdut.lostfound.service.dto.req.PublicationRemoveReq;
import com.gdut.lostfound.service.dto.req.SetPasswordReq;
import com.gdut.lostfound.service.dto.resp.PublicationPageResp;
import com.gdut.lostfound.service.dto.resp.base.ResponseDTO;
import com.gdut.lostfound.service.inter.CommentService;
import com.gdut.lostfound.service.inter.FeedbackService;
import com.gdut.lostfound.service.inter.LostFoundService;
import com.gdut.lostfound.service.inter.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * lost-found
 * com.gdut.backend.web.controller
 * 用户相关
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/06 22:03 Saturday
 */

@RestController
@Validated
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private LostFoundService lostFoundService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    // todo 发布寻物

    /**
     * 发布启事
     */
    @PostMapping("/pub")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.JUST)
    @ActionLog(service = ServiceEnum.LOST_FUND_PUB, action = ActionEnum.CREATE)
    public ResponseDTO publicationAdd(@Valid @RequestBody PublicationAddReq req, HttpSession session) throws Exception {
        lostFoundService.add(req, session);
        return ResponseDTO.successObj();
    }

    /**
     * 分页查询启事
     */
    @PostMapping("/page")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.MIN)
    @ActionLog(service = ServiceEnum.LOST_FOUND_PAGE, action = ActionEnum.READ)
    public ResponseDTO publicationPage(@Valid @RequestBody PublicationListReq req, HttpSession session) throws Exception {
        PublicationPageResp resp = lostFoundService.page(req, session);
        return ResponseDTO.successObj("page", resp);
    }

    /**
     * 启事详情
     */
    @PostMapping("/detail")
    //@AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.MIN)
    //@ActionLog(service = ServiceEnum.LOST_FOUND_DETAIL, action = ActionEnum.READ)
    public ResponseDTO publicationDetail(@NotBlank(message = "启事id不能为空") @RequestParam String id,
                                         HttpSession session) throws Exception {
        return ResponseDTO.successObj("item", lostFoundService.detail(id, session));
    }

    /**
     * 认领物品
     */
    @PostMapping("/claim")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.JUST)
    public ResponseDTO claimLostFound(@NotBlank(message = "启事id不能为空") @RequestParam String id,
                                      HttpSession session) throws Exception {
        lostFoundService.claim(id, session);
        return ResponseDTO.successObj();
    }

    /**
     * 启事评论列表
     */
    @PostMapping("/comments")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.MIN)
    @ActionLog(service = ServiceEnum.COMMENT_LIST, action = ActionEnum.READ)
    public ResponseDTO commentList(@NotBlank(message = "启事id不能为空") @RequestParam String id) {
        return ResponseDTO.successObj("comments", commentService.listComment(id));
    }

    /**
     * 发布评论
     */
    @PostMapping("/comment")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.JUST)
    @ActionLog(service = ServiceEnum.COMMENT_PUB, action = ActionEnum.CREATE)
    public ResponseDTO commentAdd(@Valid @RequestBody CommentAddReq req, HttpSession session) throws Exception {
        commentService.commentAdd(req, session);
        return ResponseDTO.successObj();
    }

    /**
     * 查寻用户消息（与我相关的评论）
     */
    @PostMapping("/messages")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.MIN)
    @ActionLog(service = ServiceEnum.COMMENT_MINE, action = ActionEnum.READ)
    public ResponseDTO messages(HttpSession session) throws Exception {
        return ResponseDTO.successObj("list", commentService.listMessage(session));
    }

    // todo 修改帖子

    /**
     * 删除启事
     */
    @PostMapping("/removeLost")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.MIN)
    @ActionLog(service = ServiceEnum.LOST_FOUND_DELETE, action = ActionEnum.DELETE)
    public ResponseDTO removeLostFound(@Valid @RequestBody PublicationRemoveReq req, HttpSession session) throws Exception {
        lostFoundService.removeLostFound(req.getIdList(), session);
        return ResponseDTO.successObj();
    }

    /**
     * 删除评论
     */
    @PostMapping("/removeComment")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.MIN)
    @ActionLog(service = ServiceEnum.COMMENT_DELETE, action = ActionEnum.DELETE)
    public ResponseDTO removeComment(@Valid @RequestBody PublicationRemoveReq req, HttpSession session) throws Exception {
        commentService.removeComment(req.getIdList(), session);
        return ResponseDTO.successObj();
    }

    /**
     * 新增反馈
     */
    @PostMapping("/addFeedback")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.MIN)
    //@ActionLog(service = ServiceEnum.COMMENT_DELETE, action = ActionEnum.DELETE)
    public ResponseDTO addFeedback(@Valid @RequestBody FeedbackAddReq req, HttpSession session) throws Exception {
        feedbackService.addFeedback(req, session);
        return ResponseDTO.successObj();
    }

    /**
     * 设置手机号
     */
    @PostMapping("/setPhone")
    @AuthCheck(level = UserKindEnum.STUDENT)
    public ResponseDTO setPhoneNumber(@NotBlank(message = "手机号不能为空")
                                      @Length(min = 5, max = 16, message = "手机号长度必须在5-16位")
                                      @RequestParam String phone,
                                      HttpSession session) throws Exception {
        return ResponseDTO.successObj("phone", userService.setPhoneNumber(phone, session));

    }

    /**
     * 设置头像
     */
    @PostMapping("/setIcon")
    @AuthCheck(level = UserKindEnum.STUDENT)
    public ResponseDTO setIcon(@NotBlank(message = "头像不能为空")
                               @RequestBody String icon,
                               HttpSession session) throws Exception {
        return ResponseDTO.successObj("icon", userService.setIcon(icon, session));
    }

    /**
     * 修改密码
     */
    @PostMapping("/setPassword")
    @AuthCheck(level = UserKindEnum.STUDENT, mode = MatchModeEnum.MIN)
    public ResponseDTO setPassword(@Valid @RequestBody SetPasswordReq req, HttpSession session) throws Exception {
        userService.setPassword(req, session);
        return ResponseDTO.successObj();
    }

}
