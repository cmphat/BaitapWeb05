<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hồ sơ cá nhân - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-lg-9">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-header bg-primary text-white py-3">
                    <h4 class="card-title mb-0 fw-bold">
                        <i class="bi bi-person-bounding-box me-2"></i> Thông tin hồ sơ cá nhân
                    </h4>
                </div>
                <div class="card-body p-4">

                    <c:if test="${not empty successMsg}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="bi bi-check-circle-fill me-2"></i> ${successMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form method="post" action="${pageContext.request.contextPath}/profile" enctype="multipart/form-data" class="needs-validation" novalidate id="profileForm">
                        <div class="row g-4">
                            <!-- Avatar Preview Column -->
                            <div class="col-12 col-md-4 text-center border-end">
                                <div class="mb-3">
                                    <c:choose>
                                        <c:when test="${not empty user.images}">
                                            <c:choose>
                                                <c:when test="${user.images.startsWith('http')}">
                                                    <img src="${user.images}" alt="Avatar" id="avatarPreview"
                                                         class="rounded-circle img-thumbnail shadow-sm"
                                                         style="width: 160px; height: 160px; object-fit: cover;"
                                                         onerror="this.src='https://placehold.co/160x160?text=Avatar';">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}${user.images}" alt="Avatar" id="avatarPreview"
                                                         class="rounded-circle img-thumbnail shadow-sm"
                                                         style="width: 160px; height: 160px; object-fit: cover;"
                                                         onerror="this.src='https://placehold.co/160x160?text=Avatar';">
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <div id="defaultAvatar" class="rounded-circle bg-light d-inline-flex align-items-center justify-content-center border shadow-sm"
                                                 style="width: 160px; height: 160px;">
                                                <i class="bi bi-person-fill text-secondary display-1"></i>
                                            </div>
                                            <img src="" alt="Avatar" id="avatarPreview" class="rounded-circle img-thumbnail shadow-sm d-none"
                                                 style="width: 160px; height: 160px; object-fit: cover;">
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <h6 class="fw-bold mb-1">${user.fullname != null ? user.fullname : user.username}</h6>
                                <p class="text-muted small mb-2">@${user.username}</p>

                                <div class="d-flex justify-content-center gap-1 mb-3">
                                    <span class="badge ${user.roleid == 1 ? 'bg-danger' : 'bg-secondary'}">
                                        ${user.roleid == 1 ? 'Quản trị viên' : 'Người dùng'}
                                    </span>
                                    <span class="badge bg-success">
                                        <i class="bi bi-check-circle me-1"></i> Đã kích hoạt
                                    </span>
                                </div>

                                <div class="mb-3 text-start">
                                    <label for="image" class="form-label fw-semibold small">
                                        <i class="bi bi-camera me-1"></i> Thay đổi ảnh đại diện
                                    </label>
                                    <input type="file" class="form-control form-control-sm" id="image" name="image" 
                                           accept="image/png, image/jpeg, image/webp" onchange="previewImage(this);">
                                    <div class="form-text text-muted" style="font-size: 0.78rem;">
                                        Hỗ trợ: JPG, JPEG, PNG, WEBP. Tối đa 5MB. Để trống nếu giữ ảnh hiện tại.
                                    </div>
                                </div>
                            </div>

                            <!-- User Info Form Column -->
                            <div class="col-12 col-md-8">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="username" class="form-label fw-semibold">Tên đăng nhập</label>
                                        <input type="text" class="form-control bg-light" id="username" 
                                               value="${user.username}" readonly>
                                        <div class="form-text">Tên đăng nhập không thể thay đổi.</div>
                                    </div>

                                    <div class="col-md-6">
                                        <label for="email" class="form-label fw-semibold">Địa chỉ Email</label>
                                        <input type="email" class="form-control bg-light" id="email" 
                                               value="${user.email}" readonly>
                                        <div class="form-text">Email gắn liền với kích hoạt tài khoản.</div>
                                    </div>

                                    <div class="col-12">
                                        <label for="fullname" class="form-label fw-semibold">
                                            Họ và tên <span class="text-danger">*</span>
                                        </label>
                                        <input type="text" class="form-control" id="fullname" name="fullname" 
                                               value="${user.fullname}" placeholder="Nhập họ và tên đầy đủ" required>
                                        <div class="invalid-feedback">Vui lòng nhập họ và tên.</div>
                                    </div>

                                    <div class="col-12">
                                        <label for="phone" class="form-label fw-semibold">Số điện thoại</label>
                                        <input type="tel" class="form-control" id="phone" name="phone" 
                                               pattern="0[0-9]{9,10}" value="${user.phone}" placeholder="VD: 0901234567">
                                        <div class="invalid-feedback">Số điện thoại gồm 10-11 chữ số và bắt đầu bằng 0.</div>
                                        <div class="form-text">Định dạng số điện thoại Việt Nam (10-11 số).</div>
                                    </div>

                                    <div class="col-12 mt-4 pt-2 border-top d-flex gap-2">
                                        <button type="submit" class="btn btn-primary px-4 fw-semibold">
                                            <i class="bi bi-save me-1"></i> Lưu thay đổi
                                        </button>
                                        <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
                                            <i class="bi bi-house me-1"></i> Trang chủ
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        function previewImage(input) {
            if (input.files && input.files[0]) {
                const file = input.files[0];
                if (file.size > 5 * 1024 * 1024) {
                    alert('Kích thước ảnh vượt quá giới hạn 5MB!');
                    input.value = '';
                    return;
                }
                const reader = new FileReader();
                reader.onload = function(e) {
                    const preview = document.getElementById('avatarPreview');
                    const defaultAvatar = document.getElementById('defaultAvatar');
                    preview.src = e.target.result;
                    preview.classList.remove('d-none');
                    if (defaultAvatar) {
                        defaultAvatar.classList.add('d-none');
                    }
                };
                reader.readAsDataURL(file);
            }
        }
    </script>
</body>
</html>
