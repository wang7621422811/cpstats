/*    */ package com.sms.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class StringUtil
/*    */ {
/*    */   private static final String key = "okbangshou2015";
/*    */ 
/*    */   public static List<String> slipt(String content, String regx)
/*    */   {
/* 10 */     List list = new ArrayList();
/* 11 */     int aStartIndex = content.indexOf(regx);
/* 12 */     if (aStartIndex == -1)
/* 13 */       list.add(content);
/*    */     else {
/*    */       do {
/* 16 */         if (aStartIndex == 0)
/* 17 */           list.add("");
/*    */         else
/* 19 */           list.add(content.substring(0, aStartIndex));
/* 20 */         content = content.substring(aStartIndex + 1);
/* 21 */       }while ((aStartIndex = content.indexOf(regx)) != -1);
/*    */     }
/* 23 */     if (content.trim().length() > 0)
/* 24 */       list.add(content);
/* 25 */     return list;
/*    */   }
/*    */ 
/*    */   public static String encoded(String code)
/*    */   {
/* 35 */     String pram = "";
/*    */     try {
/* 37 */       pram = new String(DES.decrypt(BASE64.decode(code.toString()), "okbangshou2015"));
/*    */     }
/*    */     catch (Exception e) {
/* 40 */       e.printStackTrace();
/*    */     }
/* 42 */     return pram;
/*    */   }
/*    */ 
/*    */   public static String decode64(String code)
/*    */   {
/* 52 */     String pram = "";
/*    */     try {
/* 54 */       pram = new String(BASE64.decode(code));
/*    */     }
/*    */     catch (Exception e) {
/* 57 */       e.printStackTrace();
/*    */     }
/* 59 */     return pram;
/*    */   }
/*    */ 
/*    */   public static String coded(String code)
/*    */   {
/* 69 */     String pram = "";
/*    */     try {
/* 71 */       pram = BASE64.encode(DES.desCrypto(code.getBytes(), "okbangshou2015"));
/*    */     }
/*    */     catch (Exception e) {
/* 74 */       e.printStackTrace();
/*    */     }
/* 76 */     return pram;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\Jay\Desktop\cpstat\WEB-INF\classes\
 * Qualified Name:     com.sms.util.StringUtil
 * JD-Core Version:    0.6.2
 */