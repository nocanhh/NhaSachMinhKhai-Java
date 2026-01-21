package Controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modal.DangNhapAdmin;
import Modal.DangNhapAdminBo;
import Modal.MaHoaMatKhau;
import nl.captcha.Captcha;

@WebServlet("/AdminLoginController")
public class AdminLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("admin/login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();

			// 1. Kiểm tra số lần đăng nhập sai (cho Admin)
			Integer soluotAdmin = (Integer) session.getAttribute("soluot_admin");
			if (soluotAdmin == null) soluotAdmin = 0;
			boolean showCaptcha = (soluotAdmin >= 3);

			String un = request.getParameter("txtun");
			String pass = request.getParameter("txtpass");

			// 2. Kiểm tra Captcha nếu cần
			if (showCaptcha) {
				Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
				String captchaInput = request.getParameter("captcha");
				if (captcha == null || captchaInput == null || !captcha.isCorrect(captchaInput)) {
					request.setAttribute("tb", "Mã xác nhận không đúng!");
					request.setAttribute("showCaptcha", true);
					request.getRequestDispatcher("admin/login.jsp").forward(request, response);
					return;
				}
			}

			// 3. Mã hóa mật khẩu MD5 trước khi check DB
			String passMD5 = MaHoaMatKhau.maHoaMD5(pass);

			DangNhapAdminBo dnbo = new DangNhapAdminBo();
			DangNhapAdmin admin = dnbo.ktdn(un, passMD5); // Lưu ý: BO phải check pass đã mã hóa

			if (admin != null) {
				// Thành công
				session.setAttribute("admin", admin);
				session.removeAttribute("soluot_admin"); // Reset số lần sai
				response.sendRedirect("AdminHomeController");
			} else {
				// Thất bại
				soluotAdmin++;
				session.setAttribute("soluot_admin", soluotAdmin);
				
				request.setAttribute("tb", "Sai tên đăng nhập hoặc mật khẩu!");
				request.setAttribute("showCaptcha", (soluotAdmin >= 3));
				request.getRequestDispatcher("admin/login.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}