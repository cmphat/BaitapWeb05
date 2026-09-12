<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ - BaitapWeb</title>
</head>
<body>
    <div class="row mb-4">
        <div class="col-12">
            <div class="p-4 bg-white rounded-3 shadow-sm border">
                <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
                    <div>
                        <h2 class="fw-bold mb-1">Trang chủ</h2>
                        <p class="text-muted mb-0">
                            Xin chào <strong class="text-primary">${not empty sessionScope.account.fullname ? sessionScope.account.fullname : sessionScope.account.username}</strong>!
                            Đăng nhập đang được duy trì bằng <strong>Session</strong>.
                        </p>
                    </div>
                    <div class="d-flex gap-2">
                        <a href="${pageContext.request.contextPath}/product" class="btn btn-outline-primary">
                            <i class="bi bi-grid me-1"></i> Xem tất cả sản phẩm
                        </a>
                        <a href="${pageContext.request.contextPath}/profile" class="btn btn-primary">
                            <i class="bi bi-person me-1"></i> Trang cá nhân
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="fw-bold text-secondary mb-0">
            <i class="bi bi-stars text-warning me-1"></i> 10 Sản phẩm mới nhất
        </h4>
        <a href="${pageContext.request.contextPath}/product" class="text-decoration-none">
            Xem thêm &raquo;
        </a>
    </div>

    <c:if test="${empty latestProducts}">
        <div class="alert alert-info shadow-sm">
            <i class="bi bi-info-circle me-1"></i> Hiện chưa có sản phẩm nào trong hệ thống.
        </div>
    </c:if>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-5 g-3">
        <c:forEach items="${latestProducts}" var="p">
            <div class="col">
                <div class="card h-100 shadow-sm border-0 product-card hover-shadow">
                    <div class="position-relative bg-light text-center p-2 rounded-top" style="height: 180px; display: flex; align-items: center; justify-content: center; overflow: hidden;">
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
                        <h6 class="card-title text-truncate mb-2" title="${p.productName}">${p.productName}</h6>
                        <p class="text-danger fw-bold fs-6 mb-3 mt-auto">${p.price} VNĐ</p>
                        <a href="${pageContext.request.contextPath}/product/detail?id=${p.productId}" class="btn btn-outline-primary btn-sm w-100">
                            <i class="bi bi-eye me-1"></i> Xem chi tiết
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>
