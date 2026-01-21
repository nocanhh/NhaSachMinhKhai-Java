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

import Modal.LichSuMuaHang; // Import bean mới
import Modal.hoadonbo;
import Modal.khachhang;
import Modal.loai;
import Modal.loaibo;

@WebServlet("/lichsumuahangController")
public class lichsumuahangController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public lichsumuahangController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            
            // 1. KIỂM TRA ĐĂNG NHẬP
            khachhang kh = (khachhang) session.getAttribute("un");
            
            if (kh == null) {
                response.sendRedirect("tcController");
                return; 
            }
            
            // 2. LẤY LỊCH SỬ MUA HÀNG
            hoadonbo hdbo = new hoadonbo();
            long makh = kh.getMakh();
            ArrayList<LichSuMuaHang> dsLichSu = hdbo.getLichSu(makh);
            
            loaibo lbo = new loaibo();
            ArrayList<loai> dsloai = lbo.getloai();
            request.setAttribute("dsloai", dsloai);
            
            // 3. GỬI DỮ LIỆU SANG JSP
            request.setAttribute("dsLichSu", dsLichSu);
            RequestDispatcher rd = request.getRequestDispatcher("lichsumuahang.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi tại lichsumuahangController: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}