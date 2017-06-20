1 友盟 activity继承ActivityCommBase
2 极光 application oncreate调用ApplicationUtils.oncreate(), PushReceiver中侦听事件
3 升级及增量升级 借助bmob AppUpdateUtils.check***() bMob应用用于多个app升级时，一定要指定通道BMOB_CHANNEL
4 Retrofit http加载库  HttpRxUtils.next***()
5 glide配置 缓存路径sd卡下 包名/glide

出发点是将此作为aar库，配置还是在主项目中的

注：
1、不要再考虑bmob jar包用gradlew下载了，直接放在libs，其依赖的包无法排除
2、极光的manifestPlaceholders，以及只打包armeabi的配置放在主项目下，放在子项目不行
splits {
        abi {
            enable true
            reset()
            include 'armeabi'//只打包armeabi平台的，就算有其他文件夹也不管
        }
    }

