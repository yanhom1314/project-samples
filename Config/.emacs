;; author: ya_feng_li
;; e-mail: yafengli@sina.com
;; 从color-theme中获取
;;全屏
(run-with-idle-timer 0.2 nil 'w32-send-sys-command 61488)

(add-to-list 'load-path "~/.emacs.d/common") ;;common
(add-to-list 'load-path "~/.emacs.d/evil") ;;evil
(add-to-list 'load-path "~/.emacs.d/scala-emacs-mode") ;;scala

;;样式
(require 'color-theme)
(color-theme-initialize)
(color-theme-gnome)

;;Evil
(require 'evil)
(evil-mode 1)

;;Smex
(require 'smex)
(smex-initialize)
(global-set-key (kbd "M-x") 'smex)
(global-set-key (kbd "M-X") 'smex-major-mode-commands)

;; This is your old M-x.
(global-set-key (kbd "C-c C-c M-x") 'execute-extended-command)

;;Window-Numbering
(require 'window-numbering)
(window-numbering-mode 1)

;;Package windows not work
;;(when (>= emacs-major-version 24)
;;  (require 'package)
;;  (package-initialize)
;;  (add-to-list 'package-archives '("melpa" . "http://melpa.milkbox.net/packages/") t)
;;  )

;;Clojure
(require 'clojure-mode)
(clojure-mode)
;;Scala
(require 'scala-mode-auto)
(scala-mode)
;;Markdon
(autoload 'markdown-mode "markdown-mode"
   "Major mode for editing Markdown files" t)
(add-to-list 'auto-mode-alist '("\\.md\\'" . markdown-mode))
;;不显示启动页面   
(custom-set-variables
 ;; custom-set-variables was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(column-number-mode t)
 '(display-time-mode t)
 '(ecb-layout-window-sizes nil)
 '(ecb-options-version "2.40")
 '(ede-project-directories (quote ("f:/tmp/demo/hello")))
 '(inhibit-startup-screen t)
 '(show-paren-mode t))
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
(show-paren-mode t)
(setq show-paren-style 'parentheses)
;; 显示时间，格式如下
(display-time-mode 1)  
(setq display-time-24hr-format t)  
(setq display-time-day-and-date t)  

(transient-mark-mode t) 

;; 支持emacs和外部程序的粘贴
(setq x-select-enable-clipboard t) 

;; 在标题栏提示你目前在什么位置
;;(setq frame-title-format "zhj@%b")  
(setq frame-title-format "lyf@%b")  

;; 默认显示 80列就换行 
(setq default-fill-column 80) 

;; 去掉工具栏
(tool-bar-mode nil)

;;去掉菜单栏
(menu-bar-mode nil)

;; 去掉滚动栏
(scroll-bar-mode nil)

;; 设置字体
;; 方法为: emacs->options->Set Default Font->"M-x describe-font"查看当前使用的字体名称、字体大小

(custom-set-faces
 ;; custom-set-faces was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(default ((t (:family "Droid Sans Mono" :foundry "outline" :slant normal :weight normal :height 98 :width normal)))))

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
