layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

$('#modify-user').on('click', function () {

   
    layer.open({
        type: 1 //Page
        , skin: 'layui-layer-molv'
        , area: ['380px', '270px']
        , title: ['Modify Information', 'font-size:18px']
        , btn: ['Save', 'Cancel']
        , shadeClose: true
        , shade: 0 
        , content: $("#window")
        , yes: function () {
            updateUser();
        }
    });
});

function updateUser() {
    $.ajax({

        async: false,
        type: 'post',
        url: '/updateUser',
        data: $('#updateUserForm').serialize(),
        success: function (data) {
            layer.alert('Modification Success', {icon: 1}, function () {
                location.reload();
            });
        },
        error: function (data) {
            layer.alert("Modification failed");
        }
    });
};