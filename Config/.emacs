;; author: ya_feng_li
;; e-mail: yafengli@sina.com
;; 从color-theme中获取
;;全屏
(run-with-idle-timer 0.2 nil 'w32-send-sys-command 61488)
;;增加Package Install Repository
(require 'package) ;; You might already have this line
(add-to-list 'package-archives
             '("melpa" . "http://melpa.org/packages/"))
(package-initialize) ;; You might already have this line

;;Evil
(require 'evil)
(evil-mode 1)
;;样式
(load-theme 'zenburn t)
;;Smex
(require 'smex)
(smex-initialize)
(global-set-key (kbd "M-x") 'smex)
(global-set-key (kbd "M-X") 'smex-major-mode-commands)
;; This is your old M-x.
(global-set-key (kbd "C-c C-c M-x") 'execute-extended-command)
;;NEOTree
(require 'neotree)
(global-set-key [f8] 'neotree-toggle)
;;Window-Numbering
(window-numbering-mode 1)

;;不显示启动页面   
;(custom-set-variables
 ;; custom-set-variables was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
; '(column-number-mode t)
; '(custom-safe-themes
;   (quote
;	("bc40f613df8e0d8f31c5eb3380b61f587e1b5bc439212e03d4ea44b26b4f408a" "f5eb916f6bd4e743206913e6f28051249de8ccfd070eae47b5bde31ee813d55f" "a8245b7cc985a0610d71f9852e9f2767ad1b852c2bdea6f4aadc12cce9c4d6d0" "0ec59d997a305e938d9ec8f63263a8fc12e17990aafc36ff3aff9bc5c5a202f0" default)))
; '(display-time-mode t)
; '(ecb-layout-window-sizes nil)
; '(ecb-options-version "2.40")
; '(ede-project-directories (quote ("f:/tmp/demo/hello")))
; '(inhibit-startup-screen t)
; '(show-paren-mode t))

;;空白干净的页面
(setq initial-scratch-message "") 
;;不生成备份文件
(setq make-backup-files nil)
;;显示行数
(global-linum-mode t)
;;跳转到某一行C-c C-g
(define-key global-map "\C-c\C-g" 'goto-line)
;; 一打开就起用 text 模式。  
(setq default-major-mode 'text-mode)
;; 语法高亮
(global-font-lock-mode t)
;; 以 y/n代表 yes/no
(fset 'yes-or-no-p 'y-or-n-p) 
;; 显示括号匹配 
;(require 'electric)
(electric-pair-mode t) ;自动括号补全
(setq show-paren-style 'parentheses)
;; 显示时间，格式如下
(display-time-mode 1)  
(setq display-time-24hr-format t)  
(setq display-time-day-and-date t)  
(transient-mark-mode t) 
;; 支持emacs和外部程序的粘贴
(setq x-select-enable-clipboard t) 
;; 在标题栏提示你目前在什么位置
(setq frame-title-format "ya_feng_li@163.com")  
;; 默认显示 80列就换行 
(setq default-fill-column 80) 
;; 去掉工具栏
(tool-bar-mode 0)
;;去掉菜单栏
(menu-bar-mode 0)
;; 去掉滚动栏
(scroll-bar-mode 0)
;; 设置字体
;; 方法为: emacs->options->Set Default Font->"M-x describe-font"查看当前使用的字体名称、字体大小

(custom-set-faces
 ;; custom-set-faces was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(default ((t (:family "Source Code Pro" :foundry "outline" :slant normal :weight normal :height 105 :width normal)))))
;; 显示列号
(setq column-number-mode t)
(setq line-number-mode t)
;; 设置缩进
(setq c-basic-offset 4)
(setq indent-tabs-mode nil)
(setq default-tab-width 4)
(setq tab-width 4)
(setq tab-stop-list ())

;; 回车缩进
(global-set-key "\C-m" 'newline-and-indent)
(global-set-key (kbd "C-<return>") 'newline)

;;启动单进程Server
(server-start)

