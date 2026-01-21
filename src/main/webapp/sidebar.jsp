<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="category-card mb-4">
    <div class="category-header">
        <i class="bi bi-list-ul"></i> DANH MỤC SÁCH
    </div>
    
    <a href="tcController" class="cat-link fw-bold">
        📚 Tất cả sách
    </a>
    
    <c:forEach var="l" items="${dsloai}">
        <a href="tcController?maloai=${l.maloai}" class="cat-link">
            <i class="bi bi-caret-right-fill text-secondary" style="font-size: 0.8rem;"></i> 
            ${l.tenloai}
        </a>
    </c:forEach>
</div>

<div class="card shadow-sm border-0 mt-3 text-center">
    <div class="card-body bg-white rounded">
        <i class="bi bi-headset text-success fs-1"></i>
        <h6 class="fw-bold mt-2">Hỗ trợ 24/7</h6>
        <p class="text-danger fw-bold mb-0">0234 567 1234</p>
    </div>
</div>