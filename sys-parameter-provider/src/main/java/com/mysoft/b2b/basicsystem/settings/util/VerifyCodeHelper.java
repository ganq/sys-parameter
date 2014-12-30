package com.mysoft.b2b.basicsystem.settings.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * 验证码工具类
 * @author pengym
 *
 */
public class VerifyCodeHelper {
	
	private static final VerifyCodeHelper verifyCodeHelper = new VerifyCodeHelper();

    private final int WIDTH = 16;// 图片的宽度

    private final int HEIGHT = 22;// 图片的高度

    private final int CODE_LENGTH = 4;// 字符串长度

    private final String RAND_RANGE = "ABCDEFGHJKMNPQRSTUVWXYZ123456789";// 随机字符串范围
    
    private final char[] CHARS = RAND_RANGE.toCharArray();//随机字符串范围
    
    private final String NUM_RAND_RANGE = "123456789";// 随机字符串范围
    
    private final char[] NUM_CHARS = NUM_RAND_RANGE.toCharArray();//随机字符串范围

    private Random random = new Random();
    
    private VerifyCodeHelper() {
        // 
    }

    public static VerifyCodeHelper getInstance() {
        return verifyCodeHelper;
    }

    /**
     * 生成指定字符串的图像数据
     * 
     * @param verifyCode  即将被打印的随机字符串
     * @return 生成的图像数据
     */
    public BufferedImage getImage(String verifyCode) {
        BufferedImage image = new BufferedImage(WIDTH * CODE_LENGTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics graphics = image.getGraphics();
        // 填充背景色
        graphics.fillRect(0, 0, WIDTH * 4, HEIGHT);
        // 设置边框颜色
        graphics.setColor(getRandColor(150,180));
        graphics.drawRect(0,0,WIDTH * CODE_LENGTH-1,HEIGHT-1);
        // 设置随机干扰线条颜色
        graphics.setColor(getRandColor(150, 200));
        // 产生50条干扰线条
        for (int i = 0; i < 40; i++) {
        	 int x = random.nextInt(WIDTH * CODE_LENGTH);
 	        int y = random.nextInt(HEIGHT);
 	        int xl = random.nextInt(13);
 	        int yl = random.nextInt(15);
            graphics.drawLine(x, y, x+xl, y+yl);
        }
        // 设置字体
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        // 画字符串
        for (int i = 0; i < this.CODE_LENGTH; i++) {
            String temp = verifyCode.substring(i, i + 1);
            graphics.setColor(getRandColor(50, 100));
            graphics.drawString(temp, 13 * i + 6, 16);
        }
        // 图像生效
        graphics.dispose();
        return image;
    }

    @SuppressWarnings("unused")
    private ByteArrayInputStream getVerfyImage(String verifyCode) {
        ByteArrayInputStream input = null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
            ImageIO.write(getImage(verifyCode), "JPEG", imageOut);
            imageOut.close();
            input = new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("创建验证码图片出现错误", e);
        }
        return input;
    }

    @SuppressWarnings("unused")
    private ByteArrayInputStream getDefualtVerfyImage(String imagePath) throws Exception {
        ByteArrayInputStream input = null;
        BufferedImage bu = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageIO.write(bu, "JPEG", output);
            input = new ByteArrayInputStream(output.toByteArray());
            output.close();
        } catch (Exception e) {
            throw new RuntimeException("获取验证码图片出现错误", e);
        }
        return input;
    }

    /**
     * 生成随机字符串
     * 
     * @return 随机字符串
     */
    public String getVerifyCode(int length) {
        StringBuilder sb = new StringBuilder();
        int size = length == 0 ? CODE_LENGTH :length;
        for (int i = 0; i < size; i++)
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        return sb.toString();
    }
    
    /**
     * 生成随机字符串
     * 
     * @return 随机字符串
     */
    public String getVerifyCodeOnlyNum(int length) {
        StringBuilder sb = new StringBuilder();
        int size = length == 0 ? CODE_LENGTH :length;
        for (int i = 0; i < size; i++)
            sb.append(NUM_CHARS[random.nextInt(NUM_CHARS.length)]);
        return sb.toString();
    }
    
    /*
     * 给定范围获得随机颜色
     */
    private Color getRandColor(int fc, int bc) {
    	if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
        return new Color(r,g,b);
    }
    
}
