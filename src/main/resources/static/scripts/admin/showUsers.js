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


    $(".del_btn").click(function () {

        let that = $(this);

        layer.confirm('Are you sure to delete?', {
            btn: ['Confirm', 'Cancel'] 
        }, function () {
            let userId = that.val();

            deleteUserById(userId);

            that.parent().parent().remove();
            layer.msg("Deleted successfully!", {icon: 1, time: 1000});

            setTimeout(function () {


                parent.layer.closeAll();
            }, 1000)

        });

    });

});


function deleteUserById(userId) {
    $.ajax({
        async: false,
        type: "post",
        url: "/deleteUser",
        dataType: "json",
        data: {userId: userId},
        success: function (data) {

        },
        error: function (data) {
            alert(data.result);
        }
    });
}