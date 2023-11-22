package semi.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/join")
public class join extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.JDBC연결
		String jdbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
		String jdbcUsername = "thirties";
		String jdbcPassword = "3030";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
			//2.DB값 설정
			String account_id = request.getParameter("account_id");
			String password = request.getParameter("password");
			String user_name = request.getParameter("user_name");
			String email = request.getParameter("email");
			String phonenumber = request.getParameter("phonenumber");
			
			//3.가입 insert문
			String register = "INSERT INTO USERINFO(ACCOUNT_ID, PASSWORD, USER_NAME, EMAIL, PHONENUMBER)"
							+ "VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(register);
			
			preparedStatement.setString(1, account_id);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, user_name);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, phonenumber);
			
			preparedStatement.executeUpdate();
			
			//4.회원정보 세션에 저장
			request.getSession().setAttribute("ACCOUNT_ID", account_id);
			request.getSession().setAttribute("PASSWORD", password);
			request.getSession().setAttribute("USER_NAME", user_name);
			request.getSession().setAttribute("EMAIL", email);
			request.getSession().setAttribute("PHONENUMBER", phonenumber);
			
			//5.가입 성공시 alert 뜨고 메인페이지 화면(로그인 되지 않은 상태)으로 이동
			response.sendRedirect("register_success.jsp");
		} catch (SQLException e) {
			//6.가입 실패시 alert 뜨고 회원가입 화면(새 창)으로 이동
			//response.sendRedirect("");
			e.printStackTrace();
		}
	}

}