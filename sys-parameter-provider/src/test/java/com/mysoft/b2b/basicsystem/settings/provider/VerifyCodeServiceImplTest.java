/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.basicsystem.settings.provider;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysoft.b2b.basicsystem.settings.api.VerifyCode;
import com.mysoft.b2b.basicsystem.settings.api.VerifyCodeService;
import com.mysoft.b2b.basicsystem.settings.test.BaseTestCase;

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
@RunWith(SpringJUnit4ClassRunner.class)
public class VerifyCodeServiceImplTest extends BaseTestCase{

    @Autowired
    private VerifyCodeService verifyCodeService;
    
    @Test
    public void testInsertOrUpdateVerifyCode() {
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCreatedTime(new Date());
        verifyCode.setCode(verifyCodeService.getVerifyCodeStr(VerifyCodeService.NUM_VERIFY_CODE, 4));
        //verifyCode.setErrorTimes(0);
        //verifyCode.setStatus(0);
        verifyCode.setToken("13928489648");
        verifyCodeService.insertOrUpdateVerifyCode(verifyCode);
    }

    @Test
    public void testDeleteVerifyCode() {
        VerifyCode verifyCode = new VerifyCode();
        //verifyCode.setErrorTimes(0);
        //verifyCode.setStatus(0);
        verifyCode.setToken("13928489648");
        verifyCode = verifyCodeService.getVerifyCode(verifyCode);
        if(verifyCode != null){
            verifyCodeService.deleteVerifyCode(verifyCode.getVerifyCodeId());
        }
    }

    @Test
    public void testGetVerifyCodeStr() {
        String code = verifyCodeService.getVerifyCodeStr(VerifyCodeService.VERIFY_CODE, 4);
        Assert.assertEquals(code.length(), 4);
    }

}
