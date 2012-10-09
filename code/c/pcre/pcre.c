/*
 * 编译：
 * gcc -lpcre -o pcre pcre.c 
 */
#include <pcre.h>
#include <stdio.h>
#include <string.h>
int main (){
	char *pPattern = "([a-z]*)Windows([0-9]*)";
	const char *pError;
	int nError;
	pcre *pRe;
	pRe = pcre_compile (pPattern, 0, &pError, &nError, NULL);
	if (pRe == NULL) {
		printf("The compile is ERROR.");
		return -1;
	}
	char *pSub = "helloworldWindows2000";
	int nSubLen = strlen (pSub);
	int nRc;
	int anOvector[30];
	nRc = pcre_exec (pRe, NULL, pSub, nSubLen, 0, 0, anOvector, 30);
	if(nRc<0){
		if(nRc==PCRE_ERROR_NOMATCH){
			printf("Sorry no match.\n");
		}	
		else{
			printf("Match error is %d.\n",nRc);
		}
		free(pRe);
		return -1;
	}
	printf ("nRc = %d\n", nRc);
	int i;
	for (i = 0; i < nRc; i++)
	{
		const char *pVal = pSub + anOvector[2 * i];
		int nLen = anOvector[2 * i + 1] - anOvector[2 * i];
		char achBuff[100] = { 0 };
		memcpy (achBuff, pVal, nLen);
		printf ("achBuff: %s\n", achBuff);
	}
	free(pRe);
	return 0;
}
