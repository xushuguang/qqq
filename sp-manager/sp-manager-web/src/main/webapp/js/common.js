
var snmp = {
    onTreeClick:function () {
        //约定大于配置：定义DOM对象的时候，一般定义为tree
        //定义的是一个jquery对象的话，一般定义为$tree
        var $tree = $('#menu .easyui-tree');
        // console.log(this);
        var _this = this;
        $tree.tree({
            onClick: function (node) {
                if ($('#tab').tabs('exists', node.text)) {
                    //能进入这里说明该选项卡存在
                    $('#tab').tabs('select', node.text);
                } else {
                    // console.log(this);
                    _this.addTabs(node.text, node.attributes.href);
                }
            }
        });
    },
    addTabs:function (text,href) {
        //新增选项卡
        $('#tab').tabs('add', {
            title: text,
            href: href,
            closable: true
        });
    },
    closeTabs:function (text) {
        $('#tab').tabs('close',text);
    }
};