package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modal.DangNhapAdminBo;
import Modal.MaHoaMatKhau;

/**
 * Servlet implementation class AdminDangKyController
 */
@WebServlet("/AdminDangKyController")
public class AdminDangKyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDangKyController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("admin/admin_dangky.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			String hoten = request.getParameter("hoten");
			String email = request.getParameter("email");
			String tendn = request.getParameter("tendn");
			String pass = request.getParameter("pass");
			String repass = request.getParameter("repass");
			
			// Validate mật khẩu
			if (!pass.equals(repass)) {
				request.setAttribute("tb", "Mật khẩu nhập lại không khớp!");
				request.getRequestDispatcher("admin/admin_dangky.jsp").forward(request, response);
				return;
			}
			
			DangNhapAdminBo dnbo = new DangNhapAdminBo();
			
			// Check trùng user
			if (dnbo.checkTonTai(tendn)) {
				request.setAttribute("tb", "Tên đăng nhập đã tồn tại!");
				request.getRequestDispatcher("admin/admin_dangky.jsp").forward(request, response);
				return;
			}
			
			// Đăng ký
			String passMD5 = MaHoaMatKhau.maHoaMD5(pass);
			dnbo.dangKy(hoten, email, tendn, passMD5);
			
			// Thành công -> Chuyển về trang Login (Dùng Controller để giấu đuôi jsp)
			request.setAttribute("tb", "Đăng ký thành công! Vui lòng đăng nhập.");
			request.getRequestDispatcher("admin/login.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("tb", "Lỗi: " + e.getMessage());
			request.getRequestDispatcher("admin/admin_dangky.jsp").forward(request, response);
		}
	}

}
