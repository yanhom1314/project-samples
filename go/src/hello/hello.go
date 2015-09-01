package main

import (
	"bufio"
	"fmt"
	"io"
	"mathii"
	"newmath"
	"os"
)

func main() {  
	result := newmath.Sqrt(2)
	fmt.Printf("Hello World!\n")
	fmt.Printf("Number:%d\n", result)
	mathii.SayHi("YaFengLi")
	ReadLine("e:/tmp/test.c",processLine)
}

func processLine(line []byte) {
	os.Stdout.Write(line)
}
func ReadLine(filePth string, hookfn func([]byte)) error {
  f, err := os.Open(filePth)
  if err != nil {
   return err
  }
  defer f.Close()

 bfRd := bufio.NewReader(f)
  for {
   line, err := bfRd.ReadBytes('\n')
   hookfn(line) //放在错误处理前面，即使发生错误，也会处理已经读取到的数据。
   if err != nil { //遇到任何错误立即返回，并忽略 EOF 错误信息
    if err == io.EOF {
     return nil
    }
    return err
   }
  }
  return nil
}

