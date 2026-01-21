package Controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Modal.*;
import java.util.ArrayList;

@WebServlet("/giohangController")
public class giohangController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Hiển thị giỏ hàng
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpSession session = request.getSession();
            giohangbo gh = (giohangbo) session.getAttribute("gio");
            if (gh == null) {
                gh = new giohangbo();
                session.setAttribute("gio", gh);
            }

            // Xử lý xóa và xóa toàn bộ từ GET 
            String xoa = request.getParameter("xoa");
            String xoaall = request.getParameter("xoaall");

            if (xoa != null) {
                gh.Xoa(xoa);
                session.setAttribute("gio", gh);
            }

            if (xoaall != null) {
                gh.getDs().clear();
                session.setAttribute("gio", gh);
            }

            // Nạp danh mục loại
            loaibo lbo = new loaibo();
            ArrayList<loai> dsloai = lbo.getloai();
            request.setAttribute("dsloai", dsloai);

            // Hiển thị giohang.jsp ngay cả khi giỏ hàng trống
            RequestDispatcher rd = request.getRequestDispatcher("giohang.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi hiển thị tại giohangController: " + e.getMessage());
        }
    }

    // Xử lý mọi hành động (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            HttpSession session = request.getSession();
            giohangbo gh = (giohangbo) session.getAttribute("gio");
            if (gh == null) {
                gh = new giohangbo();
                session.setAttribute("gio", gh);
            }

            // --- Các tham số ---
            String xoa = request.getParameter("xoa");
            String sua = request.getParameter("sua");
            String xoaall = request.getParameter("xoaall");
            String[] dsxoa = request.getParameterValues("chonXoa");
            String action = request.getParameter("action");

            // --- Cập nhật số lượng ---
            if (sua != null) {
                String soluongmoiParam = request.getParameter("soluongmoi_" + sua);
                if (soluongmoiParam != null) {
                    try {
                        long slm = Long.parseLong(soluongmoiParam);
                        if (slm > 0) {
                            gh.CapNhat(sua, slm);
                        } else if (slm == 0) {
                            gh.Xoa(sua); // Xóa sản phẩm nếu số lượng về 0
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Số lượng không hợp lệ cho masach: " + sua);
                    }
                }
            }

            // --- Xóa 1 sản phẩm ---
            if (xoa != null) {
                gh.Xoa(xoa);
            }

            // --- Xóa toàn bộ ---
            if (xoaall != null) {
                gh.getDs().clear();
            }

            // --- Xóa các sản phẩm đã chọn ---
            if ("xoachon".equals(action) && dsxoa != null) {
                for (String ma : dsxoa) {
                    gh.Xoa(ma);
                }
            }

            // --- Thêm sản phẩm ---
            String masach = request.getParameter("masach");
            String tensach = request.getParameter("tensach");
            String anh = request.getParameter("anh");
            String giaStr = request.getParameter("gia");

            if (masach != null && tensach != null && giaStr != null) {
                try {
                    long gia = Long.parseLong(giaStr);
                    gh.Them(masach, tensach, gia, 1, anh);
                } catch (NumberFormatException e) {
                    System.out.println("Giá không hợp lệ: " + giaStr);
                }
            }

            session.setAttribute("gio", gh);

            // Nếu giỏ hàng trống, chuyển hướng về trang chủ
            if (gh.getDs().isEmpty()) {
                response.sendRedirect("tcController");
                return;
            }

            // Sau khi xử lý xong, chuyển về GET để load lại giỏ hàng
            response.sendRedirect("giohangController");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi xử lý POST tại giohangController: " + e.getMessage());
        }
    }
}