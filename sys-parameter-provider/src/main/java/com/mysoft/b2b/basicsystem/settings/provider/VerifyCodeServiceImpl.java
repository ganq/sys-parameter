/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.basicsystem.settings.provider;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysoft.b2b.basicsystem.settings.api.VerifyCode;
import com.mysoft.b2b.basicsystem.settings.api.VerifyCodeService;
import com.mysoft.b2b.basicsystem.settings.mapper.VerifyCodeMapper;
import com.mysoft.b2b.basicsystem.settings.util.VerifyCodeHelper;
import com.mysoft.b2b.bizsupport.api.IdGenerationService;

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
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {
	private static final Log log = LogFactory.getLog(VerifyCodeServiceImpl.class);
    @Autowired
    private VerifyCodeMapper verifyCodeMapper;

    @Autowired
    private IdGenerationService idGenerationService;
    
    @Override
    public VerifyCode insertOrUpdateVerifyCode(VerifyCode verifyCode) {
        if(verifyCode != null){
            if(StringUtils.isEmpty(verifyCode.getVerifyCodeId())){
                VerifyCode oldVerifyCode = this.getVerifyCode(verifyCode);
                if(oldVerifyCode == null){
                    verifyCode.setVerifyCodeId(idGenerationService.getNextId("b2b_parameter.sysp_verify_code")+"");
                    verifyCodeMapper.insertVerifyCode(verifyCode);
                }else{
                    verifyCode.setVerifyCodeId(oldVerifyCode.getVerifyCodeId());
                    verifyCodeMapper.updateVerifyCode(verifyCode);
                }
            }else{
                verifyCodeMapper.updateVerifyCode(verifyCode); 
            }
        }
        return verifyCode;
    }
    
    /** 产生一个长度为4的验证码，生成规则是：如果这个用户token的已经有了验证码了，而且还在有效期之内就不在生成，只是改变有效期。不然则生成新的验证码。
     * @param token 注册的时候用户IP和电话号码产生的随机数
     * @param codeType 验证码的类型，1： 验证码是数据，2 ： 字母和数字的验证码
     * @param validTime 这个验证码的有效时间，单位是分钟
     * @return
     */
    public VerifyCode generateVerifyCode(String token,int codeType,int validTime){
    	if(StringUtils.isEmpty(token)){
    		return null;
    	}
    	//1,先检查整改token是否已经在DB里面了
    	VerifyCode verifyCode = new VerifyCode();
    	Date now = new Date();
    	verifyCode.setToken(token);
    	VerifyCode olderVerifyCode = getVerifyCode(verifyCode);
    	//2,如果DB里面没有了这个token记录，也就是说没有验证码,那就创建一个
    	if(olderVerifyCode == null  ){
    		String code = getVerifyCodeStr(codeType, 4);
    		verifyCode.setCreatedTime(now);
    		verifyCode.setStatus(0);
    		verifyCode.setCode(code);
    		verifyCode.setLastValidatedTime(DateUtils.addMinutes(now,validTime));
    		verifyCode.setVerifyCodeId(idGenerationService.getNextId("b2b_parameter.sysp_verify_code")+"");
            verifyCodeMapper.insertVerifyCode(verifyCode);
    		return verifyCode;
    	}
    	//3,如果DB这个验证码已经失效了，那么重新生成一个验证码
    	if(olderVerifyCode !=null && olderVerifyCode.getLastValidatedTime().before(new Date())){
    		String code = getVerifyCodeStr(codeType, 4);
    		olderVerifyCode.setCode(code);
    	}
    	//4,如果DB中已经有了这个验证码，而且这个验证码还没有过期，那么就不生产新的验证码，只是改变这个验证码的有效期。
    	olderVerifyCode.setLastValidatedTime(DateUtils.addMinutes(now,validTime));
    	verifyCodeMapper.updateVerifyCode(olderVerifyCode);
		return olderVerifyCode;
    }
    /**
	 * 更新验证码状态 
	 */
	private void updateSysCode(VerifyCode verifyCode,int status){
		if(null == verifyCode){
			return;
		}
		if(1!=status){
			verifyCode.setErrorTimes(verifyCode.getErrorTimes()+1);
		}
		verifyCode.setStatus(status);
		insertOrUpdateVerifyCode(verifyCode);
	}
    
    /**检查用户输入的验证码是是否正确，0 ： 请输入验证码  1：表示验证码已经过期；2: 输入验证码错误 3：请点击生成验证码 4：系统异常 5： 验证成功
     * @param token
     * @param code
     * @return
     */
    public Integer checkVerifyCode(String token,String code){
    	if(StringUtils.isEmpty(code)){
			return 0;
		}
		VerifyCode verifyCode = new VerifyCode();
		verifyCode.setToken(token);
		VerifyCode verifyCode1 = getVerifyCode(verifyCode);
		try{
			if(null !=verifyCode1){
				if(verifyCode1.getLastValidatedTime().before(new Date())){
					//验证码失效
					updateSysCode(verifyCode1,3);
					return 1;
				}
				if(!verifyCode1.getCode().equalsIgnoreCase(StringUtils.trimToEmpty(code))){
					//验证码错误
					updateSysCode(verifyCode1,2);
					return 2;
				}
			}else{
				return 3;
			}
		}catch(Exception e){
			log.error("校验短信验证码异常，详细信息："+e);
			return 4;
		}
		updateSysCode(verifyCode1,1);
		return 5;
    }
    @Override
    public void deleteVerifyCode(String verifyCodeId) {
        verifyCodeMapper.deleteVerifyCode(verifyCodeId);
    }

    @Override
    public VerifyCode getVerifyCode(VerifyCode verifyCode) {
        VerifyCode result = null;
        if(verifyCode!=null){
            List<VerifyCode> list = null;
            if(StringUtils.isNotEmpty(verifyCode.getVerifyCodeId())){
                list = verifyCodeMapper.getVerifyCode(verifyCode);
            }else if(StringUtils.isNotEmpty(verifyCode.getToken())){
                list = verifyCodeMapper.getVerifyCode(verifyCode);
                if(list!= null && list.size() > 1){
                    for (int i=1,j=list.size();i<j;i++) {
                        this.deleteVerifyCode(list.get(i).getVerifyCodeId());
                    }
                }
            }else{
                //do nothing
            }
            result = (list == null || list.size() == 0)? null:list.get(0);
        }else{
            //do nothing
        }
        return result;
    }

    @Override
    public String getVerifyCodeStr(int type, int length) {
        if(type == VERIFY_CODE){
            return VerifyCodeHelper.getInstance().getVerifyCode(length);
        }else{
            return VerifyCodeHelper.getInstance().getVerifyCodeOnlyNum(length);
        }
    }

}
