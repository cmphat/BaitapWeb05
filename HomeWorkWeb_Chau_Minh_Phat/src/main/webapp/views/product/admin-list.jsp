<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Sản phẩm - BaitapWeb</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom flex-wrap gap-2">
        <h2 class="fw-bold mb-0">
            <i class="bi bi-box-seam me-1 text-primary"></i> Quản lý Sản phẩm
        </h2>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-success">
                <i class="bi bi-plus-circle me-1"></i> Thêm sản phẩm mới
            </a>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
                <i class="bi bi-house me-1"></i> Về trang chủ
            </a>
        </div>
    </div>

    <c:if test="${empty products}">
        <div class="alert alert-info shadow-sm">
            <i class="bi bi-info-circle me-1"></i> Chưa có sản phẩm nào trong hệ thống.
        </div>
    </c:if>

    <c:if test="${not empty products}">
        <div class="card shadow-sm border-0 rounded-3 overflow-hidden">
            <div class="table-responsive">
                <table class="table table-hover table-striped align-middle mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th style="width: 70px;">ID</th>
                            <th style="width: 100px;">Hình ảnh</th>
                            <th>Tên sản phẩm</th>
                            <th>Giá (VNĐ)</th>
                            <th>Danh mục</th>
                            <th>Trạng thái</th>
                            <th>Ngày tạo</th>
                            <th style="width: 160px;" class="text-center">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${products}" var="p">
                            <tr>
                                <td class="fw-bold">#${p.productId}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty p.image}">
                                            <img src="${p.image}" alt="${p.productName}" class="rounded border" style="width: 60px; height: 60px; object-fit: cover;" onerror="this.src='https://placehold.co/60x60?text=No+Img';">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="bg-light rounded border text-muted d-flex align-items-center justify-content-center" style="width: 60px; height: 60px;">
                                                <i class="bi bi-image"></i>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="fw-semibold">${p.productName}</div>
                                </td>
                                <td class="text-danger fw-bold">${p.price}</td>
                                <td>
                                    <span class="badge bg-secondary">
                                        ${p.category != null ? p.category.categoryname : 'N/A'}
                                    </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.status == 1}">
                                            <span class="badge bg-success">Hoạt động</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">Khóa</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="small text-muted">${p.createdAt}</td>
                                <td class="text-center">
                                    <div class="btn-group btn-group-sm" role="group">
                                        <a href="${pageContext.request.contextPath}/admin/product/edit?id=${p.productId}" class="btn btn-warning" title="Chỉnh sửa">
                                            <i class="bi bi-pencil-square"></i> Sửa
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/product/delete?id=${p.productId}" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xoá sản phẩm này?');" title="Xóa">
                                            <i class="bi bi-trash"></i> Xóa
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</body>
</html>
