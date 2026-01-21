package Controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modal.loaibo;
import Modal.khachhangbo;
import Modal.khachhang;
import nl.captcha.Captcha;

@WebServlet("/dangnhapController")
public class dangnhapController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            loaibo lbo = new loaibo();
            request.setAttribute("dsloai", lbo.getloai());

            HttpSession session = request.getSession();

            // Lấy thông báo từ session và chuyển nó sang request
            String tb = (String) session.getAttribute("tb_dangnhap");
            if (tb != null) {
                request.setAttribute("tb", tb);
                session.removeAttribute("tb_dangnhap");
            }

            Integer soluot = (Integer) session.getAttribute("soluot");
            if (soluot == null) soluot = 0;

            boolean showCaptcha = (soluot >= 3);  
            
            String un = request.getParameter("txtun");
            String pass = request.getParameter("txtpass");

            // Chuẩn bị dispatcher cho trang chủ
            RequestDispatcher rdd_trangchu = request.getRequestDispatcher("tcController");

            if (un != null && pass != null) { 
                
                if (showCaptcha) {
                    Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
                    String captchaInput = request.getParameter("captcha");

                    if (captcha == null || captchaInput == null || !captcha.isCorrect(captchaInput)) {
                        request.setAttribute("tb", "Mã xác nhận không đúng!");
                        request.setAttribute("showCaptcha", true);
                        RequestDispatcher rd = request.getRequestDispatcher("dangnhap.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }

                // Kiểm tra tài khoản
                khachhangbo khbo = new khachhangbo();
                khachhang kh = khbo.ktDangNhap(un, pass);

                if (kh != null) {
                    // Đăng nhập đúng
                    session.setAttribute("un", kh);
                    session.removeAttribute("soluot"); 
                    
                    // Kiểm tra xem có URL nào được lưu trong Session không
                    String redirectUrl = (String) session.getAttribute("redirectUrl");
                    
                    if (redirectUrl != null && !redirectUrl.isEmpty()) {
                        // Nếu có, xóa nó khỏi session và chuyển hướng đến đó
                        session.removeAttribute("redirectUrl");
                        response.sendRedirect(redirectUrl);
                    } else {
                        // Nếu không, về trang chủ như bình thường
                        rdd_trangchu.forward(request, response);
                    }
                    return; 
                    
                } else {
                    // Đăng nhập sai
                    soluot++;
                    session.setAttribute("soluot", soluot);
                    request.setAttribute("tb", "Sai tên đăng nhập hoặc mật khẩu!");
                    showCaptcha = (soluot >= 3);
                }
            }

            // Gửi trạng thái showCaptcha sang JSP
            request.setAttribute("showCaptcha", showCaptcha);
            RequestDispatcher rd = request.getRequestDispatcher("dangnhap.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi tại dangnhapController: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}