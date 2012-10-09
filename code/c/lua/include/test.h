#ifndef T_DEMO_H
#define T_DEMO_H
#include <stdio.h>
#include <string.h>
#include <openssl/md5.h>
extern "C" {
#include "lua.h"  
#include "lualib.h" 
#include "lauxlib.h"
void md5();
}
int test(lua_State* l);
int csum(lua_State* l);
#endif
