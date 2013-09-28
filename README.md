##wordpress博客导入到OSchina博客##

使用方法：  

+ 在wordpress后台导出文章xml文件，放到本项目根目录。  
+ 本项目使用maven管理，安装maven，然后在项目根目录执行mvn install，下载依赖的jar包。  
+ 在UserInfo.java这个文件中，设置自己的用户名和密码，注意要设置不是明文密码，而是明文密码的md5（40位小写），可在这里获取。  
+ 比较懒，入口程序是个main方法，所以执行mvn install后，还需要运行Main.java中的main方法。运行后，日志会输出记录，可以查看成功和失败的结果  

遇到问题 mailto:wangxuemeng90@126.com
