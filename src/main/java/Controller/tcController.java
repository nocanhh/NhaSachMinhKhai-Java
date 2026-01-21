package Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modal.sach;
import Modal.sachbo;
import Modal.loai;
import Modal.loaibo;

@WebServlet("/tcController")
public class tcController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 6;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
        	// lấy tham số
            String ml = request.getParameter("maloai");
            String key = request.getParameter("key");
            String pageStr = request.getParameter("page");

            loaibo lbo = new loaibo();
            sachbo sbo = new sachbo();

            ArrayList<loai> dsloai = lbo.getloai();
            
            //xử lý số trang hiện tại
            int page;
			if (pageStr == null || pageStr.isEmpty()) {
				page = 1; // Mặc định
			} else {
				try {
					page = Integer.parseInt(pageStr);
					if (page < 1) page = 1;
				} catch (NumberFormatException e) {
					page = 1; 
				}
			}
			
			// đếm tổng sách (dựa trên điều kiện)
			int totalItems = sbo.demSach(ml, key);
			
			// tính tổng số trang
			int totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);
						
			// lấy ds sách cho trang hiện tại
			ArrayList<sach> dssach = sbo.getSach(ml, key, page);
						
            /*ArrayList<sach> dssach;

            if (ml != null && !ml.trim().isEmpty()) {
                dssach = sbo.TimMa(ml);
            } else if (key != null && !key.trim().isEmpty()) {
                dssach = sbo.Tim(key);
            } else {
                dssach = sbo.getsach();
            }
			*/
			
			request.setAttribute("dsloai", dsloai);
			request.setAttribute("dssach", dssach);

			// Thông tin phân trang
			request.setAttribute("totalPages", totalPages); // Tổng số trang
			request.setAttribute("currentPage", page);

			// điều liện lọc/tìm kiếm
			request.setAttribute("maloai", ml);
			request.setAttribute("key", key);

			RequestDispatcher rd = request.getRequestDispatcher("tc.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Lỗi tại tcController: " + e.getMessage());
		}
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
