var editor;
var update_flag = false, update_img = false;
var update_account, update_link;

$(function () {
    // getDefaultTime($('#article-data'));

    editor = editormd("article-editormd", {
        height              : 580,
        watch               : true,                // 关闭实时预览
        syncScrolling       : "single",
        // htmlDecode          : true,
        emoji               : true,
        flowChart           : true,
        tex                 : true,         // 开启科学公式TeX语言支持，默认关闭
        saveHTMLToTextarea  : true,
        // tocm                : true,         // Using [TOCM]
        // sequenceDiagram     : true,
        // theme               : "dark",
        // previewTheme        : "dark",
        // editorTheme         : "pastel-on-dark",
        // dialogLockScreen    : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
        //dialogShowMask      : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
        //dialogDraggable     : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
        //dialogMaskOpacity   : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
        //dialogMaskBgColor   : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
        imageUpload         : true,
        imageFormats        : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL      : "/api/mediae/images/upload",
        path                : "/editor/lib/"
    });

    $('#btn-post').click(function () {
        var title = $('#article-title').val();
        if (title === '') {
            alert_info("Input Article Title, Please");
        } else {
            if (update_flag) {
                requestUpdateArticle();
            } else {
                requestAddArticle();
            }
        }
    });

    $('#btn-save').click(function () {
        if (update_flag) {
            requestUpdateBackupArticle();
        } else {
            requestAddBackupArticle();
        }
    });

    $('#btn-edit').click(function () {
        changeToEdit();
    });

    $('#btn-exit').click(function () {
        location.href = '/web/dashboard/article';
    });

    $('#article-cover').change(function () {
        update_img = true; // no update img
        // $('#article-cover')[0].files[0].name
        var img_elem = $('#article-cover');
        var div_elem = $(img_elem[0].parentElement);
        var label_elem = $(div_elem[0].getElementsByTagName('label'));
        label_elem.text(img_elem[0].files[0].name);
    });

    requestCategoryList();

    var path_items = window.location.pathname.split("/");
    // console.log(path_items);
    if (path_items[4] === "edit" && path_items.length > 5) {
        requestEditArticle(path_items[5]);
    }
});

function requestCategoryList() {
    $.ajax({
        url: '/web/api/category/list',
        type: 'get',
        dataType: 'json',
        async: false,
        success: function (content) {
            console.log(content);
            if (content['code'] !== 0) return;
            var data = content['data'];
            var len = Object.keys(data).length;
            var category_elem = $('#article-category');
            for (let i = 0; i < len; i++) {
                let item = data[i];
                category_elem.append(
                    $("<option value='" + item.name + "'>" + item.name + "</option>"));
            }
        }
    });
}

function requestUpdateBackupArticle() {
    var article = editor.getMarkdown();
    var blob_ojb = new Blob([article], {"type" : "text/html;charset=utf-8"});
    var formData = new FormData();
    var nowData = getDefaultTime();
    var category = $('#article-category').val();
    category = (category === 'Choose...' ? null : category);

    formData.append("article", blob_ojb);
    formData.append("title", $('#article-title').val());
    // formData.append("account", update_account);
    formData.append("link", update_link);
    formData.append("postDate", nowData);
    formData.append("category", category);
    formData.append("top", $('#article-rank').val());
    $.ajax({
        url: '/web/api/article/updateBackupArticle',
        data: formData,
        type: 'post',
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (content) {
            alert_info(content['message']);
            if (content['code'] !== 0) return;
            var data = content['data'];
            if (update_img) {
                checkAndSendCover(data['account'], data['article-link']);
            }
            changeToNotEdit();
            updateStatus(data);
        }
    });
}

function requestAddBackupArticle() {
    var article = editor.getMarkdown();
    var blob_ojb = new Blob([article], {"type" : "text/html;charset=utf-8"});
    var formData = new FormData();
    var nowData = getDefaultTime();
    var category = $('#article-category').val();
    category = (category === 'Choose...' ? null : category);

    formData.append("article", blob_ojb);
    formData.append("title", $('#article-title').val());
    formData.append("postDate", nowData);
    formData.append("category", category);
    formData.append("top", $('#article-rank').val());
    $.ajax({
        url: '/web/api/article/addBackupArticle',
        data: formData,
        type: 'post',
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (content) {
            alert_info(content['message']);
            if (content['code'] !== 0) return;
            var data = content['data'];
            checkAndSendCover(data['account'], data['article-link']);
            changeToNotEdit();
            updateStatus(data);
        }
    });
}

function loadCategory(categories) {
    if (categories.length === 0) return;
    // console.log(categories);
    var category_elem = $('#article-category');
    category_elem.val("<option selected>Choose...</option>");
    for (var index in categories) {
        var category = categories[index];
        category_elem.append(new SelectCategory(-1, category).convert());
    }
}

function requestEditArticle(link) {
    // var article = editor.getHTML();
    var account = null;
    $.ajax({
        url: '/web/api/article/editInfo',
        data: {'link': link},
        type: 'get',
        async: false,
        dataType: 'json',
        success: function (content) {
            alert_info(content['message']);
            if (content['code'] !== 0) return;
            var data = content['data'];
            // console.log(data);
            changeToEdit();
            account = data['account'];
            updateStatus(data);
            loadEditState(data);
            // console.log(data['category']);
            // loadCategory(data['category']);
        }
    });

    $.ajax({
        url: '/web/api/article/md/content',
        data: {'link': link, 'account': account},
        type: 'get',
        dataType: 'text',
        success: function (content) {
            // alert_info(content['message']);
            // if (content['code'] !== 0) return;
            // var data = content['data'];
            // console.log(content);
            setTimeout(function(){
                editor.setValue(content);
            }, 500);
        }
    });
}

function loadEditState(data) {
    $('#article-title').val(data['title']);
    $('#article-rank').attr('value', data['top-rank']);
    $('#article-category').val(data['category']);
}

function requestUpdateArticle() {
    var article = editor.getMarkdown();
    var blob_ojb = new Blob([article], {"type" : "text/html;charset=utf-8"});
    var formData = new FormData();
    var nowData = getDefaultTime();
    var category = $('#article-category').val();
    category = (category === 'Choose...' ? null : category);

    formData.append("article", blob_ojb);
    formData.append("title", $('#article-title').val());
    // formData.append("account", update_account);
    formData.append("link", update_link);
    formData.append("postDate", nowData);
    formData.append('category', category);
    formData.append("top", $('#article-rank').val());
    $.ajax({
        url: '/web/api/article/updateArticle',
        data: formData,
        type: 'post',
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (content) {
            alert_info(content['message']);
            if (content['code'] !== 0) return;
            var data = content['data'];
            if (update_img) {
                checkAndSendCover(data['account'], data['article-link']);
            }
            changeToNotEdit();
            updateStatus(data);
        }
    });
}

function requestAddArticle() {
    var article = editor.getMarkdown();
    var blob_ojb = new Blob([article], {"type" : "text/html;charset=utf-8"});
    var formData = new FormData();
    var category = $('#article-category').val();
    category = (category === 'Choose...' ? null : category);
    var nowData = getDefaultTime();
    formData.append("article", blob_ojb);
    formData.append("title", $('#article-title').val());
    formData.append("postDate", nowData);
    formData.append("category", category);
    formData.append("top", $('#article-rank').val());
    $.ajax({
        url: '/web/api/article/addArticle',
        data: formData,
        type: 'post',
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (content) {
            alert_info(content['message']);
            if (content['code'] !== 0) return;
            var data = content['data'];
            checkAndSendCover(data['account'], data['article-link']);
            changeToNotEdit();
            updateStatus(data);
        }
    });
}

function updateStatus(data) {
    $('#last-post-date').html("Last Update: " + data['update-date']);
    update_account = data['account'];
    update_link = data['article-link'];
    update_flag = true;
    // console.log(update_account);
    // console.log(update_link);
    $('#btn-post').text('Update');
    // $('#btn-save').attr('disabled', false);
    // $('#btn-edit').removeClass('d-none');
    // $('#btn-post').addClass('d-none');
    // $('#article-title').attr('disabled', false);
    // $('#article-cover').attr('disabled', false);
    // $('#article-category').attr('disabled', false);
    // // editor.config({ readOnly : false });
    // $('.editormd-preview-close-btn').click();
}

function checkAndSendCover(account, link) {
    var file = $('#article-cover')[0].files[0];
    // console.log(file);
    if (file) {
        var formData = new FormData();
        formData.append("cover", file);
        formData.append('account', account);
        formData.append('link', link);

        $.ajax({
            url: '/web/api/article/addCover',
            type: 'post',
            processData: false,
            contentType: false,
            data: formData,
            dataType: 'json',
            success: function (content) {
                // alert_info(content['message']);
            }
        });
    }
}

function getDefaultTime() {
    var myDate = new Date((new Date).getTime() + 8 * 60 * 60 * 1000);
    return myDate.toJSON().substr(0, 19).replace('T', ' ');
}

function changeToNotEdit() {
    $('#btn-save').attr('disabled', true);
    $('#btn-edit').removeClass('d-none');
    $('#btn-post').addClass('d-none');
    $('#article-title').attr('disabled', true);
    $('#article-cover').attr('disabled', true);
    $('#article-category').attr('disabled', true);
    $('#article-rank').attr('disabled', true);
    editor.unwatch();
    editor.previewing();
    $('.editormd-preview-close-btn').addClass('d-none');
    // editor.config({ readOnly : true });
    // $('#article-content').attr('disabled', false);
}

function changeToEdit() {
    $('#btn-save').attr('disabled', false);
    $('#btn-edit').addClass('d-none');
    $('#btn-post').removeClass('d-none');
    $('#article-title').attr('disabled', false);
    $('#article-cover').attr('disabled', false);
    $('#article-category').attr('disabled', false);
    $('#article-rank').attr('disabled', false);
    update_img = false;
    // editor.config({ readOnly : false });
    $('.editormd-preview-close-btn').click();
    try {
        editor.watch();
    } catch (e) {
        console.log("editor init ...");
    }
}

function alert_info(message) {
    $('#alert-content').html(message);
    $('#alert-toast').toast('show');
}