<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Lịch sử Hóa đơn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/css/admin-style.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp" />
    
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-md-2"><jsp:include page="menu.jsp" /></div>
            
            <div class="col-md-10">
            	<h4 class="section-header mb-4">DANH SÁCH HOÁ ĐƠN ĐÃ THANH TOÁN</h4>
                
                <table class="table table-striped table-bordered table-hover">
                    <thead class="table-success">
                        <tr>
                            <th>Mã HĐ</th>
                            <th>Khách hàng</th>
                            <th>Ngày mua</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="hd" items="${dsDaThanhToan}">
                            <tr>
                                <td>${hd.maHoaDon}</td>
                                <td>${hd.tenKhachHang}</td>
                                <td><fmt:formatDate value="${hd.ngayMua}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td class="text-danger fw-bold">
                                    <fmt:formatNumber value="${hd.tongTien}" type="number"/> VNĐ
                                </td>
                                <td>
                                    <span class="badge bg-success">Đã thanh toán</span>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty dsDaThanhToan}">
                            <tr><td colspan="5" class="text-center">Chưa có hóa đơn nào đã thanh toán.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
</body>
</html>