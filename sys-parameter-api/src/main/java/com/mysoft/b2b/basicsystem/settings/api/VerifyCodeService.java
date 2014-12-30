/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.basicsystem.settings.api;

/**
 * chengp: Change to the actual description of this class
 * @version   Revision History
 * <pre>
 * Author     Version       Date        Changes
 * chengp    1.0           2014年8月13日     Created
 *
 * </pre>
 * @since b2b 2.0.0
 */

public interface VerifyCodeService {
    
    public static final int VERIFY_CODE = 1;
    public static final int NUM_VERIFY_CODE = 2;
    public final static int MOBILE_SEND_TIMELINESS_FIVE_MIN = 5;
    public final static int  MOBILE_SEND_TIMEINEESS_TEM_MIN=10;
    
    /**
     * 创建或更新验证码记录
     * @param verifyCode
     * @return
     */
    public VerifyCode insertOrUpdateVerifyCode(VerifyCode verifyCode);
    
    /**
     * 删除验证码记录
     * @param verifyCodeId
     */
    public void deleteVerifyCode(String verifyCodeId);
    
    /**
     * 查询验证码记录
     * @param verifyCode
     * @return
     */
    public VerifyCode getVerifyCode(VerifyCode verifyCode);
    
    /**
     * 获取验证码
     * @param type
     * @param length
     * @return
     */
    public String getVerifyCodeStr(int type, int length);
    
    
    /** 产生一个长度为4的验证码，生成规则是：如果这个用户token的已经有了验证码了，而且还在有效期之内就不在生成，只是改变有效期。不然则生成新的验证码。
     * @param token 注册的时候用户IP和电话号码产生的随机数
     * @param codeType 验证码的类型，1： 验证码是数据，2 ： 字母和数字的验证码
     * @param validTime 这个验证码的有效时间，单位是分钟
     * @return
     */
    public VerifyCode generateVerifyCode(String token,int codeType,int validTime);
    
    /** /**检查用户输入的验证码是是否正确，0 ： 请输入验证码  1：表示验证码已经过期；2: 输入验证码错误 3：请点击生成验证码 4：系统异常 5： 验证成功
     * @param token
     * @param code
     * @return
     */
    public Integer checkVerifyCode(String token,String code);
    
}
