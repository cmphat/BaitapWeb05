<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sản phẩm - BaitapWeb</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom flex-wrap gap-2">
        <div>
            <h2 class="fw-bold mb-1">
                <i class="bi bi-grid me-1 text-primary"></i> Danh sách sản phẩm
            </h2>
            <span class="text-muted">Tổng số: <span class="badge bg-primary">${totalItems}</span> sản phẩm</span>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary btn-sm">
                <i class="bi bi-arrow-left me-1"></i> Trang chủ
            </a>
        </div>
    </div>

    <c:if test="${empty products}">
        <div class="alert alert-info shadow-sm">
            <i class="bi bi-info-circle me-1"></i> Không tìm thấy sản phẩm nào.
        </div>
    </c:if>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4 mb-5">
        <c:forEach items="${products}" var="p">
            <div class="col">
                <div class="card h-100 shadow-sm border-0 product-card">
                    <div class="bg-light text-center p-2 rounded-top" style="height: 200px; display: flex; align-items: center; justify-content: center; overflow: hidden;">
                        <c:choose>
                            <c:when test="${not empty p.image}">
                                <img src="${p.image}" alt="${p.productName}" class="img-fluid" style="max-height: 100%; object-fit: cover;" onerror="this.src='https://placehold.co/300x200?text=No+Image';">
                            </c:when>
                            <c:otherwise>
                                <div class="text-muted text-center">
                                    <i class="bi bi-image fs-1 d-block"></i>
                                    <small>Không có ảnh</small>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body d-flex flex-column p-3">
                        <span class="badge bg-secondary mb-2 align-self-start">
                            ${p.category != null ? p.category.categoryname : 'Chưa phân loại'}
                        </span>
                        <h5 class="card-title text-truncate mb-2" title="${p.productName}">${p.productName}</h5>
                        <p class="text-danger fw-bold fs-5 mb-3 mt-auto">${p.price} VNĐ</p>
                        <a href="${pageContext.request.contextPath}/product/detail?id=${p.productId}" class="btn btn-primary btn-sm w-100">
                            <i class="bi bi-eye me-1"></i> Xem chi tiết
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Bootstrap 5 Pagination -->
    <c:if test="${totalPages > 0}">
        <nav aria-label="Product pagination" class="my-4">
            <ul class="pagination justify-content-center">
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/product?page=${currentPage - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo; Previous</span>
                    </a>
                </li>
                
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/product?page=${i}">${i}</a>
                    </li>
                </c:forEach>
                
                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/product?page=${currentPage + 1}" aria-label="Next">
                        <span aria-hidden="true">Next &raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>
</body>
</html>
