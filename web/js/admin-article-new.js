$(function () {
    $('#article-time').val(new Date().toISOString().split('.')[0]);
});
// 2021-04-09T11:08
$('#btn-submit').click(function () {
    // alert($('#article-title').val());
    var $title = $('#article-title').val();
    if ($title === "") {
        alert("Title");
    }
    alert($('#article-time').val());

    // var $time = $('#article-time').val();
    // $.ajax({
    //     url: '/admin/article/new/postArticle',
    //     data: {'title': $title, 'time': $title}
    // })
});
