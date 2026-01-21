# 🔧 HƯỚNG DẪN SỬA LỖI DATABASE CONNECTION

## ❌ Lỗi hiện tại:
```
Cannot invoke "java.sql.Connection.prepareStatement(String)" because "conn" is null
```

## ✅ Đã sửa:
- Thêm fallback mechanism: ứng dụng sẽ sử dụng dữ liệu mẫu khi không kết nối được database
- Cải thiện error handling và logging
- Ứng dụng giờ có thể chạy được ngay cả khi chưa có SQL Server JDBC Driver

## 🚀 CÁCH THÊM SQL SERVER JDBC DRIVER:

### Bước 1: Tải JDBC Driver
1. Truy cập: https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server
2. Tải phiên bản mới nhất (khuyến nghị **Microsoft JDBC Driver 12.2 for SQL Server**)
3. Giải nén file ZIP

### Bước 2: Thêm vào Project
1. Tìm file `mssql-jdbc-12.x.x.x.jar` trong thư mục đã giải nén
2. Copy file này vào: `src/main/webapp/WEB-INF/lib/`
3. Đổi tên thành: `mssql-jdbc.jar` (tùy chọn)

### Bước 3: Restart Server
- Restart Tomcat/Server để load driver mới

## 🗄️ CÁCH THIẾT LẬP DATABASE:

### Bước 1: Tạo Database
```sql
CREATE DATABASE QlSach;
```

### Bước 2: Chạy Script SQL
- Mở file `database_setup.sql` 
- Chạy toàn bộ script trong SQL Server Management Studio

### Bước 3: Kiểm tra kết nối
- Username: `sa`
- Password: `12345`
- Server: `localhost:1433`
- Database: `QlSach`

## 🔍 KIỂM TRA:

### 1. Kiểm tra Driver đã load:
- Xem console log khi start server
- Nếu thấy: `"SQL Server JDBC Driver đã được load thành công!"` → OK
- Nếu thấy: `"CẢNH BÁO: Không tìm thấy SQL Server JDBC Driver!"` → Cần thêm driver

### 2. Kiểm tra Database:
- Xem console log khi truy cập trang web
- Nếu thấy: `"Kết nối database thành công!"` → OK
- Nếu thấy: `"Không thể kết nối database, sử dụng dữ liệu mẫu..."` → Database chưa sẵn sàng

## 🛠️ TROUBLESHOOTING:

### Lỗi "ClassNotFoundException":
- ✅ **Đã sửa**: Ứng dụng sẽ fallback về dữ liệu mẫu
- **Giải pháp**: Thêm `mssql-jdbc.jar` vào `WEB-INF/lib/`

### Lỗi kết nối SQL Server:
- Kiểm tra SQL Server service đang chạy
- Kiểm tra port 1433 không bị chặn
- Kiểm tra username/password đúng
- Kiểm tra database `QlSach` đã được tạo

### Lỗi Authentication:
- Kiểm tra SQL Server authentication mode
- Đảm bảo user `sa` có quyền truy cập database

## 📋 TRẠNG THÁI HIỆN TẠI:

✅ **Ứng dụng có thể chạy được ngay bây giờ** với dữ liệu mẫu
✅ **Không còn lỗi NullPointerException**
✅ **Tự động fallback khi không có database**

## 🎯 BƯỚC TIẾP THEO:

1. **Ngay lập tức**: Ứng dụng đã có thể chạy với dữ liệu mẫu
2. **Khi có thời gian**: Tải và cài đặt SQL Server JDBC Driver
3. **Sau đó**: Thiết lập database và chạy script SQL
4. **Cuối cùng**: Ứng dụng sẽ tự động chuyển sang sử dụng database

---
**Lưu ý**: Ứng dụng giờ đã được thiết kế để hoạt động trong cả hai chế độ:
- **Chế độ Database**: Khi có SQL Server và JDBC Driver
- **Chế độ Fallback**: Khi chưa có database (sử dụng dữ liệu mẫu)


