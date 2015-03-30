/**
* 依赖jQuery
*/
function Pagination(obj) {
    this.id = obj.id;  						//div id
    this.url = obj.url; 					//服务端REST地址
    this.pageSize = obj.pageSize;  			//每页显示元素条数
    this.pageNum = 1; 						//当前页码从1开始
    this.total = 0; 						//合计条数
    this.totalPage = 0;                     //最大页码
    this.barSize = obj.barSize; 			//分页工具条上展现的页码数    
    this.data = obj.data;                   //服务端响应数据
    this.success = obj.success;             //scuccess：回调函数
    this.error = obj.error;                 //error：回调函数
    this.div = null;						//page tool bar的html
    this.init();
}
Pagination.prototype.init = function () {
    if (this.data == undefined) {
        this.data = {}
    }    
    this.div = $('#' + this.id);
    this.fetchData(1);
};
 
Pagination.prototype.fetchData = function (pageNum) {
    this.pageNum = pageNum;
    this.pageSize = this.pageSize;
    
    var that = this;
    $.ajax({
        url: that.url,
        data: that.data,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if(data.total==undefined) {
                that.success(data.rows);
                return;
            }
            that.total = data.total;
            var tmp = that.total % that.pageSize;
            var num = Math.floor(that.total / that.pageSize);
            that.totalPage = tmp == 0 ? num : num + 1;
            that.showUI();
            that.success(data.rows);
        },
        error: function (msg) {
            that.error(msg);
        }
    })
};
Pagination.prototype.showUI = function () {	
    var that = this;
    this.div.empty();
    var bar = this.barTuple();    
    //previous 
    if(this.pageNum <= 1) this.div.append('<span class="disabled icon item"><i class="left arrow icon"></i> 上一页</span>');
    else this.div.append('<a class="icon item"><i class="left arrow icon"></i> 上一页</a>');  
    	
    //bar    
    for (var i = bar[0]; i <= bar[1]; i++) {    
    	if(i == this.pageNum) this.div.append('<span class="active item">' + i + '</span>');
    	else this.div.append('<a class="item">' + i + '</a>');
    }

    that.div.find('a').each(function(i,d){    	
    	if(i == 0 && that.pageNum > 1){
    		$(d).click({param:that},that.previewPage);
    	}  	    	
    	else {
	    	$(d).click({param: that}, function (event) {
	        	var p = event.data.param;
	            var n = $(this).text().trim();                                
	            p.fetchData(parseInt(n));
	        });
        }
    });    
    //next    
    if(this.pageNum >= this.totalPage) this.div.append('<span class="disabled icon item"> 下一页 <i class="icon right arrow"></i></span>');
  	else {
   		this.div.append('<a class="icon item"> 下一页 <i class="icon right arrow"></i></a>');
   		this.div.find('a:last').click({param:that},that.nextPage);
   	} 
};
Pagination.prototype.barTuple = function() {
	var pageFix = Math.ceil(this.barSize / 2);
	var min = (this.pageNum - pageFix <= 1) ? 1 : this.pageNum - pageFix;
	var max= (this.pageNum + pageFix > this.totalPage) ? this.totalPage : this.pageNum + pageFix;
	return [min,max];
};
Pagination.prototype.nextPage = function (event) {
    var that = event.data.param;
    if(that.pageNum < that.totalPage) that.pageNum++;        
    that.showUI();    
};
Pagination.prototype.previewPage = function (event) {
    var that = event.data.param;
    if(that.pageNum > 1) that.pageNum--;        
    that.showUI();   
};