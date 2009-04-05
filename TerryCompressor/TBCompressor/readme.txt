
Taobao Compressor v2.4.2


安装指南
=========

安装步骤：

1. 安装请点击install.cmd
2. 卸载请点击uninstall.cmd
3. 如果安装过2.4.2之前的版本，请先卸载老版本


压缩测试：

选中test.source.js, 执行右键菜单“Compress CSS or JS”，会生成test.js. 如果再对test.js文件执行一次压缩，会生成test-min.js文件。css文件的压缩方式和js一样


注意事项：

1. 需要安装JDK >= 1.4, 并设置环境变量 JAVA_HOME
2. css和js文件编码必须是GB2312, GBK或GB18030. 如果要支持UTF-8, 请在compressor.cmd中将GB18030替换为UTF-8
3. css文件中含有中文时，如果css编码和页面编码不一致，需要手动将中文替换为\xxxx, 详细说明请参考compressor.cmd中的说明
4. 如果不需要native2ascii, 可以只安装JRE即可（需要手动修改下compressor.cmd）

Ref: 

1. Introducing the YUI Compressor: http://www.julienlecomte.net/blog/2007/08/11/
2. YUILibrary: http://yuilibrary.com/projects/yuicompressor/wiki
3. Documentation: http://developer.yahoo.com/yui/compressor/
4. native2ascii.exe: http://java.sun.com/j2se/1.4.2/docs/tooldocs/windows/native2ascii.html


历史更新
=========
2009-02-12	yubo	1. yuicompressor升级到2.4.2
					2. 重写了compressor.cmd, 添加了比较详细的注释
					3. 改变了在注册表的中的注册方式，以避免与css和js的默认编辑器引起冲突
					4. 右键菜单项中，添加C作为快速选择键
					5. 压缩后，检查taobao.net并警告

2008-10-22	yubo	yuicompressor升级到2.4

2008-05-23	yubo	将reg更改为inf, 改进安装方式；改进cmd的显示和提示信息

