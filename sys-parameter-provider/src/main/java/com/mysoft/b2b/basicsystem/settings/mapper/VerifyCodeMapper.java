/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.basicsystem.settings.mapper;

import java.util.List;

import com.mysoft.b2b.basicsystem.settings.api.VerifyCode;

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

public interface VerifyCodeMapper {
    
    public void insertVerifyCode(VerifyCode verifyCode);
    
    public void updateVerifyCode(VerifyCode verifyCode);
    
    public void deleteVerifyCode(String verifyCodeId);

    public List<VerifyCode> getVerifyCode(VerifyCode verifyCode);
    
}
