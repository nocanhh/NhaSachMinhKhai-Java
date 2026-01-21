<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Tất cả Đơn hàng</title>
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
                <h4 class="section-header mb-4">LỊCH SỬ TẤT CẢ ĐƠN HÀNG</h4>
                
                <table class="table table-bordered table-striped text-center align-middle">
                    <thead class="table-secondary">
                        <tr>
                            <th>Mã HĐ</th>
                            <th>Khách hàng</th>
                            <th>Tổng tiền</th>
                            <th>Thanh toán</th>
                            <th>Xác nhận</th>
                            <th>Trạng thái Đơn</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dh" items="${dsAllDonHang}">
                            <tr>
                                <td>${dh.maHoaDon}</td>
                                <td class="text-start">${dh.hoten}</td>
                                <td class="fw-bold text-danger">
                                    <fmt:formatNumber value="${dh.thanhTien}" type="number"/> ₫
                                </td>
                                
                                <td>
                                    <c:choose>
                                        <c:when test="${dh.damua}"><span class="badge bg-success">Đã TT</span></c:when>
                                        <c:otherwise><span class="badge bg-secondary">COD</span></c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:choose>
                                       	<c:when test="${dh.daXacNhan}">
                                            <span class="badge bg-primary"><i class="bi bi-check-all"></i> Đã duyệt</span>
                                        </c:when>
                                        
                                        <c:otherwise>
                                            <a href="QuanLyDonHangController?action=confirm&mahd=${dh.maHoaDon}" 
                                               class="badge bg-warning"
                                               onclick="return confirm('Xác nhận duyệt đơn hàng #${dh.maHoaDon}?')">
                                                <i class="bi bi-check-lg"></i> Duyệt đơn
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                
                                <td>
                                     <span class="badge bg-info text-dark">Đã lưu hệ thống</span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>