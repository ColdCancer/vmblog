$(function () {
    $('#btn-submit').click(function () {
        var $account = $('#account').val();
        var $password = $('#password').val();
        if ("" === $account || "" === $password) {
            alert('请输入账号、密码！');
            // $('#login-info').html('请输入账号、密码！').css('color', 'red');
            return;
        }
        $.ajax({
            url: '/admin/checkBloggerByForm',
            data: {'account': $account, 'password': $password},
            type: 'post',
            dataType: 'text',
            success: function (data) {
                if (data === 'true') {
                    location.href = 'dashboard';
                } else {
                    alert('账号、密码错误！');
                    // $('#login-info').html('账号、密码错误！').css('color', 'red');
                }
            }
        });
    });
});