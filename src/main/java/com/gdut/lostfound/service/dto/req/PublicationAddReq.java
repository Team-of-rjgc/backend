package com.gdut.lostfound.service.dto.req;

import com.gdut.lostfound.common.constant.enums.ApplyKindEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@Validated
@NoArgsConstructor
public class PublicationAddReq {
    /**
     * 0, 失物发布
     * 1, "认领发布"
     * 2, "身份认证"
     * 3, "物品认领"
     * 4, "账号申诉"
     * @see ApplyKindEnum
     */
    @NotNull
    private Integer applyKind;

    /**
     * 类别
     */
    @NotBlank(message = "类别不能为空")
    private String categoryName;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 详情
     */
    @NotBlank(message = "详情不能为空")
    private String about;

    /**
     * 位置
     */
    private String location;

    /**
     * base64格式图片
     */
    @NotNull(message = "图片不能为空")
    @Size(max = 3, message = "图片最多3张")
    private List<String> images;
}
