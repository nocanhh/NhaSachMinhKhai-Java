<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý Loại Sách</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/css/admin-style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2 mb-3">
                <jsp:include page="menu.jsp" />
            </div>

            <div class="col-md-10">
                <h4 class="section-header mb-4">QUẢN LÝ THỂ LOẠI SÁCH</h4>

                <form action="LoaiAdminController" method="get" class="d-flex mb-4">
                    <input class="form-control me-2 w-50 border-success shadow-sm py-2" type="search" name="key" placeholder="🔍 Tìm tên loại..." value="${param.key}">
                    <button class="btn btn-success fw-bold shadow-sm px-4" type="submit">Tìm kiếm</button>
                </form>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <c:if test="${not empty tb}">
                    <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
                        <i class="bi bi-check-circle-fill me-2"></i> ${tb}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <div class="card bg-white mb-4 shadow-sm border-0 border-top border-4 border-success">
                    <div class="card-body">
                        <h5 class="card-title text-success fw-bold border-bottom pb-2 mb-3">
                            ${isEdit ? '<i class="bi bi-pencil-square"></i> CẬP NHẬT LOẠI' : '<i class="bi bi-plus-circle-fill"></i> THÊM LOẠI MỚI'}
                        </h5>
                        
                        <form action="LoaiAdminController" method="post">
                            <input type="hidden" name="action" value="${isEdit ? 'sua' : 'them'}">
                            <div class="row align-items-end">
                                <div class="col-md-5">
                                    <label class="form-label fw-bold small text-muted">Mã loại</label>
                                    <input type="text" name="maloai" class="form-control border-success" 
                                           value="${maloaiEdit}" ${isEdit ? 'readonly' : ''} required>
                                </div>
                                <div class="col-md-5">
                                    <label class="form-label fw-bold small text-muted">Tên loại</label>
                                    <input type="text" name="tenloai" class="form-control border-success" value="${tenloaiEdit}" required>
                                </div>
                                <div class="col-md-2">
                                    <button type="submit" class="btn ${isEdit ? 'btn-warning' : 'btn-success'} w-100 fw-bold shadow-sm">
                                        ${isEdit ? 'Cập nhật' : 'Lưu'}
                                    </button>
                                    <c:if test="${isEdit}">
                                        <a href="LoaiAdminController" class="btn btn-secondary w-100 mt-1 shadow-sm">Hủy</a>
                                    </c:if>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="table-container shadow">
                    <h5 class="mb-3 text-success fw-bold"><i class="bi bi-list-ul"></i> DANH SÁCH LOẠI SÁCH</h5>
                    <div class="table-responsive">
                        <table class="table table-hover mb-0 text-center align-middle">
                            <thead class="table-light"> <%-- Thêm table-light --%>
                                <tr>
                                    <th style="width: 20%;">Mã loại</th>
                                    <th>Tên loại</th>
                                    <th style="width: 20%;">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                               <c:if test="${empty dsloai}">
                                    <tr>
                                        <td colspan="3" class="text-center py-5">
                                            <i class="bi bi-inbox fs-1 text-muted d-block mb-2"></i>
                                            <span class="text-muted">Không tìm thấy loại sách nào!</span>
                                            <c:if test="${not empty param.key}">
                                                <br>
                                                <a href="LoaiAdminController" class="btn btn-sm btn-outline-success mt-2">Quay lại danh sách</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                                
                                <c:forEach var="l" items="${dsloai}">
                                    <tr>
                                        <td class="fw-bold text-secondary font-monospace">${l.maloai}</td>
                                        
                                        <td class="text-start ps-5 fw-bold text-success">${l.tenloai}</td>
                                        <td>
                                            <a href="LoaiAdminController?action=edit&ml=${l.maloai}&ten=${l.tenloai}" class="btn btn-sm btn-warning text-white me-1 shadow-sm"><i class="bi bi-pencil-square"></i> Sửa</a>
                                            <a href="LoaiAdminController?action=xoa&ml=${l.maloai}" class="btn btn-sm btn-danger shadow-sm" onclick="return confirm('Xóa loại: ${l.tenloai}?')"><i class="bi bi-trash"></i> Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>