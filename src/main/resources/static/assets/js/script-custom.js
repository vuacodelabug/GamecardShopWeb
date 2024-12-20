$(document).ready(function () {
  $(".clear-search").click(function () {
    $.ajax({
      url: "/admin/clear-search",
      method: "POST",
      headers: {
        "X-CSRF-TOKEN": $('meta[name="csrf-token"]').attr("content"),
      },
      success: function (response) {
        window.location.reload();
      },
    });
  });

  // $('.select2').select2({});

  // bắt lỗi email
  // Kiểm tra khi người dùng nhập hoặc rời khỏi trường nhập
  $("#email").on("change", function () {
    var emailInput = $(this).val().trim();

    var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(emailInput)) {
      $(this).addClass("is-invalid");
      isValid = false;
    } else {
      $(this).removeClass("is-invalid");
    }
  });
  // bắt lỗi số điện thoại
  $("#phone").on("change", function () {
    var phoneInput = $(this).val().trim();

    // Biểu thức chính quy cho số điện thoại (ví dụ, dạng 09xxx xxx xxx hoặc 012 xxx xxx)
    var phonePattern = /^(0[3|5|7|8|9])+([0-9]{8})$/;

    if (!phonePattern.test(phoneInput)) {
      $(this).addClass("is-invalid");
      isValid = false;
    } else {
      $(this).removeClass("is-invalid");
    }
  });

  // Loại bỏ khoảng trắng thừa và viết hoa chữ đầu mỗi từ
  $("#name").on("change", function () {
    var nameInput = $(this).val().trim();

    nameInput = nameInput
      .replace(/\s+/g, " ")
      .replace(/(?:^|\s)\S/g, function (match) {
        return match.toUpperCase();
      });

    $(this).val(nameInput);
  });

  // Mở modal thong báo
  var messager = $("#messager-hiden").text();
  if (messager !== "") {
    $("#modalNotify").modal("show");
  }
  // Áp dụng hàm formatNumber khi giá trị thay đổi
  $(".format-number").on("input", function () {
    $(this).formatNumber();
  });
  // Áp dụng ngay khi load trang (nếu có giá trị trong input)
  $(".format-number").formatNumber();
});
// Thông báo bằng alet setimeout 1s
// alert1s('Không thể cập nhật nhà phát hành!');
function alert1s(message) {
  setTimeout(() => {
    alert(message);
  }, 700);
}

// Định nghĩa hàm format-number
$.fn.formatNumber = function () {
  return this.each(function () {
    var value = $(this).val();
    if ($.isNumeric(value)) {
      var formattedValue = value.replace(/\B(?=(\d{3})+(?!\d))/g, " ");
      $(this).val(formattedValue);
    }
  });
};
