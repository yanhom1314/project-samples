/*
@name: jQuery Plugin Template for Coding
*/
;(function($) {//[--jQuery Plugin Container

//declare the plugin's version ; use to check the plugin exists
$.__plugin_name__ = $.__plugin_name__ || {version:'0.1.0'};

//[--Plugin Define
var __plugin_name__ = function(node,opts) {

    var me=this,$me=$(this);
    var $mine=$(node); //get the plugin's Operation jQuery DOM Element

    //Public Methods
    $.extend(me, {
        show: function() {
            __show__();
        },
        hide: function() {
            __hide__();
        },
        options: function() {
            //return the preset options to users code
            //let users can be change options by later code
            return opts;
        }
    });

    //Private Variables ( Module Level )
    var m_var1, m_var2, m_var3;

    //init the plugin
    function __init__(){
        alert('jQuery Plugin init');
        if (opts.autoShow) __show__();
    }
    __init__();

    //Private Functions
    function __show__(){
        //function code
        $mine.text('Hello jQuery Plugin !');
        //call the event
        opts.onShow(me,opts);
    }

    function __hide__(){
        //function code
        $mine.text('');
        //call the event
        opts.onHide(me,opts);
    }


};//--]Plugin Define


//jQuery Plugin Implementation
$.fn.__plugin_name__ = function(conf) {

    //return existing instance // let users can use the Public Methods
    //Usage: var obj = $('#id').__plugin_name__({ <options> }).data("__plugin_name__");
    var el = this.eq(typeof conf == 'number' ? conf : 0).data("__plugin_name__");
    if (el) { return el; }

    //setup default options
    var opts = {
        autoShow:true,
        onShow:function(e,o){},
        onHide:function(e,o){},
        api:false
    };

    //if no users options then use the default options
    $.extend(opts, conf);

    // install the plugin for each items in jQuery
    this.each(function() {
        el = new __plugin_name__(this, opts);
        $(this).data("__plugin_name__", el);
    });

    //api=true let users can immediate use the Public Methods
    return opts.api ? el: this;

};


})(jQuery);//--]jQuery Plugin Container