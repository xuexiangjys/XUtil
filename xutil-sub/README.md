# XUtil-Sub

附加功能的Android工具类库

## 在线文档

[点击查看【v2.0.0】](https://jitpack.io/com/github/xuexiangjys/XUtil/xutil-sub/2.0.0/javadoc/)

## APIs

* ### NotificationUtils -> 通知栏工具类

```
buildSimple                 : 构建简单的通知
buildBigPic                 : 构建带图片的通知
buildBigText                : 构建多文本通知
buildMailBox                : 构建带多条消息合并的消息盒通知
buildProgress               : 构建[带/无精确]进度条的通知
buildCustomView             : 构建自定义通知

notify                      : 通知
cancel                      : 取消通知
cancelAll                   : 取消所有通知
```

* ### FragmentUtils  -> Fragment 相关工具类
```
add                   : 新增 fragment
show                  : 显示 fragment
hide                  : 隐藏 fragment
showHide              : 先显示后隐藏 fragment
replace               : 替换 fragment
pop                   : 出栈 fragment
popTo                 : 出栈到指定 fragment
popAll                : 出栈所有 fragment
remove                : 移除 fragment
removeTo              : 移除到指定 fragment
removeAll             : 移除所有 fragment
getTop                : 获取顶部 fragment
getTopInStack         : 获取栈中顶部 fragment
getTopShow            : 获取顶部可见 fragment
getTopShowInStack     : 获取栈中顶部可见 fragment
getFragments          : 获取同级别的 fragment
getFragmentsInStack   : 获取同级别栈中的 fragment
getAllFragments       : 获取所有 fragment
getAllFragmentsInStack: 获取栈中所有 fragment
findFragment          : 查找 fragment
dispatchBackPress     : 处理 fragment 回退键
setBackgroundColor    : 设置背景色
setBackgroundResource : 设置背景资源
setBackground         : 设置背景
```

* ### PackageUtils  -> APK安装工具

```
install                         : 自动安装程序（如果是系统程序或者root过，使用静默安装，否则使用系统的安装方式）
installSilent                   : 静默的安装方式
installNormal                   : 使用系统的安装方式
uninstall                       : 自动卸载程序
uninstallSilent                 : 静默的卸载方式
uninstallNormal                 : 使用系统的卸载方式
isSystemApplication             : 是否是系统应用
```

* ### PendingIntentUtils -> 跳转意图工具类

```
buildActivityIntent             : 构建跳转Activity的意图
buildBroadcastIntent            : 构建广播的意图
```

* ### PollingUtils -> 轮询工具类

```
isPollingServiceExist           : 判断是否存在轮询服务
startPollingService             : 开启轮询服务
stopPollingService              : 停止轮询服务
```

* ### Reflect -> 反射工具类

```
on                              : 包裹某个类或对象
accessible                      : 将类或者对象的某个成员变为可访问
set                             : 设置某个字段的值
get                             : 获取某个字段的值
field                           : 获取某个字段
fields                          : 获取所有字段的集合
call                            : 执行某个方法
exactMethod                     : 精准地获取某个方法
similarMethod                   : 获取某个相似签名的方法
create                          : 执行构造方法
as                              : 创建一个类对象的代理，这样就可以安全地执行代理定义的接口方法
type                            : 获取包裹对象的类型

```

* ### IDCardUtils -> 身份证工具类

```
validateCard                    : 验证身份证是否合法
hideIdCard                      : 隐藏身份证信息
getBirthByIdCard                : 根据身份编号获取出生日期
getAgeDescriptionByIdCard       : 根据身份编号获取年龄描述信息
```

* ### RandomUtils -> 随机生成工具

```
getRandomNumbersAndLetters          : 在数字和英文字母中获取一个定长的随机字符串
getRandomNumbers                    : 在数字中获取一个定长的随机字符串
getRandomLetters                    : 在英文字母中获取一个定长的随机字符串
getRandomCapitalLetters             : 在大写英文字母中获取一个定长的随机字符串
getRandomLowerCaseLetters           : 在小写英文字母中获取一个定长的随机字符串
getRandom                           : 在一个字符数组源中获取一个定长的随机字符串
getRandomColor                      : 获取随机颜色
shuffle                             : 随机打乱数组中的内容
```

* ### SpanUtils -> SpannableString相关工具类
```
setFlag           : 设置标识
setForegroundColor: 设置前景色
setBackgroundColor: 设置背景色
setLineHeight     : 设置行高
setQuoteColor     : 设置引用线的颜色
setLeadingMargin  : 设置缩进
setBullet         : 设置列表标记
setIconMargin     : 设置图标
setFontSize       : 设置字体尺寸
setFontProportion : 设置字体比例
setFontXProportion: 设置字体横向比例
setStrikethrough  : 设置删除线
setUnderline      : 设置下划线
setSuperscript    : 设置上标
setSubscript      : 设置下标
setBold           : 设置粗体
setItalic         : 设置斜体
setBoldItalic     : 设置粗斜体
setFontFamily     : 设置字体系列
setTypeface       : 设置字体
setAlign          : 设置对齐
setClickSpan      : 设置点击事件
setUrl            : 设置超链接
setBlur           : 设置模糊
setShader         : 设置着色器
setShadow         : 设置阴影
setSpans          : 设置样式
append            : 追加样式字符串
appendLine        : 追加一行样式字符串
appendImage       : 追加图片
appendSpace       : 追加空白
create            : 创建样式字符串
```

* ### SnackbarUtils -> Snackbar工具类
```
with           : 设置 snackbar 依赖 view
setMessage     : 设置消息
setMessageColor: 设置消息颜色
setBgColor     : 设置背景色
setBgResource  : 设置背景资源
setDuration    : 设置显示时长
setAction      : 设置行为
setBottomMargin: 设置底边距
show           : 显示 snackbar
showSuccess    : 显示预设成功的 snackbar
showWarning    : 显示预设警告的 snackbar
showError      : 显示预设错误的 snackbar
dismiss        : 消失 snackbar
getView        : 获取 snackbar 视图
addView        : 添加 snackbar 视图
```

* ### TypeBuilder -> 泛型类型生成工具

```
newInstance             : 获取类型构建者
beginSubType            : 开始泛型类型
endSubType              : 结束泛型类型
addTypeParam            : 增加泛型类型
addTypeParamExtends     : 增加泛型extends类型
addTypeParamSuper       : 增加泛型Super类型
build                   : 构建类型,获得泛型类型
```

* ### BLEHelper -> 低功耗蓝牙连接助手(单例)

```
setTimeout                      : 设置扫描时间
startScanListener               : 开始扫描倒计时
cancelScanListener              : 取消倒计时
isCorrectDevice                 : 是否是指定的蓝牙设备
openBluetooth                   : 开启蓝牙
searchDevices                   : 扫描蓝牙设备
startSearch                     : 开始扫描蓝牙
stopSearch                      : 停止扫描蓝牙
isOpenBluetooth                 : 是否已经开启蓝牙
setOnSearchDeviceListener       : 设备蓝牙设备扫描监听
setBluetoothDeviceFilter        : 设置蓝牙设备过滤器
getBluetoothDevice              : 根据地址获取蓝牙设备
release                         : 资源释放
```

* ### BluetoothHelper -> 经典蓝牙匹配辅助类（单例）

```
openBluetooth                   : 开启蓝牙
searchDevices                   : 扫描蓝牙设备
startSearch                     : 开始扫描蓝牙
stopSearch                      : 停止扫描蓝牙
pairBtDevice                    : 根据地址匹配具体的蓝牙设备
isOpenBluetooth                 : 是否已经开启蓝牙
isBtAddressValid                : 检验蓝牙地址的有效性
isCorrectDevice                 : 蓝牙设备是否正确
isBluetoothBond                 : 蓝牙是否已绑定
release                         : 资源释放
setOnBluetoothDeviceListener    : 设置蓝牙设备监听
setBluetoothDeviceFilter        : 设置蓝牙设备过滤器
getBluetoothDevice              : 根据地址获取蓝牙设备
```

* ### WifiAPUtils -> wifi热点工具类

```
startWifiAp             : 打开热点
stopWifiAp              : 关闭热点
getWifiApState          : 获取热点的连接状态
isWifiApEnable          : 热点是否打开
```

* ### WifiHelper -> Wifi连接、断开、状态获取辅助类（单例）

```
connectWifi                     : 连接指定wifi
openWifi                        : 打开wifi
closeWifi                       : 关闭wifi
isConnectSuccess                : 判断wifi是否已连上指定路由
setOnConnectWifiStateListener   : 设置网络连接状态监听
setConnectTimeOut               : 设置连接超时时间

```

* ### ClipboardUtils -> 剪贴板相关

```
copyText  : 复制文本到剪贴板
getText   : 获取剪贴板的文本
copyUri   : 复制 uri 到剪贴板
getUri    : 获取剪贴板的 uri
copyIntent: 复制意图到剪贴板
getIntent : 获取剪贴板的意图
```

* ### EditTextShakeHelper -> 输入框震动效果帮助类（单例）

```
setShakeAnimation           : 设置震动动画
shake                       : 开始震动
```

* ### FlashlightUtils -> 闪光灯相关

```
getInstance              : 获取闪光灯实例
Instance.register        : 注册
Instance.unregister      : 注销
Instance.setFlashlightOn : 打开闪光灯
Instance.setFlashlightOff: 关闭闪光灯
Instance.isFlashlightOn  : 判断闪光灯是否打开
isFlashlightEnable       : 判断设备是否支持闪光灯
```

* ### PhoneUtils -> 手机相关工具类

```
isPhone                 : 判断设备是否是手机
getIMEI                 : 获取 IMEI 码
getIMSI                 : 获取 IMSI 码
getPhoneType            : 获取移动终端类型
isSimCardReady          : 判断 sim 卡是否准备好
getSimOperatorName      : 获取 Sim 卡运营商名称
getPhoneInfo            : 获取手机状态信息
dial                    : 跳至拨号界面
call                    : 拨打电话
sendSms                 : 跳至发送短信界面
sendSmsSilent           : 静默发送短信
```

* ### ROMUtils -> 定制系统识别工具类

```
getROMType              : 获得ROM的类型
```

* ### ThreadPoolManager -> 线程池管理类（单例）

```
updateThreadPool      : 更新线程池
execute               : 在未来某个时间执行给定的命令
execute               : 在未来某个时间执行给定的命令链表
shutDown              : 待以前提交的任务执行完毕后关闭线程池
shutDownNow           : 试图停止所有正在执行的活动任务
isShutDown            : 判断线程池是否已关闭
isTerminated          : 关闭线程池后判断所有任务是否都已完成
awaitTermination      : 请求关闭、发生超时或者当前线程中断
submit                : 提交一个 Callable 任务用于执行
submit                : 提交一个 Runnable 任务用于执行
invokeAll, invokeAny  : 执行给定的任务
schedule              : 延迟执行 Runnable 命令
schedule              : 延迟执行 Callable 命令
scheduleWithFixedRate : 延迟并循环执行命令
scheduleWithFixedDelay: 延迟并以固定休息时间循环执行命令
```


    