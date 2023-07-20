var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var isRight = true;

        $("#write-validate").find("input[type=text]").each(function(index, item){
        //띄어쓰기도 빈 값으로 체크함
            if ($(this).val().trim() == '') {
                alert("제목을 입력하세요.")
                isRight = false;
                return false;
            }
        });

        if (!isRight) {
            return;
        }

        $("#write-validate").find("textarea").each(function(index, item){
        //띄어쓰기도 빈 값으로 체크함
            if ($(this).val().trim() == '') {
                alert("내용을 입력하세요.")
                isRight = false;
                return false;
            }
        });

        if (!isRight) {
            return;
        }

        $("#write-validate").find("input[type=number]").each(function(index, item){
            //띄어쓰기도 빈 값으로 체크함
            if ($(this).val().trim() == '') {
                alert("분량을 입력하세요.")
                isRight = false;
                return false;
            }
        });

        if (!isRight) {
            return;
        }

        $(this).prop("disabled", true);
        $(this).prop("disabled", false);

        var data = {
            title: $('#title').val(),
            description: $('#description').val(),
            runningTime: $('#runningTime').val(),
            instructorId: $('#instructorId').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/lecture/save',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('강의가 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var isRight = true;

        $("#update-validate").find("input[type=text]").each(function(index, item){
        //띄어쓰기도 빈 값으로 체크함
            if ($(this).val().trim() == '') {
                alert("제목을 입력하세요.")
                isRight = false;
                return false;
            }
        });

        if (!isRight) {
            return;
        }

        $("#update-validate").find("textarea").each(function(index, item){
        //띄어쓰기도 빈 값으로 체크함
            if ($(this).val().trim() == '') {
                alert("내용을 입력하세요.")
                isRight = false;
                return false;
            }
        });

        if (!isRight) {
            return;
        }

        $("#update-validate").find("input[type=number]").each(function(index, item){
            //띄어쓰기도 빈 값으로 체크함
            if ($(this).val().trim() == '') {
                alert("분량을 입력하세요.")
                isRight = false;
                return false;
            }
        });

        if (!isRight) {
            return;
        }

        $(this).prop("disabled", true);
        $(this).prop("disabled", false);

        var data = {
            title: $('#title').val(),
            description: $('#description').val(),
            runningTime: $('#runningTime').val(),
            instructorId: $('#instructorId').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/lecture/detail/update/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('강의가 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/lecture/detail/delete/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('강의가 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();