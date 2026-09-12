<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý danh mục - Admin Category</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="h3 mb-0 text-gray-800 fw-bold">
                <i class="bi bi-tags-fill text-primary me-2"></i>Quản lý danh mục
            </h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb mb-0">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin" class="text-decoration-none">Admin</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Danh mục</li>
                </ol>
            </nav>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/admin/categories/create" class="btn btn-primary shadow-sm">
                <i class="bi bi-plus-circle me-1"></i> Thêm danh mục mới
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
            <form action="${pageContext.request.contextPath}/admin/categories" method="get" class="row g-2 align-items-center">
                <div class="col-md-6 col-lg-5">
                    <div class="input-group">
                        <span class="input-group-text bg-light border-end-0">
                            <i class="bi bi-search text-muted"></i>
                        </span>
                        <input type="text" name="keyword" value="<c:out value='${keyword}'/>"
                               class="form-control border-start-0 ps-0" placeholder="Nhập tên danh mục cần tìm...">
                    </div>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-search me-1"></i> Tìm kiếm
                    </button>
                    <c:if test="${not empty keyword}">
                        <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline-secondary ms-1">
                            <i class="bi bi-x-circle me-1"></i> Xóa tìm kiếm
                        </a>
                    </c:if>
                </div>
                <div class="col text-md-end text-muted">
                    <small>Tổng cộng: <strong>${totalElements}</strong> danh mục</small>
                </div>
            </form>
        </div>
    </div>

    <!-- Category List Card -->
    <div class="card shadow-sm border-0">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light">
                        <tr>
                            <th class="text-center" style="width: 80px;">Mã</th>
                            <th style="width: 100px;">Hình ảnh</th>
                            <th>Tên danh mục</th>
                            <th class="text-center" style="width: 140px;">Trạng thái</th>
                            <th class="text-end pe-4" style="width: 180px;">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty categories}">
                                <c:forEach var="cat" items="${categories}">
                                    <tr>
                                        <td class="text-center fw-semibold text-muted">#${cat.categoryid}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty cat.images}">
                                                    <img src="<c:out value='${cat.images}'/>" alt="${cat.categoryname}"
                                                         class="rounded shadow-sm border" style="width: 48px; height: 48px; object-fit: cover;"
                                                         onerror="this.onerror=null; this.src='https://placehold.co/48x48?text=No+Img';">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="bg-light rounded d-flex align-items-center justify-content-center text-muted border"
                                                         style="width: 48px; height: 48px;">
                                                        <i class="bi bi-image"></i>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="fw-bold text-dark"><c:out value="${cat.categoryname}"/></div>
                                            <c:if test="${not empty cat.images}">
                                                <small class="text-muted text-truncate d-inline-block" style="max-width: 320px;">
                                                    <i class="bi bi-link-45deg"></i> <c:out value="${cat.images}"/>
                                                </small>
                                            </c:if>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${cat.status == 1}">
                                                    <span class="badge bg-success-subtle text-success border border-success-subtle px-2 py-1">
                                                        <i class="bi bi-check-circle me-1"></i>Hoạt động
                                                    </span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary-subtle text-secondary border border-secondary-subtle px-2 py-1">
                                                        <i class="bi bi-pause-circle me-1"></i>Tạm khóa
                                                    </span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-end pe-4">
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/admin/categories/edit/${cat.categoryid}"
                                                   class="btn btn-outline-primary" title="Chỉnh sửa">
                                                    <i class="bi bi-pencil"></i> Sửa
                                                </a>
                                                <button type="button" class="btn btn-outline-danger"
                                                        data-bs-toggle="modal" data-bs-target="#deleteModal${cat.categoryid}" title="Xóa">
                                                    <i class="bi bi-trash"></i> Xóa
                                                </button>
                                            </div>

                                            <!-- Delete Confirmation Modal -->
                                            <div class="modal fade text-start" id="deleteModal${cat.categoryid}" tabindex="-1" aria-labelledby="deleteModalLabel${cat.categoryid}" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="deleteModalLabel${cat.categoryid}">
                                                                <i class="bi bi-exclamation-triangle text-danger me-2"></i>Xác nhận xóa danh mục
                                                            </h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            Bạn có chắc chắn muốn xóa danh mục <strong><c:out value="${cat.categoryname}"/></strong> (Mã #${cat.categoryid}) không?
                                                            <div class="small text-muted mt-2">Lưu ý: Thao tác này không thể hoàn tác nếu danh mục không có sản phẩm liên kết.</div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                            <form action="${pageContext.request.contextPath}/admin/categories/delete/${cat.categoryid}" method="post" class="d-inline">
                                                                <button type="submit" class="btn btn-danger">
                                                                    <i class="bi bi-trash me-1"></i> Đồng ý xóa
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="text-center py-5 text-muted">
                                        <i class="bi bi-inbox fs-1 d-block mb-2 text-secondary"></i>
                                        <p class="mb-0">Không tìm thấy danh mục nào phù hợp.</p>
                                        <c:if test="${not empty keyword}">
                                            <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-sm btn-link mt-2">
                                                Hiển thị tất cả danh mục
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
                    (Hiển thị tối đa ${pageSize} mục/trang)
                </div>
                <nav aria-label="Phân trang danh mục">
                    <ul class="pagination pagination-sm mb-0">
                        <!-- Previous Page Link -->
                        <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/categories?keyword=<c:out value='${keyword}'/>&page=${currentPage - 1}&size=${pageSize}" tabindex="${currentPage == 0 ? '-1' : '0'}">
                                <i class="bi bi-chevron-left"></i> Trước
                            </a>
                        </li>

                        <!-- Page Numbers -->
                        <c:forEach var="i" begin="0" end="${totalPages - 1}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/admin/categories?keyword=<c:out value='${keyword}'/>&page=${i}&size=${pageSize}">
                                    ${i + 1}
                                </a>
                            </li>
                        </c:forEach>

                        <!-- Next Page Link -->
                        <li class="page-item ${currentPage + 1 >= totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/categories?keyword=<c:out value='${keyword}'/>&page=${currentPage + 1}&size=${pageSize}" tabindex="${currentPage + 1 >= totalPages ? '-1' : '0'}">
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
