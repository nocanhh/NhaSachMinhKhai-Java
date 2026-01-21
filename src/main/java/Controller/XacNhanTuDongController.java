package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Modal.hoadondao;

@WebServlet("/XacNhanTuDongController")
public class XacNhanTuDongController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String maHoaDonStr = request.getParameter("mahd");
			if(maHoaDonStr != null) {
				long maHoaDon = Long.parseLong(maHoaDonStr);
				
				// Cập nhật trạng thái ĐÃ MUA
				hoadondao dao = new hoadondao();
				dao.capNhatDaMua(maHoaDon);
				
				// Thông báo và quay về lịch sử
				request.setAttribute("tb", "Thanh toán thành công! Hệ thống đã ghi nhận tự động.");
				request.getRequestDispatcher("lichsumuahangController").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("lichsumuahangController");
		}
	}
}