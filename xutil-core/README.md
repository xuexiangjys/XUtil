# XUtil-Core

核心基础的Android工具类库

## APIs

* ### ActivityUtils -> Activity页面跳转工具类

```
startActivity                    : 页面跳转
startActivityForResult           : 页面跳转,返回数据
getActivityIntent                : 获取Activity跳转意图
```

* ### AppUtils -> App 相关工具类

```
isInstallApp                    : 判断 App 是否安装
installApp                      : 安装 App(支持 8.0)
uninstallApp                    : 卸载 App
isAppRoot                       : 判断 App 是否有 root 权限
launchApp                       : 打开 App
exitApp                         : 退出 App
getAppPackageName               : 获取 App 包名
getAppDetailsSettings           : 获取 App 具体设置
getAppName                      : 获取 App 名称
getAppIcon                      : 获取 App 图标
getAppPath                      : 获取 App 路径
getAppVersionName               : 获取 App 版本号
getAppVersionCode               : 获取 App 版本码
isSystemApp                     : 判断 App 是否是系统应用
isAppDebug                      : 判断 App 是否是 Debug 版本
getAppSignature                 : 获取 App 签名
getAppSignatureSHA1             : 获取应用签名的的 SHA1 值
isAppForeground                 : 判断 App 是否处于前台
getAppInfo                      : 获取 App 信息
getAppsInfo                     : 获取所有已安装 App 信息
cleanAppData                    : 清除 App 所有数据

```

* ### BroadcastUtils -> 广播工具类

```
getBroadCastIntent              : 获取广播意图
sendBroadCast                   : 发广播
registerReceiver                : 注册广播接收器
unregisterReceiver              : 注销广播接收器
```

* ### IntentUtils -> 意图相关工具类

```
getInstallAppIntent             : 获取安装 App（支持 8.0）的意图
getUninstallAppIntent           : 获取卸载 App 的意图
getLaunchAppIntent              : 获取打开 App 的意图
getAppDetailsSettingsIntent     : 获取 App 具体设置的意图
getShareTextIntent              : 获取分享文本的意图
getShareImageIntent             : 获取分享图片的意图
getComponentIntent              : 获取其他应用组件的意图
getShutdownIntent               : 获取关机的意图
getDialIntent                   : 获取跳至拨号界面意图
getCallIntent                   : 获取拨打电话意图
getSendSmsIntent                : 获取发送短信界面的意图
getCaptureIntent                : 获取拍照的意图
getExtras                       : 获取附加数据
getBundleExtra                  : 获取Intent中的Bundle数据
getStringExtra                  : 获取Intent中的String数据
getBooleanExtra                 : 获取Intent中的Boolean数据
getIntExtra                     : 获取Intent中的Int数据
getFloatExtra                   : 获取Intent中的Float数据
getSerializableExtra            : 获取Intent中的Serializable数据
getBundleSerializable           : 获取Intent中的Bundle携带的Serializable数据
getIntent                       : 获取Intent意图
putExtra                        : 向Intent中传递数据
putBundle                       : 向Bundle中传递数据
getPickIntentWithGallery        : 获取选择照片的 Intent
getPickIntentWithDocuments      : 获取从文件中选择照片的 Intent
getDocumentPickerIntent         : 获取文件选择的 Intent
```

* ### ProcessUtils -> 进程相关工具类

```
getForegroundProcessName            : 获取前台线程包名
getAllBackgroundProcesses           : 获取后台服务进程
killAllBackgroundProcesses          : 杀死所有的后台服务进程
killBackgroundProcesses             : 杀死后台服务进程
gc                                  : 清理后台进程与服务
isMainProcess                       : 判断应用当前所处进程是否是主进程
getCurrentProcessName               : 获取当前进程的名字
```

* ### ServiceUtils -> 服务相关工具类

```
getAllRunningService                : 获取所有运行的服务
startService                        : 启动服务
stopService                         : 停止服务
bindService                         : 绑定服务
unbindService                       : 解绑服务
isServiceRunning                    : 判断服务是否运行
stopAllRunningService               : 停止指定应用的所以运行的服务
```

* ### Logger -> Logger日志记录

```
setLogger                           : 设置日志记录者的接口
setTag                              : 设置日志的tag
setDebug                            : 设置是否是调试模式
setPriority                         : 设置打印日志的等级（只打印改等级以上的日志）
debug                               : 设置是否打开调试
v                                   : 打印任何（所有）信息
vTag                                : 打印任何（所有）信息
d                                   : 打印调试信息
dTag                                : 打印调试信息
i                                   : 打印提示性的信息
iTag                                : 打印提示性的信息
w                                   : 打印warning警告信息
wTag                                : 打印warning警告信息
e                                   : 打印出错信息
eTag                                : 打印出错信息
wtf                                 : 打印严重的错误信息
wtfTag                              : 打印严重的错误信息
```

* ### ClickUtils -> 快速点击工具类

```
isFastDoubleClick                   : 是否是快速点击
exitBy2Click                        : 双击返回退出程序
```

* ### ObjectUtils -> 对象相关工具类

```
isEmpty                             : 返回object是否为空
isNotEmpty                          : 返回object是否不为空
equals                              : 返回两个对象是否相等
requireNonNull                      : 判断对象是否为null，为null就直接抛出空指针异常
getOrDefault                        : 返回一个非空的对象或者默认值
```

* ### ShellUtils -> shell命令工具类

```
checkRootPermission                 : 验证是否获取root权限
execCommand                         : 执行shell命令
```

* ### StringUtils -> String相关工具类

```
isEmpty                             : 判断字符串是否为 null 或长度为 0
isEmptyTrim                         : 判断字符串是否为 null 或全为空格
toInt                               : String转Int（防止崩溃）
toFloat                             : String转Float（防止崩溃）
toShort                             : String转Short（防止崩溃）
toLong                              : String转Long（防止崩溃）
toDouble                            : String转Double（防止崩溃）
toBoolean                           : String转Boolean（防止崩溃）
getString                           : 获取String内容
isInteger                           : 判断字符串是否是整数
isDouble                            : 判断字符串是否是双精度浮点数
isNumber                            : 判断字符串是否是数字
isSpace                             : 判断字符串是否为 null 或全为空白字符
equals                              : 判断两字符串是否相等
equalsIgnoreCase                    : 判断两字符串忽略大小写是否相等
length                              : 返回字符串长度
upperFirstLetter                    : 首字母大写
lowerFirstLetter                    : 首字母小写
reverse                             : 反转字符串
getStackTraceString                 : 获取异常栈信息，不同于Log.getStackTraceString()，该方法不会过滤掉UnknownHostException.
concat                              : 字符串连接，将参数列表拼接为一个字符串
concatSpiltWith                     : 字符串连接，将参数列表通过分隔符拼接为一个字符串
contains                            : 判断一个数组里是否包含指定对象
toString                            : 将对象转化为String
replaceSpecialCharacter             : 过滤字符串中所有的特殊字符
replaceBracket                      : 过滤字符串中的[和]
replaceBlank                        : 过滤字符串中的空格
stringToList                        : 根据分隔符将String转换为List
listToString                        : 根据分隔符将List转换为String
getSimpleName                       : 获取对象的类名
format2Decimals                     : 将字符串格式化为带两位小数的字符串
compareVersionName                  : 比较两个版本号
```

* ### ACache -> 缓存相关工具类【键值对存储】

```
get             : 获取缓存实例
get().put            : 缓存中写入数据
get().getBytes       : 缓存中读取字节数组
get().getString      : 缓存中读取 String
get().getJSONObject  : 缓存中读取 JSONObject
get().getJSONArray   : 缓存中读取 JSONArray
get().getBitmap      : 缓存中读取 Bitmap
get().getDrawable    : 缓存中读取 Drawable
get().getParcelable  : 缓存中读取 Parcelable
get().getSerializable: 缓存中读取 Serializable
get().getCacheSize   : 获取缓存大小
get().getCacheCount  : 获取缓存个数
get().remove         : 根据键值移除缓存
get().clear          : 清除所有缓存
```

* ### CloneUtils -> 克隆相关工具类

```
deepClone                       : 深度克隆对象
```

* ### ConvertTools -> 转换相关工具类

```
byte2HexString                      : 一个byte转16进制String
bytes2HexString                     : byte数组转16进制String
hexStringToByteArray                : 16进制表示的字符串转换为字节数组
byteToIntUnSigned                   : 一位byte转int【无符号】
byteToIntSigned                     : 一位byte转int【有符号】
intToByte                           : int转Byte 【仅对0~255的整型有效】
intToBytesLittleEndian              : 将int数值转换为占四个字节的byte数组，本方法适用于(【小端】低位在前，高位在后)的顺序。
fillIntToBytesLittleEndian          : 将int数值填充至byte数组的指定位置，本方法适用于(【小端】低位在前，高位在后)的顺序。
intToBytesBigEndian                 : 将int数值转换为占四个字节的byte数组，本方法适用于(【大端】高位在前，低位在后)的顺序。
fillIntToBytesBigEndian             : 将int数值填充至byte数组的指定位置，本方法适用于(【大端】高位在前，低位在后)的顺序。
bytesToIntLittleEndian              : byte数组中取int数值，本方法适用于(【小端】低位在前，高位在后)的顺序
bytesToIntBigEndian                 : byte数组中取int数值，本方法适用于(【大端】高位在前，低位在后)的顺序。
shortToBytesLittleEndian            : 将short数值转换为占两个字节的byte数组，本方法适用于(【小端】低位在前，高位在后)的顺序。
fillShortToBytesLittleEndian        : 将short数值填充至byte数组的指定位置，本方法适用于(【小端】低位在前，高位在后)的顺序
fillUnsignedShortToBytesLittleEndian : 将无符号short数值填充至byte数组的指定位置，本方法适用于(【小端】低位在前，高位在后)的顺序
shortToBytesBigEndian               : 将short数值转换为占两个字节的byte数组，本方法适用于(【大端】高位在前，低位在后)的顺序。
fillShortToBytesBigEndian           : 将short数值填充至byte数组的指定位置，本方法适用于(【大端】高位在前，低位在后)的顺序
fillUnsignedShortToBytesBigEndian   : 将无符号short数值填充至byte数组的指定位置，本方法适用于(【大端】高位在前，低位在后)的顺序
bytesToShortLittleEndian            : byte数组中取short数值，本方法适用于(【小端】低位在前，高位在后)的顺序
bytesToShortBigEndian               : byte数组中取short数值，本方法适用于(【大端】高位在前，低位在后)的顺序
```


* ### DateUtils -> 日期时间工具类

```
millis2String                       : 将时间戳转为时间字符串
date2String                         : 将 Date 类型转为时间字符串
string2Millis                       : 将时间字符串转为时间戳
string2Date                         : 将时间字符串转为 Date 类型
date2Millis                         : 将 Date 类型转为时间戳
millis2Date                         : 将时间戳转为 Date 类型
translateDateFormat                 : 转换日期格式 oldFormat ---> newFormat
isDateFormatRight                   : 判断时间字符串的格式是否正确
nDaysBeforeToday                    : 获取当前日期n天前的日期，返回String
nDaysAfterToday                     : 获取当前日期n天后的日期，返回String
getTimeSpan                         : 获取两个时间差（单位：unit）
getFitTimeSpan                      : 获取合适型两个时间差
getTimeSpanByNow                    : 获取与当前时间的差（单位：unit）
getFitTimeSpanByNow                 : 获取合适型与当前时间的差
getFriendlyTimeSpanByNow            : 获取友好型与当前时间的差
getFuzzyTimeDescriptionByNow        : 根据时间戳获取模糊型的时间描述。
getAgeByBirthDay                    : 根据出生日期获取年龄
getNowMills                         : 获取当前毫秒时间戳
getNowString                        : 获取当前时间字符串
getNowDate                          : 获取当前 Date
isToday                             : 判断是否今天
getWeekIndex                        : 获取星期索引
getYear                             : 得到年份
getMonth                            : 得到月
getDay                              : 得到日
getChineseZodiac                    : 获取生肖
getZodiac                           : 获取星座
```

* ### SPUtils -> SharedPreferences工具类

```
getSharedPreferences                    : 获取SharedPreferences实例
putBoolean                              : 设置boolean值
putFloat                                : 设置float值
putLong                                 : 设置long值
putString                               : 设置String值
putInt                                  : 设置int值
putObject                               : 设置Object
putEncodeObject                         : 设置加密Object
put                                     : 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
getBoolean                              : 根据key获取boolean值
getLong                                 : 根据key获取long值
getFloat                                : 根据key获取float值
getString                               : 根据key获取String值
getInt                                  : 根据key获取int值
getEncodeObject                         : 获取加密的对象
getObject                               : 获取对象
get                                     : 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
contains                                : 查询某个key是否已经存在
getAll                                  : 返回所有的键值对
remove                                  : 去除某一键值对
clear                                   : 清空销毁
```

* ### BarUtils -> 栏相关
```
getStatusBarHeight                   : 获取状态栏高度（px）
setStatusBarVisibility               : 设置状态栏是否可见
isStatusBarVisible                   : 判断状态栏是否可见
setStatusBarLightMode                : 设置状态栏是否为浅色模式
addMarginTopEqualStatusBarHeight     : 为 view 增加 MarginTop 为状态栏高度
subtractMarginTopEqualStatusBarHeight: 为 view 减少 MarginTop 为状态栏高度
setStatusBarColor                    : 设置状态栏颜色
setStatusBarAlpha                    : 设置状态栏透明度
setStatusBarColor4Drawer             : 为 DrawerLayout 设置状态栏颜色
setStatusBarAlpha4Drawer             : 为 DrawerLayout 设置状态栏透明度
getActionBarHeight                   : 获取 ActionBar 高度
setNotificationBarVisibility         : 设置通知栏是否可见
getNavBarHeight                      : 获取导航栏高度
setNavBarVisibility                  : 设置导航栏是否可见
setNavBarImmersive                   : 设置导航栏沉浸式
setNavBarColor                       : 设置导航栏颜色
getNavBarColor                       : 获取导航栏颜色
isNavBarVisible                      : 判断导航栏是否可见
```

* ### ColorUtils -> 颜色相关工具类

```
adjustAlpha                         : 矫正颜色的透明度
setColorAlpha                       : 设置颜色的alpha值
colorToString                       : 将 color 颜色值转换为十六进制字符串
darker                              : 加深颜色
lighter                             : 变浅颜色
isColorDark                         : 是否是深色的颜色
getStateColor                       : 获取某状态颜色
getEnableColor                      : 获取可点击时的颜色
getDisableColor                     : 获取不可点击时的颜色
getDefaultColor                     : 获取默认的颜色
getRandomColor                      : 获取随机颜色
```

* ### DensityUtils -> 屏幕密度工具类

```
dip2px                               : 根据手机的分辨率从 dp 的单位 转成为 px(像素)
px2dip                               : 根据手机的分辨率从 px(像素) 的单位 转成为 dp
sp2px                                : 根据手机的分辨率从 sp 的单位 转成为 px
px2sp                                : 根据手机的分辨率从 px(像素) 的单位 转成为 sp
getScreenDpi                         : 获取屏幕分辨率
getScreenWidth                       : 得到设备屏幕的宽度
getScreenHeight                      : 得到设备屏幕的高度
getScreenDensity                     : 得到设备的密度
```

* ### ImageUtils -> 图片相关工具类

```
bitmap2Bytes, bytes2Bitmap      : bitmap 与 bytes 互转
drawable2Bitmap, bitmap2Drawable: drawable 与 bitmap 互转
drawable2Bytes, bytes2Drawable  : drawable 与 bytes 互转
view2Bitmap                     : view 转 bitmap
getBitmap                       : 获取 bitmap
scale                           : 缩放图片
clip                            : 裁剪图片
skew                            : 倾斜图片
rotate                          : 旋转图片
getRotateDegree                 : 获取图片旋转角度
toRound                         : 转为圆形图片
toRoundCorner                   : 转为圆角图片
addCornerBorder                 : 添加圆角边框
addCircleBorder                 : 添加圆形边框
addReflection                   : 添加倒影
addTextWatermark                : 添加文字水印
addImageWatermark               : 添加图片水印
toAlpha                         : 转为 alpha 位图
toGray                          : 转为灰度图片
fastBlur                        : 快速模糊
renderScriptBlur                : renderScript 模糊图片
stackBlur                       : stack 模糊图片
save                            : 保存图片
isImage                         : 根据文件名判断文件是否为图片
getImageType                    : 获取图片类型
compressByScale                 : 按缩放压缩
compressByQuality               : 按质量压缩
compressBySampleSize            : 按采样大小压缩
```

* ### ScreenUtils -> 屏幕相关工具类

```
getScreenWidth     : 获取屏幕的宽度（单位：px）
getScreenHeight    : 获取屏幕的高度（单位：px）
getScreenDensity   : 获取屏幕密度
getScreenDensityDpi: 获取屏幕密度 DPI
setFullScreen      : 设置屏幕为全屏
setLandscape       : 设置屏幕为横屏
setPortrait        : 设置屏幕为竖屏
isLandscape        : 判断是否横屏
isPortrait         : 判断是否竖屏
getScreenRotation  : 获取屏幕旋转角度
screenShot         : 截屏
isScreenLock       : 判断是否锁屏
setSleepDuration   : 设置进入休眠时长
getSleepDuration   : 获取进入休眠时长
isTablet           : 判断是否是平板
```