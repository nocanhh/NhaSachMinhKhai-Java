package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Modal.XacNhanDao;

@WebServlet("/QuanLyDonHangController")
public class QuanLyDonHangController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			XacNhanDao dao = new XacNhanDao();
			
			String action = request.getParameter("action");
            String mahd = request.getParameter("mahd");

            if ("confirm".equals(action) && mahd != null) {
                dao.xacNhanDon(Long.parseLong(mahd));
            }
            
            // Gọi hàm lấy TẤT CẢ
			request.setAttribute("dsAllDonHang", dao.getAllDonHang());
			request.getRequestDispatcher("admin/quanly_donhang.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}