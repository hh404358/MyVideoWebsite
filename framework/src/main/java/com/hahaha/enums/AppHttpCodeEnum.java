package com.hahaha.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506, "发送的评论内容不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误，请上传jpg/png文件"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已经存在"),
    PHONENUMBER_NOT_NULL(521, "手机号码不能为空"),
    FILE_SIZE_ERROR(413, "文件大小不能超出2MB"),

    USER_NO_VIDEO(600,"该用户无视频"),
    THUMBUP_FAIL(601,"点赞失败" ),
    VIDEO_PLAY_ERRPR(602,"视频播放错误" ),
    NO_SELF_STAR(603,"不可对自身视频点评" ),
    HAVA_STAR(604,"你已经参加点评" ),
    VIDEO_NOT_EXIST(605,"视频不存在" ),
    AVATAR_GET_ERROR(606,"用户头像获取异常" ),
    NO_SELF_FOCUS(607,"不能自己关注自己" ),
    NO_REPEAT_FOCUS(608,"不可重复关注" ),
    NO_FOCUS(609,"取消关注失败" ),
    NO_SELF_COLLECT(610,"不可收藏自己的视频" ),
    GET_COLLECTION_ERROR(611,"获取收藏列表失败" ),
    VIDEOID_ERROR(612,"视频ID错误" ),
    SEND_FAIL(613,"发送失败" ), ADD_TYPE_FAIL(614,"新增类别失败" ), DELETE_TYPE_FAIL(615,"删除类别失败" ), NO_RECEIVERUSER(619,"接受用户为空" ), NO_SELF_MESSAGE(620,"不可对自己私信" );
    int code;

    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}