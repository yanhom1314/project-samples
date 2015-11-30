#include <lua.h>
#include <lauxlib.h>

#include <stdlib.h> /*for fucntion exit()*/
#include <stdio.h>  /*for input/output **/

void bail(lua_State *L,char *msg) {
  fprintf(stderr, "\nFATAL ERROR:\n %s: %s\n\n",
    msg,lua_tostring(L,-1));
    exit(1);
}

int main(int argc,const char *argv[]) {
  if(argc !=2 ) {
    return 1;
  }
  lua_State *L = luaL_newstate();     /* Create new lua state variable */
  /* Load Lua libraries, otherwise, the lua function in *.lua will be nil */
  luaL_openlibs(L);
  if( luaL_loadfile(L,argv[1]))     /* only load the lua script file */
    bail(L,"luaL_loadfile() failed.");

  if(lua_pcall(L,0,0,0))            /* run the loaded lua script file */
    bail(L,"lua_pcall() failed");
  lua_close(L);                     /* close the lua state variable */
  return 0;
}
