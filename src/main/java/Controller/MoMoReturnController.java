package Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modal.giohang;
import Modal.giohangbo;
import Modal.hoadonbo;
import Modal.khachhang;

@WebServlet("/MoMoReturnController")
public class MoMoReturnController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			
			// 1. Nhận các tham số MoMo gửi về
			String errorCode = request.getParameter("errorCode");
			
			
			// 2. Kiểm tra kết quả (errorCode = 0 là thành công)
			if ("0".equals(errorCode)) { // Thành công
				HttpSession session = request.getSession();
				khachhang kh = (khachhang) session.getAttribute("un");
	            ArrayList<giohang> dsMua = (ArrayList<giohang>) session.getAttribute("dsMuaHang");
	            
	            if (kh != null && dsMua != null) {
	            	hoadonbo hdbo = new hoadonbo();
	                giohangbo ghTam = new giohangbo();
	                for(giohang g : dsMua) ghTam.Them(g.getMasach(), g.getTensach(), g.getGia(), g.getSoluong(), g.getAnh());
	                
	                // QUAN TRỌNG: Truyền TRUE (Đã thanh toán)
	                boolean daThanhToan = true;
	                hdbo.xuLyThanhToan(kh.getMakh(), ghTam, daThanhToan); 
	                
	                // Xóa giỏ hàng
	                giohangbo ghChinh = (giohangbo) session.getAttribute("gio");
	                String[] maDaMua = new String[dsMua.size()];
	                for(int i=0; i<dsMua.size(); i++) maDaMua[i] = dsMua.get(i).getMasach();
	                ghChinh.xoaDaMua(maDaMua);
	                session.setAttribute("gio", ghChinh);
	                
	                session.removeAttribute("dsMuaHang");
	                session.removeAttribute("tongTienMua");
	                
	                request.setAttribute("tb", "Thanh toán MoMo thành công!");
	                request.getRequestDispatcher("lichsumuahangController").forward(request, response);
	            }
			} else {
				request.setAttribute("error", "Giao dịch MoMo thất bại.");
				request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("tcController");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}