package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CheckSepayController")
public class CheckSepayController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String maGiaLap = "DH50"; 
        
        // Tạo một giao dịch giả
        String fakeData = "{"
                + "\"status\": 200,"
                + "\"messages\": {\"success\": true},"
                + "\"transactions\": ["
                + "  {"
                + "    \"id\": \"123456\","
                + "    \"amount_in\": \"500000\"," 
                + "    \"transaction_content\": \"Chuyen khoan " + maGiaLap + "\","
                + "    \"transaction_date\": \"2023-12-10 12:00:00\""
                + "  }"
                + "]"
                + "}";
        //Giả tưởng độ trễ mạng 2s
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(">>> [GIẢ LẬP] Đang trả về giao dịch giả: " + maGiaLap);
        response.getWriter().write(fakeData);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}