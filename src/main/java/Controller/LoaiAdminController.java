package Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Modal.loai;
import Modal.loaidao;
import Modal.loaibo;

@WebServlet("/LoaiAdminController")
public class LoaiAdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra quyền Admin
        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {
            response.sendRedirect("AdminLoginController");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String ml = request.getParameter("ml");
        String key = request.getParameter("key");
        loaidao dao = new loaidao();

        try {
            // --- XÓA ---
            if ("xoa".equals(action) && ml != null) {
                // Kiểm tra xem loại này có sách không trước khi xóa (Optional)
                // Thực hiện xóa
                try {
                    dao.xoaLoai(ml);
                    request.setAttribute("tb", "Đã xóa thành công loại: " + ml);
                } catch (Exception e) {
                    request.setAttribute("error", "Không thể xóa! Có thể loại này đang chứa sách.");
                }
            }
            
            // --- Hiển thị form sửa ---
            if ("edit".equals(action)) {
                request.setAttribute("maloaiEdit", ml);
                request.setAttribute("tenloaiEdit", request.getParameter("ten"));
                request.setAttribute("isEdit", true); // Cờ để JSP biết đang ở chế độ sửa
            }

            // Lấy danh sách
            ArrayList<loai> dsloai;
            if (key != null && !key.isEmpty()) dsloai = dao.timLoai(key);
            else {
                loaibo lbo = new loaibo();
                dsloai = lbo.getloai();
            }
            request.setAttribute("dsloai", dsloai);
            request.getRequestDispatcher("admin/quanly_loai.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        String maloai = request.getParameter("maloai");
        String tenloai = request.getParameter("tenloai");
        
        loaidao dao = new loaidao();
        
        try {
            if ("them".equals(action)) {
                // 1. KIỂM TRA TRÙNG MÃ
                if (dao.checkMaLoai(maloai)) {
                    request.setAttribute("error", "Mã loại '" + maloai + "' đã tồn tại! Vui lòng chọn mã khác.");
                    doGet(request, response); 
                    return;
                }
                dao.themLoai(maloai, tenloai);
                request.setAttribute("tb", "Thêm mới thành công!");
            } 
            else if ("sua".equals(action)) {
                dao.suaLoai(maloai, tenloai);
                request.setAttribute("tb", "Cập nhật thành công!");
            }
            
            // Chuyển lại về doGet để load danh sách
            doGet(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            doGet(request, response);
        }
    }
}