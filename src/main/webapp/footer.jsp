<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
    /* Đẩy nội dung trang web lên để không bị footer che mất */
    body {
        padding-bottom: 80px !important; /* Quan trọng */
    }

    .main-footer {
        position: fixed !important;    /* Bắt buộc cố định */
        left: 0 !important;
        bottom: 0 !important;          /* Dính đáy */
        width: 100% !important;        /* Tràn ngang */
        height: 60px;                  /* Chiều cao cố định */
        
        background-color: #ffffff !important; /* Nền trắng */
        color: #666 !important;
        
        /* Viền xanh chủ đạo #20B970 */
        border-top: 3px solid #20B970 !important; 
        
        /* Căn giữa nội dung */
        display: flex !important;
        align-items: center !important;
        justify-content: center !important;
        
        /* Đổ bóng ngược lên trên */
        box-shadow: 0 -2px 10px rgba(0,0,0,0.1) !important;
        
        /* Nổi lên trên tất cả các thành phần khác */
        z-index: 99999 !important; 
        font-family: 'Segoe UI', sans-serif;
        font-size: 14px;
    }
    
    .main-footer strong {
        color: #333;
        font-weight: bold;
    }
    
    .main-footer .designer {
        color: #20B970; /* Màu xanh tên bạn */
        font-weight: bold;
        margin-left: 5px;
    }
</style>

<footer class="main-footer">
    <div>
        &copy; 2025 <strong>Bookstore</strong>. Design by <span class="designer">Nhi Trương</span>.
    </div>
</footer>