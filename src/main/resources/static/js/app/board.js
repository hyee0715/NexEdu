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

        $(this).prop("disabled", true);
        $(this).prop("disabled", false);

        var data = {
            title: $('#title').val(),
            content: $('#content').val(),
            writerId: $('#writerId').val(),
            lectureId : $('#lectureId').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/board/save/' + data.lectureId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/board/list/' + data.lectureId;
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

        $(this).prop("disabled", true);
        $(this).prop("disabled", false);

        var data = {
            title: $('#title').val(),
            writerId: $('#writerId').val(),
            content: $('#content').val()
        };

        var id = $('#boardId').val();

        $.ajax({
            type: 'PUT',
            url: '/api/board/detail/update/' + id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/board/detail/' + id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var boardId = $('#boardId').val();
        var lectureId = $('#lectureId').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/board/detail/delete/' + boardId,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/board/list/' + lectureId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();