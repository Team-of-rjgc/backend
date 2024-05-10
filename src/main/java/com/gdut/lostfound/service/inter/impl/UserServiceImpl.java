package com.gdut.lostfound.service.inter.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.gdut.lostfound.common.constant.enums.*;
import com.gdut.lostfound.common.utils.CommonUtils;
import com.gdut.lostfound.common.utils.ExceptionUtils;
import com.gdut.lostfound.common.utils.RedisUtil;
import com.gdut.lostfound.dao.entity.User;
import com.gdut.lostfound.dao.inter.UserDAO;
import com.gdut.lostfound.service.dto.req.*;
import com.gdut.lostfound.service.dto.resp.StudentRecognizeResp;
import com.gdut.lostfound.service.inter.EmailService;
import com.gdut.lostfound.service.inter.UserService;
import com.gdut.lostfound.service.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ImageUtils imageUtils;

    // 验证码放入redis缓存过期时间
    @Value("${code.expiration}")
    private Long expiration;

    private final RedisUtil redisUtil;
    private final EmailService emailService;


    @Override
    public void sendRegisterMailCode(String email) {

        // 查看注册邮箱是否存在
        if (userDAO.findByEmailEquals(email) != null) {
            throw new RuntimeException("注册邮箱已存在");
        }

        // 获取发送邮箱验证码的HTML模板
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates/mail", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email-code.html");

        // 从redis缓存中尝试获取验证码
        Object code = redisUtil.get(email);
        if (code == null) {
            // 如果在缓存中未获取到验证码，则产生6位随机数，放入缓存中
            code = RandomUtil.randomNumbers(6);
            if (!redisUtil.set(email, code, expiration)) {
                throw new RuntimeException("后台缓存服务异常");
            }
        }
        // 发送验证码
        emailService.send(new EmailReq(Collections.singletonList(email),
                "邮箱验证码", template.render(Dict.create().set("code", code))));

    }

    @Override
    public void sendResetMailCode(String email) {

        // 查看注册邮箱是否存在
        if (userDAO.findByEmailEquals(email) == null) {
            throw new RuntimeException("该邮箱没有注册账号");
        }

        // 获取发送邮箱验证码的HTML模板
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates/mail", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email-code.html");

        // 从redis缓存中尝试获取验证码
        Object code = redisUtil.get(email);
        if (code == null) {
            // 如果在缓存中未获取到验证码，则产生6位随机数，放入缓存中
            code = RandomUtil.randomNumbers(6);
            if (!redisUtil.set(email, code, expiration)) {
                throw new RuntimeException("后台缓存服务异常");
            }
        }
        // 发送验证码
        emailService.send(new EmailReq(Collections.singletonList(email),
                "邮箱验证码", template.render(Dict.create().set("code", code))));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterReq userRegisterReq) {
        // 通过email获取redis中的code
        Object value = redisUtil.get(userRegisterReq.getEmail());
        if (value == null || !value.toString().equals(userRegisterReq.getCode())) {
            throw new RuntimeException("无效验证码");
        } else {
            redisUtil.del(userRegisterReq.getEmail());
        }

        //以邮箱号作为默认昵称进行注册
        String nickName = userRegisterReq.getEmail();

        if (userDAO.findByNickNameEquals(nickName) != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建用户
        User user = new User();
        user.setSchoolId("000");
        user.setId(CommonUtils.getUUID());
        user.setNickName(nickName);
        user.setCreateTime(new Date());
        user.setKind(UserKindEnum.STUDENT.getCode());
        try {
            user.setPassword(CommonUtils.encodeByMd5(userRegisterReq.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("注册密码异常");
        }
        user.setEmail(userRegisterReq.getEmail());
        userDAO.saveAndFlush(user);
    }

    @Override
    public StudentRecognizeResp login(UserLoginReq req, HttpSession session) throws Exception {
        User userEx = new User();
        userEx.setEmail(req.getEmail());

        Optional<User> userOptional = userDAO.findOne(Example.of(userEx));
        //用户不存在
        if (!userOptional.isPresent()) {
            throw ExceptionUtils.createException(ErrorEnum.USER_NOT_EXISTS, req.getEmail());
        }

        //用户存在
        User user = userOptional.get();

        //密码不正确
        if (!CommonUtils.encodeByMd5(req.getPassword()).equals(user.getPassword())) {
            throw ExceptionUtils.createException(ErrorEnum.PASSWORD_USERNAME_ERROR, req.getEmail());
        }

        //更新最后登录时间
        user.setLastLogin(new Date());
        userDAO.save(user);
        //session设置
        session.setAttribute("user", user);

        //返回
        StudentRecognizeResp resp = new StudentRecognizeResp();
        resp.setIcon(user.getIcon())
                .setNickName(user.getNickName())
                .setRealName(user.getRealName())
                .setKind(user.getKind())
                .setEmail(user.getEmail())
                .setGender(user.getGender())
                .setCreateTime(user.getCreateTime())
                .setLastLogin(user.getLastLogin());

        return resp;
    }

    /**
     * 修改密码
     */
    @Override
    public void setPassword(SetPasswordReq req, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        String pwd = CommonUtils.encodeByMd5(req.getOldPassword());
        //旧密码错误
        if (!user.getPassword().equals(pwd)) {
            throw ExceptionUtils.createException(ErrorEnum.OLD_PASSWORD_ERROR);
        }
        //新密码不一致
        if (!req.getNewPassword().equals(req.getConfirmPassword())) {
            throw ExceptionUtils.createException(ErrorEnum.NEW_PASSWORD_DIFFERENT);
        }
        //设置新密码
        user.setPassword(CommonUtils.encodeByMd5(req.getNewPassword()));
        userDAO.saveAndFlush(user);
    }


    /**
     *  通过邮箱重置密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(ResetPasswordReq resetPasswordReq) throws Exception {
        // 通过email获取redis中的code
        Object value = redisUtil.get(resetPasswordReq.getEmail());
        if (value == null || !value.toString().equals(resetPasswordReq.getCode())) {
            throw new RuntimeException("无效验证码");
        } else {
            redisUtil.del(resetPasswordReq.getEmail());
        }

        User user = userDAO.findByEmailEquals(resetPasswordReq.getEmail());

        //新密码不一致
        if (!resetPasswordReq.getNewPassword().equals(resetPasswordReq.getConfirmPassword())) {
            throw ExceptionUtils.createException(ErrorEnum.NEW_PASSWORD_DIFFERENT);
        }
        //设置新密码
        user.setPassword(CommonUtils.encodeByMd5(resetPasswordReq.getNewPassword()));

        userDAO.saveAndFlush(user);
    }


    /**
     * 修改昵称
     */
    @Override
    public String setNickName(String nickName, HttpSession session) throws Exception{
        User user = SessionUtils.checkAndGetUser(session);
        user.setNickName(nickName);
        userDAO.saveAndFlush(user);
        return user.getNickName();
    }

    /**
     * 修改头像
     */
    @Override
    public String setIcon(MultipartFile image, HttpSession session) throws Exception {
        User user = SessionUtils.checkAndGetUser(session);
        String fileName = imageUtils.copyFileToResource(image.getBytes()).getFilename();
        user.setIcon(fileName);
        userDAO.saveAndFlush(user);
        return fileName;
    }



//    /**
//     * 查看用户信息
//     */
//    @Override
//    public UserInfoResp userInfo(String userId) throws Exception {
//        Optional<Student> studentOptional = studentDAO.findByUserIdEquals(userId);
//        Optional<User> userOptional = userDAO.findById(userId);
//        if (!studentOptional.isPresent() || !userOptional.isPresent()) {
//            throw ExceptionUtils.createException(ErrorEnum.USER_NOT_EXISTS, userId);
//        }
//
//        Student student = studentOptional.get();
//        User user = userOptional.get();
//        UserInfoResp resp = new UserInfoResp();
//        resp.setUserId(user.getId())
//                .setName(student.getName())
//                .setUsername(student.getStudentNum())
//                .setGender(EnumUtils.getDesc(student.getGender(), GenderEnum.values()))
//                .setEmail(user.getEmail())
//                .setClassNum(student.getClassNum())
//                .setMajor(student.getMajor())
//                .setAcademy(student.getAcademy())
//                .setCampus(student.getCampusName())
//                .setLastLogin(user.getLastLogin());
//        return resp;
//    }
//
//    /**
//     * 查询学生信息列表
//     */
//    @Override
//    public UserInfoListResp userList(UserInfoListReq req, HttpSession session) throws Exception {
//        User user = SessionUtils.checkAndGetUser(session);
//        Page<Student> studentPage;
//        //根据keyword查询，若空则查询全部
//        studentPage = studentDAO.findStudentByKeyword(req.getKeyword().trim(), user.getSchoolId(),
//                PageRequest.of(req.getPageNum() < 0 ? 0 : req.getPageNum(), req.getPageSize()));
//
//        Set<String> userIdSet = new HashSet<>(studentPage.getNumberOfElements());
//        Map<String, User> userMap = new HashMap<>(studentPage.getNumberOfElements());
//        if (studentPage.hasContent()) {
//            studentPage.forEach(item -> userIdSet.add(item.getUserId()));
//            List<User> userList = userDAO.findAllById(userIdSet);
//            userList.forEach(item -> userMap.put(item.getId(), item));
//        }
//        List<UserInfoResp> list = new ArrayList<>(studentPage.getNumberOfElements());
//        UserInfoResp resp;
//        User u;
//        for (Student student : studentPage) {
//            resp = new UserInfoResp();
//            resp.setUserId(student.getUserId())
//                    .setName(student.getName())
//                    .setUsername(student.getStudentNum())
//                    .setGender(EnumUtils.getDesc(student.getGender(), GenderEnum.values()))
//                    .setClassNum(student.getClassNum())
//                    .setMajor(student.getMajor())
//                    .setAcademy(student.getAcademy())
//                    .setCampus(student.getCampusName());
//            u = userMap.get(student.getUserId());
//            if (u != null) {
//                resp.setLastLogin(u.getLastLogin())
//                        .setEmail(u.getEmail())
//                        .setPhoneNumber(u.getPhoneNumber())
//                        .setKind(u.getKind());
//            }
//            list.add(resp);
//        }
//        //按照用户类型排序，管理员在前面
//        /*
//            todo: 5/16/2019,016 05:01 PM
//            If there are too many user, cannot search user by one time
//         */
//        Collections.sort(list);
//
//        UserInfoListResp infoListResp = new UserInfoListResp();
//        infoListResp.setList(list)
//                .setPageNum(studentPage.getNumber())
//                .setPageSize(studentPage.getSize())
//                .setTotal(studentPage.getTotalElements())
//                .setTotalPage(studentPage.getTotalPages());
//
//        return infoListResp;
//    }
//
//    /**
//     * 设置用户为管理员
//     */
//    @Override
//    public void setAsAdmin(String userId, Integer flag) throws Exception {
//        Optional<User> userOptional = userDAO.findById(userId);
//        if (!userOptional.isPresent()) {
//            throw ExceptionUtils.createException(ErrorEnum.USER_NOT_EXISTS, userId);
//        }
//        User user = userOptional.get();
//        user.setKind(YesNoEnum.YES.equals(flag) ? UserKindEnum.MANAGER.getCode() : UserKindEnum.STUDENT.getCode());
//        userDAO.saveAndFlush(user);
//    }
}