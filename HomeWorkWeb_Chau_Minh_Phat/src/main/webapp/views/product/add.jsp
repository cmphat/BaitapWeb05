<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm sản phẩm mới - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-7">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-primary text-white py-3">
                    <h5 class="card-title mb-0 fw-bold">
                        <i class="bi bi-plus-circle me-1"></i> Thêm sản phẩm mới
                    </h5>
                </div>
                <div class="card-body p-4">
                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/admin/product/insert" method="post" id="productForm" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="productName" class="form-label fw-semibold">Tên sản phẩm <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="productName" name="productName" 
                                   value="${not empty productName ? productName : param.productName}" placeholder="Nhập tên sản phẩm" required autofocus>
                            <div class="invalid-feedback">Vui lòng nhập tên sản phẩm.</div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="price" class="form-label fw-semibold">Giá (VNĐ) <span class="text-danger">*</span></label>
                                <input type="number" step="1000" min="1" class="form-control" id="price" name="price" 
                                       value="${not empty price ? price : param.price}" placeholder="100000" required>
                                <div class="invalid-feedback">Vui lòng nhập giá sản phẩm lớn hơn 0.</div>
                            </div>
                            <div class="col-md-6">
                                <label for="categoryId" class="form-label fw-semibold">Danh mục <span class="text-danger">*</span></label>
                                <select class="form-select" id="categoryId" name="categoryId" required>
                                    <option value="">-- Chọn danh mục --</option>
                                    <c:forEach items="${categories}" var="cate">
                                        <option value="${cate.categoryid}" ${(selectedCategoryId == cate.categoryid || param.categoryId == cate.categoryid) ? 'selected' : ''}>
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
                                   value="${param.image}" placeholder="https://images.unsplash.com/...">
                        </div>

                        <div class="mb-3">
                            <label for="status" class="form-label fw-semibold">Trạng thái</label>
                            <select class="form-select" id="status" name="status">
                                <option value="1" ${param.status != '0' ? 'selected' : ''}>Hoạt động</option>
                                <option value="0" ${param.status == '0' ? 'selected' : ''}>Khóa</option>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label for="description" class="form-label fw-semibold">Mô tả chi tiết</label>
                            <textarea class="form-control" id="description" name="description" rows="4" 
                                      placeholder="Nhập thông số, đặc điểm sản phẩm...">${param.description}</textarea>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary px-4">
                                <i class="bi bi-save me-1"></i> Lưu sản phẩm
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
