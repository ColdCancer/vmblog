$(function () {
    requestCategoryList();

    $('#new-config').click(function () {
        var text = $('#new-text').val();
        // console.log($('#new-text').val());
        if (text === '') {
            alert_info("Input New Category, Please");
        }

        $.ajax({
            url: '/web/api/category/addClassify',
            type: 'post',
            data: {'classify': text},
            dataType: 'json',
            success: function (content) {
                // alert_info(content);
                if (content['code'] !== 0) {
                    alert_info("add category: " + text + " failure.");
                } else {
                    alert_info("add category: " + text + " success.");
                    requestCategoryList();
                }
            }
        });
    });

    $('#category-table').on('click', 'tr', function () {
        console.log($(this));
    })

    $('#new-cancel').click(function () {
        $('#new-text').val("");
    });

    $('#parent-cancel').click(function () {
        $('#parent-text').val("");
    });

    $('#modify-cancel').click(function () {
        $('#modify-text').val("");
    });

    $('.btn-category').click(function () {
        console.log(this.getAttribute('value'));
    })
});

function requestCategoryList() {
    $.ajax({
        url: '/web/api/category/list',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            var data = content['data'];
            var manager_body = $('#category-manager-body');
            manager_body.html('');
            var len = Object.keys(data).length;
            var items = [];
            for (let i = 0; i < len; i++) items[i] = [];
            for (let i = 0; i < len; i++) {
                let item = data[i];
                if (item.parentId !== null) {
                    items[item.parentId].push(i);
                    items[i] = -1;
                }
            }
            var id_count = 0;
            for (let i = 0; i < len; i++) {
                if (items[i] === -1) continue;
                let item = data[i];
                var category_item = new CategoryItem(++id_count,
                    item.name, item.count, 0);
                manager_body.append(category_item.convert());
                for (let j = 0; j < items[i].length; j++) {
                    item = data[items[i][j]];
                    category_item = new CategoryItem(++id_count,
                        item.name, item.count, 1);
                    manager_body.append(category_item.convert());
                }
            }
        }
    });
}