function ajaxCall(http, url, data, sucessCallback, errorCallback) {
    http(
        {
            method: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: data
        }).success(function (data, status, headers, config) {
        if (data.errorCode && data.errorCode >= 700) {
            console.log(data);
            if (data.data != null && data.data.rootCause != undefined && data.data.rootCause.message.includes("foreign key constraint fails")) {
                errorMsg("Operation Failed!!! Record has related row");
            } else {
                if (data.errorCode == 700) {
                    errorMsg(data.message);
                } else {
                    errorMsg("Operation Failed!!!");
                }
            }
            // alert(data.errorCode + ": " + data.message);
        } else {
            sucessCallback(data);
        }
    }).error(function (data, status, headers, config) {
        if (status == 701) {
            window.location = data;
        }
        if (errorCallback) {
            errorCallback(data);
        } else {
            errorMsg("Operation Failed!!!");
            console.log(data);
        }
    });
}

function ajaxCallNotJsonContentType(http, url, data, sucessCallback, errorCallback) {
    http(
        {
            type: 'POST',
            url: url,
            dataType: 'text',
            processData: false,
            contentType: false,
            data: data
        }).success(function (data, status, headers, config) {
        sucessCallback(data);
    }).error(function (data, status, headers, config) {
        if (errorCallback)
            errorCallback(data);
        else {
            alert(data.resposeText);
        }
    });
}

function closeModal(modalId) {
    $('#' + modalId).modal('hide');
}

function reverseDateString(date) {
    if (date.length > 0) {
        var arraydates = date.split("/");
        var newdate = arraydates[1] + "/" + arraydates[0] + "/" + arraydates[2];
        return newdate;
    }
}
