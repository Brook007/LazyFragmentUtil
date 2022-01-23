# Fragment懒加载工具类

Fragment懒加载工具，支持ViewPager嵌套

## 引入依赖

1. 根项目的build.gradle中加入以下代码

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

2. 在需要的模块加入以下的依赖

```gradle
dependencies {
	implementation 'com.gitee.brook_007:LazyFragmentUtil:1.0.2'
}
```

3. 然后在Fragment的**构造**方法中创建FragmentUserVisibleController实例，再在Fragment中调用FragmentUserVisibleController中对应的方法，推荐在BaseFragment中使用