layui.use(['element', 'form', 'layer'], function () {
    let layer = layui.layer;
    let element = layui.element;
    let form = layui.form;

    form.on('submit(btn1)', function (data) {

        addBook();


        return false; 
    });
});

function addBook() {

    $.ajax({
        async: false,
        type: 'post',
        url: '/addBook',
        data: $('#addBookForm').serialize(),
        success: function (data) {
            layer.msg("Added successfully", {time: 1500, icon: 1});

            setTimeout(function () {
                location.reload();
            }, 1500)
        },
        error: function (data) {
            alert("Add failed");
        }
    });
};

$(document).ready(function () {

    findAllBookCategory();

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
            $("select[name='bookCategory']").append('<option value="">--- Please Select ---</option>');
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













