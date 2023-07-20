var main = {
    init : function () {
        var _this = this;
        $('#btn-withdrawal').on('click', function () {
            _this.withdraw();
        });
    },
    withdraw : function () {
        var userId = $('#userId').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/user/detail/'+userId,
        }).done(function() {
            alert('탈퇴가 완료되었습니다.');
            window.location.href = '/logout';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();