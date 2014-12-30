/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;
import java.util.Date;

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

@SuppressWarnings("serial")
public class VerifyCode implements Serializable {

    /**
     * 数据库主键
     */
    private String verifyCodeId;
    /**
     * 验证码
     */
    private String code;
    /**
     * 业务主键(如cookie,mobile)
     */
    private String token;
    /**
     * 验证失败次数
     */
    private int errorTimes;
    /**
     * 状态（0-新增,1-验证成功,2-验证失败,3-无效）
     */
    private int status;
    /**
     * 记录创建时间
     */
    private Date createdTime;
    /**
     * 最后一次验证时间
     */
    private Date lastValidatedTime;

    public String getVerifyCodeId() {
        return verifyCodeId;
    }

    public void setVerifyCodeId(String verifyCodeId) {
        this.verifyCodeId = verifyCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(int errorTimes) {
        this.errorTimes = errorTimes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastValidatedTime() {
        return lastValidatedTime;
    }

    public void setLastValidatedTime(Date lastValidatedTime) {
        this.lastValidatedTime = lastValidatedTime;
    }

    @Override
    public String toString() {
        return "token=" + token + "code=" + code + "code=" + code + super.toString();
    }

}
