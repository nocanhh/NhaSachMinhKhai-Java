<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Quản lý Sách</title>
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
                <h4 class="section-header mb-4">QUẢN LÝ SÁCH</h4>
                
                <c:if test="${not empty tb}">
                    <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
                        <i class="bi bi-check-circle-fill me-2"></i> ${tb}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <form action="SachAdminController" method="get" class="d-flex mb-4">
                    <input class="form-control me-2 w-50 border-success shadow-sm py-2" type="search" name="key" placeholder="🔍 Tìm tên sách hoặc tác giả..." value="${param.key}">
                    <button class="btn btn-success fw-bold shadow-sm px-4" type="submit">Tìm kiếm</button>
                </form>

                <div class="card bg-white mb-4 shadow-sm border-0 border-top border-4 border-success">
                    <div class="card-body">
                        <h5 class="card-title text-success fw-bold border-bottom pb-2 mb-3">
                            <i class="bi bi-plus-circle-fill"></i> NHẬP SÁCH MỚI
                        </h5>
                        <form action="SachAdminController" method="post" enctype="multipart/form-data">
                           <input type="hidden" name="action" value="them"> 
                            
                            <div class="row g-3">
                                <div class="col-md-2">
                                    <label class="form-label fw-bold small text-muted">Mã sách</label>
                                    <input type="text" name="masach" class="form-control border-success" required>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label fw-bold small text-muted">Tên sách</label>
                                    <input type="text" name="tensach" class="form-control border-success" required>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label fw-bold small text-muted">Tác giả</label>
                                    <input type="text" name="tacgia" class="form-control border-success" required>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label fw-bold small text-muted">Loại sách</label>
                                    <select name="maloai" class="form-select border-success">
                                        <c:forEach var="l" items="${dsloai}">
                                            <option value="${l.maloai}">${l.tenloai}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label fw-bold small text-muted">Giá bán</label>
                                    <input type="number" name="gia" class="form-control border-success text-price fw-bold" required>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label fw-bold small text-muted">Số lượng</label>
                                    <input type="number" name="soluong" class="form-control border-success" required>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label fw-bold small text-muted">Ảnh bìa</label>
                                    <input type="file" name="txtfile" class="form-control border-success" required>
                                </div>
                                <div class="col-md-3 d-flex align-items-end">
                                    <button type="submit" class="btn btn-success fw-bold w-100 shadow-sm">
                                        <i class="bi bi-save"></i> LƯU SÁCH
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="table-container shadow">
                    <h5 class="mb-3 text-success fw-bold"><i class="bi bi-journal-bookmark-fill"></i> KHO SÁCH HIỆN TẠI</h5>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle text-center mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>Ảnh</th>
                                    <th>Mã</th>
                                    <th>Tên sách</th>
                                    <th>Giá bán</th>
                                    <th>Kho</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                
                                <c:if test="${empty dssach}">
                                    <tr>
                                        <td colspan="6" class="text-center py-5">
                                            <i class="bi bi-inbox fs-1 text-muted d-block mb-2"></i>
                                            <span class="text-muted">Không tìm thấy dữ liệu sách nào!</span>
                                            <c:if test="${not empty param.key}">
                                                <br>
                                                <a href="SachAdminController" class="btn btn-sm btn-outline-success mt-2">Quay lại danh sách</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                                
                                <c:forEach var="s" items="${dssach}">
                                    <tr>
                                        <td><img src="${s.anh}" height="50" class="rounded border shadow-sm"></td>
                                        <td class="text-muted fw-bold small">${s.masach}</td>
                                        <td class="text-start fw-bold" style="max-width: 300px;">
                                            <span class="text-success">${s.tensach}</span><br>
                                            <small class="text-muted fw-normal"><i class="bi bi-person"></i> ${s.tacgia}</small>
                                        </td>
                                        <td class="text-price fw-bold"><fmt:formatNumber value="${s.gia}" type="number"/> ₫</td>
                                        <td>
                                            <span class="badge ${s.soluong <= 10 ? 'bg-warning' : 'bg-success'} rounded-pill px-3">
                                                ${s.soluong}
                                            </span>
                                        </td>
                                        <td>
                                            <a href="SachAdminController?action=edit&ms=${s.masach}" class="btn btn-sm btn-warning text-white me-1 shadow-sm" title="Sửa">
                                                <i class="bi bi-pencil-square"></i>
                                            </a>
                                            <a href="SachAdminController?action=xoa&ms=${s.masach}" class="btn btn-sm btn-danger shadow-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa sách này?')" title="Xóa">
                                                <i class="bi bi-trash"></i>
                                            </a>
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