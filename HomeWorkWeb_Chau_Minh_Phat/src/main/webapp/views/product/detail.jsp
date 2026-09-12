<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${product.productName} - Chi tiết sản phẩm</title>
</head>
<body>
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/product" class="btn btn-outline-secondary btn-sm">
            <i class="bi bi-arrow-left me-1"></i> Quay lại danh sách sản phẩm
        </a>
    </div>

    <div class="card shadow-sm border-0 rounded-3 overflow-hidden">
        <div class="card-body p-4">
            <div class="row g-4 align-items-center">
                <div class="col-12 col-md-5 text-center">
                    <div class="bg-light p-3 rounded-3 d-flex align-items-center justify-content-center" style="min-height: 300px;">
                        <c:choose>
                            <c:when test="${not empty product.image}">
                                <img src="${product.image}" alt="${product.productName}" class="img-fluid rounded shadow-sm" style="max-height: 320px; object-fit: cover;" onerror="this.src='https://placehold.co/400x300?text=No+Image';">
                            </c:when>
                            <c:otherwise>
                                <div class="text-muted text-center py-5">
                                    <i class="bi bi-image display-1 d-block"></i>
                                    <span>Không có ảnh sản phẩm</span>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="col-12 col-md-7">
                    <div class="d-flex align-items-center gap-2 mb-2">
                        <span class="badge bg-primary">
                            <i class="bi bi-tag me-1"></i> ${product.category != null ? product.category.categoryname : 'Chưa phân loại'}
                        </span>
                        <c:choose>
                            <c:when test="${product.status == 1}">
                                <span class="badge bg-success"><i class="bi bi-check-circle me-1"></i> Đang bán</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-secondary"><i class="bi bi-lock me-1"></i> Ngừng bán</span>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <h2 class="fw-bold mb-3">${product.productName}</h2>
                    <h3 class="text-danger fw-bold mb-4">${product.price} VNĐ</h3>

                    <ul class="list-group list-group-flush mb-4">
                        <li class="list-group-item px-0">
                            <strong>Mã sản phẩm:</strong> #${product.productId}
                        </li>
                        <li class="list-group-item px-0">
                            <strong>Ngày đăng:</strong> ${product.createdAt}
                        </li>
                    </ul>

                    <div class="p-3 bg-light rounded-3 border">
                        <h6 class="fw-bold mb-2"><i class="bi bi-card-text me-1"></i> Mô tả sản phẩm</h6>
                        <p class="mb-0 text-muted" style="white-space: pre-line;">
                            ${not empty product.description ? product.description : 'Chưa có thông tin mô tả chi tiết cho sản phẩm này.'}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
