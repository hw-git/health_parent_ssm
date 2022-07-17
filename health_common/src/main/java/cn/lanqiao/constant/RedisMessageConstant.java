package cn.lanqiao.constant;

/**
 * @Author: Hou
 * @Date: 2021/5/14 15:28
 * @Description:
 */
public class RedisMessageConstant {
    public static final String SENDTYPE_ORDER = "001";//用于缓存体检预约时发送的验证码
    public static final String SENDTYPE_LOGIN = "002";//用于缓存手机号快速登录时发送的验证码
    public static final String SENDTYPE_GETPWD = "003";//用于缓存找回密码时发送的验证码
}
