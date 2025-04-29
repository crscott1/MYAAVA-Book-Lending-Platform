layui.use(['form', 'element', 'layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    let $ = layui.jquery;

    // 监听表单提交
    form.on('submit(btn_addBookCategory)', function(data){
        console.log("Click Add Category button，submit data：", data.field);
        addBookCategory(data.field);
        return false;
    });

    // 添加分类的ajax
    function addBookCategory(formData) {
        $.ajax({
            type: "POST",
            url: "/addBookCategory",
            data: formData,
            dataType: "text", // 后端返回 "true" / "false"
            success: function(response){
                console.log("server return：", response);
                if (response === "true") {
                    layer.msg("Added successfully!", {icon: 1, time: 1500});
                    setTimeout(function(){
                        location.reload();
                    }, 1500);
                } else {
                    layer.msg("Add failed!", {icon: 2, time: 1500});
                }
            },
            error: function(xhr){
                console.error("Add failed：", xhr.responseText);
                layer.msg("Server error!", {icon: 2});
            }
        });
    }

    // 监听上一页、下一页
    let lab1 = $("#lab1").html()?.trim();
    let lab2 = $("#lab2").html()?.trim();

    $("#prePage").click(function () {
        if (lab1 == 1) {
            layer.msg("This is the first page!", {icon: 7});
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

    // 绑定删除按钮
    $(document).on('click', '.btn_deleteCategory', function() {
        let that = $(this);

        layer.confirm('Are you sure to delete?', {
            btn: ['Confirm', 'Cancel']
        }, function () {
            let bookCategoryId = that.val();

            console.log("Ready to delete category,ID：", bookCategoryId);

            deleteBookCategoryById(bookCategoryId, that);
        });
    });

    // 删除分类的ajax
    function deleteBookCategoryById(bookCategoryId, buttonElement) {
        $.ajax({
            type: "POST",
            url: "/deleteCategory",
            data: {bookCategoryId: bookCategoryId},
            dataType: "text",
            success: function (response) {
                console.log("delete return:", response);
                if (response === "true") {
                    layer.msg("Deleted successfully!", {icon: 1, time: 1000});
                    // 删除该行
                    buttonElement.closest("tr").remove();
                    setTimeout(function () {
                        parent.layer.closeAll();
                    }, 1000);
                } else {
                    layer.msg("Delete failed!", {icon: 2, time: 1000});
                }
            },
            error: function (xhr) {
                console.error("Delete failed!：", xhr.responseText);
                layer.msg("Server error!", {icon: 2, time: 1000});
            }
        });
    }
});
