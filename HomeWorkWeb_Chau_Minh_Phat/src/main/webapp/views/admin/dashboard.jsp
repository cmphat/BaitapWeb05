<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bảng điều khiển - Admin Dashboard</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="h3 mb-0 text-gray-800 fw-bold">
                <i class="bi bi-speedometer2 text-primary me-2"></i>Bảng điều khiển quản trị (Dashboard)
            </h2>
            <p class="text-muted mb-0">Chào mừng bạn quay trở lại, <strong><c:out value="${sessionScope.account.fullname != null ? sessionScope.account.fullname : sessionScope.account.username}"/></strong>!</p>
        </div>
        <div>
            <span class="badge bg-danger fs-6 px-3 py-2 shadow-sm">
                <i class="bi bi-shield-lock-fill me-1"></i> Role: Quản trị viên (Admin)
            </span>
        </div>
    </div>

    <!-- Stats Cards Row -->
    <div class="row g-4 mb-4">
        <!-- Categories Card -->
        <div class="col-md-4">
            <div class="card border-0 shadow-sm border-start border-success border-4 h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="text-uppercase text-success fw-bold small mb-1">Danh mục (Categories)</div>
                            <div class="h2 mb-0 fw-bold text-dark">${totalCategories}</div>
                        </div>
                        <div class="bg-success-subtle text-success p-3 rounded-circle">
                            <i class="bi bi-tags-fill fs-2"></i>
                        </div>
                    </div>
                    <hr class="my-3 text-muted">
                    <div class="d-flex justify-content-between align-items-center">
                        <a href="${pageContext.request.contextPath}/admin/categories" class="text-success text-decoration-none small fw-semibold">
                            Xem chi tiết <i class="bi bi-arrow-right"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/categories/create" class="btn btn-sm btn-outline-success">
                            <i class="bi bi-plus"></i> Thêm mới
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Users Card -->
        <div class="col-md-4">
            <div class="card border-0 shadow-sm border-start border-info border-4 h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="text-uppercase text-info fw-bold small mb-1">Người dùng (Users)</div>
                            <div class="h2 mb-0 fw-bold text-dark">${totalUsers}</div>
                        </div>
                        <div class="bg-info-subtle text-info p-3 rounded-circle">
                            <i class="bi bi-people-fill fs-2"></i>
                        </div>
                    </div>
                    <hr class="my-3 text-muted">
                    <div class="d-flex justify-content-between align-items-center">
                        <a href="${pageContext.request.contextPath}/admin/users" class="text-info text-decoration-none small fw-semibold">
                            Xem chi tiết <i class="bi bi-arrow-right"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/users/create" class="btn btn-sm btn-outline-info">
                            <i class="bi bi-person-plus"></i> Thêm mới
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Products Card -->
        <div class="col-md-4">
            <div class="card border-0 shadow-sm border-start border-warning border-4 h-100">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="text-uppercase text-warning fw-bold small mb-1">Sản phẩm (Products)</div>
                            <div class="h2 mb-0 fw-bold text-dark">${totalProducts}</div>
                        </div>
                        <div class="bg-warning-subtle text-warning p-3 rounded-circle">
                            <i class="bi bi-box-seam-fill fs-2"></i>
                        </div>
                    </div>
                    <hr class="my-3 text-muted">
                    <div class="d-flex justify-content-between align-items-center">
                        <a href="${pageContext.request.contextPath}/admin/products" class="text-warning text-decoration-none small fw-semibold">
                            Xem chi tiết <i class="bi bi-arrow-right"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/product" class="btn btn-sm btn-outline-warning">
                            <i class="bi bi-eye"></i> Xem public
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Quick Shortcuts Card -->
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-header bg-white py-3">
            <h6 class="m-0 fw-bold text-primary">
                <i class="bi bi-grid-fill me-1"></i> Lối tắt quản lý hệ thống
            </h6>
        </div>
        <div class="card-body">
            <div class="row g-3">
                <div class="col-md-3">
                    <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline-primary w-100 p-3 text-start d-flex align-items-center">
                        <i class="bi bi-tags fs-3 me-3 text-primary"></i>
                        <div>
                            <div class="fw-bold">Quản lý Danh mục</div>
                            <small class="text-muted">CRUD, tìm kiếm &amp; phân trang</small>
                        </div>
                    </a>
                </div>
                <div class="col-md-3">
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-primary w-100 p-3 text-start d-flex align-items-center">
                        <i class="bi bi-people fs-3 me-3 text-primary"></i>
                        <div>
                            <div class="fw-bold">Quản lý Người dùng</div>
                            <small class="text-muted">CRUD, tìm kiếm &amp; phân trang</small>
                        </div>
                    </a>
                </div>
                <div class="col-md-3">
                    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-primary w-100 p-3 text-start d-flex align-items-center">
                        <i class="bi bi-box-seam fs-3 me-3 text-primary"></i>
                        <div>
                            <div class="fw-bold">Quản lý Sản phẩm</div>
                            <small class="text-muted">Quản lý danh sách sản phẩm</small>
                        </div>
                    </a>
                </div>
                <div class="col-md-3">
                    <a href="${pageContext.request.contextPath}/profile" class="btn btn-outline-primary w-100 p-3 text-start d-flex align-items-center">
                        <i class="bi bi-person-circle fs-3 me-3 text-primary"></i>
                        <div>
                            <div class="fw-bold">Hồ sơ cá nhân</div>
                            <small class="text-muted">Xem &amp; cập nhật profile</small>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
