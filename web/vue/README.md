#### 
`npm install http-server -g`
`npm install`

#### Run
+ `webpack --watch` && `http-server -p 80`
+ Access: http://localhost/


#### 验证使用vue-validate抛弃vue-validator，观察vue-form

#### 解决vue解析闪烁
+ `css`:[v-cloak]{display:none} 在页面中增加：`<span v-cloak>{{message}}</span>`
+ 使用`v-text`和`v-html`，例如：`<span v-text="message"/><span v-html="message"/>`,`v-text`输出转义html与`v-html`相反。
