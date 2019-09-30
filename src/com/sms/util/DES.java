/*    */ package com.sms.util;
/*    */ 
/*    */ import java.security.SecureRandom;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.DESKeySpec;
/*    */ 
/*    */ public class DES
/*    */ {
/*    */   public static byte[] desCrypto(byte[] datasource, String password)
/*    */   {
/*    */     try
/*    */     {
/* 24 */       SecureRandom random = new SecureRandom();
/* 25 */       DESKeySpec desKey = new DESKeySpec(password.getBytes());
/*    */ 
/* 27 */       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/* 28 */       SecretKey securekey = keyFactory.generateSecret(desKey);
/*    */ 
/* 30 */       Cipher cipher = Cipher.getInstance("DES");
/*    */ 
/* 32 */       cipher.init(1, securekey, random);
/*    */ 
/* 35 */       return cipher.doFinal(datasource);
/*    */     } catch (Throwable e) {
/* 37 */       e.printStackTrace();
/*    */     }
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */   public static byte[] decrypt(byte[] src, String password)
/*    */     throws Exception
/*    */   {
/* 50 */     DESKeySpec desKey = new DESKeySpec(password.getBytes());
/*    */ 
/* 52 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
/*    */ 
/* 54 */     SecretKey securekey = keyFactory.generateSecret(desKey);
/*    */ 
/* 56 */     Cipher cipher = Cipher.getInstance("DES");
/*    */ 
/* 58 */     cipher.init(2, securekey);
/*    */ 
/* 60 */     return cipher.doFinal(src);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jay\Desktop\cpstat\WEB-INF\classes\
 * Qualified Name:     com.sms.util.DES
 * JD-Core Version:    0.6.2
 */