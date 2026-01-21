# 🔍 GIẢI THÍCH LỖI ClassNotFoundException

## ❌ **Nguyên nhân lỗi:**

Lỗi `ClassNotFoundException` xảy ra vì:

1. **SQL Server JDBC Driver chưa được thêm vào project**
2. **File `mssql-jdbc.jar` không có trong thư mục `WEB-INF/lib/`**

## 🔧 **Đã sửa:**

✅ **Loại bỏ catch block sai** trong `DatabaseConnection.getConnection()`
✅ **Khôi phục fallback mechanism** trong `sachdao.java` và `loaidao.java`
✅ **Ứng dụng giờ có thể chạy được** với dữ liệu mẫu

## 🚀 **Trạng thái hiện tại:**

- **Ứng dụng có thể chạy ngay bây giờ** (sử dụng dữ liệu mẫu)
- **Không còn lỗi NullPointerException**
- **Tự động fallback** khi không có JDBC Driver

## 📋 **Để sử dụng database thật:**

### Bước 1: Tải SQL Server JDBC Driver
- Truy cập: https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server
- Tải phiên bản mới nhất
- Giải nén và tìm file `mssql-jdbc-12.x.x.x.jar`

### Bước 2: Thêm vào Project
- Copy file `.jar` vào: `src/main/webapp/WEB-INF/lib/`
- Restart server

### Bước 3: Tạo Database
- Tạo database `QlSach` trong SQL Server
- Chạy script trong file `database_setup.sql`

## 🎯 **Kết quả:**

- **Ngay lập tức**: Ứng dụng chạy với dữ liệu mẫu
- **Sau khi cài JDBC Driver**: Ứng dụng tự động chuyển sang database
- **Không cần thay đổi code** - fallback mechanism tự động hoạt động

---
**Lưu ý**: Ứng dụng được thiết kế để hoạt động trong cả hai chế độ, vì vậy bạn có thể tiếp tục phát triển mà không bị gián đoạn!


