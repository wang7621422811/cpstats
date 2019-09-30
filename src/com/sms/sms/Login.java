/*    */package com.sms.sms;

/*    */
/*    */import com.sms.init.init;
/*    */
import com.sms.log.LogManager;
import com.sms.util.ReadConfig;

/*    */
import java.sql.Connection;
/*    */
import java.sql.ResultSet;
import java.sql.Statement;

/*    */
/*    */public class Login
/*    */{
	/*    */private static LogManager logger;

	/*    */
	/*    */public Login()
	/*    */{
				logger = LogManager.getInstance(ReadConfig.KEY_LOG_PATH);
			}

	/*    */
	/*    */public int getlogin(String username, String password) throws Exception {
		/* 17 */String sql = "select userID,userName,userPasswor from tb_cp_user where userName='"
				+ username
				+ "' and userPasswor='"
				+ password
				+ "' and status=0";
		System.out.println(sql);
		/* 18 */int rest = 0;
		/* 19 */Connection conn = null;
		/* 20 */ResultSet rs = null;
		/* 21 */Statement stmt = null;
		/*    */try {
//			/* 23 */conn = init.getsmsConnection();
			conn = init.getConnection();
			/* 24 */stmt = conn.createStatement();
			/* 25 */rs = stmt.executeQuery(sql);
			/* 26 */if (rs.next())
				/* 27 */rest = rs.getInt("userID");
			/*    */}
		/*    */catch (Exception ex) {
			System.out.println(ex);
		}
		/*    */try
		/*    */{
			/* 33 */if (rs != null)
				/* 34 */rs.close();
			/* 35 */if (stmt != null)
				/* 36 */stmt.close();
			/* 37 */if (conn != null)
				/* 38 */conn.close();
			/*    */} catch (Exception ex) {
//			/* 40 */logger.log(ex);
			/*    */}
		/*    */finally
		/*    */{
			/*    */try
			/*    */{
				/* 33 */if (rs != null)
					/* 34 */rs.close();
				/* 35 */if (stmt != null)
					/* 36 */stmt.close();
				/* 37 */if (conn != null)
					/* 38 */conn.close();
				/*    */} catch (Exception ex) {
//				/* 40 */logger.log(ex);
				/*    */}
			/*    */}
		/* 43 */return rest;
		/*    */}

	/*    */
	/*    */public boolean getlogin(String username) throws Exception {
		/* 47 */String sql = "select userID,userName,userPasswor from tb_cp_user where userName='"
				+ username + "'";
		/* 48 */boolean rest = false;
		/* 49 */Connection conn = null;
		/* 50 */ResultSet rs = null;
		/* 51 */Statement stmt = null;
		/*    */try {
			/* 53 */conn = init.getsmsConnection();
			/* 54 */stmt = conn.createStatement();
			/* 55 */rs = stmt.executeQuery(sql);
			/* 56 */if (rs.next())
				/* 57 */rest = true;
			/*    */}
		/*    */catch (Exception ex) {
			/* 60 */logger.log(ex);
		}
		/*    */try
		/*    */{
			/* 63 */if (rs != null)
				/* 64 */rs.close();
			/* 65 */if (stmt != null)
				/* 66 */stmt.close();
			/* 67 */if (conn != null)
				/* 68 */conn.close();
			/*    */} catch (Exception ex) {
			/* 70 */logger.log(ex);
			/*    */}
		/*    */finally
		/*    */{
			/*    */try
			/*    */{
				/* 63 */if (rs != null)
					/* 64 */rs.close();
				/* 65 */if (stmt != null)
					/* 66 */stmt.close();
				/* 67 */if (conn != null)
					/* 68 */conn.close();
				/*    */} catch (Exception ex) {
				/* 70 */logger.log(ex);
				/*    */}
			/*    */}
		/* 73 */return rest;
		/*    */}

	/*    */
	/*    */public void updatepasswd(String username, String pwd) {
		/* 77 */Connection conn = null;
		/* 78 */Statement stmt = null;
		/* 79 */String sql = "update tb_cp_user set userPasswor='" + pwd
				+ "' where userName='" + username + "'";
		/*    */try {
			/* 81 */conn = init.getsmsConnection();
			/* 82 */stmt = conn.createStatement();
			/* 83 */stmt.executeUpdate(sql);
			/*    */} catch (Exception e) {
			/* 85 */logger.log(e);
			/*    */try
			/*    */{
				/* 88 */if (stmt != null)
					/* 89 */stmt.close();
				/* 90 */if (conn != null)
					/* 91 */conn.close();
				/*    */} catch (Exception ex) {
				/* 93 */logger.log(ex);
				/*    */}
			/*    */}
		/*    */finally
		/*    */{
			/*    */try
			/*    */{
				/* 88 */if (stmt != null)
					/* 89 */stmt.close();
				/* 90 */if (conn != null)
					/* 91 */conn.close();
				/*    */} catch (Exception ex) {
				/* 93 */logger.log(ex);
				/*    */}
			/*    */}
		/*    */}
	/*    */
}

/*
 * Location: C:\Users\Jay\Desktop\cpstat\WEB-INF\classes\ Qualified Name:
 * com.sms.sms.Login JD-Core Version: 0.6.2
 */