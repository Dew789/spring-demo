package com.example.demo.spring;

import com.example.demo.spring.entity.UserDao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户展示")
public class UserVO {

    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "用户名", example = "zhangSan", required = true)
    private String userName;

    @ApiModelProperty(value = "密码", example = "xxxxxx", required = true)
    private String passWord;

    @ApiModelProperty(value = "性别", example = "man", required = true)
    private String userSex;

    @ApiModelProperty(value = "别名", example = "san", required = true)
    private String nickName;

    public UserVO(UserDao userdo) {
        this.id = userdo.getId();
        this.userName = userdo.getUserName();
        this.passWord = userdo.getPassWord();
        this.userSex = userdo.getUserSex();
        this.nickName = userdo.getNickName();
    }

    public static void main(String[] args) {
        System.out.println(UUID.fromString("wasd"));
        System.out.println(UUID.randomUUID());

    }
}
