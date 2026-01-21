package Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modal.khachhangbo;
import Modal.loai;
import Modal.loaibo;

@WebServlet("/dangkyController")
public class dangkyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public dangkyController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Luôn tải danh sách loại để hiển thị menu
			loaibo lbo = new loaibo();
			ArrayList<loai> dsloai = lbo.getloai();
			request.setAttribute("dsloai", dsloai);
			
			// Chuyển tiếp đến trang đăng ký
			RequestDispatcher rd = request.getRequestDispatcher("dangky.jsp");
			rd.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			// Lấy tham số từ form
			String hoten = request.getParameter("hoten");
			String diachi = request.getParameter("diachi");
			String sodt = request.getParameter("sodt");
			String email = request.getParameter("email");
			String tendn = request.getParameter("tendn");
			String pass = request.getParameter("pass");
			String repass = request.getParameter("repass");
			
			khachhangbo khbo = new khachhangbo();
			
			// 1. Kiểm tra nhập lại mật khẩu
			if (!pass.equals(repass)) {
				// Nếu sai
				forwardToDangKy(request, response, "Mật khẩu nhập lại không khớp!");
				return;
			}
			
			// 2. Kiểm tra xem tên đăng nhập đã tồn tại chưa
			if (khbo.checkTendnTonTai(tendn)) {
				// Nếu đã tồn tại
				forwardToDangKy(request, response, "Tên đăng nhập đã tồn tại!");
				return;
			}
			
			// Nếu vượt qua 2 validation trên, tiến hành đăng ký
			khbo.dangKyKhachHang(hoten, diachi, sodt, email, tendn, pass);
			
			// Đăng ký thành công, chuyển sang trang đăng nhập và gửi thông báo
			HttpSession session = request.getSession();
			session.setAttribute("tb_dangnhap", "Đăng ký thành công! Vui lòng đăng nhập.");
			response.sendRedirect("dangnhapController");

		} catch (Exception e) {
			e.printStackTrace();
			// Báo lỗi chung nếu có lỗi CSDL
			try {
				forwardToDangKy(request, response, "Lỗi hệ thống: " + e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	private void forwardToDangKy(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws Exception {
		loaibo lbo = new loaibo();
		ArrayList<loai> dsloai = lbo.getloai();
		request.setAttribute("dsloai", dsloai);
		request.setAttribute("tb", errorMessage);
		
		RequestDispatcher rd = request.getRequestDispatcher("dangky.jsp");
		rd.forward(request, response);
	}
}