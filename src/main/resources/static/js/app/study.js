var main = {
    init : function () {
        var _this = this;
        $('#btn-study-lecture').on('click', function () {
            _this.save();
        });
    },
    save : function () {
        var data = {
            lectureId: $('#requestLectureId').val(),
            userId: $('#requestUserId').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/studies',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('수강 신청이 완료되었습니다.');
            window.location.href = '/lectures/detail/' + data.lectureId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();