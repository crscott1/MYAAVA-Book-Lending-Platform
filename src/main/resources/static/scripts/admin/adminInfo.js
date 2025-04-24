layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

$('#modify-admin').on('click', function () {

    layer.open({
        type: 1 
        , skin: 'layui-layer-molv'
        , area: ['380px', '270px']
        , title: ['Modify Information', 'font-size:18px']
        , btn: ['Save', 'Cancel']
        , shadeClose: true
        , shade: 0 
        , content: $("#window")
        , yes: function () {
            updateAdmin();
        }
    });
});

function updateAdmin() {
    $.ajax({

        async: false,
        type: 'post',
        url: '/updateAdmin',
        data: $('#updateAdminForm').serialize(),
        success: function (data) {
            layer.alert('Modification successful!', {icon: 1}, function () {
                location.reload();
            });
        },
        error: function (data) {
            layer.alert("Modification failed!");
        }
    });
};