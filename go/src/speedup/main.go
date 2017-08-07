package main

import (
	"flag"
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
	"time"
)

var myClient = &http.Client{Timeout: 10 * time.Second}

const etURL = "http://61.160.183.220/jsts/ebit/start"

func httpGet(url string) string {
	resp, err := http.Get(url)
	if err != nil {
		fmt.Printf("GET %s ERR!\r\n", url)
	}

	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		fmt.Printf("READ BODY %s ERR!\r\n", url)
	}
	return string(body)
}

func main() {
	un := flag.String("un", "b00139748", "User Number")
	key := flag.String("un", "greatbit@2012", "Securet Key")
	flag.Parse()
	//fmt.Println(*un)
	body := httpGet("http://221.231.154.58:9696/ip?un=" + *un)

	var m map[string]string
	var ss []string

	ss = strings.Split(body, "&")
	m = make(map[string]string)
	for _, pair := range ss {
		z := strings.Split(pair, "=")
		m[z[0]] = z[1]
	}
	//fmt.Println(ss)
	ip, exists := m["ip"]
	if exists {
		fmt.Println(ip)
		ips := strings.Split(ip, "+")

		for _, _ip := range ips {
			fmt.Println(_ip)
			c1 := httpGet(etURL + "/" + *un + "/" + _ip + "/down/100?k=" + *key)
			fmt.Println(c1)
			c2 := httpGet(etURL + "/" + *un + "/" + _ip + "/up/10?k=" + *key)
			fmt.Println(c2)
		}
	}
}
