# myLab
下面记录一些简单的md语法
#一级目录
1. 第一点,"1.后面要加空格才行哦"
2. 第二点
* 微信 "同样的*后面也要加空格才行哦"
* 支付宝  
加两个空格然后回车就是换行  
三只青蛙  
*[x] 第一只青蛙
*[ ] 第二只青蛙
*[ ] 第三只青蛙
##
###三级目录

==========================================================
## git使用
### 安装git
1.  这一步毋庸多说，下载git安装包，安装到操作系统中
2.  安装完成后，需要设置username和useremail，这一步是因为git是分布式版本控制系统，所以每个机器都必须自报家门
git config --global user.name "Your name"
git config --global user.email "email@example.com"
###创建版本库，往版本库中提交一个文件
版本库repository，这个目录下的所有文件都会被git管理和跟踪起来。    
通过git命令可以非常方便的创建版本库，首先在一个合适的地方准备一个空目录，在该目录下输入命令：  
git init  
版本库就创建好了，会发现当前目录下多了一个.git目录，不要乱改它。  
创建和提交一个文件  
git add file1.txt   将文件存入storge区  
git commit -m "add file1.txt"   将文件提交md
###使用github托管代码，以及如何从本地往github上的个人仓库提交代码
1. 首先要有一个github账户，登陆到github上或者使用githubDesktop(GitHub的桌面版)，都可以方便的在github上创建一个repository
2. github上创建一个repository之后这个仓库还是空的，这时将本地仓库与远程仓库进行关联有两种方式  
第一种，在github上使用clone功能，会得到一个用于clone的URL，例如：git@github.com:chrisLfj/mylab.git，后在本地创建一个对应的空文件夹mylab，
本地命令行git clone git@github.com:chrisLfj/mylab.git，这样本地的mylab仓库就与远程的mylab仓库进行关联了。  
第二种，如果本地已经有了mylab仓库，并且里面已经有了很多东西了，这时可以从本地发起与远程的关联，本地命令行：git remote add origin git@github.com:chrisLfj/mylab.gi
建立两者的关联，关联之后远程库的名字就叫origin，这是git默认的叫法。然后在通过命令：git push -u origin master 第一次推送master分支的所有内容到远程，
使用-u参数还会将本地的master分支与远程master分支进行关联，以后推送或者拉取就简单了。
3. 本地与远程仓库关联好之后就可以进行代码提交，现在本地仓库进行了代码的commit之后，本地和远程的仓库就有了差异，这时往远程仓库提交代码有两种方式  
第一种，通过用户名密码的方式，
