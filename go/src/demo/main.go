
package main

import (
    "fmt"
    "encoding/json"
)

type user struct {
    Un string `json:"un"`
    Count int   `json:"count"`
    Ips []IpAddr `json:"ips"`
}

type IpAddr struct {
    IpAddr string `json:"ip"`
    Time string   `json:"time"`
    Online bool   `json:"online"`
}
func main() {
    un := &user{Un: "test_caocheng_1",Count:100}
    un.Ips = append(un.Ips, IpAddr{IpAddr: "127.0.0.1", Time: "2016090801220"})
    un.Ips = append(un.Ips, IpAddr{IpAddr: "127.0.0.2", Time: "2016090801222"})
    un.Ips = append(un.Ips, IpAddr{IpAddr: "127.0.0.3", Time: "2016090801223",Online:true})
    b, err := json.Marshal(un)
    if err != nil {
        fmt.Println(err)
        return
    }
    fmt.Println(string(b))
}