/*    */ package com.sms.util;
/*    */ 
/*    */ import sun.misc.BASE64Decoder;
/*    */ import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public class BASE64
/*    */ {
/*    */   public static String encode(byte[] data)
/*    */   {
/*  7 */     String s = null;
/*  8 */     if (data != null) {
/*  9 */       s = new BASE64Encoder().encode(data);
/*    */     }
/* 11 */     return s;
/*    */   }
/*    */ 
/*    */   public static byte[] decode(String s) {
/* 15 */     byte[] b = (byte[])null;
/* 16 */     if (s != null) {
/* 17 */       BASE64Decoder decoder = new BASE64Decoder();
/*    */       try {
/* 19 */         return decoder.decodeBuffer(s);
/*    */       }
/*    */       catch (Exception e) {
/* 22 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 25 */     return b;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jay\Desktop\cpstat\WEB-INF\classes\
 * Qualified Name:     com.sms.util.BASE64
 * JD-Core Version:    0.6.2
 */