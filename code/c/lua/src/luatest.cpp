#include "test.h"

/* Lua解释器指针 */
int test(lua_State* l){
	/* 载入Lua基本库 */  
	luaL_openlibs(l);  
	/* 运行脚本 */  
	luaL_dofile(l, "/tmp/test.lua"); 
	/* 暂停 */   
	//printf( "Press enter to exit…" ); 
	//getchar(); 
	printf("End.");
	return 0;
}
int csum(lua_State* l){
	int a=lua_tointeger(l,1);
	int b=lua_tointeger(l,2);
	lua_pushinteger(l,a+b);
	return 1;
}
