# Hướng dẫn cài đặt SQL Server JDBC Driver

## Bước 1: Tải SQL Server JDBC Driver

1. Truy cập trang chính thức của Microsoft: https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server
2. Tải phiên bản mới nhất của SQL Server JDBC Driver (khuyến nghị phiên bản 12.x)
3. Giải nén file ZIP đã tải

## Bước 2: Thêm Driver vào Project

1. Tìm file `mssql-jdbc-12.x.x.x.jar` trong thư mục đã giải nén
2. Copy file này vào thư mục: `src/main/webapp/WEB-INF/lib/`
3. Đổi tên file thành: `mssql-jdbc.jar` (để dễ quản lý)

## Bước 3: Cấu hình Database

1. Mở SQL Server Management Studio (SSMS)
2. Tạo database mới với tên: `QlSach`
3. Chạy script SQL trong file `database_setup.sql` để tạo bảng và dữ liệu mẫu

## Bước 4: Kiểm tra kết nối

1. Đảm bảo SQL Server đang chạy trên port 1433
2. Kiểm tra username: `sa` và password: `12345`
3. Chạy ứng dụng để test kết nối

## Lưu ý quan trọng:

- Đảm bảo SQL Server đã được cài đặt và đang chạy
- Kiểm tra firewall không chặn port 1433
- Username `sa` phải có quyền tạo database và bảng
- Nếu gặp lỗi SSL, có thể cần thêm `trustServerCertificate=true` vào connection string

## Troubleshooting:

- Lỗi "ClassNotFoundException": Kiểm tra JDBC driver đã được thêm vào classpath
- Lỗi kết nối: Kiểm tra SQL Server service đang chạy
- Lỗi authentication: Kiểm tra username/password và SQL Server authentication mode


