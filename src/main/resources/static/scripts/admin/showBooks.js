layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

$(document).ready(function () {


    findAllBookCategory();

    let lab1 = $("#lab1").html().trim();
    let lab2 = $("#lab2").html().trim();


    $("#prePage").click(function () {
        if (lab1 == 1) {
            layer.msg("Already on the first page!", {icon: 7});
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
});

function findAllBookCategory() {
    $.ajax({
        async: false,
        type: "post",
        url: "/findAllBookCategory",
        dataType: "json",
        success: function (data) {
            console.log(data);

            $("select[name='bookCategory']").empty();
            $("select[name='bookCategory']").append('<option value="">——Please Select——</option>');
            for (let i = 0; i < data.length; i++) {
                let html = '<option value="' + data[i].categoryId + '">';
                html += data[i].categoryName + '</option>';
                $("select[name='bookCategory']").append(html);
            }
        },
        error: function (data) {
            alert(data.result);
        }
    });
};