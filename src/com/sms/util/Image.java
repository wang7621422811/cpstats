/*    */ package com.sms.util;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class Image
/*    */ {
/*    */   public String sRand;
/*    */ 
/*    */   public Color getRandColor(int fc, int bc)
/*    */   {
/* 10 */     Random random = new Random();
/* 11 */     if (fc > 255) fc = 255;
/* 12 */     if (bc > 255) bc = 255;
/* 13 */     int r = fc + random.nextInt(bc - fc);
/* 14 */     int g = fc + random.nextInt(bc - fc);
/* 15 */     int b = fc + random.nextInt(bc - fc);
/* 16 */     return new Color(r, g, b);
/*    */   }
/*    */ 
/*    */   public BufferedImage creatImage() {
/* 20 */     int width = 60; int height = 20;
/* 21 */     BufferedImage image = new BufferedImage(width, height, 1);
/*    */ 
/* 23 */     Graphics g = image.getGraphics();
/*    */ 
/* 25 */     Random random = new Random();
/*    */ 
/* 28 */     g.setColor(getRandColor(254, 255));
/* 29 */     g.fillRect(0, 0, width, height);
/*    */ 
/* 31 */     g.setFont(new Font("Times New Roman", 0, 18));
/*    */ 
/* 36 */     g.setColor(getRandColor(254, 255));
/* 37 */     for (int i = 0; i < 155; i++)
/*    */     {
/* 39 */       int x = random.nextInt(width);
/* 40 */       int y = random.nextInt(height);
/* 41 */       int xl = random.nextInt(12);
/* 42 */       int yl = random.nextInt(12);
/* 43 */       g.drawLine(x, y, x + xl, y + yl);
/*    */     }
/*    */ 
/* 48 */     for (int i = 0; i < 4; i++) {
/* 49 */       String rand = String.valueOf(random.nextInt(10));
/* 50 */       this.sRand += rand;
/*    */ 
/* 52 */       g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
/* 53 */       g.drawString(rand, 13 * i + 6, 16);
/*    */     }
/*    */ 
/* 56 */     g.dispose();
/* 57 */     return image;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jay\Desktop\cpstat\WEB-INF\classes\
 * Qualified Name:     com.sms.util.Image
 * JD-Core Version:    0.6.2
 */