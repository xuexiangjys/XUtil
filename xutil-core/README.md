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
