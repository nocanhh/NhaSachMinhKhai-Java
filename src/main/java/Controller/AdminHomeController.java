package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Modal.ThongKeDao;

@WebServlet("/AdminHomeController")
public class AdminHomeController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            response.sendRedirect("AdminLoginController");
            return;
        }

        try {
            ThongKeDao tk = new ThongKeDao();
            
            // 1. Số liệu tổng quan
            request.setAttribute("doanhThuHomNay", tk.getDoanhThuHomNay());
            request.setAttribute("dsSapHet", tk.getSachSapHet());
            request.setAttribute("dsBanChay", tk.getSachBanChay());
            
            // 2. Dữ liệu biểu đồ (QUAN TRỌNG)
            request.setAttribute("listDoanhThuChart", tk.getDoanhThu7Ngay());
            
            // Gửi ngày hôm nay sang JSP để tạo link (Định dạng yyyy-MM-dd chuẩn SQL)
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            request.setAttribute("homnay", today.toString());
            
            request.getRequestDispatcher("admin/index.jsp").forward(request, response);
        } catch (Exception e) { e.printStackTrace(); }
    }
}