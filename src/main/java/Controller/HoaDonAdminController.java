package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Modal.HoaDonAdminDao;

@WebServlet("/HoaDonAdminController")
public class HoaDonAdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            response.sendRedirect("AdminLoginController");
            return;
        }

        try {
            HoaDonAdminDao dao = new HoaDonAdminDao();
            String action = request.getParameter("action");

            // --- TRƯỜNG HỢP 1: XEM DANH SÁCH ĐÃ THANH TOÁN ---
            if ("dathanhtoan".equals(action)) {
                request.setAttribute("dsDaThanhToan", dao.getHoaDonDaThanhToan());
                request.getRequestDispatcher("admin/hoadon_dathanhtoan.jsp").forward(request, response);
                return; // Dừng lại, không chạy xuống phần dưới
            }

            // --- TRƯỜNG HỢP 2: CÁC CHỨC NĂNG XÁC NHẬN / XÓA (Cũ) ---
            if ("xacnhan".equals(action)) {
                long mahd = Long.parseLong(request.getParameter("mahd"));
                dao.xacNhanHoaDon(mahd);
            }
            
            if ("xoaton".equals(action)) {
                dao.xoaDonHangTon();
                request.setAttribute("tb", "Đã xóa các đơn hàng tồn quá 15 ngày!");
            }
            
            if ("xemngay".equals(action)) {
                String ngay = request.getParameter("ngay"); // Nhận ngày yyyy-MM-dd
                request.setAttribute("ngayXem", ngay); // Gửi lại ngày để hiển thị tiêu đề
                request.setAttribute("dsHoaDonNgay", dao.getHoaDonByDate(ngay));
                request.getRequestDispatcher("admin/hoadon_ngay.jsp").forward(request, response);
                return;
            }

            // Mặc định: Lấy danh sách CHƯA xác nhận để hiện ở trang quanly_hoadon.jsp
            request.setAttribute("dsHoaDon", dao.getHoaDonChuaXacNhan());
            request.getRequestDispatcher("admin/quanly_hoadon.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}