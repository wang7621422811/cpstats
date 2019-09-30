/*    */ package com.sms.init;
/*    */ 
/*    */ import java.util.StringTokenizer;
/*    */ 
/*    */ public class util
/*    */ {
/*    */   public static final String SYSTEM_LOG_SMS_PATH = "/usr/local/resinpro/webapps/cpstat/WEB-INF/log";
/*    */ 
/*    */   public static String[] getToken(String Str, String tag)
/*    */   {
/* 20 */     StringTokenizer st = new StringTokenizer(Str, tag);
/* 21 */     int tmpCount = 0;
/* 22 */     int count = st.countTokens();
/* 23 */     String[] tmp = new String[count];
/*    */ 
/* 25 */     while (st.hasMoreTokens()) {
/* 26 */       tmp[(tmpCount++)] = st.nextToken();
/*    */     }
/*    */ 
/* 29 */     return tmp;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jay\Desktop\cpstat\WEB-INF\classes\
 * Qualified Name:     com.sms.init.util
 * JD-Core Version:    0.6.2
 */