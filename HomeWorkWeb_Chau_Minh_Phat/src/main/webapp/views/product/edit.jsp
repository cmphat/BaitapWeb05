<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa sản phẩm - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-7">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-warning text-dark py-3">
                    <h5 class="card-title mb-0 fw-bold">
                        <i class="bi bi-pencil-square me-1"></i> Chỉnh sửa sản phẩm #${product.productId}
                    </h5>
                </div>
                <div class="card-body p-4">
                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/admin/product/update" method="post" id="productEditForm" class="needs-validation" novalidate>
                        <input type="hidden" name="productId" value="${product.productId}">

                        <div class="mb-3">
                            <label for="productName" class="form-label fw-semibold">Tên sản phẩm <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="productName" name="productName" 
                                   value="${product.productName}" placeholder="Nhập tên sản phẩm" required autofocus>
                            <div class="invalid-feedback">Vui lòng nhập tên sản phẩm.</div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="price" class="form-label fw-semibold">Giá (VNĐ) <span class="text-danger">*</span></label>
                                <input type="number" step="1000" min="1" class="form-control" id="price" name="price" 
                                       value="${product.price}" required>
                                <div class="invalid-feedback">Vui lòng nhập giá sản phẩm lớn hơn 0.</div>
                            </div>
                            <div class="col-md-6">
                                <label for="categoryId" class="form-label fw-semibold">Danh mục <span class="text-danger">*</span></label>
                                <select class="form-select" id="categoryId" name="categoryId" required>
                                    <c:forEach items="${categories}" var="cate">
                                        <option value="${cate.categoryid}" ${(not empty selectedCategoryId ? selectedCategoryId == cate.categoryid : cate.categoryid == product.category.categoryid) ? 'selected' : ''}>
                                            ${cate.categoryname}
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">Vui lòng chọn danh mục.</div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="image" class="form-label fw-semibold">Link hình ảnh</label>
                            <input type="url" class="form-control" id="image" name="image" 
                                   value="${product.image}">
                            <c:if test="${not empty product.image}">
                                <div class="mt-2">
                                    <small class="text-muted d-block mb-1">Ảnh hiện tại:</small>
                                    <img src="${product.image}" alt="Preview" class="rounded border" style="height: 70px; object-fit: cover;">
                                </div>
                            </c:if>
                        </div>

                        <div class="mb-3">
                            <label for="status" class="form-label fw-semibold">Trạng thái</label>
                            <select class="form-select" id="status" name="status">
                                <option value="1" ${product.status == 1 ? 'selected' : ''}>Hoạt động</option>
                                <option value="0" ${product.status == 0 ? 'selected' : ''}>Khóa</option>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label for="description" class="form-label fw-semibold">Mô tả chi tiết</label>
                            <textarea class="form-control" id="description" name="description" rows="4">${product.description}</textarea>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-warning px-4 fw-semibold">
                                <i class="bi bi-check-lg me-1"></i> Cập nhật sản phẩm
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-secondary">
                                <i class="bi bi-x-circle me-1"></i> Hủy
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
