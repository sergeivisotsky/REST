$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/rest/v1/customers/1/orders/7",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        type: 'GET',
        contentType: "application/json",
    }).then(function (data) {
        $('.order-id').append(data.orderId);
        $('.customer-id').append(data.customerId);
        $('.trans-id').append(data.transId);
        $('.good').append(data.good);
        $('.good-weight').append(data.goodWeight);
        $('.price').append(data.price);
    });
});