<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng - Admin Users</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="h3 mb-0 text-gray-800 fw-bold">
                <i class="bi bi-people-fill text-primary me-2"></i>Quản lý người dùng
            </h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb mb-0">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin" class="text-decoration-none">Admin</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Người dùng</li>
                </ol>
            </nav>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/users/create" class="btn btn-primary shadow-sm">
                <i class="bi bi-person-plus-fill me-1"></i> Thêm người dùng mới
            </a>
        </div>
    </div>

    <!-- Alert Messages -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-check-circle-fill me-2"></i><c:out value="${successMessage}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i><c:out value="${errorMessage}"/>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Search & Filter Card -->
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/users" method="get" class="row g-2 align-items-center">
                <div class="col-md-6 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-text bg-light border-end-0">
                            <i class="bi bi-search text-muted"></i>
                        </span>
                        <input type="text" name="keyword" value="<c:out value='${keyword}'/>"
                               class="form-control border-start-0 ps-0" placeholder="Tìm theo username, họ tên hoặc email...">
                    </div>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-search me-1"></i> Tìm kiếm
                    </button>
                    <c:if test="${not empty keyword}">
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary ms-1">
                            <i class="bi bi-x-circle me-1"></i> Xóa tìm kiếm
                        </a>
                    </c:if>
                </div>
                <div class="col text-md-end text-muted">
                    <small>Tổng cộng: <strong>${totalElements}</strong> người dùng</small>
                </div>
            </form>
        </div>
    </div>

    <!-- Users List Card -->
    <div class="card shadow-sm border-0">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th class="text-center" style="width: 70px;">ID</th>
                            <th style="width: 70px;">Ảnh</th>
                            <th>Tài khoản &amp; Họ tên</th>
                            <th>Email &amp; SĐT</th>
                            <th class="text-center" style="width: 140px;">Vai trò</th>
                            <th class="text-center" style="width: 130px;">Trạng thái</th>
                            <th class="text-end pe-4" style="width: 170px;">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty users}">
                                <c:forEach var="u" items="${users}">
                                    <tr>
                                        <td class="text-center fw-semibold text-muted">#${u.id}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty u.images}">
                                                    <img src="<c:out value='${u.images}'/>" alt="${u.username}"
                                                         class="rounded-circle shadow-sm border" style="width: 42px; height: 42px; object-fit: cover;"
                                                         onerror="this.onerror=null; this.src='https://placehold.co/42x42?text=User';">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="bg-primary-subtle text-primary rounded-circle d-flex align-items-center justify-content-center fw-bold border"
                                                         style="width: 42px; height: 42px; font-size: 16px;">
                                                        ${u.username.substring(0, 1).toUpperCase()}
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="fw-bold text-dark"><c:out value="${u.username}"/></div>
                                            <small class="text-muted">
                                                <c:out value="${not empty u.fullname ? u.fullname : '(Chưa đặt tên)'}"/>
                                            </small>
                                        </td>
                                        <td>
                                            <div>
                                                <i class="bi bi-envelope text-muted me-1"></i>
                                                <small><c:out value="${not empty u.email ? u.email : '(Chưa có)'}"/></small>
                                            </div>
                                            <c:if test="${not empty u.phone}">
                                                <div>
                                                    <i class="bi bi-telephone text-muted me-1"></i>
                                                    <small class="text-muted"><c:out value="${u.phone}"/></small>
                                                </div>
                                            </c:if>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${u.roleid == 1}">
                                                    <span class="badge bg-danger-subtle text-danger border border-danger-subtle px-2 py-1">
                                                        <i class="bi bi-shield-lock-fill me-1"></i>Quản trị viên
                                                    </span>
                                                </c:when>
                                                <c:when test="${u.roleid == 2}">
                                                    <span class="badge bg-warning-subtle text-warning-emphasis border border-warning-subtle px-2 py-1">
                                                        <i class="bi bi-person-gear me-1"></i>Quản lý
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-info-subtle text-info-emphasis border border-info-subtle px-2 py-1">
                                                        <i class="bi bi-person me-1"></i>Người dùng
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${u.active}">
                                                    <span class="badge bg-success-subtle text-success border border-success-subtle px-2 py-1">
                                                        <i class="bi bi-check-circle me-1"></i>Kích hoạt
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary-subtle text-secondary border border-secondary-subtle px-2 py-1">
                                                        <i class="bi bi-dash-circle me-1"></i>Khóa
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-end pe-4">
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/admin/users/edit/${u.id}"
                                                   class="btn btn-outline-primary" title="Chỉnh sửa">
                                                    <i class="bi bi-pencil"></i> Sửa
                                                </a>
                                                <button type="button" class="btn btn-outline-danger"
                                                        data-bs-toggle="modal" data-bs-target="#deleteUserModal${u.id}" title="Xóa"
                                                        ${sessionScope.account != null && sessionScope.account.id == u.id ? 'disabled' : ''}>
                                                    <i class="bi bi-trash"></i> Xóa
                                                </button>
                                            </div>

                                            <!-- Delete Modal -->
                                            <c:if test="${sessionScope.account == null || sessionScope.account.id != u.id}">
                                                <div class="modal fade text-start" id="deleteUserModal${u.id}" tabindex="-1" aria-labelledby="deleteUserModalLabel${u.id}" aria-hidden="true">
                                                    <div class="modal-dialog modal-dialog-centered">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="deleteUserModalLabel${u.id}">
                                                                    <i class="bi bi-exclamation-triangle text-danger me-2"></i>Xác nhận xóa tài khoản
                                                                </h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                Bạn có chắc chắn muốn xóa người dùng <strong><c:out value="${u.username}"/></strong> (#${u.id}) không?
                                                                <div class="small text-muted mt-2">Hành động này sẽ xóa dữ liệu người dùng khỏi cơ sở dữ liệu.</div>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                                <form action="${pageContext.request.contextPath}/admin/users/delete/${u.id}" method="post" class="d-inline">
                                                                    <button type="submit" class="btn btn-danger">
                                                                        <i class="bi bi-trash me-1"></i> Đồng ý xóa
                                                                    </button>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="7" class="text-center py-5 text-muted">
                                        <i class="bi bi-person-x fs-1 d-block mb-2 text-secondary"></i>
                                        <p class="mb-0">Không tìm thấy người dùng nào phù hợp.</p>
                                        <c:if test="${not empty keyword}">
                                            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-sm btn-link mt-2">
                                                Hiển thị tất cả người dùng
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Pagination Footer -->
        <c:if test="${totalPages > 1}">
            <div class="card-footer bg-white border-top py-3 d-flex justify-content-between align-items-center flex-wrap gap-2">
                <div class="text-muted small">
                    Trang <strong>${currentPage + 1}</strong> / <strong>${totalPages}</strong>
                    (Hiển thị tối đa ${pageSize} người dùng/trang)
                </div>
                <nav aria-label="Phân trang người dùng">
                    <ul class="pagination pagination-sm mb-0">
                        <!-- Previous Page -->
                        <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/users?keyword=<c:out value='${keyword}'/>&page=${currentPage - 1}&size=${pageSize}" tabindex="${currentPage == 0 ? '-1' : '0'}">
                                <i class="bi bi-chevron-left"></i> Trước
                            </a>
                        </li>

                        <!-- Page Numbers -->
                        <c:forEach var="i" begin="0" end="${totalPages - 1}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/users?keyword=<c:out value='${keyword}'/>&page=${i}&size=${pageSize}">
                                    ${i + 1}
                                </a>
                            </li>
                        </c:forEach>

                        <!-- Next Page -->
                        <li class="page-item ${currentPage + 1 >= totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/users?keyword=<c:out value='${keyword}'/>&page=${currentPage + 1}&size=${pageSize}" tabindex="${currentPage + 1 >= totalPages ? '-1' : '0'}">
                                Sau <i class="bi bi-chevron-right"></i>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </c:if>
    </div>
</body>
</html>
