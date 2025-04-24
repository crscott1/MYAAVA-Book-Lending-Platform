layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    
});


$(function () {
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