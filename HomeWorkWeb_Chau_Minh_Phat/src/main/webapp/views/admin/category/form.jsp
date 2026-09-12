<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Chỉnh sửa danh mục' : 'Thêm danh mục mới'} - Admin Category</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="h3 mb-0 text-gray-800 fw-bold">
                        <i class="bi ${isEdit ? 'bi-pencil-square' : 'bi-plus-circle'} text-primary me-2"></i>
                        ${isEdit ? 'Chỉnh sửa danh mục' : 'Thêm danh mục mới'}
                    </h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb mb-0">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin" class="text-decoration-none">Admin</a></li>
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/categories" class="text-decoration-none">Danh mục</a></li>
                            <li class="breadcrumb-item active" aria-current="page">${isEdit ? 'Chỉnh sửa' : 'Thêm mới'}</li>
                        </ol>
                    </nav>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left me-1"></i> Quay lại danh sách
                    </a>
                </div>
            </div>

            <!-- Error message if any -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i><c:out value="${errorMessage}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="card shadow-sm border-0">
                <div class="card-header bg-white py-3 border-bottom">
                    <h6 class="m-0 fw-bold text-primary">
                        <i class="bi bi-info-circle me-1"></i> Thông tin danh mục
                    </h6>
                </div>
                <div class="card-body p-4">
                    <form action="${isEdit ? pageContext.request.contextPath.concat('/admin/categories/edit/').concat(category.categoryid) : pageContext.request.contextPath.concat('/admin/categories/create')}"
                          method="post" class="needs-validation" novalidate>

                        <c:if test="${isEdit}">
                            <div class="mb-3">
                                <label for="categoryid" class="form-label fw-semibold">Mã danh mục (ID)</label>
                                <input type="text" id="categoryid" class="form-control bg-light" value="${category.categoryid}" readonly>
                            </div>
                        </c:if>

                        <div class="mb-3">
                            <label for="categoryname" class="form-label fw-semibold">
                                Tên danh mục <span class="text-danger">*</span>
                            </label>
                            <input type="text" class="form-control" id="categoryname" name="categoryname"
                                   value="<c:out value='${category.categoryname}'/>" required autofocus
                                   placeholder="Ví dụ: Máy ảnh kỹ thuật số, Phụ kiện...">
                            <div class="invalid-feedback">
                                Vui lòng nhập tên danh mục hợp lệ.
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="images" class="form-label fw-semibold">Đường dẫn hình ảnh (URL)</label>
                            <input type="text" class="form-control" id="images" name="images"
                                   value="<c:out value='${category.images}'/>"
                                   placeholder="https://example.com/image.jpg">
                            <div class="form-text">Bạn có thể dán đường dẫn ảnh đại diện cho danh mục.</div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-semibold d-block">Trạng thái hoạt động</label>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="status" id="statusActive" value="1"
                                       ${category.status == 1 || empty category ? 'checked' : ''}>
                                <label class="form-check-label text-success fw-medium" for="statusActive">
                                    <i class="bi bi-check-circle me-1"></i> Hoạt động
                                </label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="status" id="statusInactive" value="0"
                                       ${category.status == 0 ? 'checked' : ''}>
                                <label class="form-check-label text-secondary fw-medium" for="statusInactive">
                                    <i class="bi bi-pause-circle me-1"></i> Tạm khóa
                                </label>
                            </div>
                        </div>

                        <hr class="my-4">

                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-light px-4">
                                Hủy bỏ
                            </a>
                            <button type="submit" class="btn btn-primary px-4 shadow-sm">
                                <i class="bi bi-save me-1"></i> ${isEdit ? 'Lưu cập nhật' : 'Tạo danh mục'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
