### Git的常用命令（抄自<分布式服务架构：原理、设计与实战>）

* Git的主要区域如下，如图所示：
>* 工作区（WorkSpace）: 在计算机中能看到的目录，它持有实际文件
>* 缓存区（Index/Stage）： 临时保存我们的改动
>* 版本库（Repository）： 工作区有一个隐藏目录.git， 是Git的版本库
>* 远程仓库（Remote）：托管在因特网或其他网络中的项目的版本库，可供多人分布式开发

![git](https://github.com/kisslk/git_command/blob/master/git.jpg)

#### (1) Git 配置项

#显示当前的Git配置项  
$git config --list  
#编辑Git配置文件  
$git config -e [--global]  
#设置用户信息  
$git config [--global] user.name "[name]"  
$git config [--global] user.email "[email address]"

###### Git的设置文件为.gitconfig， 它可以全局配置（在用户主目录下），也可以只为项目配置（在项目目录下）。  

#### (2) 新建仓库
#在当前目录新建一个Git仓库  
$git init  
#新建一个目录，将其初始化为Git仓库  
$git init [project-name]
#克隆一个仓库
$git clone [url]

#### (3) 增加、删除文件  
#添加指定文件到暂存区  
$git add [file1] [file2] ...  
#添加指定目录到暂存区，包括子目录  
$git add [dir]  
#添加当前目录的所有文件到暂存区  
$git add .   
#删除工作区文件，并且将这次删除放入缓存区  
$git rm [file1] [file2] ...  
#改名文件，并且将这个改名放入缓存区  
$git mv [file-original] [file-renamed]  

#### (4) 提交文件
#提交缓存区到仓库  
$git commit -m [message]  
#提交缓存区的指定文件到仓库   
$git commit [file1] [file2] ... -m [message]  
#提交工作区自上次commit之后的变化，直接提交到仓库区  
$git commit -a     
#提交时显示所有的diff信息  
$git commit -v  
#使用一次新的commit，替代上一次提交  
#如果代码没有任何新变化，则用来改写上一次commit的提交信息  
$git commit --amend - [message]  

#### (5)Git分支
#列出所有本地分支  
$git branch  
#列出所有远程分支  
$git branch -r
#列出所有本地分支和远程分支  
$git branch -a  
#新建一个分支，但依然停留在当前分支  
$git branch [branch-name]  
#新建一个分支，并切换到该分支  
$git checkout -b [branch]  
#新建一个分支，与指定的远程分支建立追踪关系  
$git branch --track [branch] [remote-branch]  
#切换到指定分支，并更新工作区  
$git checkout [branch-name]  
#切换到上一个分支  
$git checout -  
#建立追踪关系，在现有分支与指定的远程分支之间  
$git branch --set-upstream [branch] [remote-branch]   已经废弃  
$git branch --set-upstream-to=[remote-branch]  
#合并指定分支到当前分支  
$git merge [branch]  
#选择一个commit， 合并在当前分支  
$git cherry-pick [commit]  
#删除分支  
$git branch -d [barnch-name]  
#删除远程分支  
$git push origin --delete [branch-name]  
$git branch -dr [remote-branch] 
$git push origin :[branch-name] 

#### (6)Git的标签
#列出所有的tag  
$git tag   
#在当前commit上新建一个tag  
$git tag [tag]  
#在指定的commit上新建一个tag  
$git tag [tag] [commit]  
#删除本地tag  
$git tag -d [tag]  
#删除远程tag  
$git push origin : refs/tags/[tagName]  
#查看tag信息  
$git show [tag]  
#提交指定tag  
$git push [remote] [tag]  
#提交所有tag  
$git push [remote] --tags  
#新建一个分支,指向某个tag  
$git checkout -b [branch] [tag]  

#### (7)查看信息
#显示有变更的文件  
$git status  
#显示当前分支的版本历史  
$git log  
#根据关键词搜索提交历史  
$git log -S [keyword]  
#显示指定文件是什么人在什么时间修改过   
$git blame [file]    
#显示暂存区和工作区的差异  
$git diff  
#显示暂存区和上一个commit的差异  
$git diff --cached [file]  
#显示工作区和当前分支最新commit之间的差异  
$git diff HEAD  
#显示两次提交之间的差异  
$git diff [first-branch] ... [second-branch]  
#显示某次体检的元数据和内容变化  
$git show [commit]  
#显示某次提交发生变化的文件    
$git show --name-only [commit]  

#### (8) 远程同步
#下载远程仓库的所有变动  
$git fetch [remote]  
#显示所有远程仓库  
$git remote -v   
#显示某个远程仓库的信息  
$git remote show [remote]  
#取回远程仓库的变化，并与本地分支合并  
$git pull [remote] [branch]  
#上传本地指定分支到远程仓库  
$git push [remote] [branch]   
#强行推送当前分支到远程仓库，即使有冲突  
$git push [remote] -all  

#### (9) 撤销
#恢复暂存区指定的文件到工作区  
$git checkout [file]  
#恢复某个commit的指定文件到暂存区和工作区  
$git checkout [commit] [file]  
#恢复暂存区的所有文件到工作区  
$git checkout .  
#重置暂存区的指定文件，与上一次commit保持一致，但工作区不变  
$git reset [file]  
#重置暂存区与工作区，与上一次commit保持一致  
$git reset --hard  
#重置当前分支的HEAD为指定commit，同时重置暂存区和工作区，与指定commit一致  
$git reset --hard [commit]  
#重置当前分支的HEAD为指定的commit，但保持暂存区和工作区不变  
$git reset --keep [commit]  
#新建一个commit，用来撤销指定commit  
#后者的所有变化都将被前者抵消，并且应用到当前分支  
$git revert [commit]     
#暂时将未提交的变化移除，稍后再移入  
$git stash   
$git stash pop  

#### (10) 远程代码库回滚
#这个是重点要说的内容，过程比本地回滚要复杂
#应用场景：自动部署系统发布后发现问题，需要回滚到某一个commit，再重新发布
#原理：先将本地分支退回到某个commit，删除远程分支，再重新push本地分支
#操作步骤：

$1、git checkout the_branch  
$2、git pull  
$3、git branch the_branch_backup //备份一下这个分支当前的情况  
$4、git reset --hard the_commit_id //把the_branch本地回滚到the_commit_id  
$5、git push origin :the_branch //删除远程 the_branch  
$6、git push origin the_branch //用回滚后的本地分支重新建立远程分支  
$7、git push origin :the_branch_backup //如果前面都成功了，删除这个备份分支  
$如果使用了gerrit做远程代码中心库和code review平台，需要确保操作git的用户具备分支的push权限，并且选择了 Force Push选项（在push权限设置里有这个选项）
