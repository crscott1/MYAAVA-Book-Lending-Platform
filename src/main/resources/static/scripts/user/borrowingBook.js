layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

layui.use(['form', 'layer'], function () {
    let form = layui.form;
    let layer = layui.layer;
});

$(document).ready(function () {
    $("#btn1").click(function () {
        let bookId = $("#bookId").val().toString().trim();

        if (bookId === null || bookId === '' || isNaN(bookId)) {
            // layer.alert("Please enter the correct book ID"); // need update
            layer.alert("Please enter the correct book ID", {
                title: 'Information',
                btn: ['OK']
            });
            return false;
        }

        borrowingBook(bookId);
    });
});

//borrowing
function borrowingBook(bookId) {
    $.ajax({
        async: false,
        type: "post",
        url: "/userBorrowingBook",
        dataType: "json",
        data: {bookId: bookId},
        success: function (data) {
            console.log(data.toString());
            if (data.toString() == "true") {
                layer.msg('Borrowing books successfully!', {icon: 6, time: 2000});
            } else {
                layer.msg('Book borrowing failed!', {icon: 7, time: 2000});
            }
        },
        error: function (data) {
            layer.alert(data.result);
        }
    });
};
