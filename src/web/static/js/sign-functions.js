$(function () {
    $('#btn-sign-up').click(function () {
        var $account = $('#SignUpAccount').val();
        var $password = $('#SignUpPassword').val();
        var $repassword = $('#SignUpRePassword').val();

        var pattern = /^\w{6,31}$/;
        if (!pattern.test($account)) {
            alert_info("Please enter the correct account.");
            return null;
        }
        if (!pattern.test($password)) {
            alert_info("Please enter the correct password.");
            return null;
        }
        if (!pattern.test($repassword)) {
            alert_info("Please enter the correct re-password.");
            return null;
        }
        if ($password !== $repassword) {
            alert_info("The second password is inconsistent.");
            return null;
        }

        $.ajax({
            url: '/web/passport/signup',
            data: {'account': $account, 'password': $password},
            type: 'post',
            dataType: 'text',
            success: function (data) {
                data = JSON.parse(data);
                alert_info(data["message"]);
            }
        });
    });
});

function alert_info(message) {
    $('#alert-content').html(message);
    $('#alert-toast').toast('show');
}