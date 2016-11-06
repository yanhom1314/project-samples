#include <string.h>
void
test ()
{
  int in = 0;
  char buffer[] = "Fred,John,Ann";
  char *p[3];
  char *buf = buffer;
  while ((p[in] = strtok (buf, ",")) != NULL)
    {
      in++;
      buf = NULL;
    }
}

void
lyf_t ()
{
  char src[] = "Hello world Fuck";
  char *token,*sp;
  char *buf = src;
  while ((token = strtok_r(buf, " ",&sp)) != NULL)
    {
      buf = NULL;
      printf ("[%s]\n", token);
    }
}
int
main ()
{
  test ();
  char line[12];
  memset (line, '\0', 12);
  memset (line, '*', 11);
  printf("[%s]\n",line);
  lyf_t ();
}
