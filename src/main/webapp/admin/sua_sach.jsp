<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Sửa Sách</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/admin-style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2"><jsp:include page="menu.jsp" /></div>
            
            <div class="col-md-10">
                <div class="card shadow border-0" style="max-width: 800px; margin: 0 auto;">
                    <div class="card-header bg-warning text-dark fw-bold">
                        ✏️ CẬP NHẬT THÔNG TIN SÁCH
                    </div>
                    <div class="card-body">
                        <form action="SachAdminController" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="sua">
                            
                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Mã sách:</label>
                                    <input type="text" name="masach" class="form-control bg-light" value="${sachEdit.masach}" readonly>
                                </div>
                                <div class="col-md-8">
                                    <label class="form-label fw-bold">Tên sách:</label>
                                    <input type="text" name="tensach" class="form-control" value="${sachEdit.tensach}" required>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label fw-bold">Tác giả:</label>
                                <input type="text" name="tacgia" class="form-control" value="${sachEdit.tacgia}" required>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Giá bán:</label>
                                    <input type="number" name="gia" class="form-control" value="${sachEdit.gia}" required>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Số lượng kho:</label>
                                    <input type="number" name="soluong" class="form-control" value="${sachEdit.soluong}" required>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Loại sách:</label>
                                    <select name="maloai" class="form-select">
                                        <c:forEach var="l" items="${dsloai}">
                                            <option value="${l.maloai}" ${l.maloai == sachEdit.maloai ? 'selected' : ''}>
                                                ${l.tenloai}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="mb-4 p-3 bg-light rounded border">
                                <div class="d-flex align-items-center">
                                    <div class="me-3 text-center">
                                        <span class="d-block small text-muted mb-1">Ảnh hiện tại</span>
                                        <img src="${sachEdit.anh}" height="80" class="rounded shadow-sm">
                                    </div>
                                    <div class="flex-grow-1">
                                        <label class="form-label">Chọn ảnh mới (Nếu muốn thay đổi):</label>
                                        <input type="file" name="txtfile" class="form-control">
                                    </div>
                                </div>
                            </div>
                            
                            <div class="text-end">
                                <a href="SachAdminController" class="btn btn-secondary me-2">Quay lại</a>
                                <button type="submit" class="btn btn-warning fw-bold px-4">Cập nhật Ngay</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</body>
</html>