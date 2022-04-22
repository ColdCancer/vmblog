var selectdType = null;
var btn_close = "<button id='info-close' type=\"button\" class=\"mb-2 m-auto close\" aria-label=\"category-lose\">\n" +
                "  <span aria-hidden=\"true\">&times;</span>\n" +
                "</button>";

$(function () {
    requestCategoryList();

    $('#new-config').click(function () {
        var text_elem = $('#new-text');
        var text = text_elem.val();
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
                    text_elem.val('');
                    requestCategoryList();
                }
            }
        });
    });

    $('#category-table').on('click', 'tr', function () {
        // console.log($(this));
        // $('#modify-text').removeAttr("disabled");
        // $('#modify-config').removeClass("disabled");
        // $('#parent-text').removeAttr("disabled");
        // $('#parent-config').removeClass("disabled");
        // console.log(this.getElementsByTagName('input').getAttribute('value'))
        // console.log($(this).find('input').attr('value'));
        selectdType = $(this).find('input').attr('value');
        selectState();

        $('#info-close').click(function () {
            noSelectState();
        });
    })

    $('#new-cancel').click(function () {
        $('#new-text').val("");
    });

    $('#parent-config').click(function () {
        let text = $('#parent-text').val();
        // console.log(text);
        if (text === '') {
            alert_info('Input Parent Name, Please.');
            return;
        }
        $.ajax({
            url: '/web/api/category/modifyParent',
            data: {
                'sonType': selectdType,
                'parentType': text
            },
            type: 'post',
            dataType: 'json',
            success: function (content) {
                // console.log(content);
                if (content['code'] !== 0) {
                    alert_info("set [" + selectdType + "]' parent error.");
                } else {
                    alert_info("set [" + selectdType + "]' parent success.");
                    requestCategoryList();
                }
            }
        });
    });

    $('#modify-config').click(function () {
        let text = $('#modify-text').val();
        // console.log(text);
        if (text === '') {
            alert_info('Input Modify Name, Please.');
            return;
        }
        $.ajax({
            url: '/web/api/category/renameClassify',
            data: {
                'srcClassify': selectdType,
                'desClassify': text
            },
            type: 'post',
            dataType: 'json',
            success: function (content) {
                // console.log(content);
                if (content['code'] !== 0) {
                    alert_info("modify [" + selectdType + "] error.");
                } else {
                    alert_info("modify [" + selectdType + "] success.");
                    requestCategoryList();
                }
            }
        });
    });

    $('#parent-isolate').click(function () {
        $.ajax({
            url: '/web/api/category/isolateParent',
            data: { 'classify': selectdType },
            type: 'post',
            dataType: 'json',
            success: function (content) {
                // console.log(content);
                if (content['code'] !== 0) {
                    alert_info("isolate [" + selectdType + "] error.");
                } else {
                    alert_info("isolate [" + selectdType + "] success.");
                    requestCategoryList();
                }
            }
        });
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
            var len = Object.keys(data).length, son_count = 0;
            var items = [];
            for (let i = 0; i < len; i++) items[i] = [];
            for (let i = 0; i < len; i++) {
                let item = data[i];
                if (item.parentId !== null) {
                    items[item.parentId].push(i);
                    items[i] = -1;
                    son_count += 1;
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
            noSelectState();

            $('#category-total').text('Total: ' + len + ' Parent: ' +
                    (len - son_count) + ' Son: ' + son_count);

            $('.btn-category').click(function () {
                // console.log($(this).attr('value'));
                let valueName = $(this).attr('value');
                // var modify_text_elem = $('#modify-text');
                $.ajax({
                    url: '/web/api/category/removeClassify',
                    type: 'post',
                    data: {'classify': valueName},
                    dataType: 'json',
                    success: function (content) {
                        if (content['code'] !== 0) {
                            alert_info("delete [" + selectdType + "] error.");
                        } else {
                            alert_info("delete [" + selectdType + "] success.");
                            requestCategoryList();
                        }
                    }
                })
            });
        }
    });
}

function noSelectState() {
    $('#category-info').html("Select A Category Item, Please.");
    let text_elem = $('#modify-text');
    text_elem.attr("disabled", "disabled");
    text_elem.val('');
    $('#modify-config').addClass("disabled");
    text_elem = $('#parent-text');
    text_elem.attr("disabled", "disabled");
    text_elem.val('');
    $('#parent-config').addClass("disabled");
    $('#parent-isolate').addClass("disabled");
}

function selectState() {
    $('#category-info').html("To [" + selectdType + "] Category Operation" + btn_close);
    $('#modify-text').removeAttr("disabled");
    $('#modify-config').removeClass("disabled");
    $('#parent-text').removeAttr("disabled");
    $('#parent-config').removeClass("disabled");
    $('#parent-isolate').removeClass("disabled");
}