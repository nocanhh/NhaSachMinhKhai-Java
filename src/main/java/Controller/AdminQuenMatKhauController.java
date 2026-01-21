package Controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modal.DangNhapAdminBo;
import Modal.EmailUtil;
import Modal.MaHoaMatKhau;

/**
 * Servlet implementation class AdminQuenMatKhauController
 */
@WebServlet("/AdminQuenMatKhauController")
public class AdminQuenMatKhauController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminQuenMatKhauController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("admin/admin_quenmatkhau.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			DangNhapAdminBo dnbo = new DangNhapAdminBo();
			
			// 1. Kiểm tra email có trong bảng Admin không
			// Cần hàm checkEmail(email) trong BO/DAO
			if(!dnbo.checkEmail(email)) {
				request.setAttribute("tb", "Email này không tồn tại trong hệ thống quản trị!");
				request.getRequestDispatcher("admin/admin_quenmatkhau.jsp").forward(request, response);
				return;
			}
			
			// 2. Tạo pass mới
			Random rand = new Random();
			int num = rand.nextInt(900000) + 100000;
			String newPass = "Admin@" + num;
			String newPassMD5 = MaHoaMatKhau.maHoaMD5(newPass);
			
			// 3. Cập nhật DB
			// Cần hàm updatePass(email, newPassMD5) trong BO/DAO
			dnbo.updatePass(email, newPassMD5);
			
			// 4. Gửi mail
			String subject = "Cấp lại mật khẩu Admin - Nhà sách Minh Khai";
			String content = "<h3>Xin chào quản trị viên,</h3>"
						   + "<p>Mật khẩu mới của bạn là: <b style='color:red; font-size:16px;'>" + newPass + "</b></p>"
						   + "<p>Vui lòng đăng nhập và đổi lại mật khẩu ngay.</p>";
			
			boolean sent = EmailUtil.sendEmail(email, subject, content);
			
			if(sent) {
				request.setAttribute("tb", "Mật khẩu mới đã được gửi tới email của bạn.");
				request.getRequestDispatcher("admin/login.jsp").forward(request, response);
			} else {
				request.setAttribute("tb", "Lỗi gửi mail service!");
				request.getRequestDispatcher("admin/admin_quenmatkhau.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
