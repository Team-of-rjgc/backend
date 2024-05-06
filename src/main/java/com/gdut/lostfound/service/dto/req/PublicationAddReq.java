package com.gdut.lostfound.service.dto.req;

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
    @NotNull
    private Integer applyKind;

    @NotBlank(message = "类别不能为空")
    private String categoryName;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "详情不能为空")
    private String about;

    private String location;

    /**
     * base64格式图片
     */
    @NotNull(message = "图片不能为空")
    @Size(max = 3, message = "图片最多3张")
    // todo 上传图片能否使用minio，额外给出一个接口，让前端先将图片上传，然后后端返回图片id，前端将id传给后端进行帖子的发布
    private List<String> images;
}
