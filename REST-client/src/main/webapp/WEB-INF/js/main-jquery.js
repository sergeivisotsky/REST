$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/rest/api/v1/customers/1",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        type: 'GET',
        contentType: "application/json",
    }).then(function (data) {
        $('.customer-id').append(data.customerId);
        $('.first-name').append(data.firstName);
        $('.last-name').append(data.lastName);
        $('.age').append(data.age);
    });
});