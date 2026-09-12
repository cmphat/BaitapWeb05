<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Chỉnh sửa người dùng' : 'Thêm người dùng mới'} - Admin User</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="h3 mb-0 text-gray-800 fw-bold">
                        <i class="bi ${isEdit ? 'bi-person-gear' : 'bi-person-plus'} text-primary me-2"></i>
                        ${isEdit ? 'Chỉnh sửa thông tin người dùng' : 'Thêm người dùng mới'}
                    </h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb mb-0">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin" class="text-decoration-none">Admin</a></li>
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/users" class="text-decoration-none">Người dùng</a></li>
                            <li class="breadcrumb-item active" aria-current="page">${isEdit ? 'Chỉnh sửa' : 'Thêm mới'}</li>
                        </ol>
                    </nav>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary">
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
                        <i class="bi bi-person-lines-fill me-1"></i> Chi tiết tài khoản
                    </h6>
                </div>
                <div class="card-body p-4">
                    <form action="${isEdit ? pageContext.request.contextPath.concat('/admin/users/edit/').concat(user.id) : pageContext.request.contextPath.concat('/admin/users/create')}"
                          method="post" class="needs-validation" novalidate>

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="username" class="form-label fw-semibold">
                                    Tên đăng nhập (Username) <span class="text-danger">*</span>
                                </label>
                                <input type="text" class="form-control ${isEdit ? 'bg-light' : ''}" id="username" name="username"
                                       value="<c:out value='${user.username}'/>" ${isEdit ? 'readonly' : 'required autofocus'}
                                       minlength="3" placeholder="Nhập username...">
                                <div class="invalid-feedback">
                                    Tên đăng nhập tối thiểu 3 ký tự và không được để trống.
                                </div>
                            </div>

                            <div class="col-md-6">
                                <c:choose>
                                    <c:when test="${!isEdit}">
                                        <label for="password" class="form-label fw-semibold">
                                            Mật khẩu khởi tạo <span class="text-danger">*</span>
                                        </label>
                                        <input type="password" class="form-control" id="password" name="password"
                                               required minlength="6" placeholder="Tối thiểu 6 ký tự...">
                                        <div class="invalid-feedback">
                                            Mật khẩu tối thiểu 6 ký tự.
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <label for="newPassword" class="form-label fw-semibold">
                                            Mật khẩu mới (Tùy chọn)
                                        </label>
                                        <input type="password" class="form-control" id="newPassword" name="newPassword"
                                               minlength="6" placeholder="Để trống nếu giữ nguyên mật khẩu cũ">
                                        <div class="form-text">Chỉ nhập nếu bạn muốn thay đổi mật khẩu cho người dùng này.</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="col-md-6">
                                <label for="fullname" class="form-label fw-semibold">Họ và tên</label>
                                <input type="text" class="form-control" id="fullname" name="fullname"
                                       value="<c:out value='${user.fullname}'/>"
                                       placeholder="Ví dụ: Nguyễn Văn A">
                            </div>

                            <div class="col-md-6">
                                <label for="email" class="form-label fw-semibold">
                                    Địa chỉ Email <span class="text-danger">*</span>
                                </label>
                                <input type="email" class="form-control" id="email" name="email"
                                       value="<c:out value='${user.email}'/>" required
                                       placeholder="example@domain.com">
                                <div class="invalid-feedback">
                                    Vui lòng nhập địa chỉ email hợp lệ.
                                </div>
                            </div>

                            <div class="col-md-6">
                                <label for="phone" class="form-label fw-semibold">Số điện thoại</label>
                                <input type="tel" class="form-control" id="phone" name="phone"
                                       value="<c:out value='${user.phone}'/>"
                                       pattern="[0-9]{10,11}"
                                       placeholder="10 hoặc 11 chữ số">
                                <div class="invalid-feedback">
                                    Số điện thoại phải gồm 10 hoặc 11 chữ số.
                                </div>
                            </div>

                            <div class="col-md-6">
                                <label for="roleid" class="form-label fw-semibold">Vai trò phân quyền</label>
                                <select class="form-select" id="roleid" name="roleid">
                                    <option value="1" ${user.roleid == 1 ? 'selected' : ''}>Quản trị viên (Admin - Role 1)</option>
                                    <option value="2" ${user.roleid == 2 ? 'selected' : ''}>Quản lý (Manager - Role 2)</option>
                                    <option value="3" ${user.roleid == 3 || empty user.roleid ? 'selected' : ''}>Người dùng (User - Role 3)</option>
                                </select>
                            </div>

                            <div class="col-12">
                                <label for="images" class="form-label fw-semibold">Ảnh đại diện (URL)</label>
                                <input type="text" class="form-control" id="images" name="images"
                                       value="<c:out value='${user.images}'/>"
                                       placeholder="https://example.com/avatar.jpg hoặc /uploads/profile/...">
                            </div>

                            <div class="col-12">
                                <label class="form-label fw-semibold d-block">Trạng thái kích hoạt</label>
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" role="switch" id="activeSwitch" name="active" value="true"
                                           ${user.active ? 'checked' : ''}>
                                    <label class="form-check-label fw-medium ${user.active ? 'text-success' : 'text-muted'}" for="activeSwitch">
                                        Kích hoạt tài khoản (cho phép đăng nhập hệ thống)
                                    </label>
                                </div>
                            </div>
                        </div>

                        <hr class="my-4">

                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-light px-4">
                                Hủy bỏ
                            </a>
                            <button type="submit" class="btn btn-primary px-4 shadow-sm">
                                <i class="bi bi-save me-1"></i> ${isEdit ? 'Lưu cập nhật' : 'Tạo người dùng'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
