--变量定义
width=1;
height=23;
--lua函数定义，实现加法
function lsum(a,b)
	return a+b;
end
--lua函数定义，实现字符串相加
function lstrcat(a,b)
	return a..b
end
--lua函数定义，调用c代码中的csum函数实现加法
function mysum(a,b)
	return csum(a,b)
end

