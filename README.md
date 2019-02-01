# zzc_mobile_arch

# 架构层划分

## app--业务模块
物理划分多模块，业务层开发

## base--app共性
包含业务模块共性的依赖类，平台的定制化

## platformSdk--平台层
集成组件，包含：
Rx，网络，图片处理，livedata，
动态权限，组件二次封装（完成了网络库），
模块通信ARoute，UI组件库

## platformCode--平台核心
平台共性，全局性的逻辑，几乎依赖于所有组件。
主要是application的维护，存储，环境切换，全局log，工具库。

## 组件库
已实现（或集成）了：
网络库，Rxjava，Glide，UI组件库

其中，UI组件库
1、升级了BaseActivity，改名了IntelligentActivityImpl
2、原BaseActivity是直接在里头实现，通过继承来实现多样性
现把BaseActivity的沉浸式、loading、toolbar等通用UI行为，抽象成行为，并支持外部扩展。
用组合代替继承，并遵循开闭原则。

同时，实践了以livedata驱动Activity调用loading行为，感觉代码很酷。
