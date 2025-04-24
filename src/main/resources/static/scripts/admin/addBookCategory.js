layui.use(['form','element','layer'], function () {
    let form = layui.form;
    let element = layui.element();
    let layer = layui.layer;

    form.on('submit(btn_addBookCategory)', function (data) {
        addBookCategory();
        return false;
    });
});

$(document).ready(function () {

    let lab1 = $("#lab1").html().trim();
    let lab2 = $("#lab2").html().trim();

    $("#prePage").click(function () {
        if (lab1 == 1) {
            layer.msg("This is the last page!", {icon: 7});
            return false;
        }
        return true;
    });

    $("#nextPage").click(function () {
        if (lab1 == lab2) {
            layer.msg("This is the last page!", {icon: 7});
            return false;
        }
        return true;
    });

    $(".btn_deleteCategory").click(function () {


        let that = $(this);
        
        layer.confirm('Are you sure to delete?', {
            btn: ['Confirm', 'Cancel']
        }, function () {
            let bookCategoryId = that.val();

            deleteBookCategoryById(bookCategoryId);

            that.parent().parent().remove();
            layer.msg("Deleted successfully", {icon: 1, time: 1000});

            setTimeout(function () {

                parent.layer.closeAll();
            }, 1000)

        });

    });
});


function addBookCategory() {
    $.ajax({
        async: false,
        type: "post",
        url: "/addBookCategory",
        dataType: "json",
        data: $("#addBookCategoryForm").serialize(),
        success: function (data) {

            if (data.toString() == "true") {
                layer.msg("Added successfully!", {icon: 1, time: 1500});

                setTimeout(function () {
                    location.reload();
                }, 1500);
            } else {
                layer.msg("Add failed!", {icon: 2, time: 1500});
            }
        },
        error: function (data) {
            layer.msg("Add failed!", {icon: 2, time: 1500});
        }
    });
};


function deleteBookCategoryById(bookCategoryId) {
    $.ajax({
        async: false,
        type: "post",
        url: "/deleteCategory",
        dataType: "json",
        data: {bookCategoryId: bookCategoryId},
        success: function (data) {

        },
        error: function (data) {
            alert(data.result);
        }
    });
}

