# zlbyzc代码编译说明

以下菜单说明不一定准确，以实际程序中为准。

* 首先在 https://ac.liukan.org:8211 注册帐号并在群里通知 liuk 开通相关权限、
* 使用以下命令clone代码，按照提示输入注册时的用户名，密码。
```
git clone --depth=1 https://ac.liukan.org:8211/liuk/zlbyzc.git
```
* 安装eclipse 4.5.x 和jdk 1.8 u77以上版本
* eclipse->window->Perferences->General->workspace->text file encoding->other->utf-8
* eclipse->import ->general->Existing Projects...
* eclipse->window->Perferences->Compiler->Errors/Warnings->Deprecated...->所有的选项改成warning
* eclipse->zlbyzc项目右键build path->configure build path->libraries->删除jre system->重新add library添加jre system->ok
* Open Eclipse and go to Help > Install New Software and insert the URL http://download.eclipse.org/efxclipse/updates-released/2.3.0/site under "Work with:" and press enter
* Once the packages are loaded, select and install them both

如果使用的是Linux或Mac系统
* There is one more step to do: add the jfxrt.jar to the classpath by going to the project properties and selecting "Add external JAR ..."

    Linux: /path/to/jdk/jre/lib/ext/jfxrt.jar
    Mac OS: ./Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/jfxrt.jar.

* 运行程序，右键点击代码目录中的 zlbyzc->src->zlbyzc->guiSubst....java->run as->java app....
