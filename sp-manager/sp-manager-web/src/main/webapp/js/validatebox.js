//自定义验证框验证规则
$.extend($.fn.validatebox.defaults.rules, {
    IP: {
        validator: function (value) {
            var reg =/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
            return reg.test(value);
        },
        message: '请输入正确的ip地址'
    },
});