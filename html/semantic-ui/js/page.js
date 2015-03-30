function Pagination(obj) {
    this.id = obj.id;  //div id
    this.url = obj.url; 
    this.pageSize = obj.pageSize; 
    this.pageNum = 1; //current page number
    this.total = 0; //total count
    this.totalPage = 0; 
    this.barSize = obj.barSize; //分页工具条上展现的页码数    
    this.data = obj.data;
    this.success = obj.success;
    this.error = obj.error;
    this.div = null;
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
    var bar = this.barArray();
    console.log("bar[0]:"+bar[0] + " bar[1]"+bar[1]);
    //previous 
    if(this.pageNum <= 1) this.div.append('<span class="disabled icon item"><i class="left arrow icon"></i> 上一页</span>');
    else this.div.append('<a class="icon item"><i class="left arrow icon"></i> 上一页</a>');
    //bar    
    for (var i = bar[0]; i <= bar[1]; i++) {    
    	if(i == this.pageNum) this.div.append('<a class="active item">' + i + '</a>');
    	else this.div.append('<a class="item">' + i + '</a>');
    }
    //next    
    if(this.pageNum < this.totalPage) this.div.append('<a class="icon item"> 下一页 <i class="icon right arrow"></i></a>');
    else this.div.append('<span class="disabled icon item"> 下一页 <i class="icon right arrow"></i></span>');
 
    var array = this.div.find('a');
    for (var j = 0; j < array.length; j++) {
        var current = $(array[j]);
        if (j == 0) {
            current.click({param: that}, that.previewPage);
        } else if (j == array.length - 1) {
            current.click({param: that}, that.nextPage)
        } else {
            current.click({param: that}, function (event) {
                var p = event.data.param;
                var n = $(this).text().trim();                                
                p.fetchData(parseInt(n));
            })
        }
    }        
};
Pagination.prototype.barArray = function() {
	var pageFix = Math.ceil(this.barSize / 2);
	var min = (this.pageNum - pageFix <= 1) ? 1 : this.pageNum - pageFix;
	var max= (this.pageNum + pageFix > this.totalPage) ? this.totalPage : this.pageNum + pageFix;
	return [min,max];
};
Pagination.prototype.nextPage = function (event) {
    var that = event.data.param;
    that.pageNum++;        
    that.showUI();    
};
Pagination.prototype.previewPage = function (event) {
    var that = event.data.param;
    that.pageNum--;        
    that.showUI();   
};