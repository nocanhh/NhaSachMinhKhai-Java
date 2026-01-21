package Controller;

import java.io.IOException;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modal.EmailUtil;
import Modal.MaHoaMatKhau;
import Modal.khachhangdao;

@WebServlet("/QuenMatKhauController")
public class QuenMatKhauController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chỉ hiển thị trang nhập email
        RequestDispatcher rd = request.getRequestDispatcher("quenmatkhau.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            khachhangdao khdao = new khachhangdao();

            // 1. Kiểm tra email có tồn tại không
            if (!khdao.checkEmail(email)) {
                request.setAttribute("tb", "Email này chưa được đăng ký!");
                request.getRequestDispatcher("quenmatkhau.jsp").forward(request, response);
                return;
            }

            // 2. Tạo mật khẩu mới ngẫu nhiên (Ví dụ: 6 số)
            Random rand = new Random();
            int num = rand.nextInt(900000) + 100000; 
            String matKhauMoi = "S" + num; // Ví dụ: S123456

            // 3. Mã hóa mật khẩu mới để lưu vào CSDL
            String matKhauMoiMD5 = MaHoaMatKhau.maHoaMD5(matKhauMoi);
            
            // 4. Cập nhật CSDL
            khdao.capNhatMatKhau(email, matKhauMoiMD5);

            // 5. Gửi email cho người dùng (Gửi mật khẩu chưa mã hóa)
            String tieuDe = "Cấp lại mật khẩu mới - Công ty Sách";
            String noiDung = "<p>Xin chào,</p>"
                           + "<p>Mật khẩu mới của bạn là: <b>" + matKhauMoi + "</b></p>"
                           + "<p>Vui lòng đăng nhập và đổi lại mật khẩu ngay.</p>";
            
            boolean daGui = EmailUtil.sendEmail(email, tieuDe, noiDung);

            if (daGui) {
                request.setAttribute("tb_dangnhap", "Mật khẩu mới đã được gửi vào email của bạn. Vui lòng kiểm tra!");
                response.sendRedirect("dangnhapController");
            } else {
                request.setAttribute("tb", "Lỗi gửi email. Vui lòng thử lại sau!");
                request.getRequestDispatcher("quenmatkhau.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}