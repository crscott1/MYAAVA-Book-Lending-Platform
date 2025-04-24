layui.use(['form', 'element', 'layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;

    form.on('submit(btn_addUser)', function (data) {
        addUser();
        return false; 
    });
});



function addUser() {
    $.ajax({
        async: false,
        type: 'post',
        url: '/addUser',
        data: $('#addUserForm').serialize(),
        success: function (data) {
            layer.msg("Added successfully!", {icon: 1, time: 1500});

            setTimeout(function () {
                location.reload();
            }, 1500)
        },
        error: function (data) {
            layer.msg("Add failed!", {icon: 2});
        }
    });
};