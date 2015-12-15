#include <iostream>
#include "test.h"
using namespace std;
int main(char* args[]){
	char* m="Hello World";
	char strs[]="Hello";
	const char* p=strs;
	cout<<p<<endl;
	cout<<&p<<endl;
	p=m;
	cout<<p<<endl;
	cout<<&p<<endl;
	/*lua test*/
	lua_State* l=lua_open();
	//lua_State* l=luaL_newstate();
	test(l);
	if(l==NULL){
		printf("ERROR:lua is NULL.\n");
		return -2;
	}
	/*获取变量值*/	
	int ret=luaL_loadfile(l,"/tmp/func.lua");
	ret = lua_pcall(l,0,0,0) ;
	lua_getglobal(l,"width");
	lua_getglobal(l,"height");
	printf("height:%d width:%d\n",lua_tointeger(l,-1),lua_tointeger(l,-2));
	lua_pop(l,1);	
	
	/*调用函数*/
	lua_getglobal(l,"lsum");
	lua_pushinteger(l,23);
	lua_pushinteger(l,34);
	ret=lua_pcall(l,2,1,0);
	printf("result:%d\n",lua_tointeger(l,-1));
	lua_pop(l,1);

	/*lua调用C/C++函数*/
	lua_pushcfunction(l,csum);
	lua_setglobal(l,"csum");
	
	lua_getglobal(l,"mysum");
	lua_pushinteger(l,23);
	lua_pushinteger(l,43);
	lua_pcall(l,2,1,0);
	printf("result:%d\n",lua_tointeger(l,-1));
	lua_pop(l,1);

	lua_close(l);
	/*md5 test*/
	md5();
	return 0;
}
