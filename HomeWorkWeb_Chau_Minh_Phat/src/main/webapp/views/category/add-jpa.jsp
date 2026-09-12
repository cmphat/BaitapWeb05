<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Category - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-7 col-lg-6">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-primary text-white py-3">
                    <h5 class="card-title mb-0 fw-bold">
                        <i class="bi bi-plus-circle me-1"></i> Thêm Category mới (JPA)
                    </h5>
                </div>
                <div class="card-body p-4">
                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/admin/category/insert" method="post" id="categoryForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="categoryname" class="form-label fw-semibold">Tên danh mục <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="categoryname" name="categoryname" 
                                   value="${not empty categoryname ? categoryname : param.categoryname}" placeholder="Nhập tên danh mục (VD: Máy ảnh, Phụ kiện...)" required autofocus>
                            <div class="invalid-feedback">Vui lòng nhập tên danh mục.</div>
                        </div>

                        <div class="mb-3">
                            <label for="images" class="form-label fw-semibold">Link hình ảnh icon</label>
                            <input type="url" class="form-control" id="images" name="images" 
                                   value="${param.images}" placeholder="https://images.unsplash.com/...">
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-semibold d-block">Trạng thái</label>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="status" id="statusActive" value="1" checked>
                                <label class="form-check-label text-success fw-semibold" for="statusActive">Hoạt động</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="status" id="statusLocked" value="0">
                                <label class="form-check-label text-secondary" for="statusLocked">Khóa</label>
                            </div>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary px-4">
                                <i class="bi bi-plus-lg me-1"></i> Thêm danh mục
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left me-1"></i> Quay lại
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>